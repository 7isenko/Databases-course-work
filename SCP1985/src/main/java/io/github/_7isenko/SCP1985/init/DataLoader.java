package io.github._7isenko.SCP1985.init;

import io.github._7isenko.SCP1985.jpa.entities.FoundationEntity;
import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import io.github._7isenko.SCP1985.jpa.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;
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

    private final SessionFactory sessionFactory;
    private final EntitiesLoader entitiesLoader;

    private Session session;

    public DataLoader(SessionFactory sessionFactory, EntitiesLoader entitiesLoader) {
        this.sessionFactory = sessionFactory;
        this.entitiesLoader = entitiesLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        session = sessionFactory.openSession();
        session.beginTransaction();

        requireInitialData();

        session.getTransaction().commit();
        session.close();

        entitiesLoader.saveRandomLocations(10);
    }


    private void requireInitialData() {
        if (!checkFirstFoundationLocation()) entitiesLoader.saveFirstFoundationLocation();
        if (!checkFirstFoundation()) entitiesLoader.saveFirstFoundation();
        if (!checkScp1985()) saveScp1985();
    }

    private boolean checkFirstFoundationLocation() {
        return session.get(LocationEntity.class, 1) != null;
    }

    private boolean checkFirstFoundation() {
        return session.get(FoundationEntity.class, 1) != null;
    }

    private boolean checkScp1985() {
        return session.get(ScpObjectEntity.class, 1985) != null;
    }

    private void saveScp1985() {
        session.save(getScp1985());
        session.flush();
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
