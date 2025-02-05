package com.doston.model;

import com.doston.enumeration.PostType;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
