package io.github._7isenko.SCP1985.jpa.object_types;

/**
 * @author 7isenko
 */
@SuppressWarnings("ALL")
public enum ObjectCLass {
    Безопасный, Кетер, Евклид, Таумиэль, Аполлион, Архонт, Нейтрализованный, Обоснованный, Неприменимо;

    public static boolean hasValue(String s) {
        try {
            valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
