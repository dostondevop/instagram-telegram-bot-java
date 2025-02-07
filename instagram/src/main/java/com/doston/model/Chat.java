package com.doston.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Chat extends BaseModel {
    private UUID user1Id;
    private UUID user2Id;
}