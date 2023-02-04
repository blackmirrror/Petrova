package com.blackmirrror.movies_tinkoff.ui.popular;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.R;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private List<Movie> listMovies;

    class PopularViewHolder extends RecyclerView.ViewHolder {

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public PopularAdapter(List<Movie> listMovies) {
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        ImageView ivPoster = holder.itemView.findViewById(R.id.iv_item_poster);
        TextView tvTitle = holder.itemView.findViewById(R.id.tv_item_title);
        TextView tvGenre = holder.itemView.findViewById(R.id.tv_item_genre);

        tvTitle.setText(listMovies.get(position).getId());
//        tvGenre.setText(listMovies.get(position).getGenres().get(0) +
//                " (" + listMovies.get(position).getYear() + ")");
//        Picasso.get()
//                .load(listMovies.get(position).getPosterUrlPreview())
//                .into(ivPoster);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }
}
