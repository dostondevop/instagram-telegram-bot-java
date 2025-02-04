package com.zuhriddin.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {
    ENG("English", "Английский", "Inglizcha"),
    RU("Russian", "Русский", "Ruscha"),
    UZ("Uzbek", "Узбекский", "O'zbekcha");

    private final String valueEng;
    private final String valueRu;
    private final String valueUz;
}
