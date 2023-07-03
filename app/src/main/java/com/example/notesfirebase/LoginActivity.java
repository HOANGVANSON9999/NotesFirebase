package com.example.notesfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailedt, passwordedt;
    Button loginBtn;
    ProgressBar progressBar;
    TextView createAccountv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailedt = findViewById(R.id.email_edt_tv);
        passwordedt = findViewById(R.id.password_edt_tv);
        loginBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);
        createAccountv = findViewById(R.id.create_account_btn);


        loginBtn.setOnClickListener(v -> loginUser());
        createAccountv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));

    }
    void loginUser(){
            String email = emailedt.getText().toString();
            String password = passwordedt.getText().toString();


            boolean isValidated = validateData(email, password);
            if (!isValidated) {
                return;
            }

            loginAccountFirebase(email, password);

    }


    void loginAccountFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgressBar(false);
                if (task.isSuccessful()){
                    //login is successful
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to mainactivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    }else {
                        Utility.showToast(LoginActivity.this, "Email not verified, Please verify your email");

                    }
                }else {
                    //login failed
                    Utility.showToast(LoginActivity.this, task.getException().getMessage());
                }


            }
        });

    }

    void changeInProgressBar(boolean isProgress){
        if (isProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }

    }

    boolean validateData(String email, String password) {
        //validate the data that are input user


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedt.setError("Email is invalid");
            return false;
        }
        if(password.length()<6){
            passwordedt.setError("Password length is invalid");
            return false;
        }
        return true;
    }
}