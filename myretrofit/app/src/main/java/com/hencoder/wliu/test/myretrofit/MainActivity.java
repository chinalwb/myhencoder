package com.hencoder.wliu.test.myretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hencoder.wliu.test.myretrofit.journaldev.APIClient;
import com.hencoder.wliu.test.myretrofit.journaldev.APIInterface;
import com.hencoder.wliu.test.myretrofit.journaldev.MultipleResource;
import com.hencoder.wliu.test.myretrofit.journaldev.SingleUser;
import com.hencoder.wliu.test.myretrofit.journaldev.User;
import com.hencoder.wliu.test.myretrofit.journaldev.UserList;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.RealInterceptorChain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY_RETROFIT";

    private TextView responseText;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = findViewById(R.id.responseText);
        apiInterface = APIClient.getClient().create(APIInterface.class);


    }

    @Override
    protected void onResume() {
        super.onResume();
        callAPI();
    }

    private void callAPI() {

        // ------ /api/unknown
//        Call<MultipleResource> call = apiInterface.doGetListResources();
//        call.enqueue(new Callback<MultipleResource>() {
//            @Override
//            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {
//                Log.d(TAG, response.code() + "");
//
//                String displayResponse = "";
//                MultipleResource resource = response.body();
//                Integer page = resource.page;
//                Integer total = resource.total;
//                Integer totalPages = resource.totalPages;
//                List<MultipleResource.Datum> datumList = resource.data;
//
//                displayResponse += "Page: " + page
//                        + "\n Total: " + total
//                        + "\n Total Pages " + totalPages
//                        + "\n";
//
//                for (MultipleResource.Datum datum : datumList) {
//                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
//                }
//
//                responseText.setText(displayResponse);
//            }
//
//            @Override
//            public void onFailure(Call<MultipleResource> call, Throwable t) {
//                call.cancel();
//            }
//        });
//
//        // ------- CREATE USER: /api/users
//        User user = new User("morpheus", "leader");
//        Call<User> call_1 = apiInterface.createUser(user);
//        call_1.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user1 = response.body();
//                Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                call.cancel();
//            }
//        });
//
//        // ------- USER LIST: /api/users?page=x
//        Call<UserList> call_2 = apiInterface.doGetUserList(2);
//        call_2.enqueue(new Callback<UserList>() {
//            @Override
//            public void onResponse(Call<UserList> call, Response<UserList> response) {
//                UserList userList = response.body();
//                Integer text = userList.page;
//                Integer total = userList.total;
//                Integer totalPages = userList.totalPages;
//                List<UserList.Datum> datumList = userList.data;
//                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();
//
//                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserList> call, Throwable t) {
//                call.cancel();
//            }
//        });


        // ------- /api/users?
        Call<User> call_3 = apiInterface.doCreateUserWithField("WLIU", "DEV", "1001");
//        call_3.enqueue(new Callback<UserList>() {
//            @Override
//            public void onResponse(Call<UserList> call, Response<UserList> response) {
//                UserList userList = response.body();
//                Integer text = userList.page;
//                Integer total = userList.total;
//                Integer totalPages = userList.totalPages;
//                List<UserList.Datum> datumList = userList.data;
//                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();
//
//                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserList> call, Throwable t) {
//                call.cancel();
//            }
//        });


//        call_3.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//
//                String name = user.name;
//                String id = user.id;
//                Toast.makeText(getApplicationContext(), "name == " + name + ", id = " + id, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                call.cancel();
//            }
//        });

        RealInterceptorChain D;

        // ------- /api/users/{id}
        Call<SingleUser> call_4 = apiInterface.getUserById(12);
        call_4.enqueue(new Callback<SingleUser>() {
            @Override
            public void onResponse(Call<SingleUser> call, Response<SingleUser> response) {
                SingleUser singleUser = response.body();
                SingleUser.UserDetail userDetail = singleUser.data;
                if (userDetail != null) {
                    String name = userDetail.first_name;
                    String id = userDetail.id;
                    Toast.makeText(getApplicationContext(),"name == " + name + ", id = " + id, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SingleUser> call, Throwable t) {

            }
        });
    }
}
