package Asserty.compiler;

public class AssertyParser
{
	private int cmp = 0;
	private JsonObj finalMap = null;
	private JsonObj structs = null;
	private ArrayObj tokens = null;
	private int line = 0;
	
	private Importer importer;
	private JsonObj imports;
	//private ArrayObj importedInstructions;
	
	private boolean importMaps;
	
	private int instLen = 0;
	
	private JsonObj tags = null;
	private JsonObj rtags = null;
	
	public AssertyParser(Importer importer) {
		this.importer = importer;
	}
	
	public JsonObj parseMap(String code, String name)throws Exception{
		JsonObj importsn = new JsonObj();
		importsn.put(name, 1);
		return parseMap(code, name, true, importsn);
	}
	
	public JsonObj parseMap(String code, String name, boolean doMapping, JsonObj imports)throws Exception{
		this.imports = imports;
		ArrayObj tokens = AssertyParser.bakeTokens(code+"\n");
		//if(tokens.length()==0)
			//
		JsonObj map = new JsonObj();
		finalMap = map;
		structs = new JsonObj();
		tags = new JsonObj();
		rtags = new JsonObj();
		importMaps = doMapping;
		ArrayObj instructions = new ArrayObj();
		this.tokens = tokens;
		cmp = 0;
		line = 0;
		try {
			while(cmp<tokens.length()) {
				JsonObj inst = ExpectingInstruction(instructions);
				if(inst!=null){
					inst.put("line", line);
					instructions.put(inst);
				}
			}
			map.put("instructions", instructions);
			
			int binSize = generateTags(instructions);
			map.put("binarySize", binSize);
			//System.out.println(map.toString(2));
			generateConsts(instructions);
			if(doMapping)
				generateStructs(instructions);
			attribTagsValues(instructions);
			if(doMapping){
				solveExpressions(instructions);
				attribStructAdresses(instructions);
				//solveExpressions(instructions);
				map.put("tags", tags);
			}
			map.put("rtags", rtags);
		}catch(Exception e) {
			System.err.println("Error in '"+name+"' at line "+line+": "+(e.getMessage()));
			//System.err.println("details: "+(e.printStackTrace()));
			e.printStackTrace();
			throw e;
		}
		//System.out.println(map.toString(2));
		return map;
	}
	
	public String fetch() throws Exception {
		if(Tokener.isEndOfLine(tokens.getString(cmp))){
			line = Integer.parseInt(tokens.getString(cmp).substring(3, tokens.getString(cmp).length()));
			
		}
		if(cmp>tokens.length())
			throw new Exception("Trying to escape file size");
		//line += Tokener.isEndOfLine(tokens.getString(cmp))?1:0;
		return tokens.getString(cmp++);
	}
	
	private JsonObj ExpectingInstruction(ArrayObj oprs) throws Exception{
		String tk = fetch();
		//System.out.println(tk+"->"+DataDeal.tokenType(tk));
		switch(DataDeal.tokenType(tk)) {
			case "opr":{
					JsonObj inst = DealInstruction(tk);
					return inst;
				}
			case "key":{
					JsonObj instC = DealCIns(tk, oprs);
					return instC;
				}
			case "tag":{
					JsonObj tag = DealTag(tk);
					return tag;
				}
			case "brk":{
					return null;
				}
			default:
				throw new UnexpectedEntity(tk);
		}
		//return null;
		//System.out.println(tk);
	}
	
