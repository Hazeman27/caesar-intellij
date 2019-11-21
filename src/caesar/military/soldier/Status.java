package caesar.military.soldier;

public class Status {
    
    private final int maxState = 100;
	private final int minState = 0;
	private int state;

	public Status(int state) {
		this.state = state;
	}

	public Status() {
		this.state = maxState;
	}

	public int get() {
		return this.state;
	}
	
	public void set(int value) {
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
		
		if (this.state == minState)
			return;
		
		if (this.state - amount <= minState) {
			
			this.state = minState;
			return;
		}
			
		this.state -= amount;
	}
}
