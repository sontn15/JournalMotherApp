package com.sh.journalmotherapp.network;


import com.sh.journalmotherapp.model.CommentEntity;
import com.sh.journalmotherapp.model.NewsEntity;
import com.sh.journalmotherapp.model.PostEntity;
import com.sh.journalmotherapp.model.UploadFileResponse;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.network.request.CommentRequest;
import com.sh.journalmotherapp.network.request.LoginRequest;
import com.sh.journalmotherapp.network.request.PostRequest;
import com.sh.journalmotherapp.network.request.RegisterRequest;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("users/login")
    Call<UserEntity> login(@Body LoginRequest request);

    @POST("users/register")
    Call<UserEntity> register(@Body RegisterRequest registerRequest);

    @GET("users/{userId}")
    Call<UserEntity> getUser(@Path("userId") Long userId);

    @GET("posts")
    Call<List<PostEntity>> getPosts(@Query("authorId") Long authorId, @Query("type") String type);

    @GET("posts/{postId}")
    Call<PostEntity> getPost(@Path("postId") Long postId);

    @POST("posts")
    Call<Void> addPost(@Body PostRequest request);

    @POST("posts/{postId}/comments")
    Call<Void> commentOnPost(@Path("postId") Long postId, @Body CommentRequest commentRequest);

    @GET("posts/{postId}/comments/list")
    Call<List<CommentEntity>> getAllCommentInPost(@Path("postId") Long postId);

    @Multipart
    @POST("files/upload")
    Call<UploadFileResponse> uploadFile(@Query("file") MultipartBody.Part file);

    @GET("news")
    Call<List<NewsEntity>> getAllNews(@Query("category") String category);

}