	private int generateTags(ArrayObj insts){
		int address = 0;
		for(int i=0; i<insts.length(); i++){
			JsonObj opr = insts.getJSONObject(i);
			line = opr.getInt("line");
			if(opr.getString("type")=="tag"){
				tags.put(opr.getString("tag"), address);
			}
			if(opr.getString("type")=="instruction"){
				address += opr.getInt("size");
			}
			if(opr.getString("type")=="list"){
				address += opr.getInt("size");
			}
		}
		return address;
	}
	private void generateConsts(ArrayObj insts)throws Exception{
		for(int i=0; i<insts.length(); i++){
			JsonObj opr = insts.getJSONObject(i);
			line = opr.getInt("line");
			if(opr.getString("type")=="define"){
				if(tags.has(opr.getString("tag")))
					throw new DuplicatingTokens(opr.getString("tag"));
				tags.put(opr.getString("tag"), opr.getInt("const"));
			}
			else if(opr.getString("type")=="restrict"){
				if(rtags.has(opr.getString("tag")))
					throw new DuplicatingTokens(opr.getString("tag"));
				rtags.put(opr.getString("tag"), opr.getInt("const"));
				opr.remove("type");
			}
		}
	}
	private void generateStructs(ArrayObj insts)throws Exception{
		for(int i=0; i<insts.length(); i++){
			JsonObj opr = insts.getJSONObject(i);
			line = opr.getInt("line");
			if(opr.getString("type")=="struct"){
				if(structs.has(opr.getString("tag")))
					throw new DuplicatingStructs(opr.getString("tag"));
				structs.put(opr.getString("tag"), opr.getJSONObject("attribs"));
			}
		}
	}
	private void attribTagsValues(ArrayObj insts)throws Exception{
		for(int i=0; i<insts.length(); i++){
			JsonObj opr = insts.getJSONObject(i);
			line = opr.getInt("line");
			if(opr.getString("type")=="instruction"){
				ArrayObj args = opr.getJSONArray("args");
				for(int a=0; a<args.length(); a++){
					JsonObj arg = args.getJSONObject(a);
					attribTag(arg);
				}
			}
			else if(opr.getString("type")=="list"){
				attribTag(opr.getJSONObject("address"));
				ArrayObj args = opr.getJSONArray("args");
				for(int a=0; a<args.length(); a++){
					JsonObj arg = args.getJSONObject(a);
					attribTag(arg);
				}
			}
		}
	}
	private void attribStructAdresses(ArrayObj insts) throws Exception{
		for(int i=0; i<insts.length(); i++){
			JsonObj opr = insts.getJSONObject(i);
			line = opr.getInt("line");
			if(opr.getString("type")=="instruction"){
				ArrayObj args = opr.getJSONArray("args");
				for(int a=0; a<args.length(); a++){
					JsonObj arg = args.getJSONObject(a);
					attribStc(arg);
				}
			}
		}
	}
	
	private void attribTag(JsonObj value)throws Exception{
		val: if(value.getBoolean("tagged")){
			if(!rtags.has(value.getString("value")))
				break val;
			value.put("value",
					  rtags.getInt(value.getString("value")));
			value.put("tagged", false);
		}
		if(importMaps){
			if(value.getBoolean("tagged")){
				if(!tags.has(value.getString("value")))
					throw new InexistentTag(value.getString("value"));
				value.put("value",
					tags.getInt(value.getString("value")));
			}
		}
		if(value.getString("type")=="exp") {
			ArrayObj values = value.getJSONArray("values");
			for(int v=0; v<values.length(); v++) {
				attribTag(values.getJSONObject(v));
			}
		}
	}
	private void attribStc(JsonObj value)throws Exception{
		if(value.getBoolean("structed")){
			if(!structs.has(value.getString("struct")))
				throw new InexistentStruct(value.getString("struct"));
			value.put("value",
				value.getInt("value")+structs.getJSONObject(
					value.getString("struct")).getInt(
						value.getString("attrib")));
		}
	}

	private void solveExpressions(ArrayObj insts)throws Exception{
		for(int i=0; i<insts.length(); i++){
			JsonObj opr = insts.getJSONObject(i);
			line = opr.getInt("line");
			if(opr.getString("type")=="instruction"){
				ArrayObj args = opr.getJSONArray("args");
				for(int a=0; a<args.length(); a++){
					JsonObj arg = args.getJSONObject(a);
					if(arg.getString("type")=="exp") {
						JsonObj exp = new JsonObj();
						exp.put("type", "exp");
						exp.put("values", analizeExpression(solveExpression(arg)));
						args.put(a, exp);
						
					}
				}
			}
		}
	}
	
	
	private ArrayObj solveExpression(JsonObj arg) {
		ArrayObj tokens = new ArrayObj();
		ArrayObj toks = arg.getJSONArray("values");
		for(int i=0; i<toks.length(); i++){
			JsonObj opr = toks.getJSONObject(i);
			line = opr.getInt("line");
			JsonObj ntok = new JsonObj();
			if(opr.getString("type")=="lit"){
				ntok.put("value", opr.getInt("value"));
				ntok.put("type", "lit");
				tokens.put(ntok);
			}
			else if(opr.getString("type")=="reg"){
				ntok.put("value", opr.getInt("value"));
				ntok.put("type", "reg");
				tokens.put(ntok);
			}
			else if(opr.getString("type")=="sign"){
				ntok.put("value", opr.getString("value"));
				ntok.put("type", "sign");
				tokens.put(ntok);
			}
		}
		return tokens;
	}
	
