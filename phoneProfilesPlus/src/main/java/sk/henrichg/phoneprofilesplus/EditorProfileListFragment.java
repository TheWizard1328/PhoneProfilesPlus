package sk.henrichg.phoneprofilesplus;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

//import me.drakeet.support.toast.ToastCompat;

public class EditorProfileListFragment extends Fragment
                                        implements OnStartDragItemListener {

    public DataWrapper activityDataWrapper;

    private View rootView;
    private RelativeLayout activatedProfileHeader;
    RecyclerView listView;
    private TextView activeProfileName;
    private ImageView activeProfileIcon;
    private Toolbar bottomToolbar;
    TextView textViewNoData;
    private LinearLayout progressBar;
    private ImageView profilePrefIndicatorImageView;

    private EditorProfileListAdapter profileListAdapter;
    private ItemTouchHelper itemTouchHelper;

    private WeakReference<LoadProfileListAsyncTask> asyncTaskContext;

    Profile scrollToProfile = null;

    //private ValueAnimator hideAnimator;
    //private ValueAnimator showAnimator;
    //private int headerHeight;

    static final int EDIT_MODE_UNDEFINED = 0;
    static final int EDIT_MODE_INSERT = 1;
    static final int EDIT_MODE_DUPLICATE = 2;
    static final int EDIT_MODE_EDIT = 3;
    static final int EDIT_MODE_DELETE = 4;

    static final String FILTER_TYPE_ARGUMENT = "filter_type";
    static final String START_TARGET_HELPS_ARGUMENT = "start_target_helps";

    static final int FILTER_TYPE_ALL = 0;
    static final int FILTER_TYPE_SHOW_IN_ACTIVATOR = 1;
    static final int FILTER_TYPE_NO_SHOW_IN_ACTIVATOR = 2;

    public boolean targetHelpsSequenceStarted;
    public static final String PREF_START_TARGET_HELPS = "editor_profile_list_fragment_start_target_helps";
    public static final String PREF_START_TARGET_HELPS_FILTER_SPINNER = "editor_profile_activity_start_target_helps_filter_spinner";

    private int filterType = FILTER_TYPE_ALL;

    /**
     * The fragment's current callback objects
     */
    private OnStartProfilePreferences onStartProfilePreferencesCallback = sDummyOnStartProfilePreferencesCallback;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified.
     */
    // invoked when start profile preference fragment/activity needed
    interface OnStartProfilePreferences {
        void onStartProfilePreferences(Profile profile, int editMode, int predefinedProfileIndex/*, boolean startTargetHelps*/);
    }

    /*, boolean startTargetHelps*/
    /**
     * A dummy implementation of the Callbacks interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static final OnStartProfilePreferences sDummyOnStartProfilePreferencesCallback = (profile, editMode, predefinedProfileIndex) -> {
    };

    public EditorProfileListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        onStartProfilePreferencesCallback = (OnStartProfilePreferences) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        onStartProfilePreferencesCallback = sDummyOnStartProfilePreferencesCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // this is really important in order to save the state across screen
        // configuration changes for example
        //noinspection deprecation
        setRetainInstance(true);

        filterType = getArguments() != null ? 
                getArguments().getInt(FILTER_TYPE_ARGUMENT, EditorProfileListFragment.FILTER_TYPE_ALL) :
                    EditorProfileListFragment.FILTER_TYPE_ALL;

        //noinspection ConstantConditions
        activityDataWrapper = new DataWrapper(getActivity().getApplicationContext(), false, 0, false);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.editor_profile_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doOnViewCreated(view, true);

        boolean startTargetHelps = getArguments() != null && getArguments().getBoolean(START_TARGET_HELPS_ARGUMENT, false);
        if (startTargetHelps)
            showTargetHelps();
    }

    @SuppressLint("InflateParams")
    private void doOnViewCreated(View view, boolean fromOnViewCreated)
    {
        profilePrefIndicatorImageView = view.findViewById(R.id.activated_profile_pref_indicator);
        if (!ApplicationPreferences.applicationEditorPrefIndicator)
            profilePrefIndicatorImageView.setVisibility(GONE);

        activeProfileName = view.findViewById(R.id.activated_profile_name);
        activeProfileIcon = view.findViewById(R.id.activated_profile_icon);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listView = view.findViewById(R.id.editor_profiles_list);
        //listView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        activatedProfileHeader = view.findViewById(R.id.activated_profile_header);
        bottomToolbar = view.findViewById(R.id.editor_list_bottom_bar);

        //noinspection ConstantConditions
        if (GlobalGUIRoutines.areSystemAnimationsEnabled(getActivity().getApplicationContext())) {
            /*if (activatedProfileHeader != null) {
                Handler handler = new Handler(getActivity().getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() == null)
                            return;

                        headerHeight = activatedProfileHeader.getMeasuredHeight();
                        Log.e("EditorProfileListFragment.doOnViewCreated", "headerHeight="+headerHeight);
                        hideAnimator = ValueAnimator.ofInt(headerHeight / 4, 0);
                        hideAnimator.setDuration(500);
                        hideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int val = (Integer) valueAnimator.getAnimatedValue();
                                //Log.e("hideAnimator.onAnimationUpdate", "val="+val);
                                ViewGroup.LayoutParams layoutParams = activatedProfileHeader.getLayoutParams();
                                layoutParams.height = val * 4;
                                activatedProfileHeader.setLayoutParams(layoutParams);
                            }
                        });
                        showAnimator = ValueAnimator.ofInt(0, headerHeight / 4);
                        showAnimator.setDuration(500);
                        showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int val = (Integer) valueAnimator.getAnimatedValue();
                                //Log.e("showAnimator.onAnimationUpdate", "val="+val);
                                ViewGroup.LayoutParams layoutParams = activatedProfileHeader.getLayoutParams();
                                layoutParams.height = val * 4;
                                activatedProfileHeader.setLayoutParams(layoutParams);
                            }
                        });

                    }
                }, 200);

            }*/

            if (ApplicationPreferences.applicationEditorHideHeaderOrBottomBar) {
                final LayoutTransition layoutTransition = ((ViewGroup) view.findViewById(R.id.layout_profiles_list_fragment))
                        .getLayoutTransition();
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

                listView.addOnScrollListener(new HidingRecyclerViewScrollListener() {
                    @Override
                    public void onHide() {
                /*if ((activatedProfileHeader.getMeasuredHeight() >= headerHeight - 4) &&
                    (activatedProfileHeader.getMeasuredHeight() <= headerHeight + 4))
                    hideAnimator.start();*/
                        if (!layoutTransition.isRunning()) {
                            //final int firstVisibleItem = ((LinearLayoutManager) listView.getLayoutManager()).findFirstVisibleItemPosition();
                            //if (firstVisibleItem != 0)
                            activatedProfileHeader.setVisibility(GONE);

                            bottomToolbar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onShow() {
                    /*if (activatedProfileHeader.getMeasuredHeight() == 0)
                        showAnimator.start();*/
                        if (!layoutTransition.isRunning()) {
                            //final int firstVisibleItem = ((LinearLayoutManager) listView.getLayoutManager()).findFirstVisibleItemPosition();
                            //if (firstVisibleItem == 0)
                            activatedProfileHeader.setVisibility(View.VISIBLE);

                            bottomToolbar.setVisibility(GONE);
                        }
                    }
                });
            }
            else
                showHeaderAndBottomToolbar();
        }

        textViewNoData = view.findViewById(R.id.editor_profiles_list_empty);
        progressBar = view.findViewById(R.id.editor_profiles_list_linla_progress);

        final Activity activity = getActivity();
        final EditorProfileListFragment fragment = this;

        Menu menu = bottomToolbar.getMenu();
        if (menu != null) menu.clear();
        bottomToolbar.inflateMenu(R.menu.editor_profiles_bottom_bar);
        bottomToolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_add_profile) {
                if (profileListAdapter != null) {
                    if (!activity.isFinishing()) {
                        ((EditorProfilesActivity) activity).addProfileDialog = new AddProfileDialog(activity, fragment);
                        ((EditorProfilesActivity) activity).addProfileDialog.show();
                    }
                }
                return true;
            }
            else
            if (itemId == R.id.menu_delete_all_profiles) {
                deleteAllProfiles();
                return true;
            }
            else
            if (itemId == R.id.menu_default_profile) {
                Intent intent = new Intent(activity, PhoneProfilesPrefsActivity.class);
                intent.putExtra(PhoneProfilesPrefsActivity.EXTRA_SCROLL_TO, "profileActivationCategoryRoot");
                startActivity(intent);
                return true;
            }
            else
                return false;
        });

        if (fromOnViewCreated) {
            synchronized (activityDataWrapper.profileList) {
                if (!activityDataWrapper.profileListFilled) {
                    LoadProfileListAsyncTask asyncTask = new LoadProfileListAsyncTask(this, filterType);
                    this.asyncTaskContext = new WeakReference<>(asyncTask);
                    asyncTask.execute();
                } else {
                    if (profileListAdapter != null) {
                        listView.setAdapter(profileListAdapter);
                        // update activity for activated profile
                        Profile profile = activityDataWrapper.getActivatedProfile(true,
                                ApplicationPreferences.applicationEditorPrefIndicator);
                        updateHeader(profile);
                        listView.getRecycledViewPool().clear(); // maybe fix for java.lang.IndexOutOfBoundsException: Inconsistency detected.
                    }
                    else {
                        if (filterType != EditorProfileListFragment.FILTER_TYPE_SHOW_IN_ACTIVATOR)
                            EditorProfileListFragment.sortAlphabetically(activityDataWrapper.profileList);
                        else
                            EditorProfileListFragment.sortByPOrder(activityDataWrapper.profileList);
                        // update activity for activated profile
                        Profile profile = activityDataWrapper.getActivatedProfile(true,
                                ApplicationPreferences.applicationEditorPrefIndicator);
                        updateHeader(profile);

                        listView.getRecycledViewPool().clear(); // maybe fix for java.lang.IndexOutOfBoundsException: Inconsistency detected.

                        profileListAdapter = new EditorProfileListAdapter(fragment, activityDataWrapper, filterType, fragment);

                        // added touch helper for drag and drop items
                        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(profileListAdapter, false, false);
                        itemTouchHelper = new ItemTouchHelper(callback);
                        itemTouchHelper.attachToRecyclerView(listView);

                        listView.setAdapter(profileListAdapter);
                    }
                    profileListAdapter.notifyDataSetChanged(false);
                }
            }
        }
        else {
            synchronized (activityDataWrapper.profileList) {
                if (filterType != EditorProfileListFragment.FILTER_TYPE_SHOW_IN_ACTIVATOR)
                    EditorProfileListFragment.sortAlphabetically(activityDataWrapper.profileList);
                else
                    EditorProfileListFragment.sortByPOrder(activityDataWrapper.profileList);
                // update activity for activated profile
                Profile profile = activityDataWrapper.getActivatedProfile(true,
                        ApplicationPreferences.applicationEditorPrefIndicator);
                updateHeader(profile);

                fragment.listView.getRecycledViewPool().clear(); // maybe fix for java.lang.IndexOutOfBoundsException: Inconsistency detected.

                fragment.profileListAdapter = new EditorProfileListAdapter(fragment, fragment.activityDataWrapper, filterType, fragment);

                // added touch helper for drag and drop items
                ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(fragment.profileListAdapter, false, false);
                fragment.itemTouchHelper = new ItemTouchHelper(callback);
                fragment.itemTouchHelper.attachToRecyclerView(fragment.listView);

                fragment.listView.setAdapter(fragment.profileListAdapter);

                int profilePos = ListView.INVALID_POSITION;
                if (scrollToProfile != null) {
                    profilePos = profileListAdapter.getItemPosition(scrollToProfile);
                    scrollToProfile = null;
                }

                profileListAdapter.notifyDataSetChanged(false);

                if (profilePos != ListView.INVALID_POSITION)
                {
                    if (listView != null) {
                        // set profile visible in list
                        //int last = listView.getLastVisiblePosition();
                        //int first = listView.getFirstVisiblePosition();
                        //if ((profilePos <= first) || (profilePos >= last)) {
                        //    listView.setSelection(profilePos);
                        //}
                        RecyclerView.LayoutManager lm = listView.getLayoutManager();
                        if (lm != null)
                            lm.scrollToPosition(profilePos);
                    }
                }

            }
        }

    }

    void changeFragmentFilter(int profilesFilterType, boolean startTargetHelps) {
        filterType = profilesFilterType;

        doOnViewCreated(rootView, false);

        if (startTargetHelps)
            showTargetHelps();
    }

    private static class LoadProfileListAsyncTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<EditorProfileListFragment> fragmentWeakRef;
        private final DataWrapper _dataWrapper;
        //private final Context _baseContext;
        private final int _filterType;
        boolean defaultProfilesGenerated = false;
        //boolean defaultEventsGenerated = false;

        final boolean applicationEditorPrefIndicator;

        Handler progressBarHandler;
        Runnable progressBarRunnable;

        public LoadProfileListAsyncTask (EditorProfileListFragment fragment, int filterType) {
            fragmentWeakRef = new WeakReference<>(fragment);
            _filterType = filterType;
            //noinspection ConstantConditions
            _dataWrapper = new DataWrapper(fragment.getActivity().getApplicationContext(), false, 0, false);
            //_baseContext = fragment.getActivity();

            applicationEditorPrefIndicator = ApplicationPreferences.applicationEditorPrefIndicator;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            final EditorProfileListFragment fragment = this.fragmentWeakRef.get();

            if ((fragment != null) && (fragment.isAdded())) {
                progressBarHandler = new Handler(_dataWrapper.context.getMainLooper());
                progressBarRunnable = () -> {
//                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=EditorProfileListFragment.LoadProfileListAsyncTask");
                    //fragment.textViewNoData.setVisibility(GONE);
                    fragment.progressBar.setVisibility(View.VISIBLE);
                };
                progressBarHandler.postDelayed(progressBarRunnable, 100);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            _dataWrapper.fillProfileList(true, applicationEditorPrefIndicator);
            if ((_dataWrapper.profileList.size() == 0) && PPApplication.restoreFinished)
            {
                if (ApplicationPreferences.getSharedPreferences(_dataWrapper.context).getBoolean(ApplicationPreferences.PREF_EDITOR_PROFILES_FIRST_START, true)) {
                    // no profiles in DB, generate default profiles
                    // PPApplication.restoreFinished = Google auto-backup finished
                    EditorProfileListFragment fragment = this.fragmentWeakRef.get();
                    if ((fragment != null) && (fragment.getActivity() != null)) {
                        _dataWrapper.fillPredefinedProfileList(true, applicationEditorPrefIndicator, fragment.getActivity());
                        defaultProfilesGenerated = true;
                    }
                }
            }

            SharedPreferences sharedPreferences = ApplicationPreferences.getSharedPreferences(_dataWrapper.context);
            if (sharedPreferences != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ApplicationPreferences.PREF_EDITOR_PROFILES_FIRST_START, false);
                editor.apply();
            }

            // sort list
            if (_filterType != EditorProfileListFragment.FILTER_TYPE_SHOW_IN_ACTIVATOR)
                EditorProfileListFragment.sortAlphabetically(_dataWrapper.profileList);
            else
                EditorProfileListFragment.sortByPOrder(_dataWrapper.profileList);

            return null;
        }

        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            
            EditorProfileListFragment fragment = fragmentWeakRef.get();
            
            if ((fragment != null) && (fragment.isAdded())) {
                progressBarHandler.removeCallbacks(progressBarRunnable);
                fragment.progressBar.setVisibility(GONE);

                // get local profileList
                _dataWrapper.fillProfileList(true, applicationEditorPrefIndicator);
                // set local profile list into activity dataWrapper
                fragment.activityDataWrapper.copyProfileList(_dataWrapper);

                synchronized (fragment.activityDataWrapper.profileList) {
                    if (fragment.activityDataWrapper.profileList.size() == 0)
                        fragment.textViewNoData.setVisibility(View.VISIBLE);
                }

                fragment.profileListAdapter = new EditorProfileListAdapter(fragment, fragment.activityDataWrapper, _filterType, fragment);

                // added touch helper for drag and drop items
                ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(fragment.profileListAdapter, false, false);
                fragment.itemTouchHelper = new ItemTouchHelper(callback);
                fragment.itemTouchHelper.attachToRecyclerView(fragment.listView);

                fragment.listView.setAdapter(fragment.profileListAdapter);

                // update activity for activated profile
                fragment.listView.getRecycledViewPool().clear(); // maybe fix for java.lang.IndexOutOfBoundsException: Inconsistency detected.

                Profile profile = fragment.activityDataWrapper.getActivatedProfile(true,
                                applicationEditorPrefIndicator);
                fragment.updateHeader(profile);
                fragment.profileListAdapter.notifyDataSetChanged(false);
                //if (!ApplicationPreferences.applicationEditorHeader(_dataWrapper.context))
                //    fragment.setProfileSelection(profile);

                if (defaultProfilesGenerated) {
                    //PPApplication.logE("ActivateProfileHelper.updateGUI", "from EditorProfileListFragment.LoadProfileListAsyncTask");
                    //PPApplication.logE("###### PPApplication.updateGUI", "from=EditorProfileListFragment.LoadProfileListAsyncTask.onPostExecute");
                    PPApplication.updateGUI(0, _dataWrapper.context/*, true, true*/);
                    if ((fragment.getActivity() != null) && (!fragment.getActivity().isFinishing()))
                        PPApplication.showToast(_dataWrapper.context.getApplicationContext(),
                                fragment.getResources().getString(R.string.toast_predefined_profiles_generated),
                                Toast.LENGTH_SHORT);
                }
                /*if (defaultEventsGenerated)
                {
                    Toast msg = ToastCompat.makeText(_dataWrapper.context.getApplicationContext(),
                            fragment.getResources().getString(R.string.toast_predefined_events_generated),
                            Toast.LENGTH_SHORT);
                    msg.show();
                }*/
            }
        }
    }

    boolean isAsyncTaskPendingOrRunning() {
        try {
            return this.asyncTaskContext != null &&
                    this.asyncTaskContext.get() != null &&
                    !this.asyncTaskContext.get().getStatus().equals(AsyncTask.Status.FINISHED);
        } catch (Exception e) {
            return false;
        }
    }

    void stopRunningAsyncTask() {
        this.asyncTaskContext.get().cancel(true);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (isAsyncTaskPendingOrRunning()) {
            stopRunningAsyncTask();
        }

        if (listView != null)
            listView.setAdapter(null);
        if (profileListAdapter != null)
            profileListAdapter.release();

        //if (activityDataWrapper != null)
        //    activityDataWrapper.invalidateDataWrapper();
        //activityDataWrapper = null;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    void startProfilePreferencesActivity(Profile profile, int predefinedProfileIndex)
    {
        /*
        PPApplication.logE("EditorProfileListFragment.startProfilePreferencesActivity", "profile="+profile);
        if (profile != null)
            PPApplication.logE("EditorProfileListFragment.startProfilePreferencesActivity", "profile._name="+profile._name);
        PPApplication.logE("EditorProfileListFragment.startProfilePreferencesActivity", "predefinedProfileIndex="+predefinedProfileIndex);
        */

        int editMode;

        if (profile != null)
        {
            // edit profile
            int profilePos = profileListAdapter.getItemPosition(profile);

            /*int last = listView.getLastVisiblePosition();
            int first = listView.getFirstVisiblePosition();
            if ((profilePos <= first) || (profilePos >= last)) {
                listView.setSelection(profilePos);
            }*/
            RecyclerView.LayoutManager lm = listView.getLayoutManager();
            if (lm != null)
                lm.scrollToPosition(profilePos);

            boolean startTargetHelps = getArguments() != null && getArguments().getBoolean(START_TARGET_HELPS_ARGUMENT, false);
            if (startTargetHelps)
                showAdapterTargetHelps();

            editMode = EDIT_MODE_EDIT;
        }
        else
        {
            // add new profile
            editMode = EDIT_MODE_INSERT;
        }

        //PPApplication.logE("EditorProfileListFragment.startProfilePreferencesActivity", "editMode="+editMode);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) one must start profile preferences
        onStartProfilePreferencesCallback.onStartProfilePreferences(profile, editMode, predefinedProfileIndex);
    }

    private void duplicateProfile(Profile origProfile)
    {
        int editMode;

        // duplicate profile
        editMode = EDIT_MODE_DUPLICATE;

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) one must start profile preferences
        onStartProfilePreferencesCallback.onStartProfilePreferences(origProfile, editMode, 0);

    }

    private void deleteProfile(Profile profile)
    {
        //final Profile _profile = profile;
        //final Activity activity = getActivity();

        if (activityDataWrapper.getProfileById(profile._id, false, false, false) == null)
            // profile not exists
            return;

        PPApplication.addActivityLog(activityDataWrapper.context, PPApplication.ALTYPE_PROFILE_DELETED, null, profile._name, profile._icon, 0, "");

        Profile activatedProfile = activityDataWrapper.getActivatedProfile(false, false);
        if ((activatedProfile != null) && (activatedProfile._id == profile._id)) {
            // remove alarm for profile duration
            //noinspection ConstantConditions
            ProfileDurationAlarmBroadcastReceiver.removeAlarm(profile, getActivity().getApplicationContext());
            //Profile.setActivatedProfileForDuration(getActivity().getApplicationContext(), 0);
        }

        // delete deleted profile from FIFO
//        PPApplication.logE("[FIFO_TEST] EditorProfileListFragment.deleteProfile", "#### remove deleted profile");
        synchronized (PPApplication.profileActivationMutex) {
            List<String> activateProfilesFIFO = activityDataWrapper.getActivatedProfilesFIFO();
            if (activateProfilesFIFO == null)
                activateProfilesFIFO = new ArrayList<>();
            List<String> newActivateProfilesFIFO = new ArrayList<>();
            for (String toFifo : activateProfilesFIFO) {
                String[] splits = toFifo.split("\\|");
                long profileId = Long.parseLong(splits[0]);
                if (profileId != profile._id)
                    newActivateProfilesFIFO.add(toFifo);
            }
            activityDataWrapper.saveActivatedProfilesFIFO(newActivateProfilesFIFO);
        }

        listView.getRecycledViewPool().clear();

        activityDataWrapper.stopEventsForProfileFromMainThread(profile, true);
        profileListAdapter.deleteItemNoNotify(profile);
        DatabaseHandler.getInstance(activityDataWrapper.context).deleteProfile(profile);

        profileListAdapter.notifyDataSetChanged();

        if (!Event.getGlobalEventsRunning()) {
            //Profile profile = databaseHandler.getActivatedProfile();
            Profile _profile = profileListAdapter.getActivatedProfile();
            updateHeader(_profile);
            //PPApplication.showProfileNotification(/*activityDataWrapper.context*/true, false);
            //PPApplication.logE("ActivateProfileHelper.updateGUI", "from EditorProfileListFragment.deleteProfile");
            //PPApplication.logE("###### PPApplication.updateGUI", "from=EditorProfileListFragment.deleteProfile");
            PPApplication.updateGUI(0, activityDataWrapper.context/*, true, true*/);
        }
        else {
            //activityDataWrapper.restartEvents(false, true, true, true, true);
//            PPApplication.logE("[APP_START] EditorProfileListFragment.deleteProfile", "xxx");
            activityDataWrapper.restartEventsWithRescan(true, false, true, false, true, false);
        }

        activityDataWrapper.setDynamicLauncherShortcutsFromMainThread();

        /*Intent serviceIntent = new Intent(getActivity().getApplicationContext(), PhoneProfilesService.class);
        serviceIntent.putExtra(PhoneProfilesService.EXTRA_ONLY_START, false);
        serviceIntent.putExtra(PhoneProfilesService.EXTRA_REREGISTER_RECEIVERS_AND_WORKERS, true);
        PPApplication.startPPService(getActivity(), serviceIntent);*/
        Intent commandIntent = new Intent(PhoneProfilesService.ACTION_COMMAND);
        //commandIntent.putExtra(PhoneProfilesService.EXTRA_ONLY_START, false);
        commandIntent.putExtra(PhoneProfilesService.EXTRA_REREGISTER_RECEIVERS_AND_WORKERS, true);
        PPApplication.runCommand(getActivity(), commandIntent);

        onStartProfilePreferencesCallback.onStartProfilePreferences(null, EDIT_MODE_DELETE, 0);
    }

    public void showEditMenu(View view)
    {
        //Context context = ((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext();
        final Context _context = view.getContext();
        PopupMenu popup;
        //if (android.os.Build.VERSION.SDK_INT >= 19)
            popup = new PopupMenu(_context, view, Gravity.END);
        //else
        //    popup = new PopupMenu(context, view);
        //noinspection ConstantConditions
        getActivity().getMenuInflater().inflate(R.menu.profile_list_item_edit, popup.getMenu());

        final Profile profile = (Profile)view.getTag();

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.profile_list_item_menu_activate) {
                activateProfile(profile/*, true*/);
                return true;
            }
            else
            if (itemId == R.id.profile_list_item_menu_duplicate) {
                duplicateProfile(profile);
                return true;
            }
            else
            if (itemId == R.id.profile_list_item_menu_delete) {
                deleteProfileWithAlert(profile);
                return true;
            }
            else {
                return false;
            }
        });


        if ((getActivity() != null) && (!getActivity().isFinishing()))
            popup.show();
    }

    private void deleteProfileWithAlert(Profile profile)
    {
        final Profile _profile = profile;

        //noinspection ConstantConditions
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getResources().getString(R.string.profile_string_0) + ": " + profile._name);
        dialogBuilder.setMessage(R.string.delete_profile_alert_message);
        //dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);

        dialogBuilder.setPositiveButton(R.string.alert_button_yes, (dialog, which) -> deleteProfile(_profile));
        dialogBuilder.setNegativeButton(R.string.alert_button_no, null);
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

        if ((getActivity() != null) && (!getActivity().isFinishing()))
            dialog.show();
    }

    private void deleteAllProfiles()
    {
        if (profileListAdapter != null) {
            //noinspection ConstantConditions
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle(R.string.alert_title_delete_all_profiles);
            dialogBuilder.setMessage(R.string.alert_message_delete_all_profiles);
            //dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);

            //final Activity activity = getActivity();

            dialogBuilder.setPositiveButton(R.string.alert_button_yes, (dialog, which) -> {
                PPApplication.addActivityLog(activityDataWrapper.context, PPApplication.ALTYPE_ALL_PROFILES_DELETED, null, null, null, 0, "");

                // remove alarm for profile duration
                synchronized (activityDataWrapper.profileList) {
                    if (activityDataWrapper.profileListFilled) {
                        for (Profile profile : activityDataWrapper.profileList)
                            ProfileDurationAlarmBroadcastReceiver.removeAlarm(profile, activityDataWrapper.context);
                    }
                }
                //Profile.setActivatedProfileForDuration(activityDataWrapper.context, 0);
//                    PPApplication.logE("[FIFO_TEST] EditorProfileListFragment.deleteAllProfiles", "#### clear");
                synchronized (PPApplication.profileActivationMutex) {
                    List<String> activateProfilesFIFO = new ArrayList<>();
                    activityDataWrapper.saveActivatedProfilesFIFO(activateProfilesFIFO);
                }

                listView.getRecycledViewPool().clear();

                activityDataWrapper.stopAllEventsFromMainThread(true, false);
                profileListAdapter.clearNoNotify();
                DatabaseHandler.getInstance(activityDataWrapper.context).deleteAllProfiles();
                DatabaseHandler.getInstance(activityDataWrapper.context).unlinkAllEvents();

                profileListAdapter.notifyDataSetChanged();

                //Profile profile = databaseHandler.getActivatedProfile();
                //Profile profile = profileListAdapter.getActivatedProfile();
                updateHeader(null);
                //PPApplication.showProfileNotification(/*activityDataWrapper.context*/true, false);
                //PPApplication.logE("ActivateProfileHelper.updateGUI", "from EditorProfileListFragment.deleteAllProfiles");
                //PPApplication.logE("###### PPApplication.updateGUI", "from=EditorProfileListFragment.deleteAllProfiles");
                PPApplication.updateGUI(0,  activityDataWrapper.context/*, true, true*/);

                activityDataWrapper.setDynamicLauncherShortcutsFromMainThread();

                /*Intent serviceIntent = new Intent(activityDataWrapper.context, PhoneProfilesService.class);
                serviceIntent.putExtra(PhoneProfilesService.EXTRA_ONLY_START, false);
                serviceIntent.putExtra(PhoneProfilesService.EXTRA_REREGISTER_RECEIVERS_AND_WORKERS, true);
                PPApplication.startPPService(getActivity(), serviceIntent);*/
                Intent commandIntent = new Intent(PhoneProfilesService.ACTION_COMMAND);
                //commandIntent.putExtra(PhoneProfilesService.EXTRA_ONLY_START, false);
                commandIntent.putExtra(PhoneProfilesService.EXTRA_REREGISTER_RECEIVERS_AND_WORKERS, true);
                PPApplication.runCommand(getActivity(), commandIntent);

                onStartProfilePreferencesCallback.onStartProfilePreferences(null, EDIT_MODE_DELETE, 0);
            });
            dialogBuilder.setNegativeButton(R.string.alert_button_no, null);
            AlertDialog dialog = dialogBuilder.create();

//            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                @Override
//                public void onShow(DialogInterface dialog) {
//                    Button positive = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
//                    if (positive != null) positive.setAllCaps(false);
//                    Button negative = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
//                    if (negative != null) negative.setAllCaps(false);
//                }
//            });

            if ((getActivity() != null) && (!getActivity().isFinishing()))
                dialog.show();
        }
    }

    void updateHeader(Profile profile)
    {
        //if (!ApplicationPreferences.applicationEditorHeader(activityDataWrapper.context))
        //    return;

        if ((activeProfileName == null) || (activeProfileIcon == null))
            return;

        String oldDisplayedText = (String)activatedProfileHeader.getTag();

        if (profile == null)
        {
            activatedProfileHeader.setTag(getString(R.string.profiles_header_profile_name_no_activated));

            activeProfileName.setText(getString(R.string.profiles_header_profile_name_no_activated));
            activeProfileIcon.setImageResource(R.drawable.ic_profile_default);
        }
        else
        {
            Spannable profileName = DataWrapper.getProfileNameWithManualIndicator(profile, true, "", true, false, false, activityDataWrapper);
            Spannable sbt = new SpannableString(profileName);
            Object[] spansToRemove = sbt.getSpans(0, profileName.length(), Object.class);
            for (Object span : spansToRemove) {
                if (span instanceof CharacterStyle)
                    sbt.removeSpan(span);
            }
            activatedProfileHeader.setTag(sbt.toString());

            activeProfileName.setText(profileName);
            if (profile.getIsIconResourceID())
            {
                if (profile._iconBitmap != null)
                    activeProfileIcon.setImageBitmap(profile._iconBitmap);
                else {
                    int res = Profile.getIconResource(profile.getIconIdentifier());
                    activeProfileIcon.setImageResource(res); // icon resource
                }
            }
            else
            {
                activeProfileIcon.setImageBitmap(profile._iconBitmap);
            }
        }

        if (ApplicationPreferences.applicationEditorPrefIndicator)
        {
            if (profile == null)
                //profilePrefIndicatorImageView.setImageResource(R.drawable.ic_empty);
                profilePrefIndicatorImageView.setVisibility(GONE);
            else {
                if (profile._preferencesIndicator != null)
                    profilePrefIndicatorImageView.setImageBitmap(profile._preferencesIndicator);
                else
                    profilePrefIndicatorImageView.setImageResource(R.drawable.ic_empty);
            }
        }

        String newDisplayedText = (String)activatedProfileHeader.getTag();
        if (!newDisplayedText.equals(oldDisplayedText))
            activatedProfileHeader.setVisibility(View.VISIBLE);

        new UpdateHeaderAsyncTask(this).execute();
    }

    public void doOnActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == EditorProfilesActivity.REQUEST_CODE_ACTIVATE_PROFILE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                long profile_id = data.getLongExtra(PPApplication.EXTRA_PROFILE_ID, -1);
                Profile profile = activityDataWrapper.getProfileById(profile_id, true,
                        ApplicationPreferences.applicationEditorPrefIndicator, false);

                if (profileListAdapter != null)
                    profileListAdapter.activateProfile(profile);
                updateHeader(profile);
             }
             //if (resultCode == Activity.RESULT_CANCELED)
             //{
                 //Write your code if there's no result
             //}
        }
    }

    public void activateProfile(Profile profile/*, boolean interactive*/)
    {
        if (!ProfilesPrefsFragment.isRedTextNotificationRequired(profile, activityDataWrapper.context)) {
            PPApplication.showToastForProfileActivation = true;
            activityDataWrapper.activateProfile(profile._id, PPApplication.STARTUP_SOURCE_EDITOR, getActivity(), false);
        }
        else
            EditorProfilesActivity.showDialogAboutRedText(profile, null, false, false, getActivity());
    }

    /*private void setProfileSelection(Profile profile) {
        if (profileListAdapter != null)
        {
            int profilePos = ListView.INVALID_POSITION;

            if (profile != null)
                profilePos = profileListAdapter.getItemPosition(profile);
            //else
            //    profilePos = listView.getCheckedItemPosition();

            if (profilePos != ListView.INVALID_POSITION)
            {
                if (listView != null) {
                    // set profile visible in list
                    //int last = listView.getLastVisiblePosition();
                    //int first = listView.getFirstVisiblePosition();
                    //if ((profilePos <= first) || (profilePos >= last)) {
                    //    listView.setSelection(profilePos);
                    //}
                    RecyclerView.LayoutManager lm = listView.getLayoutManager();
                    if (lm != null)
                        lm.scrollToPosition(profilePos);
                }
            }
        }

        boolean startTargetHelps = getArguments() != null && getArguments().getBoolean(START_TARGET_HELPS_ARGUMENT, false);
        if (startTargetHelps)
            showAdapterTargetHelps();
    }*/

    void updateListView(Profile profile, boolean newProfile, boolean refreshIcons, boolean setPosition/*, long loadProfileId*/)
    {
        /*if (listView != null)
            listView.cancelDrag();*/

        //if (profileListAdapter != null)
        if (listView != null)
            listView.getRecycledViewPool().clear();

        if (profileListAdapter != null) {
            if ((newProfile) && (profile != null))
                // add profile into listview
                profileListAdapter.addItem(profile);
        }

        synchronized (activityDataWrapper.profileList) {
            if (!activityDataWrapper.profileListFilled) {
                // sort list
                if (filterType != EditorProfileListFragment.FILTER_TYPE_SHOW_IN_ACTIVATOR)
                    sortAlphabetically(activityDataWrapper.profileList);
                else
                    sortByPOrder(activityDataWrapper.profileList);
            }
        }

        if (profileListAdapter != null) {

            int profilePos = ListView.INVALID_POSITION;

            if (profile != null)
                profilePos = profileListAdapter.getItemPosition(profile);
            //else
            //    profilePos = listView.getCheckedItemPosition();

            //Log.e("EditorProfileListFragment.updateListView", "profilePos="+profilePos);

            /*if (loadProfileId != 0) {
                if (getActivity() != null) {
                    Profile profileFromDB = DatabaseHandler.getInstance(getActivity().getApplicationContext()).getProfile(loadProfileId, false);
                    activityDataWrapper.updateProfile(profileFromDB);
                    refreshIcons = true;
                }
            }*/
            profileListAdapter.notifyDataSetChanged(refreshIcons);

            if (setPosition || newProfile) {
                if (profilePos != ListView.INVALID_POSITION)
                {
                    if (listView != null) {
                        // set profile visible in list
                        //int last = listView.getLastVisiblePosition();
                        //int first = listView.getFirstVisiblePosition();
                        //if ((profilePos <= first) || (profilePos >= last)) {
                        //    listView.setSelection(profilePos);
                        //}
                        RecyclerView.LayoutManager lm = listView.getLayoutManager();
                        if (lm != null)
                            lm.scrollToPosition(profilePos);
                    }
                }
            }

            boolean startTargetHelps = getArguments() != null && getArguments().getBoolean(START_TARGET_HELPS_ARGUMENT, false);
            if (startTargetHelps)
                showAdapterTargetHelps();

        }
    }

    /*
    public int getFilterType()
    {
        return filterType;
    }
    */

    private static void sortAlphabetically(List<Profile> profileList)
    {
        class AlphabeticallyComparator implements Comparator<Profile> {
            public int compare(Profile lhs, Profile rhs) {
                if (PPApplication.collator != null)
                    return PPApplication.collator.compare(lhs._name, rhs._name);
                else
                    return 0;
            }
        }
        //noinspection Java8ListSort
        Collections.sort(profileList, new AlphabeticallyComparator());
    }

    private static void sortByPOrder(List<Profile> profileList)
    {
        class ByPOrderComparator implements Comparator<Profile> {
            public int compare(Profile lhs, Profile rhs) {
                int res = 0;
                if ((lhs != null) && (rhs != null))
                    res =  lhs._porder - rhs._porder;
                return res;
            }
        }
        //noinspection Java8ListSort
        Collections.sort(profileList, new ByPOrderComparator());
    }

    void refreshGUI(/*final boolean refresh,*/ final boolean refreshIcons, final boolean setPosition, final long profileId)
    {
        if ((activityDataWrapper == null) || (profileListAdapter == null))
            return;

        //PPApplication.logE("EditorProfileListFragment.refreshGUI", "refresh="+refresh);

        EditorProfileListFragment.RefreshGUIAsyncTask asyncTask =
                new EditorProfileListFragment.RefreshGUIAsyncTask(
                        refreshIcons, setPosition, profileId, this, activityDataWrapper);
        asyncTask.execute();

/*        new AsyncTask<Void, Integer, Void>() {

            Profile profileFromDB;
            Profile profileFromDataWrapper;
            boolean _refreshIcons;

            //boolean doNotRefresh = false;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                _refreshIcons = refreshIcons;
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    profileFromDB = DatabaseHandler.getInstance(activityDataWrapper.context).getActivatedProfile();
                    if (profileFromDB != null) {
                        profileFromDataWrapper = activityDataWrapper.getProfileById(profileFromDB._id, true,
                                ApplicationPreferences.applicationEditorPrefIndicator, false);
                    }

//                    String pName;
//                    if (profileFromDB != null) {
//                        pName = DataWrapper.getProfileNameWithManualIndicatorAsString(profileFromDB, true, "", true, false, false, activityDataWrapper);
//                    } else
//                        pName = activityDataWrapper.context.getString(R.string.profiles_header_profile_name_no_activated);
//                    //PPApplication.logE("EditorProfileListFragment.refreshGUI", "pName="+pName);
//
//                    if (!refresh) {
//                        String pNameHeader = PPApplication.prefActivityProfileName2;
//                        //PPApplication.logE("EditorProfileListFragment.refreshGUI", "pNameHeader="+pNameHeader);
//
//                        if ((!pNameHeader.isEmpty()) && pName.equals(pNameHeader)) {
//                            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "activated profile NOT changed");
//                            doNotRefresh = true;
//                            return null;
//                        }
//                    }
//
//                    PPApplication.setActivityProfileName(activityDataWrapper.context, 2, pName);
//                    PPApplication.setActivityProfileName(activityDataWrapper.context, 3, pName);

                    if (profileId != 0) {
                        //if (getActivity() != null) {
                        Profile profileFromDB = DatabaseHandler.getInstance(activityDataWrapper.context).getProfile(profileId, false);
                        activityDataWrapper.updateProfile(profileFromDB);
                        _refreshIcons = true;
                        //}
                    }
                } catch (Exception e) {
                    if ((activityDataWrapper != null) && (activityDataWrapper.context != null))
                        PPApplication.recordException(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);
                if ((getActivity() != null) && (!getActivity().isFinishing())) {
                    //if (!doNotRefresh) {
                        Profile profileFromAdapter = profileListAdapter.getActivatedProfile();
                        if (profileFromAdapter != null)
                            profileFromAdapter._checked = false;

                        if (profileFromDB != null) {
                            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "profile activated");
                            if (profileFromDataWrapper != null)
                                profileFromDataWrapper._checked = true;
                            updateHeader(profileFromDataWrapper);
                            //updateListView(profileFromDataWrapper, false, _refreshIcons, setPosition);
                        } else {
                            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "profile not activated");
                            updateHeader(null);
                            //updateListView(null, false, _refreshIcons, setPosition);
                        }
                        updateListView(null, false, _refreshIcons, setPosition);
                    //}
                }
            }

        }.execute();*/

        /*Profile profileFromDB = DatabaseHandler.getInstance(activityDataWrapper.context).getActivatedProfile();

        String pName;
        if (profileFromDB != null)
            pName = DataWrapper.getProfileNameWithManualIndicatorAsString(profileFromDB, true, "", true, false, false, activityDataWrapper);
        else
            pName = getResources().getString(R.string.profiles_header_profile_name_no_activated);
        //PPApplication.logE("EditorProfileListFragment.refreshGUI", "pName="+pName);

        if (!refresh) {
            String pNameHeader = PPApplication.prefActivityProfileName2;
            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "pNameHeader="+pNameHeader);

            if ((!pNameHeader.isEmpty()) && pName.equals(pNameHeader)) {
                //PPApplication.logE("EditorProfileListFragment.refreshGUI", "activated profile NOT changed");
                return;
            }
        }

        PPApplication.setActivityProfileName(activityDataWrapper.context, 2, pName);
        PPApplication.setActivityProfileName(activityDataWrapper.context, 3, pName);

        Profile profileFromAdapter = profileListAdapter.getActivatedProfile();
        if (profileFromAdapter != null)
            profileFromAdapter._checked = false;

        if (profileFromDB != null) {
            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "profile activated");
            Profile profileFromDataWrapper = activityDataWrapper.getProfileById(profileFromDB._id, true,
                    ApplicationPreferences.applicationEditorPrefIndicator, false);
            if (profileFromDataWrapper != null)
                profileFromDataWrapper._checked = true;
            updateHeader(profileFromDataWrapper);
            updateListView(profileFromDataWrapper, false, refreshIcons, setPosition, profileId);
        } else {
            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "profile not activated");
            updateHeader(null);
            updateListView(null, false, refreshIcons, setPosition, 0);
        }*/
    }

    void removeAdapter() {
        if (listView != null)
            listView.setAdapter(null);
    }

    @SuppressWarnings("RedundantArrayCreation")
    void showShowInActivatorMenu(View view)
    {
        final Profile profile = (Profile) view.getTag();

        if (!ProfilesPrefsFragment.isRedTextNotificationRequired(profile, activityDataWrapper.context)) {

            //Context context = ((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext();
            Context _context = view.getContext();
            //Context context = new ContextThemeWrapper(getActivity().getBaseContext(), R.style.PopupMenu_editorItem_dayNight);
            PopupMenu popup;
            //if (android.os.Build.VERSION.SDK_INT >= 19)
            popup = new PopupMenu(_context, view, Gravity.END);
            //else
            //    popup = new PopupMenu(context, view);
            //noinspection ConstantConditions
            getActivity().getMenuInflater().inflate(R.menu.profile_list_item_show_in_activator, popup.getMenu());

            // show icons
            try {
                Field field = popup.getClass().getDeclaredField("mPopup");
                field.setAccessible(true);
                Object menuPopupHelper = field.get(popup);
                @SuppressLint("PrivateApi")
                Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
                Method method = cls.getDeclaredMethod("setForceShowIcon", new Class[]{boolean.class});
                method.setAccessible(true);
                method.invoke(menuPopupHelper, new Object[]{true});
            } catch (Exception e) {
                PPApplication.recordException(e);
            }

            popup.setOnMenuItemClickListener(item -> {
                if (getActivity() != null) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.profile_list_item_menu_show_in_activator_title) {
                        PPApplication.showToast(activityDataWrapper.context.getApplicationContext(),
                                getResources().getString(R.string.popupmenu_title_click_below_toast),
                                Toast.LENGTH_SHORT);
                        return true;
                    }
                    else
                    if (itemId == R.id.profile_list_item_menu_not_show_in_activator) {
                        profile._showInActivator = false;
                        DatabaseHandler.getInstance(activityDataWrapper.context).updateProfileShowInActivator(profile);
                        //profileListAdapter.notifyDataSetChanged();
                        ((EditorProfilesActivity) getActivity()).redrawProfileListFragment(profile, EDIT_MODE_EDIT);

                        PPApplication.showToast(activityDataWrapper.context.getApplicationContext(),
                                getResources().getString(R.string.show_profile_in_activator_not_show_toast),
                                Toast.LENGTH_LONG);

//                        Snackbar snackbar = Snackbar.make(getActivity(),
//                                getActivity().findViewById(R.id.editor_list_root),
//                                getResources().getString(R.string.show_profile_in_activator_not_show_toast),
//                                Snackbar.LENGTH_LONG);
//                        snackbar.show();

                        return true;
                    }
                    else
                    if (itemId == R.id.profile_list_item_menu_show_in_activator) {
                        profile._showInActivator = true;
                        DatabaseHandler.getInstance(activityDataWrapper.context).updateProfileShowInActivator(profile);
                        //profileListAdapter.notifyDataSetChanged();
                        ((EditorProfilesActivity) getActivity()).redrawProfileListFragment(profile, EDIT_MODE_EDIT);

                        PPApplication.showToast(activityDataWrapper.context.getApplicationContext(),
                                getResources().getString(R.string.show_profile_in_activator_show_toast),
                                Toast.LENGTH_LONG);

//                        Snackbar snackbar = Snackbar.make(getActivity(),
//                                getActivity().findViewById(R.id.editor_list_root),
//                                getResources().getString(R.string.show_profile_in_activator_show_toast),
//                                Snackbar.LENGTH_LONG);
//                        snackbar.show();

                        return true;
                    }
                    else {
                        return false;
                    }
                }
                return true;
            });

            if ((getActivity() != null) && (!getActivity().isFinishing()))
                popup.show();
        }
        else
            EditorProfilesActivity.showDialogAboutRedText(profile, null, true, false, getActivity());
    }

    /*
    void changeShowInActivator(final Profile profile) {
        //noinspection ConstantConditions
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getResources().getString(R.string.profile_string_0) + ": " + profile._name);
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);
        int show = profile._showInActivator ? 1 : 0;
        dialogBuilder.setSingleChoiceItems(R.array.showProfileInActivatorArray, show, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profile._showInActivator = which == 1;
                        DatabaseHandler.getInstance(activityDataWrapper.context).updateProfileShowInActivator(profile);
                        profileListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
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
        if (!getActivity().isFinishing())
            dialog.show();
    }
    */

    void showTargetHelps() {
        /*if (Build.VERSION.SDK_INT <= 19)
            // TapTarget.forToolbarMenuItem FC :-(
            // Toolbar.findViewById() returns null
            return;*/

        if (getActivity() == null)
            return;

        if (((EditorProfilesActivity)getActivity()).targetHelpsSequenceStarted)
            return;

        boolean startTargetHelps = ApplicationPreferences.prefEditorProfilesFragmentStartTargetHelps;
        boolean startTargetHelpsFilterSpinner = ApplicationPreferences.prefEditorProfilesFragmentStartTargetHelpsFilterSpinner;
        boolean startTargetHelpsDefaultProfile = ApplicationPreferences.prefEditorActivityStartTargetHelpsDefaultProfile;

        if (startTargetHelps || startTargetHelpsFilterSpinner || startTargetHelpsDefaultProfile ||
                ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelps ||
                ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelpsOrder ||
                ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelpsShowInActivator) {

            //Log.d("EditorProfileListFragment.showTargetHelps", "PREF_START_TARGET_HELPS_ORDER=true");

            if (startTargetHelps || startTargetHelpsFilterSpinner || startTargetHelpsDefaultProfile) {

                //Log.d("EditorProfileListFragment.showTargetHelps", "PREF_START_TARGET_HELPS=true");

                SharedPreferences.Editor editor = ApplicationPreferences.getEditor(activityDataWrapper.context);
                editor.putBoolean(PREF_START_TARGET_HELPS, false);
                editor.putBoolean(PREF_START_TARGET_HELPS_FILTER_SPINNER, false);
                editor.putBoolean(EditorProfilesActivity.PREF_START_TARGET_HELPS_DEFAULT_PROFILE, false);
                editor.apply();
                ApplicationPreferences.prefEditorProfilesFragmentStartTargetHelps = false;
                ApplicationPreferences.prefEditorProfilesFragmentStartTargetHelpsFilterSpinner = false;
                ApplicationPreferences.prefEditorActivityStartTargetHelpsDefaultProfile = false;

                //String appTheme = ApplicationPreferences.applicationTheme(getActivity(), true);
                int outerCircleColor = R.color.tabTargetHelpOuterCircleColor;
//                if (appTheme.equals("dark"))
//                    outerCircleColor = R.color.tabTargetHelpOuterCircleColor_dark;
                int targetCircleColor = R.color.tabTargetHelpTargetCircleColor;
//                if (appTheme.equals("dark"))
//                    targetCircleColor = R.color.tabTargetHelpTargetCircleColor_dark;
                int textColor = R.color.tabTargetHelpTextColor;
//                if (appTheme.equals("dark"))
//                    textColor = R.color.tabTargetHelpTextColor_dark;
                //boolean tintTarget = !appTheme.equals("white");

                final TapTargetSequence sequence = new TapTargetSequence(getActivity());
                List<TapTarget> targets = new ArrayList<>();
                int id = 1;
                if (startTargetHelps) {
                    try {
                        targets.add(
                                TapTarget.forView(((EditorProfilesActivity)getActivity()).filterSpinner, getString(R.string.editor_activity_targetHelps_profilesFilterSpinner_title), getString(R.string.editor_activity_targetHelps_profilesFilterSpinner_description))
                                        .transparentTarget(true)
                                        .outerCircleColor(outerCircleColor)
                                        .targetCircleColor(targetCircleColor)
                                        .textColor(textColor)
                                        .tintTarget(true)
                                        .drawShadow(true)
                                        .id(id)
                        );
                        ++id;
                    } catch (Exception e) {
                        //PPApplication.recordException(e);
                    }

                    try {
                        targets.add(
                                TapTarget.forToolbarOverflow(bottomToolbar, getString(R.string.editor_activity_targetHelps_profilesBottomMenu_title), getString(R.string.editor_activity_targetHelps_profilesBottomMenu_description))
                                        .outerCircleColor(outerCircleColor)
                                        .targetCircleColor(targetCircleColor)
                                        .textColor(textColor)
                                        .tintTarget(true)
                                        .drawShadow(true)
                                        .id(id)
                        );
                        ++id;
                    } catch (Exception e) {
                        //PPApplication.recordException(e);
                    }

                    try {
                        targets.add(
                                TapTarget.forToolbarMenuItem(bottomToolbar, R.id.menu_add_profile, getString(R.string.editor_activity_targetHelps_newProfileButton_title), getString(R.string.editor_activity_targetHelps_newProfileButton_description))
                                        .outerCircleColor(outerCircleColor)
                                        .targetCircleColor(targetCircleColor)
                                        .textColor(textColor)
                                        .tintTarget(true)
                                        .drawShadow(true)
                                        .id(id)
                        );
                        ++id;
                    } catch (Exception e) {
                        //PPApplication.recordException(e);
                    }
                    try {
                        targets.add(
                                TapTarget.forToolbarMenuItem(bottomToolbar, R.id.menu_delete_all_profiles, getString(R.string.editor_activity_targetHelps_deleteAllProfilesButton_title), getString(R.string.editor_activity_targetHelps_deleteAllProfilesButton_description))
                                        .outerCircleColor(outerCircleColor)
                                        .targetCircleColor(targetCircleColor)
                                        .textColor(textColor)
                                        .tintTarget(true)
                                        .drawShadow(true)
                                        .id(id)
                        );
                        ++id;
                    } catch (Exception e) {
                        //PPApplication.recordException(e);
                    }
                }
                if (startTargetHelpsDefaultProfile) {
                    try {
                        targets.add(
                                TapTarget.forToolbarMenuItem(bottomToolbar, R.id.menu_default_profile, getString(R.string.editor_activity_targetHelps_backgroundProfileButton_title), getString(R.string.editor_activity_targetHelps_backgroundProfileButton_description))
                                        .outerCircleColor(outerCircleColor)
                                        .targetCircleColor(targetCircleColor)
                                        .textColor(textColor)
                                        .tintTarget(true)
                                        .drawShadow(true)
                                        .id(id)
                        );
                        ++id;
                    } catch (Exception e) {
                        //PPApplication.recordException(e);
                    }
                }

                sequence.targets(targets)
                        .listener(new TapTargetSequence.Listener() {
                            // This listener will tell us when interesting(tm) events happen in regards
                            // to the sequence
                            @Override
                            public void onSequenceFinish() {
                                targetHelpsSequenceStarted = false;
                                showAdapterTargetHelps();
                            }

                            @Override
                            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                                //Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                            }

                            @Override
                            public void onSequenceCanceled(TapTarget lastTarget) {
                                targetHelpsSequenceStarted = false;
                                SharedPreferences.Editor editor = ApplicationPreferences.getEditor(activityDataWrapper.context);
                                editor.putBoolean(EditorProfileListAdapter.PREF_START_TARGET_HELPS, false);
                                if (filterType == FILTER_TYPE_SHOW_IN_ACTIVATOR)
                                    editor.putBoolean(EditorProfileListAdapter.PREF_START_TARGET_HELPS_ORDER, false);
                                if (filterType == FILTER_TYPE_ALL)
                                    editor.putBoolean(EditorProfileListAdapter.PREF_START_TARGET_HELPS_SHOW_IN_ACTIVATOR, false);
                                editor.apply();
                                ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelps = false;
                                if (filterType == FILTER_TYPE_SHOW_IN_ACTIVATOR)
                                    ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelpsOrder = false;
                                if (filterType == FILTER_TYPE_ALL)
                                    ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelpsShowInActivator = false;
                            }
                        });
                sequence.continueOnCancel(true)
                        .considerOuterCircleCanceled(true);
                targetHelpsSequenceStarted = true;
                sequence.start();
            }
            else {
                //Log.d("EditorProfileListFragment.showTargetHelps", "PREF_START_TARGET_HELPS=false");
                final Handler handler = new Handler(getActivity().getMainLooper());
                handler.postDelayed(() -> {
//                        PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=EditorProfileListFragment.showTargetHelps");
                    //noinspection Convert2MethodRef
                    showAdapterTargetHelps();
                }, 500);
            }
        }
    }

    private void showAdapterTargetHelps() {
        /*if (Build.VERSION.SDK_INT <= 19)
            // TapTarget.forToolbarMenuItem FC :-(
            // Toolbar.findViewById() returns null
            return;*/

        if (getActivity() == null)
            return;

        View itemView;
        if (listView.getChildCount() > 1)
            itemView = listView.getChildAt(1);
        else
            itemView = listView.getChildAt(0);
        //Log.d("EditorProfileListFragment.showAdapterTargetHelps", "profileListAdapter="+profileListAdapter);
        //Log.d("EditorProfileListFragment.showAdapterTargetHelps", "itemView="+itemView);
        if ((profileListAdapter != null) && (itemView != null))
            profileListAdapter.showTargetHelps(getActivity(), this, itemView);
        else {
            targetHelpsSequenceStarted = false;
            SharedPreferences.Editor editor = ApplicationPreferences.getEditor(activityDataWrapper.context);
            editor.putBoolean(PREF_START_TARGET_HELPS, false);
            editor.putBoolean(EditorProfileListAdapter.PREF_START_TARGET_HELPS, false);
            editor.apply();
            ApplicationPreferences.prefEditorProfilesFragmentStartTargetHelps = false;
            ApplicationPreferences.prefEditorProfilesAdapterStartTargetHelps = false;
        }
    }

    void showHeaderAndBottomToolbar() {
        if (activatedProfileHeader != null)
            activatedProfileHeader.setVisibility(VISIBLE);
        if (bottomToolbar != null)
            bottomToolbar.setVisibility(VISIBLE);
    }

    private static class RefreshGUIAsyncTask extends AsyncTask<Void, Integer, Void> {

        Profile profileFromDB;
        Profile profileFromDataWrapper;

        //boolean doNotRefresh = false;

        private final WeakReference<EditorProfileListFragment> fragmentWeakRef;
        final DataWrapper dataWrapper;
        private boolean refreshIcons;
        private final boolean setPosition;
        private final long profileId;

        public RefreshGUIAsyncTask(final boolean refreshIcons,
                                   final boolean setPosition,
                                   final long profileId,
                                   final EditorProfileListFragment fragment,
                                   final DataWrapper dataWrapper) {
            this.fragmentWeakRef = new WeakReference<>(fragment);
            this.dataWrapper = dataWrapper.copyDataWrapper();
            this.refreshIcons = refreshIcons;
            this.setPosition = setPosition;
            this.profileId = profileId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (fragmentWeakRef.get() != null) {
                try {
                    profileFromDB = DatabaseHandler.getInstance(dataWrapper.context).getActivatedProfile();
                    if (profileFromDB != null) {
                        profileFromDataWrapper = dataWrapper.getProfileById(profileFromDB._id, true,
                                ApplicationPreferences.applicationEditorPrefIndicator, false);
                    }

                    /*
                    String pName;
                    if (profileFromDB != null) {
                        pName = DataWrapper.getProfileNameWithManualIndicatorAsString(profileFromDB, true, "", true, false, false, activityDataWrapper);
                    } else
                        pName = activityDataWrapper.context.getString(R.string.profiles_header_profile_name_no_activated);
                    //PPApplication.logE("EditorProfileListFragment.refreshGUI", "pName="+pName);

                    if (!refresh) {
                        String pNameHeader = PPApplication.prefActivityProfileName2;
                        //PPApplication.logE("EditorProfileListFragment.refreshGUI", "pNameHeader="+pNameHeader);

                        if ((!pNameHeader.isEmpty()) && pName.equals(pNameHeader)) {
                            //PPApplication.logE("EditorProfileListFragment.refreshGUI", "activated profile NOT changed");
                            doNotRefresh = true;
                            return null;
                        }
                    }

                    PPApplication.setActivityProfileName(activityDataWrapper.context, 2, pName);
                    PPApplication.setActivityProfileName(activityDataWrapper.context, 3, pName);
                    */

                    if (profileId != 0) {
                        //if (getActivity() != null) {
                        Profile profileFromDB = DatabaseHandler.getInstance(dataWrapper.context).getProfile(profileId, false);
                        dataWrapper.updateProfile(profileFromDB);
                        refreshIcons = true;
                        //}
                    }
                } catch (Exception e) {
                    if ((dataWrapper != null) && (dataWrapper.context != null))
                        PPApplication.recordException(e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            EditorProfileListFragment fragment = fragmentWeakRef.get();
            if (fragment != null) {
                if ((fragment.getActivity() != null) && (!fragment.getActivity().isFinishing())) {
                    //if (!doNotRefresh) {
                    Profile profileFromAdapter = fragment.profileListAdapter.getActivatedProfile();
                    if (profileFromAdapter != null)
                        profileFromAdapter._checked = false;

                    if (profileFromDB != null) {
                        //PPApplication.logE("EditorProfileListFragment.refreshGUI", "profile activated");
                        if (profileFromDataWrapper != null)
                            profileFromDataWrapper._checked = true;
                        fragment.updateHeader(profileFromDataWrapper);
                        //updateListView(profileFromDataWrapper, false, _refreshIcons, setPosition/*, profileId*/);
                    } else {
                        //PPApplication.logE("EditorProfileListFragment.refreshGUI", "profile not activated");
                        fragment.updateHeader(null);
                        //updateListView(null, false, _refreshIcons, setPosition/*, 0*/);
                    }
                    fragment.updateListView(null, false, refreshIcons, setPosition/*, 0*/);
                    //}
                }
            }
        }

    }

    private static class UpdateHeaderAsyncTask extends AsyncTask<Void, Integer, Void> {

        boolean redTextVisible = false;
        DataWrapper _dataWrapper;

        private final WeakReference<EditorProfileListFragment> fragmentWeakRef;

        public UpdateHeaderAsyncTask(final EditorProfileListFragment fragment) {
            this.fragmentWeakRef = new WeakReference<>(fragment);
        }

        @Override
        protected Void doInBackground(Void... params) {
            EditorProfileListFragment fragment = fragmentWeakRef.get();
            if (fragment != null) {
                if (fragment.getActivity() != null) {
                    _dataWrapper = new DataWrapper(fragment.getActivity().getApplicationContext(), false, 0, false);
                    _dataWrapper.copyProfileList(fragment.activityDataWrapper);

                    for (Profile profile : _dataWrapper.profileList) {
                        if (ProfilesPrefsFragment.isRedTextNotificationRequired(profile, _dataWrapper.context))
                            redTextVisible = true;
                    }
                }
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            EditorProfileListFragment fragment = fragmentWeakRef.get();
            if (fragment != null) {
                if ((fragment.getActivity() != null) && (!fragment.getActivity().isFinishing())) {
                    try {
                        //if (activatedProfileHeader.isVisibleToUser()) {
                        TextView redText = fragment.activatedProfileHeader.findViewById(R.id.activated_profile_red_text);
                        if (redTextVisible)
                            redText.setVisibility(View.VISIBLE);
                        else
                            redText.setVisibility(GONE);
                        //}
                    } catch (Exception e) {
                        PPApplication.recordException(e);
                    }
                }
            }
        }

    }
}
