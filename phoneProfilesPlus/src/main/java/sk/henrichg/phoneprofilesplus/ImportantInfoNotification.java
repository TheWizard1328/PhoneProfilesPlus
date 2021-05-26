package sk.henrichg.phoneprofilesplus;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

class ImportantInfoNotification {

    // this version code must by <= version code in dependencies.gradle
    static final int VERSION_CODE_FOR_NEWS = 4651;

    private static final String PREF_SHOW_INFO_NOTIFICATION_ON_START = "show_info_notification_on_start";
    private static final String PREF_SHOW_INFO_NOTIFICATION_ON_START_VERSION = "show_info_notification_on_start_version";

    static final String EXTRA_FIRST_INSTALLATION = "first_installation";

    static void showInfoNotification(Context context) {
        //PPApplication.logE("ImportantInfoNotification.showInfoNotification","xxx");
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(PPApplication.PACKAGE_NAME, 0);
            int show = 0;
            int packageVersionCode = PPApplication.getVersionCode(pInfo);
            int savedVersionCode = getShowInfoNotificationOnStartVersion(context);

            // do not show notification, version code is not saved
            // typically it is for new users
            if (savedVersionCode == 0) {
                setShowInfoNotificationOnStart(context, false, packageVersionCode);
                return;
            }

            if (packageVersionCode > savedVersionCode) {
                show = canShowNotification(packageVersionCode, savedVersionCode, context);
                //PPApplication.logE("ImportantInfoNotification.showInfoNotification", "show="+show);
            }
            else {
                int extenderVersion = PPPExtenderBroadcastReceiver.isExtenderInstalled(context);
                //PPApplication.logE("ImportantInfoNotification.showInfoNotification", "extenderVersion="+extenderVersion);
                if ((extenderVersion != 0) && (extenderVersion < PPApplication.VERSION_CODE_EXTENDER_LATEST))
                    show = 2;

                //setShowInfoNotificationOnStartVersion(context, packageVersionCode);
            }
            setShowInfoNotificationOnStart(context, show != 0, packageVersionCode);

            if (/*(savedVersionCode == 0) ||*/ getShowInfoNotificationOnStart(context, packageVersionCode)) {
                //PPApplication.logE("ImportantInfoNotification.showInfoNotification", "show notification");

                if (show == 1)
                    showNotification(context, false/*savedVersionCode == 0*/,
                            context.getString(R.string.info_notification_title),
                            context.getString(R.string.info_notification_text));
                else
                if (show == 2)
                    showNotification(context, false/*savedVersionCode == 0*/,
                            context.getString(R.string.info_notification_title),
                            context.getString(R.string.important_info_accessibility_service_new_version_notification));

                //setShowInfoNotificationOnStart(context, false, packageVersionCode);
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
    }

    static private int canShowNotification(int packageVersionCode, int savedVersionCode, Context context) {
        boolean news = false;
        boolean newExtender = false;

        //PPApplication.logE("ImportantInfoNotification.canShowNotification", "packageVersionCode="+packageVersionCode);

        boolean newsLatest = (packageVersionCode >= ImportantInfoNotification.VERSION_CODE_FOR_NEWS);
        //boolean news4550 = ((packageVersionCode >= 4550) && (packageVersionCode < ImportantInfoNotification.VERSION_CODE_FOR_NEWS));
        //boolean news4340 = ((packageVersionCode >= 4340) && (packageVersionCode < ImportantInfoNotification.VERSION_CODE_FOR_NEWS));
        //boolean news3670 = ((packageVersionCode >= 3670) && (packageVersionCode < ImportantInfoNotification.VERSION_CODE_FOR_NEWS));
        //boolean news1804 = ((packageVersionCode >= 1804) && (packageVersionCode < ImportantInfoNotification.VERSION_CODE_FOR_NEWS));
        //boolean news1772 = ((packageVersionCode >= 1772) && (packageVersionCode < ImportantInfoNotification.VERSION_CODE_FOR_NEWS));

        boolean afterInstall = savedVersionCode == 0;

        int extenderVersion = PPPExtenderBroadcastReceiver.isExtenderInstalled(context);

        /*if (PPApplication.logEnabled()) {
            PPApplication.logE("ImportantInfoNotification.canShowNotification", "newsLatest=" + newsLatest);
            PPApplication.logE("ImportantInfoNotification.canShowNotification", "extenderVersion=" + extenderVersion);
        }*/

        if (newsLatest) {
            // change to false for not show notification
            //noinspection ConstantConditions
            news = false;
        }

        /*if (news4550) {
            if (Build.VERSION.SDK_INT >= 28)
                news = true;
        }*/

        /*if (news4340) {
            DataWrapper dataWrapper = new DataWrapper(context.getApplicationContext(), false, 0, false);
            dataWrapper.fillEventList();
            boolean sensorExists = dataWrapper.eventTypeExists(DatabaseHandler.ETYPE_SMS);
            if (!sensorExists)
                sensorExists = dataWrapper.eventTypeExists(DatabaseHandler.ETYPE_CALL);

            //if (PPApplication.logEnabled()) {
            //    PPApplication.logE("ImportantInfoNotification.canShowNotification", "smsSensorsCount=" + smsSensorsCount);
            //    PPApplication.logE("ImportantInfoNotification.canShowNotification", "callSensorsCount=" + callSensorsCount);
            //}

            //noinspection RedundantIfStatement
            if (!sensorExists)
                news = false;
            else {
                news = true;
            }
        }*/

        /*if (news3670) {
            DataWrapper dataWrapper = new DataWrapper(context.getApplicationContext(), false, 0, false);
            dataWrapper.fillEventList();
            boolean sensorExists = dataWrapper.eventTypeExists(DatabaseHandler.ETYPE_APPLICATION);
            if (!sensorExists)
                sensorExists = dataWrapper.eventTypeExists(DatabaseHandler.ETYPE_ORIENTATION);
            //noinspection RedundantIfStatement
            if (!sensorExists)
                news = false;
            else {
                news = true;
            }
        }*/

        /*if (news1804) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                news = true;
            }
        }*/

        /*if (news1772) {
            //if (android.os.Build.VERSION.SDK_INT >= 21) {
                news = true;
            //}
        }*/

        if ((extenderVersion != 0) && (extenderVersion < PPApplication.VERSION_CODE_EXTENDER_LATEST)) {
            newExtender = true;
        }

        if (afterInstall)
            news = true;

        if (newExtender)
            return 2;
        else
        if (news)
            return 1;
        else
            return 0;
    }

    static private void showNotification(Context context,
                                         @SuppressWarnings("SameParameterValue") boolean firstInstallation,
                                         String title, String text) {
        //noinspection UnnecessaryLocalVariable
        String nTitle = title;
        //noinspection UnnecessaryLocalVariable
        String nText = text;
//        if (android.os.Build.VERSION.SDK_INT < 24) {
//            nTitle = context.getString(R.string.ppp_app_name);
//            nText = title+": "+text;
//        }
        PPApplication.createExclamationNotificationChannel(context);
        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context, PPApplication.EXCLAMATION_NOTIFICATION_CHANNEL)
                .setColor(ContextCompat.getColor(context, R.color.notificationDecorationColor))
                .setSmallIcon(R.drawable.ic_exclamation_notify) // notification icon
                .setContentTitle(nTitle) // title for notification
                .setContentText(nText) // message for notification
                .setAutoCancel(true); // clear notification after click
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(nText));
        Intent intent = new Intent(context, ImportantInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_FIRST_INSTALLATION, firstInstallation);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        //if (android.os.Build.VERSION.SDK_INT >= 21)
        //{
            mBuilder.setCategory(NotificationCompat.CATEGORY_RECOMMENDATION);
            mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        //}
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        try {
            mNotificationManager.notify(
                    PPApplication.IMPORTANT_INFO_NOTIFICATION_TAG,
                    PPApplication.IMPORTANT_INFO_NOTIFICATION_ID, mBuilder.build());
        } catch (Exception e) {
            //Log.e("ImportantInfoNotification.showNotification", Log.getStackTraceString(e));
            PPApplication.recordException(e);
        }
    }

    static void removeNotification(Context context)
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.cancel(
                    PPApplication.IMPORTANT_INFO_NOTIFICATION_TAG,
                    PPApplication.IMPORTANT_INFO_NOTIFICATION_ID);
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
    }

    private static boolean getShowInfoNotificationOnStart(Context context, int version)
    {
        SharedPreferences preferences = ApplicationPreferences.getSharedPreferences(context);
        boolean show = preferences.getBoolean(PREF_SHOW_INFO_NOTIFICATION_ON_START, true);
        int _version = preferences.getInt(PREF_SHOW_INFO_NOTIFICATION_ON_START_VERSION, version);
        return ((_version >= version) && show);
    }

    static void setShowInfoNotificationOnStart(Context context, boolean show, int version)
    {
        SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
        editor.putBoolean(PREF_SHOW_INFO_NOTIFICATION_ON_START, show);
        editor.putInt(PREF_SHOW_INFO_NOTIFICATION_ON_START_VERSION, version);
        editor.apply();
    }

    private static int getShowInfoNotificationOnStartVersion(Context context)
    {
        return ApplicationPreferences.
                getSharedPreferences(context).getInt(PREF_SHOW_INFO_NOTIFICATION_ON_START_VERSION, 0);
    }

    /*
    private static void setShowInfoNotificationOnStartVersion(Context context, int version)
    {
        SharedPreferences.Editor editor = ApplicationPreferences.getEditor(context);
        editor.putInt(PREF_SHOW_INFO_NOTIFICATION_ON_START_VERSION, version);
        editor.apply();
    }
    */
}
