package com.example.pchell.beemessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyTasks extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private EditText etNewTask;
    private Button bAdd;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String userKey;
    private String userTask;
    private ListView lvTasks;
    private String findValue;
    private String findValueKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_my_tasks);
        bAdd = (Button) findViewById(R.id.buttonAdd);
        etNewTask = (EditText) findViewById(R.id.editTextNewTask);
        lvTasks = (ListView) findViewById(R.id.listViewTasks);
        bAdd.setOnClickListener((View.OnClickListener) this);
        userKey = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("Tasks/" + userKey);
        tasksListRefresh();
        //-----Чтение значения поля ListView и удаление его (удержание поля в нажатом состоянии)
        lvTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String nameAtPosition = (String) parent.getItemAtPosition(position);
                Toast.makeText(MyTasks.this, "Задача удалена!", Toast.LENGTH_SHORT).show();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot findUsersDS) {
                        final List<String> findUsersList = new ArrayList<String>();
                        for (DataSnapshot battle : findUsersDS.getChildren()) {
                            findValue =  (String) battle.getValue();
                            if ( findValue == nameAtPosition)
                            {
                                //-----Поиск значения в БД  и удаление его
                                findValueKey = battle.getKey();
                                myRef = database.getReference("Tasks/" + userKey + "/" + findValueKey);
                                myRef.removeValue();
                                System.out.print("");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        String newTask = etNewTask.getText().toString();
        String userKey = user.getUid();
        add();
    }
    //-----Добавление новой задачи
    public void add(){
        userTask = etNewTask.getText().toString();
        myRef = database.getReference("Tasks/"+ userKey);
        myRef.push().setValue(userTask);
        etNewTask.setText("");
        tasksListRefresh();
        }
    public void delete(){}
    private void tasksListRefresh() {
        //-----Отображение в базе данных задач
        myRef = database.getReference("Tasks/"+ userKey);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot tasksDS) {
                final List<String> taskList = new ArrayList<String>();
                for (DataSnapshot battle : tasksDS.getChildren())
                    taskList.add((String) battle.getValue());
                ArrayAdapter<String> taskAdapter = new ArrayAdapter<>(MyTasks.this,
                        android.R.layout.simple_list_item_1,
                        taskList.toArray(new String[taskList.size()]));
                lvTasks.setAdapter(taskAdapter);
                lvTasks.deferNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}

