package com.example.aboutmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> fragmentNameArrayList = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbarMain);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);


        setSupportActionBar(toolbar);
        toolbar.setTitle("About Movies");
        toolbar.setTitleTextColor(getResources().getColor(R.color.myPurple));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.myPurple));
        toggle.syncState();

        getMovies();

        navigationView.setNavigationItemSelectedListener(this);

    }

    public void getMovies() {
        if (!fragmentArrayList.isEmpty()) {
            fragmentArrayList.clear();
            fragmentNameArrayList.clear();
        }
        fragmentArrayList.add(new GetMoviesWithCategory("https://api.themoviedb.org/3/movie/now_playing?api_key=96802848a8923e21683a659225709332&page="));
        fragmentArrayList.add(new GetMoviesWithCategory("https://api.themoviedb.org/3/movie/upcoming?api_key=96802848a8923e21683a659225709332&page="));
        fragmentArrayList.add(new GetMoviesWithCategory("https://api.themoviedb.org/3/movie/top_rated?api_key=96802848a8923e21683a659225709332&page="));
        fragmentArrayList.add(new GetMoviesWithCategory("https://api.themoviedb.org/3/movie/popular?api_key=96802848a8923e21683a659225709332&page="));
        MyViewPageAdapter adapter = new MyViewPageAdapter(this);
        viewPager2.setAdapter(adapter);

        fragmentNameArrayList.add("Now Playing");
        fragmentNameArrayList.add("Upcoming");
        fragmentNameArrayList.add("Top Rated");
        fragmentNameArrayList.add("Popular");

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(fragmentNameArrayList.get(position))).attach();

    }
    //will be implemented
   /* public void getTV(){


    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this, SearchItemActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!fragmentArrayList.isEmpty()) {
            fragmentArrayList.clear();
            fragmentNameArrayList.clear();
        }
        fragmentArrayList.add(new GetMoviesWithCategory("https://api.themoviedb.org/3/search/movie?api_key=96802848a8923e21683a659225709332&query=" + query + "&language=en-US&page="));
        MyViewPageAdapter adapter = new MyViewPageAdapter(this);
        viewPager2.setAdapter(adapter);

        fragmentNameArrayList.add("Results");
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(fragmentNameArrayList.get(position))).attach();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!fragmentArrayList.isEmpty()) {
            fragmentArrayList.clear();
            fragmentNameArrayList.clear();
        }
        fragmentArrayList.add(new GetMoviesWithCategory("https://api.themoviedb.org/3/search/movie?api_key=96802848a8923e21683a659225709332&query=" + newText + "&language=en-US&page="));
        MyViewPageAdapter adapter = new MyViewPageAdapter(this);
        viewPager2.setAdapter(adapter);

        fragmentNameArrayList.add("Results");
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(fragmentNameArrayList.get(position))).attach();
        return false;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_movies:
                searchView.setIconified(true);
                searchView.onActionViewCollapsed();
                getMovies();
                break;

            case R.id.drawer_watchlist:

                startActivity(new Intent(MainActivity.this, MyWatchlistActivity.class));
                break;
            case R.id.drawer_genres:
                startActivity(new Intent(MainActivity.this, GenresActivity.class));
                break;
            case R.id.drawer_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            trimCache();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.onDestroy();

    }

    public void trimCache() {
        try {
            File dir = getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    private class MyViewPageAdapter extends FragmentStateAdapter {
        public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override

        public Fragment createFragment(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentArrayList.size();
        }
    }


}