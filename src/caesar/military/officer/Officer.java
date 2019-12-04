package caesar.military.officer;

import caesar.game.Game;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public abstract class Officer extends Soldier {
	
	private final int trainingBoost;
	
	public Officer(@NotNull Rank rank, Troop troop) {
		
		super(troop);
		this.trainingBoost = Game.getRandomInt(
			rank.getIndex() * 5,
			rank.getIndex() * 10
		);
	}
	
	@Override
	public void perish() {
		this.troop.removeOfficer();
		this.troop = null;
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.morale.getState() / 5 +
			this.satiety.getState() / 20 +
			this.trainingBoost;
	};
	
	@Override
	protected int block(int damageAmount) {
		return Game.getRandomInt(damageAmount) +
			this.trainingBoost;
	};
}