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
public class CommentEntity implements Serializable, Parcelable {

    private Long id;
    private String content;
    private String createdDate;
    private Date createdAt;
    private PostEntity post;
    private UserEntity user;

    protected CommentEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        content = in.readString();
        createdDate = in.readString();
        post = in.readParcelable(PostEntity.class.getClassLoader());
        user = in.readParcelable(UserEntity.class.getClassLoader());
    }

    public static final Creator<CommentEntity> CREATOR = new Creator<CommentEntity>() {
        @Override
        public CommentEntity createFromParcel(Parcel in) {
            return new CommentEntity(in);
        }

        @Override
        public CommentEntity[] newArray(int size) {
            return new CommentEntity[size];
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
        dest.writeString(content);
        dest.writeString(createdDate);
        dest.writeParcelable(post, flags);
        dest.writeParcelable(user, flags);
    }
}
