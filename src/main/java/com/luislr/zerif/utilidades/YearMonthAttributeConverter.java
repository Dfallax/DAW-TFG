package com.luislr.zerif.utilidades;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    @Override
    public String convertToDatabaseColumn(YearMonth fecha) {
        if (fecha == null) {
            return null;
        }
        return fecha.format(FORMATTER);
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return YearMonth.parse(dbData, FORMATTER);
    }
}
