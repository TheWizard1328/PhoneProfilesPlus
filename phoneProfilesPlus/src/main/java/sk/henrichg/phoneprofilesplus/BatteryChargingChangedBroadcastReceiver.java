package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;

public class BatteryChargingChangedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        PPApplication.logE("[IN_BROADCAST] BatteryChargingChangedBroadcastReceiver.onReceive", "xxx");

        //CallsCounter.logCounter(context, "BatteryChargingChangedBroadcastReceiver.onReceive", "BatteryChargingChangedBroadcastReceiver_onReceive");
        //CallsCounter.logCounterNoInc(context, "BatteryChargingChangedBroadcastReceiver.onReceive->action="+intent.getAction(), "BatteryChargingChangedBroadcastReceiver_onReceive");

        final Context appContext = context.getApplicationContext();

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        String action = intent.getAction();
//        PPApplication.logE("[IN_BROADCAST] BatteryChargingChangedBroadcastReceiver.onReceive", "action=" + action);

        if (action == null)
            return;

//        PPApplication.logE("[IN_BROADCAST] BatteryChargingChangedBroadcastReceiver.onReceive", "isCharging="+PPApplication.isCharging);
//        PPApplication.logE("[IN_BROADCAST] BatteryChargingChangedBroadcastReceiver.onReceive", "plugged="+PPApplication.plugged);

        boolean _isCharging = false;
        //int _plugged = -1;

        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            //_plugged = -BatteryManager.BATTERY_STATUS_CHARGING;
            _isCharging = true;
        }
        /*else
        if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            //_plugged = -BatteryManager.BATTERY_STATUS_NOT_CHARGING;
            _isCharging = false;
        }*/

        if ((PPApplication.isCharging != _isCharging) /*||
            ((_plugged != -1) && (PPApplication.plugged != _plugged))*/) {
//            PPApplication.logE("[IN_BROADCAST] BatteryChargingChangedBroadcastReceiver.onReceive", "---- state changed");

            PPApplication.isCharging = _isCharging;

            //if (_plugged != -1)
            //    PPApplication.plugged = _plugged;

            /*
            boolean oldIsPowerSaveMode = PPApplication.isPowerSaveMode;
            // restart scanners when any is enabled
            // required for reschedule workers for power save mode
            String applicationPowerSaveModeInternal = ApplicationPreferences.applicationPowerSaveModeInternal;
            if (applicationPowerSaveModeInternal.equals("1") || applicationPowerSaveModeInternal.equals("2")) {
                // power save mode is configured for control battery percentage

                boolean isPowerSaveMode = false;
                if (!PPApplication.isCharging) {
                    if (applicationPowerSaveModeInternal.equals("1") && (PPApplication.batteryPct <= 5))
                        isPowerSaveMode = true;
                    if (applicationPowerSaveModeInternal.equals("2") && (PPApplication.batteryPct <= 15))
                        isPowerSaveMode = true;
                }
                PPApplication.isPowerSaveMode = isPowerSaveMode;

                //if (PPApplication.logEnabled()) {
                //    PPApplication.logE("[TEST BATTERY] BatteryChargingChangedBroadcastReceiver.onReceive", "oldIsPowerSaveMode=" + oldIsPowerSaveMode);
                //    PPApplication.logE("[TEST BATTERY] BatteryChargingChangedBroadcastReceiver.onReceive", "isPowerSaveMode=" + isPowerSaveMode);
                //}

                if (PPApplication.isPowerSaveMode != oldIsPowerSaveMode) {
                    boolean restart = false;
                    if (!PPApplication.isScreenOn) {
                        // screen is off
                        // test also if scanner is enabled only during screen on
                        if (ApplicationPreferences.applicationEventLocationEnableScanning &&
                                ApplicationPreferences.applicationEventLocationScanOnlyWhenScreenIsOn)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventWifiEnableScanning &&
                                ApplicationPreferences.applicationEventWifiScanOnlyWhenScreenIsOn)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventBluetoothEnableScanning &&
                                ApplicationPreferences.applicationEventBluetoothScanOnlyWhenScreenIsOn)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventMobileCellEnableScanning &&
                                ApplicationPreferences.applicationEventMobileCellScanOnlyWhenScreenIsOn)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventOrientationEnableScanning &&
                                ApplicationPreferences.applicationEventOrientationScanOnlyWhenScreenIsOn)
                            restart = true;
                    } else {
                        if (ApplicationPreferences.applicationEventLocationEnableScanning)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventWifiEnableScanning)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventBluetoothEnableScanning)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventMobileCellEnableScanning)
                            restart = true;
                        else if (ApplicationPreferences.applicationEventOrientationEnableScanning)
                            restart = true;
                    }
                    if (restart) {
                        //PPApplication.logE("[****] BatteryChargingChangedBroadcastReceiver.onReceive", "restartAllScanners");
                        //PPApplication.logE("[RJS] BatteryChargingChangedBroadcastReceiver.onReceive", "restart all scanners");
                        // for screenOn=true -> used only for Location scanner - start scan with GPS On
                        PPApplication.restartAllScanners(appContext, true);
                    }
                }
            }
            */

            if (Event.getGlobalEventsRunning()) {
                PPApplication.startHandlerThreadBroadcast(/*"BatteryChargingChangedBroadcastReceiver.onReceive"*/);
                final Handler handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
                handler.post(() -> {
//                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=BatteryChargingChangedBroadcastReceiver.onReceive");

                    PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = null;
                    try {
                        if (powerManager != null) {
                            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":BatteryChargingChangedBroadcastReceiver_onReceive");
                            wakeLock.acquire(10 * 60 * 1000);
                        }

//                            PPApplication.logE("BatteryChargingChangedBroadcastReceiver.onReceive", "isCharging="+PPApplication.isCharging);
//                            PPApplication.logE("BatteryChargingChangedBroadcastReceiver.onReceive", "plugged="+PPApplication.plugged);

                        // start events handler
//                            PPApplication.logE("[EVENTS_HANDLER_CALL] BatteryChargingChangedBroadcastReceiver.onReceive", "sensorType=SENSOR_TYPE_BATTERY");
                        EventsHandler eventsHandler = new EventsHandler(appContext);
                        eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_BATTERY);

                        //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=BatteryChargingChangedBroadcastReceiver.onReceive");
                    } catch (Exception e) {
//                            PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", Log.getStackTraceString(e));
                        PPApplication.recordException(e);
                    } finally {
                        if ((wakeLock != null) && wakeLock.isHeld()) {
                            try {
                                wakeLock.release();
                            } catch (Exception ignored) {}
                        }
                    }
                });
            }
        }
    }

}
