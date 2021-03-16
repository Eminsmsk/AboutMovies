package com.example.aboutmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.CardTasarimTutucu> {
    private Context mContext;
    private List<Genre> genreList;

    public GenresAdapter(Context mContext, List<Genre> genreList) {
        this.mContext = mContext;
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_card_design, parent, false);
        return new GenresAdapter.CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu holder, int position) {
        final Genre genre = genreList.get(position);

        holder.genreName.setText(genre.getName());
        holder.genreImage.setImageResource(mContext.getResources().getIdentifier(genre.getImageName(), "drawable", mContext.getPackageName()));


        holder.genreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ıntent = new Intent(mContext, DecidedGenreActivity.class);

                ıntent.putExtra("genreID", String.valueOf(genre.getId()));
                ıntent.putExtra("genreName", String.valueOf(genre.getName()));

                mContext.startActivity(ıntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class CardTasarimTutucu extends RecyclerView.ViewHolder {
        private CardView genreCard;
        private TextView genreName;
        private ImageView genreImage;

        public CardTasarimTutucu(View itemView) {
            super(itemView);
            genreCard = itemView.findViewById(R.id.genreCard);
            genreName = itemView.findViewById(R.id.genreName);
            genreImage = itemView.findViewById(R.id.genreImage);

        }
    }


}
