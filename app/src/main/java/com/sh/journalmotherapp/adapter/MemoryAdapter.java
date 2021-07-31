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
import com.sh.journalmotherapp.model.MemoryModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder> {
    private final Context mContext;
    private final List<MemoryModel> models;
    private final OnPostItemClickListener listener;

    public MemoryAdapter(Context mContext, List<MemoryModel> models, OnPostItemClickListener listener) {
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
        MemoryModel model = models.get(position);


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
        protected TextView titleTextView, detailsTextView;
        protected ImageView postImageView;
        protected CircleImageView authorImageView;

        protected TextView dateTextView;

        public MemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            postImageView = itemView.findViewById(R.id.postImageView);
            authorImageView = itemView.findViewById(R.id.authorImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        public void bind(final MemoryModel model, final OnPostItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClickItem(model));
        }
    }

    public interface OnPostItemClickListener {
        void onClickItem(MemoryModel model);
    }

}