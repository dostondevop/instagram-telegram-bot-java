package com.zuhriddin.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Chat extends BaseModel {
    private UUID user1Id;
    private UUID user2Id;
}
