package caesar.military;

import java.util.List;

public interface Unit {
	
	UnitParent getParent();
	void setParent(UnitParent parent);
	void perish();
	boolean isFull();
	
	List<Unit> getChildren();
	UnitOrigin getOrigin();
	String getSummary();
	String getFullSummary();
}