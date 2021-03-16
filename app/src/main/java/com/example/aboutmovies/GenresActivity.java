package com.example.aboutmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GenresActivity extends AppCompatActivity {

    private boolean flag = true;
    private RecyclerView genresRV;
    private Toolbar genresToolbar;
    private ImageView imageViewBackGenres;
    private ArrayList<Genre> genresList = new ArrayList<>();
    private GenresAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        veritabaniKopyala();

        genresRV = findViewById(R.id.genresRV);
        genresToolbar = findViewById(R.id.genresToolbar);
        imageViewBackGenres = findViewById(R.id.imageViewBackGenres);
        genresToolbar.setTitle("Genres");
        genresToolbar.setTitleMargin(200, 0, 0, 0);
        genresToolbar.setTitleTextColor(getResources().getColor(R.color.myPurple));
        genresRV.setHasFixedSize(true);
        genresRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AppDatabase db = new AppDatabase(this);
        genresList = new GenresDAO().getAllGenres(db);
        adapter = new GenresAdapter(GenresActivity.this, genresList);
        genresRV.setAdapter(adapter);

        imageViewBackGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GenresActivity.this, MainActivity.class));
                finish();
            }
        });


        //   JSONObjectVolleyVeriAl();

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

  /*  public void JSONObjectVolleyVeriAl(){

        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=96802848a8923e21683a659225709332&language=en-US";


        ArrayList<Genre> temp = new ArrayList<>();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            // Getting JSON Array node
                            JSONArray genres = response.getJSONArray("genres");


                            for (int i = 0; i < genres.length(); i++) {


                                JSONObject j = genres.getJSONObject(i);
                                Genre m = new Genre(j.getInt("id")
                                                    ,j.getString("name")
                                                     );
                                String[] s = m.getName().trim().toLowerCase().split(" ");
                                StringBuilder imageName = new StringBuilder();
                                for(int k = 0; k <s.length ; k++) {
                                    imageName.append(s[k].toString());
                                }
                                m.setImageName(imageName.toString());
                                temp.add(m);

                            }

                            if(flag){
                                genresList = (ArrayList<Genre>) temp.clone();
                                adapter = new GenresAdapter(GenresActivity.this,genresList);
                                genresRV.setAdapter(adapter);

                                flag=false;
                            }
                            else{
                                //int size = movieArrayList.size();
                                //movieArrayList.clear();
                                genresList.addAll(temp);

                                adapter.notifyDataSetChanged();
                                adapter.notifyItemRangeChanged(0, genresList.size());//0 can be used

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
*/


}