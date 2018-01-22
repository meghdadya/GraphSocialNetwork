package ir.ac.guilan.graphsocialnetwork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.adapter.friendsAdapter;
import ir.ac.guilan.graphsocialnetwork.adapter.postsAdapter;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.users;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;

public class FindFriendActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycle_friend);
        swipeRefreshLayout = findViewById(R.id.find_friend_refresh);
        searchView = findViewById(R.id.search_find_friend);
        setSupportActionBar(toolbar);
        setTitle("Find Friend");
        swipeRefreshLayout.setOnRefreshListener(this);
        searchView.setOnSearchViewListener(this);
        searchView.setOnQueryTextListener(this);
        serviseApi.mClient.sendMessage(objectcreator(new DatePreferences(this).getToken()));

    }

    @Override
    public void onRefresh() {

        serviseApi.mClient.sendMessage(objectcreator(new DatePreferences(this).getToken()));
        swipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        commincuteObject mcommincuteObject = new Gson().fromJson(event, commincuteObject.class);
        if (mcommincuteObject.getMessage().getMessageText().equals("done")) {

            friendsAdapter adapter = new friendsAdapter(this);
            adapter.add(mcommincuteObject.getUsers());
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);

        } else {
            Snackbar.make(findViewById(R.id.find_friend_activity), mcommincuteObject.getMessage().getMessageText(), Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    String objectcreator(int id) {
        commincuteObject mcommincuteObject = new commincuteObject();
        users user = new users();
        user.setId(id);
        List<users> userList = new ArrayList<>();
        userList.add(user);
        message mmessage = new message();
        mmessage.setRoute("findfriend");
        mmessage.setMessageText("getall");
        mcommincuteObject.setMessage(mmessage);
        mcommincuteObject.setUsers(userList);
        return new Gson().toJson(mcommincuteObject);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {

    }
}
