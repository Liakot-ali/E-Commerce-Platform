package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.team12.R;

public class ActivityMySelling extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText,Emptytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_selling);
        InitializeAll();

    }
    private void  InitializeAll(){

        //-------show back button in toolbar--------
        toolbar = findViewById(R.id.mySellingToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbarText = findViewById(R.id.mySellingToolbarText);
        Emptytext = findViewById(R.id.MySellingEmptyTextView);

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