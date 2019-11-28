package caesar.military;

public interface MilitaryUnit {
	
	boolean isAlive();
	void perish();
	void flee();
	void engage(MilitaryUnit target, boolean verbose);
}
