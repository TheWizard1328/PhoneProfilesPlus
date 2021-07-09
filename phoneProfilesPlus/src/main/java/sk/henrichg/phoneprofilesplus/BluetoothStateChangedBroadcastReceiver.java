package sk.henrichg.phoneprofilesplus;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;

public class BluetoothStateChangedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        PPApplication.logE("[IN_BROADCAST] BluetoothStateChangedBroadcastReceiver.onReceive", "xxx");

        //CallsCounter.logCounter(context, "BluetoothStateChangedBroadcastReceiver.onReceive", "BluetoothStateChangedBroadcastReceiver_onReceive");

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        if (intent == null)
            return;

        String action = intent.getAction();
        if ((action != null) && action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            // BluetoothStateChangedBroadcastReceiver

            final int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

            PPApplication.startHandlerThreadBroadcast(/*"BluetoothStateChangedBroadcastReceiver.onReceive"*/);
            final Handler __handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
            __handler.post(new PPApplication.PPHandlerThreadRunnable(context.getApplicationContext()) {
                @Override
                public void run() {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=BluetoothStateChangedBroadcastReceiver.onReceive");

                    Context appContext= appContextWeakRef.get();

                    if (appContext != null) {
                        PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                        PowerManager.WakeLock wakeLock = null;
                        try {
                            if (powerManager != null) {
                                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":BluetoothStateChangedBroadcastReceiver_onReceive");
                                wakeLock.acquire(10 * 60 * 1000);
                            }

                            // remove connected devices list
                            if (bluetoothState == BluetoothAdapter.STATE_OFF) {
                                BluetoothConnectionBroadcastReceiver.clearConnectedDevices(appContext, false);
                                BluetoothConnectionBroadcastReceiver.saveConnectedDevices(appContext);
                            }

                            if (Event.getGlobalEventsRunning()) {
                                //PPApplication.logE("@@@ BluetoothStateChangedBroadcastReceiver.onReceive", "state=" + bluetoothState);

                                if ((bluetoothState == BluetoothAdapter.STATE_ON) || (bluetoothState == BluetoothAdapter.STATE_OFF)) {

                                    if (bluetoothState == BluetoothAdapter.STATE_ON) {
                                        //if ((!dataWrapper.getIsManualProfileActivation()) || PPApplication.getForceOneBluetoothScan(appContext))
                                        //{
                                        if (ApplicationPreferences.prefEventBluetoothScanRequest) {
                                            //PPApplication.logE("@@@ BluetoothStateChangedBroadcastReceiver.onReceive", "start classic scan");
                                            BluetoothScanWorker.startCLScan(appContext);
                                        } else if (ApplicationPreferences.prefEventBluetoothLEScanRequest) {
                                            //PPApplication.logE("@@@ BluetoothStateChangedBroadcastReceiver.onReceive", "start LE scan");
                                            BluetoothScanWorker.startLEScan(appContext);
                                        } else if (!(ApplicationPreferences.prefEventBluetoothWaitForResult ||
                                                ApplicationPreferences.prefEventBluetoothLEWaitForResult)) {
                                            // refresh bounded devices
                                            BluetoothScanWorker.fillBoundedDevicesList(appContext);
                                        }
                                        //}
                                    }

                                    if (!(ApplicationPreferences.prefEventBluetoothScanRequest ||
                                            ApplicationPreferences.prefEventBluetoothLEScanRequest ||
                                            ApplicationPreferences.prefEventBluetoothWaitForResult ||
                                            ApplicationPreferences.prefEventBluetoothLEWaitForResult ||
                                            ApplicationPreferences.prefEventBluetoothEnabledForScan)) {

                                        // start events handler
                                        //PPApplication.logE("****** EventsHandler.handleEvents", "START run - from=BluetoothStateChangedBroadcastReceiver.onReceive (1)");

//                                    PPApplication.logE("[EVENTS_HANDLER_CALL] BluetoothStateChangedBroadcastReceiver.onReceive", "sensorType=SENSOR_TYPE_RADIO_SWITCH");
                                        EventsHandler eventsHandler = new EventsHandler(appContext);
                                        eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_RADIO_SWITCH);

                                        //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=BluetoothStateChangedBroadcastReceiver.onReceive (1)");

                                        // start events handler
                                        //PPApplication.logE("****** EventsHandler.handleEvents", "START run - from=BluetoothStateChangedBroadcastReceiver.onReceive (2)");

//                                    PPApplication.logE("[EVENTS_HANDLER_CALL] BluetoothStateChangedBroadcastReceiver.onReceive", "sensorType=SENSOR_TYPE_BLUETOOTH_STATE");
                                        eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_BLUETOOTH_STATE);

                                        //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=BluetoothStateChangedBroadcastReceiver.onReceive (2)");
                                    }

                                }
                            }

                            //PPApplication.logE("PPApplication.startHandlerThread", "END run - from=BluetoothStateChangedBroadcastReceiver.onReceive");
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
}
