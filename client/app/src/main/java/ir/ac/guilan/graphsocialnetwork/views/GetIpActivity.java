package ir.ac.guilan.graphsocialnetwork.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.clientApi.Client;

public class GetIpActivity extends AppCompatActivity {

    private Button connect;
    private EditText ipAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ip);
        connect = findViewById(R.id.btn_connect);
        ipAdress = findViewById(R.id.ip_address);

        connect.setOnClickListener(view -> {

            String ip = ipAdress.getText().toString();
            Client.SERVERIP = ip;
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        });


    }
}
