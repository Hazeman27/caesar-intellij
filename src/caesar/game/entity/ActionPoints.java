package caesar.game.entity;

import org.jetbrains.annotations.Contract;

public class ActionPoints {

    private int amount;

    @Contract(pure = true)
	public ActionPoints(int amount) {
        this.amount = amount;
    }

    public int get() {
        return this.amount;
    }

    public void set(int amount) {
        this.amount = amount;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    public void remove(int amount) {
        this.amount -= amount;
    }
}