package com.sh.journalmotherapp.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.PostAdapter;
import com.sh.journalmotherapp.constant.PostTypeEnum;
import com.sh.journalmotherapp.model.PostEntity;
import com.sh.journalmotherapp.network.ApiService;
import com.sh.journalmotherapp.network.RetrofitClient;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment implements View.OnClickListener {

    private View root;

    private TextView tvYouThinking;
    private CircleImageView imvYou;
    private RecyclerView rcvPost;

    private PostAdapter postAdapter;
    private List<PostEntity> postModelList;

    private ApiService apiService;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);
        initData();
        initView();
        initAdapter();

        return root;
    }

    private void initData() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    private void initView() {
        imvYou = root.findViewById(R.id.imvYou);
        tvYouThinking = root.findViewById(R.id.tvYouThinking);

        imvYou.setOnClickListener(this);
        tvYouThinking.setOnClickListener(this);

        rcvPost = root.findViewById(R.id.rcvPost);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvPost.setItemAnimator(new DefaultItemAnimator());
        rcvPost.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        postModelList = new ArrayList<>();
        postAdapter = new PostAdapter(requireContext(), postModelList, model -> {
            Bundle mBundle = new Bundle();
            mBundle.putParcelable(Const.POST_SELECTED, model);
            Intent intent = new Intent(requireActivity(), DetailPostActivity.class);
            intent.putExtras(mBundle);
            startActivity(intent);
        });

        rcvPost.setAdapter(postAdapter);

        getAllPosts();
    }

    private void getAllPosts() {
        if (NetworkUtils.haveNetwork(requireContext())) {
            Call<List<PostEntity>> call = apiService.getPosts(null, PostTypeEnum.ASK_FOR_HELP.getName());
            call.enqueue(new Callback<List<PostEntity>>() {
                @Override
                public void onResponse(Call<List<PostEntity>> call, Response<List<PostEntity>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<PostEntity> models = response.body();
                        if (!models.isEmpty()) {
                            postModelList.clear();
                            postModelList.addAll(models);
                        }
                    }
                    postAdapter.notifyDataSetChanged();
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
            case R.id.tvYouThinking: {
                onClickCreateNewPost();
                break;
            }
            case R.id.imvYou: {
                onClickViewProfile();
                break;
            }
        }
    }

    private void onClickCreateNewPost() {
        Intent intent = new Intent(requireActivity(), CreatePostActivity.class);
        startActivity(intent);
    }

    private void onClickViewProfile() {

    }


}