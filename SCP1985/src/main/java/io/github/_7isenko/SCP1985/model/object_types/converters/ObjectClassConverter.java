package io.github._7isenko.SCP1985.model.object_types.converters;

import io.github._7isenko.SCP1985.model.object_types.ObjectCLass;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author 7isenko
 */
@Converter
public class ObjectClassConverter implements AttributeConverter<ObjectCLass, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ObjectCLass objectCLass) {
        return objectCLass.ordinal();
    }

    @Override
    public ObjectCLass convertToEntityAttribute(Integer i) {
        return ObjectCLass.values()[i];
    }
}
