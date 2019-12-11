package caesar.ui;

import caesar.game.relief.Direction;
import caesar.game.relief.Location;
import caesar.game.relief.Relief;
import caesar.game.relief.ReliefMap;

import java.util.Map;

public class Printer {
	
	public static String getBorder(
		String title,
		int maxLength,
		int whiteSpace
	) {
		
		int i;
		int lengthDifference = maxLength + whiteSpace - title.length();
		
		int leftFill = lengthDifference / 2;
		int rightFill = lengthDifference - leftFill;
		
		StringBuilder border = new StringBuilder("+");
		
		for (i = 0; i < leftFill; i++)
			border.append('-');
		
		border.append(title);
		
		for (i = 0; i < rightFill; i++)
			border.append('-');
		
		border.append("+\n");
		return border.toString();
	}
	
	public static String getBorder(int maxLength, int whiteSpace) {
		
		StringBuilder border = new StringBuilder("+");
		
		for (int i = 0; i < maxLength + whiteSpace; i++)
			border.append('-');
		
		border.append("+\n");
		return border.toString();
	}
	
	public static String getEmptyLine(int maxLength, int whiteSpace) {
		
		StringBuilder line = new StringBuilder("|");
		
		for (int i = 0; i < maxLength + whiteSpace; i++)
			line.append(' ');
		
		line.append("|\n");
		return line.toString();
	}
	
	public static String getFillingSpaces(
		String string,
		int maxLength
	) {
		
		StringBuilder spaces = new StringBuilder();
		
		for (int i = 0; i < maxLength - string.length(); i++)
			spaces.append(' ');
		
		spaces.append(" |\n");
		return spaces.toString();
	}
	
	public static void printRelief(ReliefMap reliefMap, Location location) {
		
		Map<Direction, Relief> reliefAroundMap =
			reliefMap.getReliefAround(location);
		
		System.out.println();
		System.out.println("North       - " + reliefAroundMap.get(Direction.NORTH) + ";");
		System.out.println("Northwest   - " + reliefAroundMap.get(Direction.NORTHWEST) + ";");
		System.out.println("Northeast   - " + reliefAroundMap.get(Direction.NORTHEAST) + ";");
		System.out.println("West        - " + reliefAroundMap.get(Direction.WEST) + ";");
		System.out.println("East        - " + reliefAroundMap.get(Direction.EAST) + ";");
		System.out.println("South       - " + reliefAroundMap.get(Direction.SOUTH) + ";");
		System.out.println("Southwest   - " + reliefAroundMap.get(Direction.SOUTHWEST) + ";");
		System.out.println("Southeast   - " + reliefAroundMap.get(Direction.SOUTHEAST) + ".");
		
		System.out.println("----------------");
		System.out.println("  NW   N    NE  ");
		System.out.println(
			"  " + reliefAroundMap.get(Direction.NORTHWEST).toString(true) + "   " +
				reliefAroundMap.get(Direction.NORTH).toString(true) + "   " +
				reliefAroundMap.get(Direction.NORTHEAST).toString(true) + "  "
		);
		
		System.out.println(
			"W " + reliefAroundMap.get(Direction.WEST).toString(true) + "   " +
				"P " + "   " +
				reliefAroundMap.get(Direction.EAST).toString(true) + " E"
		);
		
		System.out.println(
			"  " + reliefAroundMap.get(Direction.SOUTHWEST).toString(true) + "   " +
				reliefAroundMap.get(Direction.SOUTH).toString(true) + "   " +
				reliefAroundMap.get(Direction.SOUTHEAST).toString(true) + "  "
		);
		
		System.out.println("  SW   S    SE  ");
		System.out.println("----------------");
	}
	
	public static <T> void print(T object, boolean addNewLine) {
		System.out.print(object.toString() + (addNewLine ? "\n" : ""));
	}
	
	public static <T> void print(T object) {
		System.out.println(object.toString());
	}
	
	public static void printEmptyLine() {
		System.out.println();
	}
}