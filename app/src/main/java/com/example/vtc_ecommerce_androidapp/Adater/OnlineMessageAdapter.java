package com.example.vtc_ecommerce_androidapp.Adater;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vtc_ecommerce_androidapp.ModelClass.Message;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.List;

public class OnlineMessageAdapter  extends RecyclerView.Adapter<OnlineMessageAdapter.ViewHolder>{

    private List<Message> messages;
    private String currentUser;

    public OnlineMessageAdapter(List<Message> messages, String currentUser) {
        this.messages = messages;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);

        // 根据消息发送者设置不同的布局和位置
        if (message.getSender().equals(currentUser)) {
            // 设置发送者消息布局（右侧）
            holder.messageTextView.setBackgroundResource(R.drawable.rounded_rectangle_orange);
            holder.messageTextView.setText(message.getMessage());
            holder.messageTextView.setGravity(Gravity.END);
        } else {
            // 设置接收者消息布局（左侧）
            holder.messageTextView.setBackgroundResource(R.drawable.rounded_rectangle_green);
            holder.messageTextView.setText(message.getMessage());
            holder.messageTextView.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
