package com.example.pchell.beemessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Button bChat;
    private Button bUsers;
    private Button bMyTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_main_menu);
        bChat = (Button) findViewById(R.id.buttonChat);
        bChat.setOnClickListener((View.OnClickListener) this);
        bUsers = (Button) findViewById(R.id.buttonUsers);
        bUsers.setOnClickListener((View.OnClickListener) this);
        bMyTasks = (Button) findViewById(R.id.buttonMyTasks);
        bMyTasks.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        Intent intent_Chat = new Intent(this,Chat.class);
        Intent intent_Users = new Intent(this,Users.class);
        Intent intent_MyTasks = new Intent(this,MyTasks.class);
        switch (view.getId()) {
            case R.id.buttonChat: startActivity(intent_Chat); break;
            case R.id.buttonUsers: startActivity(intent_Users); break;
            case R.id.buttonMyTasks: startActivity(intent_MyTasks); break;
        }
    }
}
