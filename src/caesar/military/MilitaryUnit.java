package caesar.military;

public interface MilitaryUnit {
	
	void perish();
	void setParentUnit(MilitaryUnit parentUnit);
	MilitaryUnit engage(MilitaryUnit target, boolean verbose);
}