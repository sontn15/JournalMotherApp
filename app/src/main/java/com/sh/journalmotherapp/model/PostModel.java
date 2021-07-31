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
public class PostModel implements Serializable, Parcelable {

    private String id;
    private String title;
    private String content;
    private String createdDate;
    private String imageUrl;
    private UserModel author;

    protected PostModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
        createdDate = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(createdDate);
        dest.writeString(imageUrl);
    }
}
