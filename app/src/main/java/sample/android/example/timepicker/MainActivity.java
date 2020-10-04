package sample.android.example.timepicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TimePicker timePicker;
    private Button set;
    private Button release;
    private int notificationId = 0;
    private String CHANNEL_ID ="NOTIFICATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }



        set = (Button)findViewById(R.id.set);
        set.setOnClickListener(this);
        release = (Button)findViewById(R.id.release);
        release.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {


        Intent intent = new Intent(MainActivity.this,myAlarmReceiver.class);
        intent.putExtra("notificationId",notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager AManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        timePicker = (TimePicker)findViewById(R.id.timepicker);
        //trueは24時間表記 falseは12時間表記
        timePicker.setIs24HourView(false);

        switch (v.getId()){
            case R.id.set:
                //時間を取得
                int time = timePicker.getHour();
                //分を取得
                int minutes = timePicker.getMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,time);
                startTime.set(Calendar.MINUTE,minutes);
                startTime.set(Calendar.SECOND,0);
                long alarmStartTime = startTime.getTimeInMillis();


                AManager.set(
                        AManager.RTC_WAKEUP,
                        alarmStartTime,
                        pendingIntent
                        );
                //セットを押したときにトーストで表示する処理
                Toast.makeText(MainActivity.this,"通知をセットしました",Toast.LENGTH_SHORT).show();
                notificationId++;
                break;
            case R.id.release:
                AManager.cancel(pendingIntent);
                //解除ボタンを押したときにトーストで表示する処理
                Toast.makeText(MainActivity.this,"通知をリセットしました",Toast.LENGTH_SHORT).show();
                break;
        }




    }
}
