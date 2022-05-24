package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.Class.ClassSellingNotification;
import com.team12.R;
import com.team12.User.ActivityHome;

public class ActivityReportSeller extends AppCompatActivity {
    Toolbar toolbar;
    EditText reportMsg;
    Button submitBtn;

    FirebaseDatabase database;
    FirebaseAuth auth;

    String userId;
    long sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_seller);
        InitializeAll();
    }

    private void InitializeAll() {

        sellerId = getIntent().getLongExtra("SellerId", 0);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.reportSellerToolbar);
        //-------show back button in toolbar--------
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        reportMsg = findViewById(R.id.reportSellerMsg);
        submitBtn = findViewById(R.id.reportSellerBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = reportMsg.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(ActivityReportSeller.this, "Write something about the seller", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference adminRef = database.getReference("Admin").child("ReportMessage").child(String.valueOf(sellerId));
                    ClassSellingNotification reportNoti = new ClassSellingNotification(msg, userId, "Report");
                    adminRef.setValue(reportNoti).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ActivityReportSeller.this, "Your complain is submitted to admin.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityReportSeller.this, ActivityHome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                reportMsg.setText("");
                            }else{
                                Toast.makeText(ActivityReportSeller.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    //---------for back to previous activity-------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}