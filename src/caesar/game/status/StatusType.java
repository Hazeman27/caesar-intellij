package caesar.game.status;

public enum StatusType {
	
	HEALTH(0, 100),
	MORALE(-10, 10),
	SATIETY(-100, 100),
	WOOD_RESOURCE(0, 65536),
	FOOD_RESOURCE(0, 256372);
	
	final int minState;
	final int maxState;
	
	StatusType(int minState, int maxState) {
		this.minState = minState;
		this.maxState = maxState;
	}
}
