package io.github._7isenko.SCP1985.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class StringsHelper {

    public static String genRandomString(int bound) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder buffer = new StringBuilder(bound);
        for (int i = 0; i < bound; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
