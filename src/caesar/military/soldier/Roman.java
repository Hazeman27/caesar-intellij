package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;

public class Roman extends Soldier {
	
	private static final int[] TRAINING_BOOST_RANGE = new int[]{5, 15};
	private static final int DAMAGE_SATIETY_RELATION = 20;
	
	private final int trainingBoost;
	
	public Roman(Rank rank, UnitParent parent) {
		
		super(rank, parent, UnitOrigin.ROME);
		
		this.name = Name.getRandomRoman();
		this.trainingBoost = Game.getRandomInt(
			TRAINING_BOOST_RANGE
		);
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