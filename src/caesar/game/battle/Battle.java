package caesar.game.battle;

import caesar.military.UnitOrigin;
import caesar.military.gaul.GaulArmy;
import caesar.military.gaul.Tribe;
import caesar.military.rome.Century;
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
	
	private Map<Soldier, Soldier> battle;
	private final int committedSoldiersCount;
	
	private int romanVictorsCount;
	private int gallicVictorsCount;
	private int survivedSoldiersCount;
	
	public Battle(@NotNull Soldier ...soldiers) {
		
		this.battle = new HashMap<>();
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
		         .forEach(i -> this.battle.put(
		         	romans.get(i), gauls.get(i)
		         ));
	}
	
	@NotNull
	private CompletableFuture<Soldier> initFight(
		@NotNull Map.Entry<Soldier, Soldier> participants,
		boolean verbose
	) {
		
		return CompletableFuture.supplyAsync(() -> participants
			.getKey()
			.engage(participants.getValue(), verbose)
		);
	}
	
	public BattleReport start(boolean verbose) {
		
		this.romanVictorsCount = 0;
		this.gallicVictorsCount = 0;
		this.survivedSoldiersCount = 0;
		
		List<CompletableFuture<Soldier>> battleFutures =
			
			this.battle.entrySet()
			           .stream()
			           .map(entry -> this.initFight(entry, verbose))
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
			
			allBattleVictors.thenApplyAsync(soldiers -> {
				
				this.survivedSoldiersCount = soldiers.size();
				
				this.romanVictorsCount = (int) soldiers
					.stream()
					.filter(UnitOrigin::isRoman)
					.count();
				
				this.gallicVictorsCount =
					this.survivedSoldiersCount - this.romanVictorsCount;
				
				return new BattleReport(
					this.romanVictorsCount,
					this.gallicVictorsCount,
					this.committedSoldiersCount,
					this.survivedSoldiersCount
				);
			});
		
		try {
			return battleReport.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String ...args) {
		
		Troop A = new RomanArmy(1);
		Troop B = new GaulArmy(1);

		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
		BattleReport report = A.engage(B, false);
		Printer.print(report);
		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
	}
}
