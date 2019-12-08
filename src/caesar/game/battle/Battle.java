package caesar.game.battle;

import caesar.military.UnitOrigin;
import caesar.military.gaul.GaulArmy;
import caesar.military.rome.RomanArmy;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Battle {
	
	private final Map<Soldier, Soldier> battleParticipants;
	private final int committedSoldiersCount;
	private final boolean verbose;
	
	public Battle(boolean verbose, @NotNull Soldier ...soldiers) {
		
		this.verbose = verbose;
		this.battleParticipants = new HashMap<>();
		this.committedSoldiersCount = soldiers.length;
		
		List<Soldier> romans = Arrays
			.stream(soldiers)
			.filter(UnitOrigin::isRoman)
			.collect(Collectors.toList());
		
		List<Soldier> gauls = Arrays
			.stream(soldiers)
			.filter(UnitOrigin::isGallic)
			.collect(Collectors.toList());
		
		int minSize = Math.min(romans.size(), gauls.size());
		
		IntStream.range(0, minSize)
		         .forEach(i -> this.battleParticipants.put(
		         	romans.get(i), gauls.get(i)
		         ));
	}
	
	@NotNull
	private CompletableFuture<Soldier> initFight(
		@NotNull Map.Entry<Soldier, Soldier> participants
	) {
		
		return CompletableFuture.supplyAsync(() -> participants
			.getKey()
			.engage(participants.getValue(), this.verbose)
		);
	}
	
	@NotNull
	private BattleReport getReport(@NotNull List<Soldier> soldiers) {
		
		int survivedSoldiersCount = soldiers.size();
		
		int romanVictorsCount = (int) soldiers
			.stream()
			.filter(UnitOrigin::isRoman)
			.count();
		
		int gallicVictorsCount = survivedSoldiersCount - romanVictorsCount;
		
		return new BattleReport(
			romanVictorsCount,
			gallicVictorsCount,
			this.committedSoldiersCount,
			survivedSoldiersCount
		);
	}
	
	public BattleReport start() {
		
		List<CompletableFuture<Soldier>> battleFutures =
			this.battleParticipants
				.entrySet()
				.stream()
				.map(this::initFight)
				.collect(Collectors.toList());
		
		CompletableFuture<Void> allBattleFutures =
			CompletableFuture.allOf(
				battleFutures.toArray(new CompletableFuture[0])
			);
		
		CompletableFuture<List<Soldier>> allBattleVictors =
			allBattleFutures.thenApplyAsync(v -> battleFutures
				.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList())
			);
		
		CompletableFuture<BattleReport> battleReport =
			allBattleVictors.thenApplyAsync(this::getReport);
		
		try {
			return battleReport.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		Troop A = new RomanArmy(5);
		Troop B = new GaulArmy(5);
		
		Printer.print(A.getSummary());
		Printer.print(B.getSummary());
		
		BattleReport report = A.engage(B, false);
		Printer.print(report);
		
		Printer.print(B.getSummary());
		Printer.print(A.getSummary());
		
		report = A.engage(B, false);
		Printer.print(report);
		
		report = A.engage(B, false);
		Printer.print(report);
		
		report = A.engage(B, false);
		Printer.print(report);
		
		report = A.engage(B, false);
		Printer.print(report);
		
		Printer.print(B.getSummary());
		Printer.print(A.getSummary());
	}
}
