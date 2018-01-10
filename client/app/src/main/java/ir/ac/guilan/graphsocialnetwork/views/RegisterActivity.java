package ir.ac.guilan.graphsocialnetwork.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.ac.guilan.graphsocialnetwork.R;

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
        

    }
}
