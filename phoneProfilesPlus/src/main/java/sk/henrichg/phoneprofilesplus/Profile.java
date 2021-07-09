package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Profile {

    long _id;
    String _name;
    String _icon;
    boolean _checked;
    int _porder;
    int _duration;
    int _afterDurationDo;
    boolean _askForDuration;
    String _durationNotificationSound;
    boolean _durationNotificationVibrate;
    int _volumeRingerMode;
    int _volumeZenMode;
    String _volumeRingtone;
    String _volumeNotification;
    String _volumeMedia;
    String _volumeAlarm;
    String _volumeSystem;
    String _volumeVoice;
    int _soundRingtoneChange;
    String _soundRingtone;
    int _soundNotificationChange;
    String _soundNotification;
    int _soundAlarmChange;
    String _soundAlarm;
    int _deviceAirplaneMode;
    int _deviceMobileData;
    int _deviceMobileDataPrefs;
    int _deviceWiFi;
    int _deviceBluetooth;
    int _deviceGPS;
    int _deviceLocationServicePrefs;
    int _deviceScreenTimeout;
    String _deviceBrightness;
    int _deviceWallpaperChange;
    String _deviceWallpaper;
    int _deviceRunApplicationChange;
    String _deviceRunApplicationPackageName;
    int _deviceAutoSync;
    boolean _showInActivator;
    int _deviceAutoRotate;
    int _volumeSpeakerPhone;
    int _deviceNFC;
    int _deviceKeyguard;
    int _vibrationOnTouch;
    int _deviceWiFiAP;
    int _devicePowerSaveMode;
    int _deviceNetworkType;
    int _notificationLed;
    int _vibrateWhenRinging;
    int _deviceWallpaperFor;
    boolean _hideStatusBarIcon;
    int _lockDevice;
    String _deviceConnectToSSID;
    int _applicationDisableWifiScanning;
    int _applicationDisableBluetoothScanning;
    int _deviceWiFiAPPrefs;
    int _applicationDisableLocationScanning;
    int _applicationDisableMobileCellScanning;
    int _applicationDisableOrientationScanning;
    int _headsUpNotifications;
    int _deviceForceStopApplicationChange;
    String _deviceForceStopApplicationPackageName;
    long _activationByUserCount;
    int _deviceNetworkTypePrefs;
    int _deviceCloseAllApplications;
    int _screenDarkMode;
    int _dtmfToneWhenDialing;
    int _soundOnTouch;
    String _volumeDTMF;
    String _volumeAccessibility;
    String _volumeBluetoothSCO;
    long _afterDurationProfile;
    int _alwaysOnDisplay;
    int _screenOnPermanent;
    boolean _volumeMuteSound;
    int _deviceLocationMode;
    int _applicationDisableNotificationScanning;
    String _generateNotification;
    int _cameraFlash;
    int _deviceNetworkTypeSIM1;
    int _deviceNetworkTypeSIM2;
    int _deviceMobileDataSIM1;
    int _deviceMobileDataSIM2;
    String _deviceDefaultSIMCards;
    int _deviceOnOffSIM1;
    int _deviceOnOffSIM2;
    int _soundRingtoneChangeSIM1;
    String _soundRingtoneSIM1;
    int _soundRingtoneChangeSIM2;
    String _soundRingtoneSIM2;
    int _soundNotificationChangeSIM1;
    String _soundNotificationSIM1;
    int _soundNotificationChangeSIM2;
    String _soundNotificationSIM2;
    int _soundSameRingtoneForBothSIMCards;

    Bitmap _iconBitmap;
    Bitmap _preferencesIndicator;
    //int _ringerModeForZenMode;

    static final long PROFILE_NO_ACTIVATE = -999;

    //private static final String PREF_PROFILE_ID = "prf_pref_id";
    static final String PREF_PROFILE_NAME = "prf_pref_profileName";
    static final String PREF_PROFILE_ICON = "prf_pref_profileIcon";
    //private static final String PREF_PROFILE_CHECKED = "prf_pref_checked";
    static final String PREF_PROFILE_VOLUME_RINGER_MODE = "prf_pref_volumeRingerMode";
    static final String PREF_PROFILE_VOLUME_ZEN_MODE = "prf_pref_volumeZenMode";
    static final String PREF_PROFILE_VOLUME_RINGTONE = "prf_pref_volumeRingtone";
    static final String PREF_PROFILE_VOLUME_NOTIFICATION = "prf_pref_volumeNotification";
    static final String PREF_PROFILE_VOLUME_MEDIA = "prf_pref_volumeMedia";
    static final String PREF_PROFILE_VOLUME_ALARM = "prf_pref_volumeAlarm";
    static final String PREF_PROFILE_VOLUME_SYSTEM = "prf_pref_volumeSystem";
    static final String PREF_PROFILE_VOLUME_VOICE = "prf_pref_volumeVoice";
    static final String PREF_PROFILE_SOUND_RINGTONE_CHANGE = "prf_pref_soundRingtoneChange";
    static final String PREF_PROFILE_SOUND_RINGTONE = "prf_pref_soundRingtone";
    static final String PREF_PROFILE_SOUND_NOTIFICATION_CHANGE = "prf_pref_soundNotificationChange";
    static final String PREF_PROFILE_SOUND_NOTIFICATION = "prf_pref_soundNotification";
    static final String PREF_PROFILE_SOUND_ALARM_CHANGE = "prf_pref_soundAlarmChange";
    static final String PREF_PROFILE_SOUND_ALARM = "prf_pref_soundAlarm";
    static final String PREF_PROFILE_DEVICE_AIRPLANE_MODE = "prf_pref_deviceAirplaneMode";
    static final String PREF_PROFILE_DEVICE_WIFI = "prf_pref_deviceWiFi";
    static final String PREF_PROFILE_DEVICE_BLUETOOTH = "prf_pref_deviceBluetooth";
    static final String PREF_PROFILE_DEVICE_SCREEN_TIMEOUT = "prf_pref_deviceScreenTimeout";
    static final String PREF_PROFILE_DEVICE_BRIGHTNESS = "prf_pref_deviceBrightness";
    static final String PREF_PROFILE_DEVICE_WALLPAPER_CHANGE = "prf_pref_deviceWallpaperChange";
    static final String PREF_PROFILE_DEVICE_WALLPAPER = "prf_pref_deviceWallpaper";
    static final String PREF_PROFILE_DEVICE_MOBILE_DATA = "prf_pref_deviceMobileData";
    static final String PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS = "prf_pref_deviceMobileDataPrefs";
    static final String PREF_PROFILE_DEVICE_GPS = "prf_pref_deviceGPS";
    static final String PREF_PROFILE_DEVICE_RUN_APPLICATION_CHANGE = "prf_pref_deviceRunApplicationChange";
    static final String PREF_PROFILE_DEVICE_RUN_APPLICATION_PACKAGE_NAME = "prf_pref_deviceRunApplicationPackageName";
    static final String PREF_PROFILE_DEVICE_AUTOSYNC = "prf_pref_deviceAutosync";
    static final String PREF_PROFILE_SHOW_IN_ACTIVATOR = "prf_pref_showInActivator";
    static final String PREF_PROFILE_DEVICE_AUTOROTATE = "prf_pref_deviceAutoRotation";
    static final String PREF_PROFILE_DEVICE_LOCATION_SERVICE_PREFS = "prf_pref_deviceLocationServicePrefs";
    static final String PREF_PROFILE_VOLUME_SPEAKER_PHONE = "prf_pref_volumeSpeakerPhone";
    static final String PREF_PROFILE_DEVICE_NFC = "prf_pref_deviceNFC";
    static final String PREF_PROFILE_DURATION = "prf_pref_duration";
    static final String PREF_PROFILE_AFTER_DURATION_DO = "prf_pref_afterDurationDo";
    static final String PREF_PROFILE_ASK_FOR_DURATION = "prf_pref_askForDuration";
    static final String PREF_PROFILE_DURATION_NOTIFICATION_SOUND = "prf_pref_durationNotificationSound";
    static final String PREF_PROFILE_DURATION_NOTIFICATION_VIBRATE = "prf_pref_durationNotificationVibrate";
    static final String PREF_PROFILE_DEVICE_KEYGUARD = "prf_pref_deviceKeyguard";
    static final String PREF_PROFILE_VIBRATION_ON_TOUCH = "prf_pref_vibrationOnTouch";
    static final String PREF_PROFILE_VOLUME_UNLINK_VOLUMES_APP_SETTINGS = "prf_pref_volumeUnlinkVolumesAppSettings";
    static final String PREF_PROFILE_DEVICE_WIFI_AP = "prf_pref_deviceWiFiAP";
    static final String PREF_PROFILE_DEVICE_POWER_SAVE_MODE = "prf_pref_devicePowerSaveMode";
    static final String PREF_PROFILE_DEVICE_NETWORK_TYPE = "prf_pref_deviceNetworkType";
    static final String PREF_PROFILE_NOTIFICATION_LED = "prf_pref_notificationLed";
    static final String PREF_PROFILE_VIBRATE_WHEN_RINGING = "prf_pref_vibrateWhenRinging";
    static final String PREF_PROFILE_DEVICE_WALLPAPER_FOR = "prf_pref_deviceWallpaperFor";
    static final String PREF_PROFILE_HIDE_STATUS_BAR_ICON = "prf_pref_hideStatusBarIcon";
    static final String PREF_PROFILE_LOCK_DEVICE = "prf_pref_lockDevice";
    static final String PREF_PROFILE_DEVICE_CONNECT_TO_SSID = "prf_pref_deviceConnectToSSID";
    static final String PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING = "prf_pref_applicationDisableWifiScanning";
    static final String PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING = "prf_pref_applicationDisableBluetoothScanning";
    static final String PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS = "prf_pref_deviceAdaptiveBrightness";
    static final String PREF_PROFILE_DEVICE_WIFI_AP_PREFS = "prf_pref_deviceWiFiAPPrefs";
    static final String PREF_PROFILE_APPLICATION_DISABLE_LOCATION_SCANNING = "prf_pref_applicationDisableLocationScanning";
    static final String PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING = "prf_pref_applicationDisableMobileCellScanning";
    static final String PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING = "prf_pref_applicationDisableOrientationScanning";
    static final String PREF_PROFILE_HEADS_UP_NOTIFICATIONS = "prf_pref_headsUpNotifications";
    static final String PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_CHANGE = "prf_pref_deviceForceStopApplicationChange";
    static final String PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_PACKAGE_NAME = "prf_pref_deviceForceStopApplicationPackageName";
    static final String PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS = "prf_pref_deviceNetworkTypePrefs";
    static final String PREF_PROFILE_DEVICE_CLOSE_ALL_APPLICATIONS = "prf_pref_deviceCloseAllApplications";
    static final String PREF_PROFILE_SCREEN_DARK_MODE = "prf_pref_screenDarkMode";
    static final String PREF_PROFILE_DTMF_TONE_WHEN_DIALING = "prf_pref_dtmfToneWhenDialing";
    static final String PREF_PROFILE_SOUND_ON_TOUCH = "prf_pref_soundOnTouch";
    static final String PREF_PROFILE_VOLUME_DTMF = "prf_pref_volumeDTMF";
    static final String PREF_PROFILE_VOLUME_ACCESSIBILITY = "prf_pref_volumeAccessibility";
    static final String PREF_PROFILE_VOLUME_BLUETOOTH_SCO = "prf_pref_volumeBluetoothSCO";
    static final String PREF_PROFILE_AFTER_DURATION_PROFILE = "prf_pref_afterDurationProfile";
    static final String PREF_PROFILE_ALWAYS_ON_DISPLAY = "prf_pref_alwaysOnDisplay";
    static final String PREF_PROFILE_SCREEN_ON_PERMANENT = "prf_pref_screenOnPermanent";
    static final String PREF_PROFILE_VOLUME_MUTE_SOUND = "prf_pref_volumeMuteSound";
    static final String PREF_PROFILE_DEVICE_LOCATION_MODE = "prf_pref_deviceLocationMode";
    static final String PREF_PROFILE_APPLICATION_DISABLE_NOTIFICATION_SCANNING = "prf_pref_applicationDisableNotificationScanning";
    static final String PREF_PROFILE_GENERATE_NOTIFICATION = "prf_pref_generateNotification";
    static final String PREF_PROFILE_CAMERA_FLASH = "prf_pref_cameraFlash";
    static final String PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1 = "prf_pref_deviceNetworkTypeSIM1";
    static final String PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2 = "prf_pref_deviceNetworkTypeSIM2";
    static final String PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1 = "prf_pref_deviceMobileDataSIM1";
    static final String PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2 = "prf_pref_deviceMobileDataSIM2";
    static final String PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS = "prf_pref_deviceDefaultSIMCards";
    static final String PREF_PROFILE_DEVICE_ONOFF_SIM1 = "prf_pref_deviceOnOffSIM1";
    static final String PREF_PROFILE_DEVICE_ONOFF_SIM2 = "prf_pref_deviceOnOffSIM2";
    static final String PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM1 = "prf_pref_soundRingtoneChangeSIM1";
    static final String PREF_PROFILE_SOUND_RINGTONE_SIM1 = "prf_pref_soundRingtoneSIM1";
    static final String PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM2 = "prf_pref_soundRingtoneChangeSIM2";
    static final String PREF_PROFILE_SOUND_RINGTONE_SIM2 = "prf_pref_soundRingtoneSIM2";
    static final String PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM1 = "prf_pref_soundNotificationChangeSIM1";
    static final String PREF_PROFILE_SOUND_NOTIFICATION_SIM1 = "prf_pref_soundNotificationSIM1";
    static final String PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM2 = "prf_pref_soundNotificationChangeSIM2";
    static final String PREF_PROFILE_SOUND_NOTIFICATION_SIM2 = "prf_pref_soundNotificationSIM2";
    static final String PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS = "prf_pref_soundSameRingtoneForBothSIMCards";

    static final HashMap<String, Boolean> defaultValuesBoolean;
    static {
        defaultValuesBoolean = new HashMap<>();
        defaultValuesBoolean.put("prf_pref_showInActivator", false);
        defaultValuesBoolean.put("prf_pref_showInActivator_notShow", false);
        defaultValuesBoolean.put("prf_pref_askForDuration", false);
        defaultValuesBoolean.put("prf_pref_durationNotificationVibrate", false);
        defaultValuesBoolean.put("prf_pref_hideStatusBarIcon", false);
        defaultValuesBoolean.put("prf_pref_volumeMuteSound", false);
    }
    static final HashMap<String, String> defaultValuesString;
    static {
        defaultValuesString = new HashMap<>();
        defaultValuesString.put("prf_pref_profileName", "");
        defaultValuesString.put("prf_pref_profileIcon", "ic_profile_default|1|0|0");
        defaultValuesString.put("prf_pref_profileIcon_withoutIcon", "|1|0|0");
        defaultValuesString.put("prf_pref_duration", "0");
        defaultValuesString.put("prf_pref_afterDurationDo", "0");
        defaultValuesString.put("prf_pref_durationNotificationSound", "");
        defaultValuesString.put("prf_pref_volumeRingerMode", "0");
        defaultValuesString.put("prf_pref_volumeZenMode", "1");
        defaultValuesString.put("prf_pref_vibrationOnTouch", "0");
        defaultValuesString.put("prf_pref_volumeRingtone", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeNotification", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeMedia", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeAlarm", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeSystem", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeVoice", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeSpeakerPhone", "0");
        defaultValuesString.put("prf_pref_soundRingtoneChange", "0");
        defaultValuesString.put("prf_pref_soundRingtone", "");
        defaultValuesString.put("prf_pref_soundNotificationChange", "0");
        defaultValuesString.put("prf_pref_soundNotification", "");
        defaultValuesString.put("prf_pref_soundAlarmChange", "0");
        defaultValuesString.put("prf_pref_soundAlarm", "");
        defaultValuesString.put("prf_pref_deviceAirplaneMode", "0");
        defaultValuesString.put("prf_pref_deviceAutosync", "0");
        defaultValuesString.put("prf_pref_deviceMobileData", "0");
        defaultValuesString.put("prf_pref_deviceMobileDataPrefs", "0");
        defaultValuesString.put("prf_pref_deviceWiFi", "0");
        defaultValuesString.put("prf_pref_deviceWiFiAP", "0");
        defaultValuesString.put("prf_pref_deviceBluetooth", "0");
        defaultValuesString.put("prf_pref_deviceGPS", "0");
        defaultValuesString.put("prf_pref_deviceLocationServicePrefs", "0");
        defaultValuesString.put("prf_pref_deviceNFC", "0");
        defaultValuesString.put("prf_pref_deviceScreenTimeout", "0");
        defaultValuesString.put("prf_pref_deviceKeyguard", "0");
        defaultValuesString.put("prf_pref_deviceBrightness", "50|1|1|0");
        defaultValuesString.put("prf_pref_deviceBrightness_withoutLevel", "|1|1|0");
        defaultValuesString.put("prf_pref_deviceAutoRotation", "0");
        defaultValuesString.put("prf_pref_devicePowerSaveMode", "0");
        defaultValuesString.put("prf_pref_deviceRunApplicationChange", "0");
        defaultValuesString.put("prf_pref_deviceRunApplicationPackageName", "-");
        defaultValuesString.put("prf_pref_deviceWallpaperChange", "0");
        defaultValuesString.put("prf_pref_deviceWallpaper", "-");
        defaultValuesString.put("prf_pref_deviceNetworkType", "0");
        defaultValuesString.put("prf_pref_notificationLed", "0");
        defaultValuesString.put("prf_pref_vibrateWhenRinging", "0");
        defaultValuesString.put("prf_pref_deviceWallpaperFor", "0");
        defaultValuesString.put("prf_pref_lockDevice", "0");
        defaultValuesString.put("prf_pref_deviceConnectToSSID", "^just_any^");
        defaultValuesString.put("prf_pref_applicationDisableWifiScanning", "0");
        defaultValuesString.put("prf_pref_applicationDisableBluetoothScanning", "0");
        defaultValuesString.put("prf_pref_deviceWiFiAPPrefs", "0");
        defaultValuesString.put("prf_pref_applicationDisableLocationScanning", "0");
        defaultValuesString.put("prf_pref_applicationDisableMobileCellScanning", "0");
        defaultValuesString.put("prf_pref_applicationDisableOrientationScanning", "0");
        defaultValuesString.put("prf_pref_headsUpNotifications", "0");
        defaultValuesString.put("prf_pref_deviceForceStopApplicationChange", "0");
        defaultValuesString.put("prf_pref_deviceForceStopApplicationPackageName", "-");
        defaultValuesString.put("prf_pref_deviceNetworkTypePrefs", "0");
        defaultValuesString.put("prf_pref_deviceCloseAllApplications", "0");
        defaultValuesString.put("prf_pref_screenDarkMode", "0");
        defaultValuesString.put("prf_pref_dtmfToneWhenDialing", "0");
        defaultValuesString.put("prf_pref_soundOnTouch", "0");
        defaultValuesString.put("prf_pref_volumeDTMF", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeAccessibility", "-1|1|0");
        defaultValuesString.put("prf_pref_volumeBluetoothSCO", "-1|1|0");
        defaultValuesString.put("prf_pref_afterDurationProfile", String.valueOf(PROFILE_NO_ACTIVATE));
        defaultValuesString.put("prf_pref_alwaysOnDisplay", "0");
        defaultValuesString.put("prf_pref_screenOnPermanent", "0");
        defaultValuesString.put("prf_pref_deviceLocationMode", "0");
        defaultValuesString.put("prf_pref_applicationDisableNotificationScanning", "0");
        defaultValuesString.put("prf_pref_generateNotification", "0|0||");
        defaultValuesString.put("prf_pref_cameraFlash", "0");
        defaultValuesString.put("prf_pref_deviceNetworkTypeSIM1", "0");
        defaultValuesString.put("prf_pref_deviceNetworkTypeSIM2", "0");
        defaultValuesString.put("prf_pref_deviceMobileDataSIM1", "0");
        defaultValuesString.put("prf_pref_deviceMobileDataSIM2", "0");
        defaultValuesString.put("prf_pref_deviceDefaultSIMCards", "0|0|0");
        defaultValuesString.put("prf_pref_deviceOnOffSIM1", "0");
        defaultValuesString.put("prf_pref_deviceOnOffSIM2", "0");
        defaultValuesString.put("prf_pref_soundRingtoneChangeSIM1", "0");
        defaultValuesString.put("prf_pref_soundRingtoneSIM1", "");
        defaultValuesString.put("prf_pref_soundRingtoneChangeSIM2", "0");
        defaultValuesString.put("prf_pref_soundRingtoneSIM2", "");
        defaultValuesString.put("prf_pref_soundNotificationChangeSIM1", "0");
        defaultValuesString.put("prf_pref_soundNotificationSIM1", "");
        defaultValuesString.put("prf_pref_soundNotificationChangeSIM2", "0");
        defaultValuesString.put("prf_pref_soundNotificationSIM2", "");
        defaultValuesString.put("prf_pref_soundSameRingtoneForBothSIMCards", "0");
    }

    static final int RINGERMODE_RING = 1;
    static final int RINGERMODE_RING_AND_VIBRATE = 2;
    static final int RINGERMODE_VIBRATE = 3;
    static final int RINGERMODE_SILENT = 4;
    static final int RINGERMODE_ZENMODE = 5;

    static final int ZENMODE_ALL = 1;
    static final int ZENMODE_PRIORITY = 2;
    static final int ZENMODE_NONE = 3;
    static final int ZENMODE_ALL_AND_VIBRATE = 4;
    static final int ZENMODE_PRIORITY_AND_VIBRATE = 5;
    static final int ZENMODE_ALARMS = 6;

    static final int AFTER_DURATION_DO_NOTHING = 0;
    static final int AFTER_DURATION_DO_UNDO_PROFILE = 1;
    static final int AFTER_DURATION_DO_DEFAULT_PROFILE = 2;
    static final int AFTER_DURATION_DO_RESTART_EVENTS = 3;
    static final int AFTER_DURATION_DO_SPECIFIC_PROFILE = 4;

    static final int BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET = -99;

    static final String CONNECTTOSSID_JUSTANY = "^just_any^";
    //static final String CONNECTTOSSID_SHAREDPROFILE = "^default_profile^";

    //static final long SHARED_PROFILE_ID = -999L;
    static final String PROFILE_ICON_DEFAULT = "ic_profile_default";
    static final long RESTART_EVENTS_PROFILE_ID = -888L;

    static final int NO_CHANGE_VALUE = 0;
    //static final int SHARED_PROFILE_VALUE = 99;
    static final String NO_CHANGE_VALUE_STR = "0";
    //static final String SHARED_PROFILE_VALUE_STR = "99";

    //private static final String PREF_ACTIVATED_PROFILE_FOR_DURATION = "activatedProfileForDuration";
    private static final String PREF_ACTIVATED_PROFILE_END_DURATION_TIME = "activatedProfileEndDurationTime";
    //private static final String PREF_ACTIVATED_PROFILE_FOR_EVENT_UNDO = "activatedProfileForEventUndo";

    static final int[] profileIconId = {
            R.drawable.ic_profile_default,

            R.drawable.ic_profile_home, R.drawable.ic_profile_home_2, R.drawable.ic_profile_home_3,
            R.drawable.ic_profile_home_4, R.drawable.ic_profile_home_5, R.drawable.ic_profile_home_6,

            R.drawable.ic_profile_outdoors_1, R.drawable.ic_profile_outdoors_2, R.drawable.ic_profile_outdoors_3,
            R.drawable.ic_profile_outdoors_4, R.drawable.ic_profile_outdoors_5, R.drawable.ic_profile_outdoors_6,
            R.drawable.ic_profile_outdoors_7, R.drawable.ic_profile_outdoors_8, R.drawable.ic_profile_outdoors_9,
            R.drawable.ic_profile_running_1,

            R.drawable.ic_profile_meeting, R.drawable.ic_profile_meeting_2, R.drawable.ic_profile_meeting_3,
            R.drawable.ic_profile_meeting_4, R.drawable.ic_profile_mute, R.drawable.ic_profile_mute_2,
            R.drawable.ic_profile_volume_4, R.drawable.ic_profile_volume_1, R.drawable.ic_profile_volume_2,
            R.drawable.ic_profile_volume_3, R.drawable.ic_profile_vibrate_1,

            R.drawable.ic_profile_work_1, R.drawable.ic_profile_work_2, R.drawable.ic_profile_work_12,
            R.drawable.ic_profile_work_3, R.drawable.ic_profile_work_4, R.drawable.ic_profile_work_5,
            R.drawable.ic_profile_work_6, R.drawable.ic_profile_work_7, R.drawable.ic_profile_work_8,
            R.drawable.ic_profile_work_9, R.drawable.ic_profile_work_10, R.drawable.ic_profile_work_11,
            R.drawable.ic_profile_work_13, R.drawable.ic_profile_work_14, R.drawable.ic_profile_work_15,
            R.drawable.ic_profile_work_16, R.drawable.ic_profile_work_17, R.drawable.ic_profile_work_18,

            R.drawable.ic_profile_sleep, R.drawable.ic_profile_sleep_2, R.drawable.ic_profile_sleep_3,
            R.drawable.ic_profile_night, R.drawable.ic_profile_call_1, R.drawable.ic_profile_food_1,
            R.drawable.ic_profile_food_2, R.drawable.ic_profile_food_3, R.drawable.ic_profile_food_4,
            R.drawable.ic_profile_food_5, R.drawable.ic_profile_alarm,

            R.drawable.ic_profile_car_1, R.drawable.ic_profile_car_2, R.drawable.ic_profile_car_3,
            R.drawable.ic_profile_car_4, R.drawable.ic_profile_car_5, R.drawable.ic_profile_car_6,
            R.drawable.ic_profile_car_7, R.drawable.ic_profile_car_8, R.drawable.ic_profile_car_9,
            R.drawable.ic_profile_car_10, R.drawable.ic_profile_car_11, R.drawable.ic_profile_steering_1,
            R.drawable.ic_profile_airplane_1, R.drawable.ic_profile_airplane_2, R.drawable.ic_profile_airplane_3,
            R.drawable.ic_profile_ship_1, R.drawable.ic_profile_ship_2, R.drawable.ic_profile_ship_3,
            R.drawable.ic_profile_tram_1, R.drawable.ic_profile_tickets_1, R.drawable.ic_profile_tickets_2,
            R.drawable.ic_profile_travel_1,

            R.drawable.ic_profile_culture_1, R.drawable.ic_profile_culture_6, R.drawable.ic_profile_culture_7,
            R.drawable.ic_profile_culture_2, R.drawable.ic_profile_culture_8, R.drawable.ic_profile_culture_9,
            R.drawable.ic_profile_culture_3, R.drawable.ic_profile_culture_10, R.drawable.ic_profile_culture_11,
            R.drawable.ic_profile_culture_12, R.drawable.ic_profile_culture_13, R.drawable.ic_profile_culture_5,
            R.drawable.ic_profile_culture_14, R.drawable.ic_profile_culture_4, R.drawable.ic_profile_culture_15,
            R.drawable.ic_profile_culture_16, R.drawable.ic_profile_culture_17,

            R.drawable.ic_profile_battery_1, R.drawable.ic_profile_battery_2, R.drawable.ic_profile_battery_3,

            R.drawable.ic_profile_lock, R.drawable.ic_profile_wifi, R.drawable.ic_profile_mobile_data
    };

    static final int[] profileIconColor = {
            // default
            0xff1c9cd7,

            // home
            0xff99cc00, 0xff99cc00, 0xff99cc00, 0xff99cc00, 0xff99cc00,
            0xff99cc00,

            // outdoors
            0xffffbc33, 0xffffbc33, 0xffffbc33, 0xffffbc33, 0xffffbc33,
            0xffffbc33, 0xffffbc33, 0xffffbc33, 0xffffbc33, 0xffffbc33,

            // meeting, volume
            0xffcc0000, 0xffcc0000, 0xffcc0000, 0xffcc0000, 0xffcc0000,
            0xffcc0000, 0xffcc0000, 0xffcc0000, 0xffcc0000, 0xffcc0000,
            0xffcc0000,

            // work
            0xffa801ff, 0xffa801ff, 0xffa801ff, 0xffa801ff, 0xffa801ff,
            0xffa801ff, 0xffa801ff, 0xffa801ff, 0xffa801ff, 0xffa801ff,
            0xffa801ff, 0xffa801ff, 0xffa801ff, 0xffa801ff, 0xffa801ff,
            0xffa801ff, 0xffa801ff, 0xffa801ff,

            // sleep, food, alarm
            0xff0099cc, 0xff0099cc, 0xff0099cc, 0xff0099cc, 0xff0099cc,
            0xff0099cc, 0xff0099cc, 0xff0099cc, 0xff0099cc, 0xff0099cc,
            0xff0099cc,

            // car, airplane, ship, tickets
            0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174,
            0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174,
            0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174,
            0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174, 0xff28a174,
            0xff28a174, 0xff28a174,

            // culture
            0xffe91e63, 0xffe91e63, 0xffe91e63, 0xffe91e63, 0xffe91e63,
            0xffe91e63, 0xffe91e63, 0xffe91e63, 0xffe91e63, 0xffe91e63,
            0xffe91e63, 0xffe91e63, 0xffe91e63, 0xffe91e63, 0xffe91e63,
            0xffe91e63, 0xffe91e63,

            // battery
            0xffdb3514, 0xffdb9714, 0xff2aa561,

            //lock
            0xff6a3e18,

            // wifi, mobile data
            0xff1ea0df, 0xff1ea0df
    };

    static final HashMap<String, Integer> profileIconIdMap;
    static {
        profileIconIdMap = new HashMap<>();

        profileIconIdMap.put("ic_list_item_events_restart_color", R.drawable.ic_list_item_events_restart_color);

        profileIconIdMap.put(PROFILE_ICON_DEFAULT, R.drawable.ic_profile_default);
        profileIconIdMap.put("ic_profile_home", R.drawable.ic_profile_home);
        profileIconIdMap.put("ic_profile_home_2", R.drawable.ic_profile_home_2);
        profileIconIdMap.put("ic_profile_home_3", R.drawable.ic_profile_home_3);
        profileIconIdMap.put("ic_profile_home_4", R.drawable.ic_profile_home_4);
        profileIconIdMap.put("ic_profile_home_5", R.drawable.ic_profile_home_5);
        profileIconIdMap.put("ic_profile_home_6", R.drawable.ic_profile_home_6);
        profileIconIdMap.put("ic_profile_outdoors_1", R.drawable.ic_profile_outdoors_1);
        profileIconIdMap.put("ic_profile_outdoors_2", R.drawable.ic_profile_outdoors_2);
        profileIconIdMap.put("ic_profile_outdoors_3", R.drawable.ic_profile_outdoors_3);
        profileIconIdMap.put("ic_profile_outdoors_4", R.drawable.ic_profile_outdoors_4);
        profileIconIdMap.put("ic_profile_outdoors_5", R.drawable.ic_profile_outdoors_5);
        profileIconIdMap.put("ic_profile_outdoors_6", R.drawable.ic_profile_outdoors_6);
        profileIconIdMap.put("ic_profile_outdoors_7", R.drawable.ic_profile_outdoors_7);
        profileIconIdMap.put("ic_profile_outdoors_8", R.drawable.ic_profile_outdoors_8);
        profileIconIdMap.put("ic_profile_outdoors_9", R.drawable.ic_profile_outdoors_9);
        profileIconIdMap.put("ic_profile_running_1", R.drawable.ic_profile_running_1);
        profileIconIdMap.put("ic_profile_meeting", R.drawable.ic_profile_meeting);
        profileIconIdMap.put("ic_profile_meeting_2", R.drawable.ic_profile_meeting_2);
        profileIconIdMap.put("ic_profile_meeting_3", R.drawable.ic_profile_meeting_3);
        profileIconIdMap.put("ic_profile_meeting_4", R.drawable.ic_profile_meeting_4);
        profileIconIdMap.put("ic_profile_mute", R.drawable.ic_profile_mute);
        profileIconIdMap.put("ic_profile_mute_2", R.drawable.ic_profile_mute_2);
        profileIconIdMap.put("ic_profile_volume_4", R.drawable.ic_profile_volume_4);
        profileIconIdMap.put("ic_profile_volume_1", R.drawable.ic_profile_volume_1);
        profileIconIdMap.put("ic_profile_volume_2", R.drawable.ic_profile_volume_2);
        profileIconIdMap.put("ic_profile_volume_3", R.drawable.ic_profile_volume_3);
        profileIconIdMap.put("ic_profile_vibrate_1", R.drawable.ic_profile_vibrate_1);
        profileIconIdMap.put("ic_profile_work_1", R.drawable.ic_profile_work_1);
        profileIconIdMap.put("ic_profile_work_2", R.drawable.ic_profile_work_2);
        profileIconIdMap.put("ic_profile_work_12", R.drawable.ic_profile_work_12);
        profileIconIdMap.put("ic_profile_work_3", R.drawable.ic_profile_work_3);
        profileIconIdMap.put("ic_profile_work_4", R.drawable.ic_profile_work_4);
        profileIconIdMap.put("ic_profile_work_5", R.drawable.ic_profile_work_5);
        profileIconIdMap.put("ic_profile_work_6", R.drawable.ic_profile_work_6);
        profileIconIdMap.put("ic_profile_work_7", R.drawable.ic_profile_work_7);
        profileIconIdMap.put("ic_profile_work_8", R.drawable.ic_profile_work_8);
        profileIconIdMap.put("ic_profile_work_9", R.drawable.ic_profile_work_9);
        profileIconIdMap.put("ic_profile_work_10", R.drawable.ic_profile_work_10);
        profileIconIdMap.put("ic_profile_work_11", R.drawable.ic_profile_work_11);
        profileIconIdMap.put("ic_profile_work_13", R.drawable.ic_profile_work_13);
        profileIconIdMap.put("ic_profile_work_14", R.drawable.ic_profile_work_14);
        profileIconIdMap.put("ic_profile_work_15", R.drawable.ic_profile_work_15);
        profileIconIdMap.put("ic_profile_work_16", R.drawable.ic_profile_work_16);
        profileIconIdMap.put("ic_profile_work_17", R.drawable.ic_profile_work_17);
        profileIconIdMap.put("ic_profile_work_18", R.drawable.ic_profile_work_18);
        profileIconIdMap.put("ic_profile_sleep", R.drawable.ic_profile_sleep);
        profileIconIdMap.put("ic_profile_sleep_2", R.drawable.ic_profile_sleep_2);
        profileIconIdMap.put("ic_profile_sleep_3", R.drawable.ic_profile_sleep_3);
        profileIconIdMap.put("ic_profile_night", R.drawable.ic_profile_night);
        profileIconIdMap.put("ic_profile_call_1", R.drawable.ic_profile_call_1);
        profileIconIdMap.put("ic_profile_food_1", R.drawable.ic_profile_food_1);
        profileIconIdMap.put("ic_profile_food_2", R.drawable.ic_profile_food_2);
        profileIconIdMap.put("ic_profile_food_3", R.drawable.ic_profile_food_3);
        profileIconIdMap.put("ic_profile_food_4", R.drawable.ic_profile_food_4);
        profileIconIdMap.put("ic_profile_food_5", R.drawable.ic_profile_food_5);
        profileIconIdMap.put("ic_profile_alarm", R.drawable.ic_profile_alarm);
        profileIconIdMap.put("ic_profile_car_1", R.drawable.ic_profile_car_1);
        profileIconIdMap.put("ic_profile_car_2", R.drawable.ic_profile_car_2);
        profileIconIdMap.put("ic_profile_car_3", R.drawable.ic_profile_car_3);
        profileIconIdMap.put("ic_profile_car_4", R.drawable.ic_profile_car_4);
        profileIconIdMap.put("ic_profile_car_5", R.drawable.ic_profile_car_5);
        profileIconIdMap.put("ic_profile_car_6", R.drawable.ic_profile_car_6);
        profileIconIdMap.put("ic_profile_car_7", R.drawable.ic_profile_car_7);
        profileIconIdMap.put("ic_profile_car_8", R.drawable.ic_profile_car_8);
        profileIconIdMap.put("ic_profile_car_9", R.drawable.ic_profile_car_9);
        profileIconIdMap.put("ic_profile_car_10", R.drawable.ic_profile_car_10);
        profileIconIdMap.put("ic_profile_car_11", R.drawable.ic_profile_car_11);
        profileIconIdMap.put("ic_profile_steering_1", R.drawable.ic_profile_steering_1);
        profileIconIdMap.put("ic_profile_airplane_1", R.drawable.ic_profile_airplane_1);
        profileIconIdMap.put("ic_profile_airplane_2", R.drawable.ic_profile_airplane_2);
        profileIconIdMap.put("ic_profile_airplane_3", R.drawable.ic_profile_airplane_3);
        profileIconIdMap.put("ic_profile_ship_1", R.drawable.ic_profile_ship_1);
        profileIconIdMap.put("ic_profile_ship_2", R.drawable.ic_profile_ship_2);
        profileIconIdMap.put("ic_profile_ship_3", R.drawable.ic_profile_ship_3);
        profileIconIdMap.put("ic_profile_tram_1", R.drawable.ic_profile_tram_1);
        profileIconIdMap.put("ic_profile_tickets_1", R.drawable.ic_profile_tickets_1);
        profileIconIdMap.put("ic_profile_tickets_2", R.drawable.ic_profile_tickets_2);
        profileIconIdMap.put("ic_profile_travel_1", R.drawable.ic_profile_travel_1);
        profileIconIdMap.put("ic_profile_culture_1", R.drawable.ic_profile_culture_1);
        profileIconIdMap.put("ic_profile_culture_6", R.drawable.ic_profile_culture_6);
        profileIconIdMap.put("ic_profile_culture_7", R.drawable.ic_profile_culture_7);
        profileIconIdMap.put("ic_profile_culture_2", R.drawable.ic_profile_culture_2);
        profileIconIdMap.put("ic_profile_culture_8", R.drawable.ic_profile_culture_8);
        profileIconIdMap.put("ic_profile_culture_9", R.drawable.ic_profile_culture_9);
        profileIconIdMap.put("ic_profile_culture_3", R.drawable.ic_profile_culture_3);
        profileIconIdMap.put("ic_profile_culture_10", R.drawable.ic_profile_culture_10);
        profileIconIdMap.put("ic_profile_culture_11", R.drawable.ic_profile_culture_11);
        profileIconIdMap.put("ic_profile_culture_12", R.drawable.ic_profile_culture_12);
        profileIconIdMap.put("ic_profile_culture_13", R.drawable.ic_profile_culture_13);
        profileIconIdMap.put("ic_profile_culture_5", R.drawable.ic_profile_culture_5);
        profileIconIdMap.put("ic_profile_culture_14", R.drawable.ic_profile_culture_14);
        profileIconIdMap.put("ic_profile_culture_4", R.drawable.ic_profile_culture_4);
        profileIconIdMap.put("ic_profile_culture_15", R.drawable.ic_profile_culture_15);
        profileIconIdMap.put("ic_profile_culture_16", R.drawable.ic_profile_culture_16);
        profileIconIdMap.put("ic_profile_culture_17", R.drawable.ic_profile_culture_17);
        profileIconIdMap.put("ic_profile_battery_1", R.drawable.ic_profile_battery_1);
        profileIconIdMap.put("ic_profile_battery_2", R.drawable.ic_profile_battery_2);
        profileIconIdMap.put("ic_profile_battery_3", R.drawable.ic_profile_battery_3);
        profileIconIdMap.put("ic_profile_lock", R.drawable.ic_profile_lock);
        profileIconIdMap.put("ic_profile_wifi", R.drawable.ic_profile_wifi);
        profileIconIdMap.put("ic_profile_mobile_data", R.drawable.ic_profile_mobile_data);
    }
    
    static final HashMap<String, Integer> profileIconNotifyId;
    static {
        profileIconNotifyId = new HashMap<>();
        profileIconNotifyId.put(PROFILE_ICON_DEFAULT, R.drawable.ic_profile_default_notify);
        profileIconNotifyId.put("ic_profile_home", R.drawable.ic_profile_home_notify);
        profileIconNotifyId.put("ic_profile_home_2", R.drawable.ic_profile_home_2_notify);
        profileIconNotifyId.put("ic_profile_home_3", R.drawable.ic_profile_home_3_notify);
        profileIconNotifyId.put("ic_profile_home_4", R.drawable.ic_profile_home_4_notify);
        profileIconNotifyId.put("ic_profile_home_5", R.drawable.ic_profile_home_5_notify);
        profileIconNotifyId.put("ic_profile_home_6", R.drawable.ic_profile_home_6_notify);
        profileIconNotifyId.put("ic_profile_outdoors_1", R.drawable.ic_profile_outdoors_1_notify);
        profileIconNotifyId.put("ic_profile_outdoors_2", R.drawable.ic_profile_outdoors_2_notify);
        profileIconNotifyId.put("ic_profile_outdoors_3", R.drawable.ic_profile_outdoors_3_notify);
        profileIconNotifyId.put("ic_profile_outdoors_4", R.drawable.ic_profile_outdoors_4_notify);
        profileIconNotifyId.put("ic_profile_outdoors_5", R.drawable.ic_profile_outdoors_5_notify);
        profileIconNotifyId.put("ic_profile_outdoors_6", R.drawable.ic_profile_outdoors_6_notify);
        profileIconNotifyId.put("ic_profile_outdoors_7", R.drawable.ic_profile_outdoors_7_notify);
        profileIconNotifyId.put("ic_profile_outdoors_8", R.drawable.ic_profile_outdoors_8_notify);
        profileIconNotifyId.put("ic_profile_outdoors_9", R.drawable.ic_profile_outdoors_9_notify);
        profileIconNotifyId.put("ic_profile_running_1", R.drawable.ic_profile_running_1_notify);
        profileIconNotifyId.put("ic_profile_meeting", R.drawable.ic_profile_meeting_notify);
        profileIconNotifyId.put("ic_profile_meeting_2", R.drawable.ic_profile_meeting_2_notify);
        profileIconNotifyId.put("ic_profile_meeting_3", R.drawable.ic_profile_meeting_3_notify);
        profileIconNotifyId.put("ic_profile_meeting_4", R.drawable.ic_profile_meeting_4_notify);
        profileIconNotifyId.put("ic_profile_mute", R.drawable.ic_profile_mute_notify);
        profileIconNotifyId.put("ic_profile_mute_2", R.drawable.ic_profile_mute_2_notify);
        profileIconNotifyId.put("ic_profile_volume_4", R.drawable.ic_profile_volume_4_notify);
        profileIconNotifyId.put("ic_profile_volume_1", R.drawable.ic_profile_volume_1_notify);
        profileIconNotifyId.put("ic_profile_volume_2", R.drawable.ic_profile_volume_2_notify);
        profileIconNotifyId.put("ic_profile_volume_3", R.drawable.ic_profile_volume_3_notify);
        profileIconNotifyId.put("ic_profile_vibrate_1", R.drawable.ic_profile_vibrate_1_notify);
        profileIconNotifyId.put("ic_profile_work_1", R.drawable.ic_profile_work_1_notify);
        profileIconNotifyId.put("ic_profile_work_2", R.drawable.ic_profile_work_2_notify);
        profileIconNotifyId.put("ic_profile_work_12", R.drawable.ic_profile_work_12_notify);
        profileIconNotifyId.put("ic_profile_work_3", R.drawable.ic_profile_work_3_notify);
        profileIconNotifyId.put("ic_profile_work_4", R.drawable.ic_profile_work_4_notify);
        profileIconNotifyId.put("ic_profile_work_5", R.drawable.ic_profile_work_5_notify);
        profileIconNotifyId.put("ic_profile_work_6", R.drawable.ic_profile_work_6_notify);
        profileIconNotifyId.put("ic_profile_work_7", R.drawable.ic_profile_work_7_notify);
        profileIconNotifyId.put("ic_profile_work_8", R.drawable.ic_profile_work_8_notify);
        profileIconNotifyId.put("ic_profile_work_9", R.drawable.ic_profile_work_9_notify);
        profileIconNotifyId.put("ic_profile_work_10", R.drawable.ic_profile_work_10_notify);
        profileIconNotifyId.put("ic_profile_work_11", R.drawable.ic_profile_work_11_notify);
        profileIconNotifyId.put("ic_profile_work_13", R.drawable.ic_profile_work_13_notify);
        profileIconNotifyId.put("ic_profile_work_14", R.drawable.ic_profile_work_14_notify);
        profileIconNotifyId.put("ic_profile_work_15", R.drawable.ic_profile_work_15_notify);
        profileIconNotifyId.put("ic_profile_work_16", R.drawable.ic_profile_work_16_notify);
        profileIconNotifyId.put("ic_profile_work_17", R.drawable.ic_profile_work_17_notify);
        profileIconNotifyId.put("ic_profile_work_18", R.drawable.ic_profile_work_18_notify);
        profileIconNotifyId.put("ic_profile_sleep", R.drawable.ic_profile_sleep_notify);
        profileIconNotifyId.put("ic_profile_sleep_2", R.drawable.ic_profile_sleep_2_notify);
        profileIconNotifyId.put("ic_profile_sleep_3", R.drawable.ic_profile_sleep_3_notify);
        profileIconNotifyId.put("ic_profile_night", R.drawable.ic_profile_night_notify);
        profileIconNotifyId.put("ic_profile_call_1", R.drawable.ic_profile_call_1_notify);
        profileIconNotifyId.put("ic_profile_food_1", R.drawable.ic_profile_food_1_notify);
        profileIconNotifyId.put("ic_profile_food_2", R.drawable.ic_profile_food_2_notify);
        profileIconNotifyId.put("ic_profile_food_3", R.drawable.ic_profile_food_3_notify);
        profileIconNotifyId.put("ic_profile_food_4", R.drawable.ic_profile_food_4_notify);
        profileIconNotifyId.put("ic_profile_food_5", R.drawable.ic_profile_food_5_notify);
        profileIconNotifyId.put("ic_profile_alarm", R.drawable.ic_profile_alarm_notify);
        profileIconNotifyId.put("ic_profile_car_1", R.drawable.ic_profile_car_1_notify);
        profileIconNotifyId.put("ic_profile_car_2", R.drawable.ic_profile_car_2_notify);
        profileIconNotifyId.put("ic_profile_car_3", R.drawable.ic_profile_car_3_notify);
        profileIconNotifyId.put("ic_profile_car_4", R.drawable.ic_profile_car_4_notify);
        profileIconNotifyId.put("ic_profile_car_5", R.drawable.ic_profile_car_5_notify);
        profileIconNotifyId.put("ic_profile_car_6", R.drawable.ic_profile_car_6_notify);
        profileIconNotifyId.put("ic_profile_car_7", R.drawable.ic_profile_car_7_notify);
        profileIconNotifyId.put("ic_profile_car_8", R.drawable.ic_profile_car_8_notify);
        profileIconNotifyId.put("ic_profile_car_9", R.drawable.ic_profile_car_9_notify);
        profileIconNotifyId.put("ic_profile_car_10", R.drawable.ic_profile_car_10_notify);
        profileIconNotifyId.put("ic_profile_car_11", R.drawable.ic_profile_car_11_notify);
        profileIconNotifyId.put("ic_profile_steering_1", R.drawable.ic_profile_steering_1_notify);
        profileIconNotifyId.put("ic_profile_airplane_1", R.drawable.ic_profile_airplane_1_notify);
        profileIconNotifyId.put("ic_profile_airplane_2", R.drawable.ic_profile_airplane_2_notify);
        profileIconNotifyId.put("ic_profile_airplane_3", R.drawable.ic_profile_airplane_3_notify);
        profileIconNotifyId.put("ic_profile_ship_1", R.drawable.ic_profile_ship_1_notify);
        profileIconNotifyId.put("ic_profile_ship_2", R.drawable.ic_profile_ship_2_notify);
        profileIconNotifyId.put("ic_profile_ship_3", R.drawable.ic_profile_ship_3_notify);
        profileIconNotifyId.put("ic_profile_tram_1", R.drawable.ic_profile_tram_1_notify);
        profileIconNotifyId.put("ic_profile_tickets_1", R.drawable.ic_profile_tickets_1_notify);
        profileIconNotifyId.put("ic_profile_tickets_2", R.drawable.ic_profile_tickets_2_notify);
        profileIconNotifyId.put("ic_profile_travel_1", R.drawable.ic_profile_travel_1_notify);
        profileIconNotifyId.put("ic_profile_culture_1", R.drawable.ic_profile_culture_1_notify);
        profileIconNotifyId.put("ic_profile_culture_6", R.drawable.ic_profile_culture_6_notify);
        profileIconNotifyId.put("ic_profile_culture_7", R.drawable.ic_profile_culture_7_notify);
        profileIconNotifyId.put("ic_profile_culture_2", R.drawable.ic_profile_culture_2_notify);
        profileIconNotifyId.put("ic_profile_culture_8", R.drawable.ic_profile_culture_8_notify);
        profileIconNotifyId.put("ic_profile_culture_9", R.drawable.ic_profile_culture_9_notify);
        profileIconNotifyId.put("ic_profile_culture_3", R.drawable.ic_profile_culture_3_notify);
        profileIconNotifyId.put("ic_profile_culture_10", R.drawable.ic_profile_culture_10_notify);
        profileIconNotifyId.put("ic_profile_culture_11", R.drawable.ic_profile_culture_11_notify);
        profileIconNotifyId.put("ic_profile_culture_12", R.drawable.ic_profile_culture_12_notify);
        profileIconNotifyId.put("ic_profile_culture_13", R.drawable.ic_profile_culture_13_notify);
        profileIconNotifyId.put("ic_profile_culture_5", R.drawable.ic_profile_culture_5_notify);
        profileIconNotifyId.put("ic_profile_culture_14", R.drawable.ic_profile_culture_14_notify);
        profileIconNotifyId.put("ic_profile_culture_4", R.drawable.ic_profile_culture_4_notify);
        profileIconNotifyId.put("ic_profile_culture_15", R.drawable.ic_profile_culture_15_notify);
        profileIconNotifyId.put("ic_profile_culture_16", R.drawable.ic_profile_culture_16_notify);
        profileIconNotifyId.put("ic_profile_culture_17", R.drawable.ic_profile_culture_17_notify);
        profileIconNotifyId.put("ic_profile_battery_1", R.drawable.ic_profile_battery_1_notify);
        profileIconNotifyId.put("ic_profile_battery_2", R.drawable.ic_profile_battery_2_notify);
        profileIconNotifyId.put("ic_profile_battery_3", R.drawable.ic_profile_battery_3_notify);
        profileIconNotifyId.put("ic_profile_lock", R.drawable.ic_profile_lock_notify);
        profileIconNotifyId.put("ic_profile_wifi", R.drawable.ic_profile_wifi_notify);
        profileIconNotifyId.put("ic_profile_mobile_data", R.drawable.ic_profile_mobile_data_notify);
    }

    static final HashMap<String, Integer> profileIconNotifyColorId;
    static {
        profileIconNotifyColorId = new HashMap<>();
        profileIconNotifyColorId.put(PROFILE_ICON_DEFAULT, R.drawable.ic_profile_default_notify_color);
        profileIconNotifyColorId.put("ic_profile_home", R.drawable.ic_profile_home_notify_color);
        profileIconNotifyColorId.put("ic_profile_home_2", R.drawable.ic_profile_home_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_home_3", R.drawable.ic_profile_home_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_home_4", R.drawable.ic_profile_home_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_home_5", R.drawable.ic_profile_home_5_notify_color);
        profileIconNotifyColorId.put("ic_profile_home_6", R.drawable.ic_profile_home_6_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_1", R.drawable.ic_profile_outdoors_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_2", R.drawable.ic_profile_outdoors_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_3", R.drawable.ic_profile_outdoors_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_4", R.drawable.ic_profile_outdoors_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_5", R.drawable.ic_profile_outdoors_5_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_6", R.drawable.ic_profile_outdoors_6_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_7", R.drawable.ic_profile_outdoors_7_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_8", R.drawable.ic_profile_outdoors_8_notify_color);
        profileIconNotifyColorId.put("ic_profile_outdoors_9", R.drawable.ic_profile_outdoors_9_notify_color);
        profileIconNotifyColorId.put("ic_profile_running_1", R.drawable.ic_profile_running_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_meeting", R.drawable.ic_profile_meeting_notify_color);
        profileIconNotifyColorId.put("ic_profile_meeting_2", R.drawable.ic_profile_meeting_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_meeting_3", R.drawable.ic_profile_meeting_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_meeting_4", R.drawable.ic_profile_meeting_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_mute", R.drawable.ic_profile_mute_notify_color);
        profileIconNotifyColorId.put("ic_profile_mute_2", R.drawable.ic_profile_mute_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_volume_4", R.drawable.ic_profile_volume_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_volume_1", R.drawable.ic_profile_volume_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_volume_2", R.drawable.ic_profile_volume_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_volume_3", R.drawable.ic_profile_volume_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_vibrate_1", R.drawable.ic_profile_vibrate_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_1", R.drawable.ic_profile_work_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_2", R.drawable.ic_profile_work_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_12", R.drawable.ic_profile_work_12_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_3", R.drawable.ic_profile_work_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_4", R.drawable.ic_profile_work_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_5", R.drawable.ic_profile_work_5_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_6", R.drawable.ic_profile_work_6_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_7", R.drawable.ic_profile_work_7_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_8", R.drawable.ic_profile_work_8_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_9", R.drawable.ic_profile_work_9_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_10", R.drawable.ic_profile_work_10_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_11", R.drawable.ic_profile_work_11_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_13", R.drawable.ic_profile_work_13_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_14", R.drawable.ic_profile_work_14_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_15", R.drawable.ic_profile_work_15_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_16", R.drawable.ic_profile_work_16_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_17", R.drawable.ic_profile_work_17_notify_color);
        profileIconNotifyColorId.put("ic_profile_work_18", R.drawable.ic_profile_work_18_notify_color);
        profileIconNotifyColorId.put("ic_profile_sleep", R.drawable.ic_profile_sleep_notify_color);
        profileIconNotifyColorId.put("ic_profile_sleep_2", R.drawable.ic_profile_sleep_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_sleep_3", R.drawable.ic_profile_sleep_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_night", R.drawable.ic_profile_night_notify_color);
        profileIconNotifyColorId.put("ic_profile_call_1", R.drawable.ic_profile_call_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_food_1", R.drawable.ic_profile_food_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_food_2", R.drawable.ic_profile_food_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_food_3", R.drawable.ic_profile_food_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_food_4", R.drawable.ic_profile_food_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_food_5", R.drawable.ic_profile_food_5_notify_color);
        profileIconNotifyColorId.put("ic_profile_alarm", R.drawable.ic_profile_alarm_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_1", R.drawable.ic_profile_car_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_2", R.drawable.ic_profile_car_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_3", R.drawable.ic_profile_car_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_4", R.drawable.ic_profile_car_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_5", R.drawable.ic_profile_car_5_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_6", R.drawable.ic_profile_car_6_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_7", R.drawable.ic_profile_car_7_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_8", R.drawable.ic_profile_car_8_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_9", R.drawable.ic_profile_car_9_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_10", R.drawable.ic_profile_car_10_notify_color);
        profileIconNotifyColorId.put("ic_profile_car_11", R.drawable.ic_profile_car_11_notify_color);
        profileIconNotifyColorId.put("ic_profile_steering_1", R.drawable.ic_profile_steering_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_airplane_1", R.drawable.ic_profile_airplane_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_airplane_2", R.drawable.ic_profile_airplane_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_airplane_3", R.drawable.ic_profile_airplane_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_ship_1", R.drawable.ic_profile_ship_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_ship_2", R.drawable.ic_profile_ship_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_ship_3", R.drawable.ic_profile_ship_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_tram_1", R.drawable.ic_profile_tram_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_tickets_1", R.drawable.ic_profile_tickets_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_tickets_2", R.drawable.ic_profile_tickets_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_travel_1", R.drawable.ic_profile_travel_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_1", R.drawable.ic_profile_culture_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_6", R.drawable.ic_profile_culture_6_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_7", R.drawable.ic_profile_culture_7_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_2", R.drawable.ic_profile_culture_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_8", R.drawable.ic_profile_culture_8_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_9", R.drawable.ic_profile_culture_9_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_3", R.drawable.ic_profile_culture_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_10", R.drawable.ic_profile_culture_10_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_11", R.drawable.ic_profile_culture_11_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_12", R.drawable.ic_profile_culture_12_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_13", R.drawable.ic_profile_culture_13_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_5", R.drawable.ic_profile_culture_5_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_14", R.drawable.ic_profile_culture_14_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_4", R.drawable.ic_profile_culture_4_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_15", R.drawable.ic_profile_culture_15_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_16", R.drawable.ic_profile_culture_16_notify_color);
        profileIconNotifyColorId.put("ic_profile_culture_17", R.drawable.ic_profile_culture_17_notify_color);
        profileIconNotifyColorId.put("ic_profile_battery_1", R.drawable.ic_profile_battery_1_notify_color);
        profileIconNotifyColorId.put("ic_profile_battery_2", R.drawable.ic_profile_battery_2_notify_color);
        profileIconNotifyColorId.put("ic_profile_battery_3", R.drawable.ic_profile_battery_3_notify_color);
        profileIconNotifyColorId.put("ic_profile_lock", R.drawable.ic_profile_lock_notify_color);
        profileIconNotifyColorId.put("ic_profile_wifi", R.drawable.ic_profile_wifi_notify_color);
        profileIconNotifyColorId.put("ic_profile_mobile_data", R.drawable.ic_profile_mobile_data_notify_color);
    }

    static final int PARAMETER_TYPE_WIFI = 1;
    static final int PARAMETER_TYPE_WIFIAP = 2;
    static final int PARAMETER_CLOSE_ALL_APPLICATION = 3;

    // Empty constructor
    Profile(){
        //this._useCustomColor = true;
        //this._customColor = Color.YELLOW;

        this._iconBitmap = null;
    }

    // constructor
    Profile(long id,
                   String name,
                   String icon,
                   Boolean checked,
                   int porder,
                   int volumeRingerMode,
                   String volumeRingtone,
                   String volumeNotification,
                   String volumeMedia,
                   String volumeAlarm,
                   String volumeSystem,
                   String volumeVoice,
                   int soundRingtoneChange,
                   String soundRingtone,
                   int soundNotificationChange,
                   String soundNotification,
                   int soundAlarmChange,
                   String soundAlarm,
                   int deviceAirplaneMode,
                   int deviceWiFi,
                   int deviceBluetooth,
                   int deviceScreenTimeout,
                   String deviceBrightness,
                   int deviceWallpaperChange,
                   String deviceWallpaper,
                   int deviceMobileData,
                   int deviceMobileDataPrefs,
                   int deviceGPS,
                   int deviceRunApplicationChange,
                   String deviceRunApplicationPackageName,
                   int deviceAutoSync,
                   boolean showInActivator,
                   int deviceAutoRotate,
                   int deviceLocationServicePrefs,
                   int volumeSpeakerPhone,
                   int deviceNFC,
                   int duration,
                   int afterDurationDo,
                   int volumeZenMode,
                   int deviceKeyguard,
                   int vibrationOnTouch,
                   int deviceWifiAP,
                   int devicePowerSaveMode,
                   boolean askForDuration,
                   int deviceNetworkType,
                   int notificationLed,
                   int vibrateWhenRinging,
                   int deviceWallpaperFor,
                   boolean hideStatusBarIcon,
                   int lockDevice,
                   String deviceConnectToSSID,
                   int applicationDisableWifiScanning,
                   int applicationDisableBluetoothScanning,
                   String durationNotificationSound,
                   boolean durationNotificationVibrate,
                   int deviceWiFiAPPrefs,
                   int applicationDisableLocationScanning,
                   int applicationDisableMobileCellScanning,
                   int applicationDisableOrientationScanning,
                   int headsUpNotifications,
                   int deviceForceStopApplicationChange,
                   String deviceForceStopApplicationPackageName,
                   long activationByUserCount,
                   int deviceNetworkTypePrefs,
                   int deviceCloseAllApplications,
                   int screenDarkMode,
                   int dtmfToneWhenDialing,
                   int soundOnTouch,
                   String volumeDTMF,
                   String volumeAccessibility,
                   String volumeBluetoothSCO,
                   long afterDurationProfile,
                   int alwaysOnDisplay,
                   int screenOnPermanent,
                   boolean volumeMuteSound,
                   int deviceLocationMode,
                   int applicationDisableNotificationScanning,
                   String generateNotification,
                   int cameraFlash,
                   int deviceNetworkTypeSIM1,
                   int deviceNetworkTypeSIM2,
                   int deviceMobileDataSIM1,
                   int deviceMobileDataSIM2,
                   String deviceDefaultSIMCards,
                   int deviceOnOffSIM1,
                   int deviceOnOffSIM2,
                   int soundRingtoneChangeSIM1,
                   String soundRingtoneSIM1,
                   int soundRingtoneChangeSIM2,
                   String soundRingtoneSIM2,
                   int soundNotificationChangeSIM1,
                   String soundNotificationSIM1,
                   int soundNotificationChangeSIM2,
                   String soundNotificationSIM2,
                   int soundSameRingtoneForBothSIMCards
    )
    {
        this._id = id;
        this._name = name;
        this._icon = icon;
        this._checked = checked;
        this._porder = porder;
        this._volumeRingerMode = volumeRingerMode;
        this._volumeZenMode = volumeZenMode;
        this._volumeRingtone = volumeRingtone;
        this._volumeNotification = volumeNotification;
        this._volumeMedia = volumeMedia;
        this._volumeAlarm = volumeAlarm;
        this._volumeSystem = volumeSystem;
        this._volumeVoice = volumeVoice;
        this._soundRingtoneChange = soundRingtoneChange;
        this._soundRingtone = soundRingtone;
        this._soundNotificationChange = soundNotificationChange;
        this._soundNotification = soundNotification;
        this._soundAlarmChange = soundAlarmChange;
        this._soundAlarm = soundAlarm;
        this._deviceAirplaneMode = deviceAirplaneMode;
        this._deviceMobileData = deviceMobileData;
        this._deviceMobileDataPrefs = deviceMobileDataPrefs;
        this._deviceWiFi = deviceWiFi;
        this._deviceBluetooth = deviceBluetooth;
        this._deviceGPS = deviceGPS;
        this._deviceScreenTimeout = deviceScreenTimeout;
        this._deviceBrightness = deviceBrightness;
        this._deviceWallpaperChange = deviceWallpaperChange;
        this._deviceWallpaper = deviceWallpaper;
        this._deviceRunApplicationChange = deviceRunApplicationChange;
        this._deviceRunApplicationPackageName = deviceRunApplicationPackageName;
        this._deviceAutoSync = deviceAutoSync;
        this._showInActivator = showInActivator;
        this._deviceAutoRotate = deviceAutoRotate;
        this._deviceLocationServicePrefs = deviceLocationServicePrefs;
        this._volumeSpeakerPhone = volumeSpeakerPhone;
        this._deviceNFC = deviceNFC;
        this._duration = duration;
        this._afterDurationDo = afterDurationDo;
        this._askForDuration = askForDuration;
        this._durationNotificationSound = durationNotificationSound;
        this._durationNotificationVibrate = durationNotificationVibrate;
        this._deviceKeyguard = deviceKeyguard;
        this._vibrationOnTouch = vibrationOnTouch;
        this._deviceWiFiAP = deviceWifiAP;
        this._devicePowerSaveMode = devicePowerSaveMode;
        this._deviceNetworkType = deviceNetworkType;
        this._notificationLed = notificationLed;
        this._vibrateWhenRinging = vibrateWhenRinging;
        this._deviceWallpaperFor = deviceWallpaperFor;
        this._hideStatusBarIcon = hideStatusBarIcon;
        this._lockDevice = lockDevice;
        this._deviceConnectToSSID = deviceConnectToSSID;
        this._applicationDisableWifiScanning = applicationDisableWifiScanning;
        this._applicationDisableBluetoothScanning = applicationDisableBluetoothScanning;
        this._deviceWiFiAPPrefs = deviceWiFiAPPrefs;
        this._applicationDisableLocationScanning = applicationDisableLocationScanning;
        this._applicationDisableMobileCellScanning = applicationDisableMobileCellScanning;
        this._applicationDisableOrientationScanning = applicationDisableOrientationScanning;
        this._headsUpNotifications = headsUpNotifications;
        this._deviceForceStopApplicationChange = deviceForceStopApplicationChange;
        this._deviceForceStopApplicationPackageName = deviceForceStopApplicationPackageName;
        this._deviceNetworkTypePrefs = deviceNetworkTypePrefs;
        this._deviceCloseAllApplications = deviceCloseAllApplications;
        this._screenDarkMode = screenDarkMode;
        this._dtmfToneWhenDialing = dtmfToneWhenDialing;
        this._soundOnTouch = soundOnTouch;
        this._volumeDTMF = volumeDTMF;
        this._volumeAccessibility = volumeAccessibility;
        this._volumeBluetoothSCO = volumeBluetoothSCO;
        this._afterDurationProfile = afterDurationProfile;
        this._alwaysOnDisplay = alwaysOnDisplay;
        this._screenOnPermanent = screenOnPermanent;
        this._volumeMuteSound = volumeMuteSound;
        this._deviceLocationMode = deviceLocationMode;
        this._applicationDisableNotificationScanning = applicationDisableNotificationScanning;
        this._generateNotification = generateNotification;
        this._cameraFlash = cameraFlash;
        this._deviceNetworkTypeSIM1 = deviceNetworkTypeSIM1;
        this._deviceNetworkTypeSIM2 = deviceNetworkTypeSIM2;
        this._deviceMobileDataSIM1 = deviceMobileDataSIM1;
        this._deviceMobileDataSIM2 = deviceMobileDataSIM2;
        this._deviceDefaultSIMCards = deviceDefaultSIMCards;
        this._deviceOnOffSIM1 = deviceOnOffSIM1;
        this._deviceOnOffSIM2 = deviceOnOffSIM2;
        this._soundRingtoneChangeSIM1 = soundRingtoneChangeSIM1;
        this._soundRingtoneSIM1 = soundRingtoneSIM1;
        this._soundRingtoneChangeSIM2 = soundRingtoneChangeSIM2;
        this._soundRingtoneSIM2 = soundRingtoneSIM2;
        this._soundNotificationChangeSIM1 = soundNotificationChangeSIM1;
        this._soundNotificationSIM1 = soundNotificationSIM1;
        this._soundNotificationChangeSIM2 = soundNotificationChangeSIM2;
        this._soundNotificationSIM2 = soundNotificationSIM2;
        this._soundSameRingtoneForBothSIMCards = soundSameRingtoneForBothSIMCards;

        this._iconBitmap = null;
        this._preferencesIndicator = null;
        this._activationByUserCount = activationByUserCount;
    }

    // constructor
    Profile(String name,
                   String icon,
                   @SuppressWarnings("SameParameterValue") Boolean checked,
                   int porder,
                   int volumeRingerMode,
                   String volumeRingtone,
                   String volumeNotification,
                   String volumeMedia,
                   String volumeAlarm,
                   String volumeSystem,
                   String volumeVoice,
                   int soundRingtoneChange,
                   String soundRingtone,
                   int soundNotificationChange,
                   String soundNotification,
                   int soundAlarmChange,
                   String soundAlarm,
                   int deviceAirplaneMode,
                   int deviceWiFi,
                   int deviceBluetooth,
                   int deviceScreenTimeout,
                   String deviceBrightness,
                   int deviceWallpaperChange,
                   String deviceWallpaper,
                   int deviceMobileData,
                   int deviceMobileDataPrefs,
                   int deviceGPS,
                   int deviceRunApplicationChange,
                   String deviceRunApplicationPackageName,
                   int deviceAutoSync,
                   boolean showInActivator,
                   int deviceAutoRotate,
                   int deviceLocationServicePrefs,
                   int volumeSpeakerPhone,
                   int deviceNFC,
                   int duration,
                   int afterDurationDo,
                   int volumeZenMode,
                   int deviceKeyguard,
                   int vibrationOnTouch,
                   int deviceWiFiAP,
                   int devicePowerSaveMode,
                   boolean askForDuration,
                   int deviceNetworkType,
                   int notificationLed,
                   int vibrateWhenRinging,
                   int deviceWallpaperFor,
                   boolean hideStatusBarIcon,
                   int lockDevice,
                   String deviceConnectToSSID,
                   int applicationDisableWifiScanning,
                   int applicationDisableBluetoothScanning,
                   String durationNotificationSound,
                   boolean durationNotificationVibrate,
                   int deviceWiFiAPPrefs,
                   int applicationDisableLocationScanning,
                   int applicationDisableMobileCellScanning,
                   int applicationDisableOrientationScanning,
                   int headsUpNotifications,
                   int deviceForceStopApplicationChange,
                   String deviceForceStopApplicationPackageName,
                   long activationByUserCount,
                   int deviceNetworkTypePrefs,
                   int deviceCloseAllApplications,
                   int screenDarkMode,
                   int dtmfToneWhenDialing,
                   int soundOnTouch,
                   String volumeDTMF,
                   String volumeAccessibility,
                   String volumeBluetoothSCO,
                   long afterDurationProfile,
                   int alwaysOnDisplay,
                   int screenOnPermanent,
                   boolean volumeMuteSound,
                   int deviceLocationMode,
                   int applicationDisableNotificationScanning,
                   String generateNotification,
                   int cameraFlash,
                   int deviceNetworkTypeSIM1,
                   int deviceNetworkTypeSIM2,
                   int deviceMobileDataSIM1,
                   int deviceMobileDataSIM2,
                   String deviceDefaultSIMCards,
                   int deviceOnOffSIM1,
                   int deviceOnOffSIM2,
                   int soundRingtoneChangeSIM1,
                   String soundRingtoneSIM1,
                   int soundRingtoneChangeSIM2,
                   String soundRingtoneSIM2,
                   int soundNotificationChangeSIM1,
                   String soundNotificationSIM1,
                   int soundNotificationChangeSIM2,
                   String soundNotificationSIM2,
                   int soundSameRingtoneForBothSIMCards
    )
    {
        this._name = name;
        this._icon = icon;
        this._checked = checked;
        this._porder = porder;
        this._volumeRingerMode = volumeRingerMode;
        this._volumeZenMode = volumeZenMode;
        this._volumeRingtone = volumeRingtone;
        this._volumeNotification = volumeNotification;
        this._volumeMedia = volumeMedia;
        this._volumeAlarm = volumeAlarm;
        this._volumeSystem = volumeSystem;
        this._volumeVoice = volumeVoice;
        this._soundRingtoneChange = soundRingtoneChange;
        this._soundRingtone = soundRingtone;
        this._soundNotificationChange = soundNotificationChange;
        this._soundNotification = soundNotification;
        this._soundAlarmChange = soundAlarmChange;
        this._soundAlarm = soundAlarm;
        this._deviceAirplaneMode = deviceAirplaneMode;
        this._deviceMobileData = deviceMobileData;
        this._deviceMobileDataPrefs = deviceMobileDataPrefs;
        this._deviceWiFi = deviceWiFi;
        this._deviceBluetooth = deviceBluetooth;
        this._deviceGPS = deviceGPS;
        this._deviceScreenTimeout = deviceScreenTimeout;
        this._deviceBrightness = deviceBrightness;
        this._deviceWallpaperChange = deviceWallpaperChange;
        this._deviceWallpaper = deviceWallpaper;
        this._deviceRunApplicationChange = deviceRunApplicationChange;
        this._deviceRunApplicationPackageName = deviceRunApplicationPackageName;
        this._deviceAutoSync = deviceAutoSync;
        this._showInActivator = showInActivator;
        this._deviceAutoRotate = deviceAutoRotate;
        this._deviceLocationServicePrefs = deviceLocationServicePrefs;
        this._volumeSpeakerPhone = volumeSpeakerPhone;
        this._deviceNFC = deviceNFC;
        this._duration = duration;
        this._afterDurationDo = afterDurationDo;
        this._askForDuration = askForDuration;
        this._durationNotificationSound = durationNotificationSound;
        this._durationNotificationVibrate = durationNotificationVibrate;
        this._deviceKeyguard = deviceKeyguard;
        this._vibrationOnTouch = vibrationOnTouch;
        this._deviceWiFiAP = deviceWiFiAP;
        this._devicePowerSaveMode = devicePowerSaveMode;
        this._deviceNetworkType = deviceNetworkType;
        this._notificationLed = notificationLed;
        this._vibrateWhenRinging = vibrateWhenRinging;
        this._deviceWallpaperFor = deviceWallpaperFor;
        this._hideStatusBarIcon = hideStatusBarIcon;
        this._lockDevice = lockDevice;
        this._deviceConnectToSSID = deviceConnectToSSID;
        this._applicationDisableWifiScanning = applicationDisableWifiScanning;
        this._applicationDisableBluetoothScanning = applicationDisableBluetoothScanning;
        this._deviceWiFiAPPrefs = deviceWiFiAPPrefs;
        this._applicationDisableLocationScanning = applicationDisableLocationScanning;
        this._applicationDisableMobileCellScanning = applicationDisableMobileCellScanning;
        this._applicationDisableOrientationScanning = applicationDisableOrientationScanning;
        this._headsUpNotifications = headsUpNotifications;
        this._deviceForceStopApplicationChange = deviceForceStopApplicationChange;
        this._deviceForceStopApplicationPackageName = deviceForceStopApplicationPackageName;
        this._deviceNetworkTypePrefs = deviceNetworkTypePrefs;
        this._deviceCloseAllApplications = deviceCloseAllApplications;
        this._screenDarkMode = screenDarkMode;
        this._dtmfToneWhenDialing = dtmfToneWhenDialing;
        this._soundOnTouch = soundOnTouch;
        this._volumeDTMF = volumeDTMF;
        this._volumeAccessibility = volumeAccessibility;
        this._volumeBluetoothSCO = volumeBluetoothSCO;
        this._afterDurationProfile = afterDurationProfile;
        this._alwaysOnDisplay = alwaysOnDisplay;
        this._screenOnPermanent = screenOnPermanent;
        this._volumeMuteSound = volumeMuteSound;
        this._deviceLocationMode = deviceLocationMode;
        this._applicationDisableNotificationScanning = applicationDisableNotificationScanning;
        this._generateNotification = generateNotification;
        this._cameraFlash = cameraFlash;
        this._deviceNetworkTypeSIM1 = deviceNetworkTypeSIM1;
        this._deviceNetworkTypeSIM2 = deviceNetworkTypeSIM2;
        this._deviceMobileDataSIM1 = deviceMobileDataSIM1;
        this._deviceMobileDataSIM2 = deviceMobileDataSIM2;
        this._deviceDefaultSIMCards = deviceDefaultSIMCards;
        this._deviceOnOffSIM1 = deviceOnOffSIM1;
        this._deviceOnOffSIM2 = deviceOnOffSIM2;
        this._soundRingtoneChangeSIM1 = soundRingtoneChangeSIM1;
        this._soundRingtoneSIM1 = soundRingtoneSIM1;
        this._soundRingtoneChangeSIM2 = soundRingtoneChangeSIM2;
        this._soundRingtoneSIM2 = soundRingtoneSIM2;
        this._soundNotificationChangeSIM1 = soundNotificationChangeSIM1;
        this._soundNotificationSIM1 = soundNotificationSIM1;
        this._soundNotificationChangeSIM2 = soundNotificationChangeSIM2;
        this._soundNotificationSIM2 = soundNotificationSIM2;
        this._soundSameRingtoneForBothSIMCards = soundSameRingtoneForBothSIMCards;

        this._iconBitmap = null;
        this._preferencesIndicator = null;
        this._activationByUserCount = activationByUserCount;
    }

    void copyProfile(Profile profile)
    {
        this._id = profile._id;
        this._name = profile._name;
        this._icon = profile._icon;
        this._checked = profile._checked;
        this._porder = profile._porder;
        this._volumeRingerMode = profile._volumeRingerMode;
        this._volumeZenMode = profile._volumeZenMode;
        this._volumeRingtone = profile._volumeRingtone;
        this._volumeNotification = profile._volumeNotification;
        this._volumeMedia = profile._volumeMedia;
        this._volumeAlarm = profile._volumeAlarm;
        this._volumeSystem = profile._volumeSystem;
        this._volumeVoice = profile._volumeVoice;
        this._soundRingtoneChange = profile._soundRingtoneChange;
        this._soundRingtone = profile._soundRingtone;
        this._soundNotificationChange = profile._soundNotificationChange;
        this._soundNotification = profile._soundNotification;
        this._soundAlarmChange = profile._soundAlarmChange;
        this._soundAlarm = profile._soundAlarm;
        this._deviceAirplaneMode = profile._deviceAirplaneMode;
        this._deviceMobileData = profile._deviceMobileData;
        this._deviceMobileDataPrefs = profile._deviceMobileDataPrefs;
        this._deviceWiFi = profile._deviceWiFi;
        this._deviceBluetooth = profile._deviceBluetooth;
        this._deviceGPS = profile._deviceGPS;
        this._deviceScreenTimeout = profile._deviceScreenTimeout;
        this._deviceBrightness = profile._deviceBrightness;
        this._deviceWallpaperChange = profile._deviceWallpaperChange;
        this._deviceWallpaper = profile._deviceWallpaper;
        this._deviceRunApplicationChange = profile._deviceRunApplicationChange;
        this._deviceRunApplicationPackageName = profile._deviceRunApplicationPackageName;
        this._deviceAutoSync = profile._deviceAutoSync;
        this._showInActivator = profile._showInActivator;
        this._deviceAutoRotate = profile._deviceAutoRotate;
        this._deviceLocationServicePrefs = profile._deviceLocationServicePrefs;
        this._volumeSpeakerPhone = profile._volumeSpeakerPhone;
        this._deviceNFC = profile._deviceNFC;
        this._duration = profile._duration;
        this._afterDurationDo = profile._afterDurationDo;
        this._askForDuration = profile._askForDuration;
        this._durationNotificationSound = profile._durationNotificationSound;
        this._durationNotificationVibrate = profile._durationNotificationVibrate;
        this._deviceKeyguard = profile._deviceKeyguard;
        this._vibrationOnTouch = profile._vibrationOnTouch;
        this._deviceWiFiAP = profile._deviceWiFiAP;
        this._devicePowerSaveMode = profile._devicePowerSaveMode;
        this._deviceNetworkType = profile._deviceNetworkType;
        this._notificationLed = profile._notificationLed;
        this._vibrateWhenRinging = profile._vibrateWhenRinging;
        this._deviceWallpaperFor = profile._deviceWallpaperFor;
        this._hideStatusBarIcon = profile._hideStatusBarIcon;
        this._lockDevice = profile._lockDevice;
        this._deviceConnectToSSID = profile._deviceConnectToSSID;
        this._applicationDisableWifiScanning = profile._applicationDisableWifiScanning;
        this._applicationDisableBluetoothScanning = profile._applicationDisableBluetoothScanning;
        this._deviceWiFiAPPrefs = profile._deviceWiFiAPPrefs;
        this._applicationDisableLocationScanning = profile._applicationDisableLocationScanning;
        this._applicationDisableMobileCellScanning = profile._applicationDisableMobileCellScanning;
        this._applicationDisableOrientationScanning = profile._applicationDisableOrientationScanning;
        this._headsUpNotifications = profile._headsUpNotifications;
        this._deviceForceStopApplicationChange = profile._deviceForceStopApplicationChange;
        this._deviceForceStopApplicationPackageName = profile._deviceForceStopApplicationPackageName;
        this._deviceNetworkTypePrefs = profile._deviceNetworkTypePrefs;
        this._deviceCloseAllApplications = profile._deviceCloseAllApplications;
        this._screenDarkMode = profile._screenDarkMode;
        this._dtmfToneWhenDialing = profile._dtmfToneWhenDialing;
        this._soundOnTouch = profile._soundOnTouch;
        this._volumeDTMF = profile._volumeDTMF;
        this._volumeAccessibility = profile._volumeAccessibility;
        this._volumeBluetoothSCO = profile._volumeBluetoothSCO;
        this._afterDurationProfile = profile._afterDurationProfile;
        this._alwaysOnDisplay = profile._alwaysOnDisplay;
        this._screenOnPermanent = profile._screenOnPermanent;
        this._volumeMuteSound = profile._volumeMuteSound;
        this._deviceLocationMode = profile._deviceLocationMode;
        this._applicationDisableNotificationScanning = profile._applicationDisableNotificationScanning;
        this._generateNotification = profile._generateNotification;
        this._cameraFlash = profile._cameraFlash;
        this._deviceNetworkTypeSIM1 = profile._deviceNetworkTypeSIM1;
        this._deviceNetworkTypeSIM2 = profile._deviceNetworkTypeSIM2;
        this._deviceMobileDataSIM1 = profile._deviceMobileDataSIM1;
        this._deviceMobileDataSIM2 = profile._deviceMobileDataSIM2;
        this._deviceDefaultSIMCards = profile._deviceDefaultSIMCards;
        this._deviceOnOffSIM1 = profile._deviceOnOffSIM1;
        this._deviceOnOffSIM2 = profile._deviceOnOffSIM2;
        this._soundRingtoneChangeSIM1 = profile._soundRingtoneChangeSIM1;
        this._soundRingtoneSIM1 = profile._soundRingtoneSIM1;
        this._soundRingtoneChangeSIM2 = profile._soundRingtoneChangeSIM2;
        this._soundRingtoneSIM2 = profile._soundRingtoneSIM2;
        this._soundNotificationChangeSIM1 = profile._soundNotificationChangeSIM1;
        this._soundNotificationSIM1 = profile._soundNotificationSIM1;
        this._soundNotificationChangeSIM2 = profile._soundNotificationChangeSIM2;
        this._soundNotificationSIM2 = profile._soundNotificationSIM2;
        this._soundSameRingtoneForBothSIMCards = profile._soundSameRingtoneForBothSIMCards;

        this._iconBitmap = profile._iconBitmap;
        this._preferencesIndicator = profile._preferencesIndicator;
        this._activationByUserCount = profile._activationByUserCount;
    }

    void mergeProfiles(long withProfileId, DataWrapper dataWrapper/*, boolean setDuration*/)
    {
        //PPApplication.logE("$$$ mergeProfiles","_id="+_id);
        //PPApplication.logE("$$$ mergeProfiles","withProfileId="+withProfileId);

        Profile withProfile = dataWrapper.getProfileById(withProfileId, false, false, false);

        /*
        PPApplication.logE("$$$ mergeProfiles", "**** START");
        PPApplication.logE("$$$ mergeProfiles", "**** this.profileName=" + _name);
        PPApplication.logE("$$$ mergeProfiles", "**** this.profileId=" + _id);
        PPApplication.logE("$$$ mergeProfiles", "**** this._volumeRingerMode=" + _volumeRingerMode);
        PPApplication.logE("$$$ mergeProfiles", "**** this._volumeZenMode=" + _volumeZenMode);
        PPApplication.logE("$$$ mergeProfiles", "**** this._volumeRingtone=" + _volumeRingtone);
        PPApplication.logE("$$$ mergeProfiles", "**** this._volumeNotification=" + _volumeNotification);

        PPApplication.logE("$$$ mergeProfiles", "**** withProfile.profileName=" + withProfile._name);
        PPApplication.logE("$$$ mergeProfiles", "**** withProfile.profileId=" + withProfile._id);
        PPApplication.logE("$$$ mergeProfiles", "**** withProfile._volumeRingerMode=" + withProfile._volumeRingerMode);
        PPApplication.logE("$$$ mergeProfiles", "**** withProfile._volumeZenMode=" + withProfile._volumeZenMode);
        PPApplication.logE("$$$ mergeProfiles", "**** withProfile._volumeRingtone=" + withProfile._volumeRingtone);
        PPApplication.logE("$$$ mergeProfiles", "**** withProfile._volumeNotification=" + withProfile._volumeNotification);
        */

        if (withProfile != null) {
            if (this._id == 0) {
                // copy all data from withProfile when this profile is initialized
                copyProfile(withProfile);
            }
            else {
                this._id = withProfile._id;
                this._name = withProfile._name;
                this._icon = withProfile._icon;
                this._iconBitmap = withProfile._iconBitmap;
                this._preferencesIndicator = withProfile._preferencesIndicator;
                if (!withProfile._askForDuration/* && setDuration*/) {
                    this._duration = withProfile._duration;
                    this._afterDurationDo = withProfile._afterDurationDo;
                    this._afterDurationProfile = withProfile._afterDurationProfile;
                } else {
                    this._duration = 0;
                    this._afterDurationDo = AFTER_DURATION_DO_RESTART_EVENTS;
                    this._afterDurationProfile = PROFILE_NO_ACTIVATE;
                }
                this._durationNotificationSound = withProfile._durationNotificationSound;
                this._durationNotificationVibrate = withProfile._durationNotificationVibrate;
                this._hideStatusBarIcon = withProfile._hideStatusBarIcon;
                this._deviceConnectToSSID = withProfile._deviceConnectToSSID;
                this._activationByUserCount = withProfile._activationByUserCount;

                if (withProfile._volumeRingerMode != 0) {
                    this._volumeRingerMode = withProfile._volumeRingerMode;
                    // also look at ProfilesPrefsFragment.disableDependedPref()
                    if (withProfile._volumeZenMode != 0) {
                        this._volumeZenMode = withProfile._volumeZenMode;
                    /*if ((this._volumeRingerMode == 5) && ((this._volumeZenMode == 1) || (this._volumeZenMode == 2))){
                        if (withProfile._vibrateWhenRinging != 0)
                            this._vibrateWhenRinging = withProfile._vibrateWhenRinging;
                    }*/
                    }
                    if (withProfile._vibrateWhenRinging != 0)
                        this._vibrateWhenRinging = withProfile._vibrateWhenRinging;
                }
                if (withProfile.getVolumeRingtoneChange())
                    this._volumeRingtone = withProfile._volumeRingtone;
                if (withProfile.getVolumeNotificationChange())
                    this._volumeNotification = withProfile._volumeNotification;
                if (withProfile.getVolumeAlarmChange())
                    this._volumeAlarm = withProfile._volumeAlarm;
                if (withProfile.getVolumeMediaChange())
                    this._volumeMedia = withProfile._volumeMedia;
                if (withProfile.getVolumeSystemChange())
                    this._volumeSystem = withProfile._volumeSystem;
                if (withProfile.getVolumeVoiceChange())
                    this._volumeVoice = withProfile._volumeVoice;
                if (withProfile._soundRingtoneChange != 0) {
                    this._soundRingtoneChange = withProfile._soundRingtoneChange;
                    this._soundRingtone = withProfile._soundRingtone;
                }
                if (withProfile._soundNotificationChange != 0) {
                    this._soundNotificationChange = withProfile._soundNotificationChange;
                    this._soundNotification = withProfile._soundNotification;
                }
                if (withProfile._soundAlarmChange != 0) {
                    this._soundAlarmChange = withProfile._soundAlarmChange;
                    this._soundAlarm = withProfile._soundAlarm;
                }
                if (withProfile._deviceAirplaneMode != 0) {
                    if (withProfile._deviceAirplaneMode != 3) // toggle
                        this._deviceAirplaneMode = withProfile._deviceAirplaneMode;
                    else {
                        if (this._deviceAirplaneMode == 1)
                            this._deviceAirplaneMode = 2;
                        else if (this._deviceAirplaneMode == 2)
                            this._deviceAirplaneMode = 1;
                    }
                }
                if (withProfile._deviceAutoSync != 0) {
                    if (withProfile._deviceAutoSync != 3) // toggle
                        this._deviceAutoSync = withProfile._deviceAutoSync;
                    else {
                        if (this._deviceAutoSync == 1)
                            this._deviceAutoSync = 2;
                        else if (this._deviceAutoSync == 2)
                            this._deviceAutoSync = 1;
                    }
                }
                if (withProfile._deviceMobileData != 0) {
                    if (withProfile._deviceMobileData != 3) // toggle
                        this._deviceMobileData = withProfile._deviceMobileData;
                    else {
                        if (this._deviceMobileData == 1)
                            this._deviceMobileData = 2;
                        else if (this._deviceMobileData == 2)
                            this._deviceMobileData = 1;
                    }
                }
                if (withProfile._deviceMobileDataPrefs != 0)
                    this._deviceMobileDataPrefs = withProfile._deviceMobileDataPrefs;
                if (withProfile._deviceWiFi != 0) {
                    if (withProfile._deviceWiFi != 3) // toggle
                        this._deviceWiFi = withProfile._deviceWiFi;
                    else {
                        if (this._deviceWiFi == 1)
                            this._deviceWiFi = 2;
                        else if (this._deviceWiFi == 2)
                            this._deviceWiFi = 1;
                    }
                }
                if (withProfile._deviceBluetooth != 0) {
                    if (withProfile._deviceBluetooth != 3) // toggle
                        this._deviceBluetooth = withProfile._deviceBluetooth;
                    else {
                        if (this._deviceBluetooth == 1)
                            this._deviceBluetooth = 2;
                        else if (this._deviceBluetooth == 2)
                            this._deviceBluetooth = 1;
                    }
                }
                if (withProfile._deviceGPS != 0) {
                    if (withProfile._deviceGPS != 3) // toggle
                        this._deviceGPS = withProfile._deviceGPS;
                    else {
                        if (this._deviceGPS == 1)
                            this._deviceGPS = 2;
                        else if (this._deviceGPS == 2)
                            this._deviceGPS = 1;
                    }
                }
                if (withProfile._deviceLocationServicePrefs != 0)
                    this._deviceLocationServicePrefs = withProfile._deviceLocationServicePrefs;
                if (withProfile._deviceScreenTimeout != 0)
                    this._deviceScreenTimeout = withProfile._deviceScreenTimeout;
                if (withProfile.getDeviceBrightnessChange())
                    this._deviceBrightness = withProfile._deviceBrightness;
                if (withProfile._deviceAutoRotate != 0)
                    this._deviceAutoRotate = withProfile._deviceAutoRotate;
                if (withProfile._deviceRunApplicationChange != 0) {
                    this._deviceRunApplicationChange = 1;
                    if (this._deviceRunApplicationPackageName.isEmpty())
                        this._deviceRunApplicationPackageName = withProfile._deviceRunApplicationPackageName;
                    else
                        this._deviceRunApplicationPackageName = this._deviceRunApplicationPackageName + "|" +
                                withProfile._deviceRunApplicationPackageName;
                }
                if (withProfile._deviceWallpaperChange != 0) {
                    this._deviceWallpaperChange = 1;
                    this._deviceWallpaper = withProfile._deviceWallpaper;
                    this._deviceWallpaperFor = withProfile._deviceWallpaperFor;
                }
                if (withProfile._volumeSpeakerPhone != 0)
                    this._volumeSpeakerPhone = withProfile._volumeSpeakerPhone;
                if (withProfile._deviceNFC != 0) {
                    if (withProfile._deviceNFC != 3) // toggle
                        this._deviceNFC = withProfile._deviceNFC;
                    else {
                        if (this._deviceNFC == 1)
                            this._deviceNFC = 2;
                        else if (this._deviceNFC == 2)
                            this._deviceNFC = 1;
                    }
                }
                if (withProfile._deviceKeyguard != 0)
                    this._deviceKeyguard = withProfile._deviceKeyguard;
                if (withProfile._vibrationOnTouch != 0)
                    this._vibrationOnTouch = withProfile._vibrationOnTouch;
                if (withProfile._deviceWiFiAP != 0) {
                    if (withProfile._deviceWiFiAP != 3) // toggle
                        this._deviceWiFiAP = withProfile._deviceWiFiAP;
                    else {
                        if (this._deviceWiFiAP == 1)
                            this._deviceWiFiAP = 2;
                        else if (this._deviceWiFiAP == 2)
                            this._deviceWiFiAP = 1;
                    }
                }
                if (withProfile._devicePowerSaveMode != 0) {
                    if (withProfile._devicePowerSaveMode != 3) // toggle
                        this._devicePowerSaveMode = withProfile._devicePowerSaveMode;
                    else {
                        if (this._devicePowerSaveMode == 1)
                            this._devicePowerSaveMode = 2;
                        else if (this._devicePowerSaveMode == 2)
                            this._devicePowerSaveMode = 1;
                    }
                }
                if (withProfile._deviceNetworkType != 0)
                    this._deviceNetworkType = withProfile._deviceNetworkType;
                if (withProfile._notificationLed != 0)
                    this._notificationLed = withProfile._notificationLed;
                if (withProfile._lockDevice != 0)
                    this._lockDevice = withProfile._lockDevice;
                if (withProfile._applicationDisableWifiScanning != 0)
                    this._applicationDisableWifiScanning = withProfile._applicationDisableWifiScanning;
                if (withProfile._applicationDisableBluetoothScanning != 0)
                    this._applicationDisableBluetoothScanning = withProfile._applicationDisableBluetoothScanning;
                if (withProfile._deviceWiFiAPPrefs != 0)
                    this._deviceWiFiAPPrefs = withProfile._deviceWiFiAPPrefs;
                if (withProfile._applicationDisableLocationScanning != 0)
                    this._applicationDisableLocationScanning = withProfile._applicationDisableLocationScanning;
                if (withProfile._applicationDisableMobileCellScanning != 0)
                    this._applicationDisableMobileCellScanning = withProfile._applicationDisableMobileCellScanning;
                if (withProfile._applicationDisableOrientationScanning != 0)
                    this._applicationDisableOrientationScanning = withProfile._applicationDisableOrientationScanning;
                if (withProfile._headsUpNotifications != 0)
                    this._headsUpNotifications = withProfile._headsUpNotifications;
                if (withProfile._deviceForceStopApplicationChange != 0) {
                    this._deviceForceStopApplicationChange = 1;
                    if (this._deviceForceStopApplicationPackageName.isEmpty())
                        this._deviceForceStopApplicationPackageName = withProfile._deviceForceStopApplicationPackageName;
                    else
                        this._deviceForceStopApplicationPackageName = this._deviceForceStopApplicationPackageName + "|" +
                                withProfile._deviceForceStopApplicationPackageName;
                }
                if (withProfile._deviceNetworkTypePrefs != 0)
                    this._deviceNetworkTypePrefs = withProfile._deviceNetworkTypePrefs;
                if (withProfile._deviceCloseAllApplications != 0)
                    this._deviceCloseAllApplications = withProfile._deviceCloseAllApplications;
                if (withProfile._screenDarkMode != 0)
                    this._screenDarkMode = withProfile._screenDarkMode;
                if (withProfile._dtmfToneWhenDialing != 0)
                    this._dtmfToneWhenDialing = withProfile._dtmfToneWhenDialing;
                if (withProfile._soundOnTouch != 0)
                    this._soundOnTouch = withProfile._soundOnTouch;
                if (withProfile.getVolumeDTMFChange())
                    this._volumeDTMF = withProfile._volumeDTMF;
                if (withProfile.getVolumeAccessibilityChange())
                    this._volumeAccessibility = withProfile._volumeAccessibility;
                if (withProfile.getVolumeBluetoothSCOChange())
                    this._volumeBluetoothSCO = withProfile._volumeBluetoothSCO;
                if (withProfile._alwaysOnDisplay != 0)
                    this._alwaysOnDisplay = withProfile._alwaysOnDisplay;
                if (withProfile._screenOnPermanent != 0)
                    this._screenOnPermanent = withProfile._screenOnPermanent;
                if (withProfile._deviceLocationMode != 0)
                    this._deviceLocationMode = withProfile._deviceLocationMode;
                if (withProfile._applicationDisableNotificationScanning != 0)
                    this._applicationDisableNotificationScanning = withProfile._applicationDisableNotificationScanning;
                if (withProfile.getGenerateNotificationGenerate())
                    this._generateNotification = withProfile._generateNotification;
                if (withProfile._cameraFlash != 0)
                    this._cameraFlash = withProfile._cameraFlash;
                if (withProfile._deviceNetworkTypeSIM1 != 0)
                    this._deviceNetworkTypeSIM1 = withProfile._deviceNetworkTypeSIM1;
                if (withProfile._deviceNetworkTypeSIM2 != 0)
                    this._deviceNetworkTypeSIM2 = withProfile._deviceNetworkTypeSIM2;
                if (withProfile._deviceMobileDataSIM1 != 0) {
                    if (withProfile._deviceMobileDataSIM1 != 3) // toggle
                        this._deviceMobileDataSIM1 = withProfile._deviceMobileDataSIM1;
                    else {
                        if (this._deviceMobileDataSIM1 == 1)
                            this._deviceMobileDataSIM1 = 2;
                        else if (this._deviceMobileDataSIM1 == 2)
                            this._deviceMobileDataSIM1 = 1;
                    }
                }
                if (withProfile._deviceMobileDataSIM2 != 0) {
                    if (withProfile._deviceMobileDataSIM2 != 3) // toggle
                        this._deviceMobileDataSIM2 = withProfile._deviceMobileDataSIM2;
                    else {
                        if (this._deviceMobileDataSIM2 == 1)
                            this._deviceMobileDataSIM2 = 2;
                        else if (this._deviceMobileDataSIM2 == 2)
                            this._deviceMobileDataSIM2 = 1;
                    }
                }
                if (!withProfile._deviceDefaultSIMCards.equals("0|0|0"))
                    this._deviceDefaultSIMCards = withProfile._deviceDefaultSIMCards;
                if (withProfile._deviceOnOffSIM1 != 0) {
                    if (withProfile._deviceOnOffSIM1 != 3) // toggle
                        this._deviceOnOffSIM1 = withProfile._deviceOnOffSIM1;
                    else {
                        if (this._deviceOnOffSIM1 == 1)
                            this._deviceOnOffSIM1 = 2;
                        else if (this._deviceOnOffSIM1 == 2)
                            this._deviceOnOffSIM1 = 1;
                    }
                }
                if (withProfile._deviceOnOffSIM2 != 0) {
                    if (withProfile._deviceOnOffSIM2 != 3) // toggle
                        this._deviceOnOffSIM2 = withProfile._deviceOnOffSIM2;
                    else {
                        if (this._deviceOnOffSIM2 == 1)
                            this._deviceOnOffSIM2 = 2;
                        else if (this._deviceOnOffSIM2 == 2)
                            this._deviceOnOffSIM2 = 1;
                    }
                }
                if (withProfile._soundRingtoneChangeSIM1 != 0) {
                    this._soundRingtoneChangeSIM1 = withProfile._soundRingtoneChangeSIM1;
                    this._soundRingtoneSIM1 = withProfile._soundRingtoneSIM1;
                }
                if (withProfile._soundRingtoneChangeSIM2 != 0) {
                    this._soundRingtoneChangeSIM2 = withProfile._soundRingtoneChangeSIM2;
                    this._soundRingtoneSIM2 = withProfile._soundRingtoneSIM2;
                }
                if (withProfile._soundNotificationChangeSIM1 != 0) {
                    this._soundNotificationChangeSIM1 = withProfile._soundNotificationChangeSIM1;
                    this._soundNotificationSIM1 = withProfile._soundNotificationSIM1;
                }
                if (withProfile._soundNotificationChangeSIM2 != 0) {
                    this._soundNotificationChangeSIM2 = withProfile._soundNotificationChangeSIM2;
                    this._soundNotificationSIM2 = withProfile._soundNotificationSIM2;
                }
                if (withProfile._soundSameRingtoneForBothSIMCards != 0)
                    this._soundSameRingtoneForBothSIMCards = withProfile._soundSameRingtoneForBothSIMCards;


                if (withProfile._volumeMuteSound)
                    this._volumeMuteSound = true;
            }

            // set merged profile as activated
            DatabaseHandler.getInstance(dataWrapper.context).activateProfile(withProfile);
            dataWrapper.setProfileActive(withProfile);

            /* Do not log this, logged is merged profile in EventsHandler
            String profileIcon = withProfile._icon;
            PPApplication.addActivityLog(dataWrapper.context, PPApplication.ALTYPE_PROFILE_ACTIVATION, null,
                                    DataWrapper.getProfileNameWithManualIndicatorAsString(withProfile, true, "", false, false, false, dataWrapper, false, dataWrapper.context),
                                    profileIcon, 0);*/

            /*
            PPApplication.logE("$$$ mergeProfiles", "**** END");
            PPApplication.logE("$$$ mergeProfiles", "**** this.profileName=" + _name);
            PPApplication.logE("$$$ mergeProfiles", "**** this.profileId=" + _id);
            PPApplication.logE("$$$ mergeProfiles", "**** this._volumeRingerMode=" + _volumeRingerMode);
            PPApplication.logE("$$$ mergeProfiles", "**** this._volumeZenMode=" + _volumeZenMode);
            PPApplication.logE("$$$ mergeProfiles", "**** this._volumeRingtone=" + _volumeRingtone);
            PPApplication.logE("$$$ mergeProfiles", "**** this._volumeNotification=" + _volumeNotification);
            */
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    boolean compareProfile(Profile withProfile)
    {
        //PPApplication.logE("$$$ compareProfiles","name="+_name);

        if (withProfile != null) {
            //PPApplication.logE("$$$ compareProfiles","withProfile="+withProfile._name);

            if (this._id != withProfile._id) {
                //PPApplication.logE("$$$ compareProfiles","_id");
                return false;
            }

            if (this._afterDurationDo == AFTER_DURATION_DO_SPECIFIC_PROFILE) {
                if (this._duration > 0) {
                    if (this._afterDurationDo != withProfile._afterDurationDo) {
                        //PPApplication.logE("$$$ compareProfiles","_afterDurationDo");
                        return false;
                    }
                }
            }

            if (this._volumeRingerMode != withProfile._volumeRingerMode) {
                //PPApplication.logE("$$$ compareProfiles","_volumeRingerMode");
                return false;
            }
            if (this._volumeZenMode != withProfile._volumeZenMode) {
                //PPApplication.logE("$$$ compareProfiles","_volumeZenMode");
                return false;
            }
            if ((this._volumeRingerMode == 1) || (this._volumeRingerMode == 4)) {
                if (this._vibrateWhenRinging != withProfile._vibrateWhenRinging) {
                    //PPApplication.logE("$$$ compareProfiles","_vibrateWhenRinging 1");
                    return false;
                }
            }
            if ((this._volumeRingerMode == 5) && ((this._volumeZenMode == 1) || (this._volumeZenMode == 2))){
                if (this._vibrateWhenRinging != withProfile._vibrateWhenRinging) {
                    //PPApplication.logE("$$$ compareProfiles","_vibrateWhenRinging 2");
                    return false;
                }
            }
            if (!this._volumeRingtone.equals(withProfile._volumeRingtone)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeRingtone");
                return false;
            }
            if (!this._volumeNotification.equals(withProfile._volumeNotification)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeNotification");
                return false;
            }
            if (!this._volumeMedia.equals(withProfile._volumeMedia)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeMedia");
                return false;
            }
            if (!this._volumeAlarm.equals(withProfile._volumeAlarm)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeAlarm");
                return false;
            }
            if (!this._volumeSystem.equals(withProfile._volumeSystem)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeSystem");
                return false;
            }
            if (!this._volumeVoice.equals(withProfile._volumeVoice)) {
                //PPApplication.logE("$$$ compareProfiles","this._volumeVoice="+this._volumeVoice);
                //PPApplication.logE("$$$ compareProfiles","withProfile._volumeVoice="+withProfile._volumeVoice);
                //PPApplication.logE("$$$ compareProfiles","_volumeVoice");
                return false;
            }
            if (this._soundRingtoneChange != withProfile._soundRingtoneChange) {
                //PPApplication.logE("$$$ compareProfiles","_soundRingtoneChange");
                return false;
            }
            if (this._soundRingtoneChange != 0) {
                if (!this._soundRingtone.equals(withProfile._soundRingtone)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundRingtone");
                    return false;
                }
            }
            if (this._soundNotificationChange != withProfile._soundNotificationChange) {
                //PPApplication.logE("$$$ compareProfiles","_soundNotificationChange");
                return false;
            }
            if (this._soundNotificationChange != 0) {
                if (!this._soundNotification.equals(withProfile._soundNotification)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundNotification");
                    return false;
                }
            }
            if (this._soundAlarmChange != withProfile._soundAlarmChange) {
                //PPApplication.logE("$$$ compareProfiles","_soundAlarmChange");
                return false;
            }
            if (this._soundAlarmChange != 0) {
                if (!this._soundAlarm.equals(withProfile._soundAlarm)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundAlarm");
                    return false;
                }
            }
            if (this._deviceAirplaneMode != withProfile._deviceAirplaneMode) {
                //PPApplication.logE("$$$ compareProfiles","_deviceAirplaneMode");
                return false;
            }
            if (this._deviceMobileData != withProfile._deviceMobileData) {
                //PPApplication.logE("$$$ compareProfiles","_deviceMobileData");
                return false;
            }
            if (this._deviceMobileDataPrefs != withProfile._deviceMobileDataPrefs) {
                //PPApplication.logE("$$$ compareProfiles","_deviceMobileDataPrefs");
                return false;
            }
            if (this._deviceWiFi != withProfile._deviceWiFi) {
                //PPApplication.logE("$$$ compareProfiles","_deviceWiFi");
                return false;
            }
            if (this._deviceBluetooth != withProfile._deviceBluetooth) {
                //PPApplication.logE("$$$ compareProfiles","_deviceBluetooth");
                return false;
            }
            if (this._deviceGPS != withProfile._deviceGPS) {
                //PPApplication.logE("$$$ compareProfiles","_deviceGPS");
                return false;
            }
            if (this._deviceLocationServicePrefs != withProfile._deviceLocationServicePrefs) {
                //PPApplication.logE("$$$ compareProfiles","_deviceLocationServicePrefs");
                return false;
            }
            if (this._deviceScreenTimeout != withProfile._deviceScreenTimeout) {
                //PPApplication.logE("$$$ compareProfiles","_deviceScreenTimeout");
                return false;
            }
            if (!this._deviceBrightness.equals(withProfile._deviceBrightness)) {
                //PPApplication.logE("$$$ compareProfiles","_deviceBrightness");
                return false;
            }
            if (this._deviceWallpaperChange != withProfile._deviceWallpaperChange) {
                //PPApplication.logE("$$$ compareProfiles","_deviceWallpaperChange");
                return false;
            }
            if (this._deviceWallpaperChange != 0) {
                if (!this._deviceWallpaper.equals(withProfile._deviceWallpaper)) {
                    //PPApplication.logE("$$$ compareProfiles","_deviceWallpaper");
                    return false;
                }
                if (this._deviceWallpaperFor != withProfile._deviceWallpaperFor) {
                    //PPApplication.logE("$$$ compareProfiles","_deviceWallpaperFor");
                    return false;
                }
            }
            if (this._deviceRunApplicationChange != withProfile._deviceRunApplicationChange) {
                //PPApplication.logE("$$$ compareProfiles","_deviceRunApplicationChange");
                return false;
            }
            if (this._deviceRunApplicationChange != 0) {
                if (!this._deviceRunApplicationPackageName.equals(withProfile._deviceRunApplicationPackageName)) {
                    //PPApplication.logE("$$$ compareProfiles","_deviceRunApplicationPackageName");
                    return false;
                }
            }
            if (this._deviceAutoSync != withProfile._deviceAutoSync) {
                //PPApplication.logE("$$$ compareProfiles","_deviceAutoSync");
                return false;
            }
            if (this._deviceAutoRotate != withProfile._deviceAutoRotate) {
                //PPApplication.logE("$$$ compareProfiles","_deviceAutoRotate");
                return false;
            }
            if (this._volumeSpeakerPhone != withProfile._volumeSpeakerPhone) {
                //PPApplication.logE("$$$ compareProfiles","_volumeSpeakerPhone");
                return false;
            }
            if (this._deviceNFC != withProfile._deviceNFC) {
                //PPApplication.logE("$$$ compareProfiles","_deviceNFC");
                return false;
            }
            if (this._deviceKeyguard != withProfile._deviceKeyguard) {
                //PPApplication.logE("$$$ compareProfiles","_deviceKeyguard");
                return false;
            }
            if (this._vibrationOnTouch != withProfile._vibrationOnTouch) {
                //PPApplication.logE("$$$ compareProfiles","_vibrationOnTouch");
                return false;
            }
            if (this._deviceWiFiAP != withProfile._deviceWiFiAP) {
                //PPApplication.logE("$$$ compareProfiles","_deviceWiFiAP");
                return false;
            }
            if (this._devicePowerSaveMode != withProfile._devicePowerSaveMode) {
                //PPApplication.logE("$$$ compareProfiles","_devicePowerSaveMode");
                return false;
            }
            if (this._deviceNetworkType != withProfile._deviceNetworkType) {
                //PPApplication.logE("$$$ compareProfiles","_deviceNetworkType");
                return false;
            }
            if (this._notificationLed != withProfile._notificationLed) {
                //PPApplication.logE("$$$ compareProfiles","_notificationLed");
                return false;
            }
            if (this._lockDevice != withProfile._lockDevice) {
                //PPApplication.logE("$$$ compareProfiles","_lockDevice");
                return false;
            }
            if (!this._deviceConnectToSSID.equals(withProfile._deviceConnectToSSID)) {
                //PPApplication.logE("$$$ compareProfiles","_deviceConnectToSSID");
                return false;
            }
            if (this._applicationDisableWifiScanning != withProfile._applicationDisableWifiScanning) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableWifiScanning");
                return false;
            }
            if (this._applicationDisableBluetoothScanning != withProfile._applicationDisableBluetoothScanning) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableBluetoothScanning");
                return false;
            }
            if (this._deviceWiFiAPPrefs != withProfile._deviceWiFiAPPrefs) {
                //PPApplication.logE("$$$ compareProfiles","_deviceWiFiAPPrefs");
                return false;
            }
            if (this._applicationDisableLocationScanning != withProfile._applicationDisableLocationScanning) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableLocationScanning");
                return false;
            }
            if (this._applicationDisableMobileCellScanning != withProfile._applicationDisableMobileCellScanning) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableMobileCellScanning");
                return false;
            }
            if (this._applicationDisableOrientationScanning != withProfile._applicationDisableOrientationScanning) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableOrientationScanning");
                return false;
            }
            if (this._headsUpNotifications != withProfile._headsUpNotifications) {
                //PPApplication.logE("$$$ compareProfiles","_headsUpNotifications");
                return false;
            }
            if (this._deviceForceStopApplicationChange != withProfile._deviceForceStopApplicationChange) {
                //PPApplication.logE("$$$ compareProfiles","_deviceForceStopApplicationChange");
                return false;
            }
            if (this._deviceForceStopApplicationChange != 0) {
                if (!this._deviceForceStopApplicationPackageName.equals(withProfile._deviceForceStopApplicationPackageName)) {
                    //PPApplication.logE("$$$ compareProfiles","_deviceForceStopApplicationPackageName");
                    return false;
                }
            }
            if (this._deviceNetworkTypePrefs != withProfile._deviceNetworkTypePrefs) {
                //PPApplication.logE("$$$ compareProfiles","_deviceNetworkTypePrefs");
                return false;
            }
            if (this._deviceCloseAllApplications != withProfile._deviceCloseAllApplications) {
                //PPApplication.logE("$$$ compareProfiles","_deviceCloseAllApplications");
                return false;
            }
            if (this._screenDarkMode != withProfile._screenDarkMode) {
                //PPApplication.logE("$$$ compareProfiles","_screenDarkMode");
                return false;
            }
            if (this._dtmfToneWhenDialing != withProfile._dtmfToneWhenDialing) {
                //PPApplication.logE("$$$ compareProfiles","_dtmfToneWhenDialing");
                return false;
            }
            if (this._soundOnTouch != withProfile._soundOnTouch) {
                //PPApplication.logE("$$$ compareProfiles","_soundOnTouch");
                return false;
            }
            if (!this._volumeDTMF.equals(withProfile._volumeDTMF)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeDTMF");
                return false;
            }
            if (!this._volumeAccessibility.equals(withProfile._volumeAccessibility)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeAccessibility");
                return false;
            }
            if (!this._volumeBluetoothSCO.equals(withProfile._volumeBluetoothSCO)) {
                //PPApplication.logE("$$$ compareProfiles","_volumeBluetoothSCO");
                return false;
            }
            if (this._alwaysOnDisplay != withProfile._alwaysOnDisplay) {
                //PPApplication.logE("$$$ compareProfiles","_alwaysOnDisplay");
                return false;
            }
            if (this._screenOnPermanent != withProfile._screenOnPermanent) {
                //PPApplication.logE("$$$ compareProfiles","_screenOnPermanent");
                return false;
            }
            if (this._volumeMuteSound != withProfile._volumeMuteSound) {
                //PPApplication.logE("$$$ compareProfiles","_volumeMuteSound");
                return false;
            }
            if (this._deviceLocationMode != withProfile._deviceLocationMode) {
                //PPApplication.logE("$$$ compareProfiles","_deviceLocationMode");
                return false;
            }
            if (this._applicationDisableNotificationScanning != withProfile._applicationDisableNotificationScanning) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableNotificationScanning");
                return false;
            }
            if (!this._generateNotification.equals(withProfile._generateNotification)) {
                //PPApplication.logE("$$$ compareProfiles","_applicationDisableNotificationScanning");
                return false;
            }
            if (this._cameraFlash != withProfile._cameraFlash) {
                return false;
            }
            if (this._deviceNetworkTypeSIM1 != withProfile._deviceNetworkTypeSIM1) {
                //PPApplication.logE("$$$ compareProfiles","_deviceNetworkTypeSIM1");
                return false;
            }
            if (this._deviceNetworkTypeSIM2 != withProfile._deviceNetworkTypeSIM2) {
                //PPApplication.logE("$$$ compareProfiles","_deviceNetworkTypeSIM2");
                return false;
            }
            if (this._deviceMobileDataSIM1 != withProfile._deviceMobileDataSIM1) {
                //PPApplication.logE("$$$ compareProfiles","_deviceMobileDataSIM1");
                return false;
            }
            if (this._deviceMobileDataSIM2 != withProfile._deviceMobileDataSIM2) {
                //PPApplication.logE("$$$ compareProfiles","_deviceMobileDataSIM2");
                return false;
            }
            if (!this._deviceDefaultSIMCards.equals(withProfile._deviceDefaultSIMCards)) {
                //PPApplication.logE("$$$ compareProfiles","_deviceDefaultSIMCards");
                return false;
            }
            if (this._deviceOnOffSIM1 != withProfile._deviceOnOffSIM1) {
                //PPApplication.logE("$$$ compareProfiles","_deviceOnOffSIM1");
                return false;
            }
            if (this._deviceOnOffSIM2 != withProfile._deviceOnOffSIM2) {
                //PPApplication.logE("$$$ compareProfiles","_deviceOnOffSIM2");
                return false;
            }
            if (this._soundRingtoneChangeSIM1 != withProfile._soundRingtoneChangeSIM1) {
                //PPApplication.logE("$$$ compareProfiles","_soundRingtoneChangeSIM1");
                return false;
            }
            if (this._soundRingtoneChangeSIM1 != 0) {
                if (!this._soundRingtoneSIM1.equals(withProfile._soundRingtoneSIM1)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundRingtoneSIM1");
                    return false;
                }
            }
            if (this._soundRingtoneChangeSIM2 != withProfile._soundRingtoneChangeSIM2) {
                //PPApplication.logE("$$$ compareProfiles","_soundRingtoneChangeSIM2");
                return false;
            }
            if (this._soundRingtoneChangeSIM2 != 0) {
                if (!this._soundRingtoneSIM2.equals(withProfile._soundRingtoneSIM2)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundRingtoneSIM2");
                    return false;
                }
            }
            if (this._soundNotificationChangeSIM1 != withProfile._soundNotificationChangeSIM1) {
                //PPApplication.logE("$$$ compareProfiles","_soundNotificationChangeSIM1");
                return false;
            }
            if (this._soundNotificationChangeSIM1 != 0) {
                if (!this._soundNotificationSIM1.equals(withProfile._soundNotificationSIM1)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundNotificationSIM1");
                    return false;
                }
            }
            if (this._soundNotificationChangeSIM2 != withProfile._soundNotificationChangeSIM2) {
                //PPApplication.logE("$$$ compareProfiles","_soundNotificationChangeSIM2");
                return false;
            }
            if (this._soundNotificationChangeSIM2 != 0) {
                if (!this._soundNotificationSIM2.equals(withProfile._soundNotificationSIM2)) {
                    //PPApplication.logE("$$$ compareProfiles","_soundNotificationSIM2");
                    return false;
                }
            }
            if (this._soundSameRingtoneForBothSIMCards != withProfile._soundSameRingtoneForBothSIMCards) {
                //PPApplication.logE("$$$ compareProfiles","_soundSameRingtoneForBothSIMCards");
                return false;
            }
            return true;
        }
        return false;
    }

    // getting icon identifier
    public String getIconIdentifier()
    {
        String value;
        try {
            String[] splits = _icon.split("\\|");
            value = splits[0];
        } catch (Exception e) {
            value = "ic_profile_default";
        }
        return value;
    }

    // getting where icon is resource id
    public boolean getIsIconResourceID()
    {
        boolean value;
        try {
            String[] splits = _icon.split("\\|");
            value = splits[1].equals("1");

        } catch (Exception e) {
            value = true;
        }
        return value;
    }

    //get where icon has custom color
    boolean getUseCustomColorForIcon() {
        boolean value;
        try {
            String[] splits = _icon.split("\\|");
            value = splits[2].equals("1");

        } catch (Exception e) {
            value = false;
        }
        return value;
    }

    // get icon custom color
    private int getIconCustomColor() {
        int value;
        try {
            String[] splits = _icon.split("\\|");
            value = Integer.parseInt(splits[3]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    static int getVolumeRingtoneValue(String volumeRingtone)
    {
        int value;
        try {
            String[] splits = volumeRingtone.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    int getVolumeRingtoneValue() {
        return getVolumeRingtoneValue(_volumeRingtone);
    }

    static boolean getVolumeRingtoneChange(String volumeRingtone)
    {
        int value;
        try {
            String[] splits = volumeRingtone.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    boolean getVolumeRingtoneChange()
    {
        return getVolumeRingtoneChange(_volumeRingtone);
    }

    private boolean getVolumeRingtoneSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeRingtone.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    @SuppressWarnings({"StringConcatenationInLoop", "SameParameterValue"})
    void setVolumeRingtoneValue(int value) {
        try {
            String[] splits = _volumeRingtone.split("\\|");
            splits[0] = String.valueOf(value);
            _volumeRingtone = "";
            for (String split : splits) {
                if (!_volumeRingtone.isEmpty())
                    _volumeRingtone = _volumeRingtone + "|";
                _volumeRingtone = _volumeRingtone + split;
            }
        } catch (Exception ignore) {
        }
    }

    int getVolumeNotificationValue()
    {
        int value;
        try {
            String[] splits = _volumeNotification.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeNotificationChange()
    {
        int value;
        try {
            String[] splits = _volumeNotification.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeNotificationSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeNotification.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    @SuppressWarnings({"StringConcatenationInLoop", "SameParameterValue"})
    void setVolumeNotificationValue(int value) {

        try {
            String[] splits = _volumeNotification.split("\\|");
            splits[0] = String.valueOf(value);
            _volumeNotification = "";
            for (String split : splits) {
                if (!_volumeNotification.isEmpty())
                    _volumeNotification = _volumeNotification + "|";
                _volumeNotification = _volumeNotification + split;
            }
        } catch (Exception ignore) {
        }
    }

    int getVolumeMediaValue()
    {
        int value;
        try {
            String[] splits = _volumeMedia.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeMediaChange()
    {
        int value;
        try {
            String[] splits = _volumeMedia.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeMediaSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeMedia.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    int getVolumeAlarmValue()
    {
        int value;
        try {
            String[] splits = _volumeAlarm.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeAlarmChange()
    {
        int value;
        try {
            String[] splits = _volumeAlarm.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeAlarmSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeAlarm.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    int getVolumeSystemValue()
    {
        int value;
        try {
            String[] splits = _volumeSystem.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeSystemChange()
    {
        int value;
        try {
            String[] splits = _volumeSystem.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeSystemSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeSystem.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    int getVolumeVoiceValue()
    {
        int value;
        try {
            String[] splits = _volumeVoice.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeVoiceChange()
    {
        int value;
        try {
            String[] splits = _volumeVoice.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeVoiceSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeVoice.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    int getVolumeDTMFValue()
    {
        int value;
        try {
            String[] splits = _volumeDTMF.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeDTMFChange()
    {
        int value;
        try {
            String[] splits = _volumeDTMF.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeDTMFSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeDTMF.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    int getVolumeAccessibilityValue()
    {
        int value;
        try {
            String[] splits = _volumeAccessibility.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeAccessibilityChange()
    {
        int value;
        try {
            String[] splits = _volumeAccessibility.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeAccessibilitySharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeAccessibility.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    int getVolumeBluetoothSCOValue()
    {
        int value;
        try {
            String[] splits = _volumeBluetoothSCO.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    boolean getVolumeBluetoothSCOChange()
    {
        int value;
        try {
            String[] splits = _volumeBluetoothSCO.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getVolumeBluetoothSCOSharedProfile()
    {
        int value;
        try {
            String[] splits = _volumeBluetoothSCO.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    private int getDeviceBrightnessValue()
    {
        int maximumValue = 100;
        int defaultValue = 50;
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[0]);
            if ((value < 0) || (value > maximumValue))
                value = defaultValue;
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

    static int getDeviceBrightnessValue(String _deviceBrightness)
    {
        int maximumValue = 100;
        int defaultValue = 50;
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[0]);
            if ((value < 0) || (value > maximumValue))
                value = defaultValue;
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

    boolean getDeviceBrightnessChange()
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    static boolean getDeviceBrightnessChange(String _deviceBrightness)
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 0; // in preference dialog is checked=No change
    }

    private boolean getDeviceBrightnessSharedProfile()
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[3]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    boolean getDeviceBrightnessAutomatic()
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 1;
    }

    static boolean getDeviceBrightnessAutomatic(String _deviceBrightness)
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[2]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 1;
    }

    boolean getDeviceBrightnessChangeLevel()
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[4]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 1;
    }

    static boolean getDeviceBrightnessChangeLevel(String _deviceBrightness)
    {
        int value;
        try {
            String[] splits = _deviceBrightness.split("\\|");
            value = Integer.parseInt(splits[4]);
        } catch (Exception e) {
            value = 1;
        }
        return value == 1;
    }


//    private static int getMinimumScreenBrightnessSetting (Context context)
//    {
//        return context.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingMinimum);
//
        /*
        final Resources res = Resources.getSystem();
        int id = res.getIdentifier("config_screenBrightnessSettingMinimum", "integer", "android"); // API17+
        if (id == 0)
            id = res.getIdentifier("config_screenBrightnessDim", "integer", "android"); // lower API levels
        if (id != 0)
        {
            try {
                return res.getInteger(id);
            }
            catch (Exception ignored) {}
        }
        return 0;
        */
//    }

//    static int getMaximumScreenBrightnessSetting (Context context)
//    {
//        return context.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingMaximum);
        /*
        final Resources res = Resources.getSystem();
        final int id = res.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android");  // API17+
        if (id != 0)
        {
            try {
                int value = res.getInteger(id);
                if (value > 255)
                    value = 255;
                return value;
            }
            catch (Resources.NotFoundException e) {
                // ignore
            }
        }
        return 255;
        */
//    }

    ////// from AOSP and changed for PPP
//    private static final int GAMMA_SPACE_MAX_256 = 1023;
    //private static final int GAMMA_SPACE_MAX_1024 = 4095;

    // Hybrid Log Gamma constant values
//    private static final float _R = 0.5f;
//    private static final float _A = 0.17883277f;
//    private static final float _B = 0.28466892f;
//    private static final float _C = 0.55991073f;

/*    @SuppressWarnings("SameParameterValue")
    private static float convertLinearToGamma(float val, float min, float max) {
        // For some reason, HLG normalizes to the range [0, 12] rather than [0, 1]
        final float normalizedVal = MathUtils.norm(min, max, val) * 12;
        final float ret;
        if (normalizedVal <= 1f) {
            ret = MathUtils.sqrt(normalizedVal) * _R;
        } else {
            ret = _A * MathUtils.log(normalizedVal - _B) + _C;
        }
        //int spaceMax = GAMMA_SPACE_MAX_256;
        //if (PPApplication.romIsOnePlus)
        //    spaceMax = GAMMA_SPACE_MAX_1024;
        //return Math.round(MathUtils.lerp(0, GAMMA_SPACE_MAX_256, ret));
        return MathUtils.lerp(0, GAMMA_SPACE_MAX_256, ret);
    }

    @SuppressWarnings("SameParameterValue")
    private static float convertGammaToLinear(float val, float min, float max) {
        //int spaceMax = GAMMA_SPACE_MAX_256;
        //if (PPApplication.romIsOnePlus)
        //    spaceMax = GAMMA_SPACE_MAX_1024;
        final float normalizedVal = MathUtils.norm(0, GAMMA_SPACE_MAX_256, val);
        final float ret;
        if (normalizedVal <= _R) {
            ret = MathUtils.sq(normalizedVal / _R);
        } else {
            ret = MathUtils.exp((normalizedVal - _C) / _A) + _B;
        }
        // HLG is normalized to the range [0, 12], so we need to re-normalize to the range [0, 1]
        // in order to derive the correct setting value.
        //return Math.round(MathUtils.lerp(min, max, ret / 12));
        return MathUtils.lerp(min, max, ret / 12);
    }

    @SuppressWarnings("SameParameterValue")
    private static float getPercentage(float value, float min, float max) {
        if (value > max) {
            return 1.0f;
        }
        if (value < min) {
            return 0.0f;
        }
        //return ((float)value - min) / (max - min);
        return (value - min) / (max - min);
    }
*/
    private static int getBrightnessPercentage_A9(int settingsValue/*, int minValue, int maxValue*/) {
        /*final float value;
        float _settingsValue = settingsValue;
        if (PPApplication.romIsOnePlus)
            _settingsValue = settingsValue / 4; // convert from 1024 to 256

        value = convertLinearToGamma(_settingsValue, minValue, maxValue);
        //int spaceMax = GAMMA_SPACE_MAX_256;
        //if (PPApplication.romIsOnePlus)
        //    spaceMax = GAMMA_SPACE_MAX_1024;
        int percentage = Math.round(getPercentage(value, 0, GAMMA_SPACE_MAX_256) * 100);*/

        int _settingsValue = settingsValue;
        if (PPApplication.deviceIsOnePlus)
            _settingsValue = Math.round(settingsValue / 4f); // convert from 1024 to 256
        else
        if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)
            _settingsValue = Math.round(settingsValue / 16f); // convert from 4096 to 256
        //noinspection UnnecessaryLocalVariable
        int percentage = BrightnessLookup.lookup(_settingsValue, true);

        /*if (PPApplication.logEnabled()) {
            PPApplication.logE("getBrightnessPercentage_A9", "settingsValue=" + settingsValue);
            PPApplication.logE("getBrightnessPercentage_A9", "percentage=" + percentage);
        }*/

        return percentage;
    }

    private static int getBrightnessValue_A9(int percentage/*, int minValue, int maxValue*/) {
        //int spaceMax = GAMMA_SPACE_MAX_256;
        //if (PPApplication.romIsOnePlus)
        //    spaceMax = GAMMA_SPACE_MAX_1024;
        //int value = Math.round((GAMMA_SPACE_MAX_256+1) / 100f * (float)(percentage + 1));
        /*float value = (GAMMA_SPACE_MAX_256+1) / 100f * (float)(percentage + 1);
        float systemValue = convertGammaToLinear(value, minValue, maxValue);
        if (PPApplication.romIsOnePlus)
            systemValue = systemValue * 4; // convert from 256 to 1024
        PPApplication.logE("getBrightnessValue_A9", "float systemValue="+systemValue);

        int maximumValue = 255;
        if (PPApplication.romIsOnePlus)
            maximumValue = 1023;
        if (systemValue > maximumValue)
            systemValue = maximumValue;*/

        int systemValue = BrightnessLookup.lookup(percentage, false);
        if (PPApplication.deviceIsOnePlus)
            systemValue = systemValue * 4; // convert from 256 to 1024
        else
        if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)
            systemValue = systemValue * 16; // convert from 256 to 4096

        /*if (PPApplication.logEnabled()) {
            PPApplication.logE("getBrightnessValue_A9", "percentage=" + percentage);
            PPApplication.logE("getBrightnessValue_A9", "systemValue=" + systemValue);
        }*/

        return Math.round(systemValue);
    }

    ///////////////

    static int convertPercentsToBrightnessManualValue(int percentage, Context context)
    {
        int maximumValue;// = getMaximumScreenBrightnessSetting();
        int minimumValue;// = getMinimumScreenBrightnessSetting();

        //PPApplication.logE("convertPercentsToBrightnessManualValue", "maximumValue="+getMaximumScreenBrightnessSetting(context.getApplicationContext()));
        //PPApplication.logE("convertPercentsToBrightnessManualValue", "minimumValue="+getMinimumScreenBrightnessSetting(context.getApplicationContext()));

        //if (maximumValue-minimumValue > 255) {
            minimumValue = 0;
            maximumValue = 255;
        if (PPApplication.deviceIsOnePlus && (Build.VERSION.SDK_INT >= 28))
            maximumValue = 1023;
        else
        if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI && (Build.VERSION.SDK_INT >= 28))
            maximumValue = 4095;
        //}

        int value;

        if (percentage == BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET) {
            // brightness is not set, change it to default manual brightness value
            int defaultValue = 128;
            if (PPApplication.deviceIsOnePlus && (Build.VERSION.SDK_INT >= 28))
                defaultValue = 512;
            else
            if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI && (Build.VERSION.SDK_INT >= 28))
                defaultValue = 2048;

            if ((Build.VERSION.SDK_INT > 28) &&
                    (!PPApplication.deviceIsSamsung) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT > 28");
                defaultValue = getBrightnessValue_A9(50/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and Nexus");
                defaultValue = getBrightnessValue_A9(50/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && (!PPApplication.deviceIsSamsung) && (!PPApplication.deviceIsLG)/* && (!PPApplication.romIsOnePlus)*/) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and !Samsung and !LG");
                defaultValue = getBrightnessValue_A9(50/*, minimumValue, maximumValue*/);
            }
            /*else {
                PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 NOT called");
            }*/
            value = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, defaultValue);
        }
        else {
            /*if (PPApplication.logEnabled()) {
                try {
                    int oldValue = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                    PPApplication.logE("convertPercentsToBrightnessManualValue", "oldValue=" + oldValue);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            }*/
            if ((Build.VERSION.SDK_INT > 28) &&
                    (!PPApplication.deviceIsSamsung) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT > 28");
                value = getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and Nexus");
                value = getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && (!PPApplication.deviceIsSamsung) && (!PPApplication.deviceIsLG)/* && (!PPApplication.romIsOnePlus)*/) {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 called - SDK_INT == 28 and !Samsung and !LG");
                value = getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/);
            }
            else {
                //PPApplication.logE("convertPercentsToBrightnessManualValue", "getBrightnessValue_A9 NOT called");
                value = Math.round((float) (maximumValue - minimumValue) / 100 * percentage) + minimumValue;
            }
        }

        //PPApplication.logE("convertPercentsToBrightnessManualValue", "value="+value);
        return value;
    }

    int getDeviceBrightnessManualValue(Context context)
    {
        int percentage = getDeviceBrightnessValue();
        return convertPercentsToBrightnessManualValue(percentage, context);
    }

    static float convertPercentsToBrightnessAdaptiveValue(int percentage, Context context)
    {
        float value;

        if (percentage == BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET)
            // brightness is not set, change it to default adaptive brightness value
            value = Settings.System.getFloat(context.getContentResolver(),
                                Settings.System.SCREEN_AUTO_BRIGHTNESS_ADJ, 0f);
        else {
            boolean exponentialLevel = false;
            if ((Build.VERSION.SDK_INT > 28) &&
                    (!PPApplication.deviceIsSamsung) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=true - SDK_INT > 28");
                exponentialLevel = true;
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=true - SDK_INT == 28 and Nexus");
                exponentialLevel = true;
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && (!PPApplication.deviceIsSamsung) && (!PPApplication.deviceIsLG)/* && (!PPApplication.romIsOnePlus)*/) {
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=true - SDK_INT == 28 and !Samsung and !LG");
                exponentialLevel = true;
            }
            /*else {
                PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "exponentialLevel=false");
            }*/

            if (!exponentialLevel)
                value = (percentage - 50) / 50f;
            else {
//                int maximumValue;// = getMaximumScreenBrightnessSetting();
//                int minimumValue;// = getMinimumScreenBrightnessSetting();

                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "maximumValue="+getMaximumScreenBrightnessSetting(context.getApplicationContext()));
                //PPApplication.logE("convertPercentsToBrightnessAdaptiveValue", "minimumValue="+getMinimumScreenBrightnessSetting(context.getApplicationContext()));

                //if (maximumValue-minimumValue > 255) {
//                minimumValue = 0;
//                maximumValue = 255;
//                if (PPApplication.romIsOnePlus)
//                    maximumValue = 1023;
                //}

                if (PPApplication.deviceIsOnePlus)
                    value = (getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/) - 512) / 512f;
                else
                if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)
                    value = (getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/) - 2048) / 2048f;
                else
                    value = (getBrightnessValue_A9(percentage/*, minimumValue, maximumValue*/) - 128) / 128f;
            }
        }

        return value;
    }

    float getDeviceBrightnessAdaptiveValue(Context context)
    {
        int percentage = getDeviceBrightnessValue();
        return convertPercentsToBrightnessAdaptiveValue(percentage, context);
    }

    @SuppressWarnings("SameParameterValue")
    static long convertBrightnessToPercents(int value/*, int maxValue, int minValue*/)
    {
        long percentage;
        if (value == BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET)
            percentage = value; // keep BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET
        else {
            if ((Build.VERSION.SDK_INT > 28) &&
                    (!PPApplication.deviceIsSamsung) &&
                    (!PPApplication.deviceIsOnePlus) &&
                    (!PPApplication.deviceIsLenovo)) {
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 called - SDK_INT > 28");
                percentage = getBrightnessPercentage_A9(value/*, minValue, maxValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && Build.MODEL.contains("Nexus")) {// Nexus may be LG, Samsung, Huawei, ...
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 called - SDK_INT == 28 and Nexus");
                percentage = getBrightnessPercentage_A9(value/*, minValue, maxValue*/);
            }
            else
            if ((Build.VERSION.SDK_INT == 28) && (!PPApplication.deviceIsSamsung) && (!PPApplication.deviceIsLG)) {
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 called - SDK_INT == 28 and !Samsung and !LG");
                percentage = getBrightnessPercentage_A9(value/*, minValue, maxValue*/);
            }
            else {
                //PPApplication.logE("convertBrightnessToPercents", "getBrightnessPercentage_A9 NOT called");
                //if (maximumValue-minimumValue > 255) {
                //int minimumValue = 0;
                int maximumValue = 255;
                if (PPApplication.deviceIsOnePlus && (Build.VERSION.SDK_INT >= 28))
                    maximumValue = 1023;
                else
                if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI && (Build.VERSION.SDK_INT >= 28))
                    maximumValue = 4095;
                //}

                percentage = Math.round((float) (value/* - minValue*/) / (maximumValue/* - minValue*/) * 100.0);
            }
        }

        return percentage;
    }

    /*
    public void setDeviceBrightnessManualValue(int value)
    {
        int maxValue = getMaximumScreenBrightnessSetting();
        int minValue = getMinimumScreenBrightnessSetting();

        if (maxValue-minValue > 65535) {
            minValue = 0;
            maxValue = 65535;
        }

        long percentage = convertBrightnessToPercents(value, maxValue, minValue);

        //value|noChange|automatic|sharedProfile
        String[] splits = _deviceBrightness.split("\\|");
        // hm, found brightness values without default profile :-/
        if (splits.length == 4)
            _deviceBrightness = String.valueOf(percentage)+"|"+splits[1]+"|"+splits[2]+"|"+ splits[3];
        else
            _deviceBrightness = String.valueOf(percentage)+"|"+splits[1]+"|"+splits[2]+"|0";
    }
    */
    /*
    public void setDeviceBrightnessAdaptiveValue(float value)
    {
        long percentage;
        if (value == BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET)
            percentage = Math.round(value); // keep BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET
        else
            percentage = Math.round(value * 50 + 50);

        //value|noChange|automatic|sharedProfile
        String[] splits = _deviceBrightness.split("\\|");
        // hm, found brightness values without default profile :-/
        if (splits.length == 4)
            _deviceBrightness = String.valueOf(percentage)+"|"+splits[1]+"|"+splits[2]+"|"+ splits[3];
        else
            _deviceBrightness = String.valueOf(percentage)+"|"+splits[1]+"|"+splits[2]+"|0";
    }
    */

    boolean getGenerateNotificationGenerate()
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    /*
    static boolean getGenerateNotificationGenerate(String _generateNotification)
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[0]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }
    */

    int getGenerateNotificationIconType()
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value;
    }

    static int getGenerateNotificationIconType(String _generateNotification)
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[1]);
        } catch (Exception e) {
            value = 1;
        }
        return value;
    }

    String getGenerateNotificationTitle()
    {
        String value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = splits[2];
        } catch (Exception e) {
            value = "x";
        }
        return value;
    }

    static String getGenerateNotificationTitle(String _generateNotification)
    {
        String value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = splits[2];
        } catch (Exception e) {
            value = "x";
        }
        return value;
    }

    String getGenerateNotificationBody()
    {
        String value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = splits[3];
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    static String getGenerateNotificationBody(String _generateNotification)
    {
        String value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = splits[3];
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    private boolean getGenerateNotificationSharedProfile()
    {
        int value;
        try {
            String[] splits = _generateNotification.split("\\|");
            value = Integer.parseInt(splits[4]);
        } catch (Exception e) {
            value = 0;
        }
        return value == 1;
    }

    //----------------------------------

    void generateIconBitmap(Context context, boolean monochrome, int monochromeValue, boolean useMonochromeValueForCustomIcon)
    {
        if (!getIsIconResourceID())
        {
            releaseIconBitmap();

            int height = GlobalGUIRoutines.dpToPx(GlobalGUIRoutines.ICON_SIZE_DP);
            int width = GlobalGUIRoutines.dpToPx(GlobalGUIRoutines.ICON_SIZE_DP);
            //Log.d("---- generateIconBitmap","resampleBitmapUri");
            _iconBitmap = BitmapManipulator.resampleBitmapUri(getIconIdentifier(), width, height, true, false, context);

            if (_iconBitmap == null)
            {
                // no icon found, set default icon
                _icon = "ic_profile_default|1|0|0";
                if (monochrome)
                {
                    //int iconResource = context.getResources().getIdentifier(getIconIdentifier(), "drawable", context.PPApplication.PACKAGE_NAME);
                    int iconResource = getIconResource(getIconIdentifier());
                    //Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), iconResource);
                    Bitmap bitmap = BitmapManipulator.getBitmapFromResource(iconResource, true, context);
                    _iconBitmap = BitmapManipulator.monochromeBitmap(bitmap, monochromeValue/*, context*/);
                    // getIsIconResourceID must return false
                    //_icon = getIconIdentifier() + "|0";
                }
            }
            else
            if (monochrome) {
                //PPApplication.logE("generateIconBitmap", "monochromeValue="+monochromeValue);
                float monoValue = 255f;
                if (monochromeValue == 0x00) monoValue = -255f;
                if (monochromeValue == 0x40) monoValue = -128f;
                if (monochromeValue == 0x80) monoValue = 0f;
                if (monochromeValue == 0xC0) monoValue = 128f;
                //if (monochromeValue == 0xFF) monoValue = 255f;
                _iconBitmap = BitmapManipulator.grayScaleBitmap(_iconBitmap);
                if (useMonochromeValueForCustomIcon)
                    _iconBitmap = BitmapManipulator.setBitmapBrightness(_iconBitmap, monoValue);
            }
            //_iconDrawable = null;
        }
        else
        if (monochrome)
        {
            //Resources resources = context.getResources();
            //int iconResource = resources.getIdentifier(getIconIdentifier(), "drawable", context.PPApplication.PACKAGE_NAME);
            int iconResource = getIconResource(getIconIdentifier());
            //int height = (int) resources.getDimension(android.R.dimen.app_icon_size);
            //int width = (int) resources.getDimension(android.R.dimen.app_icon_size);
            //Bitmap bitmap = BitmapManipulator.resampleResource(resources, iconResource, width, height);
            Bitmap bitmap = BitmapManipulator.getBitmapFromResource(iconResource, true, context);
            _iconBitmap = BitmapManipulator.monochromeBitmap(bitmap, monochromeValue/*, context*/);
            // getIsIconResourceID must return false
            //_icon = getIconIdentifier() + "|0";
            /*Drawable drawable;
            drawable = ContextCompat.getDrawable(context, iconResource);
            //if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            //    drawable = context.getResources().getDrawable(iconResource, context.getTheme());
            //} else {
            //    drawable = context.getResources().getDrawable(iconResource);
            //}
            _iconDrawable = BitmapManipulator.tintDrawableByValue(drawable, monochromeValue);
            _iconBitmap = null;*/
        }
        else
        if (getUseCustomColorForIcon()) {
            //Resources resources = context.getResources();
            //int iconResource = resources.getIdentifier(getIconIdentifier(), "drawable", context.PPApplication.PACKAGE_NAME);
            int iconResource = getIconResource(getIconIdentifier());
            //int height = (int) resources.getDimension(android.R.dimen.app_icon_size);
            //int width = (int) resources.getDimension(android.R.dimen.app_icon_size);
            //Bitmap bitmap = BitmapManipulator.resampleResource(resources, iconResource, width, height);
            Bitmap bitmap = BitmapManipulator.getBitmapFromResource(iconResource, true, context);
            _iconBitmap = BitmapManipulator.recolorBitmap(bitmap, getIconCustomColor()/*, context*/);
            // getIsIconResourceID must return false
            //_icon = getIconIdentifier() + "|0";
        }
        else
            _iconBitmap = null;
    }

    void generatePreferencesIndicator(Context context, boolean monochrome, int monochromeValue)
    {
        releasePreferencesIndicator();

        ProfilePreferencesIndicator indicators = new ProfilePreferencesIndicator();
        _preferencesIndicator = indicators.paint(this, monochrome, context);
        if (_preferencesIndicator != null) {
            if (monochrome)
                _preferencesIndicator = BitmapManipulator.monochromeBitmap(_preferencesIndicator, monochromeValue/*, context*/);
        }
    }

    private void releaseIconBitmap()
    {
        if (_iconBitmap != null)
        {
            //if (!_iconBitmap.isRecycled())
            //    _iconBitmap.recycle();
            _iconBitmap = null;
        }
    }

    private void releasePreferencesIndicator()
    {
        if (_preferencesIndicator != null)
        {
            //if (!_preferencesIndicator.isRecycled())
            //    _preferencesIndicator.recycle();
            _preferencesIndicator = null;
        }
    }

    Spannable getProfileNameWithDuration(String eventName, String indicators, boolean multiLine, boolean durationInNextLine, Context context) {
        String profileName = _name;
        if (!eventName.isEmpty())
            profileName = profileName + " " + eventName;
        String durationString = "";
        if (_askForDuration) {
            if (_checked) {
                long endDurationTime = ApplicationPreferences.prefActivatedProfileEndDurationTime;
                if (endDurationTime > 0) {
                    durationString = "(" + context.getString(R.string.duration_end_acronym) +": " + timeDateStringFromTimestamp(context, endDurationTime) + ")";
                }
            }
            else
                durationString = "[ " + context.getString(R.string.profile_event_name_ask_for_duration) + " ]";
        }
        else
        if ((_duration > 0) && (_afterDurationDo != AFTER_DURATION_DO_NOTHING)) {
            boolean showEndTime = false;
            if (_checked) {
                long endDurationTime = ApplicationPreferences.prefActivatedProfileEndDurationTime;
                if (endDurationTime > 0) {
                    durationString = "(" + context.getString(R.string.duration_end_acronym) +": " + timeDateStringFromTimestamp(context, endDurationTime) + ")";
                    showEndTime = true;
                }
            }
            if (!showEndTime) {
                durationString = "[" + GlobalGUIRoutines.getDurationString(_duration) + "]";
            }
        }
        int startSpan = profileName.length();
        if (!indicators.isEmpty()) {
            if (multiLine)
                profileName = profileName + "\n" + indicators;
            else
                profileName = profileName + " " + indicators;
        }
        if (!durationString.isEmpty()) {
            if (durationInNextLine) {
                if (_checked)
                    profileName = durationString + "\n" + profileName;
                else
                    profileName = profileName + "\n" + durationString;
            }
            else
                profileName = profileName + " " + durationString;
        }
        Spannable sbt = new SpannableString(profileName);
        if (!durationString.isEmpty()) {
            if (durationInNextLine && _checked)
                sbt.setSpan(new RelativeSizeSpan(0.8f), 0, durationString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            else
                sbt.setSpan(new RelativeSizeSpan(0.8f), startSpan, profileName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sbt;
    }

    @SuppressLint("SimpleDateFormat")
    private static String timeDateStringFromTimestamp(Context applicationContext, long timestamp){
        String timeDate;
        String timestampDate = android.text.format.DateFormat.getDateFormat(applicationContext).format(new Date(timestamp));
        Calendar calendar = Calendar.getInstance();
        String currentDate = android.text.format.DateFormat.getDateFormat(applicationContext).format(new Date(calendar.getTimeInMillis()));
        String androidDateTime;
        if (timestampDate.equals(currentDate))
            androidDateTime=android.text.format.DateFormat.getTimeFormat(applicationContext).format(new Date(timestamp));
        else
            androidDateTime=android.text.format.DateFormat.getDateFormat(applicationContext).format(new Date(timestamp))+" "+
                    android.text.format.DateFormat.getTimeFormat(applicationContext).format(new Date(timestamp));
        String javaDateTime = DateFormat.getDateTimeInstance().format(new Date(timestamp));
        String AmPm="";
        if(!Character.isDigit(androidDateTime.charAt(androidDateTime.length()-1))) {
            if(androidDateTime.contains(new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.AM])){
                AmPm=" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.AM];
            }else{
                AmPm=" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.PM];
            }
            androidDateTime=androidDateTime.replace(AmPm, "");
        }
        if(!Character.isDigit(javaDateTime.charAt(javaDateTime.length()-1))){
            javaDateTime=javaDateTime.replace(" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.AM], "");
            javaDateTime=javaDateTime.replace(" "+new SimpleDateFormat().getDateFormatSymbols().getAmPmStrings()[Calendar.PM], "");
        }
        javaDateTime=javaDateTime.substring(javaDateTime.length()-3);
        timeDate=androidDateTime.concat(javaDateTime);
        return timeDate.concat(AmPm);
    }

    /*
    private static String getVolumeLevelString(int percentage, int maxValue)
    {
        //noinspection WrapperTypeMayBePrimitive
        Double dValue = maxValue / 100.0 * percentage;
        return String.valueOf(dValue.intValue());
    }
    */

    /*
    static Profile getProfileFromSharedPreferences(Context context)
    {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        int maximumValueRing = 7;
        int maximumValueNotification = 7;
        int maximumValueMusic = 15;
        int maximumValueAlarm = 7;
        int maximumValueSystem = 7;
        int maximumValueVoiceCall = 7;
        int maximumValueDTMF = 7;
        int maximumValueAccessibility = 7;
        int maximumValueBluetoothSCO = 7;
        if (audioManager != null) {
            maximumValueRing = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            maximumValueNotification = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
            maximumValueMusic = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            maximumValueAlarm = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            maximumValueSystem = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
            maximumValueVoiceCall = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
            maximumValueDTMF = audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF);
            if (Build.VERSION.SDK_INT >= 26)
                maximumValueAccessibility = audioManager.getStreamMaxVolume(AudioManager.STREAM_ACCESSIBILITY);
            maximumValueBluetoothSCO = audioManager.getStreamMaxVolume(ActivateProfileHelper.STREAM_BLUETOOTH_SCO);
        }

        SharedPreferences preferences = context.getSharedPreferences("profile_preferences_default_profile", Context.MODE_PRIVATE);

        Profile profile = new Profile();

        //if (prefsName.equals(PPApplication.SHARED_PROFILE_PREFS_NAME)) {
        //    profile._id = SHARED_PROFILE_ID;
        //    profile._name = context.getResources().getString(R.string.default_profile_name);
        //    profile._icon = PROFILE_ICON_DEFAULT+"1|0|0";
        //    profile._checked = false;
        //}
        //else {
            profile._id = preferences.getLong(PREF_PROFILE_ID, 0);
            profile._name = preferences.getString(PREF_PROFILE_NAME, context.getResources().getString(R.string.profile_name_default));
            profile._icon = preferences.getString(PREF_PROFILE_ICON, PROFILE_ICON_DEFAULT+"1|0|0");
            profile._checked = preferences.getBoolean(PREF_PROFILE_CHECKED, false);
        //}

        profile._porder = 0;
        profile._duration = 0;
        //profile._afterDurationDo = AFTER_DURATION_DO_RESTART_EVENTS;
        profile._afterDurationDo = preferences.getInt(PREF_PROFILE_AFTER_DURATION_DO, AFTER_DURATION_DO_RESTART_EVENTS);
        profile._afterDurationProfile = PROFILE_NO_ACTIVATE;
        profile._durationNotificationSound = "";
        profile._durationNotificationVibrate = false;
        profile._activationByUserCount = 0;
        profile._volumeRingerMode = Integer.parseInt(preferences.getString(PREF_PROFILE_VOLUME_RINGER_MODE, "1")); // ring
        profile._volumeZenMode = Integer.parseInt(preferences.getString(PREF_PROFILE_VOLUME_ZEN_MODE, "1")); // all
        profile._volumeRingtone = preferences.getString(PREF_PROFILE_VOLUME_RINGTONE, getVolumeLevelString(71, maximumValueRing) + "|0|0");
        profile._volumeNotification = preferences.getString(PREF_PROFILE_VOLUME_NOTIFICATION, getVolumeLevelString(86, maximumValueNotification)+"|0|0");
        profile._volumeMedia = preferences.getString(PREF_PROFILE_VOLUME_MEDIA, getVolumeLevelString(80, maximumValueMusic)+"|0|0");
        profile._volumeAlarm = preferences.getString(PREF_PROFILE_VOLUME_ALARM, getVolumeLevelString(100, maximumValueAlarm)+"|0|0");
        profile._volumeSystem = preferences.getString(PREF_PROFILE_VOLUME_SYSTEM, getVolumeLevelString(70, maximumValueSystem)+"|0|0");
        profile._volumeVoice = preferences.getString(PREF_PROFILE_VOLUME_VOICE, getVolumeLevelString(70, maximumValueVoiceCall)+"|0|0");
        profile._soundRingtoneChange = Integer.parseInt(preferences.getString(PREF_PROFILE_SOUND_RINGTONE_CHANGE, "0"));
        profile._soundRingtone = preferences.getString(PREF_PROFILE_SOUND_RINGTONE, Settings.System.DEFAULT_RINGTONE_URI.toString());
        profile._soundNotificationChange = Integer.parseInt(preferences.getString(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE, "0"));
        profile._soundNotification = preferences.getString(PREF_PROFILE_SOUND_NOTIFICATION, Settings.System.DEFAULT_NOTIFICATION_URI.toString());
        profile._soundAlarmChange = Integer.parseInt(preferences.getString(PREF_PROFILE_SOUND_ALARM_CHANGE, "0"));
        profile._soundAlarm = preferences.getString(PREF_PROFILE_SOUND_ALARM, Settings.System.DEFAULT_ALARM_ALERT_URI.toString());
        profile._deviceAirplaneMode = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_AIRPLANE_MODE, "2")); // OFF
        profile._deviceWiFi = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_WIFI, "2")); // OFF
        profile._deviceBluetooth = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_BLUETOOTH, "2")); //OFF
        profile._deviceScreenTimeout = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_SCREEN_TIMEOUT, "2")); // 30 seconds
        profile._deviceBrightness = preferences.getString(PREF_PROFILE_DEVICE_BRIGHTNESS, BRIGHTNESS_ADAPTIVE_BRIGHTNESS_NOT_SET + "|0|1|0");  // automatic on
        profile._deviceWallpaperChange = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_WALLPAPER_CHANGE, "0"));
        profile._deviceWallpaper = preferences.getString(PREF_PROFILE_DEVICE_WALLPAPER, "-");
        profile._deviceMobileData = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_MOBILE_DATA, "1")); //ON
        profile._deviceMobileDataPrefs = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS, "0"));
        profile._deviceGPS = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_GPS, "2")); //OFF
        profile._deviceRunApplicationChange = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_RUN_APPLICATION_CHANGE, "0"));
        profile._deviceRunApplicationPackageName = preferences.getString(PREF_PROFILE_DEVICE_RUN_APPLICATION_PACKAGE_NAME, "-");
        profile._deviceAutoSync = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_AUTOSYNC, "1")); // ON
        profile._deviceAutoRotate = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_AUTOROTATE, "1")); // ON
        profile._deviceLocationServicePrefs = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_LOCATION_SERVICE_PREFS, "0"));
        profile._volumeSpeakerPhone = Integer.parseInt(preferences.getString(PREF_PROFILE_VOLUME_SPEAKER_PHONE, "0"));
        profile._deviceNFC = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_NFC, "0"));
        profile._deviceKeyguard = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_KEYGUARD, "0"));
        profile._vibrationOnTouch = Integer.parseInt(preferences.getString(PREF_PROFILE_VIBRATION_ON_TOUCH, "0"));
        profile._deviceWiFiAP = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_WIFI_AP, "2")); // OFF
        profile._devicePowerSaveMode = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_POWER_SAVE_MODE, "0"));
        profile._deviceNetworkType = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_NETWORK_TYPE, "0"));
        profile._notificationLed = Integer.parseInt(preferences.getString(PREF_PROFILE_NOTIFICATION_LED, "0"));
        profile._vibrateWhenRinging = Integer.parseInt(preferences.getString(PREF_PROFILE_VIBRATE_WHEN_RINGING, "0"));
        profile._deviceWallpaperFor = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_WALLPAPER_FOR, "0"));
        profile._lockDevice = Integer.parseInt(preferences.getString(PREF_PROFILE_LOCK_DEVICE, "0"));
        profile._deviceConnectToSSID = preferences.getString(PREF_PROFILE_DEVICE_CONNECT_TO_SSID, CONNECTTOSSID_JUSTANY);
        profile._applicationDisableWifiScanning = Integer.parseInt(preferences.getString(PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING, "0"));
        profile._applicationDisableBluetoothScanning = Integer.parseInt(preferences.getString(PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING, "0"));
        profile._deviceWiFiAPPrefs = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_WIFI_AP_PREFS, "0"));
        profile._applicationDisableLocationScanning = Integer.parseInt(preferences.getString(PREF_PROFILE_APPLICATION_DISABLE_LOCATION_SCANNING, "0"));
        profile._applicationDisableMobileCellScanning = Integer.parseInt(preferences.getString(PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING, "0"));
        profile._applicationDisableOrientationScanning = Integer.parseInt(preferences.getString(PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING, "0"));
        profile._headsUpNotifications = Integer.parseInt(preferences.getString(PREF_PROFILE_HEADS_UP_NOTIFICATIONS, "0"));
        profile._deviceForceStopApplicationChange = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_CHANGE, "0"));
        profile._deviceForceStopApplicationPackageName = preferences.getString(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_PACKAGE_NAME, "-");
        profile._deviceNetworkTypePrefs = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS, "0"));
        profile._deviceCloseAllApplications = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_CLOSE_ALL_APPLICATIONS, "0"));
        profile._screenDarkMode = Integer.parseInt(preferences.getString(PREF_PROFILE_SCREEN_DARK_MODE, "0"));
        profile._dtmfToneWhenDialing = Integer.parseInt(preferences.getString(PREF_PROFILE_DTMF_TONE_WHEN_DIALING, "0"));
        profile._soundOnTouch = Integer.parseInt(preferences.getString(PREF_PROFILE_SOUND_ON_TOUCH, "0"));
        profile._volumeDTMF = preferences.getString(PREF_PROFILE_VOLUME_DTMF, getVolumeLevelString(70, maximumValueDTMF)+"|0|0");
        profile._volumeAccessibility = preferences.getString(PREF_PROFILE_VOLUME_ACCESSIBILITY, getVolumeLevelString(80, maximumValueAccessibility)+"|0|0");
        profile._volumeBluetoothSCO = preferences.getString(PREF_PROFILE_VOLUME_BLUETOOTH_SCO, getVolumeLevelString(80, maximumValueBluetoothSCO)+"|0|0");
        profile._alwaysOnDisplay = Integer.parseInt(preferences.getString(PREF_PROFILE_ALWAYS_ON_DISPLAY, "0"));
        profile._screenOnPermanent = Integer.parseInt(preferences.getString(PREF_PROFILE_SCREEN_ON_PERMANENT, "0"));
        profile._volumeMuteSound = preferences.getBoolean(PREF_PROFILE_VOLUME_MUTE_SOUND, false);
        profile._deviceLocationMode = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_LOCATION_MODE, "0"));
        profile._applicationDisableNotificationScanning = Integer.parseInt(preferences.getString(PREF_PROFILE_APPLICATION_DISABLE_NOTIFICATION_SCANNING, "0"));
        profile._generateNotification = preferences.getString(PREF_PROFILE_GENERATE_NOTIFICATION, "0|0||");
        profile._cameraFlash = Integer.parseInt(preferences.getString(PREF_PROFILE_CAMERA_FLASH, "0"));
        profile._deviceNetworkTypeSIM1 = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1, "0"));
        profile._deviceNetworkTypeSIM2 = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2, "0"));
        profile._deviceMobileDataSIM1 = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1, "1")); //ON
        profile._deviceMobileDataSIM2 = Integer.parseInt(preferences.getString(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2, "1")); //ON
        profile._deviceDefaultSIMCards = preferences.getString(PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS, "0|0|0");

        return profile;
    }
    */
    /*
    static void saveProfileToSharedPreferences(Profile profile, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PPApplication.ACTIVATED_PROFILE_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(PREF_PROFILE_ID, profile._id);
        editor.putString(PREF_PROFILE_NAME, profile._name);
        editor.putString(PREF_PROFILE_ICON, profile._icon);
        editor.putBoolean(PREF_PROFILE_CHECKED, profile._checked);
        editor.putInt(PREF_PROFILE_AFTER_DURATION_DO, profile._afterDurationDo);
        editor.putString(PREF_PROFILE_VOLUME_RINGER_MODE, String.valueOf(profile._volumeRingerMode));
        editor.putString(PREF_PROFILE_VOLUME_ZEN_MODE, String.valueOf(profile._volumeZenMode));
        editor.putString(PREF_PROFILE_VOLUME_RINGTONE, profile._volumeRingtone);
        editor.putString(PREF_PROFILE_VOLUME_NOTIFICATION, profile._volumeNotification);
        editor.putString(PREF_PROFILE_VOLUME_MEDIA, profile._volumeMedia);
        editor.putString(PREF_PROFILE_VOLUME_ALARM, profile._volumeAlarm);
        editor.putString(PREF_PROFILE_VOLUME_SYSTEM, profile._volumeSystem);
        editor.putString(PREF_PROFILE_VOLUME_VOICE, profile._volumeVoice);
        editor.putString(PREF_PROFILE_SOUND_RINGTONE_CHANGE, String.valueOf(profile._soundRingtoneChange));
        editor.putString(PREF_PROFILE_SOUND_RINGTONE, profile._soundRingtone);
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE, String.valueOf(profile._soundNotificationChange));
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION, profile._soundNotification);
        editor.putString(PREF_PROFILE_SOUND_ALARM_CHANGE, String.valueOf(profile._soundAlarmChange));
        editor.putString(PREF_PROFILE_SOUND_ALARM, profile._soundAlarm);
        editor.putString(PREF_PROFILE_DEVICE_AIRPLANE_MODE, String.valueOf(profile._deviceAirplaneMode));
        editor.putString(PREF_PROFILE_DEVICE_WIFI, String.valueOf(profile._deviceWiFi));
        editor.putString(PREF_PROFILE_DEVICE_BLUETOOTH, String.valueOf(profile._deviceBluetooth));
        editor.putString(PREF_PROFILE_DEVICE_SCREEN_TIMEOUT, String.valueOf(profile._deviceScreenTimeout));
        editor.putString(PREF_PROFILE_DEVICE_BRIGHTNESS, profile._deviceBrightness);
        editor.putString(PREF_PROFILE_DEVICE_WALLPAPER_CHANGE, String.valueOf(profile._deviceWallpaperChange));
        editor.putString(PREF_PROFILE_DEVICE_WALLPAPER, profile._deviceWallpaper);
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA, String.valueOf(profile._deviceMobileData));
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS, String.valueOf(profile._deviceMobileDataPrefs));
        editor.putString(PREF_PROFILE_DEVICE_GPS, String.valueOf(profile._deviceGPS));
        editor.putString(PREF_PROFILE_DEVICE_RUN_APPLICATION_CHANGE, String.valueOf(profile._deviceRunApplicationChange));
        editor.putString(PREF_PROFILE_DEVICE_RUN_APPLICATION_PACKAGE_NAME, profile._deviceRunApplicationPackageName);
        editor.putString(PREF_PROFILE_DEVICE_AUTOSYNC, String.valueOf(profile._deviceAutoSync));
        editor.putString(PREF_PROFILE_DEVICE_AUTOROTATE, String.valueOf(profile._deviceAutoRotate));
        editor.putString(PREF_PROFILE_DEVICE_LOCATION_SERVICE_PREFS, String.valueOf(profile._deviceLocationServicePrefs));
        editor.putString(PREF_PROFILE_VOLUME_SPEAKER_PHONE, String.valueOf(profile._volumeSpeakerPhone));
        editor.putString(PREF_PROFILE_DEVICE_NFC, String.valueOf(profile._deviceNFC));
        editor.putString(PREF_PROFILE_DEVICE_KEYGUARD, String.valueOf(profile._deviceKeyguard));
        editor.putString(PREF_PROFILE_VIBRATION_ON_TOUCH, String.valueOf(profile._vibrationOnTouch));
        editor.putString(PREF_PROFILE_DEVICE_WIFI_AP, String.valueOf(profile._deviceWiFiAP));
        editor.putString(PREF_PROFILE_DEVICE_POWER_SAVE_MODE, String.valueOf(profile._devicePowerSaveMode));
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE, String.valueOf(profile._deviceNetworkType));
        editor.putString(PREF_PROFILE_NOTIFICATION_LED, String.valueOf(profile._notificationLed));
        editor.putString(PREF_PROFILE_VIBRATE_WHEN_RINGING, String.valueOf(profile._vibrateWhenRinging));
        editor.putString(PREF_PROFILE_DEVICE_WALLPAPER_FOR, String.valueOf(profile._deviceWallpaperFor));
        editor.putString(PREF_PROFILE_LOCK_DEVICE, String.valueOf(profile._lockDevice));
        editor.putString(PREF_PROFILE_DEVICE_CONNECT_TO_SSID, profile._deviceConnectToSSID);
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING, String.valueOf(profile._applicationDisableWifiScanning));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING, String.valueOf(profile._applicationDisableBluetoothScanning));
        editor.putString(PREF_PROFILE_DEVICE_WIFI_AP_PREFS, String.valueOf(profile._deviceWiFiAPPrefs));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_LOCATION_SCANNING, String.valueOf(profile._applicationDisableLocationScanning));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING, String.valueOf(profile._applicationDisableMobileCellScanning));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING, String.valueOf(profile._applicationDisableOrientationScanning));
        editor.putString(PREF_PROFILE_HEADS_UP_NOTIFICATIONS, String.valueOf(profile._headsUpNotifications));
        editor.putString(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_CHANGE, String.valueOf(profile._deviceForceStopApplicationChange));
        editor.putString(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_PACKAGE_NAME, profile._deviceForceStopApplicationPackageName);
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS, String.valueOf(profile._deviceNetworkTypePrefs));
        editor.putString(PREF_PROFILE_DEVICE_CLOSE_ALL_APPLICATIONS, String.valueOf(profile._deviceCloseAllApplications));
        editor.putString(PREF_PROFILE_SCREEN_DARK_MODE, String.valueOf(profile._screenDarkMode));
        editor.putString(PREF_PROFILE_DTMF_TONE_WHEN_DIALING, String.valueOf(profile._dtmfToneWhenDialing));
        editor.putString(PREF_PROFILE_SOUND_ON_TOUCH, String.valueOf(profile._soundOnTouch));
        editor.putString(PREF_PROFILE_VOLUME_DTMF, profile._volumeDTMF);
        editor.putString(PREF_PROFILE_VOLUME_ACCESSIBILITY, profile._volumeAccessibility);
        editor.putString(PREF_PROFILE_VOLUME_BLUETOOTH_SCO, profile._volumeBluetoothSCO);
        editor.putString(PREF_PROFILE_ALWAYS_ON_DISPLAY, String.valueOf(profile._alwaysOnDisplay));
        editor.putString(PREF_PROFILE_SCREEN_ON_PERMANENT, String.valueOf(profile._screenOnPermanent));
        editor.putBoolean(PREF_PROFILE_VOLUME_MUTE_SOUND, profile._volumeMuteSound);
        editor.putString(PREF_PROFILE_DEVICE_LOCATION_MODE, String.valueOf(profile._deviceLocationMode));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_NOTIFICATION_SCANNING, String.valueOf(profile._applicationDisableNotificationScanning));
        editor.putString(PREF_PROFILE_GENERATE_NOTIFICATION, profile._generateNotification);
        editor.putString(PREF_PROFILE_CAMERA_FLASH, String.valueOf(profile._cameraFlash));
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1, String.valueOf(profile._deviceNetworkTypeSIM1));
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2, String.valueOf(profile._deviceNetworkTypeSIM2));
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1, String.valueOf(profile._deviceMobileDataSIM1));
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2, String.valueOf(profile._deviceMobileDataSIM2));

        editor.apply();
    }
    */
    /*
    static Profile getMappedProfile(Profile profile, Profile sharedProfile)
    {
        final int SHARED_PROFILE_VALUE = 99;
        final String CONNECTTOSSID_SHAREDPROFILE = "^default_profile^";

        if (profile != null)
        {
            Profile sharedProfile = getProfileFromSharedPreferences(context, PPApplication.SHARED_PROFILE_PREFS_NAME);

            Profile mappedProfile = new Profile(
                    profile._id,
                    profile._name,
                    profile._icon,
                    profile._checked,
                    profile._porder,
                    profile._volumeRingerMode,
                    profile._volumeRingtone,
                    profile._volumeNotification,
                    profile._volumeMedia,
                    profile._volumeAlarm,
                    profile._volumeSystem,
                    profile._volumeVoice,
                    profile._soundRingtoneChange,
                    profile._soundRingtone,
                    profile._soundNotificationChange,
                    profile._soundNotification,
                    profile._soundAlarmChange,
                    profile._soundAlarm,
                    profile._deviceAirplaneMode,
                    profile._deviceWiFi,
                    profile._deviceBluetooth,
                    profile._deviceScreenTimeout,
                    profile._deviceBrightness,
                    profile._deviceWallpaperChange,
                    profile._deviceWallpaper,
                    profile._deviceMobileData,
                    profile._deviceMobileDataPrefs,
                    profile._deviceGPS,
                    profile._deviceRunApplicationChange,
                    profile._deviceRunApplicationPackageName,
                    profile._deviceAutoSync,
                    profile._showInActivator,
                    profile._deviceAutoRotate,
                    profile._deviceLocationServicePrefs,
                    profile._volumeSpeakerPhone,
                    profile._deviceNFC,
                    profile._duration,
                    profile._afterDurationDo,
                    profile._volumeZenMode,
                    profile._deviceKeyguard,
                    profile._vibrationOnTouch,
                    profile._deviceWiFiAP,
                    profile._devicePowerSaveMode,
                    profile._askForDuration,
                    profile._deviceNetworkType,
                    profile._notificationLed,
                    profile._vibrateWhenRinging,
                    profile._deviceWallpaperFor,
                    profile._hideStatusBarIcon,
                    profile._lockDevice,
                    profile._deviceConnectToSSID,
                    profile._applicationDisableWifiScanning,
                    profile._applicationDisableBluetoothScanning,
                    profile._durationNotificationSound,
                    profile._durationNotificationVibrate,
                    profile._deviceWiFiAPPrefs,
                    profile._applicationDisableLocationScanning,
                    profile._applicationDisableMobileCellScanning,
                    profile._applicationDisableOrientationScanning,
                    profile._headsUpNotifications,
                    profile._deviceForceStopApplicationChange,
                    profile._deviceForceStopApplicationPackageName,
                    profile._activationByUserCount,
                    profile._deviceNetworkTypePrefs,
                    profile._deviceCloseAllApplications,
                    profile._screenDarkMode,
                    profile._dtmfToneWhenDialing,
                    profile._soundOnTouch,
                    profile._volumeDTMF,
                    profile._volumeAccessibility,
                    profile._volumeBluetoothSCO,
                    profile._afterDurationProfile,
                    profile._alwaysOnDisplay,
                    profile._screenOnPermanent,
                    profile._volumeMuteSound,
                    profile._deviceLocationMode,
                    profile._applicationDisableNotificationScanning,
                    profile._generateNotification,
                    profile._cameraFlash,
                    profile._deviceNetworkTypeSIM1,
                    profile._deviceNetworkTypeSIM2,
                    profile._deviceMobileDataSIM1,
                    profile._deviceMobileDataSIM2,
                    profile._deviceDefaultSIMCards
                    );

            boolean zenModeMapped = false;
            if (profile._volumeRingerMode == SHARED_PROFILE_VALUE) {
                mappedProfile._volumeRingerMode = sharedProfile._volumeRingerMode;
                if (mappedProfile._volumeRingerMode == RINGERMODE_ZENMODE) {
                    mappedProfile._volumeZenMode = sharedProfile._volumeZenMode;
                    zenModeMapped = true;
                }
            }
            if ((profile._volumeZenMode == SHARED_PROFILE_VALUE) && (!zenModeMapped))
                mappedProfile._volumeZenMode = sharedProfile._volumeZenMode;
            if (profile.getVolumeRingtoneSharedProfile())
                mappedProfile._volumeRingtone = sharedProfile._volumeRingtone;
            if (profile.getVolumeNotificationSharedProfile())
                mappedProfile._volumeNotification = sharedProfile._volumeNotification;
            if (profile.getVolumeAlarmSharedProfile())
                mappedProfile._volumeAlarm = sharedProfile._volumeAlarm;
            if (profile.getVolumeMediaSharedProfile())
                mappedProfile._volumeMedia = sharedProfile._volumeMedia;
            if (profile.getVolumeSystemSharedProfile())
                mappedProfile._volumeSystem = sharedProfile._volumeSystem;
            if (profile.getVolumeVoiceSharedProfile())
                mappedProfile._volumeVoice = sharedProfile._volumeVoice;
            if (profile._soundRingtoneChange == SHARED_PROFILE_VALUE)
            {
                mappedProfile._soundRingtoneChange = sharedProfile._soundRingtoneChange;
                mappedProfile._soundRingtone = sharedProfile._soundRingtone;
            }
            if (profile._soundNotificationChange == SHARED_PROFILE_VALUE)
            {
                mappedProfile._soundNotificationChange = sharedProfile._soundNotificationChange;
                mappedProfile._soundNotification = sharedProfile._soundNotification;
            }
            if (profile._soundAlarmChange == SHARED_PROFILE_VALUE)
            {
                mappedProfile._soundAlarmChange = sharedProfile._soundAlarmChange;
                mappedProfile._soundAlarm = sharedProfile._soundAlarm;
            }
            if (profile._deviceAirplaneMode == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAirplaneMode = sharedProfile._deviceAirplaneMode;
            if (profile._deviceAutoSync == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAutoSync = sharedProfile._deviceAutoSync;
            if (profile._deviceMobileData == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileData = sharedProfile._deviceMobileData;
            if (profile._deviceMobileDataPrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataPrefs = sharedProfile._deviceMobileDataPrefs;
            if (profile._deviceWiFi == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFi = sharedProfile._deviceWiFi;
            if (profile._deviceBluetooth == SHARED_PROFILE_VALUE)
                mappedProfile._deviceBluetooth = sharedProfile._deviceBluetooth;
            if (profile._deviceGPS == SHARED_PROFILE_VALUE)
                mappedProfile._deviceGPS = sharedProfile._deviceGPS;
            if (profile._deviceLocationServicePrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceLocationServicePrefs = sharedProfile._deviceLocationServicePrefs;
            if (profile._deviceScreenTimeout == SHARED_PROFILE_VALUE)
                mappedProfile._deviceScreenTimeout = sharedProfile._deviceScreenTimeout;
            if (profile.getDeviceBrightnessSharedProfile())
                mappedProfile._deviceBrightness = sharedProfile._deviceBrightness;
            if (profile._deviceAutoRotate == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAutoRotate = sharedProfile._deviceAutoRotate;
            if (profile._deviceRunApplicationChange == SHARED_PROFILE_VALUE)
            {
                mappedProfile._deviceRunApplicationChange = sharedProfile._deviceRunApplicationChange;
                mappedProfile._deviceRunApplicationPackageName = sharedProfile._deviceRunApplicationPackageName;
            }
            if (profile._deviceWallpaperChange == SHARED_PROFILE_VALUE)
            {
                mappedProfile._deviceWallpaperChange = sharedProfile._deviceWallpaperChange;
                mappedProfile._deviceWallpaper = sharedProfile._deviceWallpaper;
                mappedProfile._deviceWallpaperFor = sharedProfile._deviceWallpaperFor;
            }
            if (profile._volumeSpeakerPhone == SHARED_PROFILE_VALUE)
                mappedProfile._volumeSpeakerPhone = sharedProfile._volumeSpeakerPhone;
            if (profile._deviceNFC == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNFC = sharedProfile._deviceNFC;
            if (profile._deviceKeyguard == SHARED_PROFILE_VALUE)
                mappedProfile._deviceKeyguard = sharedProfile._deviceKeyguard;
            if (profile._vibrationOnTouch == SHARED_PROFILE_VALUE)
                mappedProfile._vibrationOnTouch = sharedProfile._vibrationOnTouch;
            if (profile._deviceWiFiAP == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFiAP = sharedProfile._deviceWiFiAP;
            if (profile._devicePowerSaveMode == SHARED_PROFILE_VALUE)
                mappedProfile._devicePowerSaveMode = sharedProfile._devicePowerSaveMode;
            if (profile._deviceNetworkType == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkType = sharedProfile._deviceNetworkType;
            if (profile._notificationLed == SHARED_PROFILE_VALUE)
                mappedProfile._notificationLed = sharedProfile._notificationLed;
            if (profile._vibrateWhenRinging == SHARED_PROFILE_VALUE)
                mappedProfile._vibrateWhenRinging = sharedProfile._vibrateWhenRinging;
            if (profile._lockDevice == SHARED_PROFILE_VALUE)
                mappedProfile._lockDevice = sharedProfile._lockDevice;
            if ((profile._deviceConnectToSSID != null) && (profile._deviceConnectToSSID.equals(CONNECTTOSSID_SHAREDPROFILE)))
                mappedProfile._deviceConnectToSSID = sharedProfile._deviceConnectToSSID;
            if (profile._applicationDisableWifiScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableWifiScanning = sharedProfile._applicationDisableWifiScanning;
            if (profile._applicationDisableBluetoothScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableBluetoothScanning = sharedProfile._applicationDisableBluetoothScanning;
            if (profile._deviceWiFiAPPrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFiAPPrefs = sharedProfile._deviceWiFiAPPrefs;
            if (profile._applicationDisableLocationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableLocationScanning = sharedProfile._applicationDisableLocationScanning;
            if (profile._applicationDisableMobileCellScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableMobileCellScanning = sharedProfile._applicationDisableMobileCellScanning;
            if (profile._applicationDisableOrientationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableOrientationScanning = sharedProfile._applicationDisableOrientationScanning;
            if (profile._headsUpNotifications == SHARED_PROFILE_VALUE)
                mappedProfile._headsUpNotifications = sharedProfile._headsUpNotifications;
            if (profile._deviceForceStopApplicationChange == SHARED_PROFILE_VALUE)
            {
                mappedProfile._deviceForceStopApplicationChange = sharedProfile._deviceForceStopApplicationChange;
                mappedProfile._deviceForceStopApplicationPackageName = sharedProfile._deviceForceStopApplicationPackageName;
            }
            if (profile._deviceNetworkTypePrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypePrefs = sharedProfile._deviceNetworkTypePrefs;
            if (profile._deviceCloseAllApplications == SHARED_PROFILE_VALUE)
                mappedProfile._deviceCloseAllApplications = sharedProfile._deviceCloseAllApplications;
            if (profile._screenDarkMode == SHARED_PROFILE_VALUE)
                mappedProfile._screenDarkMode = sharedProfile._screenDarkMode;
            if (profile._dtmfToneWhenDialing == SHARED_PROFILE_VALUE)
                mappedProfile._dtmfToneWhenDialing = sharedProfile._dtmfToneWhenDialing;
            if (profile._soundOnTouch == SHARED_PROFILE_VALUE)
                mappedProfile._soundOnTouch = sharedProfile._soundOnTouch;
            if (profile.getVolumeDTMFSharedProfile())
                mappedProfile._volumeDTMF = sharedProfile._volumeDTMF;
            if (profile.getVolumeAccessibilitySharedProfile())
                mappedProfile._volumeAccessibility = sharedProfile._volumeAccessibility;
            if (profile.getVolumeBluetoothSCOSharedProfile())
                mappedProfile._volumeBluetoothSCO = sharedProfile._volumeBluetoothSCO;
            if (profile._alwaysOnDisplay == SHARED_PROFILE_VALUE)
                mappedProfile._alwaysOnDisplay = sharedProfile._alwaysOnDisplay;
            if (profile._screenOnPermanent == SHARED_PROFILE_VALUE)
                mappedProfile._screenOnPermanent = sharedProfile._screenOnPermanent;
            if (profile._deviceLocationMode == SHARED_PROFILE_VALUE)
                mappedProfile._deviceLocationMode = sharedProfile._deviceLocationMode;
            if (profile._applicationDisableNotificationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableNotificationScanning = sharedProfile._applicationDisableNotificationScanning;
            if (profile.getGenerateNotificationSharedProfile())
                mappedProfile._generateNotification = sharedProfile._generateNotification;
            if (profile._cameraFlash == SHARED_PROFILE_VALUE)
                mappedProfile._cameraFlash = sharedProfile._cameraFlash;
            if (profile._deviceNetworkTypeSIM1 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypeSIM1 = sharedProfile._deviceNetworkTypeSIM1;
            if (profile._deviceNetworkTypeSIM2 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypeSIM2 = sharedProfile._deviceNetworkTypeSIM2;
            if (profile._deviceMobileDataSIM1 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataSIM1 = sharedProfile._deviceMobileDataSIM1;
            if (profile._deviceMobileDataSIM2 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataSIM2 = sharedProfile._deviceMobileDataSIM2;

            mappedProfile._iconBitmap = profile._iconBitmap;
            mappedProfile._preferencesIndicator = profile._preferencesIndicator;

            return mappedProfile;
        }
        else
            return null;
    }
    */

    static Profile removeSharedProfileParameters(Profile profile)
    {
        final int SHARED_PROFILE_VALUE = 99;
        final String CONNECTTOSSID_SHAREDPROFILE = "^default_profile^";

        if (profile != null)
        {
            //Profile sharedProfile = getProfileFromSharedPreferences(context, PPApplication.SHARED_PROFILE_PREFS_NAME);

            Profile mappedProfile = new Profile(
                    profile._id,
                    profile._name,
                    profile._icon,
                    profile._checked,
                    profile._porder,
                    profile._volumeRingerMode,
                    profile._volumeRingtone,
                    profile._volumeNotification,
                    profile._volumeMedia,
                    profile._volumeAlarm,
                    profile._volumeSystem,
                    profile._volumeVoice,
                    profile._soundRingtoneChange,
                    profile._soundRingtone,
                    profile._soundNotificationChange,
                    profile._soundNotification,
                    profile._soundAlarmChange,
                    profile._soundAlarm,
                    profile._deviceAirplaneMode,
                    profile._deviceWiFi,
                    profile._deviceBluetooth,
                    profile._deviceScreenTimeout,
                    profile._deviceBrightness,
                    profile._deviceWallpaperChange,
                    profile._deviceWallpaper,
                    profile._deviceMobileData,
                    profile._deviceMobileDataPrefs,
                    profile._deviceGPS,
                    profile._deviceRunApplicationChange,
                    profile._deviceRunApplicationPackageName,
                    profile._deviceAutoSync,
                    profile._showInActivator,
                    profile._deviceAutoRotate,
                    profile._deviceLocationServicePrefs,
                    profile._volumeSpeakerPhone,
                    profile._deviceNFC,
                    profile._duration,
                    profile._afterDurationDo,
                    profile._volumeZenMode,
                    profile._deviceKeyguard,
                    profile._vibrationOnTouch,
                    profile._deviceWiFiAP,
                    profile._devicePowerSaveMode,
                    profile._askForDuration,
                    profile._deviceNetworkType,
                    profile._notificationLed,
                    profile._vibrateWhenRinging,
                    profile._deviceWallpaperFor,
                    profile._hideStatusBarIcon,
                    profile._lockDevice,
                    profile._deviceConnectToSSID,
                    profile._applicationDisableWifiScanning,
                    profile._applicationDisableBluetoothScanning,
                    profile._durationNotificationSound,
                    profile._durationNotificationVibrate,
                    profile._deviceWiFiAPPrefs,
                    profile._applicationDisableLocationScanning,
                    profile._applicationDisableMobileCellScanning,
                    profile._applicationDisableOrientationScanning,
                    profile._headsUpNotifications,
                    profile._deviceForceStopApplicationChange,
                    profile._deviceForceStopApplicationPackageName,
                    profile._activationByUserCount,
                    profile._deviceNetworkTypePrefs,
                    profile._deviceCloseAllApplications,
                    profile._screenDarkMode,
                    profile._dtmfToneWhenDialing,
                    profile._soundOnTouch,
                    profile._volumeDTMF,
                    profile._volumeAccessibility,
                    profile._volumeBluetoothSCO,
                    profile._afterDurationProfile,
                    profile._alwaysOnDisplay,
                    profile._screenOnPermanent,
                    profile._volumeMuteSound,
                    profile._deviceLocationMode,
                    profile._applicationDisableNotificationScanning,
                    profile._generateNotification,
                    profile._cameraFlash,
                    profile._deviceNetworkTypeSIM1,
                    profile._deviceNetworkTypeSIM2,
                    profile._deviceMobileDataSIM1,
                    profile._deviceMobileDataSIM2,
                    profile._deviceDefaultSIMCards,
                    profile._deviceOnOffSIM1,
                    profile._deviceOnOffSIM2,
                    profile._soundRingtoneChangeSIM1,
                    profile._soundRingtoneSIM1,
                    profile._soundRingtoneChangeSIM2,
                    profile._soundRingtoneSIM2,
                    profile._soundNotificationChangeSIM1,
                    profile._soundNotificationSIM1,
                    profile._soundNotificationChangeSIM2,
                    profile._soundNotificationSIM2,
                    profile._soundSameRingtoneForBothSIMCards
            );

            if (profile._volumeRingerMode == SHARED_PROFILE_VALUE)
                mappedProfile._volumeRingerMode = 0;
            if (profile._volumeZenMode == SHARED_PROFILE_VALUE)
                mappedProfile._volumeZenMode = 0;
            if (profile.getVolumeRingtoneSharedProfile())
                mappedProfile._volumeRingtone = defaultValuesString.get(PREF_PROFILE_VOLUME_RINGTONE);
            if (profile.getVolumeNotificationSharedProfile())
                mappedProfile._volumeNotification = defaultValuesString.get(PREF_PROFILE_VOLUME_NOTIFICATION);
            if (profile.getVolumeAlarmSharedProfile())
                mappedProfile._volumeAlarm = defaultValuesString.get(PREF_PROFILE_VOLUME_ALARM);
            if (profile.getVolumeMediaSharedProfile())
                mappedProfile._volumeMedia = defaultValuesString.get(PREF_PROFILE_VOLUME_MEDIA);
            if (profile.getVolumeSystemSharedProfile())
                mappedProfile._volumeSystem = defaultValuesString.get(PREF_PROFILE_VOLUME_SYSTEM);
            if (profile.getVolumeVoiceSharedProfile())
                mappedProfile._volumeVoice = defaultValuesString.get(PREF_PROFILE_VOLUME_VOICE);
            if (profile._soundRingtoneChange == SHARED_PROFILE_VALUE)
                mappedProfile._soundRingtoneChange = 0;
            if (profile._soundNotificationChange == SHARED_PROFILE_VALUE)
                mappedProfile._soundNotificationChange = 0;
            if (profile._soundAlarmChange == SHARED_PROFILE_VALUE)
                mappedProfile._soundAlarmChange = 0;
            if (profile._deviceAirplaneMode == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAirplaneMode = 0;
            if (profile._deviceAutoSync == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAutoSync = 0;
            if (profile._deviceMobileData == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileData = 0;
            if (profile._deviceMobileDataPrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataPrefs = 0;
            if (profile._deviceWiFi == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFi = 0;
            if (profile._deviceBluetooth == SHARED_PROFILE_VALUE)
                mappedProfile._deviceBluetooth = 0;
            if (profile._deviceGPS == SHARED_PROFILE_VALUE)
                mappedProfile._deviceGPS = 0;
            if (profile._deviceLocationServicePrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceLocationServicePrefs = 0;
            if (profile._deviceScreenTimeout == SHARED_PROFILE_VALUE)
                mappedProfile._deviceScreenTimeout = 0;
            if (profile.getDeviceBrightnessSharedProfile())
                mappedProfile._deviceBrightness = defaultValuesString.get(PREF_PROFILE_DEVICE_BRIGHTNESS);
            if (profile._deviceAutoRotate == SHARED_PROFILE_VALUE)
                mappedProfile._deviceAutoRotate = 0;
            if (profile._deviceRunApplicationChange == SHARED_PROFILE_VALUE)
                mappedProfile._deviceRunApplicationChange = 0;
            if (profile._deviceWallpaperChange == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWallpaperChange = 0;
            if (profile._volumeSpeakerPhone == SHARED_PROFILE_VALUE)
                mappedProfile._volumeSpeakerPhone = 0;
            if (profile._deviceNFC == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNFC = 0;
            if (profile._deviceKeyguard == SHARED_PROFILE_VALUE)
                mappedProfile._deviceKeyguard = 0;
            if (profile._vibrationOnTouch == SHARED_PROFILE_VALUE)
                mappedProfile._vibrationOnTouch = 0;
            if (profile._deviceWiFiAP == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFiAP = 0;
            if (profile._devicePowerSaveMode == SHARED_PROFILE_VALUE)
                mappedProfile._devicePowerSaveMode = 0;
            if (profile._deviceNetworkType == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkType = 0;
            if (profile._notificationLed == SHARED_PROFILE_VALUE)
                mappedProfile._notificationLed = 0;
            if (profile._vibrateWhenRinging == SHARED_PROFILE_VALUE)
                mappedProfile._vibrateWhenRinging = 0;
            if (profile._lockDevice == SHARED_PROFILE_VALUE)
                mappedProfile._lockDevice = 0;
            if ((profile._deviceConnectToSSID != null) && (profile._deviceConnectToSSID.equals(CONNECTTOSSID_SHAREDPROFILE)))
                mappedProfile._deviceConnectToSSID = defaultValuesString.get(PREF_PROFILE_DEVICE_CONNECT_TO_SSID);
            if (profile._applicationDisableWifiScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableWifiScanning = 0;
            if (profile._applicationDisableBluetoothScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableBluetoothScanning = 0;
            if (profile._deviceWiFiAPPrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceWiFiAPPrefs = 0;
            if (profile._applicationDisableLocationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableLocationScanning = 0;
            if (profile._applicationDisableMobileCellScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableMobileCellScanning = 0;
            if (profile._applicationDisableOrientationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableOrientationScanning = 0;
            if (profile._headsUpNotifications == SHARED_PROFILE_VALUE)
                mappedProfile._headsUpNotifications = 0;
            if (profile._deviceForceStopApplicationChange == SHARED_PROFILE_VALUE)
                mappedProfile._deviceForceStopApplicationChange = 0;
            if (profile._deviceNetworkTypePrefs == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypePrefs = 0;
            if (profile._deviceCloseAllApplications == SHARED_PROFILE_VALUE)
                mappedProfile._deviceCloseAllApplications = 0;
            if (profile._screenDarkMode == SHARED_PROFILE_VALUE)
                mappedProfile._screenDarkMode = 0;
            if (profile._dtmfToneWhenDialing == SHARED_PROFILE_VALUE)
                mappedProfile._dtmfToneWhenDialing = 0;
            if (profile._soundOnTouch == SHARED_PROFILE_VALUE)
                mappedProfile._soundOnTouch = 0;
            if (profile.getVolumeDTMFSharedProfile())
                mappedProfile._volumeDTMF = defaultValuesString.get(PREF_PROFILE_VOLUME_DTMF);
            if (profile.getVolumeAccessibilitySharedProfile())
                mappedProfile._volumeAccessibility = defaultValuesString.get(PREF_PROFILE_VOLUME_ACCESSIBILITY);
            if (profile.getVolumeBluetoothSCOSharedProfile())
                mappedProfile._volumeBluetoothSCO = defaultValuesString.get(PREF_PROFILE_VOLUME_BLUETOOTH_SCO);
            if (profile._alwaysOnDisplay == SHARED_PROFILE_VALUE)
                mappedProfile._alwaysOnDisplay = 0;
            if (profile._screenOnPermanent == SHARED_PROFILE_VALUE)
                mappedProfile._screenOnPermanent = 0;
            if (profile._deviceLocationMode == SHARED_PROFILE_VALUE)
                mappedProfile._deviceLocationMode = 0;
            if (profile._applicationDisableNotificationScanning == SHARED_PROFILE_VALUE)
                mappedProfile._applicationDisableNotificationScanning = 0;
            if (profile.getGenerateNotificationSharedProfile())
                mappedProfile._generateNotification = defaultValuesString.get(PREF_PROFILE_GENERATE_NOTIFICATION);
            if (profile._cameraFlash == SHARED_PROFILE_VALUE)
                mappedProfile._cameraFlash = 0;
            if (profile._deviceNetworkTypeSIM1 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypeSIM1 = 0;
            if (profile._deviceNetworkTypeSIM2 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceNetworkTypeSIM2 = 0;
            if (profile._deviceMobileDataSIM1 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataSIM1 = 0;
            if (profile._deviceMobileDataSIM2 == SHARED_PROFILE_VALUE)
                mappedProfile._deviceMobileDataSIM2 = 0;
            // !!! do not add other profile aprameters. Shared profile is never used !!!

            mappedProfile._iconBitmap = profile._iconBitmap;
            mappedProfile._preferencesIndicator = profile._preferencesIndicator;

            return mappedProfile;
        }
        else
            return null;
    }

    void saveProfileToSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(PREF_PROFILE_NAME, this._name);
        editor.putString(PREF_PROFILE_ICON, this._icon);
        editor.putBoolean(PREF_PROFILE_SHOW_IN_ACTIVATOR, this._showInActivator);
        editor.putString(PREF_PROFILE_DURATION, Integer.toString(this._duration));
        editor.putString(PREF_PROFILE_AFTER_DURATION_DO, Integer.toString(this._afterDurationDo));
        editor.putBoolean(PREF_PROFILE_ASK_FOR_DURATION, this._askForDuration);
        editor.putString(PREF_PROFILE_DURATION_NOTIFICATION_SOUND, this._durationNotificationSound);
        editor.putBoolean(PREF_PROFILE_DURATION_NOTIFICATION_VIBRATE, this._durationNotificationVibrate);
        editor.putBoolean(PREF_PROFILE_HIDE_STATUS_BAR_ICON, this._hideStatusBarIcon);
        editor.putString(PREF_PROFILE_VOLUME_RINGER_MODE, Integer.toString(this._volumeRingerMode));
        editor.putString(PREF_PROFILE_VOLUME_ZEN_MODE, Integer.toString(this._volumeZenMode));
        editor.putString(PREF_PROFILE_VOLUME_RINGTONE, this._volumeRingtone);
        editor.putString(PREF_PROFILE_VOLUME_NOTIFICATION, this._volumeNotification);
        editor.putString(PREF_PROFILE_VOLUME_MEDIA, this._volumeMedia);
        editor.putString(PREF_PROFILE_VOLUME_ALARM, this._volumeAlarm);
        editor.putString(PREF_PROFILE_VOLUME_SYSTEM, this._volumeSystem);
        editor.putString(PREF_PROFILE_VOLUME_VOICE, this._volumeVoice);
        editor.putString(PREF_PROFILE_SOUND_RINGTONE_CHANGE, Integer.toString(this._soundRingtoneChange));
        String[] splits = this._soundRingtone.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_RINGTONE, splits[0]);
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE, Integer.toString(this._soundNotificationChange));
        splits = this._soundNotification.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION, splits[0]);
        editor.putString(PREF_PROFILE_SOUND_ALARM_CHANGE, Integer.toString(this._soundAlarmChange));
        splits = this._soundAlarm.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_ALARM, splits[0]);
        editor.putString(PREF_PROFILE_DEVICE_AIRPLANE_MODE, Integer.toString(this._deviceAirplaneMode));
        editor.putString(PREF_PROFILE_DEVICE_WIFI, Integer.toString(this._deviceWiFi));
        editor.putString(PREF_PROFILE_DEVICE_BLUETOOTH, Integer.toString(this._deviceBluetooth));
        editor.putString(PREF_PROFILE_DEVICE_SCREEN_TIMEOUT, Integer.toString(this._deviceScreenTimeout));
        editor.putString(PREF_PROFILE_DEVICE_BRIGHTNESS, this._deviceBrightness);
        editor.putString(PREF_PROFILE_DEVICE_WALLPAPER_CHANGE, Integer.toString(this._deviceWallpaperChange));
        editor.putString(PREF_PROFILE_DEVICE_WALLPAPER, this._deviceWallpaper);
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA, Integer.toString(this._deviceMobileData));
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS, Integer.toString(this._deviceMobileDataPrefs));
        editor.putString(PREF_PROFILE_DEVICE_GPS, Integer.toString(this._deviceGPS));
        editor.putString(PREF_PROFILE_DEVICE_RUN_APPLICATION_CHANGE, Integer.toString(this._deviceRunApplicationChange));
        editor.putString(PREF_PROFILE_DEVICE_RUN_APPLICATION_PACKAGE_NAME, this._deviceRunApplicationPackageName);
        editor.putString(PREF_PROFILE_DEVICE_AUTOSYNC, Integer.toString(this._deviceAutoSync));
        editor.putString(PREF_PROFILE_DEVICE_AUTOROTATE, Integer.toString(this._deviceAutoRotate));
        editor.putString(PREF_PROFILE_DEVICE_LOCATION_SERVICE_PREFS, Integer.toString(this._deviceLocationServicePrefs));
        editor.putString(PREF_PROFILE_VOLUME_SPEAKER_PHONE, Integer.toString(this._volumeSpeakerPhone));
        editor.putString(PREF_PROFILE_DEVICE_NFC, Integer.toString(this._deviceNFC));
        editor.putString(PREF_PROFILE_DEVICE_KEYGUARD, Integer.toString(this._deviceKeyguard));
        editor.putString(PREF_PROFILE_VIBRATION_ON_TOUCH, Integer.toString(this._vibrationOnTouch));
        editor.putString(PREF_PROFILE_DEVICE_WIFI_AP, Integer.toString(this._deviceWiFiAP));
        editor.putString(PREF_PROFILE_DEVICE_POWER_SAVE_MODE, Integer.toString(this._devicePowerSaveMode));
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE, Integer.toString(this._deviceNetworkType));
        editor.putString(PREF_PROFILE_NOTIFICATION_LED, Integer.toString(this._notificationLed));
        editor.putString(PREF_PROFILE_VIBRATE_WHEN_RINGING, Integer.toString(this._vibrateWhenRinging));
        editor.putString(PREF_PROFILE_DEVICE_WALLPAPER_FOR, Integer.toString(this._deviceWallpaperFor));
        editor.putString(PREF_PROFILE_LOCK_DEVICE, Integer.toString(this._lockDevice));
        editor.putString(PREF_PROFILE_DEVICE_CONNECT_TO_SSID, this._deviceConnectToSSID);
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING, Integer.toString(this._applicationDisableWifiScanning));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING, Integer.toString(this._applicationDisableBluetoothScanning));
        editor.putString(PREF_PROFILE_DEVICE_WIFI_AP_PREFS, Integer.toString(this._deviceWiFiAPPrefs));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_LOCATION_SCANNING, Integer.toString(this._applicationDisableLocationScanning));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING, Integer.toString(this._applicationDisableMobileCellScanning));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING, Integer.toString(this._applicationDisableOrientationScanning));
        editor.putString(PREF_PROFILE_HEADS_UP_NOTIFICATIONS, Integer.toString(this._headsUpNotifications));
        editor.putString(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_CHANGE, Integer.toString(this._deviceForceStopApplicationChange));
        editor.putString(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_PACKAGE_NAME, this._deviceForceStopApplicationPackageName);
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS, Integer.toString(this._deviceNetworkTypePrefs));
        editor.putString(PREF_PROFILE_DEVICE_CLOSE_ALL_APPLICATIONS, Integer.toString(this._deviceCloseAllApplications));
        editor.putString(PREF_PROFILE_SCREEN_DARK_MODE, Integer.toString(this._screenDarkMode));
        editor.putString(PREF_PROFILE_DTMF_TONE_WHEN_DIALING, Integer.toString(this._dtmfToneWhenDialing));
        editor.putString(PREF_PROFILE_SOUND_ON_TOUCH, Integer.toString(this._soundOnTouch));
        editor.putString(PREF_PROFILE_VOLUME_DTMF, this._volumeDTMF);
        editor.putString(PREF_PROFILE_VOLUME_ACCESSIBILITY, this._volumeAccessibility);
        editor.putString(PREF_PROFILE_VOLUME_BLUETOOTH_SCO, this._volumeBluetoothSCO);
        editor.putString(PREF_PROFILE_AFTER_DURATION_PROFILE, Long.toString(this._afterDurationProfile));
        editor.putString(PREF_PROFILE_ALWAYS_ON_DISPLAY, Integer.toString(this._alwaysOnDisplay));
        editor.putString(PREF_PROFILE_SCREEN_ON_PERMANENT, Integer.toString(this._screenOnPermanent));
        editor.putBoolean(PREF_PROFILE_VOLUME_MUTE_SOUND, this._volumeMuteSound);
        editor.putString(PREF_PROFILE_DEVICE_LOCATION_MODE, Integer.toString(this._deviceLocationMode));
        editor.putString(PREF_PROFILE_APPLICATION_DISABLE_NOTIFICATION_SCANNING, Integer.toString(this._applicationDisableNotificationScanning));
        editor.putString(PREF_PROFILE_GENERATE_NOTIFICATION, this._generateNotification);
        editor.putString(PREF_PROFILE_CAMERA_FLASH, Integer.toString(this._cameraFlash));
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1, Integer.toString(this._deviceNetworkTypeSIM1));
        editor.putString(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2, Integer.toString(this._deviceNetworkTypeSIM2));
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1, Integer.toString(this._deviceMobileDataSIM1));
        editor.putString(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2, Integer.toString(this._deviceMobileDataSIM2));
        editor.putString(PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS, this._deviceDefaultSIMCards);
        editor.putString(PREF_PROFILE_DEVICE_ONOFF_SIM1, Integer.toString(this._deviceOnOffSIM1));
        editor.putString(PREF_PROFILE_DEVICE_ONOFF_SIM2, Integer.toString(this._deviceOnOffSIM2));

        editor.putString(PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM1, Integer.toString(this._soundRingtoneChangeSIM1));
        splits = this._soundRingtoneSIM1.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_RINGTONE_SIM1, splits[0]);
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM1, Integer.toString(this._soundNotificationChangeSIM1));
        splits = this._soundNotificationSIM1.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION_SIM1, splits[0]);

        editor.putString(PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM2, Integer.toString(this._soundRingtoneChangeSIM2));
        splits = this._soundRingtoneSIM2.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_RINGTONE_SIM2, splits[0]);
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM2, Integer.toString(this._soundNotificationChangeSIM2));
        splits = this._soundNotificationSIM2.split("\\|");
        editor.putString(PREF_PROFILE_SOUND_NOTIFICATION_SIM2, splits[0]);

        editor.putString(PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS, Integer.toString(this._soundSameRingtoneForBothSIMCards));

        editor.apply();
    }

    static PreferenceAllowed isProfilePreferenceAllowed(String preferenceKey, Profile profile,
                                                           SharedPreferences sharedPreferences,
                                                           boolean fromUIThread, Context context) {
//        if (profile != null)
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "profile != null - START");

        if ((profile != null) && (!preferenceKey.equals("-")) && (sharedPreferences == null)) {
            sharedPreferences = context.getApplicationContext().getSharedPreferences("temp_isProfilePreferenceAllowed", Context.MODE_PRIVATE);
            profile.saveProfileToSharedPreferences(sharedPreferences);
            profile = null;
        }

        PreferenceAllowed preferenceAllowed = new PreferenceAllowed();

        preferenceAllowed.notAllowedRoot = false;
        preferenceAllowed.notAllowedG1 = false;

        //noinspection IfStatementWithIdenticalBranches
        if (profile == null) {
            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
            switch (preferenceKey) {
                case PREF_PROFILE_DEVICE_AIRPLANE_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE(preferenceAllowed, null, sharedPreferences, fromUIThread);
                    break;
                case PREF_PROFILE_DEVICE_WIFI:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI(preferenceAllowed);
                    break;
                case PREF_PROFILE_DEVICE_BLUETOOTH:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_BLUETOOTH(preferenceAllowed);
                    break;
                case PREF_PROFILE_DEVICE_MOBILE_DATA:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1:
                case PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_DUAL_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS(preferenceAllowed);
                    break;
                case PREF_PROFILE_DEVICE_GPS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_LOCATION_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE(preferenceAllowed, null, context);
                    break;
                case PREF_PROFILE_DEVICE_NFC:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_WIFI_AP:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_VIBRATE_WHEN_RINGING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING(preferenceAllowed, null, sharedPreferences, fromUIThread);
                    break;
                case PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS:
                    // !!! test this only for preference key !!!
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS(preferenceAllowed, sharedPreferences, fromUIThread);
                    break;
                case PREF_PROFILE_DEVICE_POWER_SAVE_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_NETWORK_TYPE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1:
                case PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_DUAL_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_NOTIFICATION_LED:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_KEYGUARD:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_KEYGUARD(preferenceAllowed, context);
                    break;
                case PREF_PROFILE_DEVICE_CONNECT_TO_SSID:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_CONNECT_TO_SSID(preferenceAllowed);
                    break;
                case PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING(preferenceAllowed);
                    break;
                case PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING(preferenceAllowed);
                    break;
                case PREF_PROFILE_DEVICE_WIFI_AP_PREFS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP_PREFS(preferenceAllowed);
                    break;
                case PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING(preferenceAllowed);
                    break;
                case PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING(preferenceAllowed);
                    break;
                case PREF_PROFILE_HEADS_UP_NOTIFICATIONS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS(preferenceAllowed);
                    break;
                case PREF_PROFILE_VOLUME_ACCESSIBILITY:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_ACCESSIBILITY(preferenceAllowed, context);
                    break;
                case PREF_PROFILE_ALWAYS_ON_DISPLAY:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_SCREEN_DARK_MODE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_VOLUME_SPEAKER_PHONE:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_SPEAKER_PHONE(preferenceAllowed, context);
                    break;
                case PREF_PROFILE_CAMERA_FLASH:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_CAMERA_FLASH(preferenceAllowed);
                    break;
                case PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_DEVICE_ONOFF_SIM1:
                case PREF_PROFILE_DEVICE_ONOFF_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM1:
                case PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM(preferenceAllowed, null, context);
                    break;
                case PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM1:
                case PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM2:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM(preferenceAllowed, preferenceKey, null, sharedPreferences, fromUIThread, context);
                    break;
                case PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS:
                    PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS(preferenceAllowed, null, sharedPreferences, fromUIThread, context);
                    break;
                default:
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            return preferenceAllowed;

        } else {
            // !!! call only methods with profile parameter

            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;

            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE(preferenceAllowed, profile, sharedPreferences, fromUIThread);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_AIRPLANE_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_BLUETOOTH(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_DUAL_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_GPS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE(preferenceAllowed, profile, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_LOCATION_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NFC] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING(preferenceAllowed, profile, sharedPreferences, fromUIThread);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_VIBRATE_WHEN_RINGING] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_POWER_SAVE_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_DUAL_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_NOTIFICATION_LED] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_KEYGUARD(preferenceAllowed, context);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_CONNECT_TO_SSID(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_WIFI_AP_PREFS(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_HEADS_UP_NOTIFICATIONS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS(preferenceAllowed);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_ACCESSIBILITY(preferenceAllowed, context);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_ALWAYS_ON_DISPLAY] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SCREEN_DARK_MODE] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_VOLUME_SPEAKER_PHONE(preferenceAllowed, context);
            //PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_CAMERA_FLASH(preferenceAllowed);
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_DEVICE_ONOFF_SIM] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM(preferenceAllowed, profile, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM(preferenceAllowed, "-", profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
            PreferenceAllowed.isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS(preferenceAllowed, profile, sharedPreferences, fromUIThread, context);
//            if (profile._name.equals("Laut"))
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [isProfilePreferenceAllowed_PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));

            if (preferenceAllowed.notAllowedG1 || preferenceAllowed.notAllowedRoot)
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;

            return preferenceAllowed;
        }
    }
/*
    static PreferenceAllowed isProfilePreferenceAllowedOld(String preferenceKey, Profile profile,
                                                        SharedPreferences sharedPreferences,
                                                        boolean fromUIThread, Context context)
    {
//        if (profile != null)
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "profile != null - START");

        Context appContext = context.getApplicationContext();

        if ((profile != null) && (!preferenceKey.equals("-")) && (sharedPreferences == null)) {
            sharedPreferences = context.getApplicationContext().getSharedPreferences("temp_isProfilePreferenceAllowed", Context.MODE_PRIVATE);
            profile.saveProfileToSharedPreferences(sharedPreferences);
            profile = null;
        }

        PreferenceAllowed preferenceAllowed = new PreferenceAllowed();

        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
        preferenceAllowed.notAllowedRoot = false;
        preferenceAllowed.notAllowedG1 = false;

        boolean applicationNeverAskForGrantRoot = ApplicationPreferences.applicationNeverAskForGrantRoot;

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_DEVICE_AIRPLANE_MODE)) {
            if (PPApplication.isRooted(fromUIThread)) {
                // device is rooted

                if (profile != null) {
                    // test if grant root is disabled
                    if (profile._deviceAirplaneMode != 0) {
                        if (applicationNeverAskForGrantRoot) {
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                        }
                    }
                } else if (sharedPreferences != null) {
                    if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                        if (applicationNeverAskForGrantRoot) {
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            // not needed to test all parameters
                            return preferenceAllowed;
                        }
                    }
                }

                if (PPApplication.settingsBinaryExists(fromUIThread)) {
                    if (profile != null) {
                        if (profile._deviceAirplaneMode != 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
            } else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                if ((profile != null) && (profile._deviceAirplaneMode != 0)) {
                    preferenceAllowed.notAllowedRoot = true;
                    //Log.e("isProfilePreferenceAllowed", "_deviceAirplaneMode");
                }
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_WIFI))
        {
            if (PPApplication.HAS_FEATURE_WIFI)
                // device has Wifi
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_BLUETOOTH))
        {
            if (PPApplication.HAS_FEATURE_BLUETOOTH)
                // device has bluetooth
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_MOBILE_DATA) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM1) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_MOBILE_DATA_SIM2))
        {
            boolean mobileDataSupported = false;
            if (!PPApplication.HAS_FEATURE_TELEPHONY) {
                // check mobile data capability for devices without phone call hardware (for example tablets)

                ConnectivityManager connManager = null;
                try {
                    connManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                } catch (Exception e) {
                    // java.lang.NullPointerException: missing IConnectivityManager
                    // Dual SIM?? Bug in Android ???
                    PPApplication.recordException(e);
                }

                if (connManager != null) {
                    //if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Network[] networks = connManager.getAllNetworks();
                    if ((networks != null) && (networks.length > 0)) {
                        for (Network network : networks) {
                            try {
//                                if (Build.VERSION.SDK_INT < 28) {
//                                    NetworkInfo ntkInfo = connManager.getNetworkInfo(network);
//                                    if (ntkInfo != null) {
//                                        if (ntkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                                            mobileDataSupported = true;
//                                            PPApplication.logE("[DUAL_SIM] isProfilePreferenceAllowed", "mobileDataSupported=true");
//                                            break;
//                                        }
//                                    }
//                                }
//                                else {
                                    NetworkCapabilities networkCapabilities = connManager.getNetworkCapabilities(network);
                                    if ((networkCapabilities != null) && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                        mobileDataSupported = true;
                                        break;
                                    }
                                //}
                            } catch (Exception ee) {
                                PPApplication.recordException(ee);
                            }
                        }
                    }
//                    } else {
//                        NetworkInfo ni = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                        mobileDataSupported = ni != null;
//                    }
                }
                //else
                //    mobileDataSupported = false;
            }
            else
                mobileDataSupported = true;
            if (mobileDataSupported)
            {
                //Log.d("isProfilePreferenceAllowed", "mobile data supported");
                // adb shell pm grant sk.henrichg.phoneprofilesplus android.permission.MODIFY_PHONE_STATE
                // not working :-/
                if (Permissions.hasPermission(appContext, Manifest.permission.MODIFY_PHONE_STATE)) {
                    if (ActivateProfileHelper.canSetMobileData(appContext))
                        if (profile != null) {
                            if ((profile._deviceMobileData != 0) ||
                                    (profile._deviceMobileDataSIM1 != 0) ||
                                    (profile._deviceMobileDataSIM2 != 0))
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                if (PPApplication.isRooted(fromUIThread)) {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if (Build.VERSION.SDK_INT < 26) {
                            if ((profile._deviceMobileData != 0)) {
                                if (applicationNeverAskForGrantRoot) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                }
                            }
                        }
                        else {
                            if ((profile._deviceMobileData != 0) ||
                                    (profile._deviceMobileDataSIM1 != 0) ||
                                    (profile._deviceMobileDataSIM2 != 0)) {
                                if (applicationNeverAskForGrantRoot) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                }
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (ActivateProfileHelper.telephonyServiceExists(PREF_PROFILE_DEVICE_MOBILE_DATA)) {
                        if (PPApplication.serviceBinaryExists(fromUIThread)) {
                            if (profile != null) {
                                if ((profile._deviceMobileData != 0) ||
                                        (profile._deviceMobileDataSIM1 != 0) ||
                                        (profile._deviceMobileDataSIM2 != 0))
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                            }
                            else
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SERVICE_NOT_FOUND;
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }

                    final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        if (Build.VERSION.SDK_INT >= 26) {
                            int phoneCount = telephonyManager.getPhoneCount();
                            if (phoneCount > 1) {
                                if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                }
                                if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                }
                            }
                        }
                        if (!PhoneProfilesService.hasSIMCard(appContext, 0, true)) {
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                    if (Build.VERSION.SDK_INT < 26) {
                        if ((profile != null) &&
                                (profile._deviceMobileData != 0)) {
                            preferenceAllowed.notAllowedRoot = true;
                            //Log.e("isProfilePreferenceAllowed", "_deviceMobileData");
                        }
                    }
                    else {
                        if ((profile != null) &&
                                ((profile._deviceMobileData != 0) ||
                                        (profile._deviceMobileDataSIM1 != 0) ||
                                        (profile._deviceMobileDataSIM2 != 0))) {
                            preferenceAllowed.notAllowedRoot = true;
                            //Log.e("isProfilePreferenceAllowed", "_deviceMobileData");
                        }
                    }
                }
            }
            else {
                //Log.d("isProfilePreferenceAllowed", "mobile data not supported");
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_MOBILE_DATA_PREFS))
        {
            if (PPApplication.HAS_FEATURE_TELEPHONY)
            {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_DEVICE_GPS))
        {
            if (PPApplication.HAS_FEATURE_LOCATION_GPS)
            {
                // device has gps
                // adb shell pm grant sk.henrichg.phoneprofilesplus android.permission.WRITE_SECURE_SETTINGS
                if (Permissions.hasPermission(appContext, Manifest.permission.WRITE_SECURE_SETTINGS)) {
                    if (profile != null) {
                        if (profile._deviceGPS!= 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                if (PPApplication.isRooted(fromUIThread))
                {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._deviceGPS != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (PPApplication.settingsBinaryExists(fromUIThread)) {
                        if (profile != null) {
                            if (profile._deviceGPS!= 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
                }
//                else
//                if (ActivateProfileHelper.canExploitGPS(appContext))
//                {
//                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
//                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_GRANTED_G1_PERMISSION;
//                    if (profile != null)
//                        PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "- profile._deviceGPS=" + profile._deviceGPS);
                    if ((profile != null) && (profile._deviceGPS != 0)) {
                        //return preferenceAllowed;
//                        if (profile._name.equals("Low battery"))
//                            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "_deviceGPS");
                        //preferenceAllowed.notAllowedRoot = true;
                        preferenceAllowed.notAllowedG1 = true;
                    }
                }
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

//            if ((profile != null) && (profile._name.equals("Low battery"))) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [IN PREF_PROFILE_DEVICE_GPS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_DEVICE_LOCATION_MODE))
        {
            // adb shell pm grant sk.henrichg.phoneprofilesplus android.permission.WRITE_SECURE_SETTINGS
            if (Permissions.hasPermission(appContext, Manifest.permission.WRITE_SECURE_SETTINGS)) {
                if (profile != null) {
                    if (profile._deviceLocationMode != 0)
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
//            else
//            if (PPApplication.isRooted(fromUIThread))
//            {
//                // device is rooted - NOT WORKING
//
//                if (profile != null) {
//                    // test if grant root is disabled
//                    if (profile._deviceLocationMode != 0) {
//                        if (applicationNeverAskForGrantRoot) {
//                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
//                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
//                            // not needed to test all parameters
//                            //return preferenceAllowed;
//                        }
//                    }
//                }
//                else
//                if (sharedPreferences != null) {
//                    if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
//                        if (applicationNeverAskForGrantRoot) {
//                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
//                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
//                            // not needed to test all parameters
//                            return preferenceAllowed;
//                        }
//                    }
//                }
//
//                if (PPApplication.settingsBinaryExists(fromUIThread))
//                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
//                else
//                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
//            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_GRANTED_G1_PERMISSION;
//                if (profile != null)
//                    PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "- profile._deviceLocationMode=" + profile._deviceLocationMode);
                if ((profile != null) && (profile._deviceLocationMode != 0)) {
                    //return preferenceAllowed;
//                    if (profile._name.equals("Low battery"))
//                        PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "_deviceLocationMode");
                    //preferenceAllowed.notAllowedRoot = true;
                    preferenceAllowed.notAllowedG1 = true;
                }
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_DEVICE_NFC))
        {
            if (PPApplication.HAS_FEATURE_NFC)
            {
                //PPApplication.logE("PPApplication.hardwareCheck","NFC=presented");

                // device has nfc
                if (Permissions.hasPermission(appContext, Manifest.permission.WRITE_SECURE_SETTINGS)) {
                    if (profile != null) {
                        if (profile._deviceNFC != 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                if (PPApplication.isRooted(fromUIThread)) {

                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._deviceNFC != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (profile != null) {
                        if (profile._deviceNFC != 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_GRANTED_G1_PERMISSION;
//                    if (profile != null)
//                        PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "- profile._deviceNFC=" + profile._deviceNFC);
                    if ((profile != null) && (profile._deviceNFC != 0)) {
                        //return preferenceAllowed;
//                        if (profile._name.equals("Low battery"))
//                            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "_deviceNFC");
                        //preferenceAllowed.notAllowedRoot = true;
                        preferenceAllowed.notAllowedG1 = true;
                    }
                }
            }
            else
            {
                //PPApplication.logE("PPApplication.hardwareCheck","NFC=not presented");
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;
            }

//            if ((profile != null) && (profile._name.equals("Low battery"))) {
//                PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [IN PREF_PROFILE_DEVICE_NFC] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//            }

            if (profile == null)
                return preferenceAllowed;
        }

//        if ((profile != null) && (profile._name.equals("Low battery"))) {
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [PREF_PROFILE_DEVICE_WIFI_AP] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_DEVICE_WIFI_AP))
        {
            if (Build.VERSION.SDK_INT < 30) {
                if (PPApplication.HAS_FEATURE_WIFI) {
                    // device has Wifi
                    if (android.os.Build.VERSION.SDK_INT < 26) {
                        if (WifiApManager.canExploitWifiAP(appContext)) {
                            if (profile != null) {
                                if (profile._deviceWiFiAP != 0)
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                            }
                            else
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else {
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                            preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                        }
                    } else if (Build.VERSION.SDK_INT < 28) {
                        if (WifiApManager.canExploitWifiTethering(appContext)) {
                            if (profile != null) {
                                if (profile._deviceWiFiAP != 0)
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                            }
                            else
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else if (PPApplication.isRooted(fromUIThread)) {
                            // device is rooted

                            if (profile != null) {
                                // test if grant root is disabled
                                if (profile._deviceWiFiAP != 0) {
                                    if (applicationNeverAskForGrantRoot) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                    }
                                }
                            } else if (sharedPreferences != null) {
                                if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                                    if (applicationNeverAskForGrantRoot) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        // not needed to test all parameters
                                        return preferenceAllowed;
                                    }
                                }
                            }

                            if (ActivateProfileHelper.wifiServiceExists(PREF_PROFILE_DEVICE_WIFI_AP)) {
                                if (PPApplication.serviceBinaryExists(fromUIThread)) {
                                    if (profile != null) {
                                        if (profile._deviceWiFiAP != 0)
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                    }
                                    else
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                }
                                else
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SERVICE_NOT_FOUND;
                            } else {
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                            }
                        } else {
                            if ((profile != null) && (profile._deviceWiFiAP != 0)) {
                                preferenceAllowed.notAllowedRoot = true;
                                //Log.e("isProfilePreferenceAllowed", "_deviceWiFiAP");
                            }
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                        }
                    } else {
                        if (profile != null) {
                            if (profile._deviceWiFiAP != 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                } else
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;
            }
            else {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported_android_version);
            }

//            if (PPApplication.logEnabled()) {
//                PPApplication.logE("$$$ WifiAP", "isProfilePreferenceAllowed-preferenceAllowed.allowed=" + preferenceAllowed.allowed);
//                PPApplication.logE("$$$ WifiAP", "isProfilePreferenceAllowed-preferenceAllowed.notAllowedReason=" + preferenceAllowed.notAllowedReason);
//            }

            if (profile == null)
                return preferenceAllowed;
        }

//        if ((profile != null) && (profile._name.equals("Low battery"))) {
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [PREF_PROFILE_VIBRATE_WHEN_RINGING] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_VIBRATE_WHEN_RINGING))
        {
            if (android.os.Build.VERSION.SDK_INT == 23 ||
                    (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI)) {
                if (PPApplication.isRooted(fromUIThread)) {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._vibrateWhenRinging != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (PPApplication.settingsBinaryExists(fromUIThread)) {
                        if (profile != null) {
                            if (profile._vibrateWhenRinging != 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
                }
                else {
                    if ((profile != null) && (profile._vibrateWhenRinging != 0)) {
                        preferenceAllowed.notAllowedRoot = true;
                        //Log.e("isProfilePreferenceAllowed", "_vibrateWhenRinging");
                    }
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                }
            }
            else {
                if (profile != null) {
                    if (profile._vibrateWhenRinging != 0)
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }

            if (profile == null)
                return preferenceAllowed;
        }

//        if ((profile != null) && (profile._name.equals("Low battery"))) {
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- [PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS] preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//        }

        // !!! test this only for preference key !!!
        if ((profile == null) && preferenceKey.equals(PREF_PROFILE_DEVICE_ADAPTIVE_BRIGHTNESS))
        {
            if (PPApplication.isRooted(fromUIThread)) {
                // device is rooted

                if (sharedPreferences != null) {
                    String value = sharedPreferences.getString(PREF_PROFILE_DEVICE_BRIGHTNESS, defaultValuesString.get(PREF_PROFILE_DEVICE_BRIGHTNESS));
                    if (getDeviceBrightnessChange(value) && getDeviceBrightnessAutomatic(value)) {
                        if (applicationNeverAskForGrantRoot) {
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            // not needed to test all parameters
                            return preferenceAllowed;
                        }
                    }
                }

                if (PPApplication.settingsBinaryExists(fromUIThread)) {
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
            } else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
            }

            return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_DEVICE_POWER_SAVE_MODE))
        {
            if (Permissions.hasPermission(appContext, Manifest.permission.WRITE_SECURE_SETTINGS)) {
                if (profile != null) {
                    if (profile._devicePowerSaveMode != 0)
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else
            if (PPApplication.isRooted(fromUIThread)) {
                // device is rooted

                if (profile != null) {
                    // test if grant root is disabled
                    if (profile._devicePowerSaveMode != 0) {
                        if (applicationNeverAskForGrantRoot) {
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                        }
                    }
                }
                else
                if (sharedPreferences != null) {
                    if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                        if (applicationNeverAskForGrantRoot) {
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            // not needed to test all parameters
                            return preferenceAllowed;
                        }
                    }
                }

                if (PPApplication.settingsBinaryExists(fromUIThread)) {
                    if (profile != null) {
                        if (profile._devicePowerSaveMode != 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_GRANTED_G1_PERMISSION;
//                    if (profile != null)
//                        PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "- profile._devicePowerSaveMode=" + profile._devicePowerSaveMode);
                if ((profile != null) && (profile._devicePowerSaveMode != 0)) {
//                        if (profile._name.equals("Low battery"))
//                            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "_devicePowerSaveMode");
                    //preferenceAllowed.notAllowedRoot = true;
                    preferenceAllowed.notAllowedG1 = true;
                }
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_NETWORK_TYPE) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM1) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_NETWORK_TYPE_SIM2))
        {
            if (PPApplication.HAS_FEATURE_TELEPHONY)
            {
                final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    int phoneCount = 1;
                    if (Build.VERSION.SDK_INT >= 26)
                        phoneCount = telephonyManager.getPhoneCount();
//                    PPApplication.logE("[DUAL_SIM] isProfilePreferenceAllowed", "phoneCount="+phoneCount);

                    final int phoneType = telephonyManager.getPhoneType();
                    if ((phoneType == TelephonyManager.PHONE_TYPE_GSM) || (phoneType == TelephonyManager.PHONE_TYPE_CDMA)) {
                        if (PPApplication.isRooted(fromUIThread)) {
                            // device is rooted

                            if (profile != null) {
                                // test if grant root is disabled
                                if (Build.VERSION.SDK_INT < 26) {
                                    if ((profile._deviceNetworkType != 0)) {
                                        if (applicationNeverAskForGrantRoot) {
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        }
                                    }
                                }
                                else {
                                    if ((profile._deviceNetworkType != 0) ||
                                            (profile._deviceNetworkTypeSIM1 != 0) ||
                                            (profile._deviceNetworkTypeSIM2 != 0)
                                    ) {
                                        if (applicationNeverAskForGrantRoot) {
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        }
                                    }
                                }
                            }
                            else
                            if (sharedPreferences != null) {
                                if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                                    if (applicationNeverAskForGrantRoot) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        // not needed to test all parameters
                                        return preferenceAllowed;
                                    }
                                }
                            }

                            if (ActivateProfileHelper.telephonyServiceExists(PREF_PROFILE_DEVICE_NETWORK_TYPE)) {
                                if (PPApplication.serviceBinaryExists(fromUIThread)) {
                                    if (profile == null)
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                    else {
                                        if (Build.VERSION.SDK_INT < 26) {
                                            if (profile._deviceNetworkType != 0)
                                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                        } else {
                                            if ((profile._deviceNetworkType != 0) ||
                                                    (profile._deviceNetworkTypeSIM1 != 0) ||
                                                    (profile._deviceNetworkTypeSIM2 != 0))
                                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                        }
                                    }
                                }
                                else
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SERVICE_NOT_FOUND;
                            } else {
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_network_type);
                            }

                            if (Build.VERSION.SDK_INT >= 26) {
                                if (phoneCount > 1) {
                                    if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                    }
                                    if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                    }
                                }
                            }
                            if (!PhoneProfilesService.hasSIMCard(appContext, 0, true)) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }
                        } else {
                            if (Build.VERSION.SDK_INT < 26) {
                                if ((profile != null) &&
                                        (profile._deviceNetworkType != 0)
                                ) {
                                    preferenceAllowed.notAllowedRoot = true;
                                    //Log.e("isProfilePreferenceAllowed", "_deviceNetworkType");
                                }
                            }
                            else {
                                if ((profile != null) &&
                                        ((profile._deviceNetworkType != 0) ||
                                                (profile._deviceNetworkTypeSIM1 != 0) ||
                                                (profile._deviceNetworkTypeSIM2 != 0))
                                ) {
                                    preferenceAllowed.notAllowedRoot = true;
                                    //Log.e("isProfilePreferenceAllowed", "_deviceNetworkType");
                                }
                            }
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_network_type);
                    }
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                    preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_network_type);
                }
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_NOTIFICATION_LED))
        {
            int value = Settings.System.getInt(appContext.getContentResolver(), "notification_light_pulse", -10);
            if (value != -10) {
//                 not working (private secure settings) :-/
//                if (Permissions.hasPermission(context, Manifest.permission.WRITE_SECURE_SETTINGS)) {
//                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
//                }
//                else
                if (PPApplication.isRooted(fromUIThread)) {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._notificationLed != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (PPApplication.settingsBinaryExists(fromUIThread)) {
                        if (profile != null) {
                            if (profile._notificationLed != 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
                }
                else {
                    if ((profile != null) && (profile._notificationLed != 0)) {
                        preferenceAllowed.notAllowedRoot = true;
                        //Log.e("isProfilePreferenceAllowed", "_notificationLed");
                    }
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                }
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_old_android);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_KEYGUARD))
        {
            boolean secureKeyguard;
            KeyguardManager keyguardManager = (KeyguardManager) appContext.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                secureKeyguard = keyguardManager.isKeyguardSecure();
                if (secureKeyguard) {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_APPLICATION;
                    preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_secure_lock);
                } else
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_CONNECT_TO_SSID))
        {
            if (PPApplication.HAS_FEATURE_WIFI)
                // device has Wifi
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_APPLICATION_DISABLE_WIFI_SCANNING))
        {
            if (PPApplication.HAS_FEATURE_WIFI)
                // device has Wifi
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_APPLICATION_DISABLE_BLUETOOTH_SCANNING))
        {
            if (PPApplication.HAS_FEATURE_BLUETOOTH)
                // device has bluetooth
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_WIFI_AP_PREFS))
        {
            if (PPApplication.HAS_FEATURE_WIFI)
            {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_APPLICATION_DISABLE_MOBILE_CELL_SCANNING))
        {
            if (PPApplication.HAS_FEATURE_TELEPHONY)
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_APPLICATION_DISABLE_ORIENTATION_SCANNING))
        {
            if (PPApplication.sensorManager != null) {
                boolean hasAccelerometer = PPApplication.accelerometerSensor != null;
                //boolean hasMagneticField = PPApplication.magneticFieldSensor != null;
                boolean hasProximity = PPApplication.proximitySensor != null;
                boolean hasLight = PPApplication.lightSensor != null;

                if (hasAccelerometer || hasProximity || hasLight)
                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                else
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_HEADS_UP_NOTIFICATIONS))
        {
            int value = Settings.Global.getInt(appContext.getContentResolver(), "heads_up_notifications_enabled", -10);
            if (value != -10) {
                if (Permissions.hasPermission(appContext, Manifest.permission.WRITE_SECURE_SETTINGS)) {
                    if (profile != null) {
                        if (profile._headsUpNotifications != 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                if (PPApplication.isRooted(fromUIThread)) {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._headsUpNotifications != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (PPApplication.settingsBinaryExists(fromUIThread)) {
                        if (profile != null) {
                            if (profile._headsUpNotifications != 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_GRANTED_G1_PERMISSION;
//                    if (profile != null)
//                        PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "- profile._headsUpNotifications=" + profile._headsUpNotifications);
                    if ((profile != null) && (profile._headsUpNotifications != 0)) {
                        //return preferenceAllowed;
//                        if (profile._name.equals("Low battery"))
//                            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "_headsUpNotifications");
                        //preferenceAllowed.notAllowedRoot = true;
                        preferenceAllowed.notAllowedG1 = true;
                    }
                }
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
            }

            if (profile == null)
                return preferenceAllowed;
        }

//
//        if (preferenceKey.equals(PREF_PROFILE_DEVICE_RUN_APPLICATION_CHANGE))
//        {
//            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
//
//            if (profile == null)
//                return preferenceAllowed;
//        }
//
//
//        if (preferenceKey.equals(PREF_PROFILE_DEVICE_FORCE_STOP_APPLICATION_CHANGE))
//        {
//            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
//
//            if (profile == null)
//                return preferenceAllowed;
//        }
//
//
//        if (preferenceKey.equals(PREF_PROFILE_LOCK_DEVICE))
//        {
//            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
//
//            if (profile == null)
//                return preferenceAllowed;
//        }
//

        if (preferenceKey.equals(PREF_PROFILE_DEVICE_NETWORK_TYPE_PREFS))
        {
            if (PPApplication.HAS_FEATURE_TELEPHONY)
            {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_VOLUME_ACCESSIBILITY))
        {
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_old_android);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_ALWAYS_ON_DISPLAY))
        {
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                if (PPApplication.isRooted(fromUIThread)) {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._alwaysOnDisplay != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (PPApplication.settingsBinaryExists(fromUIThread)) {
                        if (profile != null) {
                            if (profile._alwaysOnDisplay != 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
                }
                else {
                    if ((profile != null) && (profile._alwaysOnDisplay != 0)) {
                        preferenceAllowed.notAllowedRoot = true;
                        //Log.e("isProfilePreferenceAllowed", "_alwaysOnDisplay");
                    }
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                }
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_old_android);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) || preferenceKey.equals(PREF_PROFILE_SCREEN_DARK_MODE))
        {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                if (Permissions.hasPermission(appContext, Manifest.permission.WRITE_SECURE_SETTINGS)) {
                    if (profile != null) {
                        if (profile._screenDarkMode != 0)
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                }
                else
                if (PPApplication.isRooted(fromUIThread))
                {
                    // device is rooted
                    if (profile != null) {
                        // test if grant root is disabled
                        if (profile._screenDarkMode != 0) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    }
                    else
                    if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (PPApplication.settingsBinaryExists(fromUIThread)) {
                        if (profile != null) {
                            if (profile._screenDarkMode != 0)
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                    }
                    else
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_GRANTED_G1_PERMISSION;
//                    if (profile != null)
//                        PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "- profile._screenDarkMode=" + profile._screenDarkMode);
                    if ((profile != null) && (profile._screenDarkMode != 0)) {
                        //return preferenceAllowed;
//                        if (profile._name.equals("Low battery"))
//                            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "_screenDarkMode");
                        preferenceAllowed.notAllowedG1 = true;
                    }
                }
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_old_android);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_VOLUME_SPEAKER_PHONE))
        {
            if (Build.VERSION.SDK_INT < 29) {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported_android_version);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (preferenceKey.equals(PREF_PROFILE_CAMERA_FLASH))
        {
            if (PPApplication.HAS_FEATURE_CAMERA_FLASH)
            {
                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
            }
            else
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS))
        {
            if (Build.VERSION.SDK_INT >= 26) {
                //if (PPApplication.HAS_FEATURE_TELEPHONY) {
                    final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        int phoneCount = telephonyManager.getPhoneCount();
//                        PPApplication.logE("[DUAL_SIM] isProfilePreferenceAllowed", "phoneCount="+phoneCount);

                        if (PPApplication.isRooted(fromUIThread)) {
                            // device is rooted
                            if (profile != null) {
                                // test if grant root is disabled
                                if (!profile._deviceDefaultSIMCards.equals("0|0|0")) {
                                    if (applicationNeverAskForGrantRoot) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                    }
                                }
                            } else if (sharedPreferences != null) {
                                if (!sharedPreferences.getString(preferenceKey, "0|0|0").equals("0|0|0")) {
                                    if (applicationNeverAskForGrantRoot) {
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        // not needed to test all parameters
                                        return preferenceAllowed;
                                    }
                                }
                            }

                            if (ActivateProfileHelper.telephonyServiceExists(PREF_PROFILE_DEVICE_DEFAULT_SIM_CARDS) && (phoneCount > 1)) {
                                if (PPApplication.serviceBinaryExists(fromUIThread)) {
                                    if (profile != null) {
                                        if (!profile._deviceDefaultSIMCards.equals("0|0|0"))
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                    }
                                    else
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                }
                                else
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SERVICE_NOT_FOUND;
                            } else {
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                            }

                            if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
//                                PPApplication.logE("[DUAL_SIM] isProfilePreferenceAllowed", "not has sim 1");
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }
                            if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
//                                PPApplication.logE("[DUAL_SIM] isProfilePreferenceAllowed", "not has sim 2");
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }

                        } else {
                            if ((profile != null) &&
                                    (!profile._deviceDefaultSIMCards.equals("0|0|0"))
                            ) {
                                preferenceAllowed.notAllowedRoot = true;
                                //Log.e("isProfilePreferenceAllowed", "_deviceNetworkType");
                            }
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }
                //} else
                //    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_HARDWARE;
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_ONOFF_SIM1) ||
                preferenceKey.equals(PREF_PROFILE_DEVICE_ONOFF_SIM2)) {
            if (Build.VERSION.SDK_INT >= 26) {
                if (PPApplication.isRooted(fromUIThread)) {
                    // device is rooted

                    if (profile != null) {
                        // test if grant root is disabled
                        if ((profile._deviceOnOffSIM1 != 0) ||
                                (profile._deviceOnOffSIM2 != 0)) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                            }
                        }
                    } else if (sharedPreferences != null) {
                        if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                            if (applicationNeverAskForGrantRoot) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                // not needed to test all parameters
                                return preferenceAllowed;
                            }
                        }
                    }

                    if (ActivateProfileHelper.telephonyServiceExists(PREF_PROFILE_DEVICE_ONOFF_SIM1)) {
                        if (PPApplication.serviceBinaryExists(fromUIThread)) {
                            if (profile != null) {
                                if ((profile._deviceOnOffSIM1 != 0) ||
                                        (profile._deviceOnOffSIM2 != 0))
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                            }
                            else
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                        }
                        else
                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SERVICE_NOT_FOUND;
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }

                    final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        int phoneCount = telephonyManager.getPhoneCount();
                        if (phoneCount > 1) {
                            if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }
                            if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }
                } else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                    if ((profile != null) &&
                            ((profile._deviceOnOffSIM1 != 0) ||
                             (profile._deviceOnOffSIM2 != 0))) {
                        preferenceAllowed.notAllowedRoot = true;
                        //Log.e("isProfilePreferenceAllowed", "_deviceOnOffSIM");
                    }
                }
            } else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM1) ||
                preferenceKey.equals(PREF_PROFILE_SOUND_RINGTONE_CHANGE_SIM2)) {
            if (Build.VERSION.SDK_INT >= 26) {
                if (PPApplication.deviceIsSamsung ||
                     (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI) ||
                     (PPApplication.deviceIsHuawei && PPApplication.romIsEMUI)) {
                    final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        int phoneCount = telephonyManager.getPhoneCount();
                        if (phoneCount > 1) {
                            if (profile != null) {
                                if ((profile._soundRingtoneChangeSIM1 != 0) ||
                                        (profile._soundRingtoneChangeSIM2 != 0))
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                            }
                            else
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;

                            if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }
                            if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
                                preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                            }
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                    preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported_by_ppp);
                }
            }
            else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported_by_ppp);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM1) ||
                preferenceKey.equals(PREF_PROFILE_SOUND_NOTIFICATION_CHANGE_SIM2)) {
            if (Build.VERSION.SDK_INT >= 26) {
                if ((PPApplication.deviceIsSamsung ||
                    (PPApplication.deviceIsHuawei && PPApplication.romIsEMUI))) {
                    final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        int phoneCount = telephonyManager.getPhoneCount();
                        if (phoneCount > 1) {

                            if (PPApplication.isRooted(fromUIThread)) {
                                // device is rooted

                                if (profile != null) {
                                    // test if grant root is disabled
                                    if ((profile._soundNotificationChangeSIM1 != 0) ||
                                        (profile._soundNotificationChangeSIM2 != 0)) {
                                        if (applicationNeverAskForGrantRoot) {
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        }
                                    }
                                } else if (sharedPreferences != null) {
                                    if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                                        if (applicationNeverAskForGrantRoot) {
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                            // not needed to test all parameters
                                            return preferenceAllowed;
                                        }
                                    }
                                }

                                if (PPApplication.settingsBinaryExists(fromUIThread)) {
                                    if (profile != null) {
                                        if ((profile._soundNotificationChangeSIM1 != 0) ||
                                                (profile._soundNotificationChangeSIM2 != 0))
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                    }
                                    else
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                }
                                else
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;

                                if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                }
                                if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                }

                            } else {
                                if ((profile != null) &&
                                        ((profile._soundNotificationChangeSIM1 != 0) ||
                                        (profile._soundNotificationChangeSIM2 != 0))
                                ) {
                                    preferenceAllowed.notAllowedRoot = true;
                                    //Log.e("isProfilePreferenceAllowed", "_deviceNetworkType");
                                }
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                            }
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }
                }
                else
                if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI) {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                    preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported);
                }
                else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                    preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported_by_ppp);
                }
            } else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if ((profile != null) ||
                preferenceKey.equals(PREF_PROFILE_SOUND_SAME_RINGTONE_FOR_BOTH_SIM_CARDS)) {
            if (Build.VERSION.SDK_INT >= 26) {
                if (PPApplication.deviceIsXiaomi && PPApplication.romIsMIUI) {
                    final TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        int phoneCount = telephonyManager.getPhoneCount();
                        if (phoneCount > 1) {

                            if (PPApplication.isRooted(fromUIThread)) {
                                // device is rooted

                                if (profile != null) {
                                    // test if grant root is disabled
                                    if ((profile._soundSameRingtoneForBothSIMCards != 0)) {
                                        if (applicationNeverAskForGrantRoot) {
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                        }
                                    }
                                } else if (sharedPreferences != null) {
                                    if (!sharedPreferences.getString(preferenceKey, "0").equals("0")) {
                                        if (applicationNeverAskForGrantRoot) {
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                            preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOT_GRANTED;
                                            // not needed to test all parameters
                                            return preferenceAllowed;
                                        }
                                    }
                                }

                                if (PPApplication.settingsBinaryExists(fromUIThread)) {
                                    if (profile != null) {
                                        if (profile._soundSameRingtoneForBothSIMCards != 0)
                                            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                    }
                                    else
                                        preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;
                                }
                                else
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_SETTINGS_NOT_FOUND;

                                if (!PhoneProfilesService.hasSIMCard(appContext, 1, true)) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                }
                                if (!PhoneProfilesService.hasSIMCard(appContext, 2, true)) {
                                    preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_NOT_ALLOWED;
                                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NO_SIM_CARD;
                                }

                            } else {
                                if ((profile != null) &&
                                        (profile._soundSameRingtoneForBothSIMCards != 0)
                                ) {
                                    preferenceAllowed.notAllowedRoot = true;
                                    //Log.e("isProfilePreferenceAllowed", "_deviceNetworkType");
                                }
                                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_ROOTED;
                            }
                        }
                    } else {
                        preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                        preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
                    }
                } else {
                    preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                    preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_not_supported);
                }
            } else {
                preferenceAllowed.notAllowedReason = PreferenceAllowed.PREFERENCE_NOT_ALLOWED_NOT_SUPPORTED_BY_SYSTEM;
                preferenceAllowed.notAllowedReasonDetail = appContext.getString(R.string.preference_not_allowed_reason_detail_cant_be_change);
            }

            if (profile == null)
                return preferenceAllowed;
        }

        if (profile == null)
            preferenceAllowed.allowed = PreferenceAllowed.PREFERENCE_ALLOWED;

//        if ((profile != null) && (profile._name.equals("Low battery"))) {
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "------- preferenceAllowed.allowed=" + ((preferenceAllowed.allowed == PreferenceAllowed.PREFERENCE_ALLOWED) ? "true" : "false"));
//        }

//        if (profile != null)
//            PPApplication.logE("[G1_TEST] isProfilePreferenceAllowed", "profile != null - END");

        return preferenceAllowed;
    }
*/
    public int isAccessibilityServiceEnabled(Context context) {
        int accessibilityEnabled = 1;
        int extenderVersion = PPPExtenderBroadcastReceiver.isExtenderInstalled(context);
        if (this._deviceForceStopApplicationChange != 0) {
            if (extenderVersion == 0)
                accessibilityEnabled = -2;
            else
            if ((Build.VERSION.SDK_INT < 28) && (extenderVersion < PPApplication.VERSION_CODE_EXTENDER_3_0))
                accessibilityEnabled = -1;
            else
            if ((Build.VERSION.SDK_INT >= 28) && (extenderVersion < PPApplication.VERSION_CODE_EXTENDER_5_1_3_1))
                accessibilityEnabled = -1;
            else
            if (PPPExtenderBroadcastReceiver.isAccessibilityServiceEnabled(context))
                accessibilityEnabled = 1;
            else
                accessibilityEnabled = 0;
        }
        if (this._lockDevice == 3) {
            if (extenderVersion == 0)
                accessibilityEnabled = -2;
            else
            if (extenderVersion < PPApplication.VERSION_CODE_EXTENDER_4_0)
                accessibilityEnabled = -1;
            else
            if (PPPExtenderBroadcastReceiver.isAccessibilityServiceEnabled(context))
                accessibilityEnabled = 1;
            else
                accessibilityEnabled = 0;
        }
        return accessibilityEnabled;
    }

    /*
    static void getActivatedProfileForDuration(Context context)
    {
        synchronized (PPApplication.profileActivationMutex) {
            ApplicationPreferences.prefActivatedProfileForDuration = ApplicationPreferences.
                    getSharedPreferences(context).getLong(PREF_ACTIVATED_PROFILE_FOR_DURATION, 0);
            //return prefActivatedProfileForDuration;
        }
    }
    static void setActivatedProfileForDuration(Context context, long profileId)
    {
        synchronized (PPApplication.profileActivationMutex) {
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putLong(PREF_ACTIVATED_PROFILE_FOR_DURATION, profileId);
            editor.apply();
            ApplicationPreferences.prefActivatedProfileForDuration = profileId;
        }
    }
    */

    static void getActivatedProfileEndDurationTime(Context context)
    {
        synchronized (PPApplication.profileActivationMutex) {
            ApplicationPreferences.prefActivatedProfileEndDurationTime = ApplicationPreferences.
                    getSharedPreferences(context).getLong(PREF_ACTIVATED_PROFILE_END_DURATION_TIME, 0);
            //return prefActivatedProfileEndDurationTime;
        }
    }

    static void setActivatedProfileEndDurationTime(Context context, long time)
    {
        synchronized (PPApplication.profileActivationMutex) {
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
            editor.putLong(PREF_ACTIVATED_PROFILE_END_DURATION_TIME, time);
            editor.apply();
            ApplicationPreferences.prefActivatedProfileEndDurationTime = time;
        }
    }

    static int getIconResource(String identifier) {
        int iconResource = R.drawable.ic_profile_default;
        try {
            if ((identifier != null) && (!identifier.isEmpty())) {
                Object idx = profileIconIdMap.get(identifier);
                if (idx != null)
                    iconResource = (int) idx;
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
        return iconResource;
    }


}
