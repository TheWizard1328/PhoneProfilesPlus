package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

class ApplicationsDialogPreferenceAdapterX extends RecyclerView.Adapter<ApplicationsDialogPreferenceViewHolderX>
                                            implements ItemTouchHelperAdapter
{
    private final Context context;

    private final ApplicationsDialogPreferenceX preference;

    private final OnStartDragItemListener mDragStartListener;

    ApplicationsDialogPreferenceAdapterX(Context context, ApplicationsDialogPreferenceX preference,
                                         OnStartDragItemListener dragStartListener)
    {
        this.context = context;
        this.preference = preference;
        this.mDragStartListener = dragStartListener;
    }

    @NonNull
    @Override
    public ApplicationsDialogPreferenceViewHolderX onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applications_preference_list_item, parent, false);
        return new ApplicationsDialogPreferenceViewHolderX(view, context, preference);
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationsDialogPreferenceViewHolderX holder, int position) {
        Application application = preference.applicationsList.get(position);
        holder.bindApplication(application);

        holder.dragHandle.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDragStartListener.onStartDrag(holder);
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            /*if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }*/
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return preference.applicationsList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        preference.applicationsList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (preference.applicationsList == null)
            return false;

        //Log.d("----- ApplicationsDialogPreferenceAdapter.onItemMove", "fromPosition="+fromPosition);
        //Log.d("----- ApplicationsDialogPreferenceAdapter.onItemMove", "toPosition="+toPosition);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(preference.applicationsList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(preference.applicationsList, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}
