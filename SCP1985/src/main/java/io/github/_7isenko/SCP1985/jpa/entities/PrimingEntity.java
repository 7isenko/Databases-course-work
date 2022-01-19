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
@Table(name = "priming")
public class PrimingEntity {
    private int id;
    private Integer scpObjectId;
    private Integer personnelId;
    private Collection<ExcursionLogEntity> excursionLogsById;
    private ScpObjectEntity scpObjectByScpObjectId;
    private PersonnelEntity personnelByPersonnelId;

    public PrimingEntity(Integer scpObjectId, Integer personnelId) {
        this.scpObjectId = scpObjectId;
        this.personnelId = personnelId;
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
    @Column(name = "scp_object_id", nullable = true)
    public Integer getScpObjectId() {
        return scpObjectId;
    }

    public void setScpObjectId(Integer scpObjectId) {
        this.scpObjectId = scpObjectId;
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
        PrimingEntity that = (PrimingEntity) o;
        return id == that.id && Objects.equals(scpObjectId, that.scpObjectId) && Objects.equals(personnelId, that.personnelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scpObjectId, personnelId);
    }

    @OneToMany(mappedBy = "primingByPrimingId", fetch = FetchType.EAGER)
    public Collection<ExcursionLogEntity> getExcursionLogsById() {
        return excursionLogsById;
    }

    public void setExcursionLogsById(Collection<ExcursionLogEntity> excursionLogsById) {
        this.excursionLogsById = excursionLogsById;
    }

    @ManyToOne
    @JoinColumn(name = "scp_object_id", referencedColumnName = "id", updatable = false, insertable = false)
    public ScpObjectEntity getScpObjectByScpObjectId() {
        return scpObjectByScpObjectId;
    }

    public void setScpObjectByScpObjectId(ScpObjectEntity scpObjectByScpObjectId) {
        this.scpObjectByScpObjectId = scpObjectByScpObjectId;
    }

    @ManyToOne
    @JoinColumn(name = "personnel_id", referencedColumnName = "id", updatable = false, insertable = false)
    public PersonnelEntity getPersonnelByPersonnelId() {
        return personnelByPersonnelId;
    }

    public void setPersonnelByPersonnelId(PersonnelEntity personnelByPersonnelId) {
        this.personnelByPersonnelId = personnelByPersonnelId;
    }
}
