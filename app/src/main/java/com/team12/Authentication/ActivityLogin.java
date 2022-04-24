package com.team12.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.User.ActivityHome;
import com.team12.Admin.AdminHome;
import com.team12.R;

public class ActivityLogin extends AppCompatActivity {

    String EMAIL_VALIDITY_CHECK = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextView signUp, forgotPassword;
    TextInputEditText signInEmail, signInPassword;
    TextInputLayout signInEmailLay, signInPasswordLay;
    Button signInBtn;
    CheckBox rememberMe;

    FirebaseAuth auth;
    FirebaseDatabase database;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeAll();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignup.class);
                startActivity(intent);
                finish();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String email, password;
                email = signInEmail.getText().toString();
                password = signInPassword.getText().toString();

                if(email.equals("Admin") && password.equals("123456")){
                    Intent intent = new Intent(ActivityLogin.this, AdminHome.class);
                    startActivity(intent);
                    finish();
                    signInEmail.setText("");
                    signInPassword.setText("");
                }

                else if(email.isEmpty()){
                    progressDialog.dismiss();
                    signInEmailLay.setError("Email is empty");
                    signInPasswordLay.setErrorEnabled(false);
                }else if(!email.matches(EMAIL_VALIDITY_CHECK)){
                    progressDialog.dismiss();
                    signInEmailLay.setError("Invalid email");
                    signInPasswordLay.setErrorEnabled(false);
                }else if(password.isEmpty()){
                    progressDialog.dismiss();
                    signInEmailLay.setErrorEnabled(false);
                    signInPasswordLay.setError("Password is empty");
                }else{
                    signInEmailLay.setErrorEnabled(false);
                    signInPasswordLay.setErrorEnabled(false);

                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(rememberMe.isChecked()) {
                                //TODO----------put the value in sharedPreferences-------
                                Toast.makeText(ActivityLogin.this, "Check box clicked", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            signInEmail.setText("");
                            signInPassword.setText("");
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityLogin.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void InitializeAll() {

        //----------Firebase------------
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        //----------TextInputEditText------------
        signInEmail = findViewById(R.id.signInEmail);
        signInPassword = findViewById(R.id.signInPassword);

        //---------TextInputLayout----------
        signInEmailLay = findViewById(R.id.signInEmailLayout);
        signInPasswordLay = findViewById(R.id.signInPasswordLayout);

        //------------TextView and Button----------
        rememberMe = findViewById(R.id.signInRememberMe);
        signInBtn = findViewById(R.id.signInBtn);
        signUp = findViewById(R.id.signInSignUp);
        forgotPassword = findViewById(R.id.signInForgetPassword);

        progressDialog = new ProgressDialog(ActivityLogin.this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Connecting to server");
        progressDialog.setCancelable(false);
    }
}