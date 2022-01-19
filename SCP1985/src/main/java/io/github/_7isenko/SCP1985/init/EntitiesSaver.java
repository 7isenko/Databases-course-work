package io.github._7isenko.SCP1985.init;

import com.github.javafaker.Faker;
import io.github._7isenko.SCP1985.jpa.entities.*;
import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;
import io.github._7isenko.SCP1985.jpa.repositories.*;
import io.github._7isenko.SCP1985.utils.CollectionsHelper;
import io.github._7isenko.SCP1985.utils.PersonnelHelper;
import io.github._7isenko.SCP1985.utils.SCPReceiver;
import io.github._7isenko.SCP1985.utils.StringsHelper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private final ItemEntityRepository itemEntityRepository;
    private final EquipmentEntityRepository equipmentEntityRepository;
    private final EquipmentContentsEntityRepository equipmentContentsEntityRepository;
    private final MobileGroupEntityRepository mobileGroupEntityRepository;
    private final MobileGroupMembersEntityRepository mobileGroupMembersEntityRepository;
    private final PrimingEntityRepository primingEntityRepository;
    private final ExcursionLogEntityRepository excursionLogEntityRepository;

    public EntitiesSaver(RandomEntitiesGenerator generator, LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository, PersonnelEntityRepository personnelEntityRepository, AccessKeyEntityRepository accessKeyEntityRepository, ItemEntityRepository itemEntityRepository, EquipmentEntityRepository equipmentEntityRepository, EquipmentContentsEntityRepository equipmentContentsEntityRepository, MobileGroupEntityRepository mobileGroupEntityRepository, MobileGroupMembersEntityRepository mobileGroupMembersEntityRepository, PrimingEntityRepository primingEntityRepository, ExcursionLogEntityRepository excursionLogEntityRepository) {
        this.generator = generator;
        this.locationEntityRepository = locationEntityRepository;
        this.foundationEntityRepository = foundationEntityRepository;
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        this.personnelEntityRepository = personnelEntityRepository;
        this.accessKeyEntityRepository = accessKeyEntityRepository;
        this.itemEntityRepository = itemEntityRepository;
        this.equipmentEntityRepository = equipmentEntityRepository;
        this.equipmentContentsEntityRepository = equipmentContentsEntityRepository;
        this.mobileGroupEntityRepository = mobileGroupEntityRepository;
        this.mobileGroupMembersEntityRepository = mobileGroupMembersEntityRepository;
        this.primingEntityRepository = primingEntityRepository;
        this.excursionLogEntityRepository = excursionLogEntityRepository;
    }

    public void saveExcursions() {
        List<PrimingEntity> primingEntities = primingEntityRepository.findAll();
        List<EquipmentEntity> equipmentEntities = equipmentEntityRepository.findAll();
        for (PrimingEntity primingEntity : primingEntities) {
            if (primingEntity.getExcursionLogsById().isEmpty())
                excursionLogEntityRepository.goOnExcursion(primingEntity.getScpObjectId(), primingEntity.getPersonnelId(), CollectionsHelper.getRandomElement(equipmentEntities).getId());
        }
        excursionLogEntityRepository.flush();
    }

    public void savePrimings(int amount) {
        List<PersonnelEntity> allowedPersonnelEntities = PersonnelHelper.getAllowedPersonnel(personnelEntityRepository.findAll());
        List<ScpObjectEntity> scpObjectEntities = scpObjectEntityRepository.findAll();

        for (int i = 0; i < amount; i++) {
            int scp = CollectionsHelper.getRandomElement(scpObjectEntities).getId();
            int personnel = CollectionsHelper.getRandomElement(allowedPersonnelEntities).getId();
            primingEntityRepository.save(new PrimingEntity(scp, personnel));
        }

        primingEntityRepository.flush();
    }

    public void saveMobileGroupsContents(int min) {
        List<PersonnelEntity> personnelEntities = personnelEntityRepository.findAll();
        for (MobileGroupEntity mobileGroup : mobileGroupEntityRepository.findAll()) {
            if (mobileGroup.getMobileGroupMembersById().size() < min) {
                for (int i = 0; i < min; i++) {
                    mobileGroupMembersEntityRepository.save(new MobileGroupMembersEntity(mobileGroup.getId(), CollectionsHelper.getRandomElement(personnelEntities).getId()));
                }
            }
        }
        mobileGroupMembersEntityRepository.flush();
    }

    public void saveMobileGroups(int amount) {
        Faker faker = new Faker();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < amount; i++) {
            mobileGroupEntityRepository.save(new MobileGroupEntity(faker.pokemon().name(), new Timestamp(random.nextLong(Calendar.getInstance().getTimeInMillis() >> 3, Calendar.getInstance().getTimeInMillis()))));
        }
        mobileGroupEntityRepository.flush();
    }

    public void saveEquipmentContents(int min, int max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<ItemEntity> itemEntities = itemEntityRepository.findAll();
        for (EquipmentEntity equipmentEntity : equipmentEntityRepository.findAll()) {
            for (int i = equipmentEntity.getEquipmentContentsById().size(); i < min + random.nextInt(0, max - min); i++) {
                equipmentContentsEntityRepository.save(new EquipmentContentsEntity(equipmentEntity.getId(),
                        CollectionsHelper.getRandomElement(itemEntities).getId()));
            }
            equipmentContentsEntityRepository.flush();
        }
    }

    public void saveRandomEquipment(int amount) {
        for (String s : CollectionsHelper.getRandomStringList(amount, 3)) {
            EquipmentEntity eq = new EquipmentEntity(s);
            equipmentEntityRepository.save(eq);
        }
        equipmentEntityRepository.flush();
    }

    public void saveRandomItems(int amount) {
        for (String s : CollectionsHelper.getRandomStringList(amount, 6)) {
            itemEntityRepository.save(new ItemEntity(s));
        }
        itemEntityRepository.flush();
    }

    public void savePersonnelKeys(int amount) {
        for (int i = 0; i < amount; i++) {
            String ssh = StringsHelper.genRandomString(32);
            accessKeyEntityRepository.save(new AccessKeyEntity(ssh, i + 1));
        }
        accessKeyEntityRepository.flush();
    }

    public void saveRandomPersonnel(int amount) {
        PersonnelHelper personnelHelper = new PersonnelHelper();
        personnelEntityRepository.saveAll(personnelHelper.generatePersonnel(amount));
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
