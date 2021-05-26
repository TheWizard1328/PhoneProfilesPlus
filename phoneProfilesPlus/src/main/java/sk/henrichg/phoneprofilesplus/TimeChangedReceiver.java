package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;

public class TimeChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent != null)
//            PPApplication.logE("[IN_BROADCAST] TimeChangedReceiver.onReceive", "intent.getAction()="+intent.getAction());
//        else
//            PPApplication.logE("[IN_BROADCAST] TimeChangedReceiver.onReceive", "xxx");

        if ((intent != null) && (intent.getAction() != null)) {
            final String action = intent.getAction();
            //PPApplication.logE("TimeChangedReceiver.onReceive", "action="+action);
            if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)/* ||
                    action.equals(Intent.ACTION_TIME_CHANGED) ||
                    action.equals(Intent.ACTION_TIME_TICK)*/) {
                //CallsCounter.logCounter(context, "TimeChangedReceiver.onReceive", "TimeChangedReceiver_onReceive");

                final Context appContext = context.getApplicationContext();

                if (!PPApplication.getApplicationStarted(true))
                    return;

                /*boolean timeChanged = true;

                if (action.equals(Intent.ACTION_TIME_TICK)) {
                    long uptimeDifference = SystemClock.elapsedRealtime() - PPApplication.lastUptimeTime;
                    long epochDifference = System.currentTimeMillis() - PPApplication.lastEpochTime;
                    long timeChange = Math.abs(uptimeDifference - epochDifference);
                    if (PPApplication.logEnabled()) {
                        PPApplication.logE("TimeChangedReceiver.onReceive", "uptimeDifference=" + uptimeDifference);
                        PPApplication.logE("TimeChangedReceiver.onReceive", "epochDifference=" + epochDifference);
                        PPApplication.logE("TimeChangedReceiver.onReceive", "timeChange=" + timeChange);
                    }

                    // Time has changed more than 1 minute
                    timeChanged = timeChange > 1000 * 60;

                    PPApplication.lastUptimeTime = SystemClock.elapsedRealtime();
                    PPApplication.lastEpochTime = System.currentTimeMillis();
                }*/

                /*if (action.equals(Intent.ACTION_TIME_CHANGED)) {
                    timeChanged = false;
                    String isAutoTime = Settings.Global.getString(appContext.getContentResolver(), Settings.Global.AUTO_TIME);
                    PPApplication.logE("TimeChangedReceiver.onReceive", "isAutoTime="+isAutoTime);
                    if ((isAutoTime != null) && isAutoTime.equals("0")) {
                        timeChanged = true;
                    }
                }

                PPApplication.logE("TimeChangedReceiver.onReceive", "timeChanged="+timeChanged);
                */

                //if (timeChanged) {
                    //PPApplication.logE("TimeChangedReceiver.onReceive", "do time change");

                    PPApplication.startHandlerThreadBroadcast(/*"TimeChangedReceiver.onReceive"*/);
                    final Handler handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
                    handler.post(() -> {
//                            PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=TimeChangedReceiver.onReceive");

                        PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                        PowerManager.WakeLock wakeLock = null;
                        try {
                            if (powerManager != null) {
                                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":TimeChangedReceiver_onReceive");
                                wakeLock.acquire(10 * 60 * 1000);
                            }

                            doWork(appContext, false);

                            //PPApplication.logE("PPApplication.startHandlerThread", "END run - from=TimeChangedReceiver.onReceive");

                        } catch (Exception e) {
//                                PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", Log.getStackTraceString(e));
                            PPApplication.recordException(e);
                        } finally {
                            if ((wakeLock != null) && wakeLock.isHeld()) {
                                try {
                                    wakeLock.release();
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    });
                //}
            }
        }
    }

    static void doWork(Context appContext, boolean logRestart/*, boolean forceRestart*/) {
//        PPApplication.logE("TimeChangedReceiver.doWork", "xxx");

        DataWrapper dataWrapper = new DataWrapper(appContext, false, 0, false);

        dataWrapper.fillProfileList(false, false);
        for (Profile profile : dataWrapper.profileList) {
            ProfileDurationAlarmBroadcastReceiver.removeAlarm(profile, appContext);

            if (profile._deviceRunApplicationChange == 1) {
                String[] splits = profile._deviceRunApplicationPackageName.split("\\|");
                for (String split : splits) {
                    RunApplicationWithDelayBroadcastReceiver.removeDelayAlarm(appContext, split);
                    //RunApplicationWithDelayBroadcastReceiver.setDelayAlarm(appContext, split);
                    //int startApplicationDelay = Application.getStartApplicationDelay(split);
                    //if (Application.getStartApplicationDelay(split) > 0)
                    //    RunApplicationWithDelayBroadcastReceiver.setDelayAlarm(appContext, startApplicationDelay, split);
                    //else
                    //    RunApplicationWithDelayBroadcastReceiver.removeDelayAlarm(appContext, split);
                }
            }
        }

        //Profile.setActivatedProfileForDuration(appContext, 0);

//        PPApplication.logE("[WORKER_CALL] TimeChangedReceiver.doWork", "xxx");
        LockDeviceAfterScreenOffBroadcastReceiver.doWork(false, appContext);
        LockDeviceActivityFinishBroadcastReceiver.doWork();
//        PPApplication.logE("TimeChangedReceiver.doWork", "call of LocationScannerSwitchGPSBroadcastReceiver.doWork, useGPS=true");
        LocationScanner.useGPS = true;
        LocationScannerSwitchGPSBroadcastReceiver.doWork(appContext);

        DonationBroadcastReceiver.setAlarm(appContext);
        CheckGitHubReleasesBroadcastReceiver.setAlarm(appContext);
        CheckCriticalGitHubReleasesBroadcastReceiver.setAlarm(appContext);
        TwilightScanner.doWork();

        SearchCalendarEventsWorker.scheduleWork(true);

        //dataWrapper.clearSensorsStartTime();
        //dataWrapper.restartEvents(false, true, false, false, false);
        //dataWrapper.restartEventsWithRescan(false, false, false, false);
        /*if (forceRestart) {
            if (!DataWrapper.getIsManualProfileActivation(false)) {
                PPApplication.logE("*********** restartEvents", "from TimeChangedReceiver.doWork() - 1");
                dataWrapper.restartEventsWithRescan(true, false, true, false);
            }
            else {
                PPApplication.logE("*********** restartEvents", "from TimeChangedReceiver.doWork() - 2");
                dataWrapper.restartEventsWithRescan(false, false, true, false);
            }
        }
        else {
            PPApplication.logE("*********** restartEvents", "from TimeChangedReceiver.doWork() - 3");
            dataWrapper.restartEventsWithRescan(false, false, false, false);
        }*/
//        PPApplication.logE("[APP_START] TimeChangedReceiver.doWork", "xxxx");
        dataWrapper.restartEventsWithRescan(true, true, false, false, logRestart, false);
    }

}
