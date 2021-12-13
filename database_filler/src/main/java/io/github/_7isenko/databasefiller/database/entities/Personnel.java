package io.github._7isenko.databasefiller.database.entities;

/**
 * @author 7isenko
 */
public class Personnel {
    private final String name;
    private final String surname;
    private final int clearanceLevel;
    private final String classification;

    public Personnel(String name, String surname, int clearanceLevel, String classification) {
        this.name = name;
        this.surname = surname;
        this.clearanceLevel = clearanceLevel;
        this.classification = classification;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getClearanceLevel() {
        return clearanceLevel;
    }

    public String getClassification() {
        return classification;
    }
}
