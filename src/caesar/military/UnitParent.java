package caesar.military;

import java.util.List;

public interface UnitParent {
	
	void addChild(Unit child);
	void removeChild(Unit child);
	void removeAll(List<Unit> children);
}
