package caesar.game.relief;

import caesar.game.entity.Entity;

import java.util.Map;
import java.util.Optional;

public class ReliefAnalyzer {
	
	private final Map<Direction, Relief> reliefAround;
	
	public ReliefAnalyzer(Entity entity) {
		
		this.reliefAround = entity.getReliefMap().getReliefAround(
			entity.getLocation()
		);
	}
	
	public Destination findResourceRich() {
		
		Optional<Map.Entry<Direction, Relief>> optional =
			this.reliefAround.entrySet()
			                 .stream()
			                 .filter(entry -> Relief.isResourceRich(entry.getValue()))
			                 .findFirst();
		
		if (!optional.isPresent())
			return null;
		
		Map.Entry<Direction, Relief> entry = optional.get();
		
		return new Destination(entry.getKey(), entry.getValue());
	}
}
