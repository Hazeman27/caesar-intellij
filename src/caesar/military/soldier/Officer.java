package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public class Officer extends Soldier {
	
	private final Rank rank;
	private final String name;
	private final int trainingBoost = 20;
	
	public Officer(Rank rank, Troop troop) {
		
		super(troop);
		this.name = Name.getRandomRoman();
		this.rank = rank;
	}
	
	@Override
	public void perish() {
		this.troop.removeOfficer();
	}
	
	@Override
	public void flee() {
		this.troop.removeOfficer();
	}
	
	@Override
	int attackTarget(@NotNull Soldier target) {
		
		int damage = Game.getRandomInt(this.health.getMaxState());
		damage += this.morale.getState() / 5 +
			this.satiety.getState() / 20 +
			this.trainingBoost;
		
		return target.receiveDamage(damage);
	};
	
	@Override
	int block(int damageAmount) {
		return Game.getRandomInt(damageAmount) +
			this.trainingBoost;
	};
	
	@Override
	public String toString() {
		return super.toString() + this.rank + " " + this.name;
	}
}