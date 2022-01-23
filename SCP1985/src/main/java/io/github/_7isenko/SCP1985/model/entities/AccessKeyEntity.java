package io.github._7isenko.SCP1985.model.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@Table(name = "access_key")
public class AccessKeyEntity {
    private String sshKey;
    private Integer personnelId;
    private PersonnelEntity personnelByPersonnelId;

    public AccessKeyEntity(String sshKey, Integer personnelId) {
        this.sshKey = sshKey;
        this.personnelId = personnelId;
    }

    @Id
    @Column(name = "ssh_key", nullable = false, length = -1)
    public String getSshKey() {
        return sshKey;
    }

    public void setSshKey(String sshKey) {
        this.sshKey = sshKey;
    }

    @Basic
    @Column(name = "personnel_id", nullable = true)
    public Integer getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Integer personnelId) {
        this.personnelId = personnelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessKeyEntity that = (AccessKeyEntity) o;
        return Objects.equals(sshKey, that.sshKey) && Objects.equals(personnelId, that.personnelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sshKey, personnelId);
    }

    @OneToOne
    @JoinColumn(name = "personnel_id", referencedColumnName = "id", updatable = false, insertable = false)
    public PersonnelEntity getPersonnelByPersonnelId() {
        return personnelByPersonnelId;
    }

    public void setPersonnelByPersonnelId(PersonnelEntity personnelByPersonnelId) {
        this.personnelByPersonnelId = personnelByPersonnelId;
    }
}
