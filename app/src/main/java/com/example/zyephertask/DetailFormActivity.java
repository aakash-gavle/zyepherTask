package com.example.zyephertask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailFormActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    TextView nameTv,ageTv,addressTv;
    String name,age,address;
    Button submitBtn,cancelBtn,finalListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_form);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Form Validation Page");

        nameTv=findViewById(R.id.nameTv);
        ageTv=findViewById(R.id.ageTv);
        addressTv=findViewById(R.id.addressTv);

        submitBtn=findViewById(R.id.submitFormBtn);
        cancelBtn=findViewById(R.id.cancelBtn);
        myDB = new DatabaseHelper(this);

        Bundle getData=getIntent().getExtras();
        name=getData.getString("name");
        nameTv.setText(name);

        age=getData.getString("age");
        ageTv.setText(age);

        address=getData.getString("address");
        addressTv.setText(address);
    }

    public void submitForm(View view) {

        String newEntry = nameTv.getText().toString();
        int age=Integer.parseInt(String.valueOf(ageTv.getText()));
        if(nameTv.length()!= 0 && age<=65&&age>=18){
            AddData(newEntry);
        }else{
            Toast.makeText(this, "Please Check your Inputs!", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelFormSubmission(View view) {
        Toast.makeText(this,"Form Submission Canceled",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    public void AddData(String newEntry) {

        boolean insertData = myDB.addData(newEntry);

        if(insertData==true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }

}