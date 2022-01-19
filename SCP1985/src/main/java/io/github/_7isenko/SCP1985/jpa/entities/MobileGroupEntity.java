package io.github._7isenko.SCP1985.jpa.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@Table(name = "mobile_group")
public class MobileGroupEntity {
    private int id;
    private String name;
    private Timestamp created;
    private Collection<MobileGroupMembersEntity> mobileGroupMembersById;
    private Collection<RetrievalEntity> retrievalsById;

    public MobileGroupEntity(String name) {
        this.name = name;
    }

    public MobileGroupEntity(String name, Timestamp created) {
        this.name = name;
        this.created = created;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "created", nullable = true)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MobileGroupEntity that = (MobileGroupEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created);
    }

    @OneToMany(mappedBy = "mobileGroupByMobileGroupId", fetch = FetchType.EAGER)
    public Collection<MobileGroupMembersEntity> getMobileGroupMembersById() {
        return mobileGroupMembersById;
    }

    public void setMobileGroupMembersById(Collection<MobileGroupMembersEntity> mobileGroupMembersById) {
        this.mobileGroupMembersById = mobileGroupMembersById;
    }

    @OneToMany(mappedBy = "mobileGroupByMobileGroupId")
    public Collection<RetrievalEntity> getRetrievalsById() {
        return retrievalsById;
    }

    public void setRetrievalsById(Collection<RetrievalEntity> retrievalsById) {
        this.retrievalsById = retrievalsById;
    }
}
