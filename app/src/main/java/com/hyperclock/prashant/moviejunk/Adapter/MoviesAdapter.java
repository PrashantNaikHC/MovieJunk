package com.hyperclock.prashant.moviejunk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyperclock.prashant.moviejunk.Activity.MainActivity;
import com.hyperclock.prashant.moviejunk.Activity.MovieDetailsActivity;
import com.hyperclock.prashant.moviejunk.Model.Movie;
import com.hyperclock.prashant.moviejunk.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;
    private Context context;
    private int rowLayout;

    //constructor is required
    public MoviesAdapter(List<Movie> movies, Context context, int rowLayout) {
        this.movies = movies;
        this.context = context;
        this.rowLayout = rowLayout;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        //holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());

        double averageRating = movies.get(position).getVoteAverage();
        float averageFloat = ((float) averageRating);
        holder.ratingBar.setRating(averageFloat);

        Glide.with(holder.background.getContext())
                .load(MainActivity.IMAGE_URL+movies.get(position).getBackdropPath())
                .into(holder.background);

        holder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                int movieId = movies.get(position).getId();
                intent.putExtra("id",movieId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //including the below two to avoid the problem of view duplication
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        //TextView movieDescription;
        TextView rating;
        ImageView background;
        RatingBar ratingBar;

        public MoviesViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            //movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            background = (ImageView) v.findViewById(R.id.background);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

        }
    }
}
