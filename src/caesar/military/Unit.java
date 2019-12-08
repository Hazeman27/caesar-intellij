package caesar.military;

import java.util.List;

public interface Unit {
	
	void setParent(UnitParent parent);
	
	void perish();
	
	List<Unit> getChildren();
	
	UnitOrigin getOrigin();
	
	String getSummary();
	
	String getFullSummary();
}