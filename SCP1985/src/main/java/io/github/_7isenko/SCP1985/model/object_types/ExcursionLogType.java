package io.github._7isenko.SCP1985.model.object_types;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@Entity
public class ExcursionLogType {

    @Id
    private int id;
    private TriggerType triggerType;
    private Timestamp triggerCommitted;
    private String equipment;
    private String realityDescription;
    private LogStatus logStatus;
    private String note;
    private String item;
    private int scp_object;
    private ClearanceLevel clearanceLevel;
    private Classification classification;
    private String personnel_name;
    private Timestamp returnToReality;
    private Timestamp returnToFoundation;
    private Boolean succeed;
    private String mobile_group;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public ExcursionLogType(int id, TriggerType triggerType, Timestamp triggerCommitted,
                            String equipment, String realityDescription, LogStatus logStatus,
                            String note, String item, int scp_object, ClearanceLevel clearanceLevel,
                            Classification classification, String personnel_name,
                            Timestamp returnToReality, Timestamp returnToFoundation, Boolean succeed,
                            String mobile_group, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.triggerType = triggerType;
        this.triggerCommitted = triggerCommitted;
        this.equipment = equipment;
        this.realityDescription = realityDescription;
        this.logStatus = logStatus;
        this.note = note;
        this.item = item;
        this.scp_object = scp_object;
        this.clearanceLevel = clearanceLevel;
        this.classification = classification;
        this.personnel_name = personnel_name;
        this.returnToReality = returnToReality;
        this.returnToFoundation = returnToFoundation;
        this.succeed = succeed;
        this.mobile_group = mobile_group;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public Timestamp getTriggerCommitted() {
        return triggerCommitted;
    }

    public void setTriggerCommitted(Timestamp triggerCommitted) {
        this.triggerCommitted = triggerCommitted;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getRealityDescription() {
        return realityDescription;
    }

    public void setRealityDescription(String realityDescription) {
        this.realityDescription = realityDescription;
    }

    public LogStatus getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getScp_object() {
        return scp_object;
    }

    public void setScp_object(int scp_object) {
        this.scp_object = scp_object;
    }

    public ClearanceLevel getClearanceLevel() {
        return clearanceLevel;
    }

    public void setClearanceLevel(ClearanceLevel clearanceLevel) {
        this.clearanceLevel = clearanceLevel;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public String getPersonnel_name() {
        return personnel_name;
    }

    public void setPersonnel_name(String personnel_name) {
        this.personnel_name = personnel_name;
    }

    public Timestamp getReturnToReality() {
        return returnToReality;
    }

    public void setReturnToReality(Timestamp returnToReality) {
        this.returnToReality = returnToReality;
    }

    public Timestamp getReturnToFoundation() {
        return returnToFoundation;
    }

    public void setReturnToFoundation(Timestamp returnToFoundation) {
        this.returnToFoundation = returnToFoundation;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public String getMobile_group() {
        return mobile_group;
    }

    public void setMobile_group(String mobile_group) {
        this.mobile_group = mobile_group;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ExcursionLogType{" +
                "id=" + id +
                ", triggerType=" + triggerType +
                ", triggerCommitted=" + triggerCommitted +
                ", equipment='" + equipment + '\'' +
                ", realityDescription='" + realityDescription + '\'' +
                ", logStatus=" + logStatus +
                ", note='" + note + '\'' +
                ", item='" + item + '\'' +
                ", scp_object=" + scp_object +
                ", clearanceLevel=" + clearanceLevel +
                ", classification=" + classification +
                ", personnel_name='" + personnel_name + '\'' +
                ", returnToReality=" + returnToReality +
                ", returnToFoundation=" + returnToFoundation +
                ", succeed=" + succeed +
                ", mobile_group='" + mobile_group + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
