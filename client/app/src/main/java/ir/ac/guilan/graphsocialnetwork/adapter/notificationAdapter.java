package ir.ac.guilan.graphsocialnetwork.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.like;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.posts;
import ir.ac.guilan.graphsocialnetwork.model.user_notifications;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;

/**
 * Created by meghdadya on 7/19/17.
 */

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.RecyclerViewHolder> {

    ArrayList<user_notifications> postsArrayList;
    Activity activity;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView fullName;
        TextView type;
        TextView posttext;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.no_name);
            type = itemView.findViewById(R.id.no_type);
            posttext = itemView.findViewById(R.id.no_post_text);

        }
    }

    public notificationAdapter(Activity activity) {
        postsArrayList = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public notificationAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        notificationAdapter.RecyclerViewHolder recyclerViewHolder = new notificationAdapter.RecyclerViewHolder(itemView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        user_notifications userNotifications = postsArrayList.get(position);
        holder.fullName.setText(userNotifications.getUsers().getName());
        holder.type.setText(userNotifications.getNotification().getType());
        holder.posttext.setText(userNotifications.getPost().getText());


    }


    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    public void add(List<user_notifications> items) {
        int previousDataSize;
        if (postsArrayList != null) {
            previousDataSize = this.postsArrayList.size();
        } else {
            previousDataSize = 0;
        }
        if (previousDataSize == 0) {
            postsArrayList.addAll(items);
            notifyItemInserted(1);
        } else {
            postsArrayList.addAll(items);
            notifyItemRangeInserted(previousDataSize, items.size());
        }
    }


    String objectCreator(int user_id, int post_id) {
        commincuteObject mcommincuteObject = new commincuteObject();
        like l1 = new like();
        l1.setPost_id(post_id);
        l1.setUser_id(user_id);
        message message = new message();
        message.setRoute("like");
        message.setJson(new Gson().toJson(l1));
        mcommincuteObject.setMessage(message);
        return new Gson().toJson(mcommincuteObject);
    }
}
