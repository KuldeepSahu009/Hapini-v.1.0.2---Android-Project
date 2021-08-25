package com.crm.pvt.hapinicrm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.databinding.ChatPreviewBinding;
import com.crm.pvt.hapinicrm.model.Franchise;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.util.ChatPreviewClickCallback;
import com.crm.pvt.hapinicrm.util.FranchiseChatPreviewClickCallback;

import java.util.ArrayList;

public class ChatPreviewAdapter extends RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder> {

    ArrayList<TrackUserModel> franchises;
    ChatPreviewClickCallback callback;

    public ChatPreviewAdapter (ChatPreviewClickCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatPreviewBinding binding = ChatPreviewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrackUserModel franchise = franchises.get(position);
        holder.binding.tvName.setText(franchise.getName());
        holder.binding.cvChatPreview.setOnClickListener(v -> callback.navigateToChatsScreen(franchise));
    }

    @Override
    public int getItemCount() {
        if (franchises != null) {
            return franchises.size();
        }
        return 0;
    }

    public void setFranchises(ArrayList<TrackUserModel> franchises) {
        this.franchises = franchises;
        notifyDataSetChanged();
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ChatPreviewBinding binding;
        public ViewHolder(@NonNull ChatPreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
