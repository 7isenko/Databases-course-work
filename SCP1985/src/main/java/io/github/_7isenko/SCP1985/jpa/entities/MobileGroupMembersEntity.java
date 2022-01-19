package io.github._7isenko.SCP1985.jpa.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@Table(name = "mobile_group_members")
@IdClass(MobileGroupMembersEntityPK.class)
public class MobileGroupMembersEntity {
    private int mobileGroupId;
    private int personnelId;
    private MobileGroupEntity mobileGroupByMobileGroupId;
    private PersonnelEntity personnelByPersonnelId;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mobile_group_id", nullable = false)
    public int getMobileGroupId() {
        return mobileGroupId;
    }

    public void setMobileGroupId(int mobileGroupId) {
        this.mobileGroupId = mobileGroupId;
    }

    @Id
    @Column(name = "personnel_id", nullable = false)
    public int getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MobileGroupMembersEntity that = (MobileGroupMembersEntity) o;
        return mobileGroupId == that.mobileGroupId && personnelId == that.personnelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileGroupId, personnelId);
    }

    @ManyToOne
    @JoinColumn(name = "mobile_group_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public MobileGroupEntity getMobileGroupByMobileGroupId() {
        return mobileGroupByMobileGroupId;
    }

    public void setMobileGroupByMobileGroupId(MobileGroupEntity mobileGroupByMobileGroupId) {
        this.mobileGroupByMobileGroupId = mobileGroupByMobileGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "personnel_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public PersonnelEntity getPersonnelByPersonnelId() {
        return personnelByPersonnelId;
    }

    public void setPersonnelByPersonnelId(PersonnelEntity personnelByPersonnelId) {
        this.personnelByPersonnelId = personnelByPersonnelId;
    }
}
