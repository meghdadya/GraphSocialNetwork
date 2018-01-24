package ir.ac.guilan.graphsocialnetwork.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import ir.ac.guilan.graphsocialnetwork.adapter.friendsAdapter;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.users;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    Button btnFollowing;
    TextView userName;
    TextView postQty;
    TextView followerQty;
    TextView fwdQty;
    SwipeRefreshLayout containerProfile;
    int id;

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
        btnFollowing = findViewById(R.id.btn_following);
        containerProfile = findViewById(R.id.container_profile);
        setTitle("");
        id = getIntent().getExtras().getInt("id");
        btnFollowing.setOnClickListener(this);
        containerProfile.setOnRefreshListener(this);


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        commincuteObject mcommincuteObject = new Gson().fromJson(event, commincuteObject.class);


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
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }
}
