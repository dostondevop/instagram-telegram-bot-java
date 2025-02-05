package com.doston.model;

import com.doston.enumeration.PostState;
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
public class Post extends BaseModel {
    private UUID userId;
    private String username;
    private String title;
    private String location;
    private String path;
    private Date createdDate;
    private PostState postState;
    private PostType postType;
}
