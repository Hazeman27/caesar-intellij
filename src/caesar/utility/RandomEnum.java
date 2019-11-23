package caesar.utility;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class RandomEnum {

    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T get(@NotNull Class<T> enumClass) {

        int index = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[index];
    }
    
    public static <T extends Enum<?>> T get(@NotNull Class<T> enumClass, int minValue) {
        
        int index = random.nextInt(enumClass.getEnumConstants().length - minValue) + minValue;
        return enumClass.getEnumConstants()[index];
    }
}