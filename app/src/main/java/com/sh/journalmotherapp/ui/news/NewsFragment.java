package com.sh.journalmotherapp.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sh.journalmotherapp.R;

public class NewsFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        initAdapter();
        return root;
    }


    private void initView() {

    }

    private void initAdapter() {

    }

}