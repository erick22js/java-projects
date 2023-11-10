package com.Disa64;

import java.io.RandomAccessFile;

import com.test.Prettify;

public class Opcode64 {
	
	/*
	 * Section for Opcode Object
	 */
	public OpcDT templateInd;
	public int value = 0;
	
	public Opcode64(OpcDT dt, int v) {
		templateInd = dt;
		value = v;
	}
	
	@Override
	public String toString() {
		String txt = templateInd.opcode;
		boolean firstArg = true;
		for(int i=0; i<templateInd.mask.length; i++) {
			if(templateInd.mask[i]==-1) {
				int arg = getBitData(value, opcGDataBase[templateInd.group].map[i][0], opcGDataBase[templateInd.group].map[i][1]);
				txt += (!firstArg?", ":" ");
				txt += "0x"+Integer.toHexString(arg);
				//txt += "("+i+")";
				firstArg = false;
			}
		}
		/*String str = Integer.toBinaryString(value);
		for(int i=str.length(); i<32; i++)
			str = "0"+str;*/
		return /*"["+str+"] => "+*/"["+Prettify.prettyStrNumber(Integer.toBinaryString(value), 32)+"] "+
				(
				templateInd.group==COPz_TYPE? txt.replace("_", "Pz"):
				templateInd.hasOpcBEncoded==-1? txt:
				templateInd.hasOpcBEncoded==((value>>>26)&3)? txt.replace("_", "C"+((value>>>26)&3)):
				-templateInd.hasOpcBEncoded==((value>>>26)&3)? txt.replace("_", ""):
				templateInd.hasOpcBEncoded<=4? txt.replace("_", "Cz"):
				txt.replace("_", ""));
	}
	
	/*
	 * Section for static Opcode class
	 */
	

	public static int getBitData(int src, int offset, int size/*must be between 0 and 32*/) {
		int dest = src>>>offset;/*
		System.out.println(
				"["+
					"\nsrc:    "+Prettify.prettyStrNumber(Integer.toBinaryString(src), 32)+
					"\ndested: "+Prettify.prettyStrNumber(Integer.toBinaryString(dest), 32)+"   shift-right:"+offset+
					"\nmask:   "+Prettify.prettyStrNumber(Integer.toBinaryString(masks[size]), 32)+"   size-mask:"+size+
					"\nmasked: "+Prettify.prettyStrNumber(Integer.toBinaryString(dest&masks[size]), 32)+
				"]"
				);*/
		return dest&masks[size];
	}
	
	public static Opcode64 fetchOpcode(int code, boolean littleEndian){
		init();
		int fetch = code;//Opcode64Disasm.fetchInt(ac, littleEndian);
		//Opcode64Disasm.lastFetch = fetch;
		int opcType = fetch>>26;
		opcType &= 0b111111;
		/*String str = Integer.toBinaryString(fetch);
		for(int i=str.length(); i<32; i++)
			str = "0"+str;
		System.out.println("Data: "+str);*/
		//System.out.println("Code: "+Integer.toBinaryString(opcType));
		OpcDT dt;
		OpcGDT gdt;
		int[] sec;
		int arg;
		int mask = 0;
		boolean matchArgs = true;
		int optspb = ((opcType>>>2)&0b1111);
		for(int o=0; o<opcDataBase.length; o++) {
			dt = opcDataBase[o];
			if(dt.optspb==-1) {
				if(dt.opcType==opcType) {
					matchArgs = checkOpcodeArgs(fetch, dt);
					if(matchArgs)
						return new Opcode64(dt, fetch);
				}
			}else {
				if(optspb==dt.optspb) {
					matchArgs = checkOpcodeArgs(fetch, dt);
					if(matchArgs)
						return new Opcode64(dt, fetch);
				}
			}
			/*int dopspb = ((dt.opcType>>>2)&0b1111);
			if(dopspb==optspb&&(dt.opcType&0b11)==0b11) {//optspb==0b0100&&dt.opcType==0b010011) {
				matchArgs = checkOpcodeArgs(fetch, dt);
				if(matchArgs)
					return new Opcode64(dt, fetch);
			}*/
		}
		return null;
	}
	
