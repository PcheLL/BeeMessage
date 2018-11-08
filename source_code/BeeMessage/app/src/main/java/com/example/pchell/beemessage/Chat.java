package com.example.pchell.beemessage;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.Date;

public class Chat extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("messeges");
    private Button bUserOnline;
    private Button bSend;
    private EditText etText;
    private TextView tvChat;
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
        tvChat = (TextView) findViewById(R.id.textviewChat);
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
        databaseReference.push().setValue(msg);
        etText.setText("");

        displaychat();
    }

    public void displaychat(){

    }


}
