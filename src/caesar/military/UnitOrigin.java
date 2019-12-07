package caesar.military;

import org.jetbrains.annotations.NotNull;

public enum UnitOrigin {
	
	ROME, GAUL;
	
	public static boolean isRoman(@NotNull Unit unit) {
		return unit.getOrigin() == ROME;
	}
	
	public static boolean isGallic(@NotNull Unit unit) {
		return unit.getOrigin() == GAUL;
	}
}
