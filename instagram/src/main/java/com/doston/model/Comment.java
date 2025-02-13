package com.doston.model;

import lombok.*;
import java.util.Date;
import java.util.UUID;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseModel {
    private UUID userId;
    private UUID postId;
    private String text;
    private UUID parentId;
    private String username;
    private int stepHierarchy;
    private Date createdDate;
}