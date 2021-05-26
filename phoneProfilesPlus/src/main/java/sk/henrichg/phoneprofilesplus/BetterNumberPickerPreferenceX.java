package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

public class BetterNumberPickerPreferenceX extends DialogPreference {

    BetterNumberPickerPreferenceFragmentX fragment;

    String value;
    private String defaultValue;
    private boolean savedInstanceState;

    final int mMin, mMax;

    public BetterNumberPickerPreferenceX(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray numberPickerType = context.obtainStyledAttributes(attrs,
                R.styleable.PPBetterNumberPickerPreference, 0, 0);

        mMax = numberPickerType.getInt(R.styleable.PPBetterNumberPickerPreference_max, 5);
        mMin = numberPickerType.getInt(R.styleable.PPBetterNumberPickerPreference_min, 0);

        numberPickerType.recycle();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray ta, int index)
    {
        super.onGetDefaultValue(ta, index);
        return ta.getString(index);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        value = getPersistedString((String) defaultValue);
        this.defaultValue = (String)defaultValue;
        setSummary(value);
    }

    void persistValue() {
        if (callChangeListener(value))
        {
            persistString(value);
        }
    }

    void resetSummary() {
        if (!savedInstanceState) {
            value = getPersistedString(defaultValue);
            setSummary(value);
        }
        savedInstanceState = false;
    }

    @Override
    protected Parcelable onSaveInstanceState()
    {
        savedInstanceState = true;

        final Parcelable superState = super.onSaveInstanceState();
        /*if (isPersistent()) {
            return superState;
        }*/

        final BetterNumberPickerPreferenceX.SavedState myState = new BetterNumberPickerPreferenceX.SavedState(superState);
        myState.value = value;
        myState.defaultValue = defaultValue;
        /*myState.mMin = mMin;
        myState.mMax = mMax;*/
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (!state.getClass().equals(BetterNumberPickerPreferenceX.SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            setSummary(value);
            return;
        }

        // restore instance state
        BetterNumberPickerPreferenceX.SavedState myState = (BetterNumberPickerPreferenceX.SavedState)state;
        super.onRestoreInstanceState(myState.getSuperState());
        value = myState.value;
        defaultValue = myState.defaultValue;
        /*mMin = myState.mMin;
        mMax = myState.mMax;*/

        setSummary(value);
    }

    // SavedState class
    private static class SavedState extends BaseSavedState
    {
        String value;
        String defaultValue;
        //int mMin, mMax;

        SavedState(Parcel source)
        {
            super(source);

            // restore profileId
            value = source.readString();
            defaultValue = source.readString();
            /*mMin = source.readInt();
            mMax = source.readInt();*/
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            super.writeToParcel(dest, flags);

            // save profileId
            dest.writeString(value);
            dest.writeString(defaultValue);
            /*dest.writeInt(mMin);
            dest.writeInt(mMax);*/
        }

        SavedState(Parcelable superState)
        {
            super(superState);
        }

        public static final Creator<BetterNumberPickerPreferenceX.SavedState> CREATOR =
                new Creator<BetterNumberPickerPreferenceX.SavedState>() {
                    public BetterNumberPickerPreferenceX.SavedState createFromParcel(Parcel in)
                    {
                        return new BetterNumberPickerPreferenceX.SavedState(in);
                    }
                    public BetterNumberPickerPreferenceX.SavedState[] newArray(int size)
                    {
                        return new BetterNumberPickerPreferenceX.SavedState[size];
                    }

                };

    }

}
