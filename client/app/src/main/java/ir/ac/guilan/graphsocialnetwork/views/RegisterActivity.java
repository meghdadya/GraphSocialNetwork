package ir.ac.guilan.graphsocialnetwork.views;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.clientApi.Client;

import static java.lang.System.*;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText bio;
    EditText email;
    EditText password;
    Button register;
    private Client mClient;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        name = findViewById(R.id.register_name);
        bio = findViewById(R.id.register_bio);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        register = findViewById(R.id.email_register_in_button);
        register.setOnClickListener(this);
        new connectTask().execute("");


    }

    @Override
    public void onClick(View v) {
        if (mClient != null) {
            mClient.sendMessage("1");
        }

    }

    public class connectTask extends AsyncTask<String, String, Client> {

        @Override
        protected Client doInBackground(String... message) {

            //we create a Client object and
            //here the messageReceived method is implemented
            //this method calls the onProgressUpdate
            mClient = new Client(this::publishProgress);
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //in the arrayList we add the messaged received from server
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            out.println(values);

        }
    }
}
