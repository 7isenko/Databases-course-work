package io.github._7isenko.SCP1985.init;

import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import io.github._7isenko.SCP1985.utils.CollectionsHelper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Component
public class RandomEntitiesGenerator {



    public ArrayList<LocationEntity> getRandomLocations(int amount) {
        ArrayList<Double> latitudes = CollectionsHelper.getRandomList(amount, -90D, 90D);
        ArrayList<Double> longitudes = CollectionsHelper.getRandomList(amount, -180D, 180D);

        ArrayList<LocationEntity> locations = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            locations.add(new LocationEntity(latitudes.get(i), longitudes.get(i)));
        }

        return locations;
    }

}
