package com.sh.journalmotherapp.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadFileResponse implements Serializable {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
