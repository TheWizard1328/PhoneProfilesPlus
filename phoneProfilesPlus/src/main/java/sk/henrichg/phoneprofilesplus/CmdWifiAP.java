package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.net.IConnectivityManager;
import android.net.wifi.IWifiManager;
import android.net.wifi.WifiManager;
import android.os.ResultReceiver;
import android.os.ServiceManager;

@SuppressWarnings("WeakerAccess")
public class CmdWifiAP {

    public static void main(String[] args) {
        if (!(run(Boolean.parseBoolean(args[0]), Boolean.parseBoolean(args[1])))) {
            System.exit(1);
        }
    }

    private static boolean run(boolean enable, boolean doNotChangeWifi) {
        return setWifiAP(enable, doNotChangeWifi, null, null);
    }

    static boolean setWifiAP(boolean enable, boolean doNotChangeWifi, Context context, String profileName) {
        //PPApplication.logE("CmdWifiAP.setWifiAP", "START enable="+enable);
        //PPApplication.logE("CmdWifiAP.setWifiAP", "START doNotChangeWifi="+doNotChangeWifi);
        final String packageName = PPApplication.PACKAGE_NAME;
        try {
            IConnectivityManager connectivityAdapter = IConnectivityManager.Stub.asInterface(ServiceManager.getService("connectivity"));  // service list | grep IConnectivityManager
            //PPApplication.logE("CmdWifiAP.setWifiAP", "connectivityAdapter="+connectivityAdapter);
            if (enable) {
                if (!doNotChangeWifi) {
                    IWifiManager wifiAdapter = IWifiManager.Stub.asInterface(ServiceManager.getService("wifi"));  // service list | grep IWifiManager
                    //PPApplication.logE("CmdWifiAP.setWifiAP", "wifiAdapter="+wifiAdapter);
                    int wifiState = wifiAdapter.getWifiEnabledState();
                    boolean isWifiEnabled = ((wifiState == WifiManager.WIFI_STATE_ENABLED) || (wifiState == WifiManager.WIFI_STATE_ENABLING));
                    //PPApplication.logE("CmdWifiAP.setWifiAP", "isWifiEnabled="+isWifiEnabled);
                    if (isWifiEnabled) {
//                        PPApplication.logE("[WIFI_ENABLED] CmdWifiAP.setWifiAP", "false");
                        wifiAdapter.setWifiEnabled(packageName, false);
                    }
                }

                ResultReceiver dummyResultReceiver = new ResultReceiver(null);
                connectivityAdapter.startTethering(0, dummyResultReceiver, false, packageName);
            } else {
                connectivityAdapter.stopTethering(0, packageName);
            }

            //PPApplication.logE("CmdWifiAP.setWifiAP", "END=");
            return true;
        /*} catch (java.lang.SecurityException ee) {
            //Log.e("CmdWifiAP.setWifiAP", Log.getStackTraceString(ee));
            //PPApplication.logToCrashlytics("E/CmdWifiAP.setWifiAP: " + Log.getStackTraceString(ee));
            PPApplication.recordException(ee);
            PPApplication.logE("CmdWifiAP.setWifiAP", Log.getStackTraceString(ee));
            return false;*/
        } catch (Throwable e) {
            if (context != null) {
                //Log.e("CmdWifiAP.setWifiAP", Log.getStackTraceString(e));
                //PPApplication.recordException(e);
                //PPApplication.logE("CmdWifiAP.setWifiAP", Log.getStackTraceString(e));
                ActivateProfileHelper.showError(context, profileName, Profile.PARAMETER_TYPE_WIFIAP);
            }
            return false;
        }
    }

    /*
    static boolean setWifiAP30(boolean enable, boolean doNotChangeWifi, Context context, String profileName) {
        PPApplication.logE("CmdWifiAP30.setWifiAP", "START enable="+enable);
        PPApplication.logE("CmdWifiAP30.setWifiAP", "START doNotChangeWifi="+doNotChangeWifi);
        final String packageName = PPApplication.PACKAGE_NAME;
        try {
            //IConnectivityManager connectivityAdapter = IConnectivityManager.Stub.asInterface(ServiceManager.getService("connectivity"));  // service list | grep IConnectivityManager
            //PPApplication.logE("CmdWifiAP.setWifiAP", "connectivityAdapter="+connectivityAdapter);
            if (enable) {
                if (!doNotChangeWifi) {
                    IWifiManager wifiAdapter = IWifiManager.Stub.asInterface(ServiceManager.getService("wifi"));  // service list | grep IWifiManager
                    PPApplication.logE("CmdWifiAP.setWifiAP30", "wifiAdapter="+wifiAdapter);
                    int wifiState = wifiAdapter.getWifiEnabledState();
                    boolean isWifiEnabled = ((wifiState == WifiManager.WIFI_STATE_ENABLED) || (wifiState == WifiManager.WIFI_STATE_ENABLING));
                    PPApplication.logE("CmdWifiAP.setWifiAP30", "isWifiEnabled="+isWifiEnabled);
                    if (isWifiEnabled) {
                        PPApplication.logE("[WIFI_ENABLED] CmdWifiAP.setWifiAP30", "false");
                        wifiAdapter.setWifiEnabled(packageName, false);
                    }
                }


            } else {
            }

            PPApplication.logE("CmdWifiAP.setWifiAP30", "END=");
            return true;
//        } catch (java.lang.SecurityException ee) {
//            //Log.e("CmdWifiAP.setWifiAP", Log.getStackTraceString(ee));
//            //PPApplication.logToCrashlytics("E/CmdWifiAP.setWifiAP: " + Log.getStackTraceString(ee));
//            PPApplication.recordException(ee);
//            PPApplication.logE("CmdWifiAP.setWifiAP", Log.getStackTraceString(ee));
//            return false;
        } catch (Throwable e) {
            if (context != null) {
                //Log.e("CmdWifiAP.setWifiAP", Log.getStackTraceString(e));
                //PPApplication.recordException(e);
                PPApplication.logE("CmdWifiAP.setWifiAP30", Log.getStackTraceString(e));
                ActivateProfileHelper.showError(context, profileName, Profile.PARAMETER_TYPE_WIFIAP);
            }
            return false;
        }
    }
    */

    static boolean isEnabled() {
        //PPApplication.logE("CmdWifiAP.isEnabled", "xxx");
        try {
            boolean enabled;
            IWifiManager adapter = IWifiManager.Stub.asInterface(ServiceManager.getService("wifi"));  // service list | grep IWifiManager
            //PPApplication.logE("CmdWifiAP.isEnabled", "adapter="+adapter);
            enabled = adapter.getWifiApEnabledState() == WifiManager.WIFI_AP_STATE_ENABLED;
            //PPApplication.logE("CmdWifiAP.isEnabled", "enabled="+enabled);
            return enabled;
        } catch (Throwable e) {
            //Log.e("CmdWifiAP.isEnabled", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            //PPApplication.logE("CmdWifiAP.isEnabled", Log.getStackTraceString(e));
            return false;
        }
    }

}
