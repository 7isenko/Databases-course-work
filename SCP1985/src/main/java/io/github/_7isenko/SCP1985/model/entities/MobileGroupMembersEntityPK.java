package io.github._7isenko.SCP1985.model.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 7isenko
 */
public class MobileGroupMembersEntityPK implements Serializable {
    private int mobileGroupId;
    private int personnelId;

    @Column(name = "mobile_group_id", nullable = false)
    @Id
    public int getMobileGroupId() {
        return mobileGroupId;
    }

    public void setMobileGroupId(int mobileGroupId) {
        this.mobileGroupId = mobileGroupId;
    }

    @Column(name = "personnel_id", nullable = false)
    @Id
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
        MobileGroupMembersEntityPK that = (MobileGroupMembersEntityPK) o;
        return mobileGroupId == that.mobileGroupId && personnelId == that.personnelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileGroupId, personnelId);
    }
}
