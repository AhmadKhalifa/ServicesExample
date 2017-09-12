package orangelabs.com.servicesexample;


import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String trackUrl = extras.getString(MusicPlayerIntentService.EXTRA_MUSIC_TRACK_URL);
                Toast.makeText(MainActivity.this, trackUrl + " Loaded", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MusicPlayerService.playMusicTrack(
                this,
                "http://e-cdn-preview-c.deezer.com/stream/cc2b69842693b81001e5fc9216879032-3.mp3"
        );

//        MusicPlayerIntentService.playMusicTrack(
//                this,
//                "http://e-cdn-preview-c.deezer.com/stream/cc2b69842693b81001e5fc9216879032-3.mp3"
//        );

//        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        JobInfo musicPlayerJob = new JobInfo.Builder(
//                1,
//                new ComponentName(getPackageName(), MusicPlayerJobService.class.getName()))
//                .setPeriodic(30 * 1000)
//                .setRequiresCharging(true)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setRequiresDeviceIdle(false)
//                .build();
//        if (mJobScheduler.schedule(musicPlayerJob) <= 0) {  // Exception raised
//            Toast.makeText(this, "Something wrong has happened", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(
                mReceiver,
                new IntentFilter(MusicPlayerIntentService.INTENT_FILTER_TRACK_LOAD_COMPLETED)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
