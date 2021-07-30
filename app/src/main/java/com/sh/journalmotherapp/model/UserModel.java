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
public class UserModel implements Serializable {

    private String id;
    private String mobile;
    private String address;
    private String fullName;
    private String birthDay;
    private String username;
    private String password;
    private String imageUrl;
    private long likesCount;

}
