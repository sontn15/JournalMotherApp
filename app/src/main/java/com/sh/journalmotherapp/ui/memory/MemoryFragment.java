package com.sh.journalmotherapp.ui.memory;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.MemoryAdapter;
import com.sh.journalmotherapp.constant.PostTypeEnum;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.PostEntity;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.network.ApiService;
import com.sh.journalmotherapp.network.RetrofitClient;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MemoryFragment extends Fragment implements View.OnClickListener {

    private View root;

    private FloatingActionButton btnAdd;

    private RecyclerView rcvMemory;
    private MemoryAdapter memoryAdapter;
    private List<PostEntity> memoryModels;

    private UserEntity userLogin;
    private MySharedPreferences preferences;

    private ApiService apiService;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_memory, container, false);
        initData();
        initView();
        initAdapter();
        return root;
    }

    private void initData() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    private void initView() {
        btnAdd = root.findViewById(R.id.btnAddMemory);
        btnAdd.setOnClickListener(this);

        rcvMemory = root.findViewById(R.id.rcvMemory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvMemory.setItemAnimator(new DefaultItemAnimator());
        rcvMemory.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        preferences = new MySharedPreferences(requireContext());
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        memoryModels = new ArrayList<>();
        memoryAdapter = new MemoryAdapter(requireContext(), memoryModels, model -> {

        });

        rcvMemory.setAdapter(memoryAdapter);

        getAllMemories();
    }

    private void getAllMemories() {
        if (NetworkUtils.haveNetwork(requireContext())) {
            Call<List<PostEntity>> call = apiService.getPosts(null, PostTypeEnum.MEMORIES.getName());
            call.enqueue(new Callback<List<PostEntity>>() {
                @Override
                public void onResponse(Call<List<PostEntity>> call, Response<List<PostEntity>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<PostEntity> models = response.body();
                        if (!models.isEmpty()) {
                            memoryModels.clear();
                            memoryModels.addAll(models);
                        }
                    }
                    memoryAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<PostEntity>> call, Throwable t) {
                    Toast.makeText(requireContext(), getResources().getString(R.string.co_loi_xay_ra), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddMemory: {
                onClickButtonAddMemory();
                break;
            }
        }
    }

    private void onClickButtonAddMemory() {
        Intent intent = new Intent(requireActivity(), AddMemoryActivity.class);
        startActivity(intent);
    }
}