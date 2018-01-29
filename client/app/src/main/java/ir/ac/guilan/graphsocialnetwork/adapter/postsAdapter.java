package ir.ac.guilan.graphsocialnetwork.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.clientApi.serviseApi;
import ir.ac.guilan.graphsocialnetwork.model.commincuteObject;
import ir.ac.guilan.graphsocialnetwork.model.like;
import ir.ac.guilan.graphsocialnetwork.model.message;
import ir.ac.guilan.graphsocialnetwork.model.posts;
import ir.ac.guilan.graphsocialnetwork.utilities.DatePreferences;
import ir.ac.guilan.graphsocialnetwork.views.LikesActivity;

/**
 * Created by meghdadya on 7/19/17.
 */

public class postsAdapter extends RecyclerView.Adapter<postsAdapter.RecyclerViewHolder> {

    ArrayList<posts> postsArrayList;
    Activity activity;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView fullName;
        TextView postText;
        TextView postDate;
        ImageView like;
        TextView like_count;


        private RecyclerViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.full_name);
            postText = itemView.findViewById(R.id.post_text);
            postDate = itemView.findViewById(R.id.post_date);
            like = itemView.findViewById(R.id.like);
            like_count = itemView.findViewById(R.id.like_count);

        }
    }

    public postsAdapter(Activity activity) {
        postsArrayList = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public postsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        postsAdapter.RecyclerViewHolder recyclerViewHolder = new postsAdapter.RecyclerViewHolder(itemView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        posts posts = postsArrayList.get(position);
        holder.fullName.setText(posts.getUsers().getName());
        holder.postText.setText(posts.getPost().getText());
        holder.postDate.setText(posts.getPost().getDate());
        holder.like_count.setText(Integer.toString(posts.getLike_count()));
        if (posts.isLiked()) {
            holder.like.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.like.setImageResource(R.drawable.ic_heart);
        }

        holder.like.setOnClickListener(view -> {

            if (posts.isLiked()) {
                holder.like.setImageResource(R.drawable.ic_heart);
                postsArrayList.get(position).setLiked(false);
                postsArrayList.get(position).setLike_count(posts.getLike_count() - 1);
                holder.like_count.setText(Integer.toString(posts.getLike_count()));
                serviseApi.mClient.sendMessage(objectCreator(new DatePreferences(activity).getToken(), posts.getPost().getId()));
            } else {
                holder.like.setImageResource(R.drawable.ic_heart_filled);
                postsArrayList.get(position).setLiked(true);
                postsArrayList.get(position).setLike_count(posts.getLike_count() + 1);
                holder.like_count.setText(Integer.toString(posts.getLike_count()));
                serviseApi.mClient.sendMessage(objectCreator(new DatePreferences(activity).getToken(), posts.getPost().getId()));
            }
        });
        holder.like_count.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, LikesActivity.class).putExtra("postid", posts.getPost().getId()));

        });

    }


    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    public void add(List<posts> items) {
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
