package com.example.notesfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailedt, passwordedt, comfirmPasswordedt;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView logintv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailedt = findViewById(R.id.email_edt_tv);
        passwordedt = findViewById(R.id.password_edt_tv);
        comfirmPasswordedt = findViewById(R.id.confirm_password_edt_tv);
        createAccountBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        logintv = findViewById(R.id.login_tv);

        createAccountBtn.setOnClickListener(v -> createAccount());
        logintv.setOnClickListener(v -> finish());


    }

    void createAccount() {
        String email = emailedt.getText().toString();
        String password = passwordedt.getText().toString();
        String comfirmPassword = comfirmPasswordedt.getText().toString();

        boolean isValidated = validateData(email, password, comfirmPassword);
        if (!isValidated) {
            return;
        }

        createAccountFirebase(email, password);


        }
        void createAccountFirebase(String email, String password){

        changeInProgressBar(true);

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            changeInProgressBar(false);
                            if (task.isSuccessful()) {
                                //creating acc is done
                                Utility.showToast(CreateAccountActivity.this, "Succesfully created account,Check email to verify");
                                firebaseAuth.getCurrentUser().sendEmailVerification();
                                firebaseAuth.signOut();
                                finish();
                            }else {
                                //failure
                                Utility.showToast(CreateAccountActivity.this, task.getException().getMessage());


                            }
                        }
                    }
            );

        }

        void changeInProgressBar(boolean isProgress){
        if (isProgress){
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }

        }

        boolean validateData(String email, String password, String comfirmPassword) {
        //validate the data that are input user


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailedt.setError("Email is invalid");
                return false;
            }
            if(password.length()<6){
                passwordedt.setError("Password length is invalid");
                return false;
            }
            if (!password.equals(comfirmPassword)){
                comfirmPasswordedt.setError("Password not match");
                return false;
            }
            return true;
        }

}
