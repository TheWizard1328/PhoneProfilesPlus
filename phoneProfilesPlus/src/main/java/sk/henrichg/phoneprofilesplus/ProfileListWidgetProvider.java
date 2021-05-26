package sk.henrichg.phoneprofilesplus;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.RemoteViews;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ProfileListWidgetProvider extends AppWidgetProvider {

    //private DataWrapper dataWrapper;

    static final String ACTION_REFRESH_LISTWIDGET = PPApplication.PACKAGE_NAME + ".ACTION_REFRESH_LISTWIDGET";

    //private boolean isLargeLayout;

    private static RemoteViews buildLayout(Context context, /*AppWidgetManager appWidgetManager,*/ int appWidgetId, /*boolean largeLayout,*/ DataWrapper dataWrapper)
    {
        boolean applicationWidgetListHeader;
        boolean applicationWidgetListGridLayout;
        boolean applicationWidgetListPrefIndicator;
        boolean applicationWidgetListBackgroundType;
        String applicationWidgetListBackgroundColor;
        String applicationWidgetListLightnessB;
        String applicationWidgetListBackground;
        boolean applicationWidgetListShowBorder;
        String applicationWidgetListLightnessBorder;
        boolean applicationWidgetListRoundedCorners;
        String applicationWidgetListIconLightness;
        String applicationWidgetListIconColor;
        boolean applicationWidgetListCustomIconLightness;
        String applicationWidgetListLightnessT;
        int applicationWidgetListRoundedCornersRadius;
        synchronized (PPApplication.applicationPreferencesMutex) {
            applicationWidgetListHeader = ApplicationPreferences.applicationWidgetListHeader;
            applicationWidgetListGridLayout = ApplicationPreferences.applicationWidgetListGridLayout;
            applicationWidgetListPrefIndicator = ApplicationPreferences.applicationWidgetListPrefIndicator;
            applicationWidgetListBackgroundType = ApplicationPreferences.applicationWidgetListBackgroundType;
            applicationWidgetListBackgroundColor = ApplicationPreferences.applicationWidgetListBackgroundColor;
            applicationWidgetListLightnessB = ApplicationPreferences.applicationWidgetListLightnessB;
            applicationWidgetListBackground = ApplicationPreferences.applicationWidgetListBackground;
            applicationWidgetListShowBorder = ApplicationPreferences.applicationWidgetListShowBorder;
            applicationWidgetListLightnessBorder = ApplicationPreferences.applicationWidgetListLightnessBorder;
            applicationWidgetListRoundedCorners = ApplicationPreferences.applicationWidgetListRoundedCorners;
            applicationWidgetListIconLightness = ApplicationPreferences.applicationWidgetListIconLightness;
            applicationWidgetListIconColor = ApplicationPreferences.applicationWidgetListIconColor;
            applicationWidgetListCustomIconLightness = ApplicationPreferences.applicationWidgetListCustomIconLightness;
            applicationWidgetListLightnessT = ApplicationPreferences.applicationWidgetListLightnessT;
            applicationWidgetListRoundedCornersRadius = ApplicationPreferences.applicationWidgetListRoundedCornersRadius;
        }

        int monochromeValue = 0xFF;
        switch (applicationWidgetListIconLightness) {
            case "0":
                monochromeValue = 0x00;
                break;
            case "12":
                monochromeValue = 0x20;
                break;
            case "25":
                monochromeValue = 0x40;
                break;
            case "37":
                monochromeValue = 0x60;
                break;
            case "50":
                monochromeValue = 0x80;
                break;
            case "62":
                monochromeValue = 0xA0;
                break;
            case "75":
                monochromeValue = 0xC0;
                break;
            case "87":
                monochromeValue = 0xE0;
                break;
            case "100":
                monochromeValue = 0xFF;
                break;
        }

        // set background
        int redBackground = 0x00;
        int greenBackground;
        int blueBackground;
        if (applicationWidgetListBackgroundType) {
            int bgColor = Integer.parseInt(applicationWidgetListBackgroundColor);
            redBackground = Color.red(bgColor);
            greenBackground = Color.green(bgColor);
            blueBackground = Color.blue(bgColor);
        }
        else {
            switch (applicationWidgetListLightnessB) {
                case "0":
                    redBackground = 0x00;
                    break;
                case "12":
                    redBackground = 0x20;
                    break;
                case "25":
                    redBackground = 0x40;
                    break;
                case "37":
                    redBackground = 0x60;
                    break;
                case "50":
                    redBackground = 0x80;
                    break;
                case "62":
                    redBackground = 0xA0;
                    break;
                case "75":
                    redBackground = 0xC0;
                    break;
                case "87":
                    redBackground = 0xE0;
                    break;
                case "100":
                    redBackground = 0xFF;
                    break;
            }
            greenBackground = redBackground;
            blueBackground = redBackground;
        }
        int alphaBackground = 0x40;
        switch (applicationWidgetListBackground) {
            case "0":
                alphaBackground = 0x00;
                break;
            case "12":
                alphaBackground = 0x20;
                break;
            case "25":
                alphaBackground = 0x40;
                break;
            case "37":
                alphaBackground = 0x60;
                break;
            case "50":
                alphaBackground = 0x80;
                break;
            case "62":
                alphaBackground = 0xA0;
                break;
            case "75":
                alphaBackground = 0xC0;
                break;
            case "87":
                alphaBackground = 0xE0;
                break;
            case "100":
                alphaBackground = 0xFF;
                break;
        }
        int redBorder = 0xFF;
        int greenBorder;
        int blueBorder;
        if (applicationWidgetListShowBorder) {
            switch (applicationWidgetListLightnessBorder) {
                case "0":
                    redBorder = 0x00;
                    break;
                case "12":
                    redBorder = 0x20;
                    break;
                case "25":
                    redBorder = 0x40;
                    break;
                case "37":
                    redBorder = 0x60;
                    break;
                case "50":
                    redBorder = 0x80;
                    break;
                case "62":
                    redBorder = 0xA0;
                    break;
                case "75":
                    redBorder = 0xC0;
                    break;
                case "87":
                    redBorder = 0xE0;
                    break;
                case "100":
                    redBorder = 0xFF;
                    break;
            }
        }
        greenBorder = redBorder;
        blueBorder = redBorder;

        int redText = 0xFF;
        switch (applicationWidgetListLightnessT) {
            case "0":
                redText = 0x00;
                break;
            case "12":
                redText = 0x20;
                break;
            case "25":
                redText = 0x40;
                break;
            case "37":
                redText = 0x60;
                break;
            case "50":
                redText = 0x80;
                break;
            case "62":
                redText = 0xA0;
                break;
            case "75":
                redText = 0xC0;
                break;
            case "87":
                redText = 0xE0;
                break;
            case "100":
                redText = 0xFF;
                break;
        }
        int greenText = redText; int blueText = redText;

        int restartEventsLightness = redText;
        int separatorLightness = redText;

        //------------------

        Intent svcIntent=new Intent(context, ProfileListWidgetService.class);

        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widget;

        //if (largeLayout)
        //{
            if (applicationWidgetListHeader)
            {
                if (!applicationWidgetListGridLayout)
                {
                    if (applicationWidgetListPrefIndicator)
                        widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_list_widget);
                    else
                        widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_list_widget_no_indicator);
                }
                else
                {
                    if (applicationWidgetListPrefIndicator)
                        widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_grid_widget);
                    else
                        widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_grid_widget_no_indicator);
                }
            }
            else
            {
                if (!applicationWidgetListGridLayout)
                    widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_list_widget_no_header);
                else
                    widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_grid_widget_no_header);
            }
        /*}
        else
        {
            if (applicationWidgetListPrefIndicator)
                widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_list_widget_small);
            else
                widget=new RemoteViews(PPApplication.PACKAGE_NAME, R.layout.profile_list_widget_small_no_indicator);
        }*/

