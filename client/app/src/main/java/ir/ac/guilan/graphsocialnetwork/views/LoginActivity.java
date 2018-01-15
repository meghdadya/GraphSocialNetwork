package ir.ac.guilan.graphsocialnetwork.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.users;

import static java.lang.System.out;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    }

    public void not_registred(View view) {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {

        out.println(event);

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


    public void onClickLogin(View view) {
        if (serviseApi.mClient != null) {
            serviseApi.mClient.sendMessage(objectcreator());
        }
    }

    String objectcreator() {
        message mmessage = new message();
        mmessage.setRoute("login");
        commincuteObject mcommincuteObject = new commincuteObject();
        mcommincuteObject.setMessage(mmessage);
        return new Gson().toJson(mcommincuteObject);
    }
}

