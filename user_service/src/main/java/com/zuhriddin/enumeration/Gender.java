package com.zuhriddin.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("Male", "Мужчина", "Erkak"),
    FEMALE("Female", "Женщина", "Ayol");

    private final String valueEng;
    private final String valueRu;
    private final String valueUz;
}
