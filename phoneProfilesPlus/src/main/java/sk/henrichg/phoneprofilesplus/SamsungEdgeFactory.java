package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SamsungEdgeFactory implements RemoteViewsService.RemoteViewsFactory {

    private DataWrapper dataWrapper;

    private final Context context;
    //private int appWidgetId;
    //private List<Profile> profileList = new ArrayList<>();

    SamsungEdgeFactory(Context context, @SuppressWarnings("unused") Intent intent) {
        this.context = context;
        /*appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                       AppWidgetManager.INVALID_APPWIDGET_ID);*/
    }
  
    private DataWrapper createProfilesDataWrapper(boolean local)
    {
        String applicationWidgetListIconLightness;
        String applicationSamsungEdgeIconColor;
        boolean applicationSamsungEdgeCustomIconLightness;
        synchronized (PPApplication.applicationPreferencesMutex) {
            applicationWidgetListIconLightness = ApplicationPreferences.applicationSamsungEdgeIconLightness;
            applicationSamsungEdgeIconColor = ApplicationPreferences.applicationSamsungEdgeIconColor;
            applicationSamsungEdgeCustomIconLightness = ApplicationPreferences.applicationSamsungEdgeCustomIconLightness;
        }

        int monochromeValue = 0xFF;
        if (applicationWidgetListIconLightness.equals("0")) monochromeValue = 0x00;
        if (applicationWidgetListIconLightness.equals("25")) monochromeValue = 0x40;
        if (applicationWidgetListIconLightness.equals("50")) monochromeValue = 0x80;
        if (applicationWidgetListIconLightness.equals("75")) monochromeValue = 0xC0;
        //if (applicationWidgetListIconLightness.equals("100")) monochromeValue = 0xFF;

        if (local) {
            return new DataWrapper(context.getApplicationContext(), applicationSamsungEdgeIconColor.equals("1"),
                    monochromeValue, applicationSamsungEdgeCustomIconLightness);
        }
        else {
            if (dataWrapper == null) {
                dataWrapper = new DataWrapper(context.getApplicationContext(), applicationSamsungEdgeIconColor.equals("1"),
                        monochromeValue, applicationSamsungEdgeCustomIconLightness);
            } else {
                dataWrapper.setParameters(applicationSamsungEdgeIconColor.equals("1"),
                        monochromeValue, applicationSamsungEdgeCustomIconLightness);
            }
            return dataWrapper;
        }
    }

    public void onCreate() {
    }
  
    public void onDestroy() {
        /*if (dataWrapper != null)
            dataWrapper.invalidateDataWrapper();
        dataWrapper = null;*/
    }

    public int getCount() {
        int count = 0;
        if (dataWrapper != null) {
            //if (dataWrapper.profileList != null) {
                for (Profile profile : dataWrapper.profileList) {
                    if (profile._showInActivator)
                        ++count;
                }
            //}
        }
        return count;
    }

    private Profile getItem(int position)
    {
        if (getCount() == 0)
            return null;
        else
        {
            Profile _profile = null;

            if (dataWrapper != null) {
                int pos = -1;
                for (Profile profile : dataWrapper.profileList) {
                    if (profile._showInActivator)
                        ++pos;

                    if (pos == position) {
                        _profile = profile;
                        break;
                    }
                }
            }

            return _profile;
        }
    }

    public RemoteViews getViewAt(int position) {

        RemoteViews row;
        //if (!applicationSamsungEdgeGridLayout)
        //    row=new RemoteViews(context.PPApplication.PACKAGE_NAME, R.layout.profile_list_widget_item);
        //else
            row=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.samsung_edge_item);

        Profile profile = getItem(position);

        if (profile != null) {
            String applicationWidgetListLightnessT;
            boolean applicationSamsungEdgeHeader;
            synchronized (PPApplication.applicationPreferencesMutex) {
                applicationWidgetListLightnessT = ApplicationPreferences.applicationSamsungEdgeLightnessT;
                applicationSamsungEdgeHeader = ApplicationPreferences.applicationSamsungEdgeHeader;
            }

            if (profile.getIsIconResourceID()) {
                if (profile._iconBitmap != null)
                    row.setImageViewBitmap(R.id.widget_profile_list_item_profile_icon, profile._iconBitmap);
                else {
                    row.setImageViewResource(R.id.widget_profile_list_item_profile_icon,
                            /*context.getResources().getIdentifier(profile.getIconIdentifier(), "drawable", context.PPApplication.PACKAGE_NAME));*/
                            Profile.getIconResource(profile.getIconIdentifier()));
                }
            } else {
                row.setImageViewBitmap(R.id.widget_profile_list_item_profile_icon, profile._iconBitmap);
            }
            int red = 0xFF;
            int green;
            int blue;
            if (applicationWidgetListLightnessT.equals("0")) red = 0x00;
            if (applicationWidgetListLightnessT.equals("25")) red = 0x40;
            if (applicationWidgetListLightnessT.equals("50")) red = 0x80;
            if (applicationWidgetListLightnessT.equals("75")) red = 0xC0;
            //if (applicationWidgetListLightnessT.equals("100")) red = 0xFF;
            green = red;
            blue = red;
            if (!applicationSamsungEdgeHeader) {
                if (profile._checked) {
                    row.setTextViewTextSize(R.id.widget_profile_list_item_profile_name, TypedValue.COMPLEX_UNIT_SP, 16);

                    //if (PPApplication.applicationWidgetListIconColor.equals("1"))
                    row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xFF, red, green, blue));
                    //else
                    //	row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.parseColor("#33b5e5"));
                } else {
                    row.setTextViewTextSize(R.id.widget_profile_list_item_profile_name, TypedValue.COMPLEX_UNIT_SP, 15);

                    //if (PPApplication.applicationWidgetListIconColor.equals("1"))
                    row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xCC, red, green, blue));
                    //else
                    //	row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xFF, red, green, blue));
                }
            } else {
                row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xFF, red, green, blue));
            }
            if ((!applicationSamsungEdgeHeader) && (profile._checked)) {
                // hm, interesting, how to set bold style for RemoteView text ;-)
                Spannable profileName = DataWrapper.getProfileNameWithManualIndicator(profile, false, "", true, true, true, dataWrapper);
                Spannable sb = new SpannableString(profileName);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, profileName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                row.setTextViewText(R.id.widget_profile_list_item_profile_name, sb);
            } else {
                Spannable profileName = profile.getProfileNameWithDuration("", "", true, false, context.getApplicationContext());
                row.setTextViewText(R.id.widget_profile_list_item_profile_name, profileName);
            }
            /*if (!applicationSamsungEdgeGridLayout) {
                if (applicationSamsungEdgePrefIndicator) {
                    if (profile._preferencesIndicator != null)
                        row.setImageViewBitmap(R.id.widget_profile_list_profile_pref_indicator, profile._preferencesIndicator);
                    else
                        row.setImageViewResource(R.id.widget_profile_list_profile_pref_indicator, R.drawable.ic_empty);
                }
                else
                    row.setImageViewResource(R.id.widget_profile_list_profile_pref_indicator, R.drawable.ic_empty);
            }*/

            Intent i = new Intent();
            Bundle extras = new Bundle();

            if (Event.getGlobalEventsRunning() && (position == 0))
                extras.putLong(PPApplication.EXTRA_PROFILE_ID, Profile.RESTART_EVENTS_PROFILE_ID);
            else
                extras.putLong(PPApplication.EXTRA_PROFILE_ID, profile._id);
            extras.putInt(PPApplication.EXTRA_STARTUP_SOURCE, PPApplication.STARTUP_SOURCE_SHORTCUT);
            i.putExtras(extras);
            row.setOnClickFillInIntent(R.id.widget_profile_list_item, i);

        }

        return(row);
    }

    public RemoteViews getLoadingView() {
        return(null);
    }
  
    public int getViewTypeCount() {
        return(1);
    }

    public long getItemId(int position) {
        return(position);
    }

    public boolean hasStableIds() {
        return false;
    }

    public void onDataSetChanged() {
        DataWrapper _dataWrapper = createProfilesDataWrapper(true);

        List<Profile> newProfileList = _dataWrapper.getNewProfileList(true, false);
        _dataWrapper.getEventTimelineList(true);

        boolean applicationSamsungEdgeHeader;
        synchronized (PPApplication.applicationPreferencesMutex) {
            applicationSamsungEdgeHeader = ApplicationPreferences.applicationSamsungEdgeHeader;
        }

        if (!applicationSamsungEdgeHeader)
        {
            // show activated profile in list if is not showed in activator
            Profile profile = _dataWrapper.getActivatedProfile(newProfileList);
            if ((profile != null) && (!profile._showInActivator))
            {
                profile._showInActivator = true;
                profile._porder = -1;
            }
        }

        //noinspection Java8ListSort
        Collections.sort(newProfileList, new ProfileComparator());

        if (Event.getGlobalEventsRunning()) {
            Profile restartEvents = DataWrapper.getNonInitializedProfile(context.getString(R.string.menu_restart_events), "ic_list_item_events_restart_color|1|0|0", 0);
            restartEvents._showInActivator = true;
            newProfileList.add(0, restartEvents);
        }

        createProfilesDataWrapper(false);
        //dataWrapper.invalidateProfileList();
        dataWrapper.setProfileList(newProfileList);
        //profileList = newProfileList;
    }

    private static class ProfileComparator implements Comparator<Profile> {

        public int compare(Profile lhs, Profile rhs) {
            int res = 0;
            if ((lhs != null) && (rhs != null))
                res = lhs._porder - rhs._porder;
            return res;
        }
    }

}
