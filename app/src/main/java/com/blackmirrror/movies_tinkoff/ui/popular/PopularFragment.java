package com.blackmirrror.movies_tinkoff.ui.popular;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.databinding.FragmentPopularBinding;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.model.TopMovies;
import com.blackmirrror.movies_tinkoff.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;
    private RecyclerView rvPopular;
    private PopularAdapter adapter;
    List<Movie> movieList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPopularBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInternetAvailable())
            Toast.makeText(this.getContext(), "Нет подключения к Интернету", Toast.LENGTH_SHORT).show();
        movieList = new ArrayList<>();
        for (int i = 1; i < 6; i++)
            initPopular(i);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void initPopular(int page) {
        NetworkService.getInstance()
                .getJSONApi()
                .getPosts(page, "TOP_100_POPULAR_FILMS")
                .enqueue(new Callback<TopMovies>() {
                    @Override
                    public void onResponse(@NonNull Call<TopMovies> call, @NonNull Response<TopMovies> response) {
                        TopMovies movies = response.body();
                        for (Movie m : movies.getMovies()){
                            if (m != null) movieList.add(m);
                        }
                        if (movieList.size() >= 100) {
                            adapter = new PopularAdapter(movieList, getContext());
                            rvPopular = binding.rvPopular;
                            rvPopular.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TopMovies> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}