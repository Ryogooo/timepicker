package sample.android.example.timepicker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class myAlarmReceiver extends BroadcastReceiver {

    private String CHANNEL_ID ="NOTIFICATION_ID";
    private int NOTIFICATION_ID = 0;


    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        //アイコンの設定(これは必須)
        builder.setSmallIcon(R.drawable.ic_launcher);
        //通知のタイトル
        builder.setContentTitle("タイトル");
        //通知の内容
        builder.setContentText("テキスト");
        //通知の優先度
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int notificationId = intent.getIntExtra("notificationId",0);
        Intent bootIntent = new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,bootIntent,0);

        //通知を表示する
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());



    }
}
