package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.Status;
import caesar.military.rome.Contubernium;
import caesar.military.troop.Troop;

public class Roman extends Soldier {
	
	private final int trainingBoost;
	private Status shieldCondition;
	
	public Roman(Troop troop) {
		
		super(troop);
		this.shieldCondition = new Status();
		
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
		
		int blocked = Game.getRandomInt(damageAmount) +
			this.trainingBoost +
			this.shieldCondition.getState() / 10;
		
		this.shieldCondition.decrease(1);
		return blocked;
	};
	
	public static void main(String[] args) {
		System.out.println(new Roman(new Contubernium()));
	}
}