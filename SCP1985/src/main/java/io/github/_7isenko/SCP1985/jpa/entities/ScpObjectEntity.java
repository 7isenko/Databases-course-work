package io.github._7isenko.SCP1985.jpa.entities;

import io.github._7isenko.SCP1985.jpa.misc.PostgreSQLEnumType;
import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@TypeDef(name = "psql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "scp_object")
public class ScpObjectEntity {
    private int id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING) @Type(type = "psql_enum")
    private ObjectCLass objectClass;
    private Integer foundationId;
    private Collection<PrimingEntity> primingsById;
    private FoundationEntity foundationByFoundationId;

    public ScpObjectEntity(int id, String name, String description, ObjectCLass objectClass, Integer foundationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.objectClass = objectClass;
        this.foundationId = foundationId;
    }

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

    @Enumerated(EnumType.STRING) @Type(type = "psql_enum")
    @Column(name = "object_class",  nullable = true)
    public ObjectCLass getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(ObjectCLass objectClass) {
        this.objectClass = objectClass;
    }

    @Basic
    @Column(name = "foundation_id", nullable = true)
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
    @JoinColumn(name = "foundation_id", referencedColumnName = "id", updatable = false, insertable = false)
    public FoundationEntity getFoundationByFoundationId() {
        return foundationByFoundationId;
    }

    public void setFoundationByFoundationId(FoundationEntity foundationByFoundationId) {
        this.foundationByFoundationId = foundationByFoundationId;
    }
}
