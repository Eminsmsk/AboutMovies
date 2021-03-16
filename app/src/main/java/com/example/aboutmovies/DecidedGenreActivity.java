package com.example.aboutmovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DecidedGenreActivity extends AppCompatActivity {

    private Toolbar genreToolbar;
    private ImageView imageViewBackDecidedGenre;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String genreID;
    private String baseURL = "https://api.themoviedb.org/3/discover/movie?api_key=96802848a8923e21683a659225709332&language=en-US";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decided_genre);
        genreToolbar = findViewById(R.id.genreToolbar);
        imageViewBackDecidedGenre = findViewById(R.id.imageViewBackDecidedGenre);

        genreID = getIntent().getStringExtra("genreID");
        genreToolbar.setTitle("Genres");
        genreToolbar.setTitleTextColor(getResources().getColor(R.color.myPurple));
        genreToolbar.setSubtitle(getIntent().getStringExtra("genreName"));
        genreToolbar.setSubtitleTextColor(getResources().getColor(R.color.myPurple));
        genreToolbar.setTitleMargin(200, 0, 0, 0);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        url = baseURL + "&sort_by=popularity.desc&with_genres=" + genreID;
        fragmentTransaction.add(R.id.frameLayout, new GetMoviesWithCategory(url + "&page="));
        fragmentTransaction.commit();
        imageViewBackDecidedGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DecidedGenreActivity.this, GenresActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void replaceFragment(String url) {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new GetMoviesWithCategory(url + "&page="), null).addToBackStack(null).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addgenrefilter, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SortGenre:
                View design = getLayoutInflater().inflate(R.layout.sort_genres, null);
                AlertDialog.Builder aler = new AlertDialog.Builder(this);
                aler.setMessage("Choose The Sorting Parameter");
                aler.setView(design);
                aler.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sortingParameter = "popularity";
                        RadioGroup radioGroup = (RadioGroup) design.findViewById(R.id.radioGroupSort);
                        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (checkedRadioButtonId == -1) {
                            sortingParameter = "popularity";
                        } else {
                            if (checkedRadioButtonId == R.id.radioButtonPopularity)
                                sortingParameter = "popularity";
                            else if (checkedRadioButtonId == R.id.radioButtonRating)
                                sortingParameter = "vote_average";
                            else if (checkedRadioButtonId == R.id.radioButtonReleaseDate)
                                sortingParameter = "release_date";
                            else if (checkedRadioButtonId == R.id.radioButtonRevenue)
                                sortingParameter = "revenue";

                        }
                        url += "&sort_by=" + sortingParameter + ".desc";
                        replaceFragment(url);

                        Toast.makeText(getApplicationContext(),
                                "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                aler.create().show();
                break;

            case R.id.FilterGenre:
                View filterdesign = getLayoutInflater().inflate(R.layout.filter_genres, null);
                AlertDialog.Builder alerFilter = new AlertDialog.Builder(this);
                alerFilter.setMessage("Enter Filter Parameters");
                alerFilter.setView(filterdesign);
                alerFilter.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String filterParameter = "";
                        EditText RatingGte, RatingLte, ReleaseYearGte, ReleaseYearLte;
                        RatingGte = filterdesign.findViewById(R.id.editTextRatingGte);
                        RatingLte = filterdesign.findViewById(R.id.editTextRatingLte);
                        ReleaseYearGte = filterdesign.findViewById(R.id.editTextReleaseYearGte);
                        ReleaseYearLte = filterdesign.findViewById(R.id.editTextReleaseYearLte);
                        StringBuilder stringBuilder = new StringBuilder();
                        if (!RatingGte.getText().toString().isEmpty())
                            stringBuilder.append("&vote_average.gte=" + RatingGte.getText().toString());
                        if (!RatingLte.getText().toString().isEmpty())
                            stringBuilder.append("&vote_average.lte=" + RatingLte.getText().toString());
                        if (!ReleaseYearGte.getText().toString().isEmpty())
                            stringBuilder.append("&primary_release_date.gte=" + ReleaseYearGte.getText().toString() + "-01-01");
                        if (!ReleaseYearLte.getText().toString().isEmpty())
                            stringBuilder.append("&primary_release_date.lte=" + ReleaseYearLte.getText().toString() + "-01-01");

                        url = baseURL + stringBuilder.toString() + "&with_genres=" + genreID;
                        replaceFragment(url + "&sort_by=popularity.desc");


                        Toast.makeText(getApplicationContext(),
                                "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                alerFilter.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alerFilter.create().show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}