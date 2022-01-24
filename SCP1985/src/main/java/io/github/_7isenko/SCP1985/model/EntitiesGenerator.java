package io.github._7isenko.SCP1985.model;

import com.github.javafaker.Faker;
import io.github._7isenko.SCP1985.model.entities.*;
import io.github._7isenko.SCP1985.model.repositories.*;
import io.github._7isenko.SCP1985.model.utils.CollectionsHelper;
import io.github._7isenko.SCP1985.model.utils.PersonnelHelper;
import io.github._7isenko.SCP1985.model.utils.SCPReceiver;
import io.github._7isenko.SCP1985.model.utils.StringsHelper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author 7isenko
 */

@Component
public class EntitiesGenerator {

    private final LocationEntityRepository locationEntityRepository;
    private final ScpObjectEntityRepository scpObjectEntityRepository;
    private final PersonnelEntityRepository personnelEntityRepository;
    private final ItemEntityRepository itemEntityRepository;
    private final EquipmentEntityRepository equipmentEntityRepository;
    private final MobileGroupEntityRepository mobileGroupEntityRepository;
    private final FoundationEntityRepository foundationEntityRepository;
    private final PrimingEntityRepository primingEntityRepository;
    private final ExcursionLogEntityRepository excursionLogEntityRepository;

    public EntitiesGenerator(LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository, PersonnelEntityRepository personnelEntityRepository, AccessKeyEntityRepository accessKeyEntityRepository, ItemEntityRepository itemEntityRepository, EquipmentEntityRepository equipmentEntityRepository, EquipmentContentsEntityRepository equipmentContentsEntityRepository, MobileGroupEntityRepository mobileGroupEntityRepository, MobileGroupMembersEntityRepository mobileGroupMembersEntityRepository, FoundationEntityRepository foundationEntityRepository1, PrimingEntityRepository primingEntityRepository, ExcursionLogEntityRepository excursionLogEntityRepository) {
        this.locationEntityRepository = locationEntityRepository;
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        this.personnelEntityRepository = personnelEntityRepository;
        this.itemEntityRepository = itemEntityRepository;
        this.equipmentEntityRepository = equipmentEntityRepository;
        this.mobileGroupEntityRepository = mobileGroupEntityRepository;
        this.foundationEntityRepository = foundationEntityRepository1;
        this.primingEntityRepository = primingEntityRepository;
        this.excursionLogEntityRepository = excursionLogEntityRepository;
    }

    public ArrayList<PrimingEntity> getPrimingEntities(int amount) {
        ArrayList<PrimingEntity> primingEntities = new ArrayList<>();

        List<PersonnelEntity> allowedPersonnelEntities = PersonnelHelper.getAllowedPersonnel(personnelEntityRepository.findAll());
        List<ScpObjectEntity> scpObjectEntities = scpObjectEntityRepository.findAll();

        for (int i = 0; i < amount; i++) {
            int scp = CollectionsHelper.getRandomElement(scpObjectEntities).getId();
            int personnel = CollectionsHelper.getRandomElement(allowedPersonnelEntities).getId();
            primingEntities.add(new PrimingEntity(scp, personnel));
        }

        return primingEntities;
    }

    public ArrayList<MobileGroupMembersEntity> getRandomMobileGroupsMembers(int min) {
        ArrayList<MobileGroupMembersEntity> mobileGroupMembersEntities = new ArrayList<>();
        List<PersonnelEntity> personnelEntities = personnelEntityRepository.findAll();
        for (MobileGroupEntity mobileGroup : mobileGroupEntityRepository.findAll()) {
            if (mobileGroup.getMobileGroupMembersById() == null || mobileGroup.getMobileGroupMembersById().size() < min) {
                for (int i = 0; i < min; i++) {
                    mobileGroupMembersEntities.add(new MobileGroupMembersEntity(mobileGroup.getId(), CollectionsHelper.getRandomElement(personnelEntities).getId()));
                }
            }
        }
        return mobileGroupMembersEntities;
    }

