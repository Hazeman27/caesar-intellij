package caesar.game.entity;

public class ActionPoints {

    private int amount;

    ActionPoints(int amount) {
        this.amount = amount;
    }

    public ActionPoints() {
        this.amount = 0;
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