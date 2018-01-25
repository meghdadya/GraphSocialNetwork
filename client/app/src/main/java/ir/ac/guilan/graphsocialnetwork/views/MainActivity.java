package ir.ac.guilan.graphsocialnetwork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.adapter.postsAdapter;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.users;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    TextView nameUsers;
    TextView bioUsers;
    NotificationBadge mBadge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        recyclerView = findViewById(R.id.recycleview_post);
        mBadge = findViewById(R.id.badge);
        setTitle("");

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SendPostDialog newFragment = new SendPostDialog();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        nameUsers = header.findViewById(R.id.name_users);
        bioUsers = header.findViewById(R.id.bio_users);


        serviseApi.mClient.sendMessage(objectcreator(new DatePreferences(this).getToken()));

        swipeRefreshLayout.setOnRefreshListener(() -> {

            serviseApi.mClient.sendMessage(objectcreator(new DatePreferences(this).getToken()));
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.find_friend) {

            startActivity(new Intent(this, FindFriendActivity.class));


        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {

        if (!event.equals("")) {
            commincuteObject mcommincuteObject = new Gson().fromJson(event, commincuteObject.class);
            if (mcommincuteObject.getPosts() != null) {
                postsAdapter adapter = new postsAdapter(this);
                adapter.add(mcommincuteObject.getPosts());
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
            if (mcommincuteObject.getUsers() != null) {
                nameUsers.setText(mcommincuteObject.getUsers().get(0).getName());
                bioUsers.setText(mcommincuteObject.getUsers().get(0).getBio());
                mBadge.setNumber(10);
            }

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
        users user = new users();
        user.setId(id);
        List<users> userList = new ArrayList<>();
        userList.add(user);
        message mmessage = new message();
        mmessage.setRoute("home");
        commincuteObject mcommincuteObject = new commincuteObject();
        mcommincuteObject.setMessage(mmessage);
        mcommincuteObject.setUsers(userList);
        return new Gson().toJson(mcommincuteObject);
    }
}
