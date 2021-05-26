package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.preference.DialogPreference;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RingtonePreferenceX extends DialogPreference {

    RingtonePreferenceFragmentX fragment;

    String ringtoneUri;
    private String defaultValue;
    private boolean savedInstanceState;

    //String oldRingtoneUri;

    final String ringtoneType;
    private final boolean showSilent;
    private final boolean showDefault;
    private final int simCard;

    final Map<String, String> toneList = new LinkedHashMap<>();
    @SuppressWarnings("rawtypes")
    AsyncTask asyncTask = null;

    private final Context prefContext;

    private static MediaPlayer mediaPlayer = null;
    private static int oldMediaVolume = -1;
    private static boolean oldMediaMuted = false;
    private static Timer playTimer = null;
    private static boolean ringtoneIsPlayed = false;

    public RingtonePreferenceX(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PPRingtonePreference);

        ringtoneType = typedArray.getString(R.styleable.PPRingtonePreference_ringtoneType);
        showSilent = typedArray.getBoolean(R.styleable.PPRingtonePreference_showSilent, false);
        showDefault = typedArray.getBoolean(R.styleable.PPRingtonePreference_showDefault, false);
        simCard = typedArray.getInt(R.styleable.PPRingtonePreference_simCard, 0);

        // set ringtoneUri to default
        ringtoneUri = "";
        if (!showSilent && showDefault) {
            if (ringtoneType != null) {
                switch (ringtoneType) {
                    case "ringtone":
                        ringtoneUri = Settings.System.DEFAULT_RINGTONE_URI.toString();
                        break;
                    case "notification":
                        ringtoneUri = Settings.System.DEFAULT_NOTIFICATION_URI.toString();
                        break;
                    case "alarm":
                        ringtoneUri = Settings.System.DEFAULT_ALARM_ALERT_URI.toString();
                        break;
                }
            }
        }

        prefContext = context;

        typedArray.recycle();
    }

    @Override
    protected void onSetInitialValue(Object defaultValue)
    {
        // set ringtone uri from preference value
        String value = getPersistedString((String) defaultValue);
        String[] splits = value.split("\\|");
        ringtoneUri = splits[0];
        this.defaultValue = (String)defaultValue;
        setSummary("");
        setRingtone("", true);
    }

    void refreshListView() {
        /*if (PPApplication.logEnabled()) {
            PPApplication.logE("RingtonePreferenceX.refreshListView", "fragment=" + fragment);
            if (fragment != null) {
                PPApplication.logE("RingtonePreferenceX.refreshListView", "fragment.getDialog()=" + fragment.getDialog());
                if (fragment.getDialog() != null)
                    PPApplication.logE("RingtonePreferenceX.refreshListView", "fragment.getDialog().isShowing()=" + fragment.getDialog().isShowing());
            }
        }*/
        if ((fragment != null) && (fragment.getDialog() != null) && fragment.getDialog().isShowing()) {
            if (Permissions.checkRingtonePreference(prefContext)) {

                asyncTask = new AsyncTask<Void, Integer, Void>() {

                    //Ringtone defaultRingtone;
                    private final Map<String, String> _toneList = new LinkedHashMap<>();

                    @Override
                    protected Void doInBackground(Void... params) {
                        RingtoneManager manager = new RingtoneManager(prefContext);

                        Uri uri;// = null;
                        /*//noinspection ConstantConditions
                        switch (ringtoneType) {
                            case "ringtone":
                                uri = Settings.System.DEFAULT_RINGTONE_URI;
                                break;
                            case "notification":
                                uri = Settings.System.DEFAULT_NOTIFICATION_URI;
                                break;
                            case "alarm":
                                uri = Settings.System.DEFAULT_ALARM_ALERT_URI;
                                break;
                        }

                        defaultRingtone = RingtoneManager.getRingtone(prefContext, uri);*/

                        Ringtone _ringtone;
                        boolean typeIsSet = false;

                        switch (ringtoneType) {
                            case "ringtone":
                                manager.setType(RingtoneManager.TYPE_RINGTONE);
                                typeIsSet  = true;
                                if (showDefault) {
                                    uri = Settings.System.DEFAULT_RINGTONE_URI;
                                    _ringtone = RingtoneManager.getRingtone(prefContext, uri);
                                    String ringtoneName;
                                    try {
                                        ringtoneName = _ringtone.getTitle(prefContext);
                                    } catch (Exception e) {
                                        ringtoneName = prefContext.getString(R.string.ringtone_preference_default_ringtone);
                                    }
                                    _toneList.put(Settings.System.DEFAULT_RINGTONE_URI.toString(), ringtoneName);
                                }
                                break;
                            case "notification":
                                manager.setType(RingtoneManager.TYPE_NOTIFICATION);
                                typeIsSet  = true;
                                if (showDefault) {
                                    uri = Settings.System.DEFAULT_NOTIFICATION_URI;
                                    _ringtone = RingtoneManager.getRingtone(prefContext, uri);
                                    String ringtoneName;
                                    try {
                                        ringtoneName = _ringtone.getTitle(prefContext);
                                    } catch (Exception e) {
                                        ringtoneName = prefContext.getString(R.string.ringtone_preference_default_notification);
                                    }
                                    _toneList.put(Settings.System.DEFAULT_NOTIFICATION_URI.toString(), ringtoneName);
                                }
                                break;
                            case "alarm":
                                manager.setType(RingtoneManager.TYPE_ALARM);
                                typeIsSet  = true;
                                if (showDefault) {
                                    uri = Settings.System.DEFAULT_ALARM_ALERT_URI;
                                    _ringtone = RingtoneManager.getRingtone(prefContext, uri);
                                    String ringtoneName;
                                    try {
                                        ringtoneName = _ringtone.getTitle(prefContext);
                                    } catch (Exception e) {
                                        ringtoneName = prefContext.getString(R.string.ringtone_preference_default_alarm);
                                    }
                                    _toneList.put(Settings.System.DEFAULT_ALARM_ALERT_URI.toString(), ringtoneName);
                                }
                                break;
                        }

                        if (showSilent) {
                            _toneList.put("", prefContext.getString(R.string.ringtone_preference_none));
                        }

                        if (typeIsSet) {
                            try {
                                Cursor cursor = manager.getCursor();

                                /*
                                profile._soundRingtone=content://settings/system/ringtone
                                profile._soundNotification=content://settings/system/notification_sound
                                profile._soundAlarm=content://settings/system/alarm_alert
                                */

                                while (cursor.moveToNext()) {
                                    String _uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
                                    String _title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                                    String _id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
                                    //Log.e("RingtonePreferenceX.refreshListView", "_uri="+_uri);
                                    //Log.e("RingtonePreferenceX.refreshListView", "_title="+_title);
                                    //Log.e("RingtonePreferenceX.refreshListView", "_id="+_id);
                                    //Log.e("RingtonePreferenceX.refreshListView", "manager.getRingtoneUri()="+manager.getRingtoneUri(cursor.getPosition()));

                                    // for Samsung do not allow external tones
                                    boolean add = true;
                                    if (PPApplication.deviceIsSamsung) {
                                        if (ringtoneType.equals("ringtone") && (simCard != 0) && (!_uri.contains("content://media/internal")))
                                            add = false;
                                        if (ringtoneType.equals("notification") && (simCard != 0) && (!_uri.contains("content://media/internal")))
                                            add = false;
                                    }

                                    if (add)
                                        _toneList.put(_uri + "/" + _id, _title);
                                }
                            } catch (Exception e) {
                                PPApplication.recordException(e);
                            }
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);

                        toneList.clear();
                        toneList.putAll(_toneList);

                        /*if (defaultRingtone == null) {
                            // ringtone not found
                            //View positive = getButton(DialogInterface.BUTTON_POSITIVE);
                            //positive.setEnabled(false);
                            setPositiveButtonText(null);
                        }*/

                        if (fragment != null)
                            fragment.updateListView(true);
                    }

                }.execute();
            }
        }
    }

    void setRingtone(String newRingtoneUri, boolean onlySetName)
    {
        if (!onlySetName)
            ringtoneUri = newRingtoneUri;

        new AsyncTask<Void, Integer, Void>() {

            private String ringtoneName;

            @Override
            protected Void doInBackground(Void... params) {
                if ((ringtoneUri == null) || ringtoneUri.isEmpty())
                    ringtoneName = prefContext.getString(R.string.ringtone_preference_none);
                else {
                    Uri uri = Uri.parse(ringtoneUri);
                    Ringtone ringtone = RingtoneManager.getRingtone(prefContext, uri);
                    try {
                        ringtoneName = ringtone.getTitle(prefContext);
                    } catch (Exception e) {
                        ringtoneName = prefContext.getString(R.string.ringtone_preference_not_set);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                setSummary(ringtoneName);
            }

        }.execute();

        if (!onlySetName) {
            //View positive =
            //        getButton(DialogInterface.BUTTON_POSITIVE);
            //positive.setEnabled(true);
            setPositiveButtonText(android.R.string.ok);

            if (fragment != null)
                fragment.updateListView(false);
        }
    }

    void stopPlayRingtone() {
        final AudioManager audioManager = (AudioManager)prefContext.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            if (playTimer != null) {
                playTimer.cancel();
                playTimer = null;
            }
            if ((mediaPlayer != null) && ringtoneIsPlayed) {
                try {
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                } catch (Exception e) {
                    //PPApplication.recordException(e);
                }
                try {
                    mediaPlayer.release();
                } catch (Exception e) {
                    //PPApplication.recordException(e);
                }
                ringtoneIsPlayed = false;
                mediaPlayer = null;

                if (oldMediaVolume > -1)
                    ActivateProfileHelper.setMediaVolume(prefContext, audioManager, oldMediaVolume);
                if (oldMediaMuted)
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
        }
    }

    void playRingtone() {
        if ((ringtoneUri == null) || ringtoneUri.isEmpty())
            return;

        final AudioManager audioManager = (AudioManager)prefContext.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {

            final Uri _ringtoneUri = Uri.parse(ringtoneUri);

            PPApplication.startHandlerThreadPlayTone();
            final Handler handler = new Handler(PPApplication.handlerThreadPlayTone.getLooper());
            handler.post(() -> {
                try {
//                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPlayTone", "START run - from=RingtonePreferenceX.playRingtone");
                    stopPlayRingtone();

                    Context appContext = prefContext.getApplicationContext();

                    /*if (TonesHandler.isPhoneProfilesSilent(_ringtoneUri, appContext)) {
                        //String filename = appContext.getResources().getResourceEntryName(TonesHandler.TONE_ID) + ".ogg";
                        //File soundFile = new File(appContext.getFilesDir(), filename);
                        // /data/user/0/sk.henrichg.phoneprofilesplus/files
                        //PPApplication.logE("RingtonePreferenceX.playRingtone", "soundFile=" + soundFile);
                        //mediaPlayer.setDataSource(soundFile.getAbsolutePath());
                        Log.e("RingtonePreferenceX.playRingtone", "phoneprofiles_silent.ogg");
                        return;
                    }
                    else*/ {
                        if (mediaPlayer == null)
                            mediaPlayer = new MediaPlayer();

                        mediaPlayer.setDataSource(appContext, _ringtoneUri);
                    }

                    RingerModeChangeReceiver.internalChange = true;

                    AudioAttributes attrs = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build();
                    mediaPlayer.setAudioAttributes(attrs);
                    //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mediaPlayer.prepare();
                    mediaPlayer.setLooping(false);

                    oldMediaMuted = audioManager.isStreamMute(AudioManager.STREAM_MUSIC);
                    if (!oldMediaMuted)
                        oldMediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    else
                        oldMediaVolume = -1;

                    int ringtoneVolume = 0;
                    int maximumRingtoneValue = 0;

                    switch (ringtoneType) {
                        case "ringtone":
                            maximumRingtoneValue = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                            if (!oldMediaMuted)
                                ringtoneVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                            else
                                ringtoneVolume = Math.round(maximumRingtoneValue * 0.75f);
                            break;
                        case "notification":
                            maximumRingtoneValue = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
                            if (!oldMediaMuted)
                                ringtoneVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
                            else
                                ringtoneVolume = Math.round(maximumRingtoneValue * 0.75f);
                            break;
                        case "alarm":
                            maximumRingtoneValue = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                            if (!oldMediaMuted)
                                ringtoneVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
                            else
                                ringtoneVolume = Math.round(maximumRingtoneValue * 0.75f);
                            break;
                    }

                    //PPApplication.logE("RingtonePreferenceX.playRingtone", "ringtoneVolume=" + ringtoneVolume);

                    int maximumMediaValue = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

                    float percentage = (float) ringtoneVolume / maximumRingtoneValue * 100.0f;
                    int mediaVolume = Math.round(maximumMediaValue / 100.0f * percentage);

                    //PPApplication.logE("RingtonePreferenceX.playRingtone", "mediaVolume=" + mediaVolume);

                    if (oldMediaMuted)
                        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    ActivateProfileHelper.setMediaVolume(prefContext, audioManager, mediaVolume);

                    mediaPlayer.start();
                    ringtoneIsPlayed = true;

                    playTimer = new Timer();
                    playTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null) {
                                try {
                                    if (mediaPlayer.isPlaying())
                                        mediaPlayer.stop();
                                } catch (Exception e) {
                                    //PPApplication.recordException(e);
                                }
                                try {
                                    mediaPlayer.release();
                                } catch (Exception e) {
                                    //PPApplication.recordException(e);
                                }

                                if (oldMediaVolume > -1)
                                    ActivateProfileHelper.setMediaVolume(prefContext, audioManager, oldMediaVolume);
                                if (oldMediaMuted)
                                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                                //PPApplication.logE("RingtonePreferenceX.playRingtone", "play stopped");
                            }

                            ringtoneIsPlayed = false;
                            mediaPlayer = null;

                            DisableInternalChangeWorker.enqueueWork();

                            /*PPApplication.startHandlerThreadInternalChangeToFalse();
                            final Handler handler = new Handler(PPApplication.handlerThreadInternalChangeToFalse.getLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    RingerModeChangeReceiver.internalChange = false;
                                }
                            }, 3000);*/
                            //PostDelayedBroadcastReceiver.setAlarm(
                            //        PostDelayedBroadcastReceiver.ACTION_RINGER_MODE_INTERNAL_CHANGE_TO_FALSE, 3, prefContext);

                            playTimer = null;
                        }
                    }, mediaPlayer.getDuration());

                } catch (Exception e) {
                    //Log.e("RingtonePreferenceX.playRingtone", Log.getStackTraceString(e));
                    //PPApplication.recordException(e);
                    stopPlayRingtone();

                    DisableInternalChangeWorker.enqueueWork();

                    /*PPApplication.startHandlerThreadInternalChangeToFalse();
                    final Handler handler = new Handler(PPApplication.handlerThreadInternalChangeToFalse.getLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RingerModeChangeReceiver.internalChange = false;
                        }
                    }, 3000);*/
                    //PostDelayedBroadcastReceiver.setAlarm(
                    //        PostDelayedBroadcastReceiver.ACTION_RINGER_MODE_INTERNAL_CHANGE_TO_FALSE, 3, prefContext);
                }
            });

        }
    }

    void persistValue() {
        if (shouldPersist())
        {
            if (fragment != null) {
                final int position = fragment.getRingtonePosition();
                if (position != -1) {
                    // save to preferences
                    persistString(ringtoneUri);

                    // and notify
                    notifyChanged();
                }
            }
        }
    }

    void resetSummary() {
        if (!savedInstanceState) {
            String value = getPersistedString(defaultValue);
            String[] splits = value.split("\\|");
            ringtoneUri = splits[0];
            setSummary("");
            setRingtone("", true);
        }
        savedInstanceState = false;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        savedInstanceState = true;

        PPApplication.startHandlerThreadPlayTone();
        final Handler handler = new Handler(PPApplication.handlerThreadPlayTone.getLooper());
        handler.post(() -> {
//                PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPlayTone", "START run - from=RingtonePreferenceX.onSaveInstanceState");
            //noinspection Convert2MethodRef
            stopPlayRingtone();
        });

        final Parcelable superState = super.onSaveInstanceState();

        final SavedState myState = new SavedState(superState);
        myState.ringtoneUri = ringtoneUri;
        myState.defaultValue = defaultValue;
        //myState.oldRingtoneUri = oldRingtoneUri;

        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        PPApplication.startHandlerThreadPlayTone();
        final Handler handler = new Handler(PPApplication.handlerThreadPlayTone.getLooper());
        handler.post(() -> {
//                PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPlayTone", "START run - from=RingtonePreferenceX.onRestoreInstanceState");
            //noinspection Convert2MethodRef
            stopPlayRingtone();
        });

        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            setRingtone("", true);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        ringtoneUri = myState.ringtoneUri;
        defaultValue = myState.defaultValue;
        //oldRingtoneUri = myState.oldRingtoneUri;

        //PPApplication.logE("RingtonePreferenceX.onRestoreInstanceState", "ringtoneUri="+ringtoneUri);

        setRingtone("", true);
    }

    // From DialogPreference
    private static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR =
                new Creator<RingtonePreferenceX.SavedState>() {
                    public RingtonePreferenceX.SavedState createFromParcel(Parcel in) {
                        return new RingtonePreferenceX.SavedState(in);
                    }

                    public RingtonePreferenceX.SavedState[] newArray(int size) {
                        return new RingtonePreferenceX.SavedState[size];
                    }
                };

        String ringtoneUri;
        String defaultValue;

        //String oldRingtoneUri;

        @SuppressLint("ParcelClassLoader")
        SavedState(Parcel source) {
            super(source);
            ringtoneUri = source.readString();
            defaultValue = source.readString();
            //oldRingtoneUri = source.readString();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(ringtoneUri);
            dest.writeString(defaultValue);
            //dest.writeString(oldRingtoneUri);
        }
    }

}
