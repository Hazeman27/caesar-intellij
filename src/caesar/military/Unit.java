package caesar.military;

import org.jetbrains.annotations.NotNull;

public interface Unit {
	
	void setParent(UnitParent parent);
	
	void perish();
	
	UnitOrigin getOrigin();
	
	@NotNull String getSummary();
	
	String getFullSummary();
}