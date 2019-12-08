package caesar.military.officer;

import caesar.game.Game;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.soldier.Soldier;

public abstract class Officer extends Soldier {
	
	private static final int[] TRAINING_BOOST_RANGE = new int[] {5, 10};
	private static final int DAMAGE_SATIETY_RELATION = 30;
	
	private final int trainingBoost;
	
	Officer(UnitParent parent, Rank rank, UnitOrigin origin) {
		
		super(parent, rank, origin);
		this.trainingBoost = Game.getRandomInt(
			rank.getIndex() * TRAINING_BOOST_RANGE[0],
			rank.getIndex() * TRAINING_BOOST_RANGE[1]
		);
	}
	
	@Override
	public void perish() {
		this.parent.removeOfficer(this);
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.getMorale()
		           .getCurrentState() +
			this.getSatiety()
			    .getCurrentState() / DAMAGE_SATIETY_RELATION +
			this.trainingBoost;
	}
	
	@Override
	protected int block(int damage) {
		return Game.getRandomInt(damage) +
			this.trainingBoost;
	}
}