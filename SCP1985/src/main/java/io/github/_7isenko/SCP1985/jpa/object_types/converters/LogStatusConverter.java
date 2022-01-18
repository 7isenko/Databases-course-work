package io.github._7isenko.SCP1985.jpa.object_types.converters;

import io.github._7isenko.SCP1985.jpa.object_types.LogStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author 7isenko
 */
@Converter(autoApply = true)
public class LogStatusConverter implements AttributeConverter<LogStatus, String> {
    @Override
    public String convertToDatabaseColumn(LogStatus logStatus) {
        return logStatus.toString();
    }

    @Override
    public LogStatus convertToEntityAttribute(String s) {
        return LogStatus.valueOf(s.replaceAll(" ", "_"));
    }
}
