package caesar.game.relief;

public class Location {
	
	private int x;
	private int y;
	private Relief relief;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static void main(String[] args) {
		
		Location location = new Location(0, 0);
		System.out.println(location);
	}
	
	public void change(int deltaX, int deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
	
	public Direction getDirection(Location location) {
		
		if (location == null)
			return null;
		
		int x = location.x - this.x;
		int y = location.y - this.y;
		
		if (x != 0)
			x = this.x / Math.abs(this.x);
		
		if (y != 0)
			y = this.y / Math.abs(this.y);
		
		return Direction.valueOf(x, y);
	}
	
	public Relief getRelief() {
		return this.relief;
	}
	
	public void setRelief(Relief relief) {
		this.relief = relief;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
}