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
import ir.ac.guilan.serveradmin.model.graphNodes;

public class nodesAdapter extends RecyclerView.Adapter<nodesAdapter.RecyclerViewHolder> {

    ArrayList<graphNodes> graphNodesArrayList;
    Activity activity;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder { //create node for graph (SCC)


        TextView follow;
        TextView followback;
        RelativeLayout item;


        private RecyclerViewHolder(View itemView) {
            super(itemView);
            follow = itemView.findViewById(R.id.follow);
            followback = itemView.findViewById(R.id.followback);

        }
    }

    public nodesAdapter(Activity activity) {
        graphNodesArrayList = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nodes, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(itemView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        graphNodes graphNodes = graphNodesArrayList.get(position);
        holder.follow.setText(graphNodes.getFollow());
        holder.followback.setText(graphNodes.getFollowback());



    }


    @Override
    public int getItemCount() {
        return graphNodesArrayList.size();
    }

    public void add(List<graphNodes> items) {
        int previousDataSize;
        if (graphNodesArrayList != null) {
            previousDataSize = this.graphNodesArrayList.size();
        } else {
            previousDataSize = 0;
        }
        if (previousDataSize == 0) {
            graphNodesArrayList.addAll(items);
            notifyItemInserted(1);
        } else {
            graphNodesArrayList.addAll(items);
            notifyItemRangeInserted(previousDataSize, items.size());
        }
    }
}
