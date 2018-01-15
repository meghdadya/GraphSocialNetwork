package ir.ac.guilan.graphsocialnetwork.views;


import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.users;

import static java.lang.System.*;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText bio;
    EditText email;
    EditText password;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.register_name);
        bio = findViewById(R.id.register_bio);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        register = findViewById(R.id.email_register_in_button);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (checkEmail(email.getText().toString()) && !name.getText().toString().equals("") && !password.getText().toString().equals("") && !bio.getText().toString().equals("")) {
            if (serviseApi.mClient != null) {
                serviseApi.mClient.sendMessage(objectcreator(email.getText().toString(), name.getText().toString(), password.getText().toString(), bio.getText().toString()));
            }

        } else {
            Snackbar.make(findViewById(R.id.activity_register), "please fill all field correctly", Snackbar.LENGTH_SHORT).show();
        }

    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    String objectcreator(String email, String name, String password, String bio) {
        List<users> usersList = new ArrayList<>();
        users musers = new users();
        musers.setEmail(email);
        musers.setName(name);
        musers.setPassword(password);
        musers.setBio(bio);
        message mmessage = new message();
        mmessage.setRoute("register");
        usersList.add(musers);

        commincuteObject mcommincuteObject = new commincuteObject();
        mcommincuteObject.setUsers(usersList);
        mcommincuteObject.setMessage(mmessage);
        return new Gson().toJson(mcommincuteObject);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {

        Toast.makeText(this, event, Toast.LENGTH_SHORT).show();
        if (event.equals("Thanks for registration"))
            finish();


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
