package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BluetoothLEScanBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
//        PPApplication.logE("[IN_BROADCAST] BluetoothLEScanBroadcastReceiver.onReceive", "xxx");

        //CallsCounter.logCounter(context, "BluetoothLEScanBroadcastReceiver.onReceive", "BluetoothLEScanBroadcastReceiver_onReceive");

        final Context appContext = context.getApplicationContext();

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        BluetoothScanWorker.fillBoundedDevicesList(appContext);

        final int forceOneScan = ApplicationPreferences.prefForceOneBluetoothLEScan;

        if (Event.getGlobalEventsRunning() || (forceOneScan == BluetoothScanner.FORCE_ONE_SCAN_FROM_PREF_DIALOG))
        {
            //if (scanStarted) {
            //PPApplication.logE("@@@ BluetoothLEScanBroadcastReceiver.onReceive", "xxx");


            BluetoothScanWorker.setWaitForLEResults(appContext, false);
            BluetoothScanner.setForceOneLEBluetoothScan(appContext, BluetoothScanner.FORCE_ONE_SCAN_DISABLED);

            if (forceOneScan != BluetoothScanner.FORCE_ONE_SCAN_FROM_PREF_DIALOG)// not start service for force scan
            {
                Data workData = new Data.Builder()
                        .putString(PhoneProfilesService.EXTRA_SENSOR_TYPE, EventsHandler.SENSOR_TYPE_BLUETOOTH_SCANNER)
                        .build();

                OneTimeWorkRequest worker =
                        new OneTimeWorkRequest.Builder(MainWorker.class)
                                .addTag(MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG)
                                .setInputData(workData)
                                .setInitialDelay(5, TimeUnit.SECONDS)
                                //.keepResultsForAtLeast(PPApplication.WORK_PRUNE_DELAY_MINUTES, TimeUnit.MINUTES)
                                .build();
                try {
                    if (PPApplication.getApplicationStarted(true)) {
                        WorkManager workManager = PPApplication.getWorkManagerInstance();
                        if (workManager != null) {

//                            //if (PPApplication.logEnabled()) {
//                            ListenableFuture<List<WorkInfo>> statuses;
//                            statuses = workManager.getWorkInfosForUniqueWork(MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG);
//                            try {
//                                List<WorkInfo> workInfoList = statuses.get();
//                                PPApplication.logE("[TEST BATTERY] BluetoothLEScanBroadcastReceiver.onReceive", "for=" + MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                            } catch (Exception ignored) {
//                            }
//                            //}

                            //workManager.enqueue(worker);
//                            PPApplication.logE("[WORKER_CALL] BluetoothLEScanBroadcastReceiver.onReceive", "xxx");
                            workManager.enqueueUniqueWork(MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG, ExistingWorkPolicy./*APPEND_OR_*/REPLACE, worker);
                        }
                    }
                } catch (Exception e) {
                    PPApplication.recordException(e);
                }
            }
            //}

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
                            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":BluetoothLEScanBroadcastReceiver_onReceive");
                            wakeLock.acquire(10 * 60 * 1000);
                        }

                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=BluetoothLEScanBroadcastReceiver.onReceive.1");

                        //boolean scanStarted = (BluetoothScanWorker.getWaitForLEResults(appContext));

                        //if (scanStarted) {
                            //PPApplication.logE("@@@ BluetoothLEScanBroadcastReceiver.onReceive", "xxx");


                            BluetoothScanWorker.fillBoundedDevicesList(appContext);

                            BluetoothScanWorker.setWaitForLEResults(appContext, false);

                            BluetoothScanner.setForceOneLEBluetoothScan(appContext, BluetoothScanner.FORCE_ONE_SCAN_DISABLED);

                            if (forceOneScan != BluetoothScanner.FORCE_ONE_SCAN_FROM_PREF_DIALOG)// not start service for force scan
                            {
                                Data workData = new Data.Builder()
                                        .putString(PhoneProfilesService.EXTRA_SENSOR_TYPE, EventsHandler.SENSOR_TYPE_BLUETOOTH_SCANNER)
                                        .build();

                                OneTimeWorkRequest worker =
                                        new OneTimeWorkRequest.Builder(MainWorker.class)
                                                .addTag(MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG)
                                                .setInputData(workData)
                                                .setInitialDelay(5, TimeUnit.SECONDS)
                                                //.keepResultsForAtLeast(PPApplication.WORK_PRUNE_DELAY_MINUTES, TimeUnit.MINUTES)
                                                .build();
                                try {
                                    if (PPApplication.getApplicationStarted(true)) {
                                        WorkManager workManager = PPApplication.getWorkManagerInstance();
                                        if (workManager != null) {

//                                            //if (PPApplication.logEnabled()) {
//                                            ListenableFuture<List<WorkInfo>> statuses;
//                                            statuses = workManager.getWorkInfosForUniqueWork(MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG);
//                                            try {
//                                                List<WorkInfo> workInfoList = statuses.get();
//                                                PPApplication.logE("[TEST BATTERY] BluetoothLEScanBroadcastReceiver.onReceive", "for=" + MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                                            } catch (Exception ignored) {
//                                            }
//                                            //}

                                            //workManager.enqueue(worker);
                                            workManager.enqueueUniqueWork(MainWorker.HANDLE_EVENTS_BLUETOOTH_LE_SCANNER_WORK_TAG, ExistingWorkPolicy.REPLACE, worker);
                                        }
                                    }
                                } catch (Exception e) {
                                    PPApplication.recordException(e);
                                }
                            }
                        //}

                        //PPApplication.logE("PPApplication.startHandlerThread", "END run - from=BluetoothLEScanBroadcastReceiver.onReceive.1");
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
        }
    }

}
