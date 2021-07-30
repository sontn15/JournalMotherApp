package com.sh.journalmotherapp.ui.support;

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
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SupportFragment extends Fragment implements View.OnClickListener {

    private View root;

    private TextView tvYouThinking;
    private CircleImageView imvYou;
    private RecyclerView rcvPost;

    private PostAdapter postAdapter;
    private List<PostModel> postModelList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);
        initView();
        initAdapter();

        return root;
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
        postAdapter = new PostAdapter(requireContext(), postModelList, new PostAdapter.OnPostItemClickListener() {
            @Override
            public void onClickItem(PostModel model) {

            }

            @Override
            public void onLikeClick(PostModel model) {

            }

            @Override
            public void onCommentClick(PostModel model) {

            }
        });

        rcvPost.setAdapter(postAdapter);

        getAllPosts();
    }

    private void getAllPosts() {
        if (NetworkUtils.haveNetwork(requireContext())) {

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