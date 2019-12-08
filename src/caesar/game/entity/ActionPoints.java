package caesar.game.entity;

public class ActionPoints {
	
	private static final int MAX = 15;
	private static final int MIN = 0;
	private int amount;
	
	public ActionPoints(int amount) {
		this.amount = amount;
	}
	
	public int get() {
		return this.amount;
	}
	
	public void set(int amount) {
		this.amount = Math.min(amount, MAX);
	}
	
	public void add(int amount) {
		
		if (this.amount + amount > MAX)
			this.amount = MAX;
		
		else this.amount += amount;
	}
	
	public void remove(int amount) {
		
		if (this.amount - amount < MIN)
			this.amount = MIN;
		
		else this.amount -= amount;
	}
	
	public boolean atMinimum() {
		return this.amount == MIN;
	}
}