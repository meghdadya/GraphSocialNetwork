package ir.ac.guilan.serveradmin.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.serveradmin.R;
import ir.ac.guilan.serveradmin.model.users;


/**
 * Created by meghdadya on 7/19/17.
 */

public class friendsAdapter extends RecyclerView.Adapter<friendsAdapter.RecyclerViewHolder> {

    ArrayList<users> usersArrayList;
    Activity activity;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView fullName;
        TextView bio;
        TextView email;
        RelativeLayout item;


        private RecyclerViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.friend_name);
            bio = itemView.findViewById(R.id.friend_bio);
            email = itemView.findViewById(R.id.friend_email);
        }
    }

    public friendsAdapter(Activity activity) {
        usersArrayList = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(itemView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        users user = usersArrayList.get(position);
        holder.fullName.setText(user.getName());
        holder.bio.setText(user.getBio());
        holder.email.setText(user.getEmail());


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
