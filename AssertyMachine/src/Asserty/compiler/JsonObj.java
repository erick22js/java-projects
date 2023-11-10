package Asserty.compiler;

import org.json.*;

public class JsonObj extends JSONObject
{
	
	public JsonObj(){
		
	}
	public JsonObj(String str) throws JSONException{
		super(str);
	}
	
	@Override
	public JsonObj accumulate(String name, Object value)
	{
		// TODO: Implement this method
		try{
			super.accumulate(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}
	
	@Override
	public JsonObj put(String name, int value)
	{
		// TODO: Implement this method
		try{
			super.put(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JsonObj put(String name, boolean value)
	{
		// TODO: Implement this method
		try{
			super.put(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JsonObj put(String name, double value)
	{
		// TODO: Implement this method
		try{
			super.put(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JsonObj put(String name, Object value)
	{
		// TODO: Implement this method
		try{
			super.put(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JsonObj put(String name, long value)
	{
		// TODO: Implement this method
		try{
			super.put(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JsonObj putOpt(String name, Object value)
	{
		// TODO: Implement this method
		try{
			super.put(name, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}
	
	

	@Override
	public int getInt(String name)
	{
		// TODO: Implement this method
		try{
			return super.getInt(name);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public String getString(String name)
	{
		// TODO: Implement this method
		try{
			return super.getString(name);
		}catch(JSONException error){
			return "";
		}
	}

	@Override
	public long getLong(String name)
	{
		// TODO: Implement this method
		try{
			return super.getLong(name);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public double getDouble(String name)
	{
		// TODO: Implement this method
		try{
			return super.getDouble(name);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public ArrayObj getJSONArray(String name)
	{
		// TODO: Implement this method
		try{
			return (ArrayObj)super.getJSONArray(name);
		}catch(JSONException error){
			return null;
		}
	}

	@Override
	public JsonObj getJSONObject(String name)
	{
		// TODO: Implement this method
		try{
			return (JsonObj)super.getJSONObject(name);
		}catch(JSONException error){
			return null;
		}
	}

	@Override
	public boolean getBoolean(String name)
	{
		// TODO: Implement this method
		try{
			return super.getBoolean(name);
		}catch(JSONException error){
			return false;
		}
	}

	@Override
	public Object get(String name)
	{
		// TODO: Implement this method
		try{
			return super.get(name);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public String toString(int indentSpaces)
	{
		// TODO: Implement this method
		try{
			return super.toString(indentSpaces);
		}catch(JSONException erro){
			return "";
		}
	}
	
	
	
}
