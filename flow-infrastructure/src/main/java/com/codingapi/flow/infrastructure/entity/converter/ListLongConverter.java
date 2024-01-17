package com.codingapi.flow.infrastructure.entity.converter;

import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

import java.util.List;

public class ListLongConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
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
        if (!StringUtils.hasLength(dbData)) {
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
