package com.sh.journalmotherapp.network.request;

import lombok.Data;

@Data
public class PostRequest {

    private String title;
    private String content;
    private String imageUrl;
    private String mode;
    private String type;
    private Long authorId;
    private Boolean isAnonymous;

}
