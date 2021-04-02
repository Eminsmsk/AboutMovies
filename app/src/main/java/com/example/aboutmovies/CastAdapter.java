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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<com.example.aboutmovies.CastAdapter.CardTasarimTutucu> {

    private Context mContext;
    private List<Cast> castList;

    public CastAdapter(Context mContext, List<Cast> castList) {
        this.mContext = mContext;
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastAdapter.CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_card_design, parent, false);
        return new com.example.aboutmovies.CastAdapter.CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.aboutmovies.CastAdapter.CardTasarimTutucu holder, int position) {
        final Cast cast = castList.get(position);

        holder.textViewRealName.setText(cast.getRealName());
        holder.textViewNickName.setText(cast.getNickName());
        if (!cast.getPosterPath().equalsIgnoreCase("null"))
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + cast.getPosterPath()).apply(RequestOptions.circleCropTransform()).into(holder.imageViewCast);
        else
            holder.imageViewCast.setImageResource(R.drawable.ic_baseline_person_24);

           /*holder.castCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //Cast details will be added.
                }
            });*/
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CardTasarimTutucu extends RecyclerView.ViewHolder {
        private CardView castCard;
        private TextView textViewRealName;
        private TextView textViewNickName;
        private ImageView imageViewCast;

        public CardTasarimTutucu(View itemView) {
            super(itemView);
            castCard = itemView.findViewById(R.id.castCard);
            textViewRealName = itemView.findViewById(R.id.textViewRealName);
            textViewNickName = itemView.findViewById(R.id.textViewNickName);
            imageViewCast = itemView.findViewById(R.id.imageViewCast);

        }
    }


}












