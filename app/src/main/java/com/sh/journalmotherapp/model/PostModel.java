package com.sh.journalmotherapp.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostModel implements Serializable {

    private String id;
    private String title;
    private String content;
    private String createdDate;
    private String imageUrl;
    private long commentsCount;
    private long likesCount;
    private long watchersCount;
    private CategoryModel categoryModel;
    private UserModel author;

}
