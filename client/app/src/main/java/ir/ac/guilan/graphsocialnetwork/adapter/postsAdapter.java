package ir.ac.guilan.graphsocialnetwork.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ir.ac.guilan.graphsocialnetwork.R;
import ir.ac.guilan.graphsocialnetwork.model.posts;

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


        private RecyclerViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.full_name);
            postText = itemView.findViewById(R.id.post_text);
            postDate = itemView.findViewById(R.id.post_date);
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
        //  holder.postDate.setText(posts.getPost().getDate());

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
}
