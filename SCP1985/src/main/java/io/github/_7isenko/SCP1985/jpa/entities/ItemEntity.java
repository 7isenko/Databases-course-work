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
@Table(name = "item")
public class ItemEntity {
    private int id;
    private String name;
    private Collection<EquipmentContentsEntity> equipmentContentsById;
    private Collection<ExcursionContentsEntity> excursionContentsById;

    public ItemEntity(String name) {
        this.name = name;
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
    @Column(name = "name", nullable = true, length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "itemByItemId")
    public Collection<EquipmentContentsEntity> getEquipmentContentsById() {
        return equipmentContentsById;
    }

    public void setEquipmentContentsById(Collection<EquipmentContentsEntity> equipmentContentsById) {
        this.equipmentContentsById = equipmentContentsById;
    }

    @OneToMany(mappedBy = "itemByItemId")
    public Collection<ExcursionContentsEntity> getExcursionContentsById() {
        return excursionContentsById;
    }

    public void setExcursionContentsById(Collection<ExcursionContentsEntity> excursionContentsById) {
        this.excursionContentsById = excursionContentsById;
    }
}
