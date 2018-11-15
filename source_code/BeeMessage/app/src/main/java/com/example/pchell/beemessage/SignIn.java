package com.example.pchell.beemessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener  {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth mAuth;
    private TextView textViewNewPassword;
    private Button buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_sign_in);

        mAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        //textViewNewPassword = (TextView) findViewById(R.id.textViewNewPassword).setOnClickListener(this);
        buttonEnter = (Button) findViewById(R.id.buttonEnter);
        textViewNewPassword = (TextView) findViewById(R.id.textViewNewPassword);
        findViewById(R.id.buttonEnter).setOnClickListener(this);
        findViewById(R.id.textViewNewPassword).setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonEnter:
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
            } break;
            case R.id.textViewNewPassword:
                if (etEmail.getText().toString().equals(""))
                {
                    Toast.makeText(SignIn.this,"Заполните поле EMAIL !!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignIn.this,"Проверьте почту !!!",Toast.LENGTH_SHORT).show();
                mAuth.sendPasswordResetEmail(etEmail.getText().toString());
                }break;
        }
    }

    public void signIn (String email_S , String password_S){
        mAuth.signInWithEmailAndPassword(email_S, password_S)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (user.isEmailVerified() == true){
                            Toast.makeText(SignIn.this,"Авторизация успешна",Toast.LENGTH_SHORT).show();
                            Intent intent_Main_Menu = new Intent(SignIn.this,MainMenu.class);
                            startActivity(intent_Main_Menu);
                            }
                                if (user.isEmailVerified() == false){
                                    Toast.makeText(SignIn.this,"Нет подтверждения",Toast.LENGTH_SHORT).show();
                                }

                        } else {
                            Toast.makeText(SignIn.this,"Авторизация провалена",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
