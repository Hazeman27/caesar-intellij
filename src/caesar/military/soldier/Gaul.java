package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;

public class Gaul extends Soldier {
	
	private static final int DAMAGE_SATIETY_RELATION = 10;
	
	public Gaul(UnitParent parent, Rank rank) {
		
		super(parent, rank, UnitOrigin.GAUL);
		this.name = Name.getRandomGallic();
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.getMorale().getCurrentState() +
			this.getSatiety()
			    .getCurrentState() / DAMAGE_SATIETY_RELATION;
	}
	
	@Override
	protected int block(int damage) {
		return Game.getRandomInt(damage);
	}
}