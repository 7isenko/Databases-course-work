package io.github._7isenko.SCP1985.model;

import io.github._7isenko.SCP1985.model.entities.EquipmentEntity;
import io.github._7isenko.SCP1985.model.entities.PrimingEntity;
import io.github._7isenko.SCP1985.model.repositories.*;
import io.github._7isenko.SCP1985.model.utils.CollectionsHelper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * I don't really know how to name this class
 *
 * @author 7isenko
 */
@Component
public class GeneratingEntitiesSaver {

    private final static int MAX_SCP_ID = 4000;
    private final static int MIN_EQUIPMENT_CONTENTS_AMOUNT = 4;
    private final static int MAX_EQUIPMENT_CONTENTS_AMOUNT = 6;
    private final static int MOBILE_GROUP_MEMBERS_AMOUNT = 4;

    private final RepositoriesFacade repositoriesFacade;

    private final EntitiesGenerator generator;
    private final EquipmentEntityRepository equipmentEntityRepository;
    private final PrimingEntityRepository primingEntityRepository;


    public GeneratingEntitiesSaver(RepositoriesFacade repositoriesFacade, EntitiesGenerator generator, LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository, PersonnelEntityRepository personnelEntityRepository, AccessKeyEntityRepository accessKeyEntityRepository, ItemEntityRepository itemEntityRepository, EquipmentEntityRepository equipmentEntityRepository, EquipmentContentsEntityRepository equipmentContentsEntityRepository, MobileGroupEntityRepository mobileGroupEntityRepository, MobileGroupMembersEntityRepository mobileGroupMembersEntityRepository, PrimingEntityRepository primingEntityRepository, ExcursionLogEntityRepository excursionLogEntityRepository) {
        this.repositoriesFacade = repositoriesFacade;
        this.generator = generator;
        this.equipmentEntityRepository = equipmentEntityRepository;
        this.primingEntityRepository = primingEntityRepository;
    }

    // FIXME: если будет время, с этим методом сделать аналогично как и с теми, что ниже
    public void saveRandomExcursionLogs() {
        List<PrimingEntity> primingEntities = primingEntityRepository.findAll();
        List<EquipmentEntity> equipmentEntities = equipmentEntityRepository.findAll();
        for (PrimingEntity primingEntity : primingEntities) {
            if (primingEntity.getExcursionLogsById().isEmpty())
                repositoriesFacade.executeGoOnExcursion(primingEntity, CollectionsHelper.getRandomElement(equipmentEntities));
        }
    }

    public void saveRandomPrimings(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.savePrimings(generator.getPrimingEntities(amount));
    }

    public void saveRandomMobileGroupsMembers() {
        repositoriesFacade.saveMobileGroupsMembers(generator.getRandomMobileGroupsMembers(MOBILE_GROUP_MEMBERS_AMOUNT));
    }

    public void saveRandomMobileGroups(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveMobileGroups(generator.getRandomMobileGroups(amount));
    }

    public void saveRandomEquipmentContents() {
        repositoriesFacade.saveEquipmentContents(generator.getRandomEquipmentContents(MIN_EQUIPMENT_CONTENTS_AMOUNT, MAX_EQUIPMENT_CONTENTS_AMOUNT));
    }

    public void saveRandomEquipment(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveEquipment(generator.getRandomEquipment(amount));
    }

    public void saveRandomItems(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveItems(generator.getRandomItems(amount));
    }

    public void saveRandomPersonnelKeys(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveAccessKeys(generator.getRandomPersonnelKeys(amount));
    }

    public void saveRandomSCPs(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveSCPs(generator.getRandomSCPs(amount, MAX_SCP_ID));
    }

    public void saveRandomPersonnel(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.savePersonnel(generator.getRandomPersonnel(amount));
    }

    public void saveRandomFoundations(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveFoundations(generator.getRandomFoundations(amount));
    }

    public void saveRandomLocations(int amount) {
        if (amount <= 0) return;
        repositoriesFacade.saveLocations(generator.getRandomLocations(amount));
    }


}
