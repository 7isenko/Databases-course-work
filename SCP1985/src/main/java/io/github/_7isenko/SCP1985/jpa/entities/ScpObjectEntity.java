package io.github._7isenko.SCP1985.jpa.entities;

import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@Entity
@Table(name = "scp_object")
public class ScpObjectEntity {
    private int id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ObjectCLass objectClass;
    private Integer foundationId;
    private Collection<PrimingEntity> primingsById;
    private FoundationEntity foundationByFoundationId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "object_class", nullable = true)
    public ObjectCLass getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(ObjectCLass objectClass) {
        this.objectClass = objectClass;
    }

    @Basic
    @Column(name = "foundation_id", nullable = true, updatable = false, insertable = false)
    public Integer getFoundationId() {
        return foundationId;
    }

    public void setFoundationId(Integer foundationId) {
        this.foundationId = foundationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScpObjectEntity that = (ScpObjectEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(objectClass, that.objectClass) && Objects.equals(foundationId, that.foundationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, objectClass, foundationId);
    }

    @OneToMany(mappedBy = "scpObjectByScpObjectId")
    public Collection<PrimingEntity> getPrimingsById() {
        return primingsById;
    }

    public void setPrimingsById(Collection<PrimingEntity> primingsById) {
        this.primingsById = primingsById;
    }

    @ManyToOne
    @JoinColumn(name = "foundation_id", referencedColumnName = "id")
    public FoundationEntity getFoundationByFoundationId() {
        return foundationByFoundationId;
    }

    public void setFoundationByFoundationId(FoundationEntity foundationByFoundationId) {
        this.foundationByFoundationId = foundationByFoundationId;
    }
}
