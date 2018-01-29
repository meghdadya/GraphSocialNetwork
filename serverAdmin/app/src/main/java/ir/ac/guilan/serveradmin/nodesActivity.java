package ir.ac.guilan.serveradmin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ir.ac.guilan.serveradmin.adapter.nodesAdapter;
import ir.ac.guilan.serveradmin.clientApi.serviseApi;
import ir.ac.guilan.serveradmin.model.commincuteObject;
import ir.ac.guilan.serveradmin.model.message;

public class nodesActivity extends AppCompatActivity { //strongly connected component
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.rec_nodes_list);
        serviseApi.mClient.sendMessage(objectcreator());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        commincuteObject mcommincuteObject = new Gson().fromJson(event, commincuteObject.class);
        if (mcommincuteObject.getMessage().getMessageText().equals("done")) {

            nodesAdapter adapter = new nodesAdapter(this);
            adapter.add(mcommincuteObject.getGraphNodesList());
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

    String objectcreator() {
        commincuteObject mcommincuteObject = new commincuteObject();
        message mmessage = new message();
        mmessage.setRoute("admin");
        mmessage.setMessageText("following");
        mcommincuteObject.setMessage(mmessage);
        return new Gson().toJson(mcommincuteObject);
    }
}
