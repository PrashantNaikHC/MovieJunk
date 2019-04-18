package com.hyperclock.prashant.moviejunk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyperclock.prashant.moviejunk.Model.ApiKey;
import com.hyperclock.prashant.moviejunk.Model.Movie;
import com.hyperclock.prashant.moviejunk.R;
import com.hyperclock.prashant.moviejunk.Rest.ApiClient;
import com.hyperclock.prashant.moviejunk.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {
    private int id;
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private TextView movieTitle_tv;
    private TextView movieOriginalTitle_tv;
    private TextView movieId_tv;
    private TextView movieOriginalLang_tv;
    private TextView movieReleaseDate_tv;
    private TextView movieDetails_tv;
    private TextView movieVoteCount_tv;
    private TextView movieVoteAverage_tv;
    private TextView moviePopularity_tv;
    private ImageView moviePosterImage_iv;
    private RelativeLayout movieBackgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        //this is for test purpose



        movieTitle_tv = (TextView) findViewById(R.id.movie_title_tv);
        movieOriginalTitle_tv = (TextView) findViewById(R.id.movie_original_title_tv);
        movieId_tv = (TextView) findViewById(R.id.movie_id_tv);
        movieOriginalLang_tv = (TextView) findViewById(R.id.movie_original_language_tv);
        movieReleaseDate_tv = (TextView) findViewById(R.id.movie_release_date_tv);
        movieDetails_tv = (TextView) findViewById(R.id.movie_details_tv);
        movieVoteCount_tv = (TextView) findViewById(R.id.movie_vote_count_tv);
        movieVoteAverage_tv = (TextView) findViewById(R.id.movie_vote_average_tv);
        moviePopularity_tv = (TextView) findViewById(R.id.movie_popularity_tv);
        moviePosterImage_iv = (ImageView) findViewById(R.id.movie_poster_iv);
        movieBackgroundLayout = (RelativeLayout) findViewById(R.id.background_layout);

        getIncomingIntent();

        ApiInterface ApiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call_movie = ApiService.getMovieDetails(id, ApiKey.API_KEY);
        call_movie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                String movieTitle = response.body().getTitle();
                String movieOriginalTitle = response.body().getOriginalTitle();
                String moviePosterPath = response.body().getPosterPath();
                int movieId = response.body().getId();
                String movieOriginalLang = response.body().getOriginalLanguage();
                String movieReleaseDate = response.body().getReleaseDate();
                String movieDetails = response.body().getOverview();
                int movieVoteCount = response.body().getVoteCount();
                double movieVoteAverage = response.body().getVoteAverage();
                double moviePopularity = response.body().getPopularity();

                movieTitle_tv.setText(movieTitle);
                movieOriginalTitle_tv.setText("("+movieOriginalTitle+")");
                //movieId_tv.setText(""+movieId);
                movieOriginalLang_tv.setText(movieOriginalLang);
                movieReleaseDate_tv.setText(movieReleaseDate);
                movieDetails_tv.setText(movieDetails);
                movieVoteCount_tv.setText("vote count : "+movieVoteCount);
                movieVoteAverage_tv.setText("vote average : " + movieVoteAverage);
                moviePopularity_tv.setText("Popularity : " + moviePopularity);

                Glide.with(moviePosterImage_iv.getContext())
                        .load(ApiKey.IMAGE_URL + moviePosterPath)
                        .into(moviePosterImage_iv);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    private void getIncomingIntent() {
        id = getIntent().getIntExtra("id",0);
        Log.d(TAG, "getIncomingIntent: the value of id is" + id);
    }
}
