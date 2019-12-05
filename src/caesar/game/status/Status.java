package caesar.game.status;

import org.jetbrains.annotations.Contract;

public class Status {
	
	private final int MAX_STATE;
	private final int MIN_STATE;
	int state;

	@Contract(pure = true)
	public Status() {
		
		MAX_STATE = 100;
		MIN_STATE = 0;
		
		this.state = MAX_STATE;
	}
	
	public Status(int maxState, int minState) {
		
		MAX_STATE = maxState;
		MIN_STATE = minState;
		
		this.state = maxState;
	}
	
	public Status(int maxState, int minState, int state) {
		
		MAX_STATE = maxState;
		MIN_STATE = minState;
		
		this.state = state;
	}
	
	@Contract(pure = true)
	public int getState() {
		return this.state;
	}
	
	@Contract(pure = true)
	public int getMaxState() {
		return this.MAX_STATE;
	}
	
	public boolean isAtMinimum() {
		return this.state == MIN_STATE;
	}
	
	public void increase(int amount) {
		
		if (this.state == MAX_STATE)
			return;
		
		if (this.state + amount > MAX_STATE) {
			this.state = MAX_STATE;
			return;
		}
			
		this.state += amount;
	}
	
	public void decrease(int amount) {
		
		if (this.state == MIN_STATE)
			return;
		
		if (this.state - amount < MIN_STATE) {
			this.state = MIN_STATE;
			return;
		}
			
		this.state -= amount;
	}
}