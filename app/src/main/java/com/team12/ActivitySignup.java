package com.team12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class ActivitySignup extends AppCompatActivity {
    String EMAIL_VALIDITY_CHECK = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextInputEditText signUpName, signUpEmail, signUpPassword, signUpConfirmPassword;
    TextInputLayout signUpNameLay, signUpEmailLay, signUpPasswordLay, signUpConfirmPasswordLay;
    Button signUpBtn;
    TextView signIn;

    FirebaseDatabase database;
    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        InitializeAll();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySignup.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name, email, password, confirmPassword;
                name = signUpName.getText().toString();
                email = signUpEmail.getText().toString();
                password = signUpPassword.getText().toString();
                confirmPassword = signUpConfirmPassword.getText().toString();

                if(name.isEmpty()){
                    progressDialog.dismiss();
                    signUpNameLay.setError("Enter your name");
                    signUpEmailLay.setErrorEnabled(false);
                    signUpPasswordLay.setErrorEnabled(false);
                    signUpConfirmPasswordLay.setErrorEnabled(false);
                }else if(email.isEmpty()){
                    progressDialog.dismiss();
                    signUpNameLay.setErrorEnabled(false);
                    signUpEmailLay.setError("Email is empty");
                    signUpPasswordLay.setErrorEnabled(false);
                    signUpConfirmPasswordLay.setErrorEnabled(false);
                }else if(!email.matches(EMAIL_VALIDITY_CHECK)){
                    progressDialog.dismiss();
                    signUpNameLay.setErrorEnabled(false);
                    signUpEmailLay.setError("Invalid email");
                    signUpPasswordLay.setErrorEnabled(false);
                    signUpConfirmPasswordLay.setErrorEnabled(false);
                }else if(password.isEmpty()){
                    progressDialog.dismiss();
                    signUpNameLay.setErrorEnabled(false);
                    signUpEmailLay.setErrorEnabled(false);
                    signUpPasswordLay.setError("Password is empty");
                    signUpConfirmPasswordLay.setErrorEnabled(false);
                }else if(password.length() < 6){
                    progressDialog.dismiss();
                    signUpNameLay.setErrorEnabled(false);
                    signUpEmailLay.setErrorEnabled(false);
                    signUpPasswordLay.setError("Password must contain more than 6 characters");
                    signUpConfirmPasswordLay.setErrorEnabled(false);
                }else if(!confirmPassword.matches(password)){
                    progressDialog.dismiss();
                    signUpNameLay.setErrorEnabled(false);
                    signUpEmailLay.setErrorEnabled(false);
                    signUpPasswordLay.setErrorEnabled(false);
                    signUpConfirmPasswordLay.setError("Confirm password not matches with password");
                }else{
                    signUpNameLay.setErrorEnabled(false);
                    signUpEmailLay.setErrorEnabled(false);
                    signUpPasswordLay.setErrorEnabled(false);
                    signUpConfirmPasswordLay.setErrorEnabled(false);

                    auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                        String userId = auth.getUid();
                        assert userId != null;
                        DatabaseReference profileRef = database.getReference("User").child(userId).child("Profile");
                        ClassUserProfile newUser = new ClassUserProfile(userId, name, null, null, email, null, 0);
                        profileRef.setValue(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        //TODO----------put the value in sharedpreferences-------
                                        Intent intent = new Intent(ActivitySignup.this, ActivityHome.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                        signUpName.setText("");
                                        signUpEmail.setText("");
                                        signUpPassword.setText("");
                                        signUpConfirmPassword.setText("");
                                    }
                                });
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ActivitySignup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            progressDialog.dismiss();
                            Toast.makeText(ActivitySignup.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });
    }

    private void InitializeAll() {
        //----------Firebase-------------
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        //----------TextInputEditText------------
        signUpName = findViewById(R.id.signUpName);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpConfirmPassword = findViewById(R.id.signUpConfirmPassword);

        //---------TextInputLayout----------
        signUpNameLay = findViewById(R.id.signUpNameLayout);
        signUpEmailLay = findViewById(R.id.signUpEmailLayout);
        signUpPasswordLay = findViewById(R.id.signUpPasswordLayout);
        signUpConfirmPasswordLay = findViewById(R.id.signUpConfirmPasswordLayout);

        //------------TextView and Button----------
        signUpBtn = findViewById(R.id.signUpBtn);
        signIn = findViewById(R.id.signUpSignInBtn);

        progressDialog = new ProgressDialog(ActivitySignup.this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("We are working on your account");
        progressDialog.setCancelable(false);



    }
}