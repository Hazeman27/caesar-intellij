package caesar.game.relief;

public class Vector {
	
	private final int x;
	private final int y;
	
	Vector(int x1, int y1, int x2, int y2) {
		
		this.x = x2 - x1;
		this.y = y2 - y1;
	}
	
	public Direction getDirection() {
		
		int x = this.x, y = this.y;
		
		if (x != 0)
			x = this.x / Math.abs(this.x);
		
		if (y != 0)
			y = this.y / Math.abs(this.y);
		
		return Direction.valueOf(x, y);
	}
	
	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
}