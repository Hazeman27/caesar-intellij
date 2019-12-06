package caesar.military;

public interface MilitaryUnit {
	
	void die();
	void setParentUnit(MilitaryUnit parentUnit);
	
	String getSummary();
	String getFullSummary();
	
	MilitaryUnit engage(MilitaryUnit target, boolean verbose);
}