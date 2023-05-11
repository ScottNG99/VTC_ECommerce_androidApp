package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.OnlineMessageAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.TwoMessageAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.Message;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineChatActivity extends AppCompatActivity {
    private EditText messageEditText;
    private ImageButton sendButton;
    private RecyclerView messageRecyclerView;
    private OnlineMessageAdapter messageAdapter;
    private List<Message> messages;



    private TwoMessageAdapter twoMessageAdapter;

    //private static final String SERVER_URL = "http://192.168.1.218/ecommerce/OnlineChatAPI.php?";
    private static String CURRENT_USER_ID;
    private static final String RECEIVER_ID = "251114324";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_chat);



        CURRENT_USER_ID = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getStudent().getColleges());

        messageEditText = findViewById(R.id.onlineMessageEditText);
        sendButton = findViewById(R.id.onlineSendButton);
        messageRecyclerView = findViewById(R.id.onlineChatRecyclerView);

        // 初始化消息列表和适配器
        messages = new ArrayList<>();
        System.out.println("this cureid " + CURRENT_USER_ID);
        messageAdapter = new OnlineMessageAdapter(messages, CURRENT_USER_ID);


        twoMessageAdapter = new TwoMessageAdapter(CURRENT_USER_ID,messages,getApplicationContext());

        // 设置 RecyclerView
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //messageRecyclerView.setAdapter(messageAdapter);
        messageRecyclerView.setAdapter(twoMessageAdapter);

        // 设置发送按钮的点击事件监听器
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString().trim();


                if (!message.isEmpty()) {
                    System.out.println("have text msg " + message);
                    sendMessage(message);
                    messageEditText.getText().clear();
                }
            }
        });







        // 加载聊天消息数据
        //loadMessages();
        checkNewMessages();
    }

//    private void loadMessages() {
//        String url = SERVER_URL + "sender=" + CURRENT_USER_ID;
//        System.out.println("show chat url " + url);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("en is 1 ");
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            System.out.println("en is 2 " + jsonArray.length());
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonMessage = jsonArray.getJSONObject(i);
//
//                                String sender = jsonMessage.getString("sender");
//                                String receiver = jsonMessage.getString("receiver");
//                                String message = jsonMessage.getString("message");
//                                String timestamp = jsonMessage.getString("timestamp");
//                                String isRead = jsonMessage.getString("is_read");
//
//                                System.out.println("show msg " + message);
//
//                                Message msg = new Message(sender,receiver,message,timestamp,isRead);
//                                messages.add(msg);
//                            }
//
//                            // 刷新消息列表
//                            //messageAdapter.notifyDataSetChanged();
//                            twoMessageAdapter.notifyDataSetChanged();
//                            scrollToBottom();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//        // 添加请求到请求队列
//        Volley.newRequestQueue(this).add(stringRequest);
//    }

    private void checkNewMessages() {
        String url = Config.GETCHAT + "sender=" + CURRENT_USER_ID;
        System.out.println("show chat url " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("en is 1 ");
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println("en is 2 " + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonMessage = jsonArray.getJSONObject(i);

                                String sender = jsonMessage.getString("sender");
                                String receiver = jsonMessage.getString("receiver");
                                String message = jsonMessage.getString("message");
                                String timestamp = jsonMessage.getString("timestamp");
                                String isRead = jsonMessage.getString("is_read");

                                System.out.println("show msg " + message);

                                Message msg = new Message(sender,receiver,message,timestamp,isRead);
                                messages.add(msg);
                            }

                            // 刷新消息列表
                            //messageAdapter.notifyDataSetChanged();
                            twoMessageAdapter.notifyDataSetChanged();
                            scrollToBottom();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // 添加请求到请求队列
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // 轮询间隔时间（毫秒）
    private static final int POLLING_INTERVAL = 5000; // 5 seconds

    private Handler handler = new Handler();
    private Runnable pollMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            checkNewMessages();
            handler.postDelayed(this, POLLING_INTERVAL);
        }
    };

    // 在onStart()方法中启动轮询
    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(pollMessagesRunnable, POLLING_INTERVAL);
    }

    // 在onStop()方法中停止轮询
    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(pollMessagesRunnable);
    }

//    private void sendMessage(final String message) {
//        // 构建 POST 请求参数
//        final String uID = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getStudent().getColleges());
//        final String adminNo = "251114324";
//        final String isRead = "0";
//        final String url = SERVER_URL;
//
//        System.out.println("show tewi path "  + url);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // 发送成功，将消息添加到列表中并刷新
//                        Message msg = new Message(CURRENT_USER_ID, RECEIVER_ID, message, "", isRead);
//                        System.out.println("send msg ? " + CURRENT_USER_ID + " and " + RECEIVER_ID + " and " + message);
//                        messages.add(msg);
//                        messageAdapter.notifyDataSetChanged();
//                        scrollToBottom();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("sender", uID);
//                params.put("receiver", adminNo);
//                params.put("message", message);
//                params.put("is_read", isRead);
//                return params;
//            }
//        };
//
//        // 添加请求到请求队列
//        Volley.newRequestQueue(this).add(stringRequest);
//    }




    private void sendMessage(final String message) {
        // 构建 POST 请求参数
        final String uID = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getStudent().getColleges());
        final String adminNo = "251114324";
        final String isRead = "0";
        final String url = Config.GETCHAT;

        System.out.println("show tewi path "  + url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 发送成功，将消息添加到列表中并刷新
                        Message msg = new Message(CURRENT_USER_ID, RECEIVER_ID, message, "", isRead);
                        System.out.println("send msg ? " + CURRENT_USER_ID + " and " + RECEIVER_ID + " and " + message);
                        messages.add(msg);
                        messageAdapter.notifyDataSetChanged();
                        twoMessageAdapter.notifyDataSetChanged();
                        scrollToBottom();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender", uID);
                params.put("receiver", adminNo);
                params.put("message", message);
                params.put("is_read", isRead);
                return params;
            }
        };

        // 添加请求到请求队列
        Volley.newRequestQueue(this).add(stringRequest);
    }



    private void scrollToBottom() {
        //messageRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        messageRecyclerView.scrollToPosition(twoMessageAdapter.getItemCount() - 1);
    }
}