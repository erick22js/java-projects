package Asserty.compiler;

public class DataDeal
{
	public static String tokenType(String token){
		if(Tokener.isEndOfLine(token))
			return "brk";
		//Verifica ser um caractere especial (no caso, um e comercial)
		if(Tokener.equalStr("%", token))
			return "ptr";
		if(Tokener.equalStr("\n", token))
			return "brk";
		if(Tokener.equalStr(",", token))
			return "spt";
		if(Tokener.equalStr("[", token))
			return "eps";
		if(Tokener.equalStr("]",token))
			return "epe";
		if(
			Tokener.equalStr("+",token)||
			Tokener.equalStr("-",token)||
			Tokener.equalStr("*",token)||
			Tokener.equalStr("&",token)||
			Tokener.equalStr("|",token)||
			Tokener.equalStr("^",token)
			)
			return "sign";
		if(
			Tokener.equalStr("<",token)||
			Tokener.equalStr(">",token))
			return "sgt";
		
		//Primeiro, checa se é um número
		//Octal, binario, hexadecimal ou decimal
		if(token.matches("0[0-7]+|0b[01]+|0x[0-9a-fA-F]+|[0-9]+")){
			return "lit";
		}
		//Se não, verifica ser um registro
		for(int i=0; i<BankTokens.Registers.length; i+=2){
			if(Tokener.equalStr((String)BankTokens.Registers[i], token))
				return "reg";
		}
		//Se não, verifica ser um registro
		for(int i=0; i<BankTokens.Instructions.length; i+=2){
			if(Tokener.equalStr((String)BankTokens.Instructions[i], token))
				return "opr";
		}
		//Se não, verifica ser uma palavra-chave
		for(int i=0; i<BankTokens.Keywords.length; i++){
			if(Tokener.equalStr((String)BankTokens.Keywords[i], token))
				return "key";
		}

		//Um conjunto de texto
		if(token.charAt(0)==token.charAt(token.length()-1))
			if(token.charAt(0)=='"'||token.charAt(0)=='\'')
				return "str";

		//Por fim, concluí-se ser uma tag
		return "tag";
	}
	
	public static int intToken(String token){
		//Octal
		if(token.matches("0[0-7]+")){
			return Integer.parseInt(token.substring(1), 8);
		}
		//Binario
		if(token.matches("0b[01]+")){

			return Integer.parseInt(token.substring(2), 2);
		}
		//Hexadecimal
		if(token.matches("0x[0-9a-fA-F]+")){

			return Integer.parseInt(token.substring(2), 16);
		}
		//Decimal
		return Integer.parseInt(token);
	}
	
	public static String strToken(String token){
		return token.substring(1, token.length()-1);
	}
	
	public static int regToken(String token){
		for(int i=0; i<BankTokens.Registers.length; i+=2){
			if(Tokener.equalStr(token,(String)BankTokens.Registers[i]))
				return (int) BankTokens.Registers[i+1];
		}
		return -1;
	}
	
	public static int getInstruction(String name) {
		for(int i=0; i<BankTokens.Instructions.length; i+=2){
			if(Tokener.equalStr((String)BankTokens.Instructions[i], name))
				return i;
		}
		return -1;
	}
	public static int getDataSize(JsonObj data){
		String type = data.getString("type");
		if(type=="lit")
			return 2;
		if(type=="reg")
			return 1;
		if(type=="mem")
			return 4;
		if(type=="ptr")
			return 1;
		if(type=="sign")
			return 1;
		if(type=="exp") {
			int size = 2;
			ArrayObj list = data.getJSONArray("values");
			for(int i=0; i<list.length(); i++) {
				JsonObj d = list.getJSONObject(i);
				size += getDataSize(d);
			}
			return size;
		}
		return 0;
	}
}
