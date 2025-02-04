package com.zuhriddin.model;

import com.zuhriddin.enumeration.PostType;
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
public class Message extends BaseModel {
    private String text;
    private UUID fromId;
    private UUID toChatId;
    private String path;
    private PostType postType;
    private Date createdDate;
}
