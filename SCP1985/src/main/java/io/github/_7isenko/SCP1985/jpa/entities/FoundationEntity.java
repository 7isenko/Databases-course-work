package io.github._7isenko.SCP1985.jpa.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@Table(name = "foundation")
public class FoundationEntity {
    private int id;
    private Integer locationId;
    private LocationEntity locationByLocationId;
    private Collection<ScpObjectEntity> scpObjectsById;

    public FoundationEntity(Integer locationId) {
        this.locationId = locationId;
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
    @Column(name = "location_id", nullable = true)
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoundationEntity that = (FoundationEntity) o;
        return id == that.id && Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationId);
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", updatable = false, insertable = false)
    public LocationEntity getLocationByLocationId() {
        return locationByLocationId;
    }

    public void setLocationByLocationId(LocationEntity locationByLocationId) {
        this.locationByLocationId = locationByLocationId;
    }

    @OneToMany(mappedBy = "foundationByFoundationId")
    public Collection<ScpObjectEntity> getScpObjectsById() {
        return scpObjectsById;
    }

    public void setScpObjectsById(Collection<ScpObjectEntity> scpObjectsById) {
        this.scpObjectsById = scpObjectsById;
    }
}
