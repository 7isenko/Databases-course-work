package io.github._7isenko.databasefiller.database.entities;

import io.github._7isenko.databasefiller.misc.MathHelper;

/**
 * @author 7isenko
 */
public class Location {
    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return MathHelper.roundAvoid(latitude, 6);
    }

    public double getLongitude() {
        return MathHelper.roundAvoid(longitude, 6);
    }
}