    public ArrayList<MobileGroupEntity> getRandomMobileGroups(int amount) {
        ArrayList<MobileGroupEntity> mobileGroupEntities = new ArrayList<>();

        Faker faker = new Faker();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < amount; i++) {
            mobileGroupEntities.add(new MobileGroupEntity(faker.pokemon().name(), new Timestamp(random.nextLong(Calendar.getInstance().getTimeInMillis() >> 3, Calendar.getInstance().getTimeInMillis()))));
        }
        return mobileGroupEntities;
    }

    public ArrayList<EquipmentContentsEntity> getRandomEquipmentContents(int min, int max) {
        ArrayList<EquipmentContentsEntity> equipmentContentsEntities = new ArrayList<>();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<ItemEntity> itemEntities = itemEntityRepository.findAll();
        for (EquipmentEntity equipmentEntity : equipmentEntityRepository.findAll()) {
            int size = equipmentEntity.getEquipmentContentsById() == null ? 0 : equipmentEntity.getEquipmentContentsById().size();
            for (int i = size; i < min + random.nextInt(0, max - min); i++) {
                equipmentContentsEntities.add(new EquipmentContentsEntity(equipmentEntity.getId(),
                        CollectionsHelper.getRandomElement(itemEntities).getId()));
            }
        }

        return equipmentContentsEntities;
    }

    public ArrayList<EquipmentEntity> getRandomEquipment(int amount) {
        ArrayList<EquipmentEntity> equipmentEntities = new ArrayList<>();

        for (String s : CollectionsHelper.getRandomStringList(amount, 3)) {
            EquipmentEntity eq = new EquipmentEntity(s);
            equipmentEntities.add(eq);
        }
        return equipmentEntities;
    }

    public ArrayList<ItemEntity> getRandomItems(int amount) {
        ArrayList<ItemEntity> itemEntities = new ArrayList<>();

        for (String s : CollectionsHelper.getRandomStringList(amount, 6)) {
            itemEntities.add(new ItemEntity(s));
        }
        return itemEntities;
    }

    public ArrayList<AccessKeyEntity> getRandomPersonnelKeys() {
        ArrayList<AccessKeyEntity> accessKeyEntities = new ArrayList<>();
        List<PersonnelEntity> personnelEntityList = personnelEntityRepository.findAll()
                .stream()
                .filter(personnelEntity -> personnelEntity.getAccessKeyById() == null)
                .collect(Collectors.toList());

        for (PersonnelEntity personnel : personnelEntityList) {
            String ssh = StringsHelper.genRandomString(32);
            accessKeyEntities.add(new AccessKeyEntity(ssh, personnel.getId()));
        }

        return accessKeyEntities;
    }

    public ArrayList<ScpObjectEntity> getRandomSCPs(int amount, int maxId) {
        HashSet<Integer> known = new HashSet<>();
        for (ScpObjectEntity scpObject : scpObjectEntityRepository.findAll()) {
            known.add(scpObject.getId());
        }
        SCPReceiver scpReceiver = new SCPReceiver(maxId, known);
        ArrayList<ScpObjectEntity> scpList = scpReceiver.getSCPList(amount);

        int max = foundationEntityRepository.countAll();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (ScpObjectEntity scpObject : scpList) {
            scpObject.setFoundationId(random.nextInt(1, max));
        }

        return scpList;
    }

    public ArrayList<PersonnelEntity> getRandomPersonnel(int amount) {
        return PersonnelHelper.generatePersonnel(amount);
    }

    public ArrayList<FoundationEntity> getRandomFoundations(int amount) {
        ArrayList<FoundationEntity> foundationEntities = new ArrayList<>();

        for (LocationEntity locationEntity : locationEntityRepository.findByIdGreaterThanAndIdLessThan(amount)) {
            foundationEntities.add(new FoundationEntity(locationEntity.getId()));
        }

        return foundationEntities;
    }

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
