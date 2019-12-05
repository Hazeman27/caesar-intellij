package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.State;
import caesar.game.status.StateType;
import caesar.military.rome.Contubernium;
import caesar.military.troop.Troop;

public class Roman extends Soldier {
	
	private final int trainingBoost;
	private State shieldCondition;
	
	public Roman(Troop troop) {
		
		super(troop);
		this.shieldCondition = new State(10);
		
		this.name = Name.getRandomRoman();
		this.trainingBoost = Game.getRandomInt(5, 15);
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.state.get(StateType.MORALE).getCurrent() / 5 +
			this.state.get(StateType.SATIETY).getCurrent() / 20 +
			this.trainingBoost;
	};
	
	@Override
	protected int block(int damageAmount) {
		
		int blocked = Game.getRandomInt(damageAmount) +
			this.trainingBoost +
			this.shieldCondition.getCurrent();
		
		this.shieldCondition.modify(-1);
		return blocked;
	};
	
	public static void main(String[] args) {
		System.out.println(new Roman(new Contubernium()));
	}
}