	private ArrayObj analizeExpression(ArrayObj exp) {
		ArrayObj nexp = new ArrayObj();
		int i = 0;
		boolean prefix = true;
		//true: deve haver um sinal
		//false: deve haver um valor
		while(i<exp.length()) {
			JsonObj tok = exp.getJSONObject(i);
			if(prefix) {
				if(tok.getString("type")=="lit"||tok.getString("type")=="reg") {
					nexp.put(tok.getString("type")+"_+");
					prefix = false;
					i--;
				}else if(tok.getString("type")=="sign") {
					i++;
					JsonObj tok2 = exp.getJSONObject(i);
					nexp.put(tok2.getString("type")+"_"+tok.getString("value"));
					prefix = false;
					if(!(tok2.getString("type")=="begin"))
						i--;
					else
						prefix = true;
				}else{
					nexp.put(tok.getString("type"));
				}
			}else {
				nexp.put(tok.getInt("value"));
				prefix = true;
			}
			i++;
		}
		return nexp;
	}
	
	
	private JsonObj DealCIns(String opr, ArrayObj oprs)throws Exception{ //Abreviação para "Compiler instruction"
		JsonObj data = new JsonObj();
		switch(opr){
			case "import":
				dealImport(oprs);
				break;
			case "define":
				data = constantDef();
				break;
			case "restrict":
				data = restrictDef();
				break;
			case "struct":
				data = dealStruct();
				break;
			case "dl":
			case "dl8":
				int factor = 
					Tokener.equalStr(opr, "dl")?0:2;
				data = defineListDeal(factor);
				break;
			default:
				throw new UnexpectedEntity(opr);
		}
		return data;
	}
	
	private void dealImport(ArrayObj oprs) throws Exception {
		String arg = fetch();
		String t = DataDeal.tokenType(arg);
		boolean isModule = false;
		test: if(t!="str"){
			if(Tokener.equalStr(arg, "<")){
				arg = fetch();
				if(DataDeal.tokenType(arg)=="tag"){
					String chk = arg;
					String tk = "";
					while(!Tokener.equalStr((tk=fetch()), ">")){
						chk += tk;
					}
					arg = chk;
					isModule = true;
					break test;
				}
				throw new UnexpectedEntity(arg);
			}
			throw new UnexpectedEntity(arg);
		}
		else
			arg = DataDeal.strToken(arg);
		String end = fetch();
		if(!Tokener.isEndOfLine(end))
			throw new ExpectedEntity("end of line");//UnexpectedEntity(end);
		if(imports.has(arg)) {
			System.err.println("Duplicating imports, avoiding...");
			return;
		}
		else
			imports.put(arg, 1);
		String code;
		try {
			if(isModule)
				code = importer.getModule(arg);
			else
				code = importer.getFile(arg);
		}catch(Exception e) {
			throw e;
		}
		if(code==null)
			return;
		System.err.print(code);
		JsonObj nmap = new AssertyParser(importer).parseMap(code, arg, false, imports);
		//	System.out.println("\n"+nmap.toString(2));
		importMap(nmap, oprs);
	}
	
	private void importMap(JsonObj map, ArrayObj oprs) {
		oprs.putAll(map.getJSONArray("instructions"));
	}
	
	private JsonObj dealStruct()throws Exception{
		JsonObj data = new JsonObj();
		data.put("type","struct");
		String name = fetch();
		if(!(DataDeal.tokenType(name)=="tag"))
			throw new UnexpectedEntity(name);
		data.put("tag", name);
		JsonObj attribs = new JsonObj();
		String token = fetch();
		if(!(Tokener.equalStr(token, "{")))
			throw new UnexpectedEntity(token);
		while(true){
			token = fetch();
			if(Tokener.isEndOfLine(token)){
				continue;
			}else
			if(Tokener.equalStr(token, "}")){
				break;
			}
			String typ = DataDeal.tokenType(token);
			if(typ!="tag")
				throw new UnexpectedEntity(token);
			String tag = token;
			token = fetch();
			typ = DataDeal.tokenType(token);
			if(!Tokener.equalStr(token, ":"))
				throw new UnexpectedEntity(token);
			token = fetch();
			typ = DataDeal.tokenType(token);
			if(typ!="lit")
				throw new UnexpectedEntity(token);
			int offset = DataDeal.intToken(token);
			attribs.put(tag, offset);
		}
		data.put("attribs",attribs);
		return data;
	}
	
