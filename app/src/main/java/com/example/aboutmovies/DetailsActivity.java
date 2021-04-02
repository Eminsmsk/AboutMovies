package com.example.aboutmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends YouTubeBaseActivity {

    private ImageView imageViewDetails;
    private TextView textViewMovieName;
    private TextView textViewRate;
    private TextView textViewReleaseDate;
    private TextView textViewRunTime;
    private TextView textViewGenres;
    private TextView textViewTrailer;
    private TextView textViewDirectors;
    private TextView textViewWriters;
    private TextView textViewDescription;
    private String baseURL = "https://api.themoviedb.org/3/movie/";
    private String movieID;
    private String apiKey = "?api_key=96802848a8923e21683a659225709332";
    private String youtubeAPI = "AIzaSyCO1Oo2PIsG9EQ3N_p_fRtZekjADzdYZKs";
    private String videoKey;
    private YouTubePlayerView videoViewTrailer;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private MediaController mediaController;
    private RecyclerView castRV;
    private CastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageViewDetails = findViewById(R.id.imageViewDetails);
        textViewMovieName = findViewById(R.id.textViewMovieName);
        textViewRate = findViewById(R.id.textViewRate);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewRunTime = findViewById(R.id.textViewRunTime);
        textViewGenres = findViewById(R.id.textViewGenres);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDirectors = findViewById(R.id.textViewDirectors);
        textViewWriters = findViewById(R.id.textViewWriters);
        textViewTrailer = findViewById(R.id.textViewTrailer);
        mediaController = new MediaController(this);
        videoViewTrailer = findViewById(R.id.videoViewTrailer);
        castRV = findViewById(R.id.castRV);
        castRV.setHasFixedSize(true);
        castRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));


        Intent intent = getIntent();
        movieID = intent.getStringExtra("id");
        getDetails();
        getCredits();
        getVideos();


    }

    public void getDetails() {
        String url = baseURL + movieID + apiKey;
        System.out.println("->>>>>>>>>" + url);

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
                                    response.getInt("id"),
                                    response.getString("backdrop_path"),
                                    response.getString("overview"),
                                    response.getString("original_language"));
                            Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + m.getPosterPath())
                                    .into(imageViewDetails);
                            textViewMovieName.setText(m.getTitle());
                            textViewDescription.setText(m.getOverview());
                            textViewRate.setText(m.getVoteAverage() + "       |");
                            textViewReleaseDate.setText(m.getReleaseDate() + "        |");
                            textViewRunTime.setText(String.valueOf(m.getRuntime()) + " min");
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < g.size(); i++) {
                                stringBuilder.append(g.get(i).getName().toString());
                                if (g.size() > 1 && i != g.size() - 1)
                                    stringBuilder.append(", ");
                            }
                            textViewGenres.setText(stringBuilder.toString());


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

    public void getCredits() {
        String type = "/credits";
        String url = baseURL + movieID + type + apiKey;
        System.out.println("->>>>>>>>>" + url);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            StringBuilder directors = new StringBuilder();
                            StringBuilder writers = new StringBuilder();
                            for (int j = 0; j < response.getJSONArray("crew").length(); j++) {
                                String d = response.getJSONArray("crew").getJSONObject(j).getString("job");
                                if (d.equalsIgnoreCase("Director")) {
                                    directors.append(response.getJSONArray("crew").getJSONObject(j).getString("name") + ", ");

                                }
                                String w = response.getJSONArray("crew").getJSONObject(j).getString("department");
                                if (w.equalsIgnoreCase("Writing")) {
                                    writers.append(response.getJSONArray("crew").getJSONObject(j).getString("name") + ", ");
                                }
                            }
                            if (!directors.toString().isEmpty())
                                textViewDirectors.setText(directors.substring(0, directors.lastIndexOf(",")).toString());
                            else
                                textViewDirectors.setText("-");

                            if (!writers.toString().isEmpty())
                                textViewWriters.setText(writers.substring(0, writers.lastIndexOf(",")).toString());
                            else
                                textViewWriters.setText("-");

                            JSONArray castArray = response.getJSONArray("cast");
                            List<Cast> castList = new ArrayList<>();
                            for (int i = 0; i < castArray.length(); i++) {

                                JSONObject j = castArray.getJSONObject(i);

                                Cast c = new Cast(j.getString("name"),
                                        j.getString("character"),
                                        j.getString("profile_path")
                                );

                                if (j.getString("known_for_department").equalsIgnoreCase("Acting")) {
                                    castList.add(c);
                                    System.out.println("--> " + c.getRealName());
                                }


                            }
                            adapter = new CastAdapter(getApplicationContext(), castList);
                            castRV.setAdapter(adapter);


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

    public void getVideos() {
        String type = "/videos";
        String url = baseURL + movieID + type + apiKey;


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray videos = response.getJSONArray("results");
                            Boolean flag = true;
                            for (int i = 0; i < videos.length(); i++) {

                                JSONObject j = videos.getJSONObject(i);
                                if (j.getString("site").equalsIgnoreCase("Youtube") && j.getString("type").equalsIgnoreCase("Trailer")) {
                                    flag = false;
                                    videoKey = j.getString("key");
                                    onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                            youTubePlayer.cueVideo(videoKey);

                                        }

                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                                        }
                                    };

                                    videoViewTrailer.initialize(youtubeAPI, onInitializedListener);
                                    break;
                                }

                            }
                            if (flag)
                                textViewTrailer.setText("No trailer available");

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

    public void getSimilarMovies() {
        String type = "/similar";
    }
}