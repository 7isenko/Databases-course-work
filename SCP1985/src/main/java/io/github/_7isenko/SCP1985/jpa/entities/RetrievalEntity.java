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
@Table(name = "retrieval")
public class RetrievalEntity {
    private int id;
    private Integer locationId;
    private Integer mobileGroupId;
    private Timestamp returnToReality;
    private Timestamp returnToFoundation;
    private Boolean succeed;
    private Collection<ExcursionLogEntity> excursionLogsById;
    private LocationEntity locationByLocationId;
    private MobileGroupEntity mobileGroupByMobileGroupId;

    @Id
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

    @Basic
    @Column(name = "mobile_group_id", nullable = true)
    public Integer getMobileGroupId() {
        return mobileGroupId;
    }

    public void setMobileGroupId(Integer mobileGroupId) {
        this.mobileGroupId = mobileGroupId;
    }

    @Basic
    @Column(name = "return_to_reality", nullable = true)
    public Timestamp getReturnToReality() {
        return returnToReality;
    }

    public void setReturnToReality(Timestamp returnToReality) {
        this.returnToReality = returnToReality;
    }

    @Basic
    @Column(name = "return_to_foundation", nullable = true)
    public Timestamp getReturnToFoundation() {
        return returnToFoundation;
    }

    public void setReturnToFoundation(Timestamp returnToFoundation) {
        this.returnToFoundation = returnToFoundation;
    }

    @Basic
    @Column(name = "succeed", nullable = true)
    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrievalEntity that = (RetrievalEntity) o;
        return id == that.id && Objects.equals(locationId, that.locationId) && Objects.equals(mobileGroupId, that.mobileGroupId) && Objects.equals(returnToReality, that.returnToReality) && Objects.equals(returnToFoundation, that.returnToFoundation) && Objects.equals(succeed, that.succeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationId, mobileGroupId, returnToReality, returnToFoundation, succeed);
    }

    @OneToMany(mappedBy = "retrievalByRetrievalId")
    public Collection<ExcursionLogEntity> getExcursionLogsById() {
        return excursionLogsById;
    }

    public void setExcursionLogsById(Collection<ExcursionLogEntity> excursionLogsById) {
        this.excursionLogsById = excursionLogsById;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", updatable = false, insertable = false)
    public LocationEntity getLocationByLocationId() {
        return locationByLocationId;
    }

    public void setLocationByLocationId(LocationEntity locationByLocationId) {
        this.locationByLocationId = locationByLocationId;
    }

    @ManyToOne
    @JoinColumn(name = "mobile_group_id", referencedColumnName = "id", updatable = false, insertable = false)
    public MobileGroupEntity getMobileGroupByMobileGroupId() {
        return mobileGroupByMobileGroupId;
    }

    public void setMobileGroupByMobileGroupId(MobileGroupEntity mobileGroupByMobileGroupId) {
        this.mobileGroupByMobileGroupId = mobileGroupByMobileGroupId;
    }
}
