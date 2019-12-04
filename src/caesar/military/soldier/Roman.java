package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public class Roman extends Soldier {
	
	private final int trainingBoost;
	
	public Roman(Troop troop) {
		
		super(troop);
		this.name = Name.getRandomRoman();
		this.trainingBoost = Game.getRandomInt(5, 15);
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
	
	public static void main(String[] args) {
		System.out.println(new Roman(null));
	}
}