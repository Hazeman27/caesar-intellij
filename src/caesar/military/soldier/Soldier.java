package caesar.military.soldier;

import caesar.military.troop.Troop;

public class Soldier {

    private Status morale;
    private Status energy;
    private Status satiety;
    private Status hygiene;
    private Status health;

    protected Troop troop;

    public Soldier(
        Status morale,
        Status energy,
        Status satiety,
        Status hygiene,
        Status health,
        Troop troop
    ) {

        this.morale = morale;
        this.energy = energy;
        this.satiety = satiety;
        this.hygiene = hygiene;
        this.health = health;

        this.troop = troop;
    }

    public Soldier(Status[] status, Troop troop) {

        this.morale = status[0];
        this.energy = status[1];
        this.satiety = status[2];
        this.hygiene = status[3];
        this.health = status[4];

        this.troop = troop;
    }

    public Soldier(Troop troop) {

        this.morale = new Status(50);
        this.energy = new Status(50);
        this.satiety = new Status(50);
        this.hygiene = new Status(50);
        this.health = new Status();

        this.troop = troop;
    }

    public void perish() {
        this.troop.removeSoldier(this);
    }

    public final int getMorale() {
        return this.morale.get();
    }

    public final int getEnergy() {
        return this.energy.get();
    }

    public final int getSatiety() {
        return this.satiety.get();
    }

    public final int getHygiene() {
        return this.hygiene.get();
    }

    public final int getHealth() {
        return this.health.get();
    }

    public final void setMorale(int value) {
        this.morale.set(value);
    }

    public final void setEnergy(int value) {
        this.energy.set(value);
    }

    public final void setSatiety(int value) {
        this.satiety.set(value);
    }

    public final void setHygiene(int value) {
        this.hygiene.set(value);
    }

    public final void setHealth(int value) {
        this.health.set(value);
    }

    public void flee() {

    }

    public void eat() {

    }

    public void getSick() {

    }

    public void heal() {

    }

	public boolean attack() {
        return false;
    };

	public boolean block() {
        return false;
    };

	public boolean recieveOrder() {
        return false;
    };

	public boolean acceptOrder() {
        return false;
    };

    public boolean rejectOrder() {
        return false;
    };
}
