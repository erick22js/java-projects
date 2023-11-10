package Asserty;

public class Ints
{
	//INSTRUÇÕES MOV
	final public static byte mov_lit_reg       = 0x10;
	final public static byte mov_reg_reg       = 0x11;
	final public static byte mov_reg_mem       = 0x12;
	final public static byte mov_mem_reg       = 0x13;
	final public static byte mov_lit_mem       = 0x14;
	final public static byte mov_reg_ptr       = 0x15;
	final public static byte mov_ptr_reg       = 0x16;
	final public static byte mov_lit_ptr       = 0x17;
	final public static byte mov_exp_reg       = 0x70;
	final public static byte mov_exp_mem       = 0x71;
	final public static byte mov_exp_ptr       = 0x72;
	final public static byte mov8_mem_reg      = 0x18;
	final public static byte mov8_ptr_reg      = 0x19;
	final public static byte mov8_reg_mem      = 0x1a;
	final public static byte mov8_reg_ptr      = 0x1b;
	final public static byte mov8_lit_reg      = 0x1c;
	final public static byte mov8_lit_mem      = 0x1d;
	final public static byte mov8_lit_ptr      = 0x1e;
	
	//INSTRUÇÕES DE EMPILHAMENTO
	final public static byte psh_lit           = 0x20;
	final public static byte psh_reg           = 0x21;
	final public static byte psh_mem           = 0x22;
	final public static byte psh_ptr           = 0x23;
	final public static byte pop_reg           = 0x24;
	
	//INSTRUÇÕES ARITMÉTICAS
	final public static byte inc_reg           = 0x26;
	final public static byte dec_reg           = 0x27;
	final public static byte add_lit_reg       = 0x28;
	final public static byte add_reg_reg       = 0x29;
	final public static byte sub_lit_reg       = 0x2a;
	final public static byte sub_reg_reg       = 0x2b;
	final public static byte mul_lit_reg       = 0x2c;
	final public static byte mul_reg_reg       = 0x2d;
	final public static byte div_lit_reg       = 0x2e;
	final public static byte div_reg_reg       = 0x2f;
	final public static byte odiv_lit_reg      = 0x30;
	final public static byte odiv_reg_reg      = 0x31;
	
	//INSTRUÇÕES DE OPERAÇÕES A BIT
	final public static byte lsf_lit_reg       = 0x35;
	final public static byte lsf_reg_reg       = 0x36;
	final public static byte rsf_lit_reg       = 0x37;
	final public static byte rsf_reg_reg       = 0x38;
	final public static byte and_lit_reg       = 0x39;
	final public static byte and_reg_reg       = 0x3a;
	final public static byte or_lit_reg        = 0x3b;
	final public static byte or_reg_reg        = 0x3c;
	final public static byte xor_lit_reg       = 0x3d;
	final public static byte xor_reg_reg       = 0x3e;
	final public static byte not_reg           = 0x3f;
	
	//INSTRUÇÕES DE PULOS E CONDICIONAMENTO
	final public static byte jmp_mem           = 0x40;
	final public static byte jmp_ptr           = 0x41;
	final public static byte cmp_lit_reg       = 0x42;
	final public static byte cmp_reg_reg       = 0x43;
	final public static byte jet_mem           = 0x44;
	final public static byte jet_ptr           = 0x45;
	final public static byte jne_mem           = 0x46;
	final public static byte jne_ptr           = 0x47;
	final public static byte jgt_mem           = 0x48;
	final public static byte jgt_ptr           = 0x49;
	final public static byte jlt_mem           = 0x4a;
	final public static byte jlt_ptr           = 0x4b;
	final public static byte jge_mem           = 0x4c;
	final public static byte jge_ptr           = 0x4d;
	final public static byte jle_mem           = 0x4e;
	final public static byte jle_ptr           = 0x4f;
	
	//CHAMADAS PARA SUB-ROTINAS COM SALVAMENTO
	final public static byte call_mem          = 0x50;
	final public static byte call_ptr          = 0x51;
	final public static byte ret               = 0x54;
	
	//MANIPULANDO MULTI-VALORES NA MEMÓRIA
	final public static byte dl_mem_lits       = 0x58;
	final public static byte dl_ptr_lits       = 0x59;
	final public static byte dl8_mem_lits      = 0x5a;
	final public static byte dl8_ptr_lits      = 0x5b;
	final public static byte cdl_mem_reg_mem   = 0x5e;
	final public static byte cdl_ptr_reg_ptr   = 0x5f;
	
	//INSTRUÇÕES ESPECIAIS
	final public static byte int_lit           = 0x60;
	final public static byte bnks_lit          = 0x61;
	final public static byte bnks_reg          = 0x62;
	final public static byte bnks_exp          = 0x63;
	final public static byte bnkd_lit          = 0x64;
	final public static byte bnkd_reg          = 0x65;
	final public static byte bnkd_exp          = 0x66;
	final public static byte nop               = 0x6d;
	final public static byte wai_lit           = 0x6e;
	final public static byte hls               = 0x6f;
	
}
