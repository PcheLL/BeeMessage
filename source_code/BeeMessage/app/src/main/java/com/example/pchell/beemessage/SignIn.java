package com.example.pchell.beemessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener  {
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_sign_in);

        mAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        findViewById(R.id.buttonEnter).setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if (etEmail.getText().toString().equals(""))
        {
            Toast.makeText(SignIn.this,"Введите Email !!!",Toast.LENGTH_SHORT).show();
        }
        else if (etPassword.getText().toString().equals(""))
        {
            Toast.makeText(SignIn.this,"Введите Password !!!",Toast.LENGTH_SHORT).show();
        }

        else if(view.getId() == R.id.buttonEnter){
            signIn(etEmail.getText().toString(),etPassword.getText().toString());
        }
    }

    public void signIn (String email_S , String password_S){
        mAuth.signInWithEmailAndPassword(email_S, password_S)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this,"Авторизация успешна",Toast.LENGTH_SHORT).show();
                            Intent intent_Main_Menu = new Intent(SignIn.this,MainMenu.class);
                            startActivity(intent_Main_Menu);
                        } else {
                            Toast.makeText(SignIn.this,"Авторизация провалена",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
