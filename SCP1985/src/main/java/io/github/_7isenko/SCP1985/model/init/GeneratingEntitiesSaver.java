package io.github._7isenko.SCP1985.model.init;

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
        repositoriesFacade.savePrimings(generator.getPrimingEntities(amount));
    }

    public void saveRandomMobileGroupsMembers(int min) {
        repositoriesFacade.saveMobileGroupsMembers(generator.getRandomMobileGroupsMembers(min));
    }

    public void saveRandomMobileGroups(int amount) {
        repositoriesFacade.saveMobileGroups(generator.getRandomMobileGroups(amount));
    }

    public void saveRandomEquipmentContents(int min, int max) {
        repositoriesFacade.saveEquipmentContents(generator.getRandomEquipmentContents(min, max));
    }

    public void saveRandomEquipment(int amount) {
        repositoriesFacade.saveEquipment(generator.getRandomEquipment(amount));
    }

    public void saveRandomItems(int amount) {
        repositoriesFacade.saveItems(generator.getRandomItems(amount));
    }

    public void saveRandomPersonnelKeys(int amount) {
        repositoriesFacade.saveAccessKeys(generator.getRandomPersonnelKeys(amount));
    }

    public void saveRandomSCPs(int amount, int maxId) {
        repositoriesFacade.saveSCPs(generator.getRandomSCPs(amount, maxId));
    }

    public void saveRandomPersonnel(int amount) {
        repositoriesFacade.savePersonnel(generator.getRandomPersonnel(amount));
    }

    public void saveRandomFoundations(int amount) {
        repositoriesFacade.saveFoundations(generator.getRandomFoundations(amount));
    }

    public void saveRandomLocations(int amount) {
        repositoriesFacade.saveLocations(generator.getRandomLocations(amount));
    }


}
