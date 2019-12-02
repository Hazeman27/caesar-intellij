package caesar.military;

public interface MilitaryUnit {
	
	void perish();
	void flee();
	void engage(MilitaryUnit target, boolean verbose);
}
