package io.github._7isenko.SCP1985.init;

import io.github._7isenko.SCP1985.jpa.entities.FoundationEntity;
import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import io.github._7isenko.SCP1985.jpa.entities.ScpObjectEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author 7isenko
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final static int FOUNDATION_AMOUNT = 5;
    private final static int SCP_AMOUNT = 10;
    private final static int MAX_SCP_ID = 4000;
    private final static int PERSONNEL_AMOUNT = 30;

    private final static int ITEM_AMOUNT = 30;
    private final static int EQUIPMENT_AMOUNT = 5;
    private final static int MIN_EQUIPMENT_CONTENTS_AMOUNT = 3;
    private final static int MAX_EQUIPMENT_CONTENTS_AMOUNT = 5;

    private final static int MOBILE_GROUP_AMOUNT = 3;
    private final static int MOBILE_GROUP_MEMBERS_AMOUNT = 3;

    private final static int PRIMINGS_AMOUNT = 3;

    private final SessionFactory sessionFactory;
    private final EntitiesSaver entitiesSaver;


    public DataLoader(SessionFactory sessionFactory, EntitiesSaver entitiesSaver) {
        this.sessionFactory = sessionFactory;
        this.entitiesSaver = entitiesSaver;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        requireInitialData();

        entitiesSaver.saveRandomLocations(FOUNDATION_AMOUNT);
        entitiesSaver.saveRandomFoundations(FOUNDATION_AMOUNT);
        entitiesSaver.saveRandomSCPs(SCP_AMOUNT, MAX_SCP_ID);
        entitiesSaver.saveRandomPersonnel(PERSONNEL_AMOUNT);
        entitiesSaver.savePersonnelKeys(PERSONNEL_AMOUNT);
        entitiesSaver.saveRandomItems(ITEM_AMOUNT);
        entitiesSaver.saveRandomEquipment(EQUIPMENT_AMOUNT);
        entitiesSaver.saveEquipmentContents(MIN_EQUIPMENT_CONTENTS_AMOUNT, MAX_EQUIPMENT_CONTENTS_AMOUNT);
        entitiesSaver.saveMobileGroups(MOBILE_GROUP_AMOUNT);
        entitiesSaver.saveMobileGroupsContents(MOBILE_GROUP_MEMBERS_AMOUNT);
        entitiesSaver.savePrimings(PRIMINGS_AMOUNT);
        entitiesSaver.saveExcursions();

        System.out.println("Заполнение закончено!");
    }

    private void requireInitialData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (!checkFirstFoundationLocation(session)) entitiesSaver.saveFirstFoundationLocation();
        if (!checkFirstFoundation(session)) entitiesSaver.saveFirstFoundation();
        if (!checkScp1985(session)) entitiesSaver.saveScp1985();

        session.getTransaction().commit();
        session.close();
    }

    private boolean checkFirstFoundationLocation(Session session) {
        return session.get(LocationEntity.class, 1) != null;
    }

    private boolean checkFirstFoundation(Session session) {
        return session.get(FoundationEntity.class, 1) != null;
    }

    private boolean checkScp1985(Session session) {
        return session.get(ScpObjectEntity.class, 1985) != null;
    }

}
