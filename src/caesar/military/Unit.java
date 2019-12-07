package caesar.military;

public interface Unit {
	
	void die();
	void setParentUnit(Unit parentUnit);
	
	UnitOrigin getOrigin();
	String getSummary();
	String getFullSummary();
}