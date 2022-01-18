package io.github._7isenko.SCP1985.jpa.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 7isenko
 */
@Entity
@Table(name = "equipment_contents")
@IdClass(EquipmentContentsEntityPK.class)
public class EquipmentContentsEntity {
    private int equipmentId;
    private int itemId;
    private EquipmentEntity equipmentByEquipmentId;
    private ItemEntity itemByItemId;

    @Id
    @Column(name = "equipment_id", nullable = false)
    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Id
    @Column(name = "item_id", nullable = false)
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
        EquipmentContentsEntity that = (EquipmentContentsEntity) o;
        return equipmentId == that.equipmentId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentId, itemId);
    }

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public EquipmentEntity getEquipmentByEquipmentId() {
        return equipmentByEquipmentId;
    }

    public void setEquipmentByEquipmentId(EquipmentEntity equipmentByEquipmentId) {
        this.equipmentByEquipmentId = equipmentByEquipmentId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public ItemEntity getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(ItemEntity itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}