	private JsonObj defineListDeal(int factor)throws Exception{
		JsonObj data = new JsonObj();
		data.put("type", "list");
		data.put("factor", factor);
		int code = 0x58;
		code += factor;
		String address = fetch();
		JsonObj evalAdd = fetchValue(address);
		String addresst = evalAdd.getString("type");
		boolean poi = false;
		if(addresst=="mem"){
			data.put("pointer", "mem");
			data.put("address", evalAdd);
		}else if(addresst=="ptr"){
			code++;
			data.put("pointer", "ptr");
			data.put("address", evalAdd);
			poi = true;
		}else
			throw new UnexpectedEntity(addresst);
		data.put("code", code);
		String split = fetch();
		if(!Tokener.equalStr(split, ","))
			throw new ExpectedEntity(",");
		//Agora, é-se decidido o tipo de dado
		//Caso seja texto
		String dat = fetch();
		String datt = DataDeal.tokenType(dat);
		int size = 0;
		if(datt=="str"){
			String str = DataDeal.strToken(dat);
			ArrayObj args = new ArrayObj();
			for(int i=0; i<str.length(); i++){
				JsonObj valu = new JsonObj();
				valu.put("value", (int)str.charAt(i));
				valu.put("type", "lit");
				valu.put("tagged",false);
				valu.put("structed",false);
				args.put(valu);
			}
			data.put("args", args);
			size = args.length();
		}else if(Tokener.equalStr(dat, "[")){
			boolean forNum = true;
			ArrayObj args = new ArrayObj();
			//ForNum se verdadeiro deve receber um valor (ou encerrar)
			//ForNum se falso, deve fatiar ou finalizar
			while(true){
				String value = fetch();
				/*JsonObj valua = null;
				if(Tokener.equalStr(value, ","))
					valua = fetchValue(value);*/
				if(forNum){
					JsonObj valua = fetchValue(value);
					if(Tokener.isEndOfLine(value)){
						continue;
					}else if(Tokener.equalStr(value, "]")){
						break;
					}else if(valua.getString("type")=="lit"){
						args.put(valua);
						forNum = false;
					}else
						throw new UnexpectedEntity(value);
				}else{
					if(Tokener.isEndOfLine(value)){
						continue;
					}else if(Tokener.equalStr(value, "]")){
						break;
					}else if(Tokener.equalStr(value, ",")){
						forNum = true;
					}else
						throw new UnexpectedEntity(value);
				}
			}
			data.put("args", args);
			size = args.length();
		}else
			throw new UnexpectedEntity(dat);
		int bytes = factor==0?2:factor==2?1:4;
		size = size*bytes + 2 + (poi?1:4) + 1;
		data.put("size", size);
		return data;
	}
	
	private JsonObj restrictDef()throws Exception{
		JsonObj data = new JsonObj();
		data.put("type", "restrict");
		String tag = fetch();
		String tagt = DataDeal.tokenType(tag);
		if(tagt!="tag"){
			throw new UnexpectedEntity(tag);
		}
		String lit = fetch();
		String li = DataDeal.tokenType(lit);
		if(li!="lit"){
			throw new UnexpectedEntity(lit);
		}
		data.put("tag", tag);
		data.put("const", DataDeal.intToken(lit));
		if(!Tokener.isEndOfLine(fetch()))
			throw new ExpectedEntity("end of line");
		return data;
	}
	
	private JsonObj constantDef()throws Exception{
		JsonObj data = new JsonObj();
		data.put("type", "define");
		String tag = fetch();
		String tagt = DataDeal.tokenType(tag);
		if(tagt!="tag"){
			throw new UnexpectedEntity(tag);
		}
		String lit = fetch();
		String li = DataDeal.tokenType(lit);
		if(li!="lit"){
			throw new UnexpectedEntity(lit);
		}
		data.put("tag", tag);
		data.put("const", DataDeal.intToken(lit));
		if(!Tokener.isEndOfLine(fetch()))
			throw new ExpectedEntity("end of line");
		return data;
	}
	
