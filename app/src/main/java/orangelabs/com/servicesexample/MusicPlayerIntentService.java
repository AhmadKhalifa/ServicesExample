package orangelabs.com.servicesexample;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;


public class MusicPlayerIntentService extends IntentService {
    public static final String INTENT_FILTER_TRACK_LOAD_COMPLETED =
            "orangelabs.com.serviceexample.filter.INTENT_FILTER_TRACK_LOAD_COMPLETED";

    private static final String ACTION_PLAY_TRACK =
            "orangelabs.com.serviceexample.action.ACTION_PLAY_MUSIC_TRACK";

    public static final String EXTRA_MUSIC_TRACK_URL =
            "orangelabs.com.serviceexample.extra.EXTRA_MUSIC_TRACK_URL";

    private String mTrackUrl;

    public MusicPlayerIntentService() {
        super("MusicPlayerIntentService");
    }

    public static void playMusicTrack(Context context, String trackUrl) {
        Intent intent = new Intent(context, MusicPlayerIntentService.class);
        intent.setAction(ACTION_PLAY_TRACK);
        intent.putExtra(EXTRA_MUSIC_TRACK_URL, trackUrl);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY_TRACK.equals(action)) {
                mTrackUrl = intent.getStringExtra(EXTRA_MUSIC_TRACK_URL);
                playMusicTrack();
            }
        }
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
    }

    private void notifyTrackLoadCompleted() {
        Intent intent = new Intent(INTENT_FILTER_TRACK_LOAD_COMPLETED);
        intent.putExtra(EXTRA_MUSIC_TRACK_URL, mTrackUrl);
        sendBroadcast(intent);
    }
}
