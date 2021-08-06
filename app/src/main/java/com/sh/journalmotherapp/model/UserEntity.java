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
public class UserEntity implements Serializable, Parcelable {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String mobile;
    private String email;
    private String address;
    private String imageUrl;
    private Integer yearOfBirth;
    private String kernelStatus;    //Trang thai hon nhan
    private String votingStatus;    //Trang thai bau
    private String numberBaby;

    protected UserEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        username = in.readString();
        password = in.readString();
        fullName = in.readString();
        mobile = in.readString();
        email = in.readString();
        address = in.readString();
        imageUrl = in.readString();
        if (in.readByte() == 0) {
            yearOfBirth = null;
        } else {
            yearOfBirth = in.readInt();
        }
        kernelStatus = in.readString();
        votingStatus = in.readString();
        numberBaby = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(imageUrl);
        if (yearOfBirth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(yearOfBirth);
        }
        dest.writeString(kernelStatus);
        dest.writeString(votingStatus);
        dest.writeString(numberBaby);
    }
}
