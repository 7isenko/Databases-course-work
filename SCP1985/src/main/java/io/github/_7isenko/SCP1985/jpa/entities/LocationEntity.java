package io.github._7isenko.SCP1985.jpa.entities;

import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@Table(name = "location")
public class LocationEntity {
    private int id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Collection<FoundationEntity> foundationsById;
    private Collection<RetrievalEntity> retrievalsById;

    public LocationEntity(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = new BigDecimal(latitude);
        this.longitude = new BigDecimal(longitude);
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
    @Column(name = "latitude", nullable = true, precision = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return id == that.id && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<FoundationEntity> getFoundationsById() {
        return foundationsById;
    }

    public void setFoundationsById(Collection<FoundationEntity> foundationsById) {
        this.foundationsById = foundationsById;
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<RetrievalEntity> getRetrievalsById() {
        return retrievalsById;
    }

    public void setRetrievalsById(Collection<RetrievalEntity> retrievalsById) {
        this.retrievalsById = retrievalsById;
    }
}
