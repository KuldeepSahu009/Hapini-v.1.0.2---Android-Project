package com.crm.pvt.hapinicrm.adapters;

import static com.crm.pvt.hapinicrm.ui.UserLoginFragment.currentUser;
import static com.crm.pvt.hapinicrm.ui.AdminLoginFragment.currentFranchise;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.databinding.ChatsLayoutBinding;
import com.crm.pvt.hapinicrm.model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<Chat> chats;
//
    public ChatAdapter() { }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatsLayoutBinding binding = ChatsLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        String passcode = "";

        if(currentUser != null) {
            passcode = currentUser.getPasscode();
        } else if(currentFranchise != null) {
            passcode = currentFranchise.getPasscode();
        }

        if(passcode.equals(chat.getSenderPasscode())) {
            //TODO chat gravity
        }

        holder.binding.tvSendersName.setText(chat.getSendersName());
        holder.binding.tvMessage.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        if(chats != null) {
            return chats.size();
        }
        return 0;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ChatsLayoutBinding binding;
        public ViewHolder(@NonNull ChatsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
