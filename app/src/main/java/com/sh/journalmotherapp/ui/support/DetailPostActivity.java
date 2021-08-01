package com.sh.journalmotherapp.ui.support;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.CommentsAdapter;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.CommentModel;
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPostActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText commentEditText;

    private ScrollView scrollView;
    private TextView commentsLabel;
    private TextView authorTextView;
    private TextView dateTextView;
    private CircleImageView authorImageView;
    private ImageView postImageView;
    private TextView titleTextView;
    private TextView descriptionText;
    private RecyclerView commentsRecyclerView;

    private MenuItem editActionMenuItem;
    private MenuItem deleteActionMenuItem;

    private CommentsAdapter commentsAdapter;
    private List<CommentModel> commentModelList;


    private Button sendButton;

    private UserModel userLogin;
    @Nullable
    private PostModel postModel;
    private MySharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post detail");
        initView();
        initData();
        initRecyclerView();
    }

    private void initData() {
        preferences = new MySharedPreferences(this);
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);
        postModel = getIntent().getExtras().getParcelable(Const.POST_SELECTED);

        titleTextView.setText(postModel.getTitle());
        dateTextView.setText(postModel.getCreatedDate());
        authorTextView.setText(postModel.getAuthor().getFullName());
        descriptionText.setText(postModel.getContent());

        Picasso.get().load(postModel.getAuthor().getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(authorImageView);

        Picasso.get().load(postModel.getImageUrl()).placeholder(R.drawable.ic_app_512)
                .error(R.drawable.ic_app_512).into(postImageView);
    }


    private void initView() {
        titleTextView = findViewById(R.id.titleTextView);
        descriptionText = findViewById(R.id.descriptionEditText);
        postImageView = findViewById(R.id.postImageView);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        scrollView = findViewById(R.id.scrollView);
        commentsLabel = findViewById(R.id.commentsLabel);
        commentEditText = findViewById(R.id.commentEditText);
        authorImageView = findViewById(R.id.authorImageView);
        authorTextView = findViewById(R.id.authorTextView);
        dateTextView = findViewById(R.id.dateTextView);

        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(this);
        commentEditText.addTextChangedListener(this);

        supportPostponeEnterTransition();
    }

    private void initRecyclerView() {
        commentModelList = new ArrayList<>();

        commentsAdapter = new CommentsAdapter(DetailPostActivity.this, commentModelList, new CommentsAdapter.OnCommentItemClickListener() {

            @Override
            public void onClickAuthor(CommentModel model) {
                openProfileActivity(model);
            }
        });

        commentsRecyclerView.setNestedScrollingEnabled(false);
        commentsRecyclerView.addItemDecoration(new DividerItemDecoration(commentsRecyclerView.getContext(),
                ((LinearLayoutManager) Objects.requireNonNull(commentsRecyclerView.getLayoutManager())).getOrientation()));
        commentsRecyclerView.setAdapter(commentsAdapter);

        getAllCommentOfPost(postModel);
    }

    private void getAllCommentOfPost(PostModel postModel) {
        if (NetworkUtils.haveNetwork(DetailPostActivity.this)) {
            commentModelList.clear();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child(Const.FirebaseRef.COMMENTS)
                    .child(postModel.getId());

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        CommentModel commentModel = dataSnap.getValue(CommentModel.class);
                        if (commentModel != null) {
                            commentModelList.add(commentModel);
                        }
                    }
                    commentsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DetailPostActivity.this, getResources().getString(R.string.co_loi_xay_ra), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DetailPostActivity.this, getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton: {
                onClickSendComment();
                break;
            }
            case R.id.commentsCountTextView: {
                scrollToFirstComment();
                break;
            }
        }
    }

    private void onClickSendComment() {
        if (postModel == null) {
            return;
        }

        String commentText = commentEditText.getText().toString();

        if (commentText.isEmpty()) {
            Toast.makeText(DetailPostActivity.this, getResources().getString(R.string.please_comment), Toast.LENGTH_SHORT).show();
            return;
        }

        String id = CommonUtil.generateUUID();
        String createdDate = CommonUtil.getCurrentDateStr();

        CommentModel commentModel = new CommentModel();
        commentModel.setId(id);
        commentModel.setPost(postModel);
        commentModel.setContent(commentText);
        commentModel.setUserComment(userLogin);
        commentModel.setCreatedDate(createdDate);

        FirebaseDatabase.getInstance().getReference()
                .child(Const.FirebaseRef.COMMENTS)
                .child(postModel.getId())
                .child(id)
                .setValue(commentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                getAllCommentOfPost(postModel);
                scrollToFirstComment();
                Toast.makeText(DetailPostActivity.this, getResources().getString(R.string.commented), Toast.LENGTH_SHORT).show();
            }
        });

        commentEditText.setText(null);
        commentEditText.clearFocus();
        hideKeyBoard();
    }

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void scrollToFirstComment() {
        if (postModel != null && commentModelList.size() > 0) {
            scrollView.smoothScrollTo(0, commentsLabel.getTop());
        }
    }

    private void openProfileActivity(CommentModel model) {
//        Intent intent = new Intent(PostDetailActivity.this, ProfileActivity.class);
//        intent.putExtra(ProfileActivity.USER_ID_EXTRA_KEY, userId);
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
//            ActivityOptions options = ActivityOptions.
//                    makeSceneTransitionAnimation(PostDetailActivity.this,
//                            new android.util.Pair<>(view, getString(R.string.post_author_image_transition_name)));
//            startActivity(intent, options.toBundle());
//        } else {
//            startActivity(intent);
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        sendButton.setEnabled(charSequence.toString().trim().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
