package com.doston.model;

import lombok.*;
import java.util.UUID;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Chat extends BaseModel {
    private UUID user1Id;
    private UUID user2Id;
}