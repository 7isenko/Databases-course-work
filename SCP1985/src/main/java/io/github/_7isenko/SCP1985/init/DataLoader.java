package io.github._7isenko.SCP1985.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author 7isenko
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final static int FOUNDATION_AMOUNT = 2;
    private final static int SCP_AMOUNT = 5;
    private final static int MAX_SCP_ID = 4000;
    private final static int PERSONNEL_AMOUNT = 60;

    private final static int ITEM_AMOUNT = 30;
    private final static int EQUIPMENT_AMOUNT = 3;
    private final static int MIN_EQUIPMENT_CONTENTS_AMOUNT = 4;
    private final static int MAX_EQUIPMENT_CONTENTS_AMOUNT = 6;

    private final static int MOBILE_GROUP_AMOUNT = 2;
    private final static int MOBILE_GROUP_MEMBERS_AMOUNT = 4;

    private final static int PRIMINGS_AMOUNT = 2;

    private final GeneratingEntitiesSaver generatingEntitiesSaver;
    private final InitialDataManager initialDataManager;


    public DataLoader(GeneratingEntitiesSaver generatingEntitiesSaver, InitialDataManager initialDataManager) {
        this.generatingEntitiesSaver = generatingEntitiesSaver;
        this.initialDataManager = initialDataManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initialDataManager.requireInitialData();

        generatingEntitiesSaver.saveRandomLocations(FOUNDATION_AMOUNT);
        generatingEntitiesSaver.saveRandomFoundations(FOUNDATION_AMOUNT);
        generatingEntitiesSaver.saveRandomSCPs(SCP_AMOUNT, MAX_SCP_ID);
        generatingEntitiesSaver.saveRandomPersonnel(PERSONNEL_AMOUNT);
        generatingEntitiesSaver.saveRandomPersonnelKeys(PERSONNEL_AMOUNT);
        generatingEntitiesSaver.saveRandomItems(ITEM_AMOUNT);
        generatingEntitiesSaver.saveRandomEquipment(EQUIPMENT_AMOUNT);
        generatingEntitiesSaver.saveRandomEquipmentContents(MIN_EQUIPMENT_CONTENTS_AMOUNT, MAX_EQUIPMENT_CONTENTS_AMOUNT);
        generatingEntitiesSaver.saveRandomMobileGroups(MOBILE_GROUP_AMOUNT);
        generatingEntitiesSaver.saveRandomMobileGroupsMembers(MOBILE_GROUP_MEMBERS_AMOUNT);
        generatingEntitiesSaver.saveRandomPrimings(PRIMINGS_AMOUNT);
        generatingEntitiesSaver.saveRandomExcursionLogs();

        System.out.println("Заполнение закончено!");
    }



}
