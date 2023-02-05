package com.blackmirrror.movies_tinkoff.ui.favorite;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.database.FavoriteContract;
import com.blackmirrror.movies_tinkoff.databinding.FragmentFavoriteBinding;

public class FavoriteFragment extends Fragment {

    private static final int MEMBER_LOADER = 123;
    private FragmentFavoriteBinding binding;
    FavoriteAdapter favoriteAdapter;
    RecyclerView rvFavorite;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFavorite();
    }

    private void initFavorite() {
        rvFavorite = binding.rvFavorite;
        Cursor cursor = this.getContext().getContentResolver().query(FavoriteContract.Favorite.CONTENT_URI, new String[]{
                FavoriteContract.Favorite.KEY_TITLE,
                FavoriteContract.Favorite.KEY_YEAR,
                FavoriteContract.Favorite.KEY_GENRES,
                FavoriteContract.Favorite.KEY_POSTER_URL_PREVIEW
        }, null, null, null, null);
        favoriteAdapter = new FavoriteAdapter(this.getContext(), cursor);
        rvFavorite.setAdapter(favoriteAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}