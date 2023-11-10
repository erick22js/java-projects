package Asserty.compiler;

public class Tokener{
	public static boolean isCharName(int code){
		return 
			(code>=48&&code<=57)||
			(code>=65&&code<=90)||
			(code==95)||
			(code>=97&&code<=122);
	}
	public static boolean equalStr(String t1, String t2){
		return t1.contentEquals(t2);
	}
	public static boolean equalStr(String t1, char t2){
		return t1.contentEquals(String.valueOf(t2));
	}
	public static ArrayObj splitText(String txt){
		ArrayObj tokens = new ArrayObj();
		String token = "";
		for(int i=0; i<txt.length(); i++){
			if(isCharName(txt.codePointAt(i))){
				token += txt.charAt(i);
				if(i==txt.length()-1){
					tokens.put(token);
				}
			}else{
				if(token!=""){
					tokens.put(token);
					token = "";
				}
				tokens.put(String.valueOf(txt.charAt(i)));
			}
		}
		return tokens;
	}
	public static ArrayObj jointStrings(ArrayObj tokens){
		ArrayObj ntokens = new ArrayObj();
		char inString = 0;
		String stringToken = "";
		for(int i=0; i<tokens.length(); i++){
			String actToken = tokens.getString(i);
			char tk = actToken.charAt(0);
			if(inString=='"'||inString=='\''){
				if(tk==inString){
					ntokens.put(String.valueOf(inString)+stringToken+String.valueOf(inString));
					inString = 0;
					stringToken = "";
				}else
					stringToken+=actToken;
			}else{
				if(tk=='"'||tk=='\'')
					inString = tk;
				else
					ntokens.put(actToken);
			}
		}
		return ntokens;
	}
	public static ArrayObj removeSpaces(ArrayObj tokens){
		ArrayObj ntokens = new ArrayObj();
		for(int i=0; i<tokens.length(); i++){
			String token = tokens.getString(i);
			if(!equalStr(token, " ")&&!equalStr(token, "\t")){
				ntokens.put(token);
			}
		}
		return ntokens;
	}
	public static ArrayObj removeExtraNewLines(ArrayObj tokens){
		ArrayObj ntokens = new ArrayObj();
		int nlcount = 0;
		for(int i=0; i<tokens.length(); i++){
			String token = tokens.getString(i);
			if(equalStr(token, "\n")){
				nlcount++;
				if(i==0||i==tokens.length()-1)
					continue;
			}else
				nlcount = 0;
			if(nlcount<=1)
				ntokens.put(token);
		}
		return ntokens;
	}
	public static boolean isEndOfLine(String token) {
		/*if(token.toCharArray().length==0)
			return true;*/
		return token.length()>3 ? equalStr(token.substring(0, 3), String.valueOf((char)3)+"nl") : false;
	}
}
