package io.github._7isenko.SCP1985.init;

import io.github._7isenko.SCP1985.jpa.entities.FoundationEntity;
import io.github._7isenko.SCP1985.jpa.entities.LocationEntity;
import io.github._7isenko.SCP1985.jpa.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;
import io.github._7isenko.SCP1985.jpa.repositories.FoundationEntityRepository;
import io.github._7isenko.SCP1985.jpa.repositories.LocationEntityRepository;
import io.github._7isenko.SCP1985.jpa.repositories.ScpObjectEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * @author 7isenko
 */
@Component
public class InitialDataManager {

    private final LocationEntityRepository locationEntityRepository;
    private final FoundationEntityRepository foundationEntityRepository;
    private final ScpObjectEntityRepository scpObjectEntityRepository;

    private final SessionFactory sessionFactory;

    public InitialDataManager(LocationEntityRepository locationEntityRepository, FoundationEntityRepository foundationEntityRepository, ScpObjectEntityRepository scpObjectEntityRepository, SessionFactory sessionFactory) {
        this.locationEntityRepository = locationEntityRepository;
        this.foundationEntityRepository = foundationEntityRepository;
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        this.sessionFactory = sessionFactory;
    }

    public void requireInitialData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (!checkFirstFoundationLocation(session)) saveFirstFoundationLocation();
        if (!checkFirstFoundation(session)) saveFirstFoundation();
        if (!checkScp1985(session)) saveScp1985();

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
