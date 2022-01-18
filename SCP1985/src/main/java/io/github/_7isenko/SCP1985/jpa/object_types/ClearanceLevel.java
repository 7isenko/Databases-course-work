package io.github._7isenko.SCP1985.jpa.object_types;

/**
 * @author 7isenko
 */
public enum ClearanceLevel {
    ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6");
    private final String number;

    ClearanceLevel(String s) {
        this.number = s;
    }

    @Override
    public String toString() {
        return number;
    }
}
