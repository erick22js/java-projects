package Asserty.compiler;

import org.json.*;
import java.util.*;

public class ArrayObj extends JSONArray
{

	@Override
	public Spliterator<Object> spliterator()
	{
		// TODO: Implement this method
		return null;
	}
	
	
	public ArrayObj(){
		
	}
	public ArrayObj(String str) throws JSONException{
		super(str);
	}
	
	@Override
	public ArrayObj put(Object value)
	{
		// TODO: Implement this method
		super.put(value);
		return this;
	}

	@Override
	public ArrayObj put(int index, long value)
	{
		// TODO: Implement this method
		try{
			super.put(index, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JSONArray put(int index, double value)
	{
		// TODO: Implement this method
		try{
			super.put(index, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JSONArray put(int index, int value)
	{
		// TODO: Implement this method
		try{
			super.put(index, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JSONArray put(int index, boolean value)
	{
		// TODO: Implement this method
		try{
			super.put(index, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}

	@Override
	public JSONArray put(int index, Object value)
	{
		// TODO: Implement this method
		try{
			super.put(index, value);
			return this;
		}catch(JSONException error){
			return this;
		}
	}
	
	public JSONArray putAll(JSONArray value){
		for(int i=0; i<value.length(); i++){
			this.put(value.get(i));
		}
		return this;
	}

	@Override
	public int getInt(int index)
	{
		// TODO: Implement this method
		try{
			return super.getInt(index);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public String getString(int index)
	{
		// TODO: Implement this method
		try{
			return super.getString(index);
		}catch(JSONException error){
			return "";
		}
	}

	@Override
	public long getLong(int index)
	{
		// TODO: Implement this method
		try{
			return super.getLong(index);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public double getDouble(int index)
	{
		// TODO: Implement this method
		try{
			return super.getDouble(index);
		}catch(JSONException error){
			return 0;
		}
	}

	@Override
	public ArrayObj getJSONArray(int index)
	{
		// TODO: Implement this method
		try{
			return (ArrayObj)super.getJSONArray(index);
		}catch(JSONException error){
			return null;
		}
	}
	

	@Override
	public JsonObj getJSONObject(int index)
	{
		// TODO: Implement this method
		try{
			return (JsonObj)super.getJSONObject(index);
		}catch(JSONException error){
			return null;
		}
	}

	@Override
	public boolean getBoolean(int index)
	{
		// TODO: Implement this method
		try{
			return super.getBoolean(index);
		}catch(JSONException error){
			return false;
		}
	}

	@Override
	public Object get(int index)
	{
		// TODO: Implement this method
		try{
			return super.get(index);
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
