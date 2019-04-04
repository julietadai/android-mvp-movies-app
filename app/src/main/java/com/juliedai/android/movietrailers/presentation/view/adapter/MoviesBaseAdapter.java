package com.juliedai.android.movietrailers.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliedai.android.movietrailers.R;

import com.juliedai.android.movietrailers.data.MovieModel;
import com.juliedai.android.movietrailers.presentation.view.activity.list.OnMovieClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author  Julie Dai
 */

public class MoviesBaseAdapter extends RecyclerView.Adapter<MoviesBaseAdapter.ViewHolder> {

    private static final String LOG_TAG = "_MoviesBaseAdapter_";
    private LayoutInflater mInflater;
    private List<MovieModel> mDataSource;
    private OnMovieClickListener movieClickListener;
    private Picasso picasso;

    public MoviesBaseAdapter(Context context, List<MovieModel> items, OnMovieClickListener listener) {
        mDataSource = items;
        mInflater =  LayoutInflater.from(context);
        movieClickListener = listener;
        picasso = Picasso.get();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = mInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(mDataSource.get(position));
    }

    @Override
    public int getItemCount() {
       return mDataSource.size();
    }

    public void setData(List<MovieModel> items){
        mDataSource.clear();
        mDataSource.addAll(items);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDataSource.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imgMovie;
        public final TextView txtvMovieTitle;
        public final TextView txtvMovieOverview;

        public ViewHolder(View view) {
            super(view);
            imgMovie = view.findViewById(R.id.imgItemMovie);
            txtvMovieTitle = view.findViewById(R.id.txtvMovieItemTitle);
            txtvMovieOverview = view.findViewById(R.id.txtvMovieItemOverview);
            view.setOnClickListener(this);
        }

        public void bind(MovieModel movieModel){
            //Loading regular data
            txtvMovieTitle.setText(movieModel.getName());
            txtvMovieOverview.setText(movieModel.getOverview());

            //Loading image from url
            if(!TextUtils.isEmpty(movieModel.getImageResource())) {
                picasso.load(movieModel.getImageResource())
                        .placeholder(R.mipmap.ic_sync)   //temp image till the original will upload
                        .error(R.mipmap.ic_sync_problem) //in case of loading image failed
                        .into(imgMovie, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(LOG_TAG, "Load image from url successfully");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d(LOG_TAG, "Failed to load image from url, e= [" + e + "]");
                            }
                        });
            }

        }

        @Override
        public void onClick(View v) {
            if (movieClickListener == null) {
                return;
            }
            movieClickListener.onMovieClicked(getAdapterPosition());
        }
    }
}
