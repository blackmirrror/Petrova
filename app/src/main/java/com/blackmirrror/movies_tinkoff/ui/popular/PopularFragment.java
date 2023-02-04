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

public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;
    private PopularViewModel viewModel;
    private RecyclerView rvPopular;
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
        rvPopular = binding.rvPopular;
        rvPopular.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}