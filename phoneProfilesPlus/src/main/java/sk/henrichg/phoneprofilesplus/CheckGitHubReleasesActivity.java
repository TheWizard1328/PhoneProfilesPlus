package sk.henrichg.phoneprofilesplus;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CheckGitHubReleasesActivity extends AppCompatActivity {

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
        GlobalGUIRoutines.setTheme(this, true, false/*, false*/, false);
        //GlobalGUIRoutines.setLanguage(this);

        showDialog(this);
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(0, 0);
    }

    static void showDialog(final Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle(R.string.menu_check_github_releases);
        String message;
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(PPApplication.PACKAGE_NAME, 0);
            message = activity.getString(R.string.check_github_releases_actual_version) + " " + pInfo.versionName + " (" + PPApplication.getVersionCode(pInfo) + ")\n";
        } catch (Exception e) {
            message = "";
        }
        if (PPApplication.googlePlayInstaller)
            message = message + activity.getString(R.string.about_application_package_type_google_play);
        else
            message = message + activity.getString(R.string.about_application_package_type_github);
        message = message + "\n\n";
        message = message + activity.getString(R.string.check_github_releases_install_info_1) + "\n";
        message = message + activity.getString(R.string.check_github_releases_install_info_2) + " ";
        message = message + activity.getString(R.string.event_preferences_PPPExtenderInstallInfo_summary_3);
        dialogBuilder.setMessage(message);
        //dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton(R.string.check_github_releases_go_to_github, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String url = "https://github.com/henrichg/PhoneProfilesPlus/releases";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                try {
                    activity.startActivity(Intent.createChooser(i, activity.getString(R.string.web_browser_chooser)));
                } catch (Exception e) {
                    PPApplication.recordException(e);
                }
                activity.finish();
            }
        });
        dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                activity.finish();
            }
        });
        AlertDialog dialog = dialogBuilder.create();

//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Button positive = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
//                if (positive != null) positive.setAllCaps(false);
//                Button negative = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
//                if (negative != null) negative.setAllCaps(false);
//            }
//        });

        if (!activity.isFinishing())
            dialog.show();
    }

}
