package com.example.aboutmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyWatchlistActivity extends AppCompatActivity {
    private RecyclerView watchlistRV;
    private Toolbar toolbar;
    private ImageButton imageButtonBack;
    private ArrayList<Movie> watchList = new ArrayList<>();
    private ArrayList<WatchListItem> watchListItems = new ArrayList<>();
    private WatchlistAdapter adapter;
    private String baseUrl = "https://api.themoviedb.org/3/movie/";
    private String apiKey = "?api_key=96802848a8923e21683a659225709332";
    private boolean flag = true;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watchlist);
        veritabaniKopyala();

        watchlistRV = findViewById(R.id.genresRV);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Watchlist");
        toolbar.setTitleMargin(192, 0, 0, 0);
        toolbar.setTitleTextColor(getResources().getColor(R.color.myPurple));
        imageButtonBack = findViewById(R.id.imageButtonBack);
        watchlistRV.setHasFixedSize(true);
        watchlistRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        db = new AppDatabase(this);
        watchListItems = new WatchListDAO().getAllItems(db);
        System.out.println("********************************************** " + watchListItems.size());

        JSONObjectVolleyVeriAl(watchListItems);
        // adapter = new GenresAdapter(GenresActivity.this,watchList);
        // genresRV.setAdapter(adapter);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWatchlistActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    public void veritabaniKopyala() {

        DatabaseCopyHelper helper = new DatabaseCopyHelper(this);

        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        helper.openDataBase();


    }

    public void JSONObjectVolleyVeriAl(ArrayList<WatchListItem> items) {
        ArrayList<Movie> temp = new ArrayList<>();

        watchList.clear();
        for (int i = 0; i < items.size(); i++) {

            String url = baseUrl + String.valueOf(items.get(i).getItemID()) + apiKey;
            int finalI = i;
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            try {

                                List<Genre> g = new ArrayList<>();
                                for (int j = 0; j < response.getJSONArray("genres").length(); j++) {
                                    g.add(new Genre(response.getJSONArray("genres").getJSONObject(j).getInt("id"),
                                            response.getJSONArray("genres").getJSONObject(j).getString("name")));
                                }

                                Movie m = new Movie(response.getString("title"),
                                        g,
                                        response.getInt("runtime"),
                                        response.getString("poster_path"),
                                        response.getString("release_date"),
                                        response.getString("vote_average"),
                                        response.getInt("id"));
                                temp.add(m);


                                if (flag) {
                                    watchList = (ArrayList<Movie>) temp.clone();
                                    adapter = new WatchlistAdapter(MyWatchlistActivity.this, watchList, db);
                                    watchlistRV.setAdapter(adapter);
                                    flag = false;
                                } else {
                                    //int size = movieArrayList.size();
                                    watchList.clear();
                                    watchList.addAll(temp);
                                    adapter.notifyDataSetChanged();
                                    //adapter.notifyItemRangeChanged(0, watchList.size());//0 can be used

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

            Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
        }

        // watchList = (ArrayList<Movie>) temp.clone();
    /*    adapter = new WatchlistAdapter(MyWatchlistActivity.this,watchList);
        watchlistRV.setAdapter(adapter);
        System.out.println("**********************************************size "+watchList.size());*/
    }


}