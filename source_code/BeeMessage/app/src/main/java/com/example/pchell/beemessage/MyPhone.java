package com.example.pchell.beemessage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPhone extends AppCompatActivity implements View.OnClickListener {
    private Button bSignIn;
    private Button bCreateAcc;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui__my_phone);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MyPhone.this, MainMenu.class);
            startActivity(intent);
        }

        bSignIn = (Button) findViewById(R.id.buttonSignIn);
        bSignIn.setOnClickListener((View.OnClickListener) this);
        bCreateAcc = (Button) findViewById(R.id.buttonCreateAccount);
        bCreateAcc.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public void onClick(View view) {
        Intent intent_CreateAcc = new Intent(this, CreateAccount.class);
        Intent intent_SignIn = new Intent(this, SignIn.class);
        switch (view.getId()) {
            case R.id.buttonCreateAccount:
                startActivity(intent_CreateAcc);
                break;
            case R.id.buttonSignIn:
                startActivity(intent_SignIn);
                break;
        }
    }
}

