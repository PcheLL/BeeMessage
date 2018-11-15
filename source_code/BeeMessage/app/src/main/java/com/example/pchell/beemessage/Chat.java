package com.example.pchell.beemessage;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Messeges");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Button bUserOnline;
    private Button bSend;
    private EditText etText;
    private ListView lvChat;
    private String msg;
    private String textmessage;
    private String autor;
    private long timemessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_chat);
        bUserOnline = (Button) findViewById(R.id.buttonUserOnline);
        bUserOnline.setOnClickListener((View.OnClickListener) this);
        bSend = (Button) findViewById(R.id.buttonSend);
        bSend.setOnClickListener((View.OnClickListener) this);
        etText = (EditText) findViewById(R.id.editTextText);
        lvChat = (ListView) findViewById(R.id.listViewChat);
        //thr.start(); - разобраться с потоком !!!!1
    }

    @Override
    public void onClick(View view) {
        Intent intent_UsersO = new Intent(this,UsersOnline.class);
        switch (view.getId()) {
            case R.id.buttonUserOnline: startActivity(intent_UsersO); break;
            case R.id.buttonSend: msg = etText.getText().toString();
            if (msg.equals(""))
            {
                Toast.makeText(Chat.this,"Введите текст сообщения !!!",Toast.LENGTH_SHORT).show();
            }
            else{

                send_msg();
            }
        }

    }
    public void send_msg(){
        Toast.makeText(Chat.this,"Отправлено !!!",Toast.LENGTH_SHORT).show();
        myRef.push().setValue(msg);
        etText.setText("");

        displaychat();
    }

    public void displaychat(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot chatDS) {
                final List<String> chatList = new ArrayList<String>();
                for (DataSnapshot battle : chatDS.getChildren())
                    chatList.add((String) battle.getValue());

                ArrayAdapter<String> chatAdapter = new ArrayAdapter<>(Chat.this,
                        android.R.layout.simple_list_item_1,
                        chatList.toArray(new String[chatList.size()]));
                lvChat.setAdapter(chatAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    Thread thr = new Thread(){
        int m = 0;

        @Override
        public void run(){

            do{
                lvChat.deferNotifyDataSetChanged();
            }while (m!=1);

        }
    };
}
