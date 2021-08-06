package com.sh.journalmotherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity implements Serializable, Parcelable {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String createdDate;
    private Date createdAt;
    private String mode;        //che do dang: public / private
    private String type;
    private Boolean isAnonymous;
    private UserEntity author;


    protected PostEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        content = in.readString();
        imageUrl = in.readString();
        createdDate = in.readString();
        mode = in.readString();
        type = in.readString();
        byte tmpIsAnonymous = in.readByte();
        isAnonymous = tmpIsAnonymous == 0 ? null : tmpIsAnonymous == 1;
        author = in.readParcelable(UserEntity.class.getClassLoader());
    }

    public static final Creator<PostEntity> CREATOR = new Creator<PostEntity>() {
        @Override
        public PostEntity createFromParcel(Parcel in) {
            return new PostEntity(in);
        }

        @Override
        public PostEntity[] newArray(int size) {
            return new PostEntity[size];
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
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(createdDate);
        dest.writeString(mode);
        dest.writeString(type);
        dest.writeByte((byte) (isAnonymous == null ? 0 : isAnonymous ? 1 : 2));
        dest.writeParcelable(author, flags);
    }
}
