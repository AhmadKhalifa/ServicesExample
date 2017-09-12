package orangelabs.com.servicesexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

/**
 * Created by khalifa on 11/09/17.
 */

public class MusicPlayerJobService extends JobService {

    public static final int JOB_ID = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            playMusicTrack();
            jobFinished((JobParameters) msg.obj, false);
            return true;
        }
    });

    private void playMusicTrack() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource("http://e-cdn-preview-c.deezer.com/stream/cc2b69842693b81001e5fc9216879032-3.mp3");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        mHandler.sendMessage(Message.obtain(mHandler, JOB_ID, params));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mHandler.removeMessages(JOB_ID);
        return false;
    }
}
