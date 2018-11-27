package com.example.pchell.beemessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UpdateLayout;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.in;

public class Users extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference myRef = databaseReference.child("Users");
    private ListView lvName;
    private EditText etNameFind;
    private Button bFind;
    private String userKey;
    private String findName;
    private String findKey;

    Intent intent_UserTasks1;
    Intent intent_UserTasks2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_users);
        lvName = (ListView) findViewById(R.id.listViewName);
        etNameFind = (EditText) findViewById(R.id.editTextName);
        bFind = (Button) findViewById(R.id.buttonFind);
        findViewById(R.id.buttonFind).setOnClickListener(this);
        userKey = mAuth.getCurrentUser().getUid();



        //-----Отображение в базе данных зарег. пользователей
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot usersDS) {
                final List<String> usersList = new ArrayList<String>();
                for (DataSnapshot battle : usersDS.getChildren())
                    usersList.add((String) battle.getValue());

                ArrayAdapter<String> usersAdapter = new ArrayAdapter<>(Users.this,
                        android.R.layout.simple_list_item_1,
                        usersList.toArray(new String[usersList.size()]));
                lvName.setAdapter(usersAdapter);
                lvName.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        lvName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameAtPosition = (String) parent.getItemAtPosition(position);
                UserTasks userTasks = new UserTasks();
                find(nameAtPosition);
                    intent_UserTasks1 = new Intent(Users.this,MyTasks.class);
                    intent_UserTasks2 = new Intent(Users.this,UserTasks.class);
            }
        });

    }

    @Override
    public void onClick(View v) {
     //   find();


    }

    ///////////////////////////////////////
    public void find(final String nameAtPosition) {
        //-----Выбор пользователя
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot findUsersDS) {
                final List<String> findUsersList = new ArrayList<String>();
                for (DataSnapshot battle : findUsersDS.getChildren()) {
                    findName =  (String) battle.getValue();
                    if ( findName == nameAtPosition)
                    {
                        findKey = battle.getKey();

                        if (userKey.equals(findKey))
                        {
                            startActivity(intent_UserTasks1);
                        }
                        else {
                            UserTasks userTasks = new UserTasks();
                            userTasks.tasks(findKey);
                            startActivity(intent_UserTasks2);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}

