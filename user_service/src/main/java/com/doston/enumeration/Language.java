package com.doston.enumeration;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum Language {
    ENG("English", "Английский", "Inglizcha"),
    RU("Russian", "Русский", "Ruscha"),
    UZ("Uzbek", "Узбекский", "O'zbekcha");

    private final String valueEng;
    private final String valueRu;
    private final String valueUz;
}