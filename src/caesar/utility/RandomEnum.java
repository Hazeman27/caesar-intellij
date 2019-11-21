package caesar.utility;

import java.security.SecureRandom;

public class RandomEnum {

    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T get(Class<T> enumClass) {

        int index = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[index];
    }
}