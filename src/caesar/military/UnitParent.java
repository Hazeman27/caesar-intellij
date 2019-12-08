package caesar.military;

import caesar.military.soldier.Soldier;

import java.util.List;

public interface UnitParent {
	
	List<Unit> getChildren();
	
	List<Soldier> getOfficers();
	
	void addChild(Unit child);
	
	void removeChild(Unit child);
	
	void removeAllChildren(List<Unit> children);
	
	void addOfficer(Soldier officer);
	
	void removeOfficer(Soldier officer);
	
	boolean isEmpty();
	
	boolean isFull();
}
