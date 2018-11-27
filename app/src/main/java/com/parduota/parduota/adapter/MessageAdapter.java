package com.parduota.parduota.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parduota.parduota.R;
import com.parduota.parduota.model.MessageResponse;

import java.util.List;

/**
 * Created by MAC2015 on 12/4/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private final Context context;
    private final List<MessageResponse> messageResponses;

    public MessageAdapter(Context context, List<MessageResponse> messageResponses) {
        this.context = context;
        this.messageResponses = messageResponses;
    }


    @Override
    public int getItemViewType(int position) {
        return messageResponses.get(position).getCreatedBy();
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("TYPE", viewType + "");
        View view;
        if (viewType == 1)
        view = LayoutInflater.from(context).inflate(R.layout.message_item_out, parent, false);
        else view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.messageResponse = messageResponses.get(position);
        holder.tvFrom.setText(holder.messageResponse.getType());
        holder.tvMessage.setText(holder.messageResponse.getMessages());
        holder.tvDateTime.setText(holder.messageResponse.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return messageResponses.size();
    }
}
