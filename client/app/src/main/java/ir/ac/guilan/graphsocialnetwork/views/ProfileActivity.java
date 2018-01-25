package ir.ac.guilan.graphsocialnetwork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.adapter.postsAdapter;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.follow;
import ir.ac.guilan.graphsocialnetwork.model.followInfo;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.users;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    Button btnFollowing;
    TextView userName;
    TextView postQty;
    TextView followerQty;
    TextView fwdQty;
    TextView bioUser;
    SwipeRefreshLayout containerProfile;
    int id;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userName = findViewById(R.id.profile_user_name);
        postQty = findViewById(R.id.post_quantity);
        followerQty = findViewById(R.id.follower_quantity);
        fwdQty = findViewById(R.id.followed_quantity);
        bioUser = findViewById(R.id.profile_bio);
        btnFollowing = findViewById(R.id.btn_following);
        containerProfile = findViewById(R.id.container_profile);
        recyclerView = findViewById(R.id.recycler_view_profile);

        setTitle("");
        id = getIntent().getExtras().getInt("id");
        btnFollowing.setOnClickListener(this);
        containerProfile.setOnRefreshListener(this);
        serviseApi.mClient.sendMessage(objectcreator());


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        System.out.println(event);
        commincuteObject mcommincuteObject = new Gson().fromJson(event, commincuteObject.class);

        if (mcommincuteObject.getMessage().getRoute()!=null && mcommincuteObject.getMessage().getRoute().equals("followFunc")){
            btnFollowing.setText(mcommincuteObject.getMessage().getMessageText());

        }else {
            if (mcommincuteObject.getPosts() != null) {
                postsAdapter adapter = new postsAdapter(this);
                adapter.add(mcommincuteObject.getPosts());
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
            followInfo followInfo;
            followInfo = new Gson().fromJson(mcommincuteObject.getMessage().getJson(), followInfo.class);
            if (followInfo!=null) {
                fwdQty.setText(followInfo.getFollowedQty());
                followerQty.setText(followInfo.getFollowerQty());
                postQty.setText(followInfo.getPostQty());
            }
            if (!mcommincuteObject.getUsers().isEmpty()) {
                bioUser.setText(mcommincuteObject.getUsers().get(0).getBio());
                userName.setText(mcommincuteObject.getUsers().get(0).getEmail());
                btnFollowing.setText(mcommincuteObject.getMessage().getMessageText());
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

    String objectcreator() {
        commincuteObject mcommincuteObject = new commincuteObject();
        users user = new users();
        user.setId(id);
        List<users> userList = new ArrayList<>();
        List<follow> followList = new ArrayList<>();
        userList.add(user);
        follow follow = new follow();
        follow.setFollower_id(new DatePreferences(this).getToken());
        follow.setFollowed_id(id);
        followList.add(follow);
        message mmessage = new message();
        mmessage.setRoute("profile");
        mcommincuteObject.setMessage(mmessage);
        mcommincuteObject.setUsers(userList);
        mcommincuteObject.setFollow(followList);
        return new Gson().toJson(mcommincuteObject);
    }

    String objectcreatorBtn() {
        commincuteObject mcommincuteObject = new commincuteObject();
        List<follow> followList = new ArrayList<>();
        follow follow = new follow();
        follow.setFollower_id(new DatePreferences(this).getToken());
        follow.setFollowed_id(id);
        followList.add(follow);
        message mmessage = new message();
        mmessage.setRoute("follow");
        mcommincuteObject.setMessage(mmessage);
        mcommincuteObject.setFollow(followList);
        return new Gson().toJson(mcommincuteObject);
    }

    @Override
    public void onClick(View v) {

        serviseApi.mClient.sendMessage(objectcreatorBtn());
    }

    @Override
    public void onRefresh() {
        serviseApi.mClient.sendMessage(objectcreator());
        containerProfile.setRefreshing(false);
    }

    public void onClickFollowers(View view) {

        startActivity(new Intent(this,followListActivity.class).putExtra("path","followers").putExtra("id_user",id));

    }

    public void onClickFollowing(View view) {

        startActivity(new Intent(this,followListActivity.class).putExtra("path","following").putExtra("id_user",id));
    }
}