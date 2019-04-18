package com.hyperclock.prashant.moviejunk.Rest;

import com.hyperclock.prashant.moviejunk.Model.Movie;
import com.hyperclock.prashant.moviejunk.Model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovieDetails(@Query("api_key") String apiKey, @Query("query") String query);
}
