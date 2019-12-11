package caesar.military;

public enum UnitOrigin {
	
	ROME, GAUL;
	
	public static boolean isRoman(Unit unit) {
		return unit.getOrigin() == ROME;
	}
	
	public static boolean isGallic(Unit unit) {
		return unit.getOrigin() == GAUL;
	}
}
