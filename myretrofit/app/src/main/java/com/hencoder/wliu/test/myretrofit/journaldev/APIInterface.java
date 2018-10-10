package com.hencoder.wliu.test.myretrofit.journaldev;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") int page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<User> doCreateUserWithField(@Field("name") String name, @Field("job") String job, @Field("id") String id);

    @GET("/api/users/{id}")
    Call<SingleUser> getUserById(@Path("id") int id);
}

