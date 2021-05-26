package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PPNotificationListenerService extends NotificationListenerService {

    private static final String ACTION_REQUEST_INTERRUPTION_FILTER = PPApplication.PACKAGE_NAME + ".PPNotificationListenerService.ACTION_REQUEST_INTERRUPTION_FILTER";
    private static final String EXTRA_FILTER = "filter";

    //private static final String TAG = PPNotificationListenerService.class.getSimpleName();

    private static volatile PPNotificationListenerService instance;
    private static volatile boolean connected;

    private NLServiceReceiver nlservicereceiver;

    //private static List<PostedNotificationData> notifications = null;


    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (PPApplication.ppNotificationListenerService) {
            instance = this;
            connected = false;
        }

        nlservicereceiver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PPNotificationListenerService.ACTION_REQUEST_INTERRUPTION_FILTER);
        registerReceiver(nlservicereceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(nlservicereceiver);
        } catch (Exception ignored) {}

        synchronized (PPApplication.ppNotificationListenerService) {
            instance = null;
            connected = false;
        }
    }

    static PPNotificationListenerService getInstance() {
        //synchronized (PPApplication.ppNotificationListenerService) {
        return instance;
        //}
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        //CallsCounter.logCounter(getApplicationContext(), "PPNotificationListenerService.onNotificationPosted", "PPNotificationListenerService_onNotificationPosted");

//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onNotificationPosted", "PPApplication.notificationScannerRunning="+PPApplication.notificationScannerRunning);

        if (!PPApplication.notificationScannerRunning)
            return;

        //boolean isPowerSaveMode = PPApplication.isPowerSaveMode;
        boolean isPowerSaveMode = DataWrapper.isPowerSaveMode(getApplicationContext());
        if (isPowerSaveMode && ApplicationPreferences.applicationEventNotificationScanInPowerSaveMode.equals("2"))
            return;

        if (sbn == null)
            return;

        //PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "sbn.getPackageName()="+sbn.getPackageName());

        String packageName = sbn.getPackageName();
        if (packageName.equals(PPApplication.PACKAGE_NAME))
            return;
        // check also systemui notificatyion, may be required for notification sensor
        //if (packageName.equals("com.android.systemui"))
        //    return;

//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onNotificationPosted", "sbn="+sbn);

//        PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "is not PPP");

        //final Context appContext = getApplicationContext();

//        int gmtOffset = 0; //TimeZone.getDefault().getRawOffset();
//        long time = sbn.getPostTime() + gmtOffset;
        //getNotifiedPackages(context);
        //addNotifiedPackage(sbn.getPackageName(), time);
        //saveNotifiedPackages(context);

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        if (Event.getGlobalEventsRunning()) {
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "from=" + sbn.getPackageName());

                if (PPApplication.logEnabled()) {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                    String alarmTimeS = sdf.format(sbn.getPostTime());
                    PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "time=" + alarmTimeS);
                }

                if(sbn.getNotification().tickerText !=null) {
                    String ticker = sbn.getNotification().tickerText.toString();
                    PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "ticker=" + ticker);
                }
                Bundle extras = sbn.getNotification().extras;
                if (extras != null) {
                    String title;
                    if (extras.getString("android.title") != null)
                        title = extras.getString("android.title");
                    else
                        title = "";
                    String text;
                    if (extras.getCharSequence("android.text") != null)
                        text = extras.getCharSequence("android.text").toString();
                    else
                        text = "";
                    PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "title=" + title);
                    PPApplication.logE("PPNotificationListenerService.onNotificationPosted", "text=" + text);
                }
            }*/

            /*
            PPApplication.startHandlerThreadBroadcast();
            final Handler handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = null;
                    try {
                        if (powerManager != null) {
                            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":PPNotificationListenerService_onNotificationPosted");
                            wakeLock.acquire(10 * 60 * 1000);
                        }

                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=PPNotificationListenerService.onNotificationPosted");

                        PPApplication.logE("[EVENTS_HANDLER_CALL] PPNotificationListenerService.onNotificationPosted", "sensorType=SENSOR_TYPE_NOTIFICATION");
                        EventsHandler eventsHandler = new EventsHandler(appContext);
                        //eventsHandler.setEventNotificationParameters("posted");
                        eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_NOTIFICATION);

                        //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=PPNotificationListenerService.onNotificationPosted");
                    } finally {
                        if ((wakeLock != null) && wakeLock.isHeld()) {
                            try {
                                wakeLock.release();
                            } catch (Exception ignored) {}
                        }
                    }
                }
            });
            */

            Data workData = new Data.Builder()
                    .putString(PhoneProfilesService.EXTRA_SENSOR_TYPE, EventsHandler.SENSOR_TYPE_NOTIFICATION)
                    .build();

            OneTimeWorkRequest worker =
                    new OneTimeWorkRequest.Builder(MainWorker.class)
                            .addTag(MainWorker.HANDLE_EVENTS_NOTIFICATION_POSTED_SCANNER_WORK_TAG)
                            .setInputData(workData)
                            .setInitialDelay(5, TimeUnit.SECONDS)
                            //.keepResultsForAtLeast(PPApplication.WORK_PRUNE_DELAY_MINUTES, TimeUnit.MINUTES)
                            .build();
            try {
                if (PPApplication.getApplicationStarted(true)) {
                    WorkManager workManager = PPApplication.getWorkManagerInstance();
                    if (workManager != null) {

//                        //if (PPApplication.logEnabled()) {
//                        ListenableFuture<List<WorkInfo>> statuses;
//                        statuses = workManager.getWorkInfosForUniqueWork(MainWorker.HANDLE_EVENTS_NOTIFICATION_SCANNER_WORK_TAG);
//                        try {
//                            List<WorkInfo> workInfoList = statuses.get();
//                            PPApplication.logE("[TEST BATTERY] PPNotificationListenerService.onNotificationPosted", "for=" + MainWorker.HANDLE_EVENTS_NOTIFICATION_SCANNER_WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                        } catch (Exception ignored) {
//                        }
//                        //}

//                        PPApplication.logE("[WORKER_CALL] PPNotificationListenerService.onNotificationPosted", "xxx");
                        //workManager.enqueue(worker);
                        workManager.enqueueUniqueWork(MainWorker.HANDLE_EVENTS_NOTIFICATION_POSTED_SCANNER_WORK_TAG, ExistingWorkPolicy.REPLACE, worker);
                    }
                }
            } catch (Exception e) {
                PPApplication.recordException(e);
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onNotificationRemoved", "PPApplication.notificationScannerRunning="+PPApplication.notificationScannerRunning);

        //CallsCounter.logCounter(getApplicationContext(), "PPNotificationListenerService.onNotificationRemoved", "PPNotificationListenerService_onNotificationRemoved");

        if (!PPApplication.notificationScannerRunning)
            return;

        //boolean isPowerSaveMode = PPApplication.isPowerSaveMode;
        boolean isPowerSaveMode = DataWrapper.isPowerSaveMode(getApplicationContext());
        if (isPowerSaveMode && ApplicationPreferences.applicationEventNotificationScanInPowerSaveMode.equals("2"))
            return;


        if (sbn == null)
            return;

        //PPApplication.logE("PPNotificationListenerService.onNotificationRemoved","packageName="+sbn.getPackageName());

        String packageName = sbn.getPackageName();
        if (packageName.equals(PPApplication.PACKAGE_NAME))
            return;
        // check also systemui notificatyion, may be required for notification sensor
        //if (packageName.equals("com.android.systemui"))
        //    return;

//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onNotificationRemoved", "sbn="+sbn);

        //PPApplication.logE("PPNotificationListenerService.onNotificationRemoved", "is not PPP");

        //final Context appContext = getApplicationContext();

        //getNotifiedPackages(context);
        //removeNotifiedPackage(sbn.getPackageName());
        //saveNotifiedPackages(context);

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        /*
        if (Event.getGlobalEventsRunning()) {
            PPApplication.startHandlerThreadBroadcast();
            final Handler handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = null;
                    try {
                        if (powerManager != null) {
                            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":PPNotificationListenerService_onNotificationRemoved");
                            wakeLock.acquire(10 * 60 * 1000);
                        }

                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=PPNotificationListenerService.onNotificationRemoved");

                        PPApplication.logE("[EVENTS_HANDLER_CALL] PPNotificationListenerService.onNotificationRemoved", "sensorType=SENSOR_TYPE_NOTIFICATION");
                        EventsHandler eventsHandler = new EventsHandler(appContext);
                        //eventsHandler.setEventNotificationParameters("removed");
                        eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_NOTIFICATION);

                        //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=PPNotificationListenerService.onNotificationRemoved");
                    } finally {
                        if ((wakeLock != null) && wakeLock.isHeld()) {
                            try {
                                wakeLock.release();
                            } catch (Exception ignored) {}
                        }
                    }
                }
            });
        }
        */

        Data workData = new Data.Builder()
                .putString(PhoneProfilesService.EXTRA_SENSOR_TYPE, EventsHandler.SENSOR_TYPE_NOTIFICATION)
                .build();

        OneTimeWorkRequest worker =
                new OneTimeWorkRequest.Builder(MainWorker.class)
                        .addTag(MainWorker.HANDLE_EVENTS_NOTIFICATION_REMOVED_SCANNER_WORK_TAG)
                        .setInputData(workData)
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        //.keepResultsForAtLeast(PPApplication.WORK_PRUNE_DELAY_MINUTES, TimeUnit.MINUTES)
                        .build();
        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {

//                        //if (PPApplication.logEnabled()) {
//                        ListenableFuture<List<WorkInfo>> statuses;
//                        statuses = workManager.getWorkInfosForUniqueWork(MainWorker.HANDLE_EVENTS_NOTIFICATION_SCANNER_WORK_TAG);
//                        try {
//                            List<WorkInfo> workInfoList = statuses.get();
//                            PPApplication.logE("[TEST BATTERY] PPNotificationListenerService.onNotificationRemoved", "for=" + MainWorker.HANDLE_EVENTS_NOTIFICATION_SCANNER_WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                        } catch (Exception ignored) {
//                        }
//                        //}

//                    PPApplication.logE("[WORKER_CALL] PPNotificationListenerService.onNotificationRemoved", "xxx");
                    //workManager.enqueue(worker);
                    workManager.enqueueUniqueWork(MainWorker.HANDLE_EVENTS_NOTIFICATION_REMOVED_SCANNER_WORK_TAG, ExistingWorkPolicy.REPLACE, worker);
                }
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }

    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onListenerConnected", "xxx");

        synchronized (PPApplication.ppNotificationListenerService) {
            //PPApplication.logE("PPNotificationListenerService.onListenerConnected","xxx");
            connected = true;
        }
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onListenerDisconnected", "xxx");

        synchronized (PPApplication.ppNotificationListenerService) {
            //PPApplication.logE("PPNotificationListenerService.onListenerDisconnected","xxx");
            connected = false;
        }
    }

    /*
    @Override
    public void onListenerHintsChanged(int hints) {
        super.onListenerHintsChanged(hints);
    }
    */

    @Override
    public void onInterruptionFilterChanged(int interruptionFilter) {
        super.onInterruptionFilterChanged(interruptionFilter);

//        PPApplication.logE("[IN_LISTENER] PPNotificationListenerService.onInterruptionFilterChanged", "xxx");

        //CallsCounter.logCounter(getApplicationContext(), "PPNotificationListenerService.onInterruptionFilterChanged", "PPNotificationListenerService_onInterruptionFilterChanged");

        boolean a60 = /*(android.os.Build.VERSION.SDK_INT == 23) &&*/ Build.VERSION.RELEASE.equals("6.0");
        if (/*((android.os.Build.VERSION.SDK_INT >= 21) &&*/ /*(android.os.Build.VERSION.SDK_INT < 23)) ||*/ a60) {
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE(TAG, "onInterruptionFilterChanged(interruptionFilter=" + interruptionFilter + ')');
                PPApplication.logE(TAG, "onInterruptionFilterChanged(internalChange=" + RingerModeChangeReceiver.internalChange + ")");
            }*/
            if (!RingerModeChangeReceiver.internalChange) {

                final AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                int ringerMode = AudioManager.RINGER_MODE_NORMAL;
                if (audioManager != null)
                    ringerMode = audioManager.getRingerMode();

                // convert to profile zenMode
                int zenMode = 0;
                switch (interruptionFilter) {
                    case NotificationListenerService.INTERRUPTION_FILTER_ALL:
                        //if (ActivateProfileHelper.vibrationIsOn(/*getApplicationContext(), */audioManager, true))
                        if (ringerMode == AudioManager.RINGER_MODE_VIBRATE)
                            zenMode = Profile.ZENMODE_ALL_AND_VIBRATE;
                        else
                            zenMode = Profile.ZENMODE_ALL;
                        break;
                    case NotificationListenerService.INTERRUPTION_FILTER_PRIORITY:
                        //if (ActivateProfileHelper.vibrationIsOn(/*getApplicationContext(), */audioManager, true))
                        if (ringerMode == AudioManager.RINGER_MODE_VIBRATE)
                            zenMode = Profile.ZENMODE_PRIORITY_AND_VIBRATE;
                        else
                            zenMode = Profile.ZENMODE_PRIORITY;
                        break;
                    case NotificationListenerService.INTERRUPTION_FILTER_NONE:
                        zenMode = Profile.ZENMODE_NONE;
                        break;
                    case NotificationListenerService.INTERRUPTION_FILTER_ALARMS: // new filter - Alarm only - Android M
                        zenMode = Profile.ZENMODE_ALARMS;
                        break;
                }
                //PPApplication.logE("********* PPNotificationListenerService.setZenMode", "from=PPNotificationListenerService.onInterruptionFilterChanged zenMode="+zenMode);
                if (zenMode != 0) {
                    synchronized (PPApplication.notUnlinkVolumesMutex) {
                        RingerModeChangeReceiver.notUnlinkVolumes = true;
                    }
                    ActivateProfileHelper.saveRingerMode(getApplicationContext(), Profile.RINGERMODE_ZENMODE);
                    ActivateProfileHelper.saveZenMode(getApplicationContext(), zenMode);
                }
            }

            //RingerModeChangeReceiver.setAlarmForDisableInternalChange(getApplicationContext());
        }
    }

    private static int getZenMode(Context context, AudioManager audioManager) {
        // convert to profile zenMode
        int zenMode = 0;
        int systemZenMode = ActivateProfileHelper.getSystemZenMode(context/*, -1*/);
        //PPApplication.logE("PPNotificationListenerService.getZenMode", "systemZenMode=" + systemZenMode);
        int ringerMode = audioManager.getRingerMode();
        switch (systemZenMode) {
            case ActivateProfileHelper.ZENMODE_ALL:
                //if (ActivateProfileHelper.vibrationIsOn(/*context, */audioManager, true))
                if (ringerMode == AudioManager.RINGER_MODE_VIBRATE)
                    zenMode = Profile.ZENMODE_ALL_AND_VIBRATE;
                else
                    zenMode = Profile.ZENMODE_ALL;
                break;
            case ActivateProfileHelper.ZENMODE_PRIORITY:
                //if (ActivateProfileHelper.vibrationIsOn(/*context, */audioManager, true))
                if (ringerMode == AudioManager.RINGER_MODE_VIBRATE)
                    zenMode = Profile.ZENMODE_PRIORITY_AND_VIBRATE;
                else
                    zenMode = Profile.ZENMODE_PRIORITY;
                break;
            case ActivateProfileHelper.ZENMODE_NONE:
                zenMode = Profile.ZENMODE_NONE;
                break;
            case ActivateProfileHelper.ZENMODE_ALARMS: // new filter - Alarm only - Android M
                zenMode = Profile.ZENMODE_ALARMS;
                break;
        }
        //PPApplication.logE("PPNotificationListenerService.getZenMode", "zenMode=" + zenMode);
        return zenMode;
    }

    public static void setZenMode(Context context, AudioManager audioManager/*, String from*/) {
        boolean a60 = /*(android.os.Build.VERSION.SDK_INT == 23) &&*/ Build.VERSION.RELEASE.equals("6.0");
        if (/*((android.os.Build.VERSION.SDK_INT >= 21) && (android.os.Build.VERSION.SDK_INT < 23)) ||*/ a60) {
            int zenMode = getZenMode(context, audioManager);
            //PPApplication.logE("********* PPNotificationListenerService.setZenMode", "from="+from+" zenMode="+zenMode);
            if (zenMode != 0) {
                ActivateProfileHelper.saveRingerMode(context, Profile.RINGERMODE_ZENMODE);
                ActivateProfileHelper.saveZenMode(context, zenMode);
            }
        }
    }

    public static boolean isNotificationListenerServiceEnabled(Context context, boolean checkConnected) {
        /*
        ContentResolver contentResolver = context.getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String className = PPNotificationListenerService.class.getName();
        // check to see if the enabledNotificationListeners String contains our package name
        if ((enabledNotificationListeners == null) || (!enabledNotificationListeners.contains(className)))
        {
            // in this situation we know that the user has not granted the app the Notification access permission
            return false;
        }
        else
        {
            return true;
        }
        */

        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages (context);
        //String className = PPNotificationListenerService.class.getName();
        String packageName = PPApplication.PACKAGE_NAME;

        //if (packageNames != null) {
            if (checkConnected) {
                synchronized (PPApplication.ppNotificationListenerService) {
                    return packageNames.contains(packageName) && connected;
                }
            }
            else {
                synchronized (PPApplication.ppNotificationListenerService) {
                    return packageNames.contains(packageName);
                }
            }

            /*for (String pkgName : packageNames) {
                //if (className.contains(pkgName)) {
                if (packageName.equals(pkgName)) {
                    return true;
                }
            }
            return false;*/
        //}
        //else
        //    return false;
    }

    /*
    private static Intent getInterruptionFilterRequestIntent(final int filter, final Context context) {
        Intent request = new Intent(PPNotificationListenerService.ACTION_REQUEST_INTERRUPTION_FILTER);
        request.putExtra(EXTRA_FILTER, filter);
        request.setPackage(context.PPApplication.PACKAGE_NAME);
        return request;
    }
    */

    /** Convenience method for sending an {@link android.content.Intent} with {@link #ACTION_REQUEST_INTERRUPTION_FILTER}. */
    @SuppressLint("InlinedApi")
    public static void requestInterruptionFilter(final Context context, final int zenMode) {
        try {
            boolean a60 = /*(android.os.Build.VERSION.SDK_INT == 23) &&*/ Build.VERSION.RELEASE.equals("6.0");
            if (/*((android.os.Build.VERSION.SDK_INT >= 21) && (android.os.Build.VERSION.SDK_INT < 23)) ||*/ a60) {
                if (isNotificationListenerServiceEnabled(context, false)) {
                    int interruptionFilter = NotificationListenerService.INTERRUPTION_FILTER_ALL;
                    switch (zenMode) {
                        case ActivateProfileHelper.ZENMODE_ALL:
                            interruptionFilter = NotificationListenerService.INTERRUPTION_FILTER_ALL;
                            break;
                        case ActivateProfileHelper.ZENMODE_PRIORITY:
                            interruptionFilter = NotificationListenerService.INTERRUPTION_FILTER_PRIORITY;
                            break;
                        case ActivateProfileHelper.ZENMODE_NONE:
                            interruptionFilter = NotificationListenerService.INTERRUPTION_FILTER_NONE;
                            break;
                        case ActivateProfileHelper.ZENMODE_ALARMS:
                            interruptionFilter = NotificationListenerService.INTERRUPTION_FILTER_ALARMS;
                            break;
                    }
                    //Intent request = getInterruptionFilterRequestIntent(interruptionFilter, context);
                    Intent request = new Intent(PPNotificationListenerService.ACTION_REQUEST_INTERRUPTION_FILTER);
                    request.putExtra(EXTRA_FILTER, interruptionFilter);
                    request.setPackage(PPApplication.PACKAGE_NAME);
                    context.sendBroadcast(request);
                }
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
    }

    /*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            // Handle being told to change the interruption filter (zen mode).
            if (!TextUtils.isEmpty(intent.getAction())) {
                if (ACTION_REQUEST_INTERRUPTION_FILTER.equals(intent.getAction())) {
                    if (intent.hasExtra(EXTRA_FILTER)) {
                        final int zenMode = intent.getIntExtra(EXTRA_FILTER, ActivateProfileHelper.ZENMODE_ALL);
                        switch (zenMode) {
                            case ActivateProfileHelper.ZENMODE_ALL:
                                requestInterruptionFilter(INTERRUPTION_FILTER_ALL);
                                break;
                            case ActivateProfileHelper.ZENMODE_PRIORITY:
                                requestInterruptionFilter(INTERRUPTION_FILTER_PRIORITY);
                                break;
                            case ActivateProfileHelper.ZENMODE_NONE:
                                requestInterruptionFilter(INTERRUPTION_FILTER_NONE);
                                break;
                            case ActivateProfileHelper.ZENMODE_ALARMS:
                                requestInterruptionFilter(INTERRUPTION_FILTER_ALARMS);
                                break;
                        }
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
    */

    class NLServiceReceiver extends BroadcastReceiver {

        @SuppressLint("InlinedApi")
        @Override
        public void onReceive(Context context, Intent intent) {
//            PPApplication.logE("[IN_BROADCAST] PPNotificationListenerService.NLServiceReceiver.onReceive", "xxx");

            //CallsCounter.logCounter(getApplicationContext(), "PPNotificationListenerService.NLServiceReceiver.onReceive", "PPNotificationListenerService_NLServiceReceiver_onReceive");

            boolean a60 = /*(android.os.Build.VERSION.SDK_INT == 23) &&*/ Build.VERSION.RELEASE.equals("6.0");
            if (/*((android.os.Build.VERSION.SDK_INT >= 21) && (android.os.Build.VERSION.SDK_INT < 23)) ||*/ a60) {
                // Handle being told to change the interruption filter (zen mode).
                if (!TextUtils.isEmpty(intent.getAction())) {
                    if (PPNotificationListenerService.ACTION_REQUEST_INTERRUPTION_FILTER.equals(intent.getAction())) {
                        if (intent.hasExtra(EXTRA_FILTER)) {
                            final int filter = intent.getIntExtra(EXTRA_FILTER, INTERRUPTION_FILTER_ALL);
                            switch (filter) {
                                case INTERRUPTION_FILTER_ALL:
                                case INTERRUPTION_FILTER_PRIORITY:
                                case INTERRUPTION_FILTER_NONE:
                                case INTERRUPTION_FILTER_ALARMS:
                                    try {
                                        if (isNotificationListenerServiceEnabled(context, false))
                                            requestInterruptionFilter(filter);
                                    } catch (SecurityException e) {
                                        // Fix disallowed call from unknown listener exception.
                                        // java.lang.SecurityException: Disallowed call from unknown listener
                                        PPApplication.recordException(e);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }

        }
    }

    /*
    private static final String POSTED_NOTIFICATIONS_COUNT_PREF = "count";
    private static final String POSTED_NOTIFICATIONS_PACKAGE_PREF = "package";

    public static void getNotifiedPackages(Context context)
    {
        synchronized (PPApplication.notificationsChangeMutex) {

            if (notifications == null)
                notifications = new ArrayList<>();

            notifications.clear();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.POSTED_NOTIFICATIONS_PREFS_NAME, Context.MODE_PRIVATE);

            int count = preferences.getInt(POSTED_NOTIFICATIONS_COUNT_PREF, 0);

            Gson gson = new Gson();

            for (int i = 0; i < count; i++) {
                String json = preferences.getString(POSTED_NOTIFICATIONS_PACKAGE_PREF + i, "");
                if (!json.isEmpty()) {
                    PostedNotificationData notification = gson.fromJson(json, PostedNotificationData.class);
                    notifications.add(notification);
                }
            }
        }
    }

    private static void saveNotifiedPackages(Context context)
    {
        synchronized (PPApplication.notificationsChangeMutex) {

            if (notifications == null)
                notifications = new ArrayList<>();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.POSTED_NOTIFICATIONS_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            editor.putInt(POSTED_NOTIFICATIONS_COUNT_PREF, notifications.size());

            Gson gson = new Gson();

            for (int i = 0; i < notifications.size(); i++) {
                String json = gson.toJson(notifications.get(i));
                editor.putString(POSTED_NOTIFICATIONS_PACKAGE_PREF + i, json);
            }

            editor.apply();
        }
    }

    private void addNotifiedPackage(String packageName, long time)
    {
        synchronized (PPApplication.notificationsChangeMutex) {
            PPApplication.logE("PPNotificationListenerService.addNotifiedPackage", "packageName=" + packageName);
            PPApplication.logE("PPNotificationListenerService.addNotifiedPackage", "time=" + time);

            if (notifications == null)
                notifications = new ArrayList<>();

            boolean found = false;
            for (PostedNotificationData _notification : notifications) {
                if (_notification.packageName.equals(packageName)) {
                    found = true;
                    break;
                }
            }
            PPApplication.logE("PPNotificationListenerService.addNotifiedPackage", "found=" + found);
            if (!found) {
                notifications.add(new PostedNotificationData(packageName, time));
            }

        }
    }

    private void removeNotifiedPackage(String packageName)
    {
        synchronized (PPApplication.notificationsChangeMutex) {

            if (notifications == null)
                notifications = new ArrayList<>();

            int index = 0;
            boolean found = false;
            for (PostedNotificationData _notification : notifications) {
                if (_notification.packageName.equals(packageName)) {
                    found = true;
                    break;
                }
                ++index;
            }
            if (found)
                notifications.remove(index);
        }
    }

    static void clearNotifiedPackages(Context context) {
        synchronized (PPApplication.notificationsChangeMutex) {

            if (notifications == null)
                notifications = new ArrayList<>();
            notifications.clear();

            saveNotifiedPackages(context);
        }
    }

    public static PostedNotificationData getNotificationPosted(String packageName, boolean checkEnd)
    {
        synchronized (PPApplication.notificationsChangeMutex) {
            //packageName = ApplicationsCache.getPackageName(packageName);
            PPApplication.logE("PPNotificationListenerService.getNotificationPosted", "packageName=" + packageName);

            if (notifications == null)
                notifications = new ArrayList<>();

            for (PostedNotificationData _notification : notifications) {
                String _packageName = _notification.getPackageName();
                if (checkEnd) {
                    if (_packageName.endsWith(packageName)) {
                        PPApplication.logE("PPNotificationListenerService.getNotificationPosted", "_packageName returned=" + _packageName);
                        return _notification;
                    }
                }
                else {
                    if (_packageName.equals(packageName)) {
                        PPApplication.logE("PPNotificationListenerService.getNotificationPosted", "_packageName returned=" + _packageName);
                        return _notification;
                    }
                }
            }

            PPApplication.logE("PPNotificationListenerService.getNotificationPosted", "_packageName returned=null");
            return null;
        }
    }
    */

}
