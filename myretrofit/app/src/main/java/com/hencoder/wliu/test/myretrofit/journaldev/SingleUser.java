package com.hencoder.wliu.test.myretrofit.journaldev;

import com.google.gson.annotations.SerializedName;

public class SingleUser {

    @SerializedName("data")
    public UserDetail data;

    public static class UserDetail {

        @SerializedName("id")
        public String id;

        @SerializedName("first_name")
        public String first_name;

        @SerializedName("last_name")
        public String last_name;

        @SerializedName("avatar")
        public String avatar;


    }
}
