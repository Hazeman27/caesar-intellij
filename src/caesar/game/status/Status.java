package caesar.game.status;

import org.jetbrains.annotations.NotNull;

public class Status {
	
	private final int minState;
	private final int maxState;
	protected int currentState;
	
	public Status(@NotNull StatusType statusType, int currentState) {
		
		this.minState = statusType.getMinState();
		this.maxState = statusType.getMaxState();
		this.currentState = currentState;
	}
	
	public Status(@NotNull StatusType statusType) {
		
		this.minState = statusType.getMinState();
		this.maxState = statusType.getMaxState();
		this.currentState = this.maxState;
	}
	
	public int getCurrentState() {
		return this.currentState;
	}
	
	public boolean atMinState() {
		return this.currentState == this.minState;
	}
	
	public void updateState(int amount) {
		
		if (this.currentState + amount > this.maxState)
			this.currentState = this.maxState;
		
		else if (this.currentState + amount < this.minState)
			this.currentState = this.minState;
		
		else this.currentState += amount;
	}
}
