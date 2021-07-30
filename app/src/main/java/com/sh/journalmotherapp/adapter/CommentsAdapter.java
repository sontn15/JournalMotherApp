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
import com.sh.journalmotherapp.model.CommentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.DoctorViewHolder> {
    private final Context mContext;
    private final List<CommentModel> commentModelList;
    private final OnCommentItemClickListener listener;

    public CommentsAdapter(Context mContext, List<CommentModel> commentModelList, OnCommentItemClickListener listener) {
        this.mContext = mContext;
        this.commentModelList = commentModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        CommentModel model = commentModelList.get(position);

        holder.titleTextView.setText(model.getTitle());
        holder.detailsTextView.setText(model.getContent());
        holder.countersContainer.setText("100");

        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(holder.postImageView);

        Picasso.get().load(model.getAuthor().getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(holder.authorImageView);


        holder.watcherCounterTextView.setText(model.getWatchersCount() + "");
        holder.likeCounterTextView.setText(model.getLikesCount() + "");
        holder.commentsCountTextView.setText(model.getCommentsCount() + "");
        holder.dateTextView.setText(model.getCreatedDate());

        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        if (commentModelList != null) {
            return commentModelList.size();
        } else {
            return 0;
        }
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleTextView, detailsTextView, countersContainer;
        protected ImageView postImageView;
        protected CircleImageView authorImageView;

        protected TextView watcherCounterTextView, likeCounterTextView, commentsCountTextView, dateTextView;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            countersContainer = itemView.findViewById(R.id.countersContainer);
            postImageView = itemView.findViewById(R.id.postImageView);
            authorImageView = itemView.findViewById(R.id.authorImageView);

            watcherCounterTextView = itemView.findViewById(R.id.watcherCounterTextView);
            likeCounterTextView = itemView.findViewById(R.id.likeCounterTextView);
            commentsCountTextView = itemView.findViewById(R.id.commentsCountTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        public void bind(final CommentModel model, final OnCommentItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClickItem(model));
        }
    }

    public interface OnCommentItemClickListener {
        void onClickItem(CommentModel model);

        void onLikeClick(CommentModel model);

        void onCommentClick(CommentModel model);
    }

}