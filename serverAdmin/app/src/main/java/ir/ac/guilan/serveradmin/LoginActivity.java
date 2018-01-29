package ir.ac.guilan.serveradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {//login panel
    EditText login_password, login_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
    }


    public void onClickLogin(View view) {


            if (login_password.getText().toString().equals("admin") && login_email.getText().toString().equals("admin")) {

                startActivity(new Intent(this,MainActivity.class));
            }

    }

}

