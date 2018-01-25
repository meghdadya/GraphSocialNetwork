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
import ir.ac.guilan.graphsocialnetwork.model.users;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;

public class followListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycle_follow_list);
        serviseApi.mClient.sendMessage(objectcreator(getIntent().getExtras().getInt("id_user"),getIntent().getExtras().getString("path")));
        setTitle(getIntent().getExtras().getString("path"));


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

    String objectcreator(int id,String path) {
        commincuteObject mcommincuteObject = new commincuteObject();
        users user = new users();
        user.setId(id);
        List<users> userList = new ArrayList<>();
        userList.add(user);
        message mmessage = new message();
        mmessage.setRoute(path);
        mcommincuteObject.setMessage(mmessage);
        mcommincuteObject.setUsers(userList);
        return new Gson().toJson(mcommincuteObject);
    }
}
