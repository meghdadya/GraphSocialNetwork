package ir.ac.guilan.graphsocialnetwork.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.adapter.friendsAdapter;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.post;
import ir.ac.guilan.graphsocialnetwork.model.users;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;

public class LikesActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycle_likes_list);
        setSupportActionBar(toolbar);
        serviseApi.mClient.sendMessage(objectcreator(getIntent().getExtras().getInt("postid")));

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
            // Snackbar.make(findViewById(R.id.find_friend_activity), mcommincuteObject.getMessage().getMessageText(), Snackbar.LENGTH_SHORT).show();
        }

    }

    String objectcreator(int id) {
        commincuteObject mcommincuteObject = new commincuteObject();
        post post = new post();
        post.setId(id);
        List<post> postlist = new ArrayList<>();
        postlist.add(post);
        message mmessage = new message();
        mmessage.setRoute("postlikes");
        mcommincuteObject.setMessage(mmessage);
        mcommincuteObject.setPost(postlist);
        return new Gson().toJson(mcommincuteObject);
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

}
