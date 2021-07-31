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
public class CommentModel implements Serializable, Parcelable {

    private String id;
    private String content;
    private String createdDate;
    private UserModel userComment;
    private PostModel post;

    protected CommentModel(Parcel in) {
        id = in.readString();
        content = in.readString();
        createdDate = in.readString();
        userComment = in.readParcelable(UserModel.class.getClassLoader());
        post = in.readParcelable(PostModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(createdDate);
        dest.writeParcelable(userComment, flags);
        dest.writeParcelable(post, flags);
    }
}
