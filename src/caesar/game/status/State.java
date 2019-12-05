package caesar.game.status;

public class State implements StatusModifier {
	
	private final int max;
	private final int min = 0;
	int current;
	
	public State() {
		this.max = 100;
		this.current = max;
	}
	
	public State(int max) {
		this.max = max;
		this.current = max;
	}
	
	public State(int max, int current) {
		this.max = max;
		this.current = current;
	}
	
	public int getCurrent() {
		return this.current;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public boolean isAtMinimum() {
		return this.current == min;
	}
	
	@Override
	public void modify(int amount) {
		
		if (this.current + amount > max) {
			this.current = max;
			return;
		}
		
		if (this.current + amount < min) {
			this.current = min;
			return;
		}
		
		this.current += amount;
	}
}