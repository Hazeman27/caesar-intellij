package caesar.military;

public interface Unit {
	
	void die();
	void setParentUnit(Unit parentUnit);
	
	String getSummary();
	String getFullSummary();
	
	UnitOrigin getOrigin();
}