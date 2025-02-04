package com.zuhriddin.model;

import com.zuhriddin.enumeration.Gender;
import com.zuhriddin.enumeration.Language;
import com.zuhriddin.enumeration.RegisterStep;
import com.zuhriddin.enumeration.UserState;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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
