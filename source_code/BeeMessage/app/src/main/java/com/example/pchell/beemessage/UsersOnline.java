package com.example.pchell.beemessage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersOnline extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefUserOnline = database.getReference("UsersOnline");
    private DatabaseReference myRefUsers = database.getReference("Users");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ListView lvUsersOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_users_online);
        lvUsersOnline = (ListView) findViewById(R.id.listViewUserOnline);
        getUsersOnline();
    }

    private void getUsersOnline() {

        myRefUserOnline.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot usersOnlineDS) {
                final List<String> usersOnlineList = new ArrayList<String>();
                for (DataSnapshot battle : usersOnlineDS.getChildren())
                    usersOnlineList.add((String) battle.getValue());
                {
                    ArrayAdapter<String> usersOnlineAdapter = new ArrayAdapter<>(UsersOnline.this,
                            android.R.layout.simple_list_item_1,
                            usersOnlineList.toArray(new String[usersOnlineList.size()]));
                    lvUsersOnline.setAdapter(usersOnlineAdapter);
                    lvUsersOnline.deferNotifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
