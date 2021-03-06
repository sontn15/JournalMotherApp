package com.sh.journalmotherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.model.PostEntity;
import com.sh.journalmotherapp.util.Const;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class TheJournalAdapter extends RecyclerView.Adapter<TheJournalAdapter.PostViewHolder> {
    private final Context mContext;
    private final List<PostEntity> listPostModels;
    private final OnPostItemClickListener listener;

    public TheJournalAdapter(Context mContext, List<PostEntity> listPostModels, OnPostItemClickListener listener) {
        this.mContext = mContext;
        this.listPostModels = listPostModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_the_journal, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostEntity model = listPostModels.get(position);

        holder.titleTextView.setText(model.getTitle());
        holder.detailsTextView.setText(model.getContent());

        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.ic_stub)
                .error(R.drawable.ic_stub).into(holder.postImageView);

        String authorImageUrl = model.getAuthor().getImageUrl();
        if (model.getIsAnonymous()) {
            authorImageUrl = Const.ANONYMOUS_IMAGE_URL;
        }

        Picasso.get().load(authorImageUrl).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(holder.authorImageView);

        holder.dateTextView.setText(model.getCreatedDate());
        holder.tvHashtag.setText(model.getType());

        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        if (listPostModels != null) {
            return listPostModels.size();
        } else {
            return 0;
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleTextView, detailsTextView, tvHashtag;
        protected ImageView postImageView;
        protected CircleImageView authorImageView;

        protected TextView dateTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            postImageView = itemView.findViewById(R.id.postImageView);
            authorImageView = itemView.findViewById(R.id.authorImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            tvHashtag = itemView.findViewById(R.id.tvHashtag);
        }

        public void bind(final PostEntity model, final OnPostItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClickItem(model));
        }
    }

    public interface OnPostItemClickListener {
        void onClickItem(PostEntity model);
    }

}