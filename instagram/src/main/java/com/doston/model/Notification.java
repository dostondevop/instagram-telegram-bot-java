package com.doston.model;

import lombok.*;
import java.util.Date;
import java.util.UUID;
import lombok.experimental.SuperBuilder;
import com.doston.enumeration.NotificationType;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseModel {
    private UUID clpId;
    private UUID userId;
    private String username;
    private Date createdDate;
    private NotificationType notificationType;
}