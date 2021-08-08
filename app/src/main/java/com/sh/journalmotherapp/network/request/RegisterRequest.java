package com.sh.journalmotherapp.network.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String imageUrl;

    private Integer yearOfBirth;

    private String kernelStatus;    //Trang thai hon nhan

    private String votingStatus;    //Trang thai bau

    private String numberBaby;

}
