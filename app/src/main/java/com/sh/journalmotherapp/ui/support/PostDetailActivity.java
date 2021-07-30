package com.sh.journalmotherapp.ui.support;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.adapter.CommentsAdapter;
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.ui.main.BaseActivity;

import java.util.Objects;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private EditText commentEditText;

    private ScrollView scrollView;
    private ViewGroup likesContainer;
    private ImageView likesImageView;
    private TextView commentsLabel;
    private TextView likeCounterTextView;
    private TextView commentsCountTextView;
    private TextView watcherCounterTextView;
    private TextView authorTextView;
    private TextView dateTextView;
    private ImageView authorImageView;
    private ProgressBar progressBar;
    private ImageView postImageView;
    private TextView titleTextView;
    private TextView descriptionEditText;
    private ProgressBar commentsProgressBar;
    private RecyclerView commentsRecyclerView;
    private TextView warningCommentsTextView;

    private boolean attemptToLoadComments = false;

    private MenuItem complainActionMenuItem;
    private MenuItem editActionMenuItem;
    private MenuItem deleteActionMenuItem;

    private String postId;

    private boolean postRemovingProcess = false;
    private boolean isPostExist;
    private boolean authorAnimationInProgress = false;

    private boolean isAuthorAnimationRequired;
    private CommentsAdapter commentsAdapter;
    private ActionMode mActionMode;
    private boolean isEnterTransitionFinished = false;

    private Button sendButton;

    @Nullable
    private PostModel postModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initView();
        initRecyclerView();
    }


    private void initView() {
        titleTextView = findViewById(R.id.titleTextView);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        postImageView = findViewById(R.id.postImageView);
        progressBar = findViewById(R.id.progressBar);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        scrollView = findViewById(R.id.scrollView);
        commentsLabel = findViewById(R.id.commentsLabel);
        commentEditText = findViewById(R.id.commentEditText);
        likesContainer = findViewById(R.id.likesContainer);
        likesImageView = findViewById(R.id.likesImageView);
        authorImageView = findViewById(R.id.authorImageView);
        authorTextView = findViewById(R.id.authorTextView);
        likeCounterTextView = findViewById(R.id.likeCounterTextView);
        commentsCountTextView = findViewById(R.id.commentsCountTextView);
        watcherCounterTextView = findViewById(R.id.watcherCounterTextView);
        dateTextView = findViewById(R.id.dateTextView);
        commentsProgressBar = findViewById(R.id.commentsProgressBar);
        warningCommentsTextView = findViewById(R.id.warningCommentsTextView);

        sendButton = findViewById(R.id.sendButton);


        postImageView.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        commentsCountTextView.setOnClickListener(this);
        commentEditText.addTextChangedListener(this);

        supportPostponeEnterTransition();
    }

    private void initRecyclerView() {
        commentsAdapter = new CommentsAdapter();
        commentsAdapter.setCallback(new CommentsAdapter.Callback() {
            @Override
            public void onLongItemClick(View view, int position) {
                Comment selectedComment = commentsAdapter.getItemByPosition(position);
                startActionMode(selectedComment);
            }

            @Override
            public void onAuthorClick(String authorId, View view) {
                openProfileActivity(authorId, view);
            }
        });

        commentsRecyclerView.setAdapter(commentsAdapter);
        commentsRecyclerView.setNestedScrollingEnabled(false);
        commentsRecyclerView.addItemDecoration(new DividerItemDecoration(commentsRecyclerView.getContext(),
                ((LinearLayoutManager) commentsRecyclerView.getLayoutManager()).getOrientation()));

        commentManager.getCommentsList(this, postId, createOnCommentsChangedDataListener());
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
            case R.id.postImageView: {
                openImageDetailScreen();
                break;
            }
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

        if (commentText.length() > 0 && isPostExist) {
            commentManager.createOrUpdateComment(commentText, post.getId(), new OnTaskCompleteListener() {
                @Override
                public void onTaskComplete(boolean success) {
                    if (success) {
                        scrollToFirstComment();
                    }
                }
            });
            commentEditText.setText(null);
            commentEditText.clearFocus();
            hideKeyBoard();
        }
    }

    private void openImageDetailScreen() {
        if (postModel != null) {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra(ImageDetailActivity.IMAGE_URL_EXTRA_KEY, postModel.getImageUrl());
            startActivity(intent);
        }
    }

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void scrollToFirstComment() {
        if (postModel != null && postModel.getCommentsCount() > 0) {
            scrollView.smoothScrollTo(0, commentsLabel.getTop());
        }
    }

    private void openProfileActivity(String userId, View view) {
        Intent intent = new Intent(PostDetailActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER_ID_EXTRA_KEY, userId);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(PostDetailActivity.this,
                            new android.util.Pair<>(view, getString(R.string.post_author_image_transition_name)));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_details_menu, menu);

        editActionMenuItem = menu.findItem(R.id.edit_post_action);
        deleteActionMenuItem = menu.findItem(R.id.delete_post_action);

        if (postModel != null) {
            updateOptionMenuVisibility();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isPostExist) {
            return super.onOptionsItemSelected(item);
        }

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_post_action:
                if (hasAccessToModifyPost()) {
                    openEditPostActivity();
                }
                return true;

            case R.id.delete_post_action:
                if (hasAccessToModifyPost()) {
                    attemptToRemovePost();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptToRemovePost() {
        if (hasInternetConnection()) {
            if (!postRemovingProcess) {
                openConfirmDeletingDialog();
            }
        } else {
            showSnackBar(R.string.internet_connection_failed);
        }
    }

    private void openConfirmDeletingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_deletion_post)
                .setNegativeButton(R.string.button_title_cancel, null)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removePost();
                    }
                });

        builder.create().show();
    }

    private void removePost() {
        postManager.removePost(post, new OnTaskCompleteListener() {
            @Override
            public void onTaskComplete(boolean success) {
                if (success) {
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent.putExtra(POST_STATUS_EXTRA_KEY, PostStatus.REMOVED));
                    finish();
                } else {
                    postRemovingProcess = false;
                    showSnackBar(R.string.error_fail_remove_post);
                }

                hideProgress();
            }
        });

        showProgress(R.string.removing);
        postRemovingProcess = true;
    }

    private boolean hasAccessToModifyPost() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser != null && post != null && post.getAuthorId().equals(currentUser.getUid());
    }

    private void updateOptionMenuVisibility() {
        if (editActionMenuItem != null && deleteActionMenuItem != null && hasAccessToModifyPost()) {
            editActionMenuItem.setVisible(true);
            deleteActionMenuItem.setVisible(true);
        }

        if (complainActionMenuItem != null && postModel != null && !post.isHasComplain()) {
            complainActionMenuItem.setVisible(true);
        }
    }

    private void openEditPostActivity() {
        if (hasInternetConnection()) {
            Intent intent = new Intent(PostDetailActivity.this, EditPostActivity.class);
            intent.putExtra(EditPostActivity.POST_EXTRA_KEY, post);
            startActivityForResult(intent, EditPostActivity.EDIT_POST_REQUEST);
        } else {
            showSnackBar(R.string.internet_connection_failed);
        }
    }

}
