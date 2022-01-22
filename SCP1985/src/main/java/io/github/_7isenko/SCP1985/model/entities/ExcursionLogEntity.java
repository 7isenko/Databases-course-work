package io.github._7isenko.SCP1985.model.entities;

import io.github._7isenko.SCP1985.model.misc.PostgreSQLEnumType;
import io.github._7isenko.SCP1985.model.object_types.LogStatus;
import io.github._7isenko.SCP1985.model.object_types.TriggerType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@TypeDef(name = "psql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "excursion_log")
public class ExcursionLogEntity {
    private int id;
    private TriggerType triggerType;
    private Timestamp triggerCommitted;
    private Integer equipmentId;
    private String realityDescription;
    private LogStatus logStatus;
    private Integer retrievalId;
    private String note;
    private Integer primingId;
    private Collection<ExcursionContentsEntity> excursionContentsById;
    private EquipmentEntity equipmentByEquipmentId;
    private RetrievalEntity retrievalByRetrievalId;
    private PrimingEntity primingByPrimingId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING) @Type(type = "psql_enum")
    @Column(name = "trigger_type", nullable = true)
    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    @Basic
    @Column(name = "trigger_committed", nullable = true)
    public Timestamp getTriggerCommitted() {
        return triggerCommitted;
    }

    public void setTriggerCommitted(Timestamp triggerCommitted) {
        this.triggerCommitted = triggerCommitted;
    }

    @Basic
    @Column(name = "equipment_id", nullable = true)
    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Basic
    @Column(name = "reality_description", nullable = true, length = -1)
    public String getRealityDescription() {
        return realityDescription;
    }

    public void setRealityDescription(String realityDescription) {
        this.realityDescription = realityDescription;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "log_status", nullable = true)
    public LogStatus getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }

    @Basic
    @Column(name = "retrieval_id", nullable = true)
    public Integer getRetrievalId() {
        return retrievalId;
    }

    public void setRetrievalId(Integer retrievalId) {
        this.retrievalId = retrievalId;
    }

    @Basic
    @Column(name = "note", nullable = true, length = -1)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "priming_id", nullable = true)
    public Integer getPrimingId() {
        return primingId;
    }

    public void setPrimingId(Integer primingId) {
        this.primingId = primingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcursionLogEntity that = (ExcursionLogEntity) o;
        return id == that.id && Objects.equals(triggerType, that.triggerType) && Objects.equals(triggerCommitted, that.triggerCommitted) && Objects.equals(equipmentId, that.equipmentId) && Objects.equals(realityDescription, that.realityDescription) && Objects.equals(logStatus, that.logStatus) && Objects.equals(retrievalId, that.retrievalId) && Objects.equals(note, that.note) && Objects.equals(primingId, that.primingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, triggerType, triggerCommitted, equipmentId, realityDescription, logStatus, retrievalId, note, primingId);
    }

    @OneToMany(mappedBy = "excursionLogByExcursionLogId")
    public Collection<ExcursionContentsEntity> getExcursionContentsById() {
        return excursionContentsById;
    }

    public void setExcursionContentsById(Collection<ExcursionContentsEntity> excursionContentsById) {
        this.excursionContentsById = excursionContentsById;
    }

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", updatable = false, insertable = false)
    public EquipmentEntity getEquipmentByEquipmentId() {
        return equipmentByEquipmentId;
    }

    public void setEquipmentByEquipmentId(EquipmentEntity equipmentByEquipmentId) {
        this.equipmentByEquipmentId = equipmentByEquipmentId;
    }

    @ManyToOne
    @JoinColumn(name = "retrieval_id", referencedColumnName = "id", updatable = false, insertable = false)
    public RetrievalEntity getRetrievalByRetrievalId() {
        return retrievalByRetrievalId;
    }

    public void setRetrievalByRetrievalId(RetrievalEntity retrievalByRetrievalId) {
        this.retrievalByRetrievalId = retrievalByRetrievalId;
    }

    @ManyToOne
    @JoinColumn(name = "priming_id", referencedColumnName = "id", updatable = false, insertable = false)
    public PrimingEntity getPrimingByPrimingId() {
        return primingByPrimingId;
    }

    public void setPrimingByPrimingId(PrimingEntity primingByPrimingId) {
        this.primingByPrimingId = primingByPrimingId;
    }
}
