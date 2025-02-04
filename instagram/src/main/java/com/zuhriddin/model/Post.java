package com.zuhriddin.model;

import com.zuhriddin.enumeration.PostState;
import com.zuhriddin.enumeration.PostType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
