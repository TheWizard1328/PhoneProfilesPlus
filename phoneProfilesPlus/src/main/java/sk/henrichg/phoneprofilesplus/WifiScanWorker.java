package sk.henrichg.phoneprofilesplus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class WifiScanWorker extends Worker {

    private final Context context;

    static final String WORK_TAG  = "WifiScanJob";
    static final String WORK_TAG_SHORT  = "WifiScanJobShort";
    static final String WORK_TAG_START_SCAN = "startWifiScanWork";

    public static WifiManager wifi = null;
    private static WifiManager.WifiLock wifiLock = null;

    private static final String PREF_EVENT_WIFI_SCAN_REQUEST = "eventWifiScanRequest";
    private static final String PREF_EVENT_WIFI_WAIT_FOR_RESULTS = "eventWifiWaitForResults";
    private static final String PREF_EVENT_WIFI_ENABLED_FOR_SCAN = "eventWifiEnabledForScan";

    public WifiScanWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
//            PPApplication.logE("[IN_WORKER] WifiScanWorker.doWork", "xxxx");

            //PPApplication.logE("[IN_WORKER] WifiScanWorker.doWork", "---------------------------------------- START");
//            Set<String> tags = getTags();
//            for (String tag : tags)
//                PPApplication.logE("[IN_WORKER] WifiScanWorker.doWork", "tag=" + tag);

            //CallsCounter.logCounter(context, "WifiScanWorker.doWork", "WifiScanWorker_doWork");

            if (!PPApplication.getApplicationStarted(true))
                // application is not started
                return Result.success();

            if (Event.isEventPreferenceAllowed(EventPreferencesWifi.PREF_EVENT_WIFI_ENABLED, context).allowed !=
                    PreferenceAllowed.PREFERENCE_ALLOWED) {
                cancelWork(context, false/*, null*/);
                /*if (PPApplication.logEnabled()) {
                    PPApplication.logE("WifiScanWorker.doWork", "return - not allowed wifi scanning");
                    PPApplication.logE("WifiScanWorker.doWork", "---------------------------------------- END");
                }*/
                return Result.success();
            }

            //boolean isPowerSaveMode = PPApplication.isPowerSaveMode;
            boolean isPowerSaveMode = DataWrapper.isPowerSaveMode(context);
            if (isPowerSaveMode && ApplicationPreferences.applicationEventLocationUpdateInPowerSaveMode.equals("2")) {
                cancelWork(context, false/*, null*/);
                /*if (PPApplication.logEnabled()) {
                    PPApplication.logE("WifiScanWorker.doWork", "return - update in power save mode is not allowed");
                    PPApplication.logE("WifiScanWorker.doWork", "---------------------------------------- END");
                }*/
                return Result.success();
            }

            if (wifi == null)
                wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            if (Event.getGlobalEventsRunning()) {
                /*if (PPApplication.logEnabled()) {
                    PPApplication.logE("WifiScanWorker.doWork", "global events running=true");
                    PPApplication.logE("WifiScanWorker.doWork", "start scanner");
                }*/
                startScanner(context, false);
            }

            //PPApplication.logE("[RJS] WifiScanWorker.doWork", "schedule work");
            //scheduleWork(context.getApplicationContext(), false);
            OneTimeWorkRequest worker =
                    new OneTimeWorkRequest.Builder(MainWorker.class)
                            .addTag(MainWorker.SCHEDULE_LONG_INTERVAL_WIFI_WORK_TAG)
                            .setInitialDelay(5000, TimeUnit.MILLISECONDS)
                            .build();
            try {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {

//                            //if (PPApplication.logEnabled()) {
//                            ListenableFuture<List<WorkInfo>> statuses;
//                            statuses = workManager.getWorkInfosForUniqueWork(MainWorker.SCHEDULE_LONG_INTERVAL_WIFI_WORK_TAG);
//                            try {
//                                List<WorkInfo> workInfoList = statuses.get();
//                                PPApplication.logE("[TEST BATTERY] WifiScanWorker.doWork", "for=" + MainWorker.SCHEDULE_LONG_INTERVAL_WIFI_WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                            } catch (Exception ignored) {
//                            }
//                            //}

//                    PPApplication.logE("[WORKER_CALL] WifiScanWorker.doWork", "xxx");
                    workManager.enqueueUniqueWork(MainWorker.SCHEDULE_LONG_INTERVAL_WIFI_WORK_TAG, ExistingWorkPolicy.REPLACE, worker);
                }
            } catch (Exception e) {
                PPApplication.recordException(e);
            }
            /*
            PPApplication.startHandlerThreadPPScanners();
            final Handler handler = new Handler(PPApplication.handlerThreadPPScanners.getLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PPApplication.logE("[IN_WORKER] WifiScanWorker.doWork - handler", "schedule work");
                    scheduleWork(context, false);
                }
            }, 1500);
            */

            //PPApplication.logE("WifiScanWorker.doWork", "---------------------------------------- END");

            return Result.success();
        } catch (Exception e) {
            //Log.e("WifiScanWorker.doWork", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            /*Handler _handler = new Handler(getApplicationContext().getMainLooper());
            Runnable r = new Runnable() {
                public void run() {
                    android.os.Process.killProcess(PPApplication.pid);
                }
            };
            _handler.postDelayed(r, 1000);*/
            return Result.failure();
        }
    }

    public void onStopped () {
//        PPApplication.logE("[IN_LISTENER] WifiScanWorker.onStopped", "xxx");

        //CallsCounter.logCounter(context, "WifiScanWorker.onStopped", "WifiScanWorker_onStopped");

        setScanRequest(context, false);
        setWaitForResults(context, false);
        WifiScanner.setForceOneWifiScan(context, WifiScanner.FORCE_ONE_SCAN_DISABLED);
    }

    private static void _scheduleWork(final Context context, final boolean shortInterval) {
        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {

                    /*if (PPApplication.logEnabled()) {
                        PPApplication.logE("WifiScanWorker._scheduleWork", "---------------------------------------- START");
                        PPApplication.logE("WifiScanWorker._scheduleWork", "shortInterval=" + shortInterval);
                    }*/

                    int interval = ApplicationPreferences.applicationEventWifiScanInterval;
                    //boolean isPowerSaveMode = PPApplication.isPowerSaveMode;
                    boolean isPowerSaveMode = DataWrapper.isPowerSaveMode(context);
                    if (isPowerSaveMode && ApplicationPreferences.applicationEventWifiScanInPowerSaveMode.equals("1"))
                        interval = 2 * interval;

                    //PPApplication.logE("WifiScanWorker._scheduleWork", "interval=" + interval);

                    if (!shortInterval) {
                        //PPApplication.logE("WifiScanWorker._scheduleWork", "delay work");
                        /*int keepResultsDelay = (interval * 5);
                        if (keepResultsDelay < PPApplication.WORK_PRUNE_DELAY)
                            keepResultsDelay = PPApplication.WORK_PRUNE_DELAY;*/
                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WifiScanWorker.class)
                                .setInitialDelay(interval, TimeUnit.MINUTES)
                                .addTag(WifiScanWorker.WORK_TAG)
                                .build();
                        /*PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(WifiScanWorker.class,
                                interval, TimeUnit.MINUTES)
                                .addTag(WifiScanWorker.WORK_TAG)
                                .build();*/

//                        //if (PPApplication.logEnabled()) {
//                        ListenableFuture<List<WorkInfo>> statuses;
//                        statuses = workManager.getWorkInfosForUniqueWork(WifiScanWorker.WORK_TAG);
//                        try {
//                            List<WorkInfo> workInfoList = statuses.get();
//                            PPApplication.logE("[TEST BATTERY] WifiScanWorker._scheduleWork", "for=" + WifiScanWorker.WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                        } catch (Exception ignored) {
//                        }
//                        //}

//                        PPApplication.logE("[WORKER_CALL] WifiScanWorker._scheduleWork", "(1)");
                        workManager.enqueueUniqueWork(WifiScanWorker.WORK_TAG, ExistingWorkPolicy.REPLACE/*KEEP*/, workRequest);
                        //workManager.enqueueUniquePeriodicWork(WifiScanWorker.WORK_TAG, ExistingPeriodicWorkPolicy.REPLACE/*KEEP*/, periodicWorkRequest);
                    } else {
                        //PPApplication.logE("WifiScanWorker._scheduleWork", "start now work");
                        //waitForFinish(false);
                        //waitForFinish(true);
                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WifiScanWorker.class)
                                .addTag(WifiScanWorker.WORK_TAG_SHORT)
                                .build();

//                        //if (PPApplication.logEnabled()) {
//                        ListenableFuture<List<WorkInfo>> statuses;
//                        statuses = workManager.getWorkInfosForUniqueWork(WifiScanWorker.WORK_TAG_SHORT);
//                        try {
//                            List<WorkInfo> workInfoList = statuses.get();
//                            PPApplication.logE("[TEST BATTERY] WifiScanWorker._scheduleWork", "for=" + WifiScanWorker.WORK_TAG_SHORT + " workInfoList.size()=" + workInfoList.size());
//                        } catch (Exception ignored) {
//                        }
//                        //}

//                        PPApplication.logE("[WORKER_CALL] WifiScanWorker._scheduleWork", "(2)");
                        workManager.enqueueUniqueWork(WifiScanWorker.WORK_TAG_SHORT, ExistingWorkPolicy.REPLACE/*KEEP*/, workRequest);
                    }

                    //PPApplication.logE("WifiScanWorker._scheduleWork", "---------------------------------------- END");
                }
            }
        } catch (Exception e) {
            //Log.e("WifiScanWorker._scheduleWork", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    static void scheduleWork(final Context context, final boolean shortInterval) {
//        PPApplication.logE("[SHEDULE_WORK] WifiScanWorker.scheduleWork", "shortInterval="+shortInterval);

        if (Event.isEventPreferenceAllowed(EventPreferencesWifi.PREF_EVENT_WIFI_ENABLED, context).allowed
                == PreferenceAllowed.PREFERENCE_ALLOWED) {
            if (shortInterval) {
                PPApplication.startHandlerThreadPPScanners();
                final Handler __handler = new Handler(PPApplication.handlerThreadPPScanners.getLooper());
                __handler.post(new PPApplication.PPHandlerThreadRunnable(
                        context.getApplicationContext()) {
                    @Override
                    public void run() {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPPScanners", "START run - from=WifiScanWorker.scheduleWork" + " shortInterval=true");
                        Context appContext= appContextWeakRef.get();
                        if (appContext != null) {
                            _cancelWork(appContext);
                            PPApplication.sleep(5000);
                            _scheduleWork(appContext, true);
                        }
                    }
                });
            }
            else
                _scheduleWork(context, false);
        }
        //else
        //    PPApplication.logE("WifiScanWorker.scheduleWork","WifiHardware=false");
    }

    private static void _cancelWork(final Context context) {
        if (isWorkScheduled(false) || isWorkScheduled(true)) {
            try {
                waitForFinish(false);
                waitForFinish(true);

                setScanRequest(context, false);
                setWaitForResults(context, false);
                WifiScanner.setForceOneWifiScan(context, WifiScanner.FORCE_ONE_SCAN_DISABLED);

                PPApplication.cancelWork(WORK_TAG, false);
                PPApplication.cancelWork(WORK_TAG_SHORT, false);

//                PPApplication.logE("[FIFO_TEST] WifiScanWorker._cancelWork", "CANCELED");

            } catch (Exception e) {
                //Log.e("WifiScanWorker._cancelWork", Log.getStackTraceString(e));
                PPApplication.recordException(e);
            }
        }
    }

    private static void waitForFinish(boolean shortWork) {
        if (!isWorkRunning(shortWork)) {
            //PPApplication.logE("WifiScanWorker.waitForFinish", "NOT RUNNING");
            return;
        }

        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {
                    //PPApplication.logE("WifiScanWorker.waitForFinish", "START WAIT FOR FINISH");
                    long start = SystemClock.uptimeMillis();
                    do {
                        ListenableFuture<List<WorkInfo>> statuses;
                        if (shortWork)
                            statuses = workManager.getWorkInfosForUniqueWork(WORK_TAG_SHORT);
                        else
                            statuses = workManager.getWorkInfosForUniqueWork(WORK_TAG);
                        boolean allFinished = true;
                        //noinspection TryWithIdenticalCatches
                        try {
                            List<WorkInfo> workInfoList = statuses.get();
                            //PPApplication.logE("[TEST BATTERY] WifiScanWorker.waitForFinish", "workInfoList.size()="+workInfoList.size());
                            for (WorkInfo workInfo : workInfoList) {
                                WorkInfo.State state = workInfo.getState();
                                if (state == WorkInfo.State.RUNNING) {
                                    allFinished = false;
                                    break;
                                }
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (allFinished) {
                            //PPApplication.logE("WifiScanWorker.waitForFinish", "FINISHED");
                            break;
                        }

                        PPApplication.sleep(500);
                    } while (SystemClock.uptimeMillis() - start < WifiScanner.WIFI_SCAN_DURATION * 1000);
                    //PPApplication.logE("WifiScanWorker.waitForFinish", "END WAIT FOR FINISH");
                }
            }
        } catch (Exception e) {
            //Log.e("WifiScanWorker.waitForFinish", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    static void cancelWork(final Context context, final boolean useHandler/*, final Handler _handler*/) {
//        PPApplication.logE("[SHEDULE_WORK] WifiScanWorker.cancelWork", "xxx");

        if (useHandler /*&& (_handler == null)*/) {
            PPApplication.startHandlerThreadPPScanners();
            final Handler __handler = new Handler(PPApplication.handlerThreadPPScanners.getLooper());
            __handler.post(new PPApplication.PPHandlerThreadRunnable(
                    context.getApplicationContext()) {
                @Override
                public void run() {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPPScanners", "START run - from=WifiScanWorker.cancelWork");
                    Context appContext= appContextWeakRef.get();
                    if (appContext != null) {
                        _cancelWork(appContext);
                    }
                }
            });
        }
        else {
            _cancelWork(context);
        }
    }

    private static boolean isWorkRunning(boolean shortWork) {
        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {
                    ListenableFuture<List<WorkInfo>> statuses;
                    if (shortWork)
                        statuses = workManager.getWorkInfosForUniqueWork(WORK_TAG_SHORT);
                    else
                        statuses = workManager.getWorkInfosForUniqueWork(WORK_TAG);
                    //noinspection TryWithIdenticalCatches
                    try {
                        List<WorkInfo> workInfoList = statuses.get();
                        //PPApplication.logE("[TEST BATTERY] WifiScanWorker.isWorkRunning", "workInfoList.size()="+workInfoList.size());
                        boolean running = false;
                        for (WorkInfo workInfo : workInfoList) {
                            WorkInfo.State state = workInfo.getState();
                            running = state == WorkInfo.State.RUNNING;
                            break;
                        }
                        return running;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        return false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                else
                    return false;
            }
            else
                return false;
        } catch (Exception e) {
            //Log.e("WifiScanWorker.isWorkRunning", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            return false;
        }
    }

    static boolean isWorkScheduled(boolean shortWork) {
        //PPApplication.logE("WifiScanWorker.isWorkScheduled", "xxx");
        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {
                    ListenableFuture<List<WorkInfo>> statuses;
                    if (shortWork)
                        statuses = workManager.getWorkInfosForUniqueWork(WORK_TAG_SHORT);
                    else
                        statuses = workManager.getWorkInfosForUniqueWork(WORK_TAG);
                    //noinspection TryWithIdenticalCatches
                    try {
                        List<WorkInfo> workInfoList = statuses.get();
                        //PPApplication.logE("[TEST BATTERY] WifiScanWorker.isWorkScheduled", "workInfoList.size()="+workInfoList.size());
                        boolean running = false;
                        for (WorkInfo workInfo : workInfoList) {
                            WorkInfo.State state = workInfo.getState();
                            running = (state == WorkInfo.State.RUNNING) || (state == WorkInfo.State.ENQUEUED);
                            //PPApplication.logE("WifiScanWorker.isWorkScheduled", "running="+running);
                            break;
                        }
                        return running;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        return false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                else
                    return false;
            }
            else
                return false;
        } catch (Exception e) {
            //Log.e("WifiScanWorker.isWorkScheduled", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            return false;
        }
    }

    //---------------------------------------------------------------

    public static void initialize(Context context, boolean clearScanResult)
    {
        setScanRequest(context, false);
        setWaitForResults(context, false);

        if (Event.isEventPreferenceAllowed(EventPreferencesWifi.PREF_EVENT_WIFI_ENABLED, context).allowed !=
                PreferenceAllowed.PREFERENCE_ALLOWED)
            return;

        if (wifi == null)
            wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        unlock();

        //Log.e("------ WifiScanWorker.initialize", "clearScanResult="+clearScanResult);
        if (clearScanResult)
            clearScanResults(context);

        /*
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        SharedPreferences preferences = context.getSharedPreferences(PPApplication.APPLICATION_PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        //if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
        if ((activeNetwork != null) &&
            (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) &&
            activeNetwork.isConnected())
            editor.putInt(PPApplication.PREF_EVENT_WIFI_LAST_STATE, 1);
        else
        //if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED)
            editor.putInt(PPApplication.PREF_EVENT_WIFI_LAST_STATE, 0);
        //else
        //    editor.putInt(PPApplication.PREF_EVENT_WIFI_LAST_STATE, -1);
        editor.commit();
        */

        fillWifiConfigurationList(context/*, false*/);
    }

    public static void lock(Context context)
    {
        //if (android.os.Build.VERSION.SDK_INT >= 23)
        //    PPApplication.logE("$$$ WifiScanWorker.lock","idleMode="+powerManager.isDeviceIdleMode());

        if (wifi == null)
            wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // initialise the locks
        if ((wifi != null) && (wifiLock == null))
            //noinspection deprecation
            wifiLock = wifi.createWifiLock(WifiManager.WIFI_MODE_SCAN_ONLY , "WifiScanWifiLock");

        try {
            if (!wifiLock.isHeld())
                wifiLock.acquire();
            //PPApplication.logE("$$$ WifiScanWorker.lock","xxx");
        } catch (Exception e) {
            //Log.e("WifiScanWorker.lock", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    public static void unlock()
    {
        try {
            if ((wifiLock != null) && (wifiLock.isHeld()))
                wifiLock.release();
            //PPApplication.logE("$$$ WifiScanWorker.unlock", "xxx");
        } catch (Exception e) {
            //Log.e("WifiScanWorker.unlock", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    static void getScanRequest(Context context)
    {
        synchronized (PPApplication.eventWifiSensorMutex) {
            ApplicationPreferences.prefEventWifiScanRequest = ApplicationPreferences.
                    getSharedPreferences(context).getBoolean(PREF_EVENT_WIFI_SCAN_REQUEST, false);
        }
    }
    static void setScanRequest(Context context, boolean scanRequest)
    {
        synchronized (PPApplication.eventWifiSensorMutex) {
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putBoolean(PREF_EVENT_WIFI_SCAN_REQUEST, scanRequest);
            editor.apply();
            ApplicationPreferences.prefEventWifiScanRequest = scanRequest;
            //PPApplication.logE("@@@ WifiScanWorker.setScanRequest","scanRequest="+scanRequest);
        }
    }

    static void getWaitForResults(Context context)
    {
        synchronized (PPApplication.eventWifiSensorMutex) {
            ApplicationPreferences.prefEventWifiWaitForResult = ApplicationPreferences.
                    getSharedPreferences(context).getBoolean(PREF_EVENT_WIFI_WAIT_FOR_RESULTS, false);
        }
    }
    static void setWaitForResults(Context context, boolean waitForResults)
    {
        synchronized (PPApplication.eventWifiSensorMutex) {
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putBoolean(PREF_EVENT_WIFI_WAIT_FOR_RESULTS, waitForResults);
            editor.apply();
            ApplicationPreferences.prefEventWifiWaitForResult = waitForResults;
            //PPApplication.logE("$$$ WifiScanWorker.setWaitForResults", "waitForResults=" + waitForResults);
        }
    }

    static void startScan(Context context)
    {
        lock(context); // lock wakeLock and wifiLock, then scan.
        // unlock() is then called at the end of the onReceive function of WifiScanBroadcastReceiver
        try {
            if (wifi == null)
                wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            boolean startScan = false;
            if (wifi != null) {
                //noinspection deprecation
                startScan = wifi.startScan();
            }
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE("$$$ WifiScanWorker.startScan", "scanStarted=" + startScan);
                //PPApplication.logE("$$$ WifiAP", "WifiScanWorker.startScan-startScan=" + startScan);
            }*/
            if (!startScan) {
                if (ApplicationPreferences.prefEventWifiEnabledForScan) {
                    /*if (PPApplication.logEnabled()) {
                        PPApplication.logE("$$$ WifiScanWorker.startScan", "disable wifi");
                        PPApplication.logE("#### setWifiEnabled", "from WifiScanWorker.startScan 1");
                    }*/
                    //if (Build.VERSION.SDK_INT >= 29)
                    //    CmdWifi.setWifi(false);
                    //else
                    if (wifi != null) {
//                        PPApplication.logE("[WIFI_ENABLED] WifiScanWorker.startScan", "false (1)");
                        //noinspection deprecation
                        wifi.setWifiEnabled(false);
                    }
                }
                unlock();
            }
            setWaitForResults(context, startScan);
            setScanRequest(context, false);
        } catch (Exception e) {
            if (ApplicationPreferences.prefEventWifiEnabledForScan) {
                /*if (PPApplication.logEnabled()) {
                    PPApplication.logE("$$$ WifiScanWorker.startScan", "disable wifi");
                    PPApplication.logE("#### setWifiEnabled", "from WifiScanWorker.startScan 2");
                }*/
                //if (Build.VERSION.SDK_INT >= 29)
                //    CmdWifi.setWifi(false);
                //else {
                    if (wifi == null)
                        wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wifi != null) {
//                        PPApplication.logE("[WIFI_ENABLED] WifiScanWorker.startScan", "false (2)");
                        //noinspection deprecation
                        wifi.setWifiEnabled(false);
                    }
                //}
            }
            unlock();
            setWaitForResults(context, false);
            setScanRequest(context, false);
        }
    }

    static void startScanner(Context context, boolean fromDialog)
    {
        //PPApplication.logE("$$$ WifiScanWorker.startScanner", "xxx");
        //DataWrapper dataWrapper = new DataWrapper(context, false, 0, false);
        if (fromDialog || ApplicationPreferences.applicationEventWifiEnableScanning) {
            if (fromDialog)
                setScanRequest(context, true);

            WifiScanner wifiBluetoothScanner = new WifiScanner(context);
            wifiBluetoothScanner.doScan();
        }
        //dataWrapper.invalidateDataWrapper();
    }

    /*
    static public void stopScan(Context context)
    {
        PPApplication.logE("@@@ WifiScanWorker.stopScan","xxx");
        unlock();
        if (getWifiEnabledForScan(context))
            wifi.setWifiEnabled(false);
        setWifiEnabledForScan(context, false);
        setScanRequest(context, false);
        setWaitForResults(context, false);
        WifiBluetoothScanner.setForceOneWifiScan(context, WifiBluetoothScanner.FORCE_ONE_SCAN_DISABLED);
    }
    */

    static void getWifiEnabledForScan(Context context)
    {
        synchronized (PPApplication.eventWifiSensorMutex) {
            ApplicationPreferences.prefEventWifiEnabledForScan = ApplicationPreferences.
                    getSharedPreferences(context).getBoolean(PREF_EVENT_WIFI_ENABLED_FOR_SCAN, false);
        }
    }

    static void setWifiEnabledForScan(Context context, boolean setEnabled)
    {
        synchronized (PPApplication.eventWifiSensorMutex) {
            //PPApplication.logE("@@@ WifiScanWorker.setWifiEnabledForScan","setEnabled="+setEnabled);
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putBoolean(PREF_EVENT_WIFI_ENABLED_FOR_SCAN, setEnabled);
            editor.apply();
            ApplicationPreferences.prefEventWifiEnabledForScan = setEnabled;
        }
    }

    @SuppressLint("MissingPermission")
    static void fillWifiConfigurationList(Context context/*, boolean enableWifi*/)
    {
        //if (wifiConfigurationList == null)
        //    wifiConfigurationList = new ArrayList<WifiSSIDData>();

        List<WifiSSIDData> wifiConfigurationList = new ArrayList<>();

        if (wifi == null)
            wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi == null)
            return;

        //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","wifi="+wifi);

        //boolean wifiEnabled = false;
        if (wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
            //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","wifi is NOT enabled");
            /*if (enableWifi) {
                try {
                    wifiEnabled = true;
                    wifi.setWifiEnabled(true);
                    PPApplication.sleep(500);
                } catch (Exception e) {
                    wifiEnabled = false;
                }
            }
            else*/
                // wifi must be enabled for wifi.getConfiguredNetworks()
                return;
        }
        //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","wifi is enabled");

        //noinspection deprecation
        List<WifiConfiguration> _wifiConfigurationList = null;
        if (Permissions.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION))
            //noinspection deprecation
            _wifiConfigurationList = wifi.getConfiguredNetworks();

        /*if (wifiEnabled) {
            try {
                wifi.setWifiEnabled(false);
            } catch (Exception ignored) {}
        }*/

        if (_wifiConfigurationList != null)
        {
            //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","_wifiConfigurationList.size()="+_wifiConfigurationList.size());
            //wifiConfigurationList.clear();
            //noinspection deprecation
            for (WifiConfiguration device : _wifiConfigurationList)
            {
                //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","device.SSID="+device.SSID);
                if (device.SSID != null) {
                    boolean found = false;
                    for (WifiSSIDData _device : wifiConfigurationList) {
                        //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","_device.ssid="+_device.ssid);
                        //if (_device.bssid.equals(device.BSSID))
                        if ((_device.ssid != null) && (_device.ssid.equals(device.SSID))) {
                            //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","device found");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","device NOT found, add it");
                        wifiConfigurationList.add(new WifiSSIDData(device.SSID, /*device.BSSID,*/ false, true, false));
                    }
                }
            }
        }
        //PPApplication.logE("WifiScanWorker.fillWifiConfigurationList","wifiConfigurationList.size()="+wifiConfigurationList.size());
        saveWifiConfigurationList(context, wifiConfigurationList);
    }

    static void fillScanResults(Context context)
    {
        List<WifiSSIDData> scanResults = null;
        //boolean save = false;

        if (wifi == null)
            wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (Permissions.checkLocation(context)) {
            List<ScanResult> _scanResults = wifi.getScanResults();
            //if (PPApplication.logEnabled()) {
                //int wifiState = wifi.getWifiState();
                //PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "wifiState=" + wifiState);
                //PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "_scanResults=" + _scanResults);
                //PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "getScanResults() called");
            //}
            if (_scanResults != null) {
                scanResults = new ArrayList<>();

                //PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                //boolean isScreenOn = PPApplication.isScreenOn(pm);
                //PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "isScreenOn="+isScreenOn);
                //if ((android.os.Build.VERSION.SDK_INT < 21) || (_scanResults.size() > 0) || isScreenOn) {
                //scanResults.clear();
                for (ScanResult _device : _scanResults) {
                    /*if (_device.SSID.equals("OLIN")) {
                        PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "_device.SSID=" + _device.SSID);
                        PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "_device.BSSID=" + _device.BSSID);
                    }*/
                    boolean found = false;
                    for (WifiSSIDData device : scanResults) {
                        /*if (device.bssid.equals(_device.BSSID)) {
                            // is already in scanResults
                            found = true;
                            break;
                        }*/
                        if (device.ssid.equals(_device.SSID)) {
                            // is already in scanResults
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        scanResults.add(new WifiSSIDData(_device.SSID, /*_device.BSSID,*/ false, false, true));
                    }
                }
                //}
            }
            //else
            //    PPApplication.logE("%%%% WifiScanWorker.fillScanResults", "_scanResults=null");
        }
        saveScanResults(context, scanResults);
    }

    private static final String SCAN_RESULT_COUNT_PREF = "count";
    private static final String SCAN_RESULT_DEVICE_PREF = "device";

    //public static void getWifiConfigurationList(Context context)
    static List<WifiSSIDData> getWifiConfigurationList(Context context)
    {
        synchronized (PPApplication.wifiScanResultsMutex) {
            //if (wifiConfigurationList == null)
            //    wifiConfigurationList = new ArrayList<WifiSSIDData>();

            //wifiConfigurationList.clear();

            List<WifiSSIDData> wifiConfigurationList = new ArrayList<>();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.WIFI_CONFIGURATION_LIST_PREFS_NAME, Context.MODE_PRIVATE);

            int count = preferences.getInt(SCAN_RESULT_COUNT_PREF, 0);

            Gson gson = new Gson();

            for (int i = 0; i < count; i++) {
                String json = preferences.getString(SCAN_RESULT_DEVICE_PREF + i, "");
                if (!json.isEmpty()) {
                    WifiSSIDData device = gson.fromJson(json, WifiSSIDData.class);
                    device.configured = true;
                    wifiConfigurationList.add(device);
                }
            }

            return wifiConfigurationList;
        }
    }

    //private static void saveWifiConfigurationList(Context context)
    private static void saveWifiConfigurationList(Context context, List<WifiSSIDData> wifiConfigurationList)
    {
        synchronized (PPApplication.wifiScanResultsMutex) {
            //if (wifiConfigurationList == null)
            //    wifiConfigurationList = new ArrayList<WifiSSIDData>();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.WIFI_CONFIGURATION_LIST_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            //PPApplication.logE("WifiScanWorker.saveWifiConfigurationList","wifiConfigurationList.size()="+wifiConfigurationList.size());
            editor.putInt(SCAN_RESULT_COUNT_PREF, wifiConfigurationList.size());

            Gson gson = new Gson();

            for (int i = 0; i < wifiConfigurationList.size(); i++) {
                //PPApplication.logE("WifiScanWorker.saveWifiConfigurationList","wifiConfigurationList.get(i).ssid="+wifiConfigurationList.get(i).ssid);
                String json = gson.toJson(wifiConfigurationList.get(i));
                editor.putString(SCAN_RESULT_DEVICE_PREF + i, json);
            }

            editor.apply();
        }
    }

    //public static void getScanResults(Context context)
    static List<WifiSSIDData> getScanResults(Context context)
    {
        synchronized (PPApplication.wifiScanResultsMutex) {
            SharedPreferences preferences = context.getSharedPreferences(PPApplication.WIFI_SCAN_RESULTS_PREFS_NAME, Context.MODE_PRIVATE);
            int count = preferences.getInt(SCAN_RESULT_COUNT_PREF, -1);

            if (count > -1) {
                List<WifiSSIDData> scanResults = new ArrayList<>();

                Gson gson = new Gson();

                for (int i = 0; i < count; i++) {
                    String json = preferences.getString(SCAN_RESULT_DEVICE_PREF + i, "");
                    if (!json.isEmpty()) {
                        WifiSSIDData device = gson.fromJson(json, WifiSSIDData.class);
                        device.scanned = true;
                        scanResults.add(device);
                    }
                }
                return scanResults;
            } else
                return null;
        }
    }

    //private static void saveScanResults(Context context)
    private static void saveScanResults(Context context, List<WifiSSIDData> scanResults)
    {
        synchronized (PPApplication.wifiScanResultsMutex) {
            //if (scanResults == null)
            //    scanResults = new ArrayList<WifiSSIDData>();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.WIFI_SCAN_RESULTS_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            if (scanResults == null)
                editor.putInt(SCAN_RESULT_COUNT_PREF, -1);
            else {
                editor.putInt(SCAN_RESULT_COUNT_PREF, scanResults.size());

                Gson gson = new Gson();

                for (int i = 0; i < scanResults.size(); i++) {
                    String json = gson.toJson(scanResults.get(i));
                    editor.putString(SCAN_RESULT_DEVICE_PREF + i, json);
                }
            }

            editor.apply();
        }
    }

    private static void clearScanResults(Context context) {
        synchronized (PPApplication.wifiScanResultsMutex) {
            SharedPreferences preferences = context.getSharedPreferences(PPApplication.WIFI_SCAN_RESULTS_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();
            editor.putInt(SCAN_RESULT_COUNT_PREF, -1);

            editor.apply();
        }
    }

    @SuppressLint("MissingPermission")
    private static String getSSID(WifiManager wifiManager, WifiInfo wifiInfo, List<WifiSSIDData> wifiConfigurationList, Context context)
    {
        String SSID = wifiInfo.getSSID();
        if (SSID == null)
            SSID = "";
        SSID = SSID.replace("\"", "");

        if (SSID.isEmpty())
        {
            if (wifiConfigurationList != null)
            {
                for (WifiSSIDData wifiConfiguration : wifiConfigurationList)
                {
                    /*if ((wifiConfiguration.bssid != null) &&
                            (wifiConfiguration.bssid.equals(wifiInfo.getBSSID())))
                        return wifiConfiguration.ssid.replace("\"", "");*/
                    if ((wifiConfiguration.ssid != null) &&
                            (wifiConfiguration.ssid.equals(wifiInfo.getSSID())))
                        return wifiConfiguration.ssid.replace("\"", "");
                }
            }
        }

        if (SSID.equals("<unknown ssid>")) {
            //noinspection deprecation
            List<WifiConfiguration> listOfConfigurations = null;
            if (Permissions.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION))
                //noinspection deprecation
                listOfConfigurations = wifiManager.getConfiguredNetworks();

            if (listOfConfigurations != null) {
                for (int index = 0; index < listOfConfigurations.size(); index++) {
                    //noinspection deprecation
                    WifiConfiguration configuration = listOfConfigurations.get(index);
                    if (configuration.networkId == wifiInfo.getNetworkId()) {
                        return configuration.SSID;
                    }
                }
            }
        }

        return SSID;
    }

    static boolean compareSSID(WifiManager wifiManager, WifiInfo wifiInfo, String SSID, List<WifiSSIDData> wifiConfigurationList, Context context)
    {
        String wifiInfoSSID = getSSID(wifiManager, wifiInfo, wifiConfigurationList, context);
        String ssid2 = "\"" + SSID + "\"";
        //return (wifiInfoSSID.equals(SSID) || wifiInfoSSID.equals(ssid2));
        return (Wildcard.match(wifiInfoSSID, SSID, '_', '%', true) || Wildcard.match(wifiInfoSSID, ssid2, '_', '%', true));
    }

    static String getSSID(WifiSSIDData result, List<WifiSSIDData> wifiConfigurationList)
    {
        String SSID;
        if (result.ssid == null)
            SSID = "";
        else
            SSID = result.ssid.replace("\"", "");

        if (SSID.isEmpty())
        {
            if (wifiConfigurationList != null)
            {
                for (WifiSSIDData wifiConfiguration : wifiConfigurationList)
                {
                    /*if ((wifiConfiguration.bssid != null) &&
                            (wifiConfiguration.bssid.equals(result.bssid)))
                        return wifiConfiguration.ssid.replace("\"", "");*/
                    if ((wifiConfiguration.ssid != null) &&
                            (wifiConfiguration.ssid.equals(result.ssid)))
                        return wifiConfiguration.ssid.replace("\"", "");
                }
            }
        }

        //PPApplication.logE("@@@x WifiScanWorker.getSSID", "SSID="+SSID);

        return SSID;
    }

    static boolean compareSSID(WifiSSIDData result, String SSID, List<WifiSSIDData> wifiConfigurationList)
    {
        String wifiInfoSSID = getSSID(result, wifiConfigurationList);
        String ssid2 = "\"" + SSID + "\"";
        //PPApplication.logE("@@@x WifiScanWorker.compareSSID", "wifiInfoSSID="+wifiInfoSSID);
        //PPApplication.logE("@@@x WifiScanWorker.compareSSID", "ssid2="+ssid2);

        //return (getSSID(result).equals(SSID) || getSSID(result).equals(ssid2));
        return (Wildcard.match(wifiInfoSSID, SSID, '_', '%', true) || Wildcard.match(wifiInfoSSID, ssid2, '_', '%', true));
    }

}
