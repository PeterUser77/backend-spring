package com.backendspring.enums.converter;

import java.util.stream.Stream;

import com.backendspring.enums.Status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if(status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return findCategory(value);
    }

    public Status findCategory(String value) {
        return Stream.of(Status.values())
            .filter(s -> value.equals(s.getValue()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

}
