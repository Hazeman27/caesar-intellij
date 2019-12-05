package caesar.military.officer;

import caesar.game.Game;
import caesar.game.status.StateType;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public abstract class Officer extends Soldier {
	
	private final int trainingBoost;
	
	Officer(@NotNull Rank rank, Troop troop) {
		
		super(troop);
		this.trainingBoost = Game.getRandomInt(
			rank.getIndex() * 5,
			rank.getIndex() * 10
		);
	}
	
	@Override
	public void perish() {
		this.parentUnit.removeOfficer();
		this.parentUnit = null;
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.state.get(StateType.MORALE).getCurrent() / 5 +
			this.state.get(StateType.SATIETY).getCurrent() / 20 +
			this.trainingBoost;
	};
	
	@Override
	protected int block(int damageAmount) {
		return Game.getRandomInt(damageAmount) +
			this.trainingBoost;
	};
}