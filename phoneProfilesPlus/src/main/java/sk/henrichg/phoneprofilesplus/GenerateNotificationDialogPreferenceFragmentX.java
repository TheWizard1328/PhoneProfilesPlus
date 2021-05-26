package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.preference.PreferenceDialogFragmentCompat;

@SuppressWarnings("WeakerAccess")
public class GenerateNotificationDialogPreferenceFragmentX extends PreferenceDialogFragmentCompat {

    private GenerateNotificationDialogPreferenceX preference;

    // Layout widgets
    private CheckBox generateChBtn = null;
    private RadioButton informationIconRBtn = null;
    private RadioButton exclamationIconRBtn = null;
    private RadioButton profileIconRBtn = null;
    private EditText notificationTitleEdtText = null;
    private EditText notificationBodyEdtText = null;

    @SuppressLint("InflateParams")
    @Override
    protected View onCreateDialogView(Context context)
    {
        preference = (GenerateNotificationDialogPreferenceX) getPreference();
        preference.fragment = this;

        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.dialog_generate_notification_preference, null, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        generateChBtn = view.findViewById(R.id.generateNotificationPrefDialogGenerate);
        informationIconRBtn = view.findViewById(R.id.generateNotificationPrefDialogInformationIcon);
        exclamationIconRBtn = view.findViewById(R.id.generateNotificationPrefDialogExclamationIcon);
        profileIconRBtn = view.findViewById(R.id.generateNotificationPrefDialogProfileIcon);
        notificationTitleEdtText = view.findViewById(R.id.generateNotificationPrefDialogNotificationTitle);
        notificationBodyEdtText = view.findViewById(R.id.generateNotificationPrefDialogNotificationBody);

        generateChBtn.setChecked(preference.generate == 1);
        informationIconRBtn.setChecked(preference.iconType == 0);
        exclamationIconRBtn.setChecked(preference.iconType == 1);
        profileIconRBtn.setChecked(preference.iconType == 2);
        notificationTitleEdtText.setText(preference.notificationTitle);
        notificationBodyEdtText.setText(preference.notificationBody);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            preference.generate = generateChBtn.isChecked() ? 1 : 0;
            if (informationIconRBtn.isChecked())
                preference.iconType = 0;
            else
            if (exclamationIconRBtn.isChecked())
                preference.iconType = 1;
            else
            if (profileIconRBtn.isChecked())
                preference.iconType = 2;
            else
                preference.iconType = 0;
            preference.notificationTitle = notificationTitleEdtText.getText().toString();
            preference.notificationBody = notificationBodyEdtText.getText().toString();

            preference.persistValue();
        }
        else {
            preference.resetSummary();
        }

        preference.fragment = null;
    }

}
