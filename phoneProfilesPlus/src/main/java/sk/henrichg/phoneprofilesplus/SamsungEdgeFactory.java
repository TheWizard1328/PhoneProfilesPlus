package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressLint("NewApi")
class SamsungEdgeFactory implements RemoteViewsService.RemoteViewsFactory {

    private DataWrapper dataWrapper;

    private Context context = null;
    //private int appWidgetId;
    private List<Profile> profileList = new ArrayList<>();

    SamsungEdgeFactory(Context ctxt, Intent intent) {
        context = ctxt;
        /*appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                       AppWidgetManager.INVALID_APPWIDGET_ID);*/
    }
  
    private void createProfilesDataWrapper()
    {
        //PPApplication.loadPreferences(context);

        int monochromeValue = 0xFF;
        String applicationWidgetListIconLightness = ApplicationPreferences.applicationSamsungEdgeIconLightness(context);
        if (applicationWidgetListIconLightness.equals("0")) monochromeValue = 0x00;
        if (applicationWidgetListIconLightness.equals("25")) monochromeValue = 0x40;
        if (applicationWidgetListIconLightness.equals("50")) monochromeValue = 0x80;
        if (applicationWidgetListIconLightness.equals("75")) monochromeValue = 0xC0;
        if (applicationWidgetListIconLightness.equals("100")) monochromeValue = 0xFF;

        if (dataWrapper == null)
        {
            dataWrapper = new DataWrapper(context, true, ApplicationPreferences.applicationSamsungEdgeIconColor(context).equals("1"), monochromeValue);
        }
        else
        {
            dataWrapper.setParameters(true, ApplicationPreferences.applicationSamsungEdgeIconColor(context).equals("1"), monochromeValue);
        }
    }

    public void onCreate() {
        //Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler());
    }
  
    public void onDestroy() {
        if (dataWrapper != null)
            dataWrapper.invalidateDataWrapper();
        dataWrapper = null;
    }

    public int getCount() {
        int count = 0;
        if (profileList != null) {
            for (Profile profile : profileList) {
                if (profile._showInActivator)
                    ++count;
            }
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

            int pos = -1;
            for (Profile profile : profileList)
            {
                if (profile._showInActivator)
                    ++pos;

                if (pos == position)
                {
                    _profile = profile;
                    break;
                }
            }

            return _profile;
        }
    }

    public RemoteViews getViewAt(int position) {

        RemoteViews row;
        //if (!ApplicationPreferences.applicationSamsungEdgeGridLayout(context))
        //    row=new RemoteViews(context.getPackageName(), R.layout.profile_list_widget_item);
        //else
            row=new RemoteViews(context.getPackageName(), R.layout.profile_grid_widget_item);
    
        Profile profile = getItem(position);

        if (profile != null) {

            if (profile.getIsIconResourceID()) {
                if (profile._iconBitmap != null)
                    row.setImageViewBitmap(R.id.widget_profile_list_item_profile_icon, profile._iconBitmap);
                else {
                    row.setImageViewResource(R.id.widget_profile_list_item_profile_icon,
                            context.getResources().getIdentifier(profile.getIconIdentifier(), "drawable", context.getPackageName()));
                }
            } else {
                row.setImageViewBitmap(R.id.widget_profile_list_item_profile_icon, profile._iconBitmap);
            }
            int red = 0xFF;
            int green;
            int blue;
            String applicationWidgetListLightnessT = ApplicationPreferences.applicationSamsungEdgeLightnessT(context);
            if (applicationWidgetListLightnessT.equals("0")) red = 0x00;
            if (applicationWidgetListLightnessT.equals("25")) red = 0x40;
            if (applicationWidgetListLightnessT.equals("50")) red = 0x80;
            if (applicationWidgetListLightnessT.equals("75")) red = 0xC0;
            if (applicationWidgetListLightnessT.equals("100")) red = 0xFF;
            green = red;
            blue = red;
            if (!ApplicationPreferences.applicationSamsungEdgeHeader(context)) {
                if (profile._checked) {
                    if (android.os.Build.VERSION.SDK_INT >= 16)
                        row.setTextViewTextSize(R.id.widget_profile_list_item_profile_name, TypedValue.COMPLEX_UNIT_SP, 17);

                    //if (PPApplication.applicationWidgetListIconColor.equals("1"))
                    row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xFF, red, green, blue));
                    //else
                    //	row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.parseColor("#33b5e5"));
                } else {
                    if (android.os.Build.VERSION.SDK_INT >= 16)
                        row.setTextViewTextSize(R.id.widget_profile_list_item_profile_name, TypedValue.COMPLEX_UNIT_SP, 15);

                    //if (PPApplication.applicationWidgetListIconColor.equals("1"))
                    row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xCC, red, green, blue));
                    //else
                    //	row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xFF, red, green, blue));
                }
            } else {
                row.setTextColor(R.id.widget_profile_list_item_profile_name, Color.argb(0xFF, red, green, blue));
            }
            if ((!ApplicationPreferences.applicationSamsungEdgeHeader(context)) && (profile._checked)) {
                // hm, interesting, how to set bold style for RemoteView text ;-)
                //String profileName = dataWrapper.getProfileNameWithManualIndicator(profile, !ApplicationPreferences.applicationSamsungEdgeGridLayout(context), true, ApplicationPreferences.applicationSamsungEdgeGridLayout(context));
                String profileName = dataWrapper.getProfileNameWithManualIndicator(profile, false, true, true);
                Spannable sb = new SpannableString(profileName);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, profileName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                row.setTextViewText(R.id.widget_profile_list_item_profile_name, sb);
            } else {
                //String profileName = profile.getProfileNameWithDuration(ApplicationPreferences.applicationSamsungEdgeGridLayout(context), dataWrapper.context);
                String profileName = profile.getProfileNameWithDuration(true, dataWrapper.context);
                row.setTextViewText(R.id.widget_profile_list_item_profile_name, profileName);
            }
            /*if (!ApplicationPreferences.applicationSamsungEdgeGridLayout(context)) {
                if (ApplicationPreferences.applicationSamsungEdgePrefIndicator(context)) {
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
        return(true);
    }

    public void onDataSetChanged() {
        //Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler());

        createProfilesDataWrapper();

        List<Profile> newProfileList = dataWrapper.getNewProfileList();

        if (!ApplicationPreferences.applicationSamsungEdgeHeader(context))
        {
            // show activated profile in list if is not showed in activator
            Profile profile = dataWrapper.getActivatedProfile(newProfileList);
            if ((profile != null) && (!profile._showInActivator))
            {
                profile._showInActivator = true;
                profile._porder = -1;
            }
        }

        Collections.sort(newProfileList, new ProfileComparator());

        dataWrapper.invalidateProfileList();
        dataWrapper.setProfileList(newProfileList, false);
        profileList = newProfileList;
    }

    private class ProfileComparator implements Comparator<Profile> {

        public int compare(Profile lhs, Profile rhs) {
            int res = 0;
            if ((lhs != null) && (rhs != null))
                res = lhs._porder - rhs._porder;
            return res;
        }
    }

}