	private static boolean checkOpcodeArgs(int fetch, OpcDT dt) {
		OpcGDT gdt = opcGDataBase[dt.group];
		//System.out.println(dt.opcode);
		int[] sec;
		int arg;
		boolean matchArgs = true;
		for(int c=0; c<dt.mask.length; c++) {
			if(dt.mask[c]>=0){
				sec = gdt.map[c];
				arg = getBitData(fetch, sec[0], sec[1]);/*
						fetch>>sec[0];
				mask = 0;
				for(int i=0; i<sec[1]; i++)
					mask |= 1<<i;
				arg &= mask;*/
				matchArgs &= arg==dt.mask[c];
				//System.out.println("Arg("+c+")["+sec[1]+"]-> "+Integer.toBinaryString(arg)+" compare to "+Integer.toBinaryString(dt.mask[c]));
			}
		}
		return matchArgs;
	}
	
	public static OpcGDT[] opcGDataBase;
	public static OpcDT[] opcDataBase;
	public static String[] COP0Regs;
	public static String[] MAINRegs;
	public static int[] masks;
	private static boolean opcDatabaseInit = false;
	private static final int R_TYPE = 0;
	private static final int I_TYPE = 1;
	private static final int J_TYPE = 2;
	private static final int SPECIAL_TYPE = 3;
	private static final int TEQ_TYPE = 4;
	private static final int ERET_TYPE = 5;
	private static final int BC1F_TYPE = 6;
	private static final int C_cond_fmt_TYPE = 7;
	private static final int COPz_TYPE = 8;
	
