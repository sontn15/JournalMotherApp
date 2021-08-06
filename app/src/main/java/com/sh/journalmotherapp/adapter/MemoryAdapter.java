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
import com.squareup.picasso.Picasso;

import java.util.List;


public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder> {
    private final Context mContext;
    private final List<PostEntity> models;
    private final OnPostItemClickListener listener;

    public MemoryAdapter(Context mContext, List<PostEntity> models, OnPostItemClickListener listener) {
        this.mContext = mContext;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_memory, parent, false);
        return new MemoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryViewHolder holder, int position) {
        PostEntity model = models.get(position);
        holder.tvContent.setText(model.getContent());
        holder.tvFeeling.setText(model.getTitle());
        holder.dateTextView.setText(model.getCreatedDate());

        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.ic_stub)
                .error(R.drawable.ic_stub).into(holder.imv_memory);

        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        if (models != null) {
            return models.size();
        } else {
            return 0;
        }
    }

    public static class MemoryViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvFeeling, tvContent;
        protected ImageView imv_memory;

        protected TextView dateTextView;

        public MemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeeling = itemView.findViewById(R.id.tv_feeling);
            tvContent = itemView.findViewById(R.id.contentTextView);
            imv_memory = itemView.findViewById(R.id.memoryImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView_memory);
        }

        public void bind(final PostEntity model, final OnPostItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClickItem(model));
        }
    }

    public interface OnPostItemClickListener {
        void onClickItem(PostEntity model);
    }

}