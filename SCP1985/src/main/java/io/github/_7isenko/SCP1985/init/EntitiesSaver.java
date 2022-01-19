package io.github._7isenko.SCP1985.init;

import io.github._7isenko.SCP1985.jpa.entities.AccessKeyEntity;
import io.github._7isenko.SCP1985.jpa.entities.FoundationEntity;
import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import io.github._7isenko.SCP1985.jpa.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;
import io.github._7isenko.SCP1985.jpa.repositories.*;
import io.github._7isenko.SCP1985.utils.PersonnelGenerator;
import io.github._7isenko.SCP1985.utils.SCPReceiver;
import io.github._7isenko.SCP1985.utils.StringsHelper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @author 7isenko
 */
@Component
public class EntitiesSaver {

    private final RandomEntitiesGenerator generator;
    private final LocationEntityRepository locationEntityRepository;
    private final FoundationEntityRepository foundationEntityRepository;
    private final ScpObjectEntityRepository scpObjectEntityRepository;
    private final PersonnelEntityRepository personnelEntityRepository;
    private final AccessKeyEntityRepository accessKeyEntityRepository;

    public EntitiesSaver(RandomEntitiesGenerator generator, LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository, PersonnelEntityRepository personnelEntityRepository, AccessKeyEntityRepository accessKeyEntityRepository) {
        this.generator = generator;
        this.locationEntityRepository = locationEntityRepository;
        this.foundationEntityRepository = foundationEntityRepository;
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        this.personnelEntityRepository = personnelEntityRepository;
        this.accessKeyEntityRepository = accessKeyEntityRepository;
    }

    public void savePersonnelKeys(int amount) {
        for (int i = 0; i < amount; i++) {
            String ssh = StringsHelper.genRandomString(32);
            accessKeyEntityRepository.save(new AccessKeyEntity(ssh, i + 1));
        }
        accessKeyEntityRepository.flush();
    }

    public void saveRandomPersonnel(int amount) {
        PersonnelGenerator personnelGenerator = new PersonnelGenerator();
        personnelEntityRepository.saveAll(personnelGenerator.generatePersonnel(amount));
    }

    public void saveRandomSCPs(int amount, int maxId) {
        HashSet<Integer> known = new HashSet<>();
        for (ScpObjectEntity scpObject : scpObjectEntityRepository.findAll()) {
            known.add(scpObject.getId());
        }
        SCPReceiver scpReceiver = new SCPReceiver(maxId, known);
        scpObjectEntityRepository.saveAll(scpReceiver.getSCPList(amount));
    }

    /**
     * Добавит по фонду на каждую существующую локацию
     */
    public void saveRandomFoundations(int amount) {
        for (LocationEntity locationEntity : locationEntityRepository.findByIdGreaterThanAndIdLessThan(1, amount + 1)) {
            foundationEntityRepository.save(new FoundationEntity(locationEntity.getId()));
        }
        foundationEntityRepository.flush();
    }

    public void saveRandomLocations(int amount) {
        locationEntityRepository.saveAll(generator.getRandomLocations(amount));
    }


    /**
     * Часть класса для базовых значений, которые всегда должны быть в таблице
     */

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
