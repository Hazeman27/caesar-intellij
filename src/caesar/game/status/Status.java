package caesar.game.status;

import org.jetbrains.annotations.Contract;

public class Status {
	
	private static final int MAX_STATE = 100;
	private static final int MIN_STATE = 0;
	private int state;

	@Contract(pure = true)
	public Status() {
		this.state = MAX_STATE;
	}
	
	@Contract(pure = true)
	public int getState() {
		return this.state;
	}
	
	@Contract(pure = true)
	public static int getMaxState() {
		return MAX_STATE;
	}
	
	@Contract(pure = true)
	public static int getMinState() {
		return MIN_STATE;
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