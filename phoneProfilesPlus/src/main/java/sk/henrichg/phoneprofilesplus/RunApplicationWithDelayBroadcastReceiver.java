package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RunApplicationWithDelayBroadcastReceiver extends BroadcastReceiver {

    static final String EXTRA_RUN_APPLICATION_DATA = "run_application_data";
    static final String EXTRA_PROFILE_NAME = "profile_name";

    @Override
    public void onReceive(Context context, Intent intent) {
//        PPApplication.logE("[IN_BROADCAST] RunApplicationWithDelayBroadcastReceiver.onReceive", "xxx");
        //CallsCounter.logCounter(context, "RunApplicationWithDelayBroadcastReceiver.onReceive", "RunApplicationWithDelayBroadcastReceiver_onReceive");

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        if (intent != null) {
            final String profileName = intent.getStringExtra(EXTRA_PROFILE_NAME);
            final String runApplicationData = intent.getStringExtra(EXTRA_RUN_APPLICATION_DATA);

            PPApplication.startHandlerThreadBroadcast(/*"RunApplicationWithDelayBroadcastReceiver.onReceive"*/);
            final Handler __handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
            __handler.post(new PPApplication.PPHandlerThreadRunnable(context.getApplicationContext()) {
                @Override
                public void run() {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=RunApplicationWithDelayBroadcastReceiver.onReceive");

                    Context appContext= appContextWeakRef.get();

                    if (appContext != null) {
                        PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                        PowerManager.WakeLock wakeLock = null;
                        try {
                            if (powerManager != null) {
                                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":RunApplicationWithDelayBroadcastReceiver_onReceive");
                                wakeLock.acquire(10 * 60 * 1000);
                            }

                            doWork(appContext, profileName, runApplicationData);

                            //PPApplication.logE("PPApplication.startHandlerThread", "END run - from=RunApplicationWithDelayBroadcastReceiver.onReceive");
                        } catch (Exception e) {
//                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", Log.getStackTraceString(e));
                            PPApplication.recordException(e);
                        } finally {
                            if ((wakeLock != null) && wakeLock.isHeld()) {
                                try {
                                    wakeLock.release();
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private static int hashData(String runApplicationData) {
        int sLength = runApplicationData.length();
        int sum = 0;
        for(int i = 0 ; i < sLength-1 ; i++){
            sum += runApplicationData.charAt(i)<<(5*i);
        }
        return sum;
    }

    @SuppressLint("NewApi")
    static void setDelayAlarm(Context context, int startApplicationDelay, String profileName, String runApplicationData)
    {
        removeDelayAlarm(context, runApplicationData);

        if (startApplicationDelay > 0)
        {
            int requestCode = hashData(runApplicationData); //PPApplication.requestCodeForAlarm.nextInt();

            if (!PPApplication.isIgnoreBatteryOptimizationEnabled(context)) {
                if (ApplicationPreferences.applicationUseAlarmClock) {
                    //Intent intent = new Intent(_context, RunApplicationWithDelayBroadcastReceiver.class);
                    Intent intent = new Intent();
                    intent.setAction(PhoneProfilesService.ACTION_RUN_APPLICATION_DELAY_BROADCAST_RECEIVER);
                    //intent.setClass(context, RunApplicationWithDelayBroadcastReceiver.class);

                    intent.putExtra(EXTRA_PROFILE_NAME, profileName);
                    intent.putExtra(EXTRA_RUN_APPLICATION_DATA, runApplicationData);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    if (alarmManager != null) {
                        Calendar now = Calendar.getInstance();
                        now.add(Calendar.SECOND, startApplicationDelay);
                        long alarmTime = now.getTimeInMillis();

                        /*if (PPApplication.logEnabled()) {
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                            String result = sdf.format(alarmTime);
                            PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.setDelayAlarm", "startTime=" + result);
                        }*/

                        Intent editorIntent = new Intent(context, EditorProfilesActivity.class);
                        editorIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent infoPendingIntent = PendingIntent.getActivity(context, 1000, editorIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(alarmTime, infoPendingIntent);
                        alarmManager.setAlarmClock(clockInfo, pendingIntent);
                    }
                } else {
                    Data workData = new Data.Builder()
                            .putString(EXTRA_PROFILE_NAME, profileName)
                            .putString(EXTRA_RUN_APPLICATION_DATA, runApplicationData)
                            .build();

                /*int keepResultsDelay = (startApplicationDelay * 5) / 60; // conversion to minutes
                if (keepResultsDelay < PPApplication.WORK_PRUNE_DELAY)
                    keepResultsDelay = PPApplication.WORK_PRUNE_DELAY;*/
                    OneTimeWorkRequest worker =
                            new OneTimeWorkRequest.Builder(MainWorker.class)
                                    .addTag(MainWorker.RUN_APPLICATION_WITH_DELAY_WORK_TAG + "_" + requestCode)
                                    .setInputData(workData)
                                    .setInitialDelay(startApplicationDelay, TimeUnit.SECONDS)
                                    .keepResultsForAtLeast(PPApplication.WORK_PRUNE_DELAY_DAYS, TimeUnit.DAYS)
                                    .build();
                    try {
                        if (PPApplication.getApplicationStarted(true)) {
                            WorkManager workManager = PPApplication.getWorkManagerInstance();
                            if (workManager != null) {
                            /*if (PPApplication.logEnabled()) {
                                PPApplication.logE("[HANDLER] RunApplicationWithDelayBroadcastReceiver.setAlarm", "enqueueUniqueWork - startApplicationDelay=" + startApplicationDelay);
                                PPApplication.logE("[HANDLER] RunApplicationWithDelayBroadcastReceiver.setAlarm", "enqueueUniqueWork - runApplicationData=" + runApplicationData);
                            }*/

//                            //if (PPApplication.logEnabled()) {
//                            ListenableFuture<List<WorkInfo>> statuses;
//                            statuses = workManager.getWorkInfosForUniqueWork(MainWorker.RUN_APPLICATION_WITH_DELAY_TAG_WORK +"_"+requestCode);
//                            try {
//                                List<WorkInfo> workInfoList = statuses.get();
//                                PPApplication.logE("[TEST BATTERY] RunApplicationWithDelayBroadcastReceiver.setDelayAlarm", "for=" + MainWorker.RUN_APPLICATION_WITH_DELAY_TAG_WORK +"_"+requestCode + " workInfoList.size()=" + workInfoList.size());
//                            } catch (Exception ignored) {
//                            }
//                            //}

//                                PPApplication.logE("[WORKER_CALL] RunApplicationWithDelayBroadcastReceiver.setDelayAlarm", "xxx");
                                //workManager.enqueue(worker);
                                workManager.enqueueUniqueWork(MainWorker.RUN_APPLICATION_WITH_DELAY_WORK_TAG + "_" + requestCode, ExistingWorkPolicy.APPEND_OR_REPLACE, worker);
                                PPApplication.elapsedAlarmsRunApplicationWithDelayWork.add(MainWorker.RUN_APPLICATION_WITH_DELAY_WORK_TAG + "_" + requestCode);
                            }
                        }
                    } catch (Exception e) {
                        PPApplication.recordException(e);
                    }
                }
            }
            else {

                //Intent intent = new Intent(_context, RunApplicationWithDelayBroadcastReceiver.class);
                Intent intent = new Intent();
                intent.setAction(PhoneProfilesService.ACTION_RUN_APPLICATION_DELAY_BROADCAST_RECEIVER);
                //intent.setClass(context, RunApplicationWithDelayBroadcastReceiver.class);

                intent.putExtra(EXTRA_PROFILE_NAME, profileName);
                intent.putExtra(EXTRA_RUN_APPLICATION_DATA, runApplicationData);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    if (ApplicationPreferences.applicationUseAlarmClock) {
                        Calendar now = Calendar.getInstance();
                        now.add(Calendar.SECOND, startApplicationDelay);
                        long alarmTime = now.getTimeInMillis();

                        /*if (PPApplication.logEnabled()) {
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                            String result = sdf.format(alarmTime);
                            PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.setDelayAlarm", "startTime=" + result);
                        }*/

                        Intent editorIntent = new Intent(context, EditorProfilesActivity.class);
                        editorIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent infoPendingIntent = PendingIntent.getActivity(context, 1000, editorIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(alarmTime, infoPendingIntent);
                        alarmManager.setAlarmClock(clockInfo, pendingIntent);
                    } else {
                        long alarmTime = SystemClock.elapsedRealtime() + startApplicationDelay * 1000;

                        //if (android.os.Build.VERSION.SDK_INT >= 23)
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmTime, pendingIntent);
                        //else //if (android.os.Build.VERSION.SDK_INT >= 19)
                        //    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmTime, pendingIntent);
                        //else
                        //    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
                    }
                }
            }
        }
    }

    static void removeDelayAlarm(Context context, String runApplicationData)
    {
        int requestCode = hashData(runApplicationData);

        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                Context _context = context;
                if (PhoneProfilesService.getInstance() != null)
                    _context = PhoneProfilesService.getInstance();

                //Intent intent = new Intent(_context, RunApplicationWithDelayBroadcastReceiver.class);
                Intent intent = new Intent();
                intent.setAction(PhoneProfilesService.ACTION_RUN_APPLICATION_DELAY_BROADCAST_RECEIVER);
                //intent.setClass(context, RunApplicationWithDelayBroadcastReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, requestCode, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent != null) {
                    //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.removeDelayAlarm", "alarm found");

                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
        PPApplication.cancelWork(MainWorker.RUN_APPLICATION_WITH_DELAY_WORK_TAG +"_"+requestCode, false);
        PPApplication.elapsedAlarmsRunApplicationWithDelayWork.remove(MainWorker.RUN_APPLICATION_WITH_DELAY_WORK_TAG +"_"+requestCode);
        //PPApplication.logE("[HANDLER] RunApplicationWithDelayBroadcastReceiver.removeAlarm", "removed");
    }

    static void doWork(Context context, String profileName, String runApplicationData) {
        //PPApplication.logE("[HANDLER] RunApplicationWithDelayBroadcastReceiver.doWork", "runApplicationData="+runApplicationData);

        //final Context appContext = context.getApplicationContext();

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        Intent appIntent;
        PackageManager packageManager = context.getPackageManager();

        //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","profileName="+profileName);
        if (Application.isShortcut(runApplicationData)) {
            long shortcutId = Application.getShortcutId(runApplicationData);
            //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","shortcut - shortcutId="+shortcutId);
            if (shortcutId > 0) {
                Shortcut shortcut = DatabaseHandler.getInstance(context).getShortcut(shortcutId);
                if (shortcut != null) {
                    try {
                        appIntent = Intent.parseUri(shortcut._intent, 0);
                        if (appIntent != null) {
                            //noinspection TryWithIdenticalCatches
                            try {
                                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(appIntent);
                            } catch (ActivityNotFoundException ee) {
                                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","shortcut - ERROR (01)");
                                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_SHORTCUT, null,
                                        profileName, null, 0, "");
                            } catch (SecurityException e) {
                                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","shortcut - ERROR (02)");
                                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_SHORTCUT, null,
                                        profileName, null, 0, "");
                            } catch (Exception ee) {
                                PPApplication.recordException(ee);
                            }
                        } else {
                            //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","shortcut - ERROR (1)");
                            PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_SHORTCUT, null,
                                    profileName, null, 0, "");
                        }
                    } catch (Exception e) {
                        PPApplication.recordException(e);
                    }
                } else {
                    //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","shortcut - ERROR (2)");
                    PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_SHORTCUT, null,
                            profileName, null, 0, "");
                }
            } else {
                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","shortcut - ERROR (3)");
                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_SHORTCUT, null,
                        profileName, null, 0, "");
            }
        }
        else
        if (Application.isIntent(runApplicationData)) {
            long intentId = Application.getIntentId(runApplicationData);
            //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","intent - intentId="+intentId);
            if (intentId > 0) {
                PPIntent ppIntent = DatabaseHandler.getInstance(context).getIntent(intentId);
                if (ppIntent != null) {
                    appIntent = ApplicationEditorIntentActivityX.createIntent(ppIntent);
                    if (appIntent != null) {
                        if (ppIntent._intentType == 0) {
                            //noinspection TryWithIdenticalCatches
                            try {
                                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(appIntent);
                            } catch (ActivityNotFoundException ee) {
                                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","intent - ERROR (01)");
                                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_INTENT, null,
                                        profileName, null, 0, "");
                            } catch (SecurityException e) {
                                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","intent - ERROR (02)");
                                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_INTENT, null,
                                        profileName, null, 0, "");
                            } catch (Exception e) {
                                PPApplication.recordException(e);
                            }
                        }
                        else {
                            try {
                                context.sendBroadcast(appIntent);
                            } catch (Exception e) {
                                //PPApplication.recordException(e);
                            }
                        }
                    } else {
                        //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","intent - ERROR (1)");
                        PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_INTENT, null,
                                profileName, null, 0, "");
                    }
                } else {
                    //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","intent - ERROR (2)");
                    PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_INTENT, null,
                            profileName, null, 0, "");
                }
            } else {
                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","intent - ERROR (3)");
                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_INTENT, null,
                        profileName, null, 0, "");
            }
        } else {
            String packageName = Application.getPackageName(runApplicationData);
            appIntent = packageManager.getLaunchIntentForPackage(packageName);
            //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","application - appIntent="+appIntent);
            if (appIntent != null) {
                appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                //noinspection TryWithIdenticalCatches
                try {
                    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ee) {
                    //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","application - ERROR (01)");
                    PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_APPLICATION, null,
                            profileName, null, 0, "");
                } catch (SecurityException e) {
                    //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","application - ERROR (02)");
                    PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_APPLICATION, null,
                            profileName, null, 0, "");
                } catch (Exception e) {
                    PPApplication.recordException(e);
                }
            } else {
                //PPApplication.logE("RunApplicationWithDelayBroadcastReceiver.doWork","application - ERROR (1)");
                PPApplication.addActivityLog(context, PPApplication.ALTYPE_PROFILE_ERROR_RUN_APPLICATION_APPLICATION, null,
                        profileName, null, 0, "");
            }
        }
    }

}
