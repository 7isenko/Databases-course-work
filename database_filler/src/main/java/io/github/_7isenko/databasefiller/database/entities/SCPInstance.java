package io.github._7isenko.databasefiller.database.entities;

/**
 * @author 7isenko
 */

public class SCPInstance {
    private final int id;
    private final String name;
    private final String description;
    private final String clazz;

    public SCPInstance(int id, String name, String description, String clazz) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getClazz() {
        return clazz;
    }
}
