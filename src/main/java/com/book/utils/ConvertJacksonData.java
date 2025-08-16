package com.book.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvertJacksonData implements IConvertData {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T fromJson(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to object: " + tClass.getSimpleName(), e);
        }
    }
}
