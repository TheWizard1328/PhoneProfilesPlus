package sk.henrichg.phoneprofilesplus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.CalendarContract.Instances;
import android.text.Html;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EventPreferencesCalendar extends EventPreferences {

    public String _calendars;
    public int _searchField;
    public String _searchString;
    public int _availability;
    public boolean _ignoreAllDayEvents;

    public long _startTime;
    public long _endTime;
    public boolean _eventFound;

    static final String PREF_EVENT_CALENDAR_ENABLED = "eventCalendarEnabled";
    static final String PREF_EVENT_CALENDAR_CALENDARS = "eventCalendarCalendars";
    static final String PREF_EVENT_CALENDAR_SEARCH_FIELD = "eventCalendarSearchField";
    static final String PREF_EVENT_CALENDAR_SEARCH_STRING = "eventCalendarSearchString";
    static final String PREF_EVENT_CALENDAR_AVAILABILITY = "eventCalendarAvailability";
    static final String PREF_EVENT_CALENDAR_IGNORE_ALL_DAY_EVENTS = "eventCalendarIgnoreAllDayEvents";

    static final String PREF_EVENT_CALENDAR_CATEGORY = "eventCalendarCategory";

    static final int SEARCH_FIELD_TITLE = 0;
    static final int SEARCH_FIELD_DESCRIPTION = 1;
    static final int SEARCH_FIELD_LOCATION = 2;

    static final int AVAILABILITY_NO_CHECK = 0;
    static final int AVAILABILITY_BUSY = 1;
    static final int AVAILABILITY_FREE = 2;
    static final int AVAILABILITY_TENTATIVE = 3;

    public EventPreferencesCalendar(Event event,
                                boolean enabled,
                                String calendars,
                                int searchField,
                                String searchString,
                                int availability,
                                boolean ignoreAllDayEvents)
    {
        super(event, enabled);

        this._calendars = calendars;
        this._searchField = searchField;
        this._searchString = searchString;
        this._availability = availability;
        this._ignoreAllDayEvents = ignoreAllDayEvents;

        this._startTime = 0;
        this._endTime = 0;
        this._eventFound = false;
    }

    @Override
    public void copyPreferences(Event fromEvent)
    {
        this._enabled = ((EventPreferencesCalendar)fromEvent._eventPreferencesCalendar)._enabled;
        this._calendars = ((EventPreferencesCalendar)fromEvent._eventPreferencesCalendar)._calendars;
        this._searchField = ((EventPreferencesCalendar)fromEvent._eventPreferencesCalendar)._searchField;
        this._searchString = ((EventPreferencesCalendar)fromEvent._eventPreferencesCalendar)._searchString;
        this._availability = ((EventPreferencesCalendar)fromEvent._eventPreferencesCalendar)._availability;
        this._ignoreAllDayEvents = ((EventPreferencesCalendar)fromEvent._eventPreferencesCalendar)._ignoreAllDayEvents;

        this._startTime = 0;
        this._endTime = 0;
        this._eventFound = false;
    }

    @Override
    public void loadSharedPreferences(SharedPreferences preferences)
    {
        Editor editor = preferences.edit();
        editor.putBoolean(PREF_EVENT_CALENDAR_ENABLED, _enabled);
        editor.putString(PREF_EVENT_CALENDAR_CALENDARS, _calendars);
        editor.putString(PREF_EVENT_CALENDAR_SEARCH_FIELD, String.valueOf(_searchField));
        editor.putString(PREF_EVENT_CALENDAR_SEARCH_STRING, _searchString);
        editor.putString(PREF_EVENT_CALENDAR_AVAILABILITY, String.valueOf(_availability));
        editor.putBoolean(PREF_EVENT_CALENDAR_IGNORE_ALL_DAY_EVENTS, _ignoreAllDayEvents);
        editor.commit();
    }

    @Override
    public void saveSharedPreferences(SharedPreferences preferences)
    {
        this._enabled = preferences.getBoolean(PREF_EVENT_CALENDAR_ENABLED, false);
        this._calendars = preferences.getString(PREF_EVENT_CALENDAR_CALENDARS, "");
        this._searchField = Integer.parseInt(preferences.getString(PREF_EVENT_CALENDAR_SEARCH_FIELD, "0"));
        this._searchString = preferences.getString(PREF_EVENT_CALENDAR_SEARCH_STRING, "");
        this._availability = Integer.parseInt(preferences.getString(PREF_EVENT_CALENDAR_AVAILABILITY, "0"));
        this._ignoreAllDayEvents = preferences.getBoolean(PREF_EVENT_CALENDAR_IGNORE_ALL_DAY_EVENTS, false);

        this._startTime = 0;
        this._endTime = 0;
        this._eventFound = false;
    }

    @Override
    public String getPreferencesDescription(boolean addBullet, Context context)
    {
        String descr = "";

        if (!this._enabled)
        {
            //descr = descr + context.getString(R.string.event_type_calendar) + ": ";
            //descr = descr + context.getString(R.string.event_preferences_not_enabled);
        }
        else
        {
            if (addBullet) {
                descr = descr + "<b>\u2022 </b>";
                descr = descr + "<b>" + context.getString(R.string.event_type_calendar) + ": " + "</b>";
            }

            String[] searchFields = context.getResources().getStringArray(R.array.eventCalendarSearchFieldArray);
            descr = descr + searchFields[this._searchField] + "; ";

            descr = descr + "\"" + this._searchString + "\""  + "; ";

            if (this._ignoreAllDayEvents)
                descr = descr + context.getString(R.string.event_preferences_calendar_ignore_all_day_events) + "; ";

            String[] availabilities = context.getResources().getStringArray(R.array.eventCalendarAvailabilityArray);
            descr = descr + availabilities[this._availability];

            //Calendar calendar = Calendar.getInstance();

            if (addBullet) {
                if (GlobalData.getGlobalEventsRuning(context)) {
                    if (_eventFound) {
                        long alarmTime;
                        //SimpleDateFormat sdf = new SimpleDateFormat("EEd/MM/yy HH:mm");
                        String alarmTimeS = "";
                        if (_event.getStatus() == Event.ESTATUS_PAUSE) {
                            alarmTime = computeAlarm(true);
                            // date and time format by user system settings configuration
                            alarmTimeS = "(st) " + DateFormat.getDateFormat(context).format(alarmTime) +
                                    " " + DateFormat.getTimeFormat(context).format(alarmTime);
                            descr = descr + "<br>"; //'\n';
                            descr = descr + "&nbsp;&nbsp;&nbsp;-> " + alarmTimeS;
                        } else if (_event.getStatus() == Event.ESTATUS_RUNNING) {
                            alarmTime = computeAlarm(false);
                            // date and time format by user system settings configuration
                            alarmTimeS = "(et) " + DateFormat.getDateFormat(context).format(alarmTime) +
                                    " " + DateFormat.getTimeFormat(context).format(alarmTime);
                            descr = descr + "<br>"; //'\n';
                            descr = descr + "&nbsp;&nbsp;&nbsp;-> " + alarmTimeS;
                        }
                    } else {
                        descr = descr + "<br>"; //'\n';
                        descr = descr + "&nbsp;&nbsp;&nbsp;-> " + context.getResources().getString(R.string.event_preferences_calendar_no_event);
                    }
                }
            }
        }

        return descr;
    }

    @Override
    public void setSummary(PreferenceManager prefMng, String key, String value, Context context)
    {
        if (key.equals(PREF_EVENT_CALENDAR_CALENDARS))
        {
            Preference preference = prefMng.findPreference(key);
            if (preference != null) {
                GUIData.setPreferenceTitleStyle(preference, false, true, false);
            }
        }
        if (key.equals(PREF_EVENT_CALENDAR_SEARCH_FIELD) ||
            key.equals(PREF_EVENT_CALENDAR_AVAILABILITY))
        {
            ListPreference listPreference = (ListPreference)prefMng.findPreference(key);
            if (listPreference != null) {
                int index = listPreference.findIndexOfValue(value);
                CharSequence summary = (index >= 0) ? listPreference.getEntries()[index] : null;
                listPreference.setSummary(summary);
            }
        }
        if (key.equals(PREF_EVENT_CALENDAR_SEARCH_STRING))
        {
            Preference preference = prefMng.findPreference(key);
            if (preference != null) {
                preference.setSummary(value);
                String helpString = context.getString(R.string.pref_dlg_info_about_wildcards_1) + " " +
                        context.getString(R.string.pref_dlg_info_about_wildcards_2) + " " +
                        context.getString(R.string.calendar_pref_dlg_info_about_wildcards) + " " +
                        context.getString(R.string.pref_dlg_info_about_wildcards_3);
                ((EditTextPreference) preference).setDialogMessage(helpString);
                GUIData.setPreferenceTitleStyle(preference, false, true, false);
            }
        }
    }

    @Override
    public void setSummary(PreferenceManager prefMng, String key, SharedPreferences preferences, Context context)
    {
        if (key.equals(PREF_EVENT_CALENDAR_CALENDARS) ||
            key.equals(PREF_EVENT_CALENDAR_SEARCH_FIELD) ||
            key.equals(PREF_EVENT_CALENDAR_SEARCH_STRING) ||
            key.equals(PREF_EVENT_CALENDAR_AVAILABILITY))
        {
            setSummary(prefMng, key, preferences.getString(key, ""), context);
        }
    }

    @Override
    public void setAllSummary(PreferenceManager prefMng, SharedPreferences preferences, Context context)
    {
        setSummary(prefMng, PREF_EVENT_CALENDAR_CALENDARS, preferences, context);
        setSummary(prefMng, PREF_EVENT_CALENDAR_SEARCH_FIELD, preferences, context);
        setSummary(prefMng, PREF_EVENT_CALENDAR_SEARCH_STRING, preferences, context);
        setSummary(prefMng, PREF_EVENT_CALENDAR_AVAILABILITY, preferences, context);
    }

    @Override
    public void setCategorySummary(PreferenceManager prefMng, String key, SharedPreferences preferences, Context context) {
        if (GlobalData.isEventPreferenceAllowed(PREF_EVENT_CALENDAR_ENABLED, context) == GlobalData.PREFERENCE_ALLOWED) {
            EventPreferencesCalendar tmp = new EventPreferencesCalendar(this._event, this._enabled, this._calendars, this._searchField, this._searchString, this._availability, this._ignoreAllDayEvents);
            if (preferences != null)
                tmp.saveSharedPreferences(preferences);

            Preference preference = prefMng.findPreference(PREF_EVENT_CALENDAR_CATEGORY);
            if (preference != null) {
                GUIData.setPreferenceTitleStyle(preference, tmp._enabled, false, !tmp.isRunnable(context));
                preference.setSummary(Html.fromHtml(tmp.getPreferencesDescription(false, context)));
            }
        }
        else {
            Preference preference = prefMng.findPreference(PREF_EVENT_CALENDAR_CATEGORY);
            if (preference != null) {
                preference.setSummary(context.getResources().getString(R.string.profile_preferences_device_not_allowed)+
                        ": "+context.getResources().getString(GlobalData.getNotAllowedPreferenceReasonString()));
                preference.setEnabled(false);
            }
        }
    }

    @Override
    public boolean isRunnable(Context context)
    {

        boolean runable = super.isRunnable(context);

        runable = runable && (!_calendars.isEmpty());
        runable = runable && (!_searchString.isEmpty());

        return runable;
    }

    @Override
    public boolean activateReturnProfile()
    {
        return true;
    }

    public long computeAlarm(boolean startEvent)
    {
        GlobalData.logE("EventPreferencesCalendar.computeAlarm","startEvent="+startEvent);

        ///// set calendar for startTime and endTime
        Calendar calStartTime = Calendar.getInstance();
        Calendar calEndTime = Calendar.getInstance();

        int gmtOffset = TimeZone.getDefault().getRawOffset();

        calStartTime.setTimeInMillis(_startTime - gmtOffset);
        calStartTime.set(Calendar.SECOND, 0);
        calStartTime.set(Calendar.MILLISECOND, 0);

        calEndTime.setTimeInMillis(_endTime - gmtOffset);
        calEndTime.set(Calendar.SECOND, 0);
        calEndTime.set(Calendar.MILLISECOND, 0);

        long alarmTime;
        if (startEvent)
            alarmTime = calStartTime.getTimeInMillis();
        else
            alarmTime = calEndTime.getTimeInMillis();

        return alarmTime;

    }

    @Override
    public void setSystemEventForStart(Context context)
    {
        // set alarm for state PAUSE

        // this alarm generates broadcast, that change state into RUNNING;
        // from broadcast will by called EventsService


        removeAlarm(true, context);
        removeAlarm(false, context);

        searchEvent(context);

        if (!(isRunnable(context) && _enabled && _eventFound))
            return;

        setAlarm(true, computeAlarm(true), context);
    }

    @Override
    public void setSystemEventForPause(Context context)
    {
        // set alarm for state RUNNING

        // this alarm generates broadcast, that change state into PAUSE;
        // from broadcast will by called EventsService

        removeAlarm(true, context);
        removeAlarm(false, context);

        searchEvent(context);

        if (!(isRunnable(context) && _enabled && _eventFound))
            return;

        setAlarm(false, computeAlarm(false), context);
    }

    @Override
    public void removeSystemEvent(Context context)
    {
        // remove alarms for state STOP

        removeAlarm(true, context);
        removeAlarm(false, context);

        _eventFound = false;

        GlobalData.logE("EventPreferencesCalendar.removeSystemEvent", "xxx");
    }

    public void removeAlarm(boolean startEvent, Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);

        Intent intent = new Intent(context, EventCalendarBroadcastReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int) _event._id, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null)
        {
            GlobalData.logE("EventPreferencesCalendar.removeAlarm","alarm found");

            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    @SuppressLint({"SimpleDateFormat", "NewApi"})
    private void setAlarm(boolean startEvent, long alarmTime, Context context)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EE d.MM.yyyy HH:mm:ss:S");
        String result = sdf.format(alarmTime);
        if (startEvent)
            GlobalData.logE("EventPreferencesCalendar.setAlarm","startTime="+result);
        else
            GlobalData.logE("EventPreferencesCalendar.setAlarm","endTime="+result);

        Intent intent = new Intent(context, EventCalendarBroadcastReceiver.class);

        //intent.putExtra(GlobalData.EXTRA_EVENT_ID, _event._id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int) _event._id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);

        if (GlobalData.exactAlarms && (android.os.Build.VERSION.SDK_INT >= 23))
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime+GlobalData.EVENT_ALARM_TIME_OFFSET, pendingIntent);
        else
        if (GlobalData.exactAlarms && (android.os.Build.VERSION.SDK_INT >= 19))
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime+GlobalData.EVENT_ALARM_TIME_OFFSET, pendingIntent);
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime+GlobalData.EVENT_ALARM_TIME_OFFSET, pendingIntent);
    }

    public void searchEvent(Context context)
    {
        if (!(isRunnable(context) && _enabled && Permissions.checkCalendar(context)))
        {
            _startTime = 0;
            _endTime = 0;
            _eventFound = false;
            DatabaseHandler.getInstance(context).updateEventCalendarTimes(_event);
            return;
        }

        final String[] INSTANCE_PROJECTION = new String[] {
                Instances.BEGIN,           // 0
                Instances.END,			   // 1
                Instances.TITLE,           // 2
                Instances.DESCRIPTION,     // 3
                Instances.CALENDAR_ID,     // 4
                Instances.ALL_DAY,         // 5
                Instances.EVENT_LOCATION,  // 6
                Instances.AVAILABILITY/*,  // 7
            Instances.EVENT_TIMEZONE   // 8 */
        };

        // The indices for the projection array above.
        final int PROJECTION_BEGIN_INDEX = 0;
        final int PROJECTION_END_INDEX = 1;
        final int PROJECTION_TITLE_INDEX = 2;
        //final int PROJECTION_DESCRIPTION_INDEX = 3;
        final int PROJECTION_CALENDAR_ID_INDEX = 4;
        final int PROJECTION_ALL_DAY_INDEX = 5;
        //final int PROJECTION_EVENT_TIMEZONE_INDEX = 6;

        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();

        String selection =  "(    ";

        switch (_searchField) {
            case SEARCH_FIELD_TITLE:
                selection = selection +
                        "     (lower(" + Instances.TITLE + ")" + " LIKE lower(?) ESCAPE '\\')";
                break;
            case SEARCH_FIELD_DESCRIPTION:
                selection = selection +
                        "     (lower(" + Instances.DESCRIPTION + ")" + " LIKE lower(?) ESCAPE '\\')";
                break;
            case SEARCH_FIELD_LOCATION:
                selection = selection +
                        "     (lower(" + Instances.EVENT_LOCATION + ")" + " LIKE lower(?) ESCAPE '\\')";
                break;
        }

        switch (_availability) {
            case AVAILABILITY_BUSY:
                selection = selection +
                        " AND (" + Instances.AVAILABILITY + "=" + Instances.AVAILABILITY_BUSY + ")";
                break;
            case AVAILABILITY_FREE:
                selection = selection +
                        " AND (" + Instances.AVAILABILITY + "=" + Instances.AVAILABILITY_FREE + ")";
                break;
            case AVAILABILITY_TENTATIVE:
                selection = selection +
                        " AND (" + Instances.AVAILABILITY + "=" + Instances.AVAILABILITY_TENTATIVE + ")";
                break;
        }
        selection = selection + ")";

        //Log.e("** EventPrefCalendar", "_searchField="+_searchField);
        //Log.e("** EventPrefCalendar", "selection="+selection);

        // Construct the query with the desired date range.
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        long startMillis = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, 32);
        long endMillis = calendar.getTimeInMillis();

        Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        _eventFound = false;
        _startTime = 0;
        _endTime = 0;

        String[] splits = _calendars.split("\\|");

        String searchPattern = _searchString;
        // when in searchPattern are not widcards add %
        if (!(searchPattern.contains("%") || searchPattern.contains("_")))
            searchPattern = "%"+searchPattern+"%";
        String[] selectionArgs = new String[] { searchPattern };

        // Submit the query
        cur =  cr.query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, Instances.BEGIN + " ASC");

        if (cur != null)
        {
            while (cur.moveToNext()) {

                boolean calendarFound = false;
                for (int i = 0; i < splits.length; i++) {
                    long calendarId = Long.parseLong(splits[i]);
                    if (cur.getLong(PROJECTION_CALENDAR_ID_INDEX) == calendarId) {
                        calendarFound = true;
                    }
                }
                if (!calendarFound)
                    continue;

                if ((cur.getInt(PROJECTION_ALL_DAY_INDEX) == 1) && this._ignoreAllDayEvents)
                    continue;

                long beginVal = 0;
                long endVal = 0;
                //String title = null;

                //Log.e("** EventPrefCalendar", "title="+cur.getString(PROJECTION_TITLE_INDEX));

                // Get the field values
                beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
                endVal = cur.getLong(PROJECTION_END_INDEX);

                if (cur.getInt(PROJECTION_ALL_DAY_INDEX) == 1)
                {
                    // get UTC offset
                    Date _now = new Date();
                    int utcOffset = TimeZone.getDefault().getOffset(_now.getTime());

                    beginVal -= utcOffset;
                    endVal -= utcOffset;
                }

                //title = cur.getString(PROJECTION_TITLE_INDEX);

                int gmtOffset = TimeZone.getDefault().getRawOffset();

                if ((beginVal <= now) && (endVal > now))
                {
                    // event instance is found
                    _eventFound = true;
                    _startTime = beginVal + gmtOffset;
                    _endTime = endVal + gmtOffset;
                    //Log.e("** EventPrefCalendar", "beginVal="+getDate(_startTime));
                    //Log.e("** EventPrefCalendar", "endVal="+getDate(_endTime));
                    break;
                }
                else
                if (beginVal > now)
                {
                    // event instance is found
                    _eventFound = true;
                    _startTime = beginVal + gmtOffset;
                    _endTime = endVal + gmtOffset;
                    //Log.e("** EventPrefCalendar", "beginVal="+getDate(_startTime));
                    //Log.e("** EventPrefCalendar", "endVal="+getDate(_endTime));
                    break;
                }

            }

            cur.close();
        }

        DatabaseHandler.getInstance(context).updateEventCalendarTimes(_event);

    }

    public void saveStartEndTime(DataWrapper dataWrapper) {
        _event._eventPreferencesCalendar.searchEvent(dataWrapper.context);
        if (_event.getStatus() == Event.ESTATUS_RUNNING)
            _event._eventPreferencesCalendar.setSystemEventForPause(dataWrapper.context);
        if (_event.getStatus() == Event.ESTATUS_PAUSE)
            _event._eventPreferencesCalendar.setSystemEventForStart(dataWrapper.context);
    }

}
