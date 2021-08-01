package com.sh.journalmotherapp.ui.news;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.model.NewsModel;
import com.sh.journalmotherapp.util.Const;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailNewsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView detailsTextView;
    private ImageView imvImage;

    private NewsModel newsModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News detail");

        initViews();
        initData();
    }

    private void initViews() {
        titleTextView = this.findViewById(R.id.titleTextView);
        detailsTextView = this.findViewById(R.id.detailsTextView);
        imvImage = this.findViewById(R.id.imvImage);
    }

    private void initData() {
        newsModel = getIntent().getExtras().getParcelable(Const.NEWS_SELECTED);

        if (newsModel != null) {
            titleTextView.setText(newsModel.getTitle());
            detailsTextView.setText(newsModel.getContent());

            Picasso.get().load(newsModel.getImageUrl()).placeholder(R.drawable.ic_app_512)
                    .error(R.drawable.ic_app_512).into(imvImage);
        } else {
            Toast.makeText(DetailNewsActivity.this, getResources().getString(R.string.co_loi_xay_ra), Toast.LENGTH_SHORT).show();
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

}
