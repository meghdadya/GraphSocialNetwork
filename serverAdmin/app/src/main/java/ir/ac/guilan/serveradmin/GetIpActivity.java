package ir.ac.guilan.serveradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import ir.ac.guilan.serveradmin.clientApi.Client;
import ir.ac.guilan.serveradmin.clientApi.serviseApi;


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
            startService(new Intent(this, serviseApi.class));
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        });


    }



    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this, serviseApi.class));
    }
}
