package com.doston.model;

import com.doston.enumeration.NotificationType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseModel {
    private UUID clpId;
    private NotificationType notificationType;
    private UUID userId;
    private String username;
    private Date createdDate;
}
