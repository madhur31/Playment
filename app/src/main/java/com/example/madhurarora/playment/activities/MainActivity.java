package com.example.madhurarora.playment.activities;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.madhurarora.playment.App.AppController;
import com.example.madhurarora.playment.App.Constants;
import com.example.madhurarora.playment.DataHandler.MovieDataHandler;
import com.example.madhurarora.playment.R;
import com.example.madhurarora.playment.Response.ImdbResponse;
import com.example.madhurarora.playment.Response.SearchResponse;
import com.example.madhurarora.playment.adapter.MovieListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieListAdapter movieListAdapter;
    private ArrayList<SearchResponse> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.moviesRecyclerView);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(response);
        recyclerView.setAdapter(movieListAdapter);

    }

    private void dropDrag() {
        findViewById(R.id.linearView).setOnDragListener(new myDragListner());

    }

    class myDragListner implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            switch (event.getAction()) {

                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.removeAllViews();
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dropDrag();
    }

    private void getData(String query) {
        String url = Constants.SEARCH_URL + query;

        MovieDataHandler movieDataHandler = new MovieDataHandler(url) {
            @Override
            public void resultReceivedMovieInfo(int resultCode, String errorMessage, ImdbResponse response) {
                if (resultCode == 200 && response != null) {
                    updateUI(response.getSearch());
                } else {
                    Toast.makeText(AppController.getAppContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };

        movieDataHandler.fetchData();
    }

    private void updateUI(ArrayList<SearchResponse> searchResponse) {
        if (searchResponse != null) {
            response = searchResponse;
        } else {
            response = new ArrayList<>();
        }
        if (movieListAdapter != null) {
            movieListAdapter.setMovieList(response);
            movieListAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setQueryHint("Search Movie");
        //searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                getData(query);
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
