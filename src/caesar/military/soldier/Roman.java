package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.rome.Contubernium;
import caesar.military.troop.Troop;

public class Roman extends Soldier {
	
	private final int trainingBoost;
	
	public Roman(Troop troop) {
		
		super(troop);
		this.name = Name.getRandomRoman();
		this.trainingBoost = Game.getRandomInt(5, 15);
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
	
	public static void main(String[] args) {
		System.out.println(new Roman(new Contubernium()));
	}
}