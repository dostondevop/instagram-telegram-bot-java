package com.doston.enumeration;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("Male", "Мужчина", "Erkak"),
    FEMALE("Female", "Женщина", "Ayol");

    private final String valueEng;
    private final String valueRu;
    private final String valueUz;
}