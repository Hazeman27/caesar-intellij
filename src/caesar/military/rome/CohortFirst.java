package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class CohortFirst extends Cohort {
	
	private static final int unitCapacity = 5;
	
	CohortFirst(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[::]");
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new CenturyFirstCohort(this);
	}
}