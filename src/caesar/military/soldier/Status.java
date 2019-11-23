package caesar.military.soldier;

import org.jetbrains.annotations.Contract;

public class Status {
    
    private final int maxState = 100;
	private int state;

	@Contract(pure = true)
	Status(int state) {
		this.state = state;
	}

	@Contract(pure = true)
	Status() {
		this.state = maxState;
	}

	int get() {
		return this.state;
	}
	
	void set(int value) {
		this.state = value;
	}

	public void increase(int amount) {
		
		if (this.state == maxState)
			return;
		
		if (this.state + amount >= maxState) {
			
			this.state = maxState;
			return;
		}
			
		this.state += amount;
	}
	
	public void decrease(int amount) {

		int minState = 0;
		if (this.state == minState)
			return;
		
		if (this.state - amount <= minState) {
			
			this.state = minState;
			return;
		}
			
		this.state -= amount;
	}
}
