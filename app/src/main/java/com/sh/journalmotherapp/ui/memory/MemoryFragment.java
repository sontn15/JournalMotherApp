package com.sh.journalmotherapp.ui.memory;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.MemoryAdapter;
import com.sh.journalmotherapp.adapter.PostAdapter;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.MemoryModel;
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;


public class MemoryFragment extends Fragment implements View.OnClickListener {

    private View root;

    private FloatingActionButton btnAdd;

    private RecyclerView rcvMemory;
    private MemoryAdapter memoryAdapter;
    private List<MemoryModel> memoryModels;

    private MySharedPreferences preferences;
    private UserModel userLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_memory, container, false);
        initView();
        initAdapter();
        return root;
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
        memoryAdapter = new MemoryAdapter(requireContext(), memoryModels, new MemoryAdapter.OnPostItemClickListener() {
            @Override
            public void onClickItem(MemoryModel model) {

            }
        });

        rcvMemory.setAdapter(memoryAdapter);

        getAllMemories();
    }

    private void getAllMemories() {
        if (NetworkUtils.haveNetwork(requireContext())) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child(Const.FirebaseRef.MEMORIES)
                    .child(userLogin.getUsername());

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        MemoryModel model = dataSnap.getValue(MemoryModel.class);
                        if (model != null) {
                            memoryModels.add(model);
                            memoryAdapter.notifyDataSetChanged();
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
    }
}