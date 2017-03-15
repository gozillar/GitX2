package com.example.android.gitx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.android.gitx.SimpleDividerItemDecoration;
import com.example.android.gitx.adapter.UserAdapter;
import com.example.android.gitx.model.User;
import com.example.android.gitx.model.UserList;
import com.example.android.gitx.rest.ApiClient;
import com.example.android.gitx.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ApiClient apiClient;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureViews();
        apiClient = new ApiClient();
        fetchUsersData();
        //checking for network connectivity
        /*if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Network connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fetchUsersData();
                        }
                    });

            snackbar.show();
        } else {
            fetchUsersData();
        }
        */
    }

    // send GET request with location and language parameters
    private void fetchUsersData() {
        String searchParams = "language:java location:lagos";
        ApiInterface apiService = apiClient.getService();
        Call<UserList> userListCall = apiService.getAllUsers(searchParams);
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()) {
                    UserList userList = response.body();
                    userAdapter = new UserAdapter(userList.getItems());
                    mRecyclerView.setAdapter(userAdapter);
                    mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
                } else {
                    Toast.makeText(MainActivity.this, "Request not Sucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //setup RecyclerView object
    private void configureViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    //check for Network availability
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
