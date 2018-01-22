package ir.ac.guilan.graphsocialnetwork.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.ac.guilan.graphsocialnetwork.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    Button btnFollowing;
    TextView userName;
    TextView postQty;
    TextView followerQty;
    TextView fwdQty;
    SwipeRefreshLayout containerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userName = findViewById(R.id.profile_user_name);
        postQty = findViewById(R.id.post_quantity);
        followerQty = findViewById(R.id.follower_quantity);
        fwdQty = findViewById(R.id.followed_quantity);
        btnFollowing = findViewById(R.id.btn_following);
        containerProfile = findViewById(R.id.container_profile);
        setTitle("");
        btnFollowing.setOnClickListener(this);
        containerProfile.setOnRefreshListener(this);



        btnFollowing.setOnClickListener(v -> {

        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }
}
