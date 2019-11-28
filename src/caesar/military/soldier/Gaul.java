package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public class Gaul extends Soldier {
	
	private final String name;
	
	public Gaul(Troop troop) {
		super(troop);
		this.name = Name.getRandomGaul();
	}
	
	@Override
	int attackTarget(@NotNull Soldier target) {
		
		int damage = Game.getRandomInt(this.health.getMaxState());
		damage += this.morale.getState() / 10 +
			this.satiety.getState() / 20;
		
		return target.receiveDamage(damage);
	};
	
	@Override
	int block(int damageAmount) {
		return Game.getRandomInt(damageAmount);
	};
	
	@Override
	public String toString() {
		return super.toString() + this.name;
	}
}
