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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Button bChat;
    private Button bUsers;
    private Button bMyTasks;
    private Button bExit;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userKey;
    private String autor;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefUserOnline = database.getReference("UsersOnline");
    private DatabaseReference myRefUsers = database.getReference("Users");

    private void userOnline() {
        userKey = mAuth.getCurrentUser().getUid();
        //-----Получаем имя из списка Users по KEY (личному)
        myRefUsers.child(userKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        autor = (String) dataSnapshot.getValue();
                        myRefUserOnline.child(userKey).setValue(autor);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        myRefUserOnline.child(userKey).onDisconnect().removeValue();
    }


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
        userOnline();
    }

    @Override
    public void onClick(View view) {
        Intent intent_Chat = new Intent(this, Chat.class);
        Intent intent_Users = new Intent(this, Users.class);
        Intent intent_MyTasks = new Intent(this, MyTasks.class);
        switch (view.getId()) {
            case R.id.buttonChat:
                startActivity(intent_Chat);
                break;
            case R.id.buttonUsers:
                startActivity(intent_Users);
                break;
            case R.id.buttonMyTasks:
                startActivity(intent_MyTasks);
                break;
        }
    }


}
