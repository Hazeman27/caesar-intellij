package caesar.military;

public interface MilitaryUnit {
	
	void perish();
	MilitaryUnit engage(MilitaryUnit target, boolean verbose);
}