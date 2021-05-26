package sk.henrichg.phoneprofilesplus;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHealth;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class BluetoothConnectedDevices {

    private static BluetoothHeadset bluetoothHeadset = null;
    @SuppressWarnings("deprecation")
    private static BluetoothHealth bluetoothHealth = null;
    private static BluetoothA2dp bluetoothA2dp = null;

    private static BluetoothProfile.ServiceListener profileListener = null;

    static void getConnectedDevices(final Context context) {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //BluetoothScanWorker.getBluetoothAdapter(context);
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled())
                return;

// HandlerThread is not needed, this method is already called from it in PhoneProfilesService.doFirstStart()

            if (profileListener == null) {
                profileListener = new BluetoothProfile.ServiceListener() {
                    public void onServiceConnected(int profile, BluetoothProfile proxy) {
//                        PPApplication.logE("[IN_LISTENER] BluetoothConnectedDevices.onServiceConnected", "xxx");

                        if (profile == BluetoothProfile.HEADSET) {
                            //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceConnected", "HEADSET service connected");

                            bluetoothHeadset = (BluetoothHeadset) proxy;

                            final Context appContext = context.getApplicationContext();

                            if (bluetoothHeadset != null) {
                                try {
                                    List<BluetoothDevice> devices = bluetoothHeadset.getConnectedDevices();
                                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "HEADSET size=" + devices.size());
                                    final List<BluetoothDeviceData> connectedDevices = new ArrayList<>();
                                    addConnectedDevices(devices, connectedDevices);
                                    BluetoothConnectionBroadcastReceiver.addConnectedDeviceData(connectedDevices);
                                    BluetoothConnectionBroadcastReceiver.saveConnectedDevices(appContext);
                                } catch (Exception e) {
                                    // not log this, profile may not exists
                                    //Log.e("BluetoothConnectedDevices.getConnectedDevices", Log.getStackTraceString(e));
                                    //PPApplication.recordException(e);
                                }
                                //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "HEADSET end");
                                bluetoothAdapter.closeProfileProxy(BluetoothProfile.HEADSET, bluetoothHeadset);
                            }
                        }
                        //noinspection deprecation
                        if (profile == BluetoothProfile.HEALTH) {
                            //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceConnected", "HEALTH service connected");

                            //noinspection deprecation
                            bluetoothHealth = (BluetoothHealth) proxy;

                            final Context appContext = context.getApplicationContext();

                            if (bluetoothHealth != null) {
                                try {
                                    List<BluetoothDevice> devices = bluetoothHealth.getConnectedDevices();
                                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "HEALTH size=" + devices.size());
                                    final List<BluetoothDeviceData> connectedDevices = new ArrayList<>();
                                    addConnectedDevices(devices, connectedDevices);
                                    BluetoothConnectionBroadcastReceiver.addConnectedDeviceData(connectedDevices);
                                    BluetoothConnectionBroadcastReceiver.saveConnectedDevices(appContext);
                                } catch (Exception e) {
                                    // not log this, profile may not exists
                                    //Log.e("BluetoothConnectedDevices.getConnectedDevices", Log.getStackTraceString(e));
                                    //PPApplication.recordException(e);
                                }
                                //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "HEALTH end");
                                //noinspection deprecation
                                bluetoothAdapter.closeProfileProxy(BluetoothProfile.HEALTH, bluetoothHealth);
                            }
                        }
                        if (profile == BluetoothProfile.A2DP) {
                            //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceConnected", "A2DP service connected");

                            bluetoothA2dp = (BluetoothA2dp) proxy;

                            final Context appContext = context.getApplicationContext();

                            if (bluetoothA2dp != null) {
                                try {
                                    List<BluetoothDevice> devices = bluetoothA2dp.getConnectedDevices();
                                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "A2DP size=" + devices.size());
                                    final List<BluetoothDeviceData> connectedDevices = new ArrayList<>();
                                    addConnectedDevices(devices, connectedDevices);
                                    BluetoothConnectionBroadcastReceiver.addConnectedDeviceData(connectedDevices);
                                    BluetoothConnectionBroadcastReceiver.saveConnectedDevices(appContext);
                                } catch (Exception e) {
                                    // not log this, profile may not exists
                                    //Log.e("BluetoothConnectedDevices.getConnectedDevices", Log.getStackTraceString(e));
                                    //PPApplication.recordException(e);
                                }
                                //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "A2DP end");
                                bluetoothAdapter.closeProfileProxy(BluetoothProfile.A2DP, bluetoothA2dp);
                            }
                        }
                    }

                    public void onServiceDisconnected(int profile) {
//                        PPApplication.logE("[IN_LISTENER] BluetoothConnectedDevices.onServiceDisconnected", "xxx");

                        //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceDisconnected", "xxx");
                        if (profile == BluetoothProfile.HEADSET) {
                            //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceDisconnected", "HEADSET service disconnected");
                            bluetoothHeadset = null;
                        }
                        //noinspection deprecation
                        if (profile == BluetoothProfile.HEALTH) {
                            //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceDisconnected", "HEALTH service disconnected");
                            bluetoothHealth = null;
                        }
                        if (profile == BluetoothProfile.A2DP) {
                            //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices.onServiceDisconnected", "A2DP service disconnected");
                            bluetoothA2dp = null;
                        }
                    }
                };
            }

            try {

                bluetoothHeadset = null;
                bluetoothHealth = null;
                bluetoothA2dp = null;

                bluetoothAdapter.getProfileProxy(context, profileListener, BluetoothProfile.A2DP);
                //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "A2DP start="+okA2DP);

                bluetoothAdapter.getProfileProxy(context, profileListener, BluetoothProfile.HEADSET);
                //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "HEADSET start="+okHEADSET);

                if (Build.VERSION.SDK_INT < 29) {
                    //noinspection deprecation
                    bluetoothAdapter.getProfileProxy(context, profileListener, BluetoothProfile.HEALTH);
                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "HEALTH start=" + okHEALTH);
                }

                final Context appContext = context.getApplicationContext();
                final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
                if (bluetoothManager != null) {
                    final List<BluetoothDeviceData> connectedDevices = new ArrayList<>();
                    //final Context appContext = context.getApplicationContext();
                    List<BluetoothDevice> devices;

                    devices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);
                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "GATT size=" + devices.size());
                    addConnectedDevices(devices, connectedDevices);

                    devices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT_SERVER);
                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "GATT_SERVER size=" + devices.size());
                    addConnectedDevices(devices, connectedDevices);

//                    devices = bluetoothManager.getConnectedDevices(BluetoothProfile.SAP);
//                    //PPApplication.logE("------ BluetoothConnectedDevices.getConnectedDevices", "SAP size=" + devices.size());
//                    addConnectedDevices(devices, connectedDevices);

                    BluetoothConnectionBroadcastReceiver.addConnectedDeviceData(connectedDevices);
                    BluetoothConnectionBroadcastReceiver.saveConnectedDevices(appContext);
                }

            } catch (Exception e) {
                //Log.e("BluetoothConnectedDevices.getConnectedDevices", Log.getStackTraceString(e));
                PPApplication.recordException(e);
            }
        }
    }

    private static void addConnectedDevices(List<BluetoothDevice> detectedDevices, List<BluetoothDeviceData> connectedDevices)
    {
        //synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            for (BluetoothDevice device : detectedDevices) {
                /*if (PPApplication.logEnabled()) {
                    PPApplication.logE("------ BluetoothConnectedDevices.addConnectedDevice", "device.name=" + device.getName());
                    PPApplication.logE("------ BluetoothConnectedDevices.addConnectedDevice", "device.address=" + device.getAddress());
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
                //PPApplication.logE("------ BluetoothConnectedDevices.addConnectedDevice", "found=" + found);
                if (!found) {
                    int gmtOffset = 0; //TimeZone.getDefault().getRawOffset();
                    Calendar now = Calendar.getInstance();
                    long timestamp = now.getTimeInMillis() - gmtOffset;
                    connectedDevices.add(new BluetoothDeviceData(device.getName(), device.getAddress(),
                            BluetoothScanWorker.getBluetoothType(device), false, timestamp, false, false));
                }
            }
        //}
    }

    /*
    static boolean isBluetoothConnected(List<BluetoothDeviceData> connectedDevices, BluetoothDeviceData deviceData, String sensorDeviceName)
    {
        //synchronized (PPApplication.bluetoothConnectionChangeStateMutex) {
            if ((deviceData == null) && sensorDeviceName.isEmpty())
                return (connectedDevices != null) && (connectedDevices.size() > 0);
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
                                if (_device.getName().equalsIgnoreCase(deviceData.getName())) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                        return found;
                    }
                    else {
                        for (BluetoothDeviceData _device : connectedDevices) {
                            String device = _device.getName().toUpperCase();
                            String _adapterName = sensorDeviceName.toUpperCase();
                            if (Wildcard.match(device, _adapterName, '_', '%', true)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        //}
    }
    */

}
