package com.example.vtc_ecommerce_androidapp.Adater;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vtc_ecommerce_androidapp.ModelClass.ChatMessage;
import com.example.vtc_ecommerce_androidapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private List<ChatMessage> chatMessages;

    public MessageAdapter(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

//    public void addMessage(ChatMessage chatMessage) {
//        chatMessages.add(chatMessage);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
//
//        return new ViewHolder(view);

        View view;
        if (viewType == 0) {
            // 加載用戶回覆的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
        } else {
            // 加載機器人回覆的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.bind(chatMessages.get(position));
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String currentDateTimeString = sdf.format(chatMessages.get(position).getTime());
        holder.timeText.setText(currentDateTimeString);
        holder.messageTextView.setText(chatMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).isUser()) {
            return 0; // 用戶回覆
        } else {
            return 1; // 機器人回覆
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView, timeText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        public void bind(ChatMessage chatMessage) {
            messageTextView.setText(chatMessage.getMessage());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageTextView.getLayoutParams();
            layoutParams.gravity = chatMessage.isUser() ? Gravity.RIGHT : Gravity.LEFT;

        }
    }

}
