package com.sh.journalmotherapp.ui.askforhelp;

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

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.CommentsAdapter;
import com.sh.journalmotherapp.database.MySharedPreferences;
import com.sh.journalmotherapp.model.CommentEntity;
import com.sh.journalmotherapp.model.PostEntity;
import com.sh.journalmotherapp.model.UserEntity;
import com.sh.journalmotherapp.network.ApiService;
import com.sh.journalmotherapp.network.RetrofitClient;
import com.sh.journalmotherapp.network.request.CommentRequest;
import com.sh.journalmotherapp.util.Const;
import com.sh.journalmotherapp.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAskForHelpActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

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

    private CommentsAdapter commentsAdapter;
    private List<CommentEntity> commentModelList;

    private Button sendButton;

    private UserEntity userLogin;
    @Nullable
    private PostEntity postModel;
    private MySharedPreferences preferences;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_journal);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ask for help detail");
        initView();
        initData();
        initRecyclerView();
    }

    private void initData() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
        preferences = new MySharedPreferences(this);
        userLogin = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);
        postModel = getIntent().getExtras().getParcelable(Const.POST_SELECTED);

        String authorImageUrl = postModel.getAuthor().getImageUrl();
        String authorName = postModel.getAuthor().getFullName();

        boolean isAnonymous = postModel.getIsAnonymous();
        if (isAnonymous) {
            authorImageUrl = Const.ANONYMOUS_IMAGE_URL;
            authorName = "Anonymous";
        }

        authorTextView.setText(authorName);

        Picasso.get().load(authorImageUrl).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(authorImageView);

        Picasso.get().load(postModel.getImageUrl()).placeholder(R.drawable.ic_app_512)
                .error(R.drawable.ic_app_512).into(postImageView);

        titleTextView.setText(postModel.getTitle());
        dateTextView.setText(postModel.getCreatedDate());
        descriptionText.setText(postModel.getContent());
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

        commentsAdapter = new CommentsAdapter(DetailAskForHelpActivity.this, commentModelList, this::openProfileActivity);

        commentsRecyclerView.setNestedScrollingEnabled(false);
        commentsRecyclerView.addItemDecoration(new DividerItemDecoration(commentsRecyclerView.getContext(),
                ((LinearLayoutManager) Objects.requireNonNull(commentsRecyclerView.getLayoutManager())).getOrientation()));
        commentsRecyclerView.setAdapter(commentsAdapter);

        getAllCommentOfPost(postModel);
    }

    private void getAllCommentOfPost(PostEntity postModel) {
        if (NetworkUtils.haveNetwork(DetailAskForHelpActivity.this)) {

            Call<List<CommentEntity>> call = apiService.getAllCommentInPost(postModel.getId());
            call.enqueue(new Callback<List<CommentEntity>>() {
                @Override
                public void onResponse(Call<List<CommentEntity>> call, Response<List<CommentEntity>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<CommentEntity> models = response.body();
                        if (!models.isEmpty()) {
                            commentModelList.clear();
                            commentModelList.addAll(models);
                        }
                    }
                    commentsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<CommentEntity>> call, Throwable t) {
                    Toast.makeText(DetailAskForHelpActivity.this, getResources().getString(R.string.co_loi_xay_ra), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DetailAskForHelpActivity.this, getResources().getString(R.string.check_connection_network), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DetailAskForHelpActivity.this, getResources().getString(R.string.please_comment), Toast.LENGTH_SHORT).show();
            return;
        }

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setContent(commentText);
        commentRequest.setCommentUserId(userLogin.getId());

        Call<Void> voidCall = apiService.commentOnPost(postModel.getId(), commentRequest);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getAllCommentOfPost(postModel);
                scrollToFirstComment();
                Toast.makeText(DetailAskForHelpActivity.this, getResources().getString(R.string.commented), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

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

    private void openProfileActivity(CommentEntity model) {
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
