package com.sh.journalmotherapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {

    private String id;
    private String content;
    private String createdDate;
    private String imageUrl;
    private CategoryModel categoryModel;
    private UserModel userOwner;

}
