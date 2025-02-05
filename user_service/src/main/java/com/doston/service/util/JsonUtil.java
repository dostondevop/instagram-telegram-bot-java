package com.doston.service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.List;

@UtilityClass
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public <T> void write(String path, T t) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(path), t);
    }

    @SneakyThrows
    public <T> List<T> read(String path, TypeReference<List<T>> typeReference) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.readValue(new File(path), typeReference);
    }
}
