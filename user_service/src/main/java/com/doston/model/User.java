package com.doston.model;

import lombok.*;
import java.util.Date;
import com.doston.enumeration.Gender;
import com.doston.enumeration.Language;
import lombok.experimental.SuperBuilder;
import com.doston.enumeration.RegisterStep;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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