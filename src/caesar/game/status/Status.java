package caesar.game.status;

public class Status {
	
	private final int minState;
	private final int maxState;
	protected int currentState;
	
	public Status(StatusType statusType, int currentState) {
		
		this.minState = statusType.minState;
		this.maxState = statusType.maxState;
		this.currentState = currentState;
	}
	
	public Status(StatusType statusType) {
		
		this.minState = statusType.minState;
		this.maxState = statusType.maxState;
		this.currentState = this.maxState;
	}
	
	public int getCurrentState() {
		return this.currentState;
	}
	
	public int getMinState() {
		return this.minState;
	}
	
	public int getMaxState() {
		return this.maxState;
	}
	
	public boolean atMinState() {
		return this.currentState == this.minState;
	}
	
	public boolean atMaxState() {
		return this.currentState == this.maxState;
	}
	
	public void updateState(int amount) {
		
		if (this.currentState + amount > this.maxState)
			this.currentState = this.maxState;
		
		else if (this.currentState + amount < this.minState)
			this.currentState = this.minState;
		
		else this.currentState += amount;
	}
}
