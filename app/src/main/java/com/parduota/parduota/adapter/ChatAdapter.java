package com.parduota.parduota.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.parduota.parduota.model.Chat;

import java.util.ArrayList;

/**
 * Created by huy_quynh on 10/30/17.
 */

class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {


    private final ArrayList<Chat> chats;

    public ChatAdapter(Context context, ArrayList<Chat> chats) {
        Context context1 = context;
        this.chats = chats;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
