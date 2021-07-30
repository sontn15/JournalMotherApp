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
public class NewsModel implements Serializable {

    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private String category;

}
