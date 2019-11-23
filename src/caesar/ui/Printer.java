package caesar.ui;

import caesar.game.entity.Location;
import caesar.game.map.Map;
import caesar.game.map.Relief;
import org.jetbrains.annotations.NotNull;

public class Printer {
	
    @NotNull
    public static String getBorder(@NotNull String title, int maxLength) {

        int i;
        int lengthDifference = maxLength + 5 - title.length();

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

    @NotNull
    public static String getBorder(int maxLength) {

        StringBuilder border = new StringBuilder("+");

        for (int i = 0; i < maxLength + 5; i++)
            border.append('-');

        border.append("+\n");
        return border.toString();
    }

    @NotNull
    public static String getEmptyLine(int maxLength) {
    	
    	StringBuilder line = new StringBuilder("|");

        for (int i = 0; i < maxLength + 5; i++)
            line.append(' ');

        line.append("|\n");
        return line.toString();
    }

    @NotNull
    public static String getFillingSpaces(@NotNull String string, int maxLength) {
    	
    	StringBuilder spaces = new StringBuilder();
    	
        for (int i = 0; i < maxLength - string.length(); i++)
            spaces.append(' ');

        spaces.append(" |\n");
        return spaces.toString();
    }
    
    public static void printRelief(@NotNull Map map, Location location) {
    	
    	Relief[] relief = map.getReliefAround(location);

        System.out.println("North       - " + relief[0] + ";");
        System.out.println("Northwest   - " + relief[1] + ";");
        System.out.println("Northeast   - " + relief[2] + ";");
        System.out.println("West        - " + relief[3] + ";");
        System.out.println("East        - " + relief[4] + ";");
        System.out.println("South       - " + relief[5] + ";");
        System.out.println("Southwest   - " + relief[6] + ";");
        System.out.println("Southeast   - " + relief[7] + ".");

        System.out.println("----------------");
        System.out.println("  NW   N    NE  ");
        System.out.println(
        	"  " + relief[1].toString(true) + "   " +
            relief[0].toString(true) + "   " +
            relief[2].toString(true) + "  "
        );
        
        System.out.println(
            "W " + relief[3].toString(true) + "   " +
            "P " + "   " +
            relief[4].toString(true) + " E"
        );
        
        System.out.println(
            "  " + relief[6].toString(true) + "   " +
            relief[5].toString(true) + "   " +
            relief[7].toString(true) + "  "
        );
        
        System.out.println("  SW   S    SE  ");
        System.out.println("----------------");
    }
    
    public static void printBorder(String title, int maxLength) {
    	System.out.println(getBorder(title, maxLength));
    }
    
    public static void printBorder(int maxLength) {
    	System.out.println(getBorder(maxLength));
    }
    
    public static void printEmptyLine(int maxLength) {
    	System.out.println(getEmptyLine(maxLength));
    }
    
    public static void printFillingSpaces(String string, int maxLength) {
    	System.out.println(getFillingSpaces(string, maxLength));
    }
    
    public static <T> void print(@NotNull T string, boolean addNewLine) {
    	System.out.print(string.toString() + (addNewLine ? "\n" : ""));
    }
    
    public static <T> void print(@NotNull T string) {
        System.out.println(string.toString());
    }
}
