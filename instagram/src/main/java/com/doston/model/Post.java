package com.doston.model;

import lombok.*;
import java.util.Date;
import java.util.UUID;
import com.doston.enumeration.PostType;
import com.doston.enumeration.PostState;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseModel {
    private UUID userId;
    private String path;
    private String title;
    private String username;
    private String location;
    private Date createdDate;
    private PostType postType;
    private PostState postState;
}