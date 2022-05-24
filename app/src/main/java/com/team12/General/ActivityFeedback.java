package com.team12.General;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.R;
import com.team12.User.ActivityHome;

public class ActivityFeedback extends AppCompatActivity {

    Toolbar toolbar;
    EditText msg;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar = findViewById(R.id.feedbackToolbar);
        msg = findViewById(R.id.feedbackMsg);
        btn = findViewById(R.id.feedbackBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msg.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(ActivityFeedback.this, "Give your experience about this app", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("Admin").child("Feedback").child(FirebaseAuth.getInstance().getUid());
                    adminRef.setValue(msg).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ActivityFeedback.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityFeedback.this, ActivityHome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(ActivityFeedback.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}