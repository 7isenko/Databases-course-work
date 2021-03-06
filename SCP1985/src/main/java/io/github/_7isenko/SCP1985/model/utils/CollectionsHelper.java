package io.github._7isenko.SCP1985.model.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class CollectionsHelper {
    /**
     * https://stackoverflow.com/a/21092353/12787638
     */
    public static <T> T getRandomElement(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for (T t : coll) if (--num < 0) return t;
        throw new AssertionError();
    }

    public static <T> T getFirstElement(Collection<T> coll) {
        for (T t : coll) return t;
        throw new AssertionError();
    }

    public static ArrayList<String> getRandomStringList(int amount, int bound) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            strings.add(StringsHelper.genRandomString(bound));
        }
        return strings;
    }

    public static ArrayList<Double> getRandomList(int amount, double min, double max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        ArrayList<Double> doubles = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            doubles.add(random.nextDouble(min, max));
        }
        return doubles;
    }
}