	private JsonObj DealTag(String tag)throws Exception{
		JsonObj data = new JsonObj();
		String nxt = fetch();
		if(Tokener.equalStr(nxt, ":")){
			String previne = fetch();
			if(Tokener.isEndOfLine(previne)){
				data.put("type", "tag");
				data.put("tag", tag);
			}else{
				throw new UnexpectedEntity(previne);
			}
		}else{
			throw new UnexpectedEntity(nxt);
		}
		return data;
	}
	
	private JsonObj DealInstruction(String opr) throws Exception{
		JsonObj data = new JsonObj();
		ArrayObj arguments = new ArrayObj();
		data.put("type", "instruction");
		data.put("family", opr);
		boolean argmode = true; 
		//true para receber argumento
		//false para receber separador ou encerramento
		while(cmp<tokens.length()) {
			String val = fetch();
			//System.out.println(val);
			if(Tokener.isEndOfLine(val)){
				break;
			}
			if(argmode==false){
				if(Tokener.equalStr(val,",")){
					argmode = true;
					continue;
				}
				else
					throw new ExpectedEntity(",");
			}
			String type =  DataDeal.tokenType(val);
			JsonObj value = fetchValue(val);
			arguments.put(value);
			argmode = false;
		}
		data.put("args", arguments);
		int posArg = compareArguments(arguments, (Object[]) BankTokens.Instructions[DataDeal.getInstruction(opr)+1]);
		data.put("size", instLen+1);
		if(posArg<0)
			throw new InvalidArguments(opr, arguments);
		data.put("vary", ((Object[]) BankTokens.Instructions[DataDeal.getInstruction(opr)+1])[posArg+1]);
		return data;
	}
	
	public int compareArguments(ArrayObj args, Object[] batch) {
		batchloop: for(int l=0; l<batch.length; l+=2) {
			String[] lote = (String[]) batch[l];
			instLen = 0;
			if(args.length()!=lote.length)
				continue batchloop;
			for(int arg=0; arg<args.length(); arg++) {
				JsonObj argo = args.getJSONObject(arg);
				instLen += DataDeal.getDataSize(argo);
				if(!Tokener.equalStr(argo.getString("type"), lote[arg])) {
					continue batchloop;
				}
			}
			return l;
		}
		return -1;
	}
	
	private JsonObj fetchValue(String val)throws Exception{
		JsonObj data = new JsonObj();
		String type = DataDeal.tokenType(val);
		data.put("type", type);
		data.put("tagged", false);
		data.put("structed", false);
		//System.out.println(val);
		switch(type) {
			case "lit":
				data.put("value", DataDeal.intToken(val));
				break;
			case "reg":
				data.put("value", DataDeal.regToken(val));
				break;
			case "ptr":
				String nxt = fetch();
				String ntype = DataDeal.tokenType(nxt);
				if(ntype=="lit") {
					data.put("type", "mem");
					data.put("value", DataDeal.intToken(nxt));
				}else if(ntype=="tag"){
					data.put("type", "mem");
					data.put("value", nxt);
					data.put("tagged", true);
				}else if(ntype=="reg") {
					data.put("type", "ptr");
					data.put("value", DataDeal.regToken(nxt));
				}else {
					throw new UnexpectedEntity(nxt);
				}
				break;
			case "str":
				data.put("value", DataDeal.strToken(val));
				break;
			case "tag":
				data.put("type", "lit");
				data.put("value", val);
				data.put("tagged", true);
				break;
			default:
				if(Tokener.equalStr("<",val)){
					String fet = fetch();
					String fety = DataDeal.tokenType(fet);
					if(fety!="tag")
						throw new UnexpectedEntity(fet);
					String stcName = fet;
					fet = fetch();
					if(!Tokener.equalStr(fet, ">"))
						throw new UnexpectedEntity(fet);
					String adr = fetch();
					JsonObj adrs = fetchValue(adr);
					if(adrs.getString("type")!="mem")
						throw new UnexpectedEntity(adr);
					fet = fetch();
					if(!Tokener.equalStr(fet, "."))
						throw new UnexpectedEntity(fet);
					fet = fetch();
					fety = DataDeal.tokenType(fet);
					if(fety!="tag")
						throw new UnexpectedEntity(fet);
					data.put("type","mem");
					//data.put("value", 999);
					//data.put("address", adr);
					data.put("struct", stcName);
					data.put("attrib", fet);
					data.put("value", adrs.get("value"));
					data.put("tagged", adrs.get("tagged"));
					data.put("structed", true);
				}
				//Checa sinal
				else if(Tokener.equalStr("sign", type)){
					data.put("type", "sign");
					data.put("value", val);
					data.put("tagged", false);
				}
				//Inicia expressão
				else if(Tokener.equalStr("[", val)){
					String fet = fetch();
					ArrayObj values = new ArrayObj();
					String latest = "";
					//boolean first = true;
					while(!Tokener.equalStr("]",val)){
						JsonObj va = fetchValue(fet);
						String vat = va.getString("type");
						//if(first&&vat)
						//first = false;
						if(vat!="lit"&&vat!="reg"&&vat!="sign")
							throw new UnexpectedEntity(fet);
						if(((latest=="lit"||latest=="reg")&&(vat=="lit"||vat=="reg"))
							||latest==vat
							)
							throw new UnexpectedEntity(fet);
						values.put(va);
						fet = fetch();
						latest = vat;
						if(Tokener.equalStr(fet, "]"))
							if(latest=="sign")
								throw new UnexpectedEntity(fet);
							else
								break;
					}
					data.put("values", values);
					data.put("type", "exp");
				}else
					throw new UnexpectedEntity(val);
		}
		return data;
	}
	
