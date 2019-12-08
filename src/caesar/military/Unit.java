package caesar.military;

public interface Unit {
	
	void setParent(UnitParent parent);
	
	void perish();
	
	UnitOrigin getOrigin();
	
	String getSummary();
	
	String getFullSummary();
}