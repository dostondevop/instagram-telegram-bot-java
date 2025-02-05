package com.doston.service;

import com.doston.model.BaseModel;

import java.util.*;

public interface BaseService<T extends BaseModel, R> {

    default T add(T t) {
        List<T> list = read();
        if (!has(list, t)) {
            list.add(t);
            write(list);
            return t;
        }
        throw new RuntimeException("is already exist.");
    }

    default boolean has(List<T> list, T t) {
        return true;
    }

    default T get(R id) {
        return read().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    default List<T> list() {
        return read();
    }

    default void delete(R id) {
        List<T> list = read();

        read().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .ifPresent(list::remove);
        write(list);
    }

    List<T> read();

    void write(List<T> list);
}
