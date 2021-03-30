package com.example.aboutmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetMoviesWithCategory extends Fragment {


    private RecyclerView moviesRV;
    private ArrayList<Movie> movieArrayList;
    private MoviesAdapter adapter;


    private int pageNo = 1;
    private int totalPage = 1;
    private boolean flag = true;
    private int previousSize = 0;
    private String Baseurl;

    public GetMoviesWithCategory(String baseurl) {
        Baseurl = baseurl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_getmovieswithcategory, container, false);
        moviesRV = rootView.findViewById(R.id.movieRV);

        moviesRV.setHasFixedSize(true);
        moviesRV.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        JSONObjectVolleyVeriAl();
        moviesRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1) && totalPage >= pageNo) {
                    pageNo += 1;
                    JSONObjectVolleyVeriAl();
                }
            }
        });

        return rootView;

    }

    public void JSONObjectVolleyVeriAl() {

        String url = Baseurl + String.valueOf(pageNo);


        ArrayList<Movie> filmler = new ArrayList<>();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            // Getting JSON Array node
                            JSONArray movies = response.getJSONArray("results");


                            for (int i = 0; i < movies.length(); i++) {


                                JSONObject j = movies.getJSONObject(i);
                                Movie m = new Movie(j.getString("overview")


                                        , j.getString("title")
                                        , j.getString("poster_path")


                                        , j.getString("vote_average")
                                        , j.getInt("id")

                                        , j.getInt("vote_count")
                                );
                                final StringBuilder release_date = new StringBuilder();
                                try {
                                    release_date.append(j.getString("release_date"));


                                } catch (Exception e) {
                                    continue;
                                } finally {
                                    m.setReleaseDate(release_date.toString());
                                }

                                // ??????
                                if (m.getPosterPath().equalsIgnoreCase("null"))
                                    continue;

                                filmler.add(m);


                            }

                            if (flag) {
                                movieArrayList = (ArrayList<Movie>) filmler.clone();
                                adapter = new MoviesAdapter(getActivity(), movieArrayList);
                                moviesRV.setAdapter(adapter);
                                previousSize = filmler.size();
                                flag = false;
                            } else {
                                //int size = movieArrayList.size();
                                //movieArrayList.clear();
                                movieArrayList.addAll(filmler);

                                adapter.notifyDataSetChanged();
                                adapter.notifyItemRangeChanged(0, movieArrayList.size());//0 can be used

                            }


                            totalPage = response.getInt("total_pages");


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

        Volley.newRequestQueue(getActivity()).add(jsonRequest);

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toprated_addgenrefilter,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

                View design = getLayoutInflater().inflate(R.layout.filter_genres,null);
                final ChipGroup chipGroup = design.findViewById(R.id.ChipGroup);
                Context context;
                AlertDialog.Builder aler = new AlertDialog.Builder(getActivity());
                aler.setMessage("Add Genres for Filtering");
                aler.setView(design);
                aler.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String msg = "";

                        int chipsCount = chipGroup.getChildCount();
                        if (chipsCount == 0) {
                            Baseurl = "https://api.themoviedb.org/3/movie/top_rated?api_key=96802848a8923e21683a659225709332&with_genres=&page=";

                        } else {
                            int j = 0;
                            while (j < chipsCount) {
                                Chip chip = (Chip) chipGroup.getChildAt(j);
                                if (chip.isChecked() ) {
                                    String x = chip.getResources().getResourceName(chip.getId()).toString();
                                    String[] xx = x.split("_");
                                   // System.out.println("->"+xx[1]);
                                    msg += xx[1].toString() + ",";
                                }
                                j++;
                            }
                        }
                        if(msg.length()<1){
                            Baseurl = "https://api.themoviedb.org/3/movie/top_rated?api_key=96802848a8923e21683a659225709332&page=";
                        }
                        else{
                            Baseurl = "https://api.themoviedb.org/3/movie/top_rated?api_key=96802848a8923e21683a659225709332&with_genres="+msg.substring(0,msg.length()-1)+"&page=";
                            System.out.println("->"+Baseurl);

                        }
                        pageNo=1;
                        movieArrayList.clear();
                        JSONObjectVolleyVeriAl();

                        Toast.makeText(getActivity(),
                                "Added",Toast.LENGTH_SHORT).show();
                    }
                });
                aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),
                                "Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                aler.create().show();


        return true;


    }*/
}