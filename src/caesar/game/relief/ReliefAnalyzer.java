package caesar.game.relief;

import caesar.game.entity.Entity;
import java.util.Map;

public class ReliefAnalyzer {
	
	private final Map<Direction, Relief> reliefAround;
	
	public ReliefAnalyzer(Entity entity) {
		
		this.reliefAround = entity.getReliefMap().getReliefAround(
			entity.getLocation()
		);
	}
	
	public Map.Entry<Direction, Relief> findResourceRich() {
		
		return this.reliefAround.entrySet()
		                        .stream()
		                        .filter(entry -> Relief.isResourceRich(entry.getValue()))
		                        .findFirst()
		                        .orElse(null);
	}
}
