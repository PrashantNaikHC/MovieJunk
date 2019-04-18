package com.hyperclock.prashant.moviejunk.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hyperclock.prashant.moviejunk.Adapter.MoviesAdapter;
import com.hyperclock.prashant.moviejunk.Model.ApiKey;
import com.hyperclock.prashant.moviejunk.Model.Movie;
import com.hyperclock.prashant.moviejunk.Model.MovieResponse;
import com.hyperclock.prashant.moviejunk.R;
import com.hyperclock.prashant.moviejunk.Rest.ApiClient;
import com.hyperclock.prashant.moviejunk.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static RecyclerView recyclerView;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ApiKey.API_KEY.isEmpty()){
            Toast.makeText(this, "Ensure the key is proper", Toast.LENGTH_SHORT).show();
            return;
        }

        //setting recycler view and layout manager
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call_top = apiService.getTopRatedMovies(ApiKey.API_KEY);
        call_top.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "onResponse: Number of movies recieved " +movies.size());

                recyclerView.setAdapter(new MoviesAdapter(movies,getApplicationContext(),R.layout.list_item_layout));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load the data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        //associate searchview
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<MovieResponse> search_call = apiService.getSearchMovieDetails(ApiKey.API_KEY,query);
                search_call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<Movie> movies = response.body().getResults();
                        //Log.d(TAG, "onResponse: Number of movies searched " +movies.size());

                        recyclerView.setAdapter(new MoviesAdapter(movies,getApplicationContext(),R.layout.list_item_layout));
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }



        });
    return true;
    }

}
