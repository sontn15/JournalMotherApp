package com.sh.journalmotherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.custom.ExpandableTextView;
import com.sh.journalmotherapp.model.CommentEntity;
import com.sh.journalmotherapp.util.FormatterUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.SneakyThrows;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private final Context mContext;
    private final List<CommentEntity> commentModelList;
    private final OnCommentItemClickListener listener;

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public CommentsAdapter(Context mContext, List<CommentEntity> commentModelList, OnCommentItemClickListener listener) {
        this.mContext = mContext;
        this.commentModelList = commentModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_comment, parent, false);
        return new CommentsAdapter.CommentViewHolder(view);
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentEntity model = commentModelList.get(position);

        long timestamp = Objects.requireNonNull(dateFormat.parse(model.getCreatedDate())).getTime();
        CharSequence date = FormatterUtil.getRelativeTimeSpanString(mContext, timestamp);
        holder.dateTextView.setText(date);

        Spannable contentString = new SpannableStringBuilder(model.getUser().getFullName() + "   " + model.getContent());
        contentString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.highlight_text)),
                0, model.getUser().getFullName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.commentTextView.setText(model.getContent());

        holder.tvNameUserComment.setText(model.getUser().getFullName());

        Picasso.get().load(model.getUser().getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(holder.avatarImageView);

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

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        protected TextView dateTextView, tvNameUserComment;
        protected CircleImageView avatarImageView;
        protected ExpandableTextView commentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUserComment = itemView.findViewById(R.id.tvNameUserComment);
            commentTextView = itemView.findViewById(R.id.commentTextComment);
            avatarImageView = itemView.findViewById(R.id.avatarImageViewComment);
            dateTextView = itemView.findViewById(R.id.dateTextViewComment);
        }

        public void bind(final CommentEntity model, final OnCommentItemClickListener listener) {
            avatarImageView.setOnClickListener(v -> listener.onClickAuthor(model));
        }
    }

    public interface OnCommentItemClickListener {
        void onClickAuthor(CommentEntity model);
    }

}