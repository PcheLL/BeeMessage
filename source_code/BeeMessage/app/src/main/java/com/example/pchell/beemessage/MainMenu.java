package com.example.pchell.beemessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Button bChat;
    private Button bUsers;
    private Button bMyTasks;
    private Button bExit;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_main_menu);


        System.out.print(user);
        bChat = (Button) findViewById(R.id.buttonChat);
        bChat.setOnClickListener((View.OnClickListener) this);
        bUsers = (Button) findViewById(R.id.buttonUsers);
        bUsers.setOnClickListener((View.OnClickListener) this);
        bMyTasks = (Button) findViewById(R.id.buttonMyTasks);
        bMyTasks.setOnClickListener((View.OnClickListener) this);
        bExit = (Button) findViewById(R.id.buttonExit);
        bExit.setOnClickListener((View.OnClickListener)this);


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
            //-----Выход из учетной записи
            case R.id.buttonExit:

                //mAuth.signOut();
                 //   Intent intent = new Intent(MainMenu.this,MyPhone.class);
                 //   startActivity(intent);
                break;
        }
    }



}
