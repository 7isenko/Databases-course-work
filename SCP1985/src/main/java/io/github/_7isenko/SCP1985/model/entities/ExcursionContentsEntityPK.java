package io.github._7isenko.SCP1985.model.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 7isenko
 */
public class ExcursionContentsEntityPK implements Serializable {
    private int excursionLogId;
    private int itemId;

    @Column(name = "excursion_log_id", nullable = false)
    @Id
    public int getExcursionLogId() {
        return excursionLogId;
    }

    public void setExcursionLogId(int excursionLogId) {
        this.excursionLogId = excursionLogId;
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
        ExcursionContentsEntityPK that = (ExcursionContentsEntityPK) o;
        return excursionLogId == that.excursionLogId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(excursionLogId, itemId);
    }
}
