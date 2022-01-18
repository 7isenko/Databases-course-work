package io.github._7isenko.SCP1985.jpa.entities;

import io.github._7isenko.SCP1985.jpa.object_types.Classification;
import io.github._7isenko.SCP1985.jpa.object_types.ClearanceLevel;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@Entity
@Table(name = "personnel")
public class PersonnelEntity {
    private int id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private ClearanceLevel clearanceLevel;
    @Enumerated(EnumType.STRING)
    private Classification classification;
    private Collection<AccessKeyEntity> accessKeysById;
    private Collection<MobileGroupMembersEntity> mobileGroupMembersById;
    private Collection<PrimingEntity> primingsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 60)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "clearance_level", nullable = true)
    public ClearanceLevel getClearanceLevel() {
        return clearanceLevel;
    }

    public void setClearanceLevel(ClearanceLevel clearanceLevel) {
        this.clearanceLevel = clearanceLevel;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = true)
    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonnelEntity that = (PersonnelEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(clearanceLevel, that.clearanceLevel) && Objects.equals(classification, that.classification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, clearanceLevel, classification);
    }

    @OneToMany(mappedBy = "personnelByPersonnelId")
    public Collection<AccessKeyEntity> getAccessKeysById() {
        return accessKeysById;
    }

    public void setAccessKeysById(Collection<AccessKeyEntity> accessKeysById) {
        this.accessKeysById = accessKeysById;
    }

    @OneToMany(mappedBy = "personnelByPersonnelId")
    public Collection<MobileGroupMembersEntity> getMobileGroupMembersById() {
        return mobileGroupMembersById;
    }

    public void setMobileGroupMembersById(Collection<MobileGroupMembersEntity> mobileGroupMembersById) {
        this.mobileGroupMembersById = mobileGroupMembersById;
    }

    @OneToMany(mappedBy = "personnelByPersonnelId")
    public Collection<PrimingEntity> getPrimingsById() {
        return primingsById;
    }

    public void setPrimingsById(Collection<PrimingEntity> primingsById) {
        this.primingsById = primingsById;
    }
}
