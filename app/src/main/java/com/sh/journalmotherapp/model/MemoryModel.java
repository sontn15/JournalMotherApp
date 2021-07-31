package com.sh.journalmotherapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoryModel {

    private String id;
    private String content;
    private String emotion;
    private String imageUrl;
    private String createdDate;
    private UserModel userModel;

}
