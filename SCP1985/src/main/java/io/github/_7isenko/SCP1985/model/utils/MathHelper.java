package io.github._7isenko.SCP1985.model.utils;

/**
 * @author 7isenko
 */
public class MathHelper {

    /**
     * https://www.internet-technologies.ru/articles/kak-v-java-okruglit-chislo-do-n-znakov-posle-zapyatoy.html
     */
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
