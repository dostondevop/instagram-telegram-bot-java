package com.doston.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Like extends BaseModel {
    private UUID userId;
    private String username;
    private UUID postId;
    private Date createdDate;
}
