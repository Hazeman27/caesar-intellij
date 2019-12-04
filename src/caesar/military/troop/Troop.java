package caesar.military.troop;

import caesar.game.engagement.EngagementController;
import caesar.military.MilitaryUnit;
import caesar.military.rome.RomanArmy;
import caesar.military.soldier.Soldier;
import caesar.ui.Message;
import caesar.ui.Printer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Troop implements MilitaryUnit {
	
	private final String symbol;
	
	private List<MilitaryUnit> units;
	private Troop parentTroop;
	
	protected final int unitsAmount;
	protected Soldier officer;
	
	protected Troop(Troop parentTroop, int unitsAmount, String symbol) {
		
		this.unitsAmount = unitsAmount;
		this.units = this.initUnits();
		this.parentTroop = parentTroop;
		this.symbol = symbol;
	}
	
	protected Troop(int unitsAmount, String symbol) {
		
		this.unitsAmount = unitsAmount;
		this.units = this.initUnits();
		this.symbol = symbol;
	}
	
	protected abstract List<MilitaryUnit> initUnits();
	
	@Override
	public void perish() {
		
		if (this.parentTroop != null) {
			
			this.parentTroop.units.remove(this);
			this.parentTroop = null;
		}
		
		else Printer.print(Message.CANT_REMOVE_TROOP);
	}
	
	@Override
	public MilitaryUnit engage(MilitaryUnit target, boolean verbose) {
		
		if (target == null)
			return this;
		
		Troop targetTroop = (Troop) target;
		
		new EngagementController(this.officer, targetTroop.officer)
			.start(verbose);
		
		new EngagementController(this.units, targetTroop.units)
			.start(verbose);
		
		return this;
	}
	
	public void removeSoldier(@NotNull Soldier soldier) {
		
		this.units.remove(soldier);
		
		if (this.units.size() == 0 && this.officer == null)
			this.perish();
	}
	
	public void removeOfficer() {
		
		this.officer.setTroop(null);
		
		if (this.units.size() == 0)
			this.perish();
	}
	
	@Override
	public String toString() {
		return super.toString() +
			"[" +
			this.symbol +
			"] (" +
			(this.units.size()) +
			")";
	}
	
	@Contract(pure = true)
	public static int countSoldiers(MilitaryUnit unit) {
		
		if (unit instanceof Soldier)
			return 1;
		
		int total = 0;
		Troop troop = (Troop) unit;
		
		for (MilitaryUnit u:  troop.units)
			total += countSoldiers(u);
		
		return total + (troop.officer == null ? 0 : 1);
	}
	
	@NotNull
	public static String getSummary(@NotNull Troop troop) {
		
		return troop.getClass().getName() +
			":\n" +
			"-> Officer: " +
			troop.officer +
			"\n" +
			"-> Soldiers count: " +
			countSoldiers(troop) +
			"\n" +
			"-> Troops count: " +
			troop.units.size() +
			"\n";
	}
	
	@NotNull
	public static String getFullSummary(Troop troop) {
		
		StringBuilder fullSummary = new StringBuilder(getSummary(troop));
		
		IntStream.range(0, troop.units.size())
		         .forEach(i -> {
			         Troop current = (Troop) troop.units.get(i);
			         fullSummary.append(getSummary(current));
		         });
		
		return fullSummary.toString();
	}
	
	public static void main(String[] args) {
		
		Troop troop = new RomanArmy(10);
		Printer.print(Troop.getFullSummary(troop));
	}
}