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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.NewsAdapter;
import com.sh.journalmotherapp.model.NewsModel;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class BabyLifeFragment extends Fragment {
    private View root;

    private RecyclerView rcvView;
    private NewsAdapter adapter;
    private List<NewsModel> listModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_baby_life, container, false);
        initView();
        initAdapter();

        return root;
    }

    private void initView() {
        rcvView = root.findViewById(R.id.rcvBaby);
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
            listModel.clear();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child(Const.FirebaseRef.NEWS)
                    .child(Const.FirebaseRef.NEWS_BABY);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        NewsModel postModel = dataSnap.getValue(NewsModel.class);
                        if (postModel != null) {
                            listModel.add(postModel);
                            adapter.notifyDataSetChanged();
                        }
                    }
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
}
