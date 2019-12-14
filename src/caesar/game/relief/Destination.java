package caesar.game.relief;

public class Destination {
	
	private final Direction direction;
	private final Relief relief;
	
	public Destination(Direction direction, Relief relief) {
		
		this.direction = direction;
		this.relief = relief;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Relief getRelief() {
		return relief;
	}
}
