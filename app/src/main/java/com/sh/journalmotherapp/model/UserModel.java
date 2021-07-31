package com.sh.journalmotherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable, Parcelable {

    private String id;
    private String mobile;
    private String address;
    private String fullName;
    private String birthDay;
    private String username;
    private String password;
    private String imageUrl;
    private long likesCount;

    protected UserModel(Parcel in) {
        id = in.readString();
        mobile = in.readString();
        address = in.readString();
        fullName = in.readString();
        birthDay = in.readString();
        username = in.readString();
        password = in.readString();
        imageUrl = in.readString();
        likesCount = in.readLong();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mobile);
        dest.writeString(address);
        dest.writeString(fullName);
        dest.writeString(birthDay);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(imageUrl);
        dest.writeLong(likesCount);
    }
}
