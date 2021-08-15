package com.crm.pvt.hapinicrm.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.databinding.ChatPreviewBinding;
import com.crm.pvt.hapinicrm.model.TrackUserModel;
import com.crm.pvt.hapinicrm.util.FranchiseChatPreviewClickCallback;

import java.util.ArrayList;

public class FranchiseChatPreviewAdapter extends RecyclerView.Adapter<FranchiseChatPreviewAdapter.ViewHolder> {

    ArrayList<TrackUserModel> users;
    FranchiseChatPreviewClickCallback callback;

    public FranchiseChatPreviewAdapter(FranchiseChatPreviewClickCallback callback) {
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
        TrackUserModel user = users.get(position);
        holder.binding.tvName.setText(user.getName());
        holder.binding.cvChatPreview.setOnClickListener(v -> callback.navigateToChatsScreen(user));
    }

    @Override
    public int getItemCount() {
        if(users != null) {
            return users.size();
        }
        return 0;
    }

    public void setUsers(ArrayList<TrackUserModel> users) {
        this.users = users;
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
