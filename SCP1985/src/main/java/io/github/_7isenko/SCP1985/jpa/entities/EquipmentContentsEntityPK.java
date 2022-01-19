package io.github._7isenko.SCP1985.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 7isenko
 */
public class EquipmentContentsEntityPK implements Serializable {
    private int equipmentId;
    private int itemId;

    @Column(name = "equipment_id", nullable = false)
    @Id
    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Column(name = "item_id", nullable = false)
    @Id
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentContentsEntityPK that = (EquipmentContentsEntityPK) o;
        return equipmentId == that.equipmentId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentId, itemId);
    }
}
