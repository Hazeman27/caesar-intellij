package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Soldier {
    
    private Status morale;
    private Status satiety;
    private Status health;
    private Troop troop;
    private String name;
    private boolean alive;

    @Contract(pure = true)
    public Soldier(Troop troop) {

        this.morale = Status.MORALE;
        this.satiety = Status.SATIETY;
        this.health = Status.HEALTH;
        this.troop = troop;
        
        this.name = Name.getRandom();
        this.alive = true;
    }
    
    public boolean isAlive() {
        return this.alive;
    }

    private void perish() {
    
        this.troop.removeSoldier(this);
        this.alive = false;
    }
    
    private int receiveDamage(int damageAmount) {
     
    	damageAmount -= this.block(damageAmount);
    	
        this.health.decrease(damageAmount);
        
        if (this.health.getState() == this.health.getMinState())
            this.perish();
        
        return damageAmount;
	}

	private int block(int damageAmount) {
        return Game.getRandomInt(damageAmount);
    };
    
    private int attackSoldier(Soldier soldier) {
        
        if (soldier == null)
            return 0;
        
        int damage = Game.getRandomInt(Status.HEALTH.getMaxState());
        damage += this.morale.getState() / 10 + this.satiety.getState() / 20;
	
		return soldier.receiveDamage(damage);
	};
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static void engage(@NotNull Soldier A, Soldier B, boolean verbose) {
		
		if (verbose) {
			Printer.print(A + " is engaging with " + B);
			
			while (A.isAlive() && B.isAlive()) {
				
				Printer.print(A + " dealt " + A.attackSoldier(B) + " damage to " + B);
				Printer.print(B + " dealt " + B.attackSoldier(A) + " damage to " + A);
			}
			
			if (!B.isAlive())
				Printer.print(A + " has killed " + B);
			
			else if (!A.isAlive())
				Printer.print(B + " has killed " + A);
		}
		
		else {
			
			while (A.isAlive() && B.isAlive()) {
				A.attackSoldier(B);
				B.attackSoldier(A);
			}
		}
    }
    
    public static void main(String[] args) {
        
        Troop A = new Troop(TroopType.CONTUBERNIUM);
        Troop B = new Troop(TroopType.GAULS_GROUP);
    
        System.out.println(Troop.countSoldiers(A, false));
        System.out.println(Troop.countSoldiers(B, false));
        
        Soldier.engage(A.getSoldiers().get(0), B.getSoldiers().get(0), false);
    
        System.out.println(Troop.countSoldiers(A, false));
        System.out.println(Troop.countSoldiers(B, false));
    }
}
