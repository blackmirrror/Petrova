package com.blackmirrror.movies_tinkoff.ui.popular;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.databinding.FragmentPopularBinding;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.model.TopMovies;
import com.blackmirrror.movies_tinkoff.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;
    private PopularViewModel viewModel;
    private RecyclerView rvPopular;
    private TextView textView;
    private PopularAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PopularViewModel homeViewModel =
                new ViewModelProvider(this).get(PopularViewModel.class);

        binding = FragmentPopularBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(PopularViewModel.class);
        textView = binding.temp;
        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID(301)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                        Movie post = response.body();

                        textView.append(post.getId() + "\n");
                        textView.append(post.getTitle() + "\n");
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                        textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
        NetworkService.getInstance()
                .getJSONApi()
                .getPosts(2, "TOP_100_POPULAR_FILMS")
                .enqueue(new Callback<TopMovies>() {
                    @Override
                    public void onResponse(Call<TopMovies> call, Response<TopMovies> response) {
                        TopMovies posts = response.body();
                        textView.append("Full: " + posts.getCount() + "\n");
                        textView.append("tm: " + posts.getMovies().size() + "\n");
                        List<Movie> po = (List<Movie>) posts.getMovies();
                        adapter = new PopularAdapter(po);
                        for (Movie p : po) {
                            Movie post = (Movie) p;
                            textView.append(post.getId() + "\n");
                            textView.append(post.getTitle() + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<TopMovies> call, Throwable t) {
                        textView.append("MANY");
                        t.printStackTrace();
                    }
                });
        rvPopular = binding.rvPopular;
        rvPopular.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}