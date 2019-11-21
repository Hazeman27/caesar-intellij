package caesar.ui;

import caesar.game.map.Map;
import caesar.game.map.Relief;

public class Printer {
	
    public static String getBorder(String title, int maxLength) {

        int i;
        int lengthDifference = maxLength + 5 - title.length();

        int leftFill = lengthDifference / 2;
        int rightFill = lengthDifference - leftFill;
        
        String border = "+";

        for (i = 0; i < leftFill; i++)
            border += '-';

        border += title;
        
        for (i = 0; i < rightFill; i++)
        	border += '-';

        border += "+\n";
        return border;
    }

    public static String getBorder(int maxLength) {

        String border = "+";

        for (int i = 0; i < maxLength + 5; i++)
            border += '-';

        border += "+\n";
        return border;
    }

    public static String getEmptyLine(int maxLength) {
    	
    	String line = "|";

        for (int i = 0; i < maxLength + 5; i++)
            line += ' ';

        line += "|\n";
        return line;
    }

    public static String getFillingSpaces(String string, int maxLength) {
    	
    	String spaces = "";
    	
        for (int i = 0; i < maxLength - string.length(); i++)
            spaces += ' ';

        spaces += " |\n";
        return spaces;
    }
    
    public static void printRelief(Map map, int[] coordinates) {
    	
    	Relief[] relief = map.getReliefAround(coordinates);

        System.out.println("north       - " + relief[0] + ";");
        System.out.println("northwest   - " + relief[1] + ";");
        System.out.println("northeast   - " + relief[2] + ";");
        System.out.println("west        - " + relief[3] + ";");
        System.out.println("east        - " + relief[4] + ";");
        System.out.println("south       - " + relief[5] + ";");
        System.out.println("southwest   - " + relief[6] + ";");
        System.out.println("southeast   - " + relief[7] + ".");

        System.out.println("----------------");
        System.out.println("  NW   N    NE  ");
        System.out.println(
        	"  " + relief[1] + "   " +
            relief[0] + "   " +
            relief[2] + "  " 
        );
        System.out.println(
            "W " + relief[3] + "   " +
            "P " + "   " +
            relief[4] + " E" 
        );
        System.out.println(
            "  " + relief[6] + "   " +
            relief[5] + "   " +
            relief[7] + "  " 
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
    
    public static <T> void print(T string) {
    	System.out.println(string.toString());
    }
}
