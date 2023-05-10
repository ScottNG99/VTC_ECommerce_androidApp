package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.ChatMessage;
import com.example.vtc_ecommerce_androidapp.ModelClass.Message;
import com.example.vtc_ecommerce_androidapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TwoMessageAdapter extends RecyclerView.Adapter<TwoMessageAdapter.ViewHolder>{
    private String currentUser;
    private List<Message> messages;
    private Context context;

    public TwoMessageAdapter(String currentUser, List<Message> messages, Context context) {
        this.currentUser = currentUser;
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            // 加載用戶回覆的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
        } else {
            // 加載機器人回覆的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_received, parent, false);
        }
        return new TwoMessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);

        String dtStart = messages.get(position).getTimestamp();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(dtStart);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            String formattedTime = sdf.format(date);
            holder.timeText.setText(formattedTime); // 将格式化的时间字符串设置到时间文本视图中
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.messageTextView.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {

        Boolean isUsers;
        currentUser.equals(String.valueOf(SharedPrefManager.getInstance(context).getStudent().getColleges()));

//        if (currentUser.equals(String.valueOf(SharedPrefManager.getInstance(context).getStudent().getColleges()))){
//            isUsers = true;
//        }else {
//            isUsers = false;
//        }

        if (messages.get(position).getSender().equals(currentUser)){
            isUsers = true;
        }else {
            isUsers = false;
        }

        if (isUsers) {

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

        public void bind(Message chatMessage) {
            Boolean isUser;

            if (currentUser.equals(String.valueOf(SharedPrefManager.getInstance(context).getStudent().getColleges()))){
                isUser = true;
            }else {
                isUser = false;
            }
            messageTextView.setText(chatMessage.getMessage());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageTextView.getLayoutParams();
            layoutParams.gravity = isUser ? Gravity.RIGHT : Gravity.LEFT;

        }
    }
}
