package caesar.utility;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class UserInput {

    public static void awaitInput(@NotNull Scanner scanner) {

        System.out.println("press enter to continue...");
        scanner.nextLine();
    };

    public static boolean isNumeric(String strNum) {

        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }

        return true;
    }
}