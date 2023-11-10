package Asserty.compiler;

public class BankTokens
{
	public static Object[] DataSize = new Object[]{
		"reg", 0x1,
		"lit", 0x2,
		"ptr", 0x1,
		"mem", 0x4,
	};
	public static Object[] Registers = new Object[]{
		"inp", 0x0,
		"stp", 0x1,
		"acu", 0x2,
		"stt", 0x3,
		"dls", 0x4,
		"r0", 0x5,
		"r1", 0x6,
		"r2", 0x7,
		"r3", 0x8,
		"r4", 0x9,
		"r5", 0xa,
		"r6", 0xb,
		"r7", 0xc,
		"r8", 0xd,
		"r9", 0xe,
		"r10", 0xf,
		"r11", 0x10,
		"r12", 0x11,
		"ax", 0x12,
		"bx", 0x13,
		"cx", 0x14,
		"dx", 0x15,
		"ex", 0x16,
		"fx", 0x17,
	};
	public static Object[] Instructions = new Object[]{
		"mov", new Object[]{
				new String[] {"lit", "reg"}, 0x10,
				new String[] {"reg", "reg"}, 0x11,
				new String[] {"reg", "mem"}, 0x12,
				new String[] {"mem", "reg"}, 0x13,
				new String[] {"lit", "mem"}, 0x14,
				new String[] {"reg", "ptr"}, 0x15,
				new String[] {"ptr", "reg"}, 0x16,
				new String[] {"lit", "ptr"}, 0x17,
				new String[] {"exp", "reg"}, 0x70,
				new String[] {"exp", "mem"}, 0x71,
				new String[] {"exp", "ptr"}, 0x72,
			},
		"mov8", new Object[]{
				new String[] {"mem", "reg"}, 0x18,
				new String[] {"ptr", "reg"}, 0x19,
				new String[] {"reg", "mem"}, 0x1a,
				new String[] {"reg", "ptr"}, 0x1b,
				new String[] {"lit", "reg"}, 0x1c,
				new String[] {"lit", "mem"}, 0x1d,
				new String[] {"lit", "ptr"}, 0x1e,
			},
		"psh", new Object[] {
				new String[] {"lit"}, 0x20,
				new String[] {"reg"}, 0x21,
				new String[] {"mem"}, 0x22,
				new String[] {"ptr"}, 0x23,
			},
		"pop", new Object[] {
				new String[] {"reg"}, 0x24,
			},
		"inc", new Object[] {
				new String[] {"reg"}, 0x26,
			},
		"dec", new Object[] {
				new String[] {"reg"}, 0x27,
			},
		"add", new Object[]{
				new String[] {"lit", "reg"}, 0x28,
				new String[] {"reg", "reg"}, 0x29,
			},
		"sub", new Object[]{
				new String[] {"lit", "reg"}, 0x2a,
				new String[] {"reg", "reg"}, 0x2b,
			},
		"mul", new Object[]{
				new String[] {"lit", "reg"}, 0x2c,
				new String[] {"reg", "reg"}, 0x2d,
			},
		"div", new Object[]{
				new String[] {"lit", "reg"}, 0x2e,
				new String[] {"reg", "reg"}, 0x2f,
			},
		"odiv", new Object[]{
				new String[] {"lit", "reg"}, 0x30,
				new String[] {"reg", "reg"}, 0x31,
			},
		"odiv", new Object[]{
				new String[] {"lit", "reg"}, 0x30,
				new String[] {"reg", "reg"}, 0x31,
			},
		"lsf", new Object[]{
				new String[] {"lit", "reg"}, 0x35,
				new String[] {"reg", "reg"}, 0x36,
			},
		"rsf", new Object[]{
				new String[] {"lit", "reg"}, 0x37,
				new String[] {"reg", "reg"}, 0x38,
			},
		"and", new Object[]{
				new String[] {"lit", "reg"}, 0x39,
				new String[] {"reg", "reg"}, 0x3a,
			},
		"or", new Object[]{
				new String[] {"lit", "reg"}, 0x3b,
				new String[] {"reg", "reg"}, 0x3c,
			},
		"xor", new Object[]{
				new String[] {"lit", "reg"}, 0x3d,
				new String[] {"reg", "reg"}, 0x3e,
			},
		"not", new Object[] {
				new String[] {"reg"}, 0x3f,
			},
		"jmp", new Object[] {
				new String[] {"mem"}, 0x40,
				new String[] {"ptr"}, 0x41,
			},
		"cmp", new Object[] {
				new String[] {"lit", "reg"}, 0x42,
				new String[] {"reg", "reg"}, 0x43,
			},
		"jet", new Object[] {
				new String[] {"mem"}, 0x44,
				new String[] {"ptr"}, 0x45,
			},
		"jne", new Object[] {
				new String[] {"mem"}, 0x46,
				new String[] {"ptr"}, 0x47,
			},
		"jgt", new Object[] {
				new String[] {"mem"}, 0x48,
				new String[] {"ptr"}, 0x49,
			},
		"jlt", new Object[] {
				new String[] {"mem"}, 0x4a,
				new String[] {"ptr"}, 0x4b,
			},
		"jge", new Object[] {
				new String[] {"mem"}, 0x4c,
				new String[] {"ptr"}, 0x4d,
			},
		"jle", new Object[] {
				new String[] {"mem"}, 0x4e,
				new String[] {"ptr"}, 0x4f,
			},
		"call", new Object[] {
				new String[] {"mem"}, 0x50,
				new String[] {"ptr"}, 0x51,
			},
		"ret", new Object[] {
				new String[] {}, 0x54,
			},
		"cdl", new Object[] {
				new String[] {"mem", "reg", "mem"}, 0x5e,
				new String[] {"ptr", "reg", "ptr"}, 0x5f,
			},
		"int", new Object[] {
				new String[] {"lit"}, 0x60,
			},
		"bks", new Object[]{
				new String[] {"lit"}, 0x61,
				new String[] {"reg"}, 0x62,
				new String[] {"exp"}, 0x63,
		},
		"bkd", new Object[]{
				new String[] {"lit"}, 0x64,
				new String[] {"reg"}, 0x65,
				new String[] {"exp"}, 0x66,
		},
		"nop", new Object[] {
				new String[] {}, 0x6d,
			},
		"wai", new Object[] {
				new String[] {"lit"}, 0x6e,
			},
		"hls", new Object[] {
				new String[] {}, 0x6f,
			},
		
	};
	public static Object[] Keywords = new Object[] {
		"import", "define", "restrict",
		"struct",
		"dl", "dl8",
	};
}
