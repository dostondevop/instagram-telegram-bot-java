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
public class Follow extends BaseModel {
    private UUID followerId;
    private UUID followingId;
    private Date createdTime;
}