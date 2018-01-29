package ir.ac.guilan.graphsocialnetwork.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.model.users;
import ir.ac.guilan.graphsocialnetwork.views.ProfileActivity;

public class friendsAdapter extends RecyclerView.Adapter<friendsAdapter.RecyclerViewHolder> {

    ArrayList<users> usersArrayList;
    Activity activity;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView fullName;
        TextView bio;
        RelativeLayout item;


        private RecyclerViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.friend_name);
            bio = itemView.findViewById(R.id.friend_bio);
            item = itemView.findViewById(R.id.item_friend);
        }
    }

    public friendsAdapter(Activity activity) {
        usersArrayList = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public friendsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        friendsAdapter.RecyclerViewHolder recyclerViewHolder = new friendsAdapter.RecyclerViewHolder(itemView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        users user = usersArrayList.get(position);
        holder.fullName.setText(user.getName());
        holder.bio.setText(user.getBio());
        holder.item.setOnClickListener(v -> {

            activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("id",user.getId()));
        });


    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public void add(List<users> items) {
        int previousDataSize;
        if (usersArrayList != null) {
            previousDataSize = this.usersArrayList.size();
        } else {
            previousDataSize = 0;
        }
        if (previousDataSize == 0) {
            usersArrayList.addAll(items);
            notifyItemInserted(1);
        } else {
            usersArrayList.addAll(items);
            notifyItemRangeInserted(previousDataSize, items.size());
        }
    }
}