//        PPApplication.logE("ProfileListWidgetProvider.onUpdate", "applicationWidgetListRoundedCornersRadius="+applicationWidgetListRoundedCornersRadius);
        int roundedBackground = 0;
        int roundedBorder = 0;
        switch (applicationWidgetListRoundedCornersRadius) {
            case 1:
                roundedBackground = R.drawable.rounded_widget_background_1;
                roundedBorder = R.drawable.rounded_widget_border_1;
                break;
            case 2:
                roundedBackground = R.drawable.rounded_widget_background_2;
                roundedBorder = R.drawable.rounded_widget_border_2;
                break;
            case 3:
                roundedBackground = R.drawable.rounded_widget_background_3;
                roundedBorder = R.drawable.rounded_widget_border_3;
                break;
            case 4:
                roundedBackground = R.drawable.rounded_widget_background_4;
                roundedBorder = R.drawable.rounded_widget_border_4;
                break;
            case 5:
                roundedBackground = R.drawable.rounded_widget_background_5;
                roundedBorder = R.drawable.rounded_widget_border_5;
                break;
            case 6:
                roundedBackground = R.drawable.rounded_widget_background_6;
                roundedBorder = R.drawable.rounded_widget_border_6;
                break;
            case 7:
                roundedBackground = R.drawable.rounded_widget_background_7;
                roundedBorder = R.drawable.rounded_widget_border_7;
                break;
            case 8:
                roundedBackground = R.drawable.rounded_widget_background_8;
                roundedBorder = R.drawable.rounded_widget_border_8;
                break;
            case 9:
                roundedBackground = R.drawable.rounded_widget_background_9;
                roundedBorder = R.drawable.rounded_widget_border_9;
                break;
            case 10:
                roundedBackground = R.drawable.rounded_widget_background_10;
                roundedBorder = R.drawable.rounded_widget_border_10;
                break;
            case 11:
                roundedBackground = R.drawable.rounded_widget_background_11;
                roundedBorder = R.drawable.rounded_widget_border_11;
                break;
            case 12:
                roundedBackground = R.drawable.rounded_widget_background_12;
                roundedBorder = R.drawable.rounded_widget_border_12;
                break;
            case 13:
                roundedBackground = R.drawable.rounded_widget_background_13;
                roundedBorder = R.drawable.rounded_widget_border_13;
                break;
            case 14:
                roundedBackground = R.drawable.rounded_widget_background_14;
                roundedBorder = R.drawable.rounded_widget_border_14;
                break;
            case 15:
                roundedBackground = R.drawable.rounded_widget_background_15;
                roundedBorder = R.drawable.rounded_widget_border_15;
                break;
        }
        if (roundedBackground != 0)
            widget.setImageViewResource(R.id.widget_profile_list_background, roundedBackground);
        else
            widget.setImageViewResource(R.id.widget_profile_list_background, R.drawable.ic_empty);
        if (roundedBorder != 0)
            widget.setImageViewResource(R.id.widget_profile_list_rounded_border, roundedBorder);
        else
            widget.setImageViewResource(R.id.widget_profile_list_rounded_border, R.drawable.ic_empty);

        if (applicationWidgetListRoundedCorners) {
            widget.setViewVisibility(R.id.widget_profile_list_background, View.VISIBLE);
            widget.setViewVisibility(R.id.widget_profile_list_not_rounded_border, View.GONE);
            if (applicationWidgetListShowBorder)
                widget.setViewVisibility(R.id.widget_profile_list_rounded_border, View.VISIBLE);
            else
                widget.setViewVisibility(R.id.widget_profile_list_rounded_border, View.GONE);
            widget.setInt(R.id.widget_profile_list_root, "setBackgroundColor", 0x00000000);
            widget.setInt(R.id.widget_profile_list_background, "setColorFilter", Color.argb(0xFF, redBackground, greenBackground, blueBackground));
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                widget.setInt(R.id.widget_profile_list_background, "setImageAlpha", alphaBackground);
            //else
            //    widget.setInt(R.id.widget_profile_list_background, "setAlpha", alpha);
            if (applicationWidgetListShowBorder)
                widget.setInt(R.id.widget_profile_list_rounded_border, "setColorFilter", Color.argb(0xFF, redBorder, greenBorder, blueBorder));
        }
        else {
            widget.setViewVisibility(R.id.widget_profile_list_background, View.GONE);
            widget.setViewVisibility(R.id.widget_profile_list_rounded_border, View.GONE);
            if (applicationWidgetListShowBorder)
                widget.setViewVisibility(R.id.widget_profile_list_not_rounded_border, View.VISIBLE);
            else
                widget.setViewVisibility(R.id.widget_profile_list_not_rounded_border, View.GONE);
            widget.setInt(R.id.widget_profile_list_root, "setBackgroundColor", Color.argb(alphaBackground, redBackground, greenBackground, blueBackground));
            /*widget.setInt(R.id.widget_profile_list_background, "setColorFilter", 0x00000000);
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                widget.setInt(R.id.widget_profile_list_background, "setImageAlpha", 0);
            //else
            //    widget.setInt(R.id.widget_profile_list_background, "setAlpha", 0);*/
            if (applicationWidgetListShowBorder)
                widget.setInt(R.id.widget_profile_list_not_rounded_border, "setColorFilter", Color.argb(0xFF, redBorder, greenBorder, blueBorder));
        }

        // header
        if (applicationWidgetListHeader/* || (!largeLayout)*/)
        {
            Profile profile;

            //boolean fullyStarted = false;
            //if (PhoneProfilesService.getInstance() != null)
            //    fullyStarted = PhoneProfilesService.getInstance().getApplicationFullyStarted();
            //boolean fullyStarted = PPApplication.applicationFullyStarted;
            //if ((!fullyStarted) /*|| applicationPackageReplaced*/)
            //    profile = null;
            //else
                profile = dataWrapper.getActivatedProfile(false, false);

            boolean isIconResourceID;
            String iconIdentifier;
            Spannable profileName;
            if (profile != null)
            {
                profile.generateIconBitmap(context.getApplicationContext(),
                        applicationWidgetListIconColor.equals("1"),
                        monochromeValue,
                        applicationWidgetListCustomIconLightness);
                if (applicationWidgetListPrefIndicator)
                    profile.generatePreferencesIndicator(context.getApplicationContext(),
                            applicationWidgetListIconColor.equals("1"),
                        monochromeValue);
                isIconResourceID = profile.getIsIconResourceID();
                iconIdentifier = profile.getIconIdentifier();
                profileName = DataWrapper.getProfileNameWithManualIndicator(profile, true, "", true, false, false, dataWrapper);
            }
            else
            {
                // create empty profile and set icon resource
                profile = new Profile();
                profile._name = context.getResources().getString(R.string.profiles_header_profile_name_no_activated);
                profile._icon = Profile.PROFILE_ICON_DEFAULT+"|1|0|0";

                profile.generateIconBitmap(context.getApplicationContext(),
                        applicationWidgetListIconColor.equals("1"),
                        monochromeValue,
                        applicationWidgetListCustomIconLightness);
                /*if (applicationWidgetListPrefIndicator)
                    profile.generatePreferencesIndicator(context,
                        applicationWidgetListIconColor.equals("1"),
                        monochromeValue);*/
                isIconResourceID = profile.getIsIconResourceID();
                iconIdentifier = profile.getIconIdentifier();
                profileName = new SpannableString(profile._name);
            }
            if (isIconResourceID)
            {
                if (profile._iconBitmap != null)
                    widget.setImageViewBitmap(R.id.widget_profile_list_header_profile_icon, profile._iconBitmap);
                else {
                    //int iconResource = context.getResources().getIdentifier(iconIdentifier, "drawable", context.PPApplication.PACKAGE_NAME);
                    int iconResource = Profile.getIconResource(iconIdentifier);
                    widget.setImageViewResource(R.id.widget_profile_list_header_profile_icon, iconResource);
                }
            }
            else
            {
                widget.setImageViewBitmap(R.id.widget_profile_list_header_profile_icon, profile._iconBitmap);
            }

            widget.setTextColor(R.id.widget_profile_list_header_profile_name, Color.argb(0xFF, redText, greenText, blueText));

            widget.setTextViewText(R.id.widget_profile_list_header_profile_name, profileName);
            if (applicationWidgetListPrefIndicator)
            {
                if (profile._preferencesIndicator != null) {
                    widget.setImageViewBitmap(R.id.widget_profile_list_header_profile_pref_indicator, profile._preferencesIndicator);
                    widget.setViewVisibility(R.id.widget_profile_list_header_profile_pref_indicator, VISIBLE);
                }
                else
                    widget.setViewVisibility(R.id.widget_profile_list_header_profile_pref_indicator, GONE);
                    //widget.setImageViewResource(R.id.widget_profile_list_header_profile_pref_indicator, R.drawable.ic_empty);
            }
            //if (largeLayout)
            //{
                widget.setInt(R.id.widget_profile_list_header_separator, "setBackgroundColor", Color.argb(0xFF, separatorLightness, separatorLightness, separatorLightness));
            //}

            //if (Event.getGlobalEventsRunning() && PPApplication.getApplicationStarted(true)) {
                Bitmap bitmap = BitmapManipulator.getBitmapFromResource(R.drawable.ic_widget_restart_events, true, context);
                bitmap = BitmapManipulator.monochromeBitmap(bitmap, restartEventsLightness);
                widget.setImageViewBitmap(R.id.widget_profile_list_header_restart_events, bitmap);
            //}

        }
        ////////////////////////////////////////////////

        // clicks
        //if (largeLayout)
        //{
            Intent intent = new Intent(context, EditorProfilesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent,
                                                        PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setOnClickPendingIntent(R.id.widget_profile_list_header_profile_root, pendingIntent);

            //if (Event.getGlobalEventsRunning() && PPApplication.getApplicationStarted(true)) {
                //widget.setViewVisibility(R.id.widget_profile_list_header_restart_events, View.VISIBLE);
                Intent intentRE = new Intent(context, RestartEventsFromGUIActivity.class);
                PendingIntent pIntentRE = PendingIntent.getActivity(context, 2, intentRE, PendingIntent.FLAG_UPDATE_CURRENT);
                widget.setOnClickPendingIntent(R.id.widget_profile_list_header_restart_events_click, pIntentRE);
            //}
            //else
            //    widget.setViewVisibility(R.id.widget_profile_list_header_restart_events, View.GONE);

            if (!applicationWidgetListGridLayout)
                widget.setRemoteAdapter(R.id.widget_profile_list, svcIntent);
            else
                widget.setRemoteAdapter(R.id.widget_profile_grid, svcIntent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews
            // object above.
            if (!applicationWidgetListGridLayout)
                widget.setEmptyView(R.id.widget_profile_list, R.id.widget_profiles_list_empty);
            else
                widget.setEmptyView(R.id.widget_profile_grid, R.id.widget_profiles_list_empty);

            Intent clickIntent=new Intent(context, BackgroundActivateProfileActivity.class);
            clickIntent.putExtra(PPApplication.EXTRA_STARTUP_SOURCE, PPApplication.STARTUP_SOURCE_WIDGET);
            PendingIntent clickPI=PendingIntent.getActivity(context, 300,
                                                        clickIntent,
                                                        PendingIntent.FLAG_UPDATE_CURRENT);

            if (!applicationWidgetListGridLayout)
                widget.setPendingIntentTemplate(R.id.widget_profile_list, clickPI);
            else
                widget.setPendingIntentTemplate(R.id.widget_profile_grid, clickPI);
        /*}
        else
        {
            Intent intent = new Intent(context, LauncherActivity.class);
            // clear all opened activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(PPApplication.EXTRA_STARTUP_SOURCE, PPApplication.STARTUP_SOURCE_WIDGET);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 300, intent,
                                                        PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setOnClickPendingIntent(R.id.widget_profile_list_header, pendingIntent);

            //if (Event.getGlobalEventsRunning()) {
                //widget.setViewVisibility(R.id.widget_profile_list_header_restart_events, View.VISIBLE);
                Intent intentRE = new Intent(context, RestartEventsFromGUIActivity.class);
                PendingIntent pIntentRE = PendingIntent.getActivity(context, 2, intentRE, PendingIntent.FLAG_UPDATE_CURRENT);
                widget.setOnClickPendingIntent(R.id.widget_profile_list_header_restart_events, pIntentRE);
            //}
            //else
            //    widget.setViewVisibility(R.id.widget_profile_list_header_restart_events, View.GONE);
        }*/

        return widget;
    }

    /*
    private void createProfilesDataWrapper(Context context)
    {
        if (dataWrapper == null)
        {
            dataWrapper = new DataWrapper(context.getApplicationContext(), false, 0, false);
        }
    }
    */

    private static void doOnUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId, boolean fromOnUpdate)
    {
        //Bundle widgetIdOptions;
        //widgetIdOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        //boolean isLargeLayout = setLayoutParams(context, appWidgetManager, appWidgetId, widgetIdOptions);
        DataWrapper dataWrapper = new DataWrapper(context.getApplicationContext(), false, 0, false);
        RemoteViews widget = buildLayout(context, appWidgetId, /*isLargeLayout,*/ dataWrapper);
        try {
            appWidgetManager.updateAppWidget(appWidgetId, widget);
        } catch (Exception e) {
            PPApplication.recordException(e);
        }

        if (!fromOnUpdate) {
            //if (isLargeLayout) {
                if (!ApplicationPreferences.applicationWidgetListGridLayout)
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_profile_list);
                else
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_profile_grid);
            //}
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
//        PPApplication.logE("[IN_LISTENER] ProfileListWidgetProvider.onUpdate", "xxx");
        if (appWidgetIds.length > 0) {

            final Context _context = context;
            final AppWidgetManager _appWidgetManager = appWidgetManager;
            final int[] _appWidgetIds = appWidgetIds;

            PPApplication.startHandlerThreadWidget();
            final Handler handler = new Handler(PPApplication.handlerThreadWidget.getLooper());
            handler.post(() -> {
//                    PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadWidget", "START run - from=ProfileListWidgetProvider.onUpdate");
                //createProfilesDataWrapper(_context);

                for (int appWidgetId : _appWidgetIds) {
                    doOnUpdate(_context, _appWidgetManager, appWidgetId, true);
                }

                //if (dataWrapper != null)
                //    dataWrapper.invalidateDataWrapper();
                //dataWrapper = null;
            });
        }
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent); // calls onUpdate, is required for widget
//        PPApplication.logE("[IN_BROADCAST] ProfileListWidgetProvider.onReceive", "xxx");

        final String action = intent.getAction();

        if (action != null) {
            if (action.equalsIgnoreCase("com.motorola.blur.home.ACTION_SET_WIDGET_SIZE")) {
                //final int spanX = intent.getIntExtra("spanX", 1);
                //final int spanY = intent.getIntExtra("spanY", 1);
                final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ProfileListWidgetProvider.class));

                if ((appWidgetIds != null) && (appWidgetIds.length > 0)) {
                    PPApplication.startHandlerThreadWidget();
                    final Handler handler = new Handler(PPApplication.handlerThreadWidget.getLooper());
                    handler.post(() -> {
//                            PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadWidget", "START run - from=ProfileListWidgetProvider.onReceive (1)");
                        for (int appWidgetId : appWidgetIds) {
                            //boolean isLargeLayout = setLayoutParamsMotorola(context, spanX, spanY, appWidgetId);
                            RemoteViews layout;
                            DataWrapper dataWrapper = new DataWrapper(context.getApplicationContext(), false, 0, false);
                            layout = buildLayout(context, appWidgetId, /*isLargeLayout,*/ dataWrapper);
                            try {
                                appWidgetManager.updateAppWidget(appWidgetId, layout);
                            } catch (Exception e) {
                                PPApplication.recordException(e);
                            }
                        }
                    });
                }
            }
            else
            if (action.equalsIgnoreCase(ACTION_REFRESH_LISTWIDGET)) {
                final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ProfileListWidgetProvider.class));

                if ((appWidgetIds != null) && (appWidgetIds.length > 0)) {
                    PPApplication.startHandlerThreadWidget();
                    final Handler handler = new Handler(PPApplication.handlerThreadWidget.getLooper());
                    handler.post(() -> {
//                            PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadWidget", "START run - from=ProfileListWidgetProvider.onReceive (2)");
                        for (int appWidgetId : appWidgetIds) {
                            doOnUpdate(context, appWidgetManager, appWidgetId, false);
                        }
                    });
                }
            }
        }
    }

    /*
    private static boolean setLayoutParams(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId, Bundle widgetIdOptions)
    {
        String preferenceKey = "isLargeLayout_"+appWidgetId;

        boolean isLargeLayout;

        SharedPreferences preferences = ApplicationPreferences.getSharedPreferences(context);
        if (preferences.contains(preferenceKey))
            // is already saved, use it
            isLargeLayout = preferences.getBoolean(preferenceKey, true);
        else
        {
            // is not saved, compute it

            AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);

            int minHeight;
            if (widgetIdOptions != null)
            {
                //int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
                //int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
                minHeight = widgetIdOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
                //int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

                if ((minHeight == 0) && (appWidgetProviderInfo != null))
                {
                    minHeight = appWidgetProviderInfo.minHeight;
                }
            }
            else
            {
                if (appWidgetProviderInfo != null)
                    minHeight = appWidgetProviderInfo.minHeight;
                else
                    minHeight = 0;

                //if (minHeight == 0)
                //	return;
            }

            isLargeLayout = minHeight >= 110;

            Editor editor = preferences.edit();
            editor.putBoolean(preferenceKey, isLargeLayout);
            editor.apply();
        }

        return isLargeLayout;
        //return true;
    }
    */
    /*
    private static boolean setLayoutParamsMotorola(Context context, @SuppressWarnings("unused") int spanX, int spanY, int appWidgetId)
    {
        // for Motorola devices use spanY

        boolean isLargeLayout = spanY != 1;
        
        String preferenceKey = "isLargeLayout_"+appWidgetId;

        Editor editor = ApplicationPreferences.getEditor(context);
        editor.putBoolean(preferenceKey, isLargeLayout);
        editor.apply();

        return isLargeLayout;
        //return true;
    }
    */

    public void onAppWidgetOptionsChanged(final Context context, final AppWidgetManager appWidgetManager,
            final int appWidgetId, final Bundle newOptions)
    {
//        PPApplication.logE("[IN_LISTENER] ProfileListWidgetProvider.onAppWidgetOptionsChanged", "xxx");

        PPApplication.startHandlerThreadWidget();
        final Handler handler = new Handler(PPApplication.handlerThreadWidget.getLooper());
        handler.post(() -> {
//                PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadWidget", "START run - from=ProfileListWidgetProvider.onAppWidgetOptionsChanged");
            //createProfilesDataWrapper(context);

            String preferenceKey = "isLargeLayout_"+appWidgetId;

            // remove preference, will by computed in setLayoutParams
            Editor editor = ApplicationPreferences.getEditor(context);
            editor.remove(preferenceKey);
            editor.apply();


            updateAfterWidgetOptionsChanged(context, appWidgetId);

            //if (dataWrapper != null)
            //    dataWrapper.invalidateDataWrapper();
            //dataWrapper = null;
        });

        /*
        if (PPApplication.widgetHandler != null) {
            PPApplication.widgetHandler.post(new Runnable() {
                public void run() {
                    createProfilesDataWrapper(context);

                    String preferenceKey = "isLargeLayout_"+appWidgetId;
                    ApplicationPreferences.getSharedPreferences(context);

                    // remove preference, will by reset in setLayoutParams
                    Editor editor = ApplicationPreferences.preferences.edit();
                    editor.remove(preferenceKey);
                    editor.apply();


                    updateWidget(context, appWidgetId);

                    if (dataWrapper != null)
                        dataWrapper.invalidateDataWrapper();
                    dataWrapper = null;
                }
            });
        }
        */
    }

    private static void updateAfterWidgetOptionsChanged(Context context, int appWidgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        doOnUpdate(context, appWidgetManager, appWidgetId, false);
    }

    /*
    private static void _updateWidgets(Context context) {
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ProfileListWidgetProvider.class));

            if (appWidgetIds != null) {
                for (int appWidgetId : appWidgetIds) {
                    updateWidget(context, appWidgetId);
                }
            }
        } catch (Exception e) {
            PPApplication.recordException(e);
        }
    }
    */

    static void updateWidgets(final Context context/*, final boolean refresh*/) {
        //createProfilesDataWrapper(context);

        /*DataWrapper dataWrapper = new DataWrapper(context.getApplicationContext(), false, 0, false);
        Profile profile = dataWrapper.getActivatedProfile(false, false);
        //dataWrapper.getEventTimelineList(true);

        String pName;
        if (profile != null)
            pName = DataWrapper.getProfileNameWithManualIndicatorAsString(profile, true, "", true, false, false, dataWrapper);
        else
            pName = context.getResources().getString(R.string.profiles_header_profile_name_no_activated);

        if (!refresh) {
            String pNameWidget = PPApplication.prefWidgetProfileName3;

            if (!pNameWidget.isEmpty()) {
                if (pName.equals(pNameWidget)) {
                    //PPApplication.logE("ProfileListWidgetProvider.onUpdate", "activated profile NOT changed");
                    return;
                }
            }
        }

        PPApplication.setWidgetProfileName(context, 3, pName);*/

//        PPApplication.logE("[LOCAL_BROADCAST_CALL] ProfileListWidgetProvider.updateWidgets", "xxx");
        Intent intent3 = new Intent(ACTION_REFRESH_LISTWIDGET);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent3);

        //Intent intent = new Intent(context, ProfileListWidgetProvider.class);
        //intent.setAction(ACTION_REFRESH_LISTWIDGET);
        //context.sendBroadcast(intent);
        //_updateWidgets(context);

        //if (dataWrapper != null)
        //    dataWrapper.invalidateDataWrapper();
        //dataWrapper = null;
    }

}
