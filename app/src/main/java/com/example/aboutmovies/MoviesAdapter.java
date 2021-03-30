package com.example.aboutmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.CardTasarimTutucu> {


    private Context mContext;
    private List<Movie> filmlerListe;

    public MoviesAdapter(Context mContext, List<Movie> filmlerListe) {
        this.mContext = mContext;
        this.filmlerListe = filmlerListe;
    }

    public class CardTasarimTutucu extends RecyclerView.ViewHolder {
        private CardView film_card;
        private TextView textViewFilmAd;
        private TextView textViewRating;
        private ImageView ımageViewFilmResim;
        private RatingBar ratingBar;

        public CardTasarimTutucu(View itemView) {
            super(itemView);
            film_card = itemView.findViewById(R.id.movie_card);
            textViewFilmAd = itemView.findViewById(R.id.textViewMovie);
            ımageViewFilmResim = itemView.findViewById(R.id.imageViewMovie);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }

    @Override
    public CardTasarimTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_design, parent, false);
        return new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(CardTasarimTutucu holder, int position) {
        final Movie film = filmlerListe.get(position);

        holder.textViewFilmAd.setText(film.getTitle());
        holder.ratingBar.setRating(Float.valueOf(film.getVoteAverage()) / 2);
        holder.textViewRating.setText(film.getVoteAverage());
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + film.getPosterPath())
                .into(holder.ımageViewFilmResim);


        holder.film_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("id", String.valueOf(film.getId()));
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return filmlerListe.size();
    }


}
