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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_Time) TextView tv_Time;
    @BindView(R.id.tv_News) TextView tv_News;
    @BindView(R.id.button) Button button;
    @BindView(R.id.listView) ListView listView;

    long sumaTeraz;

//    int d1 = 0; int h1 = 0; int m1 = 0; int s1 = 0;
//    int d2 = 0; int h2 = 0; int m2 = 0; int s2 = 0;
//    int d3 = 0; int h3 = 0; int m3 = 0; int s3 = 0;
//    int d4 = 0; int h4 = 0; int m4 = 0; int s4 = 0;
//    int d5 = 0; int h5 = 0; int m5 = 0; int s5 = 0;
//    long sumaFinish1 = s1 + m1*60 + h1*3600 + d1*(24*60);
//    long sumaFinish2 = s2 + m2*60 + h2*3600 + d2*(24*60);
//    long sumaFinish3 = s3 + m3*60 + h3*3600 + d3*(24*60);
//    long sumaFinish4 = s4 + m4*60 + h4*3600 + d4*(24*60);
//    long sumaFinish5 = s5 + m5*60 + h5*3600 + d5*(24*60);


    long sumaFinish1 = 30 + 59*60 + 16*3600 + 27*(24*60);
    long sumaFinish2 = 0 + 00*60 + 17*3600 + 27*(24*60);


    private int iStep = 1;

    private ThreadBasic threadBasic;
    private Thread1 t1;
    private Thread2 t2;
    private Thread2 t3;
    private Thread2 t4;
    private Thread2 t5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setList();

        threadBasic = new ThreadBasic(); //odlicza aktualny czas
        threadBasic.start();

        t1 = new Thread1();
        t1.start();
    }



    public class Thread1 extends Thread{
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(sumaFinish1 >= sumaTeraz) {
                                long czasomierz = sumaFinish1 - sumaTeraz;

                                int h = (int) (czasomierz / 3600); //tyle godzin
                                int m = (int) ((czasomierz- (h*3600))/60); //tyle godzin
                                int s = (int) (czasomierz- (h*3600) - (m*60)); //tyle godzin
//
                                String hourS = String.format("%02d", h);
                                String minuteS = String.format("%02d", m);
                                String secondS = String.format("%02d", s);

                                tv_Time.setText(hourS + ":" + minuteS + ":" + secondS);
                            }
                            else {
                                button.setEnabled(true);
                                t2 = new Thread2();
                                t2.start();
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public class Thread2 extends Thread{
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(sumaFinish2 >= sumaTeraz) {

                                long czasomierz = sumaFinish2 - sumaTeraz;

                                int h = (int) (czasomierz / 3600); //tyle godzin
                                int m = (int) ((czasomierz- (h*3600))/60); //tyle godzin
                                int s = (int) (czasomierz- (h*3600) - (m*60)); //tyle godzin
//
                                String hourS = String.format("%02d", h);
                                String minuteS = String.format("%02d", m);
                                String secondS = String.format("%02d", s);

                                tv_Time.setText(hourS + ":" + minuteS + ":" + secondS);
                            }
                            else {
                                button.setEnabled(true);
//                                t2 = new Thread2();
//                                t2.start();
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    }






    public void getNews(View view) {
        switch (iStep){
            case 1:
                tv_News.setText(R.string.TIP_1);
                t1.interrupt();
                break;
            case 2:
                tv_News.setText(R.string.TIP_2);
                t2.interrupt();
                break;
            case 3:
                tv_News.setText(R.string.TIP_3);
                t3.interrupt();
                break;
            case 4:
//                listView.setVisibility(View.VISIBLE);
                t4.interrupt();
                break;
            case 5:
                tv_News.setText(R.string.TIP_5);
                t5.interrupt();
                break;
        }
        button.setEnabled(false);
        iStep++;
    }

    public class ThreadBasic extends Thread{
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar now = Calendar.getInstance();
                            int day = now.get(Calendar.DAY_OF_MONTH);
                            int hour = now.get(Calendar.HOUR_OF_DAY);
                            int minute = now.get(Calendar.MINUTE);
                            int second = now.get(Calendar.SECOND);

                            sumaTeraz = second + minute*60 + hour*3600 + day*24*60;
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    }
    private void setList(){
        final List<UserModel> users = new ArrayList<>();
        users.add(new UserModel(false, "Kurtka?"));
        users.add(new UserModel(false, "Szkolny plecak a w nim..."));
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
    }
}