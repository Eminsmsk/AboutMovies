package com.example.aboutmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.CardTasarimTutucu> {

    private Context mContext;
    private ArrayList<Movie> watchList;
    private AppDatabase db;

    public WatchlistAdapter(Context mContext, ArrayList<Movie> watchList, AppDatabase db) {
        this.mContext = mContext;
        this.watchList = watchList;
        this.db = db;
    }

    @NonNull
    @Override
    public WatchlistAdapter.CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_card_design, parent, false);
        return new WatchlistAdapter.CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistAdapter.CardTasarimTutucu holder, int position) {
        final Movie film = watchList.get(position);

        holder.movieName.setText(film.getTitle());
        if (!film.getReleaseDate().isEmpty())
            holder.movieYear.setText(film.getReleaseDate().substring(0, 4) + " |");

        holder.movieRuntime.setText(String.valueOf(film.getRuntime()) + " minutes");
        holder.movieRating.setText(film.getVoteAverage());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < film.getGenres().size(); i++) {
            stringBuilder.append(film.getGenres().get(i).getName().toString());
            if (film.getGenres().size() > 1 && i != film.getGenres().size() - 1)
                stringBuilder.append(", ");
        }
        holder.movieGenres.setText(stringBuilder.toString());

        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + film.getPosterPath())
                .into(holder.movieImage);


        holder.card_watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("id", String.valueOf(film.getId()));
                mContext.startActivity(intent);

            }
        });

        holder.moreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View anchor;
                PopupMenu popupMenu = new PopupMenu(mContext, holder.moreImage);
                popupMenu.getMenuInflater().inflate(R.menu.popmenu_favoritemovies, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        new WatchListDAO().deleteMovie(db, film.getId());
                        notifyDataSetChanged();
                        Snackbar.make(v, "Deleted", Snackbar.LENGTH_SHORT).show();
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return watchList.size();
    }

    public class CardTasarimTutucu extends RecyclerView.ViewHolder {
        private CardView card_watchlist;
        private TextView movieName;
        private TextView movieYear;
        private TextView movieRuntime;
        private TextView movieGenres;
        private TextView movieRating;
        private ImageView movieImage;
        private ImageView moreImage;

        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);
            card_watchlist = itemView.findViewById(R.id.card_watchlist);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
            movieYear = (TextView) itemView.findViewById(R.id.movieYear);
            movieRuntime = (TextView) itemView.findViewById(R.id.movieRuntime);
            movieGenres = (TextView) itemView.findViewById(R.id.movieGenres);
            movieRating = (TextView) itemView.findViewById(R.id.movieRating);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            moreImage = (ImageView) itemView.findViewById(R.id.moreImage);

        }


    }


}
