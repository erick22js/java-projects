package Asserty;
import java.nio.*;
import java.sql.Savepoint;

public class Cpu extends Device{
	
	private Memory ram;
	
	private final int RAM = 0;
	private final int STK = 1;
	private final int FRM = 2;
	private final int INB = 3;
	
	private short[] mapper = new short[]{0, 0, 0, 1};
	public Memory[] bus;
	public Device[] devices;
	//private ByteBuffer[] bus;
	
	int cmp1 = 0;
	int cmp2 = 0;
	
	int fmp = 0;
	int bnks = 0;
	int bnkd = 0;
	
	int[] regs;
	boolean[] waits;
	
	public Cpu(Memory mem, Memory[] bus, short[] bank_mapper, Device...devices){
		ram = mem;
		this.devices = devices;
		//rom = new Memory(0, new MemoryMapper(new int[]{0,0}));
		regs = new int[Regs.length];
		waits = new boolean[1024];
		this.bus = bus;
		this.mapper = bank_mapper;
		//reBuildBus();
	}
	//Reseta a cpu junto com a memória
	public void reset(){
		for(int i=0; i<regs.length; i++)
			regs[i] = 0;
		regs[Regs.stp] = ram.map.Map[mapper[RAM]][1]-2;
		fmp = ram.map.Map[mapper[FRM]][1]-4;
		ram.reset();
	}
	//Carrega uma memória contendo instruções
	/*
	//Monta toda a memória no sistema para fácil acesso
	public void reBuildBus(){
		bus = new Memory[ram.banksQ+rom.banksQ];
		for(int i=0; i<ram.banksQ; i++)
			bus[i] = new Memory(ram.banks[i], false);
		for(int i=0; i<rom.banksQ; i++)
			bus[ram.banksQ+i] = new Memory(rom.banks[i], true);
	}
	*/
	public void debug(){
		System.out.println(
		" * inp = 0x"+Integer.toHexString(getRegister16(Regs.inp))+"\n"+
		" * stp = 0x"+Integer.toHexString(getRegister16(Regs.stp))+"\n"+
		" * acu = 0x"+Integer.toHexString(getRegister16(Regs.acu))+"\n"+
		" * fmp = 0x"+Integer.toHexString(fmp)+"\n"+
		" * bks = 0x"+Integer.toHexString(bnks)+"\n"+
		" * bkd = 0x"+Integer.toHexString(bnkd)+"\n"+
		" * r1  = 0x"+Integer.toHexString(getRegister16(Regs.r1))+"\n"+
		" * r2  = 0x"+Integer.toHexString(getRegister16(Regs.r2))+"\n"+
		" * r3  = 0x"+Integer.toHexString(getRegister16(Regs.r3))+"\n"+
		" * r4  = 0x"+Integer.toHexString(getRegister16(Regs.r4))+"\n"+
		" * r5  = 0x"+Integer.toHexString(getRegister16(Regs.r5))+"\n"+
		" * r6  = 0x"+Integer.toHexString(getRegister16(Regs.r6))+"\n"+
		" * r7  = 0x"+Integer.toHexString(getRegister16(Regs.r7))+"\n"+
		" * r8  = 0x"+Integer.toHexString(getRegister16(Regs.r8))+"\n"+
		" * r9  = 0x"+Integer.toHexString(getRegister16(Regs.r9))+"\n"+
		" * ax  = 0x"+Integer.toHexString(getRegister16(Regs.ax))+"\n"+
		" * bx  = 0x"+Integer.toHexString(getRegister16(Regs.bx))+"\n"+
		" * cx  = 0x"+Integer.toHexString(getRegister16(Regs.cx))+"\n"+
		" * dx  = 0x"+Integer.toHexString(getRegister16(Regs.dx))+"\n-------------------------------------------------"
		);
		for(int i=0; i<256; i++){
			if((i%16)==0)
				System.out.println();
			System.out.print("0x"+((getMemoryBus8(0,i))<16?"0":"")+Integer.toHexString(getMemoryBus8(0,i))+" ");
		}
		System.out.println();
	}
	
	//Operações de requestação **********************************************************************
	//Só retornam valores na memória
	public int fetch8(){
		int byt = getMemoryBus8(mapper[INB], getRegister32(Regs.inp));
		//rom.get8(getRegister32(Regs.inp))&0xff;
		incRegister(Regs.inp);
		//System.out.println("- fetch 8bits : 0x"+Integer.toHexString(byt));
		return byt;
		//byt;
	}
	public int fetch16(){
		int wrd = getMemoryBus16(mapper[INB], getRegister32(Regs.inp));
		//rom.get16(getRegister32(Regs.inp))&0xffff;
		addRegister(Regs.inp, 2);
		//System.out.println("- fetch 16bits: 0x"+Integer.toHexString(wrd));
		return wrd;
	}
	public int fetch32(){
		int dwr = getMemoryBus32(mapper[INB], getRegister32(Regs.inp));
		//rom.get32(getRegister32(Regs.inp));
		addRegister(Regs.inp, 4);
		//System.out.println("- fetch 32bits: 0x"+Integer.toHexString(dwr));
		return dwr;
	}
	public int fetchRegister8(){
		return getRegister8(fetch8());
	}
	public int fetchRegister16(){
		return getRegister16(fetch8());
	}
	public int fetchRegister32(){
		return getRegister32(fetch8());
	}
	public int fetchMemory8(){
		return getMemoryBus8(getBankMemory(), fetch32());
		//ram.get8(fetch32(), 0);
	}
	public int fetchMemory16(){
		return getMemoryBus16(getBankMemory(), fetch32());
		//ram.get16(fetch32(), 0);
	}
	public int fetchMemory32(){
		return getMemoryBus32(getBankMemory(), fetch32());
		//ram.get32(fetch32(), 0);
	}
	public int fetchPointer8(){
		return getMemoryBus8(getBankMemory(), fetchRegister32());
		//ram.get8(fetchRegister32(), 0);
	}
	public int fetchPointer16(){
		return getMemoryBus16(getBankMemory(), fetchRegister32());
		//ram.get16(fetchRegister32(), 0);
	}
	public int fetchPointer32(){
		return getMemoryBus32(getBankMemory(), fetchRegister32());
		//ram.get32(fetchRegister32(), 0);
	}
	
	public int fetchExpression(){
		int res = 0;
		int opr;
		while(true){
			opr = fetch8();
			if(opr==ArithBank.end_exp)
				break;
			else{
				int next = opr>=8?fetchRegister16():fetch16();
				opr &= 0b111;
				res = 
					ArithBank.plus==opr?res+next:
					ArithBank.mnus==opr?res-next:
					ArithBank.mult==opr?res*next:
					ArithBank.and==opr?res&next:
					ArithBank.or==opr?res|next:
					res^next;
			}
		}
		return res;
	}
	
	//***********REGISTROS E MANIPULAÇÃO******
	//Setter
	public void setRegister(int r, int v){
		regs[r] = v;
	}
	//Getters
	public int getRegister32(int r){
		return regs[r];
	}
	public int getRegister16(int r){
		return regs[r]&0xffff;
	}
	public int getRegister8(int r){
		return regs[r]&0xff;
	}
	
	//Operadores aritméticos
	public void incRegister(int r){
		setRegister(r, getRegister32(r)+1);
	}
	public void decRegister(int r){
		setRegister(r, getRegister32(r)-1);
	}
	public void addRegister(int r, int value){
		setRegister(r, getRegister32(r)+value);
	}
	public void subRegister(int r, int value){
		setRegister(r, getRegister32(r)-value);
	}
	public void mulRegister(int r, int value){
		setRegister(r, getRegister32(r)*value);
	}
	public void divRegister(int r, int value){
		setRegister(r, getRegister32(r)/value);
	}
	public void modRegister(int r, int value){
		setRegister(r, getRegister32(r)%value);
	}
	//Operadores lógicos
	public void andRegister(int r, int value){
		setRegister(r, getRegister32(r)&value);
	}
	public void orRegister(int r, int value){
		setRegister(r, getRegister32(r)|value);
	}
	public void xorRegister(int r, int value){
		setRegister(r, getRegister32(r)^value);
	}
	public void lsfRegister(int r, int value){
		setRegister(r, getRegister32(r)<<value);
	}
	public void rsfRegister(int r, int value){
		setRegister(r, getRegister32(r)>>value);
	}
	public void notRegister(int r){
		setRegister(r, ~getRegister32(r));
	}
	
	//*********ACESSO A MEMÓRIA VIA BUS*********

	public void setMemoryBus8(int bnk, int p, int lit){
		bus[bnk%bus.length].set8(lit, p);
	}
	public void setMemoryBus16(int bnk, int p, int lit){
		bus[bnk%bus.length].set16(lit, p);
		//bus[0].set8(50, 0);
		//bus[0].buffer.put(1,(byte)0x50);
	}
	public void setMemoryBus32(int bnk, int p, int lit){
		bus[bnk%bus.length].set16(lit, p);
	}

	public int getMemoryBus8(int bnk, int p){
		return bus[bnk%bus.length].get8(p);
	}
	public int getMemoryBus16(int bnk, int p){
		return bus[bnk%bus.length].get16(p);
	}
	public int getMemoryBus32(int bnk, int p){
		return bus[bnk%bus.length].get16(p)+(bus[bnk%bus.length].get16(p+2)<<16);
	}
	
	//Define um aguardamento
	public void setWait(int w) {
		waits[w%1024] = true;
	}
	//Recebe conclusão de aguardamento
	public boolean getWait(int w) {
		boolean wait = waits[w%1024];
		waits[w%1024] = false;
		return wait;
	}
	
	public int getBankMemory(){
		return bnks;//getRegister32(Regs.bkp);
	}
	
	//Estocamento de qualquer valor
	public void pushStack(int lit){
		setMemoryBus16(mapper[STK], getRegister32(Regs.stp), lit);
		subRegister(Regs.stp, 2);
	}
	public int popStack(){
		addRegister(Regs.stp, 2);
		return getMemoryBus16(mapper[STK], getRegister32(Regs.stp));
	}
	
	//Estocamento de estado de máquina
	private void push(int lit){
		setMemoryBus32(mapper[FRM], fmp, lit);
		fmp -= 4;
	}
	private int pop(){
		fmp += 4;
		return getMemoryBus32(mapper[FRM], fmp);
	}
	public void pushState(){
		for(int i=0; i<Regs.length; i++)
			push(regs[i]);
		push(cmp1);
		push(cmp2);
		push(bnks);
		push(bnkd);
	}
	public void popState(){
		bnkd = pop();
		bnks = pop();
		cmp2 = pop();
		cmp1 = pop();
		for(int i=Regs.length-1; i>-1; i--)
			//regs.put(i, pop());
			regs[i] = pop();
	}
	
	//Operações internas
	public void jump(int address) {
		setRegister(Regs.inp, address);
	}
	
