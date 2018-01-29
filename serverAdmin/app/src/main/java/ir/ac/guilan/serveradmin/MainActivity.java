package ir.ac.guilan.serveradmin;

import android.content.Intent;
import android.os.Bundle;
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

import ir.ac.guilan.serveradmin.adapter.friendsAdapter;
import ir.ac.guilan.serveradmin.clientApi.serviseApi;
import ir.ac.guilan.serveradmin.model.commincuteObject;
import ir.ac.guilan.serveradmin.model.message;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //admin panel
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.search_user);
        recyclerView = findViewById(R.id.rec_user_list);


        serviseApi.mClient.sendMessage(objectcreator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
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
            searchView.setMenuItem(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        commincuteObject mcommincuteObject = new Gson().fromJson(event, commincuteObject.class);
        if (mcommincuteObject.getMessage().getMessageText().equals("home done")) {

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

    String objectcreator() {
        commincuteObject mcommincuteObject = new commincuteObject();
        message mmessage = new message();
        mmessage.setRoute("admin");
        mmessage.setMessageText("getallusers");
        mcommincuteObject.setMessage(mmessage);
        return new Gson().toJson(mcommincuteObject);
    }

    public void onFabClick(View view) {

        startActivity(new Intent(this, nodesActivity.class));
    }
}
