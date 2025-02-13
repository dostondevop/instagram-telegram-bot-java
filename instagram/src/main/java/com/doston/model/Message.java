package com.doston.model;

import lombok.*;
import java.util.Date;
import java.util.UUID;
import com.doston.enumeration.PostType;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseModel {
    private String text;
    private UUID fromId;
    private String path;
    private UUID toChatId;
    private PostType postType;
    private Date createdDate;
}