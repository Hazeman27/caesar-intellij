package caesar.military;

public interface MilitaryUnit {
	
	void perish();
	void flee();
	MilitaryUnit engage(MilitaryUnit target, boolean verbose);
}