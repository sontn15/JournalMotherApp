package com.sh.journalmotherapp.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.NewsAdapter;
import com.sh.journalmotherapp.constant.NewsCategoryEnum;
import com.sh.journalmotherapp.model.NewsEntity;
import com.sh.journalmotherapp.network.ApiService;
import com.sh.journalmotherapp.network.RetrofitClient;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodNutritionFragment extends Fragment {
    private View root;

    private RecyclerView rcvView;
    private NewsAdapter adapter;
    private List<NewsEntity> listModel;

    private ApiService apiService;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_food_nutrition, container, false);
        initData();
        initView();
        initAdapter();

        return root;
    }

    private void initData() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    private void initView() {
        rcvView = root.findViewById(R.id.rcvFoodNutrition);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvView.setItemAnimator(new DefaultItemAnimator());
        rcvView.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        listModel = new ArrayList<>();
        adapter = new NewsAdapter(requireContext(), listModel, model -> {
            Bundle mBundle = new Bundle();
            mBundle.putParcelable(Const.NEWS_SELECTED, model);
            Intent intent = new Intent(requireActivity(), DetailNewsActivity.class);
            intent.putExtras(mBundle);
            startActivity(intent);
        });
        rcvView.setAdapter(adapter);

        getAllPosts();
    }

    private void getAllPosts() {
        if (NetworkUtils.haveNetwork(requireContext())) {
            Call<List<NewsEntity>> call = apiService.getAllNews(NewsCategoryEnum.FOOD.getName());
            call.enqueue(new Callback<List<NewsEntity>>() {
                @Override
                public void onResponse(Call<List<NewsEntity>> call, Response<List<NewsEntity>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<NewsEntity> models = response.body();
                        if (!models.isEmpty()) {
                            listModel.clear();
                            listModel.addAll(models);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<NewsEntity>> call, Throwable t) {
                    Toast.makeText(requireContext(), getResources().getString(R.string.co_loi_xay_ra), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
        }
    }
}
