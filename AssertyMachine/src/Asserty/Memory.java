package Asserty;
import java.nio.*;

public class Memory{
	//Buffer de memória para acesso
	ByteBuffer buffer;
	MemoryMapper map;
	//MemoryMapper entirely;
	int length;
	int banksQ;
	
	//Proprieade de uma rom
	boolean readOnly;
	
	public Memory(int size, MemoryMapper mapper){
		buffer = ByteBuffer.allocate(size);
		length = size;
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		readOnly = false;
		remapBanks(mapper);
	}
	public Memory(int size){
		buffer = ByteBuffer.allocate(size);
		length = size;
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		readOnly = false;
		remapBanks(new MemoryMapper(new int[]{0, length}));
	}
	public Memory(byte[] data, MemoryMapper mapper, boolean readOnly){
		buffer = ByteBuffer.wrap(data);
		length = data.length;
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.readOnly = readOnly;
		remapBanks(mapper);
	}
	public Memory(byte[] data, boolean readOnly){
		buffer = ByteBuffer.wrap(data);
		length = data.length;
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.readOnly = readOnly;
		remapBanks(new MemoryMapper(new int[]{0, length}));
	}
	
	public Memory(ByteBuffer data, boolean readOnly){
		buffer = data;
		length = data.capacity();
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.readOnly = readOnly;
		map = new MemoryMapper(new int[]{0, length});
		banksQ = 1;
		remapBanks(new MemoryMapper(new int[]{0, length}));
	}
	
	public Memory(ByteBuffer data, MemoryMapper mapper, boolean readOnly){
		buffer = data;
		length = data.capacity();
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.readOnly = readOnly;
		map = mapper;
		banksQ = 1;
		remapBanks(mapper);
	}
	
	public Memory getBank(int bank) {
		byte[] b = buffer.array();
		byte[] b2 = new byte[map.Map[bank][1]];
		System.arraycopy(b, map.Map[bank][0], b2, 0, b2.length);
		return new Memory(b2, readOnly);
	}
	
	private void remapBanks(MemoryMapper banksMap){
		map = banksMap;
		banksQ = map.Map.length;
	}
	
	//ACESSO Á MEMÓRIA
	//Definir valores de 8 bits
	public void set8(byte value, int p){
		if(readOnly)
			return;
		buffer.put(p%length, value);
	}
	public void set8(int value, int p){
		if(readOnly)
			return;
		buffer.put(p%length, (byte)value);
	}
	//Definir valores de 16 bits
	public void set16(Short value, int p){
		if(readOnly)
			return;
		buffer.putShort(p%length, value);
	}
	public void set16(int value, int p){
		if(readOnly)
			return;
		buffer.putShort(p%length, (short)value);
	}

	//Obtém valores de 8 bits
	public int get8(int p){
		return buffer.get(p%length)&0xff;
	}
	//Obtém valores de 16 bits
	public int get16(int p){
		return buffer.getShort(p%length)&0xffff;
	}
	
	public int getOffset(int p){
		return p%length;
	}
	
	public int size(){
		return length;
	}
	
	//Opção para limpar(resetar) a memória
	public void reset(){
		buffer.clear();
	}
	
	//Este retorna os bytes da memória
	public byte[] getBytes(){
		return buffer.array();
	}
	//Este retorna o buffer da memória
	public ByteBuffer getBuffer(){
		return buffer;
	}
}
