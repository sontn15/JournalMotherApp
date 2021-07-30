package com.sh.journalmotherapp.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sh.journalmotherapp.R;

public class NewsFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_news, container, false);

        initView();
        return root;
    }


    private void initView() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getParentFragmentManager(), FragmentPagerItems.with(requireContext())
                .add("Food Nutrition", FoodNutritionFragment.class)
                .add("Baby Life", BabyLifeFragment.class)
                .add("Mom Life", MomFragment.class)
                .create());

        ViewPager viewPager = root.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = root.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }
}