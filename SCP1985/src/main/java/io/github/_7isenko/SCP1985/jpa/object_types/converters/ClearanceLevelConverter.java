package io.github._7isenko.SCP1985.jpa.object_types.converters;

import io.github._7isenko.SCP1985.jpa.object_types.ClearanceLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author 7isenko
 */
@Converter(autoApply = true)
public class ClearanceLevelConverter implements AttributeConverter<ClearanceLevel, String> {
    @Override
    public String convertToDatabaseColumn(ClearanceLevel clearanceLevel) {
        return clearanceLevel.toString();
    }

    @Override
    public ClearanceLevel convertToEntityAttribute(String s) {
        for (ClearanceLevel clearanceLevel : ClearanceLevel.values()) {
            if (clearanceLevel.toString().equals(s))
                return clearanceLevel;
        }
        return null;
    }
}
