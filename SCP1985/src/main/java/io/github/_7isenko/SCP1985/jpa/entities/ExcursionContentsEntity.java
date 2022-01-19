package io.github._7isenko.SCP1985.jpa.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 7isenko
 */
@NoArgsConstructor
@Entity
@Table(name = "excursion_contents")
@IdClass(ExcursionContentsEntityPK.class)
public class ExcursionContentsEntity {
    private int excursionLogId;
    private int itemId;
    private ExcursionLogEntity excursionLogByExcursionLogId;
    private ItemEntity itemByItemId;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_log_id", nullable = false)
    public int getExcursionLogId() {
        return excursionLogId;
    }

    public void setExcursionLogId(int excursionLogId) {
        this.excursionLogId = excursionLogId;
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
        ExcursionContentsEntity that = (ExcursionContentsEntity) o;
        return excursionLogId == that.excursionLogId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(excursionLogId, itemId);
    }

    @ManyToOne
    @JoinColumn(name = "excursion_log_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public ExcursionLogEntity getExcursionLogByExcursionLogId() {
        return excursionLogByExcursionLogId;
    }

    public void setExcursionLogByExcursionLogId(ExcursionLogEntity excursionLogByExcursionLogId) {
        this.excursionLogByExcursionLogId = excursionLogByExcursionLogId;
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
