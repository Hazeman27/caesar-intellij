package caesar.game.map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum Direction {
	
	NORTH("North", 0, 1),
	NORTHWEST("Northwest", -1, 1),
	NORTHEAST("Northeast", 1, 1),
	WEST("West", -1, 0),
	EAST("East", 1, 0),
	SOUTH("South", 0, -1),
	SOUTHWEST("Southwest", -1, -1),
	SOUTHEAST("Southeast", 1, -1);
	
	private final String name;
	private final int x;
	private final int y;
	
	@Contract(pure = true)
	Direction(String name, int x, int y) {
		
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	@Contract(pure = true)
	public int getX() {
		return this.x;
	}
	
	@Contract(pure = true)
	public int getY() {
		return y;
	}
	
	@Contract(pure = true)
	@Override
	public String toString() {
		return this.name;
	}
	
	@Nullable
	@Contract(pure = true)
	public static Direction valueOf(int x, int y) {
		
		for (Direction direction: values()) {
			
			if (direction.x == x && direction.y == y)
				return direction;
		}
		
		return null;
	}
}
