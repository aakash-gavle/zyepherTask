package com.example.zyephertask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> nameList=new ArrayList<>();
    ListView listView;
    String name,age,address;
    Button newFormBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView=findViewById(R.id.listView);
        newFormBtn=findViewById(R.id.newFormBtn);

        get_json();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this,DetailFormActivity.class);

                String json;

                try {
                    InputStream inputStream=getAssets().open("task.json");
                    int size= inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();

                    json=new String(buffer,"UTF-8");
                    try {
                        JSONArray jsonArray= new JSONArray(json);

                        for (int i =0;i<=position;i++)
                        {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);

                            name=jsonObject.getString("name");
                            age=jsonObject.getString("age");
                            address=jsonObject.getString("address");

                            nameList.add(name);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                intent.putExtra("name",name);
                intent.putExtra("age",age);
                intent.putExtra("address",address);

                startActivity(intent);

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameList);
        listView.setAdapter(arrayAdapter);

    }

    public void get_json(){
        String json;

        try {
            InputStream inputStream=getAssets().open("task.json");
            int size= inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json=new String(buffer,"UTF-8");
            try {
                JSONArray jsonArray= new JSONArray(json);

                for (int i =0;i<=jsonArray.length();i++)
                {
                    JSONObject jsonObject= jsonArray.getJSONObject(i);

                    name=jsonObject.getString("name");
                    age=jsonObject.getString("age");
                    address=jsonObject.getString("address");
                    int key= Integer.parseInt(jsonObject.getString("key"));

                    nameList.add(name);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFinalList(View view) {
        Intent intent=new Intent(this,FinalListActivity.class);
        startActivity(intent);
    }

    public void showMemberList(View view) {
        newFormBtn.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }
}