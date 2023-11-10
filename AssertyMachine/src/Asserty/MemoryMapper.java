package Asserty;

public class MemoryMapper{
	public int[][] Map;
	public MemoryMapper(int[] data){
		int size = 0;
		if(data.length%2==0){
			size = data.length/2;
			Map = new int[size][2];
			for(int offset=0; offset<data.length; offset+=2){
				Map[offset/2][0] = data[offset];
				Map[offset/2][1] = data[offset+1];//==0?1:data[offset+1];
			}
		}else{
			Map = new int[0][0];
		}
	}
	
	public int getOffset(int offset, int map){
		return offset%Map[map%Map.length][1];
	}
	
	public MemoryMapper joinMappers(MemoryMapper mapper2) {
		int[][] nMap= new int[Map.length+mapper2.Map.length][2];
		for(int i=0; i<Map.length; i++) {
			nMap[i] = Map[i];
		}
		for(int i=0; i<mapper2.Map.length; i++) {
			nMap[Map.length+i] = mapper2.Map[i];
		}
		MemoryMapper nMapper = new MemoryMapper(new int[] {1,1});
		nMapper.Map = nMap;
		return nMapper;
	}
}
