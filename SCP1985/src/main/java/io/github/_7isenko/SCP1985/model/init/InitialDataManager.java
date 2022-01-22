package io.github._7isenko.SCP1985.model.init;

import io.github._7isenko.SCP1985.model.entities.FoundationEntity;
import io.github._7isenko.SCP1985.model.entities.LocationEntity;
import io.github._7isenko.SCP1985.model.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.model.object_types.ObjectCLass;
import io.github._7isenko.SCP1985.model.repositories.FoundationEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.LocationEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.ScpObjectEntityRepository;
import org.springframework.stereotype.Component;

/**
 * @author 7isenko
 */
@Component
public class InitialDataManager {

    private final LocationEntityRepository locationEntityRepository;
    private final FoundationEntityRepository foundationEntityRepository;
    private final ScpObjectEntityRepository scpObjectEntityRepository;

    public InitialDataManager(LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository) {
        this.locationEntityRepository = locationEntityRepository;
        this.foundationEntityRepository = foundationEntityRepository;
        this.scpObjectEntityRepository = scpObjectEntityRepository;
    }

    public void requireInitialData() {
        if (!checkFirstFoundationLocation()) saveFirstFoundationLocation();
        if (!checkFirstFoundation()) saveFirstFoundation();
        if (!checkScp1985()) saveScp1985();
    }

    private boolean checkFirstFoundationLocation() {
        return locationEntityRepository.findById(1).isPresent();
    }

    private boolean checkFirstFoundation() {
        return foundationEntityRepository.findById(1).isPresent();
    }

    private boolean checkScp1985() {
        return scpObjectEntityRepository.findById(1985).isPresent();
    }

    public void saveFirstFoundationLocation() {
        locationEntityRepository.saveAndFlush(new LocationEntity(37.928141, -119.026927));
    }

    public void saveFirstFoundation() {
        foundationEntityRepository.saveAndFlush(new FoundationEntity(1));
    }

    public void saveScp1985() {
        scpObjectEntityRepository.save(getScp1985());
    }

    private ScpObjectEntity getScp1985() {
        int id = 1985;
        String name = "Устройство для исследований сценариев класса K";
        String description = "SCP-1985 – это женщина афроамериканского происхождения по имени Жаклин ███████. SCP-1985-A – это устройство для телепортации между мирами, имплантированное в тело субъекта. Оно состоит из миллионов ультратонких компонентов в головном и спинном мозге и нескольких крупных частей в районе живота и грудной клетки. Предполагается, что аномальные эффекты и субъекта, и устройства основаны на свойствах множества других SCP-объектов.";
        ObjectCLass objectClass = ObjectCLass.Евклид;
        Integer foundationId = 1;
        return new ScpObjectEntity(id, name, description, objectClass, foundationId);
    }
}
