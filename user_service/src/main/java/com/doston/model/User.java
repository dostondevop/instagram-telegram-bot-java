package com.doston.model;

import com.doston.enumeration.Gender;
import com.doston.enumeration.Language;
import com.doston.enumeration.RegisterStep;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseModel {
    private Long chatId;
    private String name;
    private String username;
    private String link;
    private Gender gender;
    private Date birthDate;
    private String bio;
    private String phoneNumber;
    private String email;
    private String password;
    private String imagePath;
    private Date createdDate;
    private Date lastActiveDate;
    private RegisterStep registerStep;
    private Language language;
}
