package com.codingapi.flow.infrastructure.entity.converter;

import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListLongConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Long aLong : attribute) {
            sb.append(aLong).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String[] split = dbData.split(",");
        List<Long> list = new java.util.ArrayList<>();
        for (String s : split) {
            list.add(Long.parseLong(s));
        }
        return list;
    }


}
