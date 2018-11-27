package com.example.pchell.beemessage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserTasks extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button bAdd;

    private ListView lvUserTasks;
    private String userTask;
    private static String key;
    private EditText etUserTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_user_tasks);
        lvUserTasks = (ListView) findViewById(R.id.listViewUserTasks);
        etUserTask = (EditText) findViewById(R.id.editTextNewUserTask);
        bAdd = (Button) findViewById(R.id.buttonAdd);
        bAdd.setOnClickListener((View.OnClickListener) this);
        refreshTaskList();
    }
    //-----Запись задачи пользователю
    public void add() {
        userTask = etUserTask.getText().toString();
        myRef.push().setValue(userTask);
        etUserTask.setText("");
        refreshTaskList();
    }

    @Override
    public void onClick(View v) {
        add();
    }

    public void tasks(String userKey) {
        this.key = userKey;
        return;
    }

    private void refreshTaskList() {
        myRef = database.getReference("Tasks/" + this.key);
        //-----Отображение в базе данных сообщений
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userTasksDS) {
                final List<String> userTaskList = new ArrayList<String>();
                for (DataSnapshot battle : userTasksDS.getChildren())
                    userTaskList.add((String) battle.getValue());
                ArrayAdapter<String> userTaskAdapter = new ArrayAdapter<>(UserTasks.this,
                        android.R.layout.simple_list_item_1,
                        userTaskList.toArray(new String[userTaskList.size()]));
                lvUserTasks.setAdapter(userTaskAdapter);
                lvUserTasks.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