		//Lidando com formatações
	
	public static ArrayObj bakeTokens(String code){
		ArrayObj tokens = Tokener.splitText(code);
		int line = 1;
		for(int i=0; i<tokens.length(); i++) {
			if(Tokener.equalStr("\n", tokens.getString(i))) {
				tokens.put(i, String.valueOf((char)3)+"nl"+line);
				line++;
			}
		}
		tokens = removeComment(tokens);
		tokens = Tokener.jointStrings(tokens);
		tokens = Tokener.removeSpaces(tokens);
		//System.out.println("Removing comment:\n"+tokens.toString(2));
		return tokens;
	}
	private static ArrayObj removeComment(ArrayObj tokens){
		ArrayObj ntokens = new ArrayObj();
		boolean inComment = false;
		for(int i=0; i<tokens.length(); i++){
			String token = tokens.getString(i);
			char tk = token.charAt(0);
			if(inComment){
				if(Tokener.isEndOfLine(token)){
					inComment = false;
					ntokens.put(token);
				}
			}else{
				if(tk==';'){
					inComment = true;
				}else{
					ntokens.put(token);
				}
			}
		}
		return ntokens;
	}
}

class UnexpectedEntity extends Exception{
	public UnexpectedEntity(String entity) {
		super(
			Tokener.isEndOfLine(entity)?
			"Unexpected end of line":
			"Unexpected entity '"+entity+"'"
			);
	}
}

class ExpectedEntity extends Exception{
	public ExpectedEntity(String entity){
		super("Was expected entity '"+entity+"'");
	}
}

class InvalidArguments extends Exception{
	public InvalidArguments(String opr, ArrayObj args) {
		super("Invalid Arguments: '"+solveArguments(args)+"' for the instruction '"+opr+"'");
	}
	
	public static String solveArguments(ArrayObj args){
		String form = "";
		for(int a=0; a<args.length(); a++){
			String type = args.getJSONObject(a).getString("type");
			form += type+(a<args.length()-2?", ":a<args.length()-1?" and ":"");
		}
		return form;
	}
}

class DuplicatingTokens extends Exception{
	public DuplicatingTokens(String token){
		super("Cannot duplicate const declaration with name '"+token+"'");
	}
}

class DuplicatingStructs extends Exception{
	public DuplicatingStructs(String struct){
		super("Cannot duplicate struct declaration with name '"+struct+"'");
	}
}

class InexistentTag extends Exception{
	public InexistentTag(String tagRequired){
		super("Don't exist tag with name '"+tagRequired+"'");
	}
}
class InexistentStruct extends Exception{
	public InexistentStruct(String stcRequired){
		super("Don't exist struct with name '"+stcRequired+"'");
	}
}
