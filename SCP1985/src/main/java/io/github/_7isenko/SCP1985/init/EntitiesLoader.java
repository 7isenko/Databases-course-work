package io.github._7isenko.SCP1985.init;

import io.github._7isenko.SCP1985.jpa.entities.FoundationEntity;
import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import io.github._7isenko.SCP1985.jpa.repositories.FoundationEntityRepository;
import io.github._7isenko.SCP1985.jpa.repositories.LocationEntityRepository;
import org.springframework.stereotype.Component;

/**
 * @author 7isenko
 */
@Component
public class EntitiesLoader {

    private final RandomEntitiesGenerator generator;
    private final LocationEntityRepository locationEntityRepository;
    private final FoundationEntityRepository foundationEntityRepository;

    public EntitiesLoader(RandomEntitiesGenerator generator, LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository) {
        this.generator = generator;
        this.locationEntityRepository = locationEntityRepository;
        this.foundationEntityRepository = foundationEntityRepository;
    }

    public void saveRandomLocations(int amount) {
        locationEntityRepository.saveAll(generator.getRandomLocations(amount));
    }

    public void saveFirstFoundationLocation() {
        locationEntityRepository.saveAndFlush(new LocationEntity(37.928141, -119.026927));
    }

    public void saveFirstFoundation() {
        foundationEntityRepository.saveAndFlush(new FoundationEntity(1));
    }

}
