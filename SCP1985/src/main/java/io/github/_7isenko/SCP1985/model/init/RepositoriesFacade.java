package io.github._7isenko.SCP1985.model.init;

import io.github._7isenko.SCP1985.model.entities.*;
import io.github._7isenko.SCP1985.model.repositories.*;
import org.springframework.stereotype.Component;

/**
 * This class wraps every repository to simplify interaction with the database.
 * Most methods work with collections (iterable).
 *
 * @author 7isenko
 */
@Component
public class RepositoriesFacade {

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

    public RepositoriesFacade(LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository, PersonnelEntityRepository personnelEntityRepository, AccessKeyEntityRepository accessKeyEntityRepository, ItemEntityRepository itemEntityRepository, EquipmentEntityRepository equipmentEntityRepository, EquipmentContentsEntityRepository equipmentContentsEntityRepository, MobileGroupEntityRepository mobileGroupEntityRepository, MobileGroupMembersEntityRepository mobileGroupMembersEntityRepository, PrimingEntityRepository primingEntityRepository, ExcursionLogEntityRepository excursionLogEntityRepository) {
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

    public void saveExcursionLogs(Iterable<ExcursionLogEntity> excursionLogEntities) {
        excursionLogEntityRepository.saveAllAndFlush(excursionLogEntities);
    }

    public void executeGoOnExcursion(PrimingEntity primingEntity, EquipmentEntity equipmentEntity) {
        executeGoOnExcursion(primingEntity.getScpObjectId(), primingEntity.getPersonnelId(), equipmentEntity.getId());
    }

    public void executeGoOnExcursion(Integer scpObjectEntityId, Integer personnelId, Integer equipmentEntityId) {
        excursionLogEntityRepository.goOnExcursion(scpObjectEntityId, personnelId, equipmentEntityId);
    }

    public void savePrimings(Iterable<PrimingEntity> primingEntities) {
        primingEntityRepository.saveAllAndFlush(primingEntities);
    }

    public void saveMobileGroupsMembers(Iterable<MobileGroupMembersEntity> mobileGroupMembersEntities) {
        mobileGroupMembersEntityRepository.saveAllAndFlush(mobileGroupMembersEntities);
    }

    public void saveMobileGroups(Iterable<MobileGroupEntity> mobileGroupEntities) {
        mobileGroupEntityRepository.saveAllAndFlush(mobileGroupEntities);
    }

    public void saveEquipmentContents(Iterable<EquipmentContentsEntity> equipmentContentsEntities) {
        equipmentContentsEntityRepository.saveAllAndFlush(equipmentContentsEntities);
    }

    public void saveEquipment(Iterable<EquipmentEntity> equipmentEntities) {
        equipmentEntityRepository.saveAllAndFlush(equipmentEntities);
    }

    public void saveItems(Iterable<ItemEntity> itemEntities) {
        itemEntityRepository.saveAllAndFlush(itemEntities);
    }

    public void saveAccessKeys(Iterable<AccessKeyEntity> accessKeyEntities) {
        accessKeyEntityRepository.saveAllAndFlush(accessKeyEntities);
    }

    public void savePersonnel(Iterable<PersonnelEntity> personnelEntities) {
        personnelEntityRepository.saveAllAndFlush(personnelEntities);
    }

    public void saveSCPs(Iterable<ScpObjectEntity> scpObjectEntities) {
        scpObjectEntityRepository.saveAllAndFlush(scpObjectEntities);
    }

    public void saveFoundations(Iterable<FoundationEntity> foundationEntities) {
        foundationEntityRepository.saveAllAndFlush(foundationEntities);
    }

    public void saveLocations(Iterable<LocationEntity> locationEntities) {
        locationEntityRepository.saveAllAndFlush(locationEntities);
    }

}
