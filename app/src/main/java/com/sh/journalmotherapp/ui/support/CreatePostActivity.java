package com.sh.journalmotherapp.ui.support;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sh.journalmotherapp.R;

import java.util.Objects;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private EditText titleEditText, descriptionEditText;
    private Button btnCancel, btnPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng bài");

        initView();
    }

    private void initView() {
        imageView = this.findViewById(R.id.imageView);
        titleEditText = this.findViewById(R.id.titleEditText);
        descriptionEditText = this.findViewById(R.id.descriptionEditText);

        btnPost = this.findViewById(R.id.btnPost);
        btnCancel = this.findViewById(R.id.btnCancel);

        btnPost.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imageView.setOnClickListener(this);
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
            case R.id.imageView: {

                break;
            }
            case R.id.btnPost: {

                break;
            }

            case R.id.btnCancel: {

                break;
            }
        }
    }
}
