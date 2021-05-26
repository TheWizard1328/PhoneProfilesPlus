package sk.henrichg.phoneprofilesplus;


import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

class RunStopIndicatorPopupWindow extends GuiInfoPopupWindow {

    RunStopIndicatorPopupWindow(final DataWrapper dataWrapper, final Activity activity) {
        super(R.layout.popup_window_run_stop_indicator, R.string.editor_activity_targetHelps_trafficLightIcon_title, activity);

        // Disable default animation
        //setAnimationStyle(0);

        final TextView textView = popupView.findViewById(R.id.run_stop_indicator_popup_window_important_info);
        textView.setClickable(true);
        textView.setOnClickListener(v -> {
            Intent intentLaunch = new Intent(activity, ImportantInfoActivity.class);
            intentLaunch.putExtra(ImportantInfoActivity.EXTRA_SHOW_QUICK_GUIDE, false);
            intentLaunch.putExtra(ImportantInfoActivity.EXTRA_SCROLL_TO, R.id.activity_info_notification_event_not_started);
            activity.startActivity(intentLaunch);

            dismiss();
        });

        final SwitchCompat checkBox = popupView.findViewById(R.id.run_stop_indicator_popup_window_checkbox);
        checkBox.setChecked(Event.getGlobalEventsRunning());
        checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (dataWrapper != null)
                dataWrapper.runStopEventsWithAlert(activity, checkBox, isChecked);
        });
    }

}
