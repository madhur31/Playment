package com.example.madhurarora.playment.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.madhurarora.playment.App.AppController;
import com.example.madhurarora.playment.R;
import com.example.madhurarora.playment.Response.SearchResponse;

import java.util.ArrayList;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    ArrayList<SearchResponse> movieList;
    private Context context;
    Activity activity;

    public MovieListAdapter(ArrayList<SearchResponse> response) {
        if(response != null) {
            movieList = response;
        }
        else {
            movieList = new ArrayList<>();
        }
    }

    public void setMovieList(ArrayList<SearchResponse> movieList) {
        if(movieList != null) {
            this.movieList = movieList;
        }
        else {
            this.movieList = new ArrayList<>();
        }    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        activity = (Activity)context;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchResponse list = movieList.get(position);

        holder.imageView.setImageUrl(list.getPoster(), AppController.getInstance().getImageLoader());
        holder.imageView.setDefaultImageResId(R.drawable.defaultmovie);
        holder.title.setText(list.getTitle());
        holder.year.setText(list.getYear());

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.setVisibility(View.INVISIBLE);
                return false;
            }
        });

/*
        holder.layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("Action", String.valueOf(motionEvent.getAction()));
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.setVisibility(View.INVISIBLE);
                    return false;
                }

                else{
                    return false;
                }
            }
        });
        */

        holder.layout.setOnDragListener(new myDragListner());



    }

    @Override
    public int getItemCount() {
        if(movieList != null)
            return movieList.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView title;
        TextView year;
        NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayout)itemView.findViewById(R.id.rootView);
            year = (TextView) itemView.findViewById(R.id.year);
            title = (TextView) itemView.findViewById(R.id.movietitle);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageMovie);
        }
    }

    class myDragListner implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(0xFF00FF00);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(0x0000000);
                    break;

                case DragEvent.ACTION_DROP:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(0xEEEEEEE);
                default:
                    break;
            }

            return true;
        }
    }
}
