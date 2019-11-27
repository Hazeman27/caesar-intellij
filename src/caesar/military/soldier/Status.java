package caesar.military.soldier;

import org.jetbrains.annotations.Contract;

public enum Status {
	
	MORALE(100, 0),
	SATIETY(100, 0),
	HEALTH(100, 0);
	
    private final int maxState;
    private final int minState;
	private int state;

	@Contract(pure = true)
	Status(int maxState, int minState) {
		
		this.maxState = maxState;
		this.minState = minState;
		this.state = maxState;
	}
	
	@Contract(pure = true)
	int getMaxState() {
		return maxState;
	}
	
	@Contract(pure = true)
	int getMinState() {
		return minState;
	}
	
	@Contract(pure = true)
	int getState() {
		return state;
	}
	
	void setState(int state) {
		this.state = state;
	}
	
	void increase(int amount) {
		
		if (this.state == maxState)
			return;
		
		if (this.state + amount >= maxState) {
			
			this.state = maxState;
			return;
		}
			
		this.state += amount;
	}
	
	void decrease(int amount) {
		
		if (this.state - amount <= this.minState) {
			
			this.state = this.minState;
			return;
		}
			
		this.state -= amount;
	}
}
