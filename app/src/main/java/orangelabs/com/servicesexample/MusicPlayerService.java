package orangelabs.com.servicesexample;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static orangelabs.com.servicesexample.MusicPlayerService.Constants.ACTION.STARTFOREGROUND_ACTION;

public class MusicPlayerService extends Service {

    public static final String INTENT_FILTER_TRACK_LOAD_COMPLETED =
            "orangelabs.com.serviceexample.filter.INTENT_FILTER_TRACK_LOAD_COMPLETED";

    private static final String ACTION_PLAY_TRACK =
            "orangelabs.com.serviceexample.action.ACTION_PLAY_MUSIC_TRACK";

    public static final String EXTRA_MUSIC_TRACK_URL =
            "orangelabs.com.serviceexample.extra.EXTRA_MUSIC_TRACK_URL";

    private String mTrackUrl;

    public static void playMusicTrack(Context context, String trackUrl) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(STARTFOREGROUND_ACTION);
        intent.putExtra(EXTRA_MUSIC_TRACK_URL, trackUrl);
        context.startService(intent);
    }

    public static class Constants {
        public interface ACTION {
            public static String MAIN_ACTION = "com.truiton.foregroundservice.action.main";
            public static String PREV_ACTION = "com.truiton.foregroundservice.action.prev";
            public static String PLAY_ACTION = "com.truiton.foregroundservice.action.play";
            public static String NEXT_ACTION = "com.truiton.foregroundservice.action.next";
            public static String STARTFOREGROUND_ACTION = "com.truiton.foregroundservice.action.startforeground";
            public static String STOPFOREGROUND_ACTION = "com.truiton.foregroundservice.action.stopforeground";
        }

        public interface NOTIFICATION_ID {
            public static int FOREGROUND_SERVICE = 101;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (intent.getAction().equals(STARTFOREGROUND_ACTION)) {

            mTrackUrl = intent.getStringExtra(EXTRA_MUSIC_TRACK_URL);
            playMusicTrack();
            Log.i("TAG", "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, MusicPlayerService.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, MusicPlayerService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, MusicPlayerService.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Truiton Music Player")
                    .setTicker("Truiton Music Player")
                    .setContentText("My Music")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_previous,
                            "Previous", ppreviousIntent)
                    .addAction(android.R.drawable.ic_media_play, "Play",
                            pplayIntent)
                    .addAction(android.R.drawable.ic_media_next, "Next",
                            pnextIntent).build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i("TAG", "Clicked Previous");
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i("TAG", "Clicked Play");
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i("TAG", "Clicked Next");
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i("TAG", "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;


//        mTrackUrl = intent.getStringExtra(EXTRA_MUSIC_TRACK_URL);
//        playMusicTrack();
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification notification = new Notification.Builder(this)
//                .setContentTitle("Playing music")
//                .setContentText("Songbird")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(false)
//                .setOngoing(true)
//                .build();
//        startForeground(123, notification);


//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setAction(ACTION_PLAY_TRACK);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        Intent previousIntent = new Intent(this, MusicPlayerService.class);
//        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
//                previousIntent, 0);
//
//        Intent playIntent = new Intent(this, MusicPlayerService.class);
//        playIntent.setAction(ACTION_PLAY_TRACK);
//        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
//                playIntent, 0);
//
//        Intent nextIntent = new Intent(this, MusicPlayerService.class);
//        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
//                nextIntent, 0);
//
//        Bitmap icon = BitmapFactory.decodeResource(getResources(),
//                R.mipmap.ic_launcher);
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle("Truiton Music Player")
//                .setTicker("Truiton Music Player")
//                .setContentText("My Music")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(
//                        Bitmap.createScaledBitmap(icon, 128, 128, false))
//                .setContentIntent(pendingIntent)
//                .setOngoing(true)
//                .addAction(android.R.drawable.ic_media_previous,
//                        "Previous", ppreviousIntent)
//                .addAction(android.R.drawable.ic_media_play, "Play",
//                        pplayIntent)
//                .addAction(android.R.drawable.ic_media_next, "Next",
//                        pnextIntent).build();
//        startForeground(9556,
//                notification);
//        stopForeground(true);
//        return Service.START_REDELIVER_INTENT;
    }

    private void playMusicTrack() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(mTrackUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
            notifyTrackLoadCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }

        stopSelf();
    }

    private void notifyTrackLoadCompleted() {
        Intent intent = new Intent(INTENT_FILTER_TRACK_LOAD_COMPLETED);
        intent.putExtra(EXTRA_MUSIC_TRACK_URL, mTrackUrl);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
