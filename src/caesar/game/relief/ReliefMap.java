package caesar.game.relief;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ReliefMap {
	
	private final Relief[][] relief;
	private final int size;
	
	public ReliefMap(int size) {
		
		this.relief = new Relief[size][size];
		this.size = size;
		
		for (int x = 0; x < size; x++) {
			
			for (int y = 0; y < size; y++)
				this.relief[x][y] = Relief.getRandom();
		}
	}
	
	@Contract(pure = true)
	public Relief getRelief(int x, int y) {
		
		if (x >= this.size || x < 0 || y >= this.size || y < 0)
			return Relief.UNKNOWN;
		
		return this.relief[x][y];
	}
	
	public Map<Direction, Relief> getReliefAround(@NotNull Location location) {
		
		Map<Direction, Relief> reliefMap = new HashMap<>();
		
		int x = location.getX();
		int y = location.getY();
		
		reliefMap.put(Direction.NORTH, this.getRelief(x, y + 1));
		reliefMap.put(Direction.NORTHWEST, this.getRelief(x - 1, y + 1));
		reliefMap.put(Direction.NORTHEAST, this.getRelief(x + 1, y + 1));
		reliefMap.put(Direction.WEST, this.getRelief(x - 1, y));
		reliefMap.put(Direction.EAST, this.getRelief(x + 1, y));
		reliefMap.put(Direction.SOUTH, this.getRelief(x, y - 1));
		reliefMap.put(Direction.SOUTHWEST, this.getRelief(x - 1, y - 1));
		reliefMap.put(Direction.SOUTHEAST, this.getRelief(x + 1, y - 1));
		
		return reliefMap;
	}
	
	public int getSize() {
		return this.size;
	}
	
	@Override
	public String toString() {
		
		StringBuilder map = new StringBuilder();
		
		for (int x = 0; x < this.size; x++) {
			
			for (int y = 0; y < this.size; y++)
				map.append(this.relief[x][y].toString(true));
			
			map.append("\n");
		}
		
		return map.toString();
	}
	
	public static void main(String[] args) {
		
		ReliefMap reliefMap = new ReliefMap(20);
		System.out.println(reliefMap);
	}
}