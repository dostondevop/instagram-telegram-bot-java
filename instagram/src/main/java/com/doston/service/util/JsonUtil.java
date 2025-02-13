package com.doston.service.util;

import java.util.*;
import java.io.File;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@UtilityClass
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public <T> void write(String path, T t) {
        objectMapper.writeValue(new File(path), t);
    }

    @SneakyThrows
    public <T> List<T> read(String path, TypeReference<List<T>> typeReference) {
        return objectMapper.readValue(new File(path), typeReference);
    }
}