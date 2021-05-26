package sk.henrichg.phoneprofilesplus;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

// DO NOT REMOVE. MUST EXISTS !!!
@SuppressWarnings("unused")
public class GeofenceScanWorker extends Worker {

    //private final Context context;

    static final String WORK_TAG  = "GeofenceScannerJob";
    static final String WORK_TAG_SHORT  = "GeofenceScannerJobShort";

    public GeofenceScanWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        //this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
//        PPApplication.logE("[IN_WORKER]  GeofenceScanWorker.doWork", "xxxx");
        /*try {

            //PPApplication.logE("GeofenceScanWorker.doWork", "---------------------------------------- START");

            //CallsCounter.logCounter(context, "GeofenceScanWorker.doWork", "GeofenceScanWorker_doWork");

            if (!PPApplication.getApplicationStarted(true))
                // application is not started
                return Result.success();

            if (Event.isEventPreferenceAllowed(EventPreferencesLocation.PREF_EVENT_LOCATION_ENABLED, context).allowed !=
                    PreferenceAllowed.PREFERENCE_ALLOWED) {
                cancelWork(false);
                //if (PPApplication.logEnabled()) {
                //    PPApplication.logE("GeofenceScanWorker.doWork", "return - not allowed geofence scanning");
                //    PPApplication.logE("GeofenceScanWorker.doWork", "---------------------------------------- END");
                //}
                return Result.success();
            }

            //boolean isPowerSaveMode = PPApplication.isPowerSaveMode;
            boolean isPowerSaveMode = DataWrapper.isPowerSaveMode(context);
            if (isPowerSaveMode && ApplicationPreferences.applicationEventLocationUpdateInPowerSaveMode.equals("2")) {
                //PPApplication.logE("GeofenceScanWorker.doWork", "update in power save mode is not allowed");
                cancelWork(false);
                //PPApplication.logE("GeofenceScanWorker.doWork", "---------------------------------------- END");
                return Result.success();
            }

            if (Event.getGlobalEventsRunning()) {
                boolean geofenceScannerUpdatesStarted = false;
                synchronized (PPApplication.locationScannerMutex) {
                    if ((PhoneProfilesService.getInstance() != null) && (PhoneProfilesService.getInstance().getGeofencesScanner() != null)) {
                        LocationScanner scanner = PhoneProfilesService.getInstance().getGeofencesScanner();
                        if (scanner.mUpdatesStarted) {
                            //PPApplication.logE("GeofenceScanWorker.doWork", "location updates started - save to DB");

                            //if ((PhoneProfilesService.getInstance() != null) && PhoneProfilesService.getInstance().isGeofenceScannerStarted())
                            scanner.updateGeofencesInDB();

                            geofenceScannerUpdatesStarted = true;
                        }
                    }
                }

                if (geofenceScannerUpdatesStarted) {
                    //PPApplication.logE("GeofenceScanWorker.doWork", "location updates started - start EventsHandler");

                    // start events handler
                    //PPApplication.logE("****** EventsHandler.handleEvents", "START run - from=GeofenceScanWorker.doWork");

//                    PPApplication.logE("[EVENTS_HANDLER_CALL] GeofenceScanWorker.doWork", "sensorType=SENSOR_TYPE_LOCATION_SCANNER");
                    EventsHandler eventsHandler = new EventsHandler(context);
                    eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_LOCATION_SCANNER);

                    //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=GeofenceScanWorker.doWork");
                }
            }

            //PPApplication.logE("GeofenceScanWorker.doWork - handler", "schedule work");
            //scheduleWork(context.getApplicationContext(), false);
            OneTimeWorkRequest worker =
                    new OneTimeWorkRequest.Builder(MainWorker.class)
                            .addTag(MainWorker.SCHEDULE_LONG_INTERVAL_GEOFENCE_WORK_TAG)
                            //.setInitialDelay(200, TimeUnit.MILLISECONDS)
                            .build();
            try {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {

//                            //if (PPApplication.logEnabled()) {
//                            ListenableFuture<List<WorkInfo>> statuses;
//                            statuses = workManager.getWorkInfosForUniqueWork(MainWorker.SCHEDULE_LONG_INTERVAL_GEOFENCE_WORK_TAG);
//                            try {
//                                List<WorkInfo> workInfoList = statuses.get();
//                                PPApplication.logE("[TEST BATTERY] GeofenceScanWorker.doWork", "for=" + MainWorker.SCHEDULE_LONG_INTERVAL_GEOFENCE_WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                            } catch (Exception ignored) {
//                            }
//                            //}

                    workManager.enqueueUniqueWork(MainWorker.SCHEDULE_LONG_INTERVAL_GEOFENCE_WORK_TAG, ExistingWorkPolicy.REPLACE, worker);
                }
            } catch (Exception e) {
                PPApplication.recordException(e);
            }*/

            /*PPApplication.startHandlerThreadPPScanners();
            final Handler handler = new Handler(PPApplication.handlerThreadPPScanners.getLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PPApplication.logE("GeofenceScanWorker.doWork - handler", "schedule work");
                    scheduleWork(context, false, null, false);
                }
            }, 500);*/

            //PPApplication.logE("GeofenceScanWorker.doWork", "---------------------------------------- END");
            return Result.success();
        /*} catch (Exception e) {
            //Log.e("GeofenceScanWorker.doWork", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            //Handler _handler = new Handler(getApplicationContext().getMainLooper());
            //Runnable r = new Runnable() {
            //    public void run() {
            //        android.os.Process.killProcess(PPApplication.pid);
            //    }
            //};
            //_handler.postDelayed(r, 1000);
            return Result.failure();
        }*/
    }

    /*
    public void onStopped () {
        //PPApplication.logE("GeofenceScanWorker.onStopped", "xxx");

        //CallsCounter.logCounter(context, "GeofenceScanWorker.onStopped", "GeofenceScanWorker_onStopped");
    }

    private static void _scheduleWork(final Context context, boolean shortInterval) {
        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {

                    //PPApplication.logE("GeofenceScanWorker._scheduleWork", "---------------------------------------- START");

                    int interval;
                    synchronized (PPApplication.locationScannerMutex) {
                        //if (PPApplication.logEnabled()) {
                        //    if ((PhoneProfilesService.getInstance() != null) && PhoneProfilesService.getInstance().isLocationScannerStarted())
                        //        PPApplication.logE("GeofenceScanWorker._scheduleWork", "mUpdatesStarted=" + PhoneProfilesService.getInstance().getGeofencesScanner().mUpdatesStarted);
                        //    else {
                        //        PPApplication.logE("GeofenceScanWorker._scheduleWork", "scanner is not started");
                        //        PPApplication.logE("GeofenceScanWorker._scheduleWork", "PhoneProfilesService.getInstance()=" + PhoneProfilesService.getInstance());
                        //    }
                        //}

                        // look at LocationScanner:UPDATE_INTERVAL_IN_MILLISECONDS
                        //int updateDuration = 30;

                        if ((PhoneProfilesService.getInstance() != null) && PhoneProfilesService.getInstance().isLocationScannerStarted() &&
                                PhoneProfilesService.getInstance().getGeofencesScanner().mUpdatesStarted) {
                            interval = ApplicationPreferences.applicationEventLocationUpdateInterval * 60;
                            //boolean isPowerSaveMode = PPApplication.isPowerSaveMode;
                            boolean isPowerSaveMode = DataWrapper.isPowerSaveMode(context);
                            if (isPowerSaveMode && ApplicationPreferences.applicationEventLocationUpdateInPowerSaveMode.equals("1"))
                                interval = 2 * interval;
                            //interval = interval - updateDuration;
                        } else {
                            interval = 5;
                            shortInterval = true;
                        }

                        //PPApplication.logE("GeofenceScanWorker._scheduleWork", "interval=" + interval);
                    }

                    if (!shortInterval) {
                        //PPApplication.logE("GeofenceScanWorker._scheduleWork", "delay work");
                        //int keepResultsDelay = (interval * 5);
                        //if (keepResultsDelay < PPApplication.WORK_PRUNE_DELAY)
                        //    keepResultsDelay = PPApplication.WORK_PRUNE_DELAY;
                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(GeofenceScanWorker.class)
                                .setInitialDelay(interval, TimeUnit.SECONDS)
                                .addTag(GeofenceScanWorker.WORK_TAG)
                                .build();

//                        //if (PPApplication.logEnabled()) {
//                        ListenableFuture<List<WorkInfo>> statuses;
//                        statuses = workManager.getWorkInfosForUniqueWork(GeofenceScanWorker.WORK_TAG);
//                        try {
//                            List<WorkInfo> workInfoList = statuses.get();
//                            PPApplication.logE("[TEST BATTERY] GeofenceScanWorker._scheduleWork", "for=" + GeofenceScanWorker.WORK_TAG + " workInfoList.size()=" + workInfoList.size());
//                        } catch (Exception ignored) {
//                        }
//                        //}

                        workManager.enqueueUniqueWork(GeofenceScanWorker.WORK_TAG, ExistingWorkPolicy.REPLACE, workRequest);
                    } else {
                        //PPApplication.logE("GeofenceScanWorker._scheduleWork", "start now work");
                        //waitForFinish();
                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(GeofenceScanWorker.class)
                                .addTag(GeofenceScanWorker.WORK_TAG_SHORT)
                                .build();

//                        //if (PPApplication.logEnabled()) {
//                        ListenableFuture<List<WorkInfo>> statuses;
//                        statuses = workManager.getWorkInfosForUniqueWork(GeofenceScanWorker.WORK_TAG_SHORT);
//                        try {
//                            List<WorkInfo> workInfoList = statuses.get();
//                            PPApplication.logE("[TEST BATTERY] GeofenceScanWorker._scheduleWork", "for=" + GeofenceScanWorker.WORK_TAG_SHORT + " workInfoList.size()=" + workInfoList.size());
//                        } catch (Exception ignored) {
//                        }
//                        //}

                        workManager.enqueueUniqueWork(GeofenceScanWorker.WORK_TAG_SHORT, ExistingWorkPolicy.REPLACE, workRequest);
                    }

                    //PPApplication.logE("GeofenceScanWorker._scheduleWork", "---------------------------------------- END");
                }
            }
        } catch (Exception e) {
            //Log.e("GeofenceScanWorker._scheduleWork", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    static void scheduleWork(final Context context, final boolean shortInterval) {
        //PPApplication.logE("GeofenceScanWorker.scheduleWork", "startScanning="+startScanning);

        PPApplication.startHandlerThreadPPScanners();
        final Handler handler = new Handler(PPApplication.handlerThreadPPScanners.getLooper());
        //if (shortInterval) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPPScanners", "START run - from=GeofenceScanWorker.scheduleWork" + " shortInterval="+shortInterval);
                    _scheduleWork(context, shortInterval);
                }
            });
        //}
        //else {
        //    handler.postDelayed(new Runnable() {
        //        @Override
        //        public void run() {
        //            PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPPScanners", "START run - from=GeofenceScanWorker.scheduleWork");
        //            _scheduleWork(context, shortInterval);
        //        }
        //    }, 500);
        //}
        //else
        //    PPApplication.logE("GeofenceScanWorker.scheduleWork", "scanner is not started");
    }

    private static void _cancelWork() {
        if (isWorkScheduled(false) || isWorkScheduled(true)) {
            try {
                waitForFinish(false);
                waitForFinish(true);

                PPApplication.cancelWork(WORK_TAG);
                PPApplication.cancelWork(WORK_TAG_SHORT);

                //PPApplication.logE("GeofenceScanWorker._cancelWork", "CANCELED");

            } catch (Exception e) {
                //Log.e("GeofenceScanWorker._cancelWork", Log.getStackTraceString(e));
                PPApplication.recordException(e);
            }
        }
    }

    private static void waitForFinish(boolean shortWork) {
        if (!isWorkRunning(shortWork)) {
            //PPApplication.logE("GeofenceScanWorker.waitForFinish", "NOT RUNNING");
            return;
        }

        try {
            if (PPApplication.getApplicationStarted(true)) {
                WorkManager workManager = PPApplication.getWorkManagerInstance();
                if (workManager != null) {

                    //PPApplication.logE("GeofenceScanWorker.waitForFinish", "START WAIT FOR FINISH");
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
                            //PPApplication.logE("[TEST BATTERY] GeofenceScanWorker.waitForFinish", "workInfoList.size()="+workInfoList.size());
                            for (WorkInfo workInfo : workInfoList) {
                                WorkInfo.State state = workInfo.getState();
                                if (!state.isFinished()) {
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
                            //PPApplication.logE("GeofenceScanWorker.waitForFinish", "FINISHED");
                            break;
                        }

                        PPApplication.sleep(500);
                    } while (SystemClock.uptimeMillis() - start < 10 * 1000);

                    //PPApplication.logE("GeofenceScanWorker.waitForFinish", "END WAIT FOR FINISH");
                }
            }
        } catch (Exception e) {
            //Log.e("GeofenceScanWorker.waitForFinish", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    static void cancelWork(final boolean useHandler) {
        //PPApplication.logE("GeofenceScanWorker.cancelWork", "xxx");

        if (useHandler) {
            PPApplication.startHandlerThreadPPScanners();
            final Handler handler = new Handler(PPApplication.handlerThreadPPScanners.getLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadPPScanners", "START run - from=GeofenceScanWorker.cancelWork");
                    _cancelWork();
                }
            });
        }
        else {
            _cancelWork();
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
                        //PPApplication.logE("[TEST BATTERY] GeofenceScanWorker.isWorkRunning", "workInfoList.size()="+workInfoList.size());
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
            //Log.e("GeofenceScanWorker.isWorkRunning", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            return false;
        }
    }

    static boolean isWorkScheduled(boolean shortWork) {
        //PPApplication.logE("GeofenceScanWorker.isWorkScheduled", "xxx");
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
                        //PPApplication.logE("[TEST BATTERY] GeofenceScanWorker.isWorkScheduled", "workInfoList.size()="+workInfoList.size());
                        boolean running = false;
                        for (WorkInfo workInfo : workInfoList) {
                            WorkInfo.State state = workInfo.getState();
                            running = (state == WorkInfo.State.RUNNING) || (state == WorkInfo.State.ENQUEUED);
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
            //Log.e("GeofenceScanWorker.isWorkScheduled", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            return false;
        }
    }
    */

}
