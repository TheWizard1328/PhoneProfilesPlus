package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

class BluetoothScanJob extends Job {

    static final String JOB_TAG  = "BluetoothScanJob";
    static final String JOB_TAG_SHORT  = "BluetoothScanJob_short";
    private static BluetoothScanJobBroadcastReceiver broadcastReceiver = new BluetoothScanJobBroadcastReceiver();
    private static boolean isBroadcastSend = false;

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        PPApplication.logE("BluetoothScanJob.onRunJob", "xxx");

        sendBroadcast(getContext());
        return Result.SUCCESS;
    }

    static void scheduleJob(Context context, boolean shortInterval, boolean forScreenOn) {
        PPApplication.logE("BluetoothScanJob.scheduleJob", "shortInterval="+shortInterval);

        if (Event.isEventPreferenceAllowed(EventPreferencesBluetooth.PREF_EVENT_BLUETOOTH_ENABLED, context)
                == PPApplication.PREFERENCE_ALLOWED) {
            JobRequest.Builder jobBuilder;
            if (!shortInterval) {
                JobManager jobManager = JobManager.instance();
                jobManager.cancelAllForTag(JOB_TAG_SHORT);

                int interval = ApplicationPreferences.applicationEventBluetoothScanInterval(context);
                boolean isPowerSaveMode = DataWrapper.isPowerSaveMode();
                if (isPowerSaveMode && ApplicationPreferences.applicationEventBluetoothScanInPowerSaveMode(context).equals("1"))
                    interval = 2 * interval;

                jobBuilder = new JobRequest.Builder(JOB_TAG);

                if (TimeUnit.MINUTES.toMillis(interval) < JobRequest.MIN_INTERVAL) {
                    jobManager.cancelAllForTag(JOB_TAG);
                    jobBuilder.setExact(TimeUnit.MINUTES.toMillis(interval));
                } else {
                    int requestsForTagSize = jobManager.getAllJobRequestsForTag(JOB_TAG).size();
                    PPApplication.logE("BluetoothScanJob.scheduleJob", "requestsForTagSize=" + requestsForTagSize);
                    if (requestsForTagSize == 0) {
                        if (TimeUnit.MINUTES.toMillis(interval) < JobRequest.MIN_INTERVAL)
                            jobBuilder.setPeriodic(JobRequest.MIN_INTERVAL);
                        else
                            jobBuilder.setPeriodic(TimeUnit.MINUTES.toMillis(interval));
                    } else
                        return;
                }
            } else {
                cancelJob();
                jobBuilder = new JobRequest.Builder(JOB_TAG_SHORT);
                if (forScreenOn)
                    jobBuilder.setExact(TimeUnit.SECONDS.toMillis(5));
                else
                    jobBuilder.setExact(TimeUnit.SECONDS.toMillis(5));
            }

            PPApplication.logE("BluetoothScanJob.scheduleJob", "build and schedule");

            jobBuilder
                    .setPersisted(false)
                    .setUpdateCurrent(false) // don't update current, it would cancel this currently running job
                    .build()
                    .schedule();
        }
        else
            PPApplication.logE("BluetoothScanJob.scheduleJob","BluetoothHardware=false");
    }

    static void cancelJob() {
        PPApplication.logE("BluetoothScanJob.cancelJob", "xxx");

        JobManager jobManager = JobManager.instance();
        jobManager.cancelAllForTag(JOB_TAG_SHORT);
        jobManager.cancelAllForTag(JOB_TAG);
    }

    static boolean isJobScheduled() {
        PPApplication.logE("BluetoothScanJob.isJobScheduled", "xxx");

        JobManager jobManager = JobManager.instance();
        return (jobManager.getAllJobRequestsForTag(JOB_TAG).size() != 0) ||
                (jobManager.getAllJobRequestsForTag(JOB_TAG_SHORT).size() != 0);
    }

    static void sendBroadcast(Context context)
    {
        PPApplication.logE("BluetoothScanJob.sendBroadcast", "xxx");

        if (!isBroadcastSend) {
            isBroadcastSend = true;
            LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, new IntentFilter("BluetoothScanJobBroadcastReceiver"));
            Intent intent = new Intent("BluetoothScanJobBroadcastReceiver");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    static void unregisterReceiver(Context context) {
        PPApplication.logE("BluetoothScanJob.unregisterReceiver", "xxx");

        LocalBroadcastManager.getInstance(context.getApplicationContext()).unregisterReceiver(broadcastReceiver);
        isBroadcastSend = false;
    }

}