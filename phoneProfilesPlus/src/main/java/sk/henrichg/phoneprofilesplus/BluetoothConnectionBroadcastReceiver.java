package sk.henrichg.phoneprofilesplus;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BluetoothConnectionBroadcastReceiver extends BroadcastReceiver {

    private static List<BluetoothDeviceData> connectedDevices = null;

    @Override
    public void onReceive(Context context, Intent intent) {
//        PPApplication.logE("[IN_BROADCAST] BluetoothConnectionBroadcastReceiver.onReceive", "xxx");

        //CallsCounter.logCounter(context, "BluetoothConnectionBroadcastReceiver.onReceive", "BluetoothConnectionBroadcastReceiver_onReceive");

        if (!PPApplication.getApplicationStarted(true))
            // application is not started
            return;

        if (intent == null)
            return;

        final String action = intent.getAction();
        if (action == null)
            return;

        //PPApplication.logE("BluetoothConnectionBroadcastReceiver.onReceive", "action=" + action);

        if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED) ||
                action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED) ||
                action.equals(BluetoothDevice.ACTION_NAME_CHANGED)/* ||
                action.equals(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)*/) {
            // BluetoothConnectionBroadcastReceiver

            final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device == null)
                return;

            //final boolean connected = action.equals(BluetoothDevice.ACTION_ACL_CONNECTED);
            final String newName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);

            if (action.equals(BluetoothDevice.ACTION_NAME_CHANGED)) {
                if (newName == null) {
                    //PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "action=" + action + " with newName == null");
                    return;
                }
                try {
                    if (newName.equals(device.getName())) {
                        //PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "action=" + action + " with not changed name");
                        return;
                    }
                } catch (SecurityException e) {
                    return;
                }
            }

            PPApplication.startHandlerThreadBroadcast(/*"BluetoothConnectionBroadcastReceiver.onReceive"*/);
            final Handler __handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
            __handler.post(new PPHandlerThreadRunnable(context.getApplicationContext(), device) {
                @Override
                public void run() {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=BluetoothConnectionBroadcastReceiver.onReceive");

                    Context appContext= appContextWeakRef.get();
                    BluetoothDevice device = deviceWeakRef.get();

                    if ((appContext != null) && (device != null)) {
                        PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                        PowerManager.WakeLock wakeLock = null;
                        try {
                            if (powerManager != null) {
                                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":BluetoothConnectionBroadcastReceiver_onReceive");
                                wakeLock.acquire(10 * 60 * 1000);
                            }

                            getConnectedDevices(appContext);

                            //if (device != null) {

                            //PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "action=" + action);

                            try {
                                //if (!action.equals(BluetoothDevice.ACTION_NAME_CHANGED)) {
                            /*if (PPApplication.logEnabled()) {
                                PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "connected=" + connected);
                                PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "device.getName()=" + device.getName());
                                PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "device.getAddress()=" + device.getAddress());
                            }*/
                                //}

                                switch (action) {
                                    case BluetoothDevice.ACTION_ACL_CONNECTED:
                                        addConnectedDevice(device);
                                        break;
                                    case BluetoothDevice.ACTION_NAME_CHANGED:
                                        //noinspection ConstantConditions
                                        if (newName != null) {
                                            //PPApplication.logE("$$$ BluetoothConnectionBroadcastReceiver.onReceive", "newName=" + newName);
                                            changeDeviceName(device, newName);
                                        }
                                        break;
                                    default:
                                        removeConnectedDevice(device);
                                        break;
                                }
                            } catch (Exception e) {
                                PPApplication.recordException(e);
                            }

                            saveConnectedDevices(appContext);


                            if (Event.getGlobalEventsRunning()) {

                                //if (lastState != currState)
                                //{

                                if (!(ApplicationPreferences.prefEventBluetoothScanRequest ||
                                        ApplicationPreferences.prefEventBluetoothLEScanRequest ||
                                        ApplicationPreferences.prefEventBluetoothWaitForResult ||
                                        ApplicationPreferences.prefEventBluetoothLEWaitForResult ||
                                        ApplicationPreferences.prefEventBluetoothEnabledForScan)) {
                                    // bluetooth is not scanned

                                    //PPApplication.logE("@@@ BluetoothConnectionBroadcastReceiver.onReceive", "start EventsHandler");

                            /*DataWrapper dataWrapper = new DataWrapper(appContext, false, false, 0);
                            boolean bluetoothEventsExists = dataWrapper.getDatabaseHandler().getTypeEventsCount(DatabaseHandler.ETYPE_BLUETOOTHCONNECTED) > 0;
                            dataWrapper.invalidateDataWrapper();

                            if (bluetoothEventsExists)
                            {
                            */
                                    // start events handler
                                    //PPApplication.logE("****** EventsHandler.handleEvents", "START run - from=BluetoothConnectionBroadcastReceiver.onReceive");

//                                    PPApplication.logE("[EVENTS_HANDLER_CALL] BluetoothConnectionBroadcastReceiver.onReceive", "sensorType=SENSOR_TYPE_RADIO_SWITCH");
                                    EventsHandler eventsHandler = new EventsHandler(appContext);
                                    eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_RADIO_SWITCH);

//                                    PPApplication.logE("[EVENTS_HANDLER_CALL] BluetoothConnectionBroadcastReceiver.onReceive", "sensorType=SENSOR_TYPE_BLUETOOTH_CONNECTION");
                                    eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_BLUETOOTH_CONNECTION);

                                    //PPApplication.logE("****** EventsHandler.handleEvents", "END run - from=BluetoothConnectionBroadcastReceiver.onReceive");

                                    //}
                                } //else
                                //    PPApplication.logE("@@@ BluetoothConnectionBroadcastReceiver.onReceive", "not start EventsHandler, scanner is running");
                                //}
                            }
                            //}

                            //PPApplication.logE("PPApplication.startHandlerThread", "END run - from=BluetoothConnectionBroadcastReceiver.onReceive");
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

    private static final String CONNECTED_DEVICES_COUNT_PREF = "count";
    private static final String CONNECTED_DEVICES_DEVICE_PREF = "device";

    static void getConnectedDevices(Context context)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {

            if (connectedDevices == null)
                connectedDevices = new ArrayList<>();

            connectedDevices.clear();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.BLUETOOTH_CONNECTED_DEVICES_PREFS_NAME, Context.MODE_PRIVATE);

            int count = preferences.getInt(CONNECTED_DEVICES_COUNT_PREF, 0);

            Gson gson = new Gson();

            int gmtOffset = 0; //TimeZone.getDefault().getRawOffset();
            for (int i = 0; i < count; i++) {
                String json = preferences.getString(CONNECTED_DEVICES_DEVICE_PREF + i, "");
                if (!json.isEmpty()) {
                    BluetoothDeviceData device = gson.fromJson(json, BluetoothDeviceData.class);

                    /*if (PPApplication.logEnabled()) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                        if (PPApplication.logEnabled()) {
                            PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "device.name=" + device.getName());
                            PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "device.address=" + device.getAddress());
                            PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "device.timestamp=" + sdf.format(device.timestamp));
                        }
                    }*/
                    //long bootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime() - gmtOffset;
                    Calendar calendar = Calendar.getInstance();
                    long bootTime = calendar.getTimeInMillis() - SystemClock.elapsedRealtime() - gmtOffset;
                    /*if (PPApplication.logEnabled()) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                        PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "bootTime=" + sdf.format(bootTime));
                    }*/

                    if (device.timestamp >= bootTime) {
                        //PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "added");
                        connectedDevices.add(device);
                    }
                    //else
                    //    PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "not added");
                }
            }

            //PPApplication.logE("BluetoothConnectionBroadcastReceiver.getConnectedDevices", "connectedDevices.size()=" + connectedDevices.size());
        }
    }

    static void saveConnectedDevices(Context context)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {

            if (connectedDevices == null)
                connectedDevices = new ArrayList<>();

            SharedPreferences preferences = context.getSharedPreferences(PPApplication.BLUETOOTH_CONNECTED_DEVICES_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            editor.putInt(CONNECTED_DEVICES_COUNT_PREF, connectedDevices.size());

            Gson gson = new Gson();

            for (int i = 0; i < connectedDevices.size(); i++) {
                String json = gson.toJson(connectedDevices.get(i));
                editor.putString(CONNECTED_DEVICES_DEVICE_PREF + i, json);
            }

            editor.apply();
        }
    }

    private static void addConnectedDevice(BluetoothDevice device)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE("BluetoothConnectionBroadcastReceiver.addConnectedDevice", "device.name=" + device.getName());
                PPApplication.logE("BluetoothConnectionBroadcastReceiver.addConnectedDevice", "device.address=" + device.getAddress());
            }*/
            boolean found = false;
            for (BluetoothDeviceData _device : connectedDevices) {
                if (_device.address.equals(device.getAddress())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (BluetoothDeviceData _device : connectedDevices) {
                    if (_device.getName().equalsIgnoreCase(device.getName())) {
                        found = true;
                        break;
                    }
                }
            }
            //PPApplication.logE("BluetoothConnectionBroadcastReceiver.addConnectedDevice","found="+found);
            if (!found) {
                int gmtOffset = 0; //TimeZone.getDefault().getRawOffset();
                Calendar now = Calendar.getInstance();
                long timestamp = now.getTimeInMillis() - gmtOffset;
                connectedDevices.add(new BluetoothDeviceData(device.getName(), device.getAddress(),
                        BluetoothScanWorker.getBluetoothType(device), false, timestamp, false, false));
            }
        }
    }

    private static void removeConnectedDevice(BluetoothDevice device)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE("BluetoothConnectionBroadcastReceiver.removeConnectedDevice", "device.name=" + device.getName());
                PPApplication.logE("BluetoothConnectionBroadcastReceiver.removeConnectedDevice", "device.address=" + device.getAddress());
            }*/
            int index = 0;
            boolean found = false;
            for (BluetoothDeviceData _device : connectedDevices) {
                if (_device.address.equals(device.getAddress())) {
                    found = true;
                    break;
                }
                ++index;
            }
            if (!found) {
                index = 0;
                for (BluetoothDeviceData _device : connectedDevices) {
                    if (_device.getName().equalsIgnoreCase(device.getName())) {
                        found = true;
                        break;
                    }
                    ++index;
                }
            }
            //PPApplication.logE("BluetoothConnectionBroadcastReceiver.removeConnectedDevice","found="+found);
            if (found)
                connectedDevices.remove(index);
        }
    }

    static void clearConnectedDevices(Context context, boolean onlyOld)
    {
        //PPApplication.logE("BluetoothConnectionBroadcastReceiver.clearConnectedDevices","onlyOld="+onlyOld);

        if (onlyOld) {
            getConnectedDevices(context);
        }

        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            if (connectedDevices != null) {
                if (onlyOld) {
                    int gmtOffset = 0; //TimeZone.getDefault().getRawOffset();
                    for (BluetoothDeviceData device : connectedDevices) {
                        /*if (PPApplication.logEnabled()) {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                            if (PPApplication.logEnabled()) {
                                PPApplication.logE("BluetoothConnectionBroadcastReceiver.clearConnectedDevices", "device.name=" + device.name);
                                PPApplication.logE("BluetoothConnectionBroadcastReceiver.clearConnectedDevices", "device.timestamp=" + sdf.format(device.timestamp));
                            }
                        }*/
                        //long bootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime() - gmtOffset;
                        Calendar calendar = Calendar.getInstance();
                        long bootTime = calendar.getTimeInMillis() - SystemClock.elapsedRealtime() - gmtOffset;
                        /*if (PPApplication.logEnabled()) {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
                            PPApplication.logE("BluetoothConnectionBroadcastReceiver.clearConnectedDevices", "bootTime=" + sdf.format(bootTime));
                        }*/
                        if (device.timestamp < bootTime)
                            connectedDevices.remove(device);
                    }
                }
                else
                    connectedDevices.clear();
            }
        }
    }

    private static void changeDeviceName(BluetoothDevice device, String deviceName)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            boolean found = false;
            for (BluetoothDeviceData _device : connectedDevices) {
                if (_device.address.equals(device.getAddress()) && !deviceName.isEmpty()) {
                    _device.setName(deviceName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (BluetoothDeviceData _device : connectedDevices) {
                    if (_device.getName().equalsIgnoreCase(device.getName()) && !deviceName.isEmpty()) {
                        _device.setName(deviceName);
                        break;
                    }
                }
            }
        }
    }

    static void addConnectedDeviceData(List<BluetoothDeviceData> detectedDevices)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            for (BluetoothDeviceData device : detectedDevices) {
                /*if (PPApplication.logEnabled()) {
                    PPApplication.logE("BluetoothConnectionBroadcastReceiver.addConnectedDeviceData", "device.name=" + device.getName());
                    PPApplication.logE("BluetoothConnectionBroadcastReceiver.addConnectedDeviceData", "device.address=" + device.getAddress());
                }*/
                boolean found = false;
                for (BluetoothDeviceData _device : connectedDevices) {
                    if (_device.getAddress().equals(device.getAddress())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (BluetoothDeviceData _device : connectedDevices) {
                        if (_device.getName().equalsIgnoreCase(device.getName())) {
                            found = true;
                            break;
                        }
                    }
                }
                //PPApplication.logE("BluetoothConnectionBroadcastReceiver.addConnectedDeviceData", "found=" + found);
                if (!found) {
                    connectedDevices.add(device);
                }
            }
        }
    }

    static boolean isBluetoothConnected(BluetoothDeviceData deviceData, String sensorDeviceName)
    {
        synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            if ((deviceData == null) && sensorDeviceName.isEmpty()) {
//                PPApplication.logE("BluetoothConnectionBroadcastReceiver.isBluetoothConnected", "connectedDevices="+connectedDevices);
//                if (connectedDevices != null) {
//                    PPApplication.logE("BluetoothConnectionBroadcastReceiver.isBluetoothConnected", "connectedDevices.size()=" + connectedDevices.size());
//                    if (connectedDevices.size() > 0)
//                        PPApplication.logE("BluetoothConnectionBroadcastReceiver.isBluetoothConnected", "connectedDevices.get(0).name=" + connectedDevices.get(0).name);
//                }
                return (connectedDevices != null) && (connectedDevices.size() > 0);
            }
            else {
                if (connectedDevices != null) {
                    if (deviceData != null) {
                        boolean found = false;
                        for (BluetoothDeviceData _device : connectedDevices) {
                            if (_device.address.equals(deviceData.getAddress())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            for (BluetoothDeviceData _device : connectedDevices) {
                                String _deviceName = _device.getName().trim();
                                String deviceDataName = deviceData.getName().trim();
                                if (_deviceName.equalsIgnoreCase(deviceDataName)) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                        return found;
                    }
                    else {
                        for (BluetoothDeviceData _device : connectedDevices) {
                            String device = _device.getName().trim().toUpperCase();
                            String _adapterName = sensorDeviceName.trim().toUpperCase();
                            if (Wildcard.match(device, _adapterName, '_', '%', true)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        }
    }

    private static abstract class PPHandlerThreadRunnable implements Runnable {

        public final WeakReference<Context> appContextWeakRef;
        public final WeakReference<BluetoothDevice> deviceWeakRef;

        public PPHandlerThreadRunnable(Context appContext,
                                       BluetoothDevice device) {
            this.appContextWeakRef = new WeakReference<>(appContext);
            this.deviceWeakRef = new WeakReference<>(device);
        }

    }

}
