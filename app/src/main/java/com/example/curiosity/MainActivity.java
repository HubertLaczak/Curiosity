package com.example.curiosity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv_Time;
    TextView tv_News;
    Button button;

    private int i = 1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Time = findViewById(R.id.tv_Time);
        tv_News = findViewById(R.id.tv_News);
        button = findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);


        final List<UserModel> users = new ArrayList<>();
        users.add(new UserModel(false, "Szkolny plecak"));
        users.add(new UserModel(false, "Mark"));
        users.add(new UserModel(false, "Singh"));
        users.add(new UserModel(false, "The Mobile Era"));

        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserModel model = users.get(i); //changed it to model because viewers will confused about it
                if(model.isSelected)
                    model.setSelected(false);
                else
                    model.setSelected(true);
                users.set(i, model);
                adapter.updateRecords(users);
            }
        });


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar now = Calendar.getInstance();
                                int year = now.get(Calendar.YEAR);
                                int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
                                int day = now.get(Calendar.DAY_OF_MONTH);
                                int hour = now.get(Calendar.HOUR_OF_DAY);
                                int minute = now.get(Calendar.MINUTE);
                                int second = now.get(Calendar.SECOND);
                                int millis = now.get(Calendar.MILLISECOND);


                                tv_Time.setText("Godzina " + String.valueOf(hour) + ":"+ String.valueOf(minute)+ ":" + String.valueOf(second));

                                if(hour > 12){
                                    button.setEnabled(true);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }



    public void getNews(View view) {
        button.setEnabled(false);
        switch (i){
            case 1:
                tv_News.setText(R.string.TIP_1);
                break;
            case 2:
                tv_News.setText(R.string.TIP_2);
                break;
            case 3:
                tv_News.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                break;
            case 4:
                listView.setVisibility(View.GONE);
                tv_News.setVisibility(View.VISIBLE);
                tv_News.setText("Cztery");
                break;
        }
        i++;
    }
}