package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public class Officer extends Soldier {
	
	private final Rank rank;
	private final int trainingBoost;
	
	public Officer(Rank rank, Troop troop) {
		
		super(troop);
		this.name = Name.getRandomRoman();
		this.trainingBoost = Game.getRandomInt(15, 35);
		this.rank = rank;
	}
	
	@Override
	public void perish() {
		
		this.troop.removeOfficer();
		this.troop = null;
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
}