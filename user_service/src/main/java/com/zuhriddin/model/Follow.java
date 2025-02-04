package com.zuhriddin.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Follow extends BaseModel {
    private UUID followerId;
    private UUID followingId;
    private Date createdTime;
}
