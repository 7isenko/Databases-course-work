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

    private final static int AMOUNT_OF_FOUNDATIONS = 5;
    private final static int AMOUNT_OF_SCP = 5;
    private final static int MAX_SCP_ID = 4000;

    private final SessionFactory sessionFactory;
    private final EntitiesSaver entitiesSaver;

    public DataLoader(SessionFactory sessionFactory, EntitiesSaver entitiesSaver) {
        this.sessionFactory = sessionFactory;
        this.entitiesSaver = entitiesSaver;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        requireInitialData();

        entitiesSaver.saveRandomLocations(AMOUNT_OF_FOUNDATIONS);
        entitiesSaver.saveRandomFoundations(AMOUNT_OF_FOUNDATIONS);
        entitiesSaver.saveRandomSCPs(AMOUNT_OF_SCP, MAX_SCP_ID);
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
