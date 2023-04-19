package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.vtc_ecommerce_androidapp.Adater.MessageAdapter;
import com.example.vtc_ecommerce_androidapp.ModelClass.ChatMessage;
import com.example.vtc_ecommerce_androidapp.R;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class ChatRoomActivity extends AppCompatActivity {

    private ImageView imgBackprofile;

    private EditText messageEditText;
    private ImageButton sendButton;
    private RecyclerView chatRecyclerView;

    private ArrayList<ChatMessage> chatMessages;
    //private ChatAdapter chatAdapter;
    private MessageAdapter messageAdapter;

    private SessionsClient sessionsClient;
    private SessionName session;

    private final String DIALOGFLOW_LANGUAGE_CODE = "en-US";
    private final String DIALOGFLOW_PROJECT_ID = "eshopbot-kwaf";
    //private final String GOOGLE_APPLICATION_CREDENTIALS = "eshopbot.json";
    private final String GOOGLE_APPLICATION_CREDENTIALS = "eshopbot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        imgBackprofile = findViewById(R.id.backtoprofile);

        // Get views
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        // Initialize chat messages array and adapter
        chatMessages = new ArrayList<>();

        //chatAdapter = new ChatAdapter(chatMessages);
        messageAdapter = new MessageAdapter(chatMessages);

        // Set chat adapter and layout manager for recycler view
        //chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setAdapter(messageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);

        // Initialize Dialogflow sessions client
        SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
        try {
            //InputStream stream = getResources().getAssets().open(GOOGLE_APPLICATION_CREDENTIALS);
            InputStream stream = getApplication().getResources().openRawResource(
                    getApplication().getResources().getIdentifier(
                            GOOGLE_APPLICATION_CREDENTIALS,
                            "raw",
                            getApplication().getPackageName()
                    )
            );
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)
            );
            SessionsSettings sessionsSettings = settingsBuilder.build();
            sessionsClient = SessionsClient.create(sessionsSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        session = SessionName.of(DIALOGFLOW_PROJECT_ID, UUID.randomUUID().toString());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    messageEditText.getText().clear();
                }
            }
        });






        imgBackprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatRoomActivity.this,MyProfileActivity.class);
                startActivity(intent);
            }
        });


    }


    private void sendMessage(String message) {
        //ChatMessage chatMessage = new ChatMessage(message, true);
        //chatAdapter.addMessage(chatMessage);
        //messageAdapter.addMessage(chatMessage);
        chatMessages.add(new ChatMessage(message,true, Calendar.getInstance().getTime()));
        messageAdapter.notifyItemInserted(chatMessages.size() - 1);
        QueryInput queryInput = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode(DIALOGFLOW_LANGUAGE_CODE))
                .build();

        DetectIntentResponse response = null;
        try {
            response = sessionsClient.detectIntent(session, queryInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response != null) {
            String botMessage = response.getQueryResult().getFulfillmentText();
            //ChatMessage botChatMessage = new ChatMessage(botMessage, false);
            //chatAdapter.addMessage(botChatMessage);
            chatMessages.add(new ChatMessage(botMessage,false,Calendar.getInstance().getTime()));
            messageAdapter.notifyItemInserted(chatMessages.size() - 1);
        }
    }
}