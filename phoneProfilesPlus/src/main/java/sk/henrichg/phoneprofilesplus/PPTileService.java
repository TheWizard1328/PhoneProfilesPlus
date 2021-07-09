package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.lang.ref.WeakReference;

public class PPTileService extends TileService {

    private long profileId = 0;

    public final BroadcastReceiver chooseTileBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent ) {
        profileId = intent.getLongExtra(PPApplication.EXTRA_PROFILE_ID, -1);
//        PPApplication.logE("[IN_BROADCAST] PPTileService.chooseTileBroadcastReceiver", "profileId="+profileId);

        // update Tile and save profileId int SharedPreferences
        ApplicationPreferences.setQuickTileProfileId(getApplicationContext(), getTileId(), profileId);
        PPTileService.this.updateTile();
        }
    };

    @Override
    public void onClick () {
        super.onClick();

        // Called when the user click the tile

        // get profileId from shaered preferences
        profileId = ApplicationPreferences.getQuickTileProfileId(getApplicationContext(), getTileId());
        updateTile();

//        PPApplication.logE("PPTileService.onClick", "profileId="+profileId);

        boolean isOK = false;
        if ((profileId != 0) && (profileId != -1)) {
            Profile profile = null;
            if (profileId != Profile.RESTART_EVENTS_PROFILE_ID) {
                DataWrapper dataWrapper = new DataWrapper(getApplicationContext(), false, 0, false);
                profile = dataWrapper.getProfileById(profileId, false, false, false);
//                if (profile != null)
//                    PPApplication.logE("PPTileService.onClick", "profile=" + profile._name);
            }
            if ((profileId == Profile.RESTART_EVENTS_PROFILE_ID) || (profile != null)) {
                isOK = true;
                Intent intent = new Intent(getApplicationContext(), BackgroundActivateProfileActivity.class);
                intent.setAction(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(PPApplication.EXTRA_STARTUP_SOURCE, PPApplication.STARTUP_SOURCE_QUICK_TILE);
                intent.putExtra(PPApplication.EXTRA_PROFILE_ID, profileId);
                startActivityAndCollapse(intent);
            }
        }
        if (!isOK) {
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(chooseTileBroadcastReceiver,
                    new IntentFilter(PPApplication.PACKAGE_NAME + ".ChooseTileBroadcastReceiver"+getTileId()));

            Intent intent = new Intent(getApplicationContext(), TileChooserActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(PPApplication.EXTRA_STARTUP_SOURCE, PPApplication.STARTUP_SOURCE_QUICK_TILE);
            intent.putExtra(TileChooserActivity.EXTRA_TILE_ID, getTileId());
            startActivityAndCollapse(intent);
        }
    }

    @Override
    public void onTileRemoved () {
        super.onTileRemoved();
        // Do something when the user removes the Tile

        // set it inactive when removed
        profileId = 0;
        ApplicationPreferences.setQuickTileProfileId(getApplicationContext(), getTileId(), profileId);
        updateTile();
    }

    @Override
    public void onTileAdded () {
        super.onTileAdded();
        // Do something when the user add the Tile

        // get profileId from SharedPreferences and update it
        profileId = ApplicationPreferences.getQuickTileProfileId(getApplicationContext(), getTileId());
        updateTile();
    }

    @Override
    public void onStartListening () {
        super.onStartListening();
        // Called when the Tile becomes visible
//        PPApplication.logE("PPTileService.onStartListening", "getTileId()="+getTileId());

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(chooseTileBroadcastReceiver,
                new IntentFilter(PPApplication.PACKAGE_NAME + ".ChooseTileBroadcastReceiver"+getTileId()));

        // get profileId of tile from SharedPreferences and update it
        profileId = ApplicationPreferences.getQuickTileProfileId(getApplicationContext(), getTileId());
        updateTile();
    }

    /*
    @Override
    public void onStopListening () {
        super.onStopListening();
        // Called when the tile is no longer visible
    }
    */

    @Override
    public void onDestroy () {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(chooseTileBroadcastReceiver);
    }


    int getTileId() {
        return 0;
    }

    void updateTile() {
        Tile tile = getQsTile();

//        PPApplication.logE("PPTileService.updateTile", "profileId="+profileId);

        if ((profileId != 0) && (profileId != -1)) {
            final Handler __handler = new Handler(PPApplication.handlerThreadWidget.getLooper());
            __handler.post(new PPHandlerThreadRunnable(getApplicationContext(), tile) {
                @Override
                public void run() {
//                            PPApplication.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThreadWidget", "START run - from=IconWidgetProvider.onReceive");

                    Context appContext= appContextWeakRef.get();
                    Tile tile = tileWeakRef.get();

                    if ((appContext != null) && (tile != null)) {

//                        PPApplication.logE("PPTileService.updateTile", "udate tile");

                        if (profileId == Profile.RESTART_EVENTS_PROFILE_ID) {
                            tile.setLabel(getString(R.string.menu_restart_events));
                            tile.setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.ic_list_item_events_restart_color));
                            tile.setState(Tile.STATE_INACTIVE);
                        }
                        else {
                            DataWrapper dataWrapper = new DataWrapper(getApplicationContext(), false, 0, false);
                            Profile profile = dataWrapper.getProfileById(profileId, true, false, false);
                            if (profile != null) {
                                tile.setLabel(profile._name);

                                if (profile.getIsIconResourceID()) {
                                    if (profile._iconBitmap != null)
                                        tile.setIcon(Icon.createWithBitmap(profile._iconBitmap));
                                    else {
                                        int res = Profile.getIconResource(profile.getIconIdentifier());
                                        tile.setIcon(Icon.createWithResource(getApplicationContext(), res));
                                    }
                                } else {
                                    tile.setIcon(Icon.createWithBitmap(profile._iconBitmap));
                                }

                                if (profile._checked)
                                    tile.setState(Tile.STATE_ACTIVE);
                                else
                                    tile.setState(Tile.STATE_INACTIVE);
                            }
                        }
                        tile.updateTile();

                        // save tile profileId into SharedPreferences
                    }
                }
            });
        } else {
            tile.setLabel(getString(R.string.quick_tile_icon_label));
            tile.setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.ic_profile_default));
            tile.setState(Tile.STATE_INACTIVE);
            tile.updateTile();
        }
    }

    private static abstract class PPHandlerThreadRunnable implements Runnable {

        public final WeakReference<Context> appContextWeakRef;
        public final WeakReference<Tile> tileWeakRef;

        public PPHandlerThreadRunnable(Context appContext,
                                       Tile tile) {
            this.appContextWeakRef = new WeakReference<>(appContext);
            this.tileWeakRef = new WeakReference<>(tile);
        }

    }

}
