package sk.henrichg.phoneprofilesplus;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ExitApplicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        //PPApplication.logE("ExitApplicationActivity.onCreate", "xxx");
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // set theme and language for dialog alert ;-)
        // not working on Android 2.3.x
        GlobalGUIRoutines.setTheme(this, true, false/*, false*/, false);
        //GlobalGUIRoutines.setLanguage(this);

        //PPApplication.logE("ExitApplicationActivity.onStart", "xxx");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.exit_application_alert_title);
        dialogBuilder.setMessage(R.string.exit_application_alert_message);
        //dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        dialogBuilder.setPositiveButton(R.string.alert_button_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //PPApplication.logE("ExitApplicationActivity.onStart", "exit");

                IgnoreBatteryOptimizationNotification.setShowIgnoreBatteryOptimizationNotificationOnStart(getApplicationContext(), true);
                SharedPreferences settings = ApplicationPreferences.getSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(ApplicationPreferences.PREF_APPLICATION_EVENT_NEVER_ASK_FOR_ENABLE_RUN, false);
                editor.putBoolean(ApplicationPreferences.PREF_APPLICATION_NEVER_ASK_FOR_GRANT_ROOT, false);
                editor.apply();
                ApplicationPreferences.applicationEventNeverAskForEnableRun(getApplicationContext());
                ApplicationPreferences.applicationNeverAskForGrantRoot(getApplicationContext());

                DataWrapper dataWrapper = new DataWrapper(getApplicationContext(), false, 0, false);
                //PPApplication.logE("PPApplication.exitApp", "from ExitApplicationActivity.onStart shutdown=false");
                PPApplication.exitApp(true, getApplicationContext(), dataWrapper, ExitApplicationActivity.this, false/*, true, true*/);
            }
        });
        dialogBuilder.setNegativeButton(R.string.alert_button_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ExitApplicationActivity.this.finish();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        /*dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positive = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if (positive != null) positive.setAllCaps(false);
                Button negative = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negative != null) negative.setAllCaps(false);
            }
        });*/
        if (!isFinishing())
            dialog.show();
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