	//Executa uma instrução
	//Execuções retornam esperas, se não houver
	//retornará -1.
	private int executeOpr(int opr){
		//System.out.println("*******Instruction: 0x"+Integer.toHexString(opr)+"*******");
		//if(getRegister32(Regs.inp)>=bus[mapper[3]].length)
		//	return -1;
		//System.out.println("Operation: "+opr);
		instruction: switch(opr){
			
				//****INSTRUÇÕES DE REGISTROS
			
			case Ints.mov_lit_reg:{
					int val = fetch16();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov_reg_reg:{
					int val = fetchRegister16();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov_reg_mem:{
					int val = fetchRegister16();
					int mem = fetch32();
					setMemoryBus16(getBankMemory(), mem, val);
					//ram.set16(val, mem, getBankMemory());
				}
				break;
			case Ints.mov_mem_reg:{
					int val = fetchMemory16();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov_lit_mem:{
					int val = fetch16();
					int mem = fetch32();
					setMemoryBus16(getBankMemory(), mem, val);
					//ram.set16(val, mem, getBankMemory());
				}
				break;
			case Ints.mov_reg_ptr:{
					int val = fetchRegister16();
					int ptr = fetchRegister32();
					setMemoryBus16(getBankMemory(), ptr, val);
					//ram.set16(val, ptr, getBankMemory());
				}
				break;
			case Ints.mov_ptr_reg:{
					int val = fetchPointer16();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov_lit_ptr:{
					int val = fetch16();
					int ptr = fetchRegister32();
					setMemoryBus16(getBankMemory(), ptr, val);
					//ram.set16(val, ptr, getBankMemory());
				}
				break;
			case Ints.mov_exp_reg:{
					int val = fetchExpression();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov_exp_mem:{
					int val = fetchExpression();
					int mem = fetch32();
					//System.out.println("na mem "+mem+" coloca val "+val);
					setMemoryBus16(getBankMemory(), mem, val);
					//ram.set16(val, mem, getBankMemory());
				}
				break;
			case Ints.mov_exp_ptr:{
					int val = fetchExpression();
					int ptr = fetchRegister32();
					setMemoryBus16(getBankMemory(), ptr, val);
					//ram.set16(val, ptr, getBankMemory());
				}
				break;
			case Ints.mov8_mem_reg:{
					int val = fetchMemory8();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov8_ptr_reg:{
					int val = fetchPointer8();
					int reg = fetch8();
					setRegister(reg, val);
				}
				break;
			case Ints.mov8_reg_mem:{
					int val = fetchRegister8();
					int mem = fetch32();
					setMemoryBus8(getBankMemory(), mem, val);
					//ram.set8(val, mem, getBankMemory());
				}
				break;
			case Ints.mov8_reg_ptr:{
					int val = fetchRegister8();
					int ptr = fetchRegister32();
					setMemoryBus8(getBankMemory(), ptr, val);
					//ram.set8(val, ptr, getBankMemory());
				}
				break;
			case Ints.mov8_lit_reg:{
					int val = fetch16();
					int reg = fetch8();
					setRegister(reg, val&0xff);
				}
				break;
			case Ints.mov8_lit_mem:{
					int val = fetch16();
					int mem = fetch32();
					setMemoryBus8(getBankMemory(), mem, val);
					//ram.set16(val, mem, getBankMemory());
				}
				break;
			case Ints.mov8_lit_ptr:{
					int val = fetch16();
					int ptr = fetchRegister32();
					setMemoryBus8(getBankMemory(), ptr, val);
					//ram.set16(val, ptr, getBankMemory());
				}
				break;
				
				//****INSTRUÇÕES DE ESTOCAMENTO
			
			case Ints.psh_lit:{
					int val = fetch16();
					pushStack(val);
				}
				break;
			case Ints.psh_reg:{
					int val = fetchRegister16();
					pushStack(val);
				}
				break;
			case Ints.psh_mem:{
					int val = fetchMemory16();
					pushStack(val);
				}
				break;
			case Ints.psh_ptr:{
					int val = fetchPointer16();
					pushStack(val);
				}
				break;
			case Ints.pop_reg:{
					int reg = fetch8();
					setRegister(reg, popStack());
				}
				break;
			
				//****INSTRUÇÕES ARITMÉTICAS
			
			case Ints.add_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					addRegister(reg, lit);
				}
				break;
			case Ints.add_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					addRegister(reg, lit);
				}
				break;
			case Ints.sub_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					subRegister(reg, lit);
				}
				break;
			case Ints.sub_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					subRegister(reg, lit);
				}
				break;
			case Ints.mul_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					mulRegister(reg, lit);
				}
				break;
			case Ints.mul_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					mulRegister(reg, lit);
				}
				break;
			case Ints.div_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					divRegister(reg, lit);
				}
				break;
			case Ints.div_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					divRegister(reg, lit);
				}
				break;
			case Ints.odiv_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					modRegister(reg, lit);
				}
				break;
			case Ints.odiv_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					modRegister(reg, lit);
				}
				break;
			case Ints.inc_reg:{
					int reg = fetch8();
					incRegister(reg);
				}
				break;
			case Ints.dec_reg:{
					int reg = fetch8();
					decRegister(reg);
				}
				break;

				//****INSTRUÇÕES A BIT
				
			case Ints.lsf_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					lsfRegister(reg, lit);
				}
				break;
			case Ints.lsf_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					lsfRegister(reg, lit);
				}
				break;
			case Ints.rsf_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					rsfRegister(reg, lit);
				}
				break;
			case Ints.rsf_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					rsfRegister(reg, lit);
				}
				break;
			case Ints.and_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					andRegister(reg, lit);
				}
				break;
			case Ints.and_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					andRegister(reg, lit);
				}
				break;
			case Ints.or_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					orRegister(reg, lit);
				}
				break;
			case Ints.or_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					orRegister(reg, lit);
				}
				break;
			case Ints.xor_lit_reg:{
					int lit = fetch16();
					int reg = fetch8();
					xorRegister(reg, lit);
				}
				break;
			case Ints.xor_reg_reg:{
					int lit = fetchRegister16();
					int reg = fetch8();
					xorRegister(reg, lit);
				}
				break;
			case Ints.not_reg:{
					int reg = fetch8();
					notRegister(reg);
				}
				break;
			
				//****INSTRUÇÕES DE PULO E CONDICIONAMENTOS
				
			case Ints.jmp_mem:{
					int lit = fetch32();
					jump(lit);
				}
				break;
			case Ints.jmp_ptr:{
					int lit = fetchPointer32();
					jump(lit);
				}
				break;
			case Ints.cmp_lit_reg:{
					int lit = fetch16();
					int reg = fetchRegister16();
					cmp1 = lit; cmp2 = reg;
				}
				break;
			case Ints.cmp_reg_reg:{
					int reg1 = fetchRegister16();
					int reg2 = fetchRegister16();
					cmp1 = reg1; cmp2 = reg2;
				}
				break;
			case Ints.jet_mem:{
					int mem = fetch32();
					if(cmp1==cmp2)
						jump(mem);
				}
				break;
			case Ints.jet_ptr:{
					int mem = fetchPointer32();
					if(cmp1==cmp2)
						jump(mem);
				}
				break;
			case Ints.jne_mem:{
					int mem = fetch32();
					if(cmp1!=cmp2)
						jump(mem);
				}
				break;
			case Ints.jne_ptr:{
					int mem = fetchPointer32();
					if(cmp1!=cmp2)
						jump(mem);
				}
				break;
			case Ints.jgt_mem:{
					int mem = fetch32();
					if(cmp1>cmp2)
						jump(mem);
				}
				break;
			case Ints.jgt_ptr:{
					int mem = fetchPointer32();
					if(cmp1>cmp2)
						jump(mem);
				}
				break;
			case Ints.jlt_mem:{
					int mem = fetch32();
					if(cmp1<cmp2)
						jump(mem);
				}
				break;
			case Ints.jlt_ptr:{
					int mem = fetchPointer32();
					if(cmp1<cmp2)
						jump(mem);
				}
				break;
			case Ints.jge_mem:{
					int mem = fetch32();
					if(cmp1>=cmp2)
						jump(mem);
				}
				break;
			case Ints.jge_ptr:{
					int mem = fetchPointer32();
					if(cmp1>=cmp2)
						jump(mem);
				}
				break;
			case Ints.jle_mem:{
					int mem = fetch32();
					if(cmp1<=cmp2)
						jump(mem);
				}
				break;
			case Ints.jle_ptr:{
					int mem = fetchPointer32();
					if(cmp1<=cmp2)
						jump(mem);
				}
				break;

				//****INSTRUÇÕES E CHAMADAS DE SUB-ROTINAS
			
			case Ints.call_mem:{
					int lit = fetch32();
					pushState();
					jump(lit);
				}
				break;
			case Ints.call_ptr:{
					int lit = fetchPointer32();
					pushState();
					jump(lit);
				}
				break;
			case Ints.ret:{
					popState();
				}
				break;

				//****INSTRUÇÕES DE MANIPULAÇÃO DE MULTI-DADOS
			
			case Ints.dl_mem_lits:{
					int mem = fetch32();
					int length = fetch16()*2;
					setRegister(Regs.dls, length);
					for(int i=0; i<length; i+=2)
						setMemoryBus16(getBankMemory(), mem+i, fetch16());
						//ram.set16(fetch16(), mem+i);
				}
				break;
			case Ints.dl_ptr_lits:{
					int mem = fetchPointer32();
					int length = fetch16()*2;
					setRegister(Regs.dls, length);
					for(int i=0; i<length; i+=2)
						setMemoryBus16(getBankMemory(), mem+i, fetch16());
						//ram.set16(fetch16(), mem+i);
				}
				break;
			case Ints.dl8_mem_lits:{
					int mem = fetch32();
					int length = fetch16();
					setRegister(Regs.dls, length);
					for(int i=0; i<length; i++)
						setMemoryBus8(getBankMemory(), mem+i, fetch8());
						//ram.set8(fetch8(), mem+i);
				}
				break;
			case Ints.dl8_ptr_lits:{
					int mem = fetchPointer32();
					int length = fetch16();
					setRegister(Regs.dls, length);
					for(int i=0; i<length; i++)
						setMemoryBus8(getBankMemory(), mem+i, fetch8());
						//ram.set8(fetch8(), mem+i);
				}
				break;
			case Ints.cdl_mem_reg_mem:{
					int mems = fetch32();
					int len = fetchRegister16();// fetch16();
					int memd = fetch32();
					for(int i=0; i<len; i++){
						setMemoryBus8(
							bnkd,//getBankMemory(),
							memd+i,
							getMemoryBus8(
								bnks,
								mems+i)
							);
					}
				}
				break;
			case Ints.cdl_ptr_reg_ptr:{
					int mems = fetchRegister32();
					int len = fetchRegister16();// fetch16();
					int memd = fetchRegister32();
					for(int i=0; i<len; i++){
						System.out.println("****copying"+getMemoryBus8(
											   bnks,
											   mems+i));
						setMemoryBus8(
							bnkd,//getBankMemory(),
							memd+i,
							getMemoryBus8(
								bnks,
								mems+i)
						);
					}
				}
				break;

				//****INSTRUÇÕES ESPECIAIS
			
			case Ints.int_lit:{
					int lit = fetch16();
					for(Device dev:devices){
						boolean reach = dev.execute(this, lit);
						if(reach)
							break instruction;
					}
				}
				break;
			
			case Ints.bnks_lit:{
					bnks = fetch16();
				}
				break;
			
			case Ints.bnks_reg:{
					bnks = fetchRegister16();
				}
				break;
			
			case Ints.bnks_exp:{
					bnks = fetchExpression();
				}
				break;
			case Ints.bnkd_lit:{
					bnkd = fetch16();
				}
				break;

			case Ints.bnkd_reg:{
					bnkd = fetchRegister16();
				}
				break;

			case Ints.bnkd_exp:{
					bnkd = fetchExpression();
				}
				break;
			
			case Ints.nop:{
					
				}
				break;
			
			case Ints.wai_lit:{
					int lit = fetch16();
					if(!getWait(lit))
						subRegister(Regs.inp, 3);
					return lit;
				}
			case Ints.hls:{
					
				}
				break;
			
			default:{
					
				}
		}
		return -1;
	}
	//Continua executando
	public int stepCpu() {
		int opr = fetch8();
		int sucess = executeOpr(opr);
		return sucess;
	}
	public void branchStepCpu(int addressB, int waitEnd) {
		pushState();
		jump(addressB);
		while(stepCpu()!=waitEnd) {}
		popState();
	}
	@Override
	public int step(){
		//Incinde o ponto de ativação 0
		//Este ocorre a cada tick de tela
		setWait(0);
		for(int i=0; i<17000; i++){
			stepCpu();
		}
		return 0;
	}
	
}