	public static void init() {
		if(opcDatabaseInit)
			return;
		opcDatabaseInit = true;
		{
			masks = new int[32];
			for(int m=0; m<32; m++) {
				int mask = 0;
				for(int mb=0; mb<m; mb++)
					mask |= 1<<mb;
				masks[m] = mask;
			}
		}
		COP0Regs = new String[] {
			"Index", "Random", "EntryLo0", "EntryLo1", "Context", "PageMask", "Wired", null,
			"BadVAddr", "Count", "EntryHi", "Compare", "Status", "Cause", "EPC", "PRevID",
			"Config", "LLAddr", "WatchLo", "WatchHi", "XContext", null, null, null,
			null, null, "PErr", "CacheErr", "TagLo", "TagHi", "ErrorEPC", null,
		};
		MAINRegs = new String[] {
			"r0", "at", "v0", "v1", "a0", "a1", "a2", "a3",
			"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
			"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
			"t8", "t9", "k0", "k1", "gp", "sp", "s8", "ra",
		};
		opcGDataBase = new OpcGDT[] {
			//0 => R-Type: 21[5], 16[5], 11[5], 6[5], 0[6]
			new OpcGDT(new int[] {21, 5},new int[] {16, 5},new int[] {11, 5},new int[] {6, 5},new int[] {0, 6}),
			//1 => I-Type: 21[5], 16[5], 0[15]
			new OpcGDT(new int[] {21, 5},new int[] {16, 5},new int[] {0, 16}),
			//2 => J-Type: 0[25]
			new OpcGDT(new int[] {0, 25}),
			//3 => SPECIAL-Type: 6[20], 0[6]
			new OpcGDT(new int[] {6, 20},new int[] {0, 6}),
			//4 => TEQ-Type: 21[5], 16[5], 6[10], 0[6]
			new OpcGDT(new int[] {21, 5},new int[] {16, 5},new int[] {6, 10},new int[] {0, 6}),
			//5 => ERET-Type: 25[1], 19[5], 0[6]
			new OpcGDT(new int[] {25, 1}, new int[] {19, 5},new int[] {0, 6}),
			//6 => BC1F-Type: 21[5], 18[3], 17[1], 16[1], 0[16]
			new OpcGDT(new int[] {21, 5}, new int[] {18, 3}, new int[] {17, 1}, new int[] {16, 1},new int[] {0, 16}),
			//7 => C_cond_fmt_Type: 21[5], 16[5], 11[5], 8[3], 6[2], 4[2], 0[4]
			new OpcGDT(new int[] {21, 5}, new int[] {16, 5}, new int[] {11, 5}, new int[] {8, 3}, new int[] {6, 2}, new int[] {4, 2},new int[] {0, 4}),
			//5 => COPz-Type: 25[1], 0[25]
			new OpcGDT(new int[] {25, 1}, new int[] {0, 25}),
		};
		opcDataBase = new OpcDT[] {
			new OpcDT("NOP"   , -1, 0, I_TYPE, 0, 0, 0),
			new OpcDT("LB"    , -1, 0b100000, I_TYPE, -1, -1, -1),
			new OpcDT("LBU"   , -1, 0b100100, I_TYPE, -1, -1, -1),
			//new OpcDT("LD"    , -1, 0b110111, I_TYPE, -1, -1, -1),
			/**/new OpcDT("LD_"  , -3, 0b110111, I_TYPE, -1, -1, -1),
			new OpcDT("LDL"   , -1, 0b011010, I_TYPE, -1, -1, -1),
			new OpcDT("LDR"   , -1, 0b011011, I_TYPE, -1, -1, -1),
			new OpcDT("LH"    , -1, 0b100001, I_TYPE, -1, -1, -1),
			new OpcDT("LHU"   , -1, 0b100101, I_TYPE, -1, -1, -1),
			new OpcDT("LL"    , -1, 0b110000, I_TYPE, -1, -1, -1),
			new OpcDT("LLD"   , -1, 0b110100, I_TYPE, -1, -1, -1),
			new OpcDT("LW"    , -1, 0b100011, I_TYPE, -1, -1, -1),
			new OpcDT("LWL"   , -1, 0b100010, I_TYPE, -1, -1, -1),
			new OpcDT("LWR"   , -1, 0b100110, I_TYPE, -1, -1, -1),
			new OpcDT("LWU"   , -1, 0b100111, I_TYPE, -1, -1, -1),
			new OpcDT("SB"    , -1, 0b101000, I_TYPE, -1, -1, -1),
			new OpcDT("SC"    , -1, 0b111000, I_TYPE, -1, -1, -1),
			new OpcDT("SCD"   , -1, 0b111100, I_TYPE, -1, -1, -1),
			new OpcDT("SD"    , -1, 0b111111, I_TYPE, -1, -1, -1),
			new OpcDT("SDL"   , -1, 0b101100, I_TYPE, -1, -1, -1),
			new OpcDT("SDR"   , -1, 0b101101, I_TYPE, -1, -1, -1),
			new OpcDT("SH"    , -1, 0b101001, I_TYPE, -1, -1, -1),
			new OpcDT("SW"    , -1, 0b101011, I_TYPE, -1, -1, -1),
			new OpcDT("SWL"   , -1, 0b101010, I_TYPE, -1, -1, -1),
			new OpcDT("SWR"   , -1, 0b101110, I_TYPE, -1, -1, -1),
			new OpcDT("SYNC"  , -1, 0b000000, R_TYPE, 0, 0, 0, -1, -1),
			
			new OpcDT("ADD"    , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100000),
			new OpcDT("ADDI"   , -1, 0b001000, I_TYPE, -1, -1, -1),
			new OpcDT("ADDIU"  , -1, 0b001001, I_TYPE, -1, -1, -1),
			new OpcDT("ADDU"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100001),
			new OpcDT("AND"    , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100100),
			new OpcDT("ANDI"   , -1, 0b001100, I_TYPE, -1, -1, -1),
			new OpcDT("DADD"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b101100),
			new OpcDT("DADDI"  , -1, 0b011000, I_TYPE, -1, -1, -1),
			new OpcDT("DADDIU" , -1, 0b011001, I_TYPE, -1, -1, -1),
			new OpcDT("DADDU"  , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b101101),
			new OpcDT("DDIV"   , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011110),
			new OpcDT("DDIVU"  , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011111),
			new OpcDT("DIV"    , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011010),
			new OpcDT("DIVU"   , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011011),
			new OpcDT("DMULT"  , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011100),
			new OpcDT("DMULTU" , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011101),
			new OpcDT("DSLL"   , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b111000),
			new OpcDT("DSLL32" , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b111100),
			new OpcDT("DSLLV"  , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b111100),
			new OpcDT("DSRA"   , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b111011),
			new OpcDT("DSRA32" , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b111111),
			new OpcDT("DSRAV"  , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b010111),
			new OpcDT("DSRL"   , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b111010),
			new OpcDT("DSRL32" , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b111110),
			new OpcDT("DSRLV"  , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b010110),
			new OpcDT("DSUB"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b101110),
			new OpcDT("DSUBU"  , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b101111),
			new OpcDT("LUI"    , -1, 0b001111, I_TYPE, 0, -1, -1),
			new OpcDT("MFHI"   , -1, 0b000000, R_TYPE, 0, 0, -1, 0, 0b010000),
			new OpcDT("MFLO"   , -1, 0b000000, R_TYPE, 0, 0, -1, 0, 0b010010),
			new OpcDT("MTHI"   , -1, 0b000000, R_TYPE, -1, 0, 0, 0, 0b010001),
			new OpcDT("MTLO"   , -1, 0b000000, R_TYPE, -1, 0, 0, 0, 0b010011),
			new OpcDT("MULT"   , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011000),
			new OpcDT("MULTU"  , -1, 0b000000, R_TYPE, -1, -1, 0, 0, 0b011001),
			new OpcDT("NOR"    , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100111),
			new OpcDT("OR"     , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100101),
			new OpcDT("ORI"    , -1, 0b001101, I_TYPE, -1, -1, -1),
			new OpcDT("SLL"    , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0),
			new OpcDT("SLLV"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b000100),
			new OpcDT("SLT"    , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b101010),
			new OpcDT("SLTI"   , -1, 0b001010, I_TYPE, -1, -1, -1),
			new OpcDT("SLTIU"  , -1, 0b001011, I_TYPE, -1, -1, -1),
			new OpcDT("SLTU"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b101011),
			new OpcDT("SRA"    , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b000011),
			new OpcDT("SRAV"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b000111),
			new OpcDT("SRL"    , -1, 0b000000, R_TYPE, 0, -1, -1, -1, 0b000010),
			new OpcDT("SRLV"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b000110),
			new OpcDT("SUB"    , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100010),
			new OpcDT("SUBU"   , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100011),
			new OpcDT("XOR"    , -1, 0b000000, R_TYPE, -1, -1, -1, 0, 0b100110),
			new OpcDT("XORI"   , -1, 0b001110, I_TYPE, -1, -1, -1),
			
			/**/new OpcDT("B_F"   , -2, 0b010011, I_TYPE, 0b01000, 0b00000, -1),
			/**/new OpcDT("B_FL"  , -2, 0b010011, I_TYPE, 0b01000, 0b00010, -1),
			/**/new OpcDT("B_T"   , -2, 0b010011, I_TYPE, 0b01000, 0b00001, -1),
			/**/new OpcDT("B_TL"  , -2, 0b010011, I_TYPE, 0b01000, 0b00011, -1),
			new OpcDT("BEQ"    , -1, 0b000100, I_TYPE, -1, -1, -1),
			new OpcDT("BEQL"   , -1, 0b010100, I_TYPE, -1, -1, -1),
			new OpcDT("BGEZ"   , -1, 0b000001, I_TYPE, -1, 0b00001, -1),
			new OpcDT("BGEZAL" , -1, 0b000001, I_TYPE, -1, 0b10001, -1),
			new OpcDT("BGEZALL", -1, 0b000001, I_TYPE, -1, 0b10001, -1),
			new OpcDT("BGEZL"  , -1, 0b000001, I_TYPE, -1, 0b00011, -1),
			new OpcDT("BGTZ"   , -1, 0b000111, I_TYPE, -1, 0, -1),
			new OpcDT("BGTZL"  , -1, 0b010111, I_TYPE, -1, 0, -1),
			new OpcDT("BLEZ"   , -1, 0b000110, I_TYPE, -1, 0, -1),
			new OpcDT("BLEZL"  , -1, 0b010110, I_TYPE, -1, 0, -1),
			new OpcDT("BLTZ"   , -1, 0b000001, I_TYPE, -1, 0, -1),
			new OpcDT("BLTZAL" , -1, 0b000001, I_TYPE, -1, 0b10000, -1),
			new OpcDT("BLTZALL", -1, 0b000001, I_TYPE, -1, 0b10010, -1),
			new OpcDT("BLTZL"  , -1, 0b000001, I_TYPE, -1, 0b00010, -1),
			new OpcDT("BNEZ"   , -1, 0b000101, I_TYPE, -1, 0, -1),
			new OpcDT("BNE"    , -1, 0b000101, I_TYPE, -1, -1, -1),
			new OpcDT("BNEL"   , -1, 0b010101, I_TYPE, -1, -1, -1),
			new OpcDT("J"      , -1, 0b000010, J_TYPE, -1),
			new OpcDT("JAL"    , -1, 0b000011, J_TYPE, -1),
			new OpcDT("JALR"   , -1, 0b000000, R_TYPE, -1, 0, -1, 0, 0b001001),
			new OpcDT("JR"     , -1, 0b000000, R_TYPE, -1, 0, 0, 0, 0b001000),
			
			new OpcDT("BREAK"  , -1, 0b000000, SPECIAL_TYPE, -1, 0b001101),
			new OpcDT("SYSCALL", -1, 0b000000, SPECIAL_TYPE, -1, 0b001100),
			
			new OpcDT("TEQ"    , -1, 0b000000, TEQ_TYPE, -1, -1, -1, 0b110100),
			new OpcDT("TEQI"   , -1, 0b000001, I_TYPE, -1, 0b01100, -1),
			new OpcDT("TGE"    , -1, 0b000000, TEQ_TYPE, -1, -1, -1, 0b110000),
			new OpcDT("TGEI"   , -1, 0b000001, I_TYPE, -1, 0b01000, -1),
			new OpcDT("TGEIU"  , -1, 0b000001, I_TYPE, -1, 0b01001, -1),
			new OpcDT("TGEU"   , -1, 0b000000, TEQ_TYPE, -1, -1, -1, 0b110001),
			new OpcDT("TLT"    , -1, 0b000000, TEQ_TYPE, -1, -1, -1, 0b110010),
			new OpcDT("TLTI"   , -1, 0b000001, I_TYPE, -1, 0b01010, -1),
			new OpcDT("TLTIU"  , -1, 0b000001, I_TYPE, -1, 0b01011, -1),
			new OpcDT("TLTU"   , -1, 0b000000, TEQ_TYPE, -1, -1, -1, 0b110011),
			new OpcDT("TNE"    , -1, 0b000000, TEQ_TYPE, -1, -1, -1, 0b110110),
			new OpcDT("TNEI"   , -1, 0b000001, I_TYPE, -1, 0b01110, -1),
			
			new OpcDT("CACHE"  , -1, 0b101111, I_TYPE, -1, 0b01110, -1),
			new OpcDT("DMFC0"  , -1, 0b010000, R_TYPE, 0b00001, -1, -1, 0, 0),
			new OpcDT("DMTC0"  , -1, 0b010000, R_TYPE, 0b00101, -1, -1, 0, 0),
			new OpcDT("ERET"   , -1, 0b010000, ERET_TYPE, -1, 0, 0b011000),
			new OpcDT("MFC0"   , -1, 0b010000, R_TYPE, 0, -1, -1, 0, 0),
			new OpcDT("MTC0"   , -1, 0b010000, R_TYPE, 0b00100, -1, -1, 0, 0),
			new OpcDT("TLBP"   , -1, 0b010000, ERET_TYPE, -1, 0, 0b001000),
			new OpcDT("TLBR"   , -1, 0b010000, ERET_TYPE, -1, 0, 0b000001),
			new OpcDT("TLBWI"  , -1, 0b010000, ERET_TYPE, -1, 0, 0b000010),
			new OpcDT("TLBWR"  , -1, 0b010000, ERET_TYPE, -1, 0, 0b000110),
			
			new OpcDT("ABS.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b000101),
			new OpcDT("ADD.fmt", -1, 0b010001, R_TYPE, -1, -1, -1, -1, 0b000000),
			new OpcDT("BC1F"   , -1, 0b010001, BC1F_TYPE, 0b01000, -1, 0, 0, -1),
			new OpcDT("BC1FL"  , -1, 0b010001, BC1F_TYPE, 0b01000, -1, 0b1, 0, -1),
			new OpcDT("BC1T"   , -1, 0b010001, BC1F_TYPE, 0b01000, -1, 0, 0b1, -1),
			new OpcDT("BC1TL"  , -1, 0b010001, BC1F_TYPE, 0b01000, -1, 0b1, 0b1, -1),
			new OpcDT("C.cond.fmt", -1, 0b010001, C_cond_fmt_TYPE, -1, -1, -1, -1, 0, 0b11, -1),
			new OpcDT("CEIL.L.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001010),
			new OpcDT("CEIL.W.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001110),
			//new OpcDT("CFC1"      , -1, 0b010001, R_TYPE, 0b00010, -1, -1, 0, 0),
			/**/new OpcDT("CF_"      ,  1, 0b010011, R_TYPE, 0b00010, -1, -1, 0, 0),
			/**/new OpcDT("CO_"      , -2, 0b010011, COPz_TYPE, 1, -1),
			//new OpcDT("CTC1"      , -1, 0b010001, R_TYPE, 0b00110, -1, -1, 0, 0),
			/**/new OpcDT("CT_"      ,  1, 0b010011, R_TYPE, 0b00110, -1, -1, 0, 0),new OpcDT("CVT.D.fmt" , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b100001),
			new OpcDT("CVT.L.fmt" , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b100101),
			new OpcDT("CVT.S.fmt" , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b100000),
			new OpcDT("CVT.W.fmt" , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b100100),
			new OpcDT("DIV.fmt"   , -1, 0b010001, R_TYPE, -1, -1, -1, -1, 0b000011),
			new OpcDT("DMFC1"     , -1, 0b010001, R_TYPE, 0b00101, -1, -1, 0, 0),
			new OpcDT("FLOOR.L.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001011),
			new OpcDT("FLOOR.W.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001111),
			//new OpcDT("LDC1"       , -1, 0b110101, I_TYPE, -1, -1, -1),
			/**/new OpcDT("LD_"       , 1, 0b110101, I_TYPE, -1, -1, -1),
			//new OpcDT("LWC1"       , -1, 0b110001, I_TYPE, -1, -1, -1),
			/**/new OpcDT("LW_"       , 1, 0b110001, I_TYPE, -1, -1, -1),
			//new OpcDT("MFC1"       , -1, 0b010001, R_TYPE, 0, -1, -1, 0, 0),
			/**/new OpcDT("MF_"       , 1, 0b010001, R_TYPE, 0, -1, -1, 0, 0),
			new OpcDT("MOV.fmt"    , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b000110),
			//new OpcDT("MTC1"       , -1, 0b010001, R_TYPE, 0b00100, -1, -1, 0, 0),
			/**/new OpcDT("MT_"       , 0, 0b010001, R_TYPE, 0b00100, -1, -1, 0, 0),
			new OpcDT("MUL.fmt"    , -1, 0b010001, R_TYPE, -1, -1, -1, -1, 0b000010),
			new OpcDT("NEG.fmt"    , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b000111),
			new OpcDT("ROUND.L.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001000),
			new OpcDT("ROUND.W.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001100),
			//new OpcDT("SDC1"       , -1, 0b111101, I_TYPE, -1, -1, -1),
			/**/new OpcDT("SD_"       , 1, 0b111101, I_TYPE, -1, -1, -1),
			new OpcDT("SQRT.fmt"   , -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b000100),
			new OpcDT("SUB.fmt"    , -1, 0b010001, R_TYPE, -1, -1, -1, -1, 0b000001),
			//new OpcDT("SWC1"       , -1, 0b111001, I_TYPE, -1, -1, -1),
			/**/new OpcDT("SW_"       , 1, 0b111001, I_TYPE, -1, -1, -1),
			new OpcDT("TRUNC.L.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001001),
			new OpcDT("TRUNC.W.fmt", -1, 0b010001, R_TYPE, -1, 0, -1, -1, 0b001101),
		};
	}
	
	static class OpcDT{
		public String opcode = "";
		public int opcType = 0;
		public int optspb = -1;
		public int group = 0;
		public int[] mask;
		public int hasOpcBEncoded;
		public OpcDT(String opcode, int hasOpcBEncoded, int opcType, int group, int...mask) {
			this.opcode = opcode;
			this.opcType = opcType;
			if(hasOpcBEncoded!=-1)
				this.optspb = ((opcType>>>2)&0b1111);
			this.group = group;
			this.mask = mask;
			this.hasOpcBEncoded = hasOpcBEncoded;
		}
	}
	private static class OpcGDT{
		public int[][] map;
		public OpcGDT(int[]...sec) {
			map = sec;
		}
	}
}
