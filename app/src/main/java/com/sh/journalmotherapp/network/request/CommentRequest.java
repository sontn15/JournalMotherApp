package com.sh.journalmotherapp.network.request;

import lombok.Data;

@Data
public class CommentRequest {

    private String content;
    private Long commentUserId;

}
