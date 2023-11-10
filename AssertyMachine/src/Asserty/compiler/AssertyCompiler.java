package Asserty.compiler;
import Asserty.*;

public class AssertyCompiler
{
	Memory bin;
	int inp = 0;
	int cdp = 0;
	JsonObj map;
	
	public byte[] compile(JsonObj mapParsed){
		this.bin = new Memory(mapParsed.getInt("binarySize"), new MemoryMapper(new int[]{0,0}));
		inp = 0;
		this.map = mapParsed;
		
		loopInstructions();
		
		return bin.getBytes();
	}
	
	private JsonObj fetchInstruction(){
		return map.getJSONArray("instructions").getJSONObject(inp++);
	}
	
	private void push8(int v){
		bin.set8(v, cdp);
		cdp+=1;
	}
	private void push16(int v){
		bin.set16(v, cdp);
		cdp+=2;
	}
	private void push32(int v){
		bin.set16(v, cdp);
		cdp+=4;
	}
	
	private void loopInstructions(){
		ArrayObj insts = map.getJSONArray("instructions");
		while(inp<insts.length()){
			JsonObj inst = fetchInstruction();
			if(inst.getString("type")=="instruction"){
				push8(inst.getInt("vary"));
				ArrayObj args = inst.getJSONArray("args");
				for(int i=0; i<args.length(); i++){
					JsonObj arg = args.getJSONObject(i);
					pushValue(arg);
				}
			}else if(inst.getString("type")=="list"){
				push8(inst.getInt("code"));
				pushValue(inst.getJSONObject("address"));
				//System.out.println("value"+(inst.getJSONObject("address").getJSONObject("address").toString(1)));
				ArrayObj args = inst.getJSONArray("args");
				push16(args.length());
				for(int i=0; i<args.length(); i++){
					JsonObj arg = args.getJSONObject(i);
					if(inst.getInt("factor")==0)
						push16(arg.getInt("value"));
					if(inst.getInt("factor")==2)
						push8(arg.getInt("value"));
					//pushValue(arg);
				}
			}
		}
	}
	
	private void pushValue(JsonObj val){
		if(Tokener.equalStr(val.getString("type"),"lit")){
			push16(val.getInt("value"));
		}
		if(Tokener.equalStr(val.getString("type"),"mem")){
			push32(val.getInt("value"));
		}
		if(Tokener.equalStr(val.getString("type"),"exp")){
			//push32(val.getInt("value"));
			ArrayObj tokens = val.getJSONArray("values");
			for(int i=0; i<tokens.length(); i++) {
				//JsonObj token = tokens.getJSONObject(i);
				String signal = tokens.getString(i++);
				int value = tokens.getInt(i);
				String type = typeFromExpression(signal);
				String sign = signFromExpression(signal);
				int sinalf = 
						(Tokener.equalStr(type,"reg")?8:0)
						+(
							Tokener.equalStr(sign,"+")?0:
							Tokener.equalStr(sign,"-")?1:
							Tokener.equalStr(sign,"*")?2:
							Tokener.equalStr(sign,"&")?3:
							Tokener.equalStr(sign,"|")?4:
							Tokener.equalStr(sign,"^")?5:
							0
						);
				if(Tokener.equalStr(type,"reg")) {
					push8(sinalf);
					push8(value);
				}else {
					push8(sinalf);
					push16(value);
				}
			}
			push8(0x0f);
		}else if(
			Tokener.equalStr(val.getString("type"),"reg")||
			Tokener.equalStr(val.getString("type"),"ptr")
			){
			push8(val.getInt("value"));
		}
	}
	
	private String typeFromExpression(String token) {
		return token.substring(0, token.indexOf("_"));
	}
	private String signFromExpression(String token) {
		return token.substring(token.indexOf("_")+1, token.length());
	}
}
