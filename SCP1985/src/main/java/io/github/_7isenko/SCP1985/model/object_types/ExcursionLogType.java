package io.github._7isenko.SCP1985.model.object_types;

import io.github._7isenko.SCP1985.model.misc.PostgreSQLEnumType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@TypeDef(name = "psql_enum", typeClass = PostgreSQLEnumType.class)
@NoArgsConstructor
@Entity
public class ExcursionLogType {

    @Id
    private int id;

    @ Enumerated(EnumType.STRING)
    @ Type(type = "psql_enum")
    private TriggerType trigger_type;
    private Timestamp trigger_committed;
    private String equipment;
    private String reality_description;

    @ Enumerated(EnumType.STRING)
    @ Type(type = "psql_enum")
    private LogStatus log_status;
    private String note;
    private String item;
    private int scp_object;
    private ClearanceLevel clearance_level;

    @ Enumerated(EnumType.STRING)
    @ Type(type = "psql_enum")
    private Classification classification;
    private String personnel_name;
    private Timestamp return_to_reality;

    @Nullable
    private Timestamp return_to_foundation;
    private Boolean succeed;
    private String mobile_group;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public ExcursionLogType(int id, TriggerType trigger_type, Timestamp trigger_committed,
                            String equipment, String reality_description, LogStatus log_status,
                            String note, String item, int scp_object, ClearanceLevel clearance_level,
                            Classification classification, String personnel_name,
                            Timestamp return_to_reality, Timestamp return_to_foundation, Boolean succeed,
                            String mobile_group, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.trigger_type = trigger_type;
        this.trigger_committed = trigger_committed;
        this.equipment = equipment;
        this.reality_description = reality_description;
        this.log_status = log_status;
        this.note = note;
        this.item = item;
        this.scp_object = scp_object;
        this.clearance_level = clearance_level;
        this.classification = classification;
        this.personnel_name = personnel_name;
        this.return_to_reality = return_to_reality;
        this.return_to_foundation = return_to_foundation;
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

    public TriggerType getTrigger_type() {
        return trigger_type;
    }

    public void setTrigger_type(TriggerType trigger_type) {
        this.trigger_type = trigger_type;
    }

    public Timestamp getTrigger_committed() {
        return trigger_committed;
    }

    public void setTrigger_committed(Timestamp trigger_committed) {
        this.trigger_committed = trigger_committed;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getReality_description() { return reality_description; }

    public void setReality_description(String reality_description) { this.reality_description = reality_description; }

    public LogStatus getLog_status() { return log_status; }

    public void setLog_status(LogStatus log_status) { this.log_status = log_status; }

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

    public ClearanceLevel getClearance_level() { return clearance_level; }

    public void setClearance_level(ClearanceLevel clearance_level) { this.clearance_level = clearance_level; }

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

    public Timestamp getReturn_to_reality() {
        return return_to_reality;
    }

    public void setReturn_to_reality(Timestamp return_to_reality) {
        this.return_to_reality = return_to_reality;
    }

    public Timestamp getReturn_to_foundation() { return return_to_foundation; }

    public void setReturn_to_foundation(Timestamp return_to_foundation) { this.return_to_foundation = return_to_foundation; }

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
                ", trigger_type=" + trigger_type +
                ", trigger_committed=" + trigger_committed +
                ", equipment='" + equipment + '\'' +
                ", realityDescription='" + reality_description + '\'' +
                ", logStatus=" + log_status +
                ", note='" + note + '\'' +
                ", item='" + item + '\'' +
                ", scp_object=" + scp_object +
                ", clearanceLevel=" + clearance_level +
                ", classification=" + classification +
                ", personnel_name='" + personnel_name + '\'' +
                ", return_to_reality=" + return_to_reality +
                ", return_to_foundation=" + return_to_foundation +
                ", succeed=" + succeed +
                ", mobile_group='" + mobile_group + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
