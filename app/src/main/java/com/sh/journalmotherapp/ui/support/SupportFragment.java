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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.PostAdapter;
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.util.Const;
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
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Const.FirebaseRef.POSTS);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        PostModel postModel = dataSnap.getValue(PostModel.class);
                        if (postModel != null) {
                            postModelList.add(postModel);
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
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