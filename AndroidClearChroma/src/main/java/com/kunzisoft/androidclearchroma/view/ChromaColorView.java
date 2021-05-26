package com.kunzisoft.androidclearchroma.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.DrawableCompat;

import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.R;
import com.kunzisoft.androidclearchroma.colormode.Channel;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.listener.OnColorChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Chroma color view to show a color view with channels
 * @author JJamet
 */
public class ChromaColorView extends RelativeLayout {

    private final Context context;

    private @ColorInt
    int currentColor = Color.GRAY;
    private ColorMode colorMode = ColorMode.RGB;
    private IndicatorMode indicatorMode = IndicatorMode.DECIMAL;

    private AppCompatImageView colorView;
    private final List<ChannelView> channelViews = new ArrayList<>();
    private EditText colorEdit;
    //private TextView colorEditButton;

    private OnColorChangedListener mOnColorChangedListener;

    public ChromaColorView(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    public ChromaColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public ChromaColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    @SuppressWarnings("unused")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChromaColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attributeSet) {

        TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.ChromaColorView);
        try {
            currentColor = a.getColor(R.styleable.ChromaPreference_chromaInitialColor, currentColor);

            colorMode = ColorMode.values()[
                    a.getInt(R.styleable.ChromaPreference_chromaColorMode,
                            colorMode.ordinal())];

            indicatorMode = IndicatorMode.values()[
                    a.getInt(R.styleable.ChromaPreference_chromaIndicatorMode,
                            indicatorMode.ordinal())];
        }
        finally {
            a.recycle();
        }

        View root = inflate(context, R.layout.acch_chroma_color, this);

        colorView = root.findViewById(R.id.acch_color_view);
        colorEdit = root.findViewById(R.id.acch_color_edit);
        //colorEditButton = root.findViewById(R.id.acch_color_edit_button);

        //FragmentManager fragmentManager = context.getSupportFragmentManager();
        //Log.e("ChromaColorView.init", "fragmentManager="+fragmentManager);

        createView();
    }

    private final ChannelView.OnProgressChangedListener seekBarChangeListener = new ChannelView.OnProgressChangedListener() {
        @SuppressWarnings("unused")
        @Override
        public void onProgressChanged() {
            List<Channel> channels = new ArrayList<>();
            for (ChannelView chan : channelViews) {
                channels.add(chan.getChannel());
            }
            currentColor = colorMode.getColorMode().evaluateColor(channels);
            // Listener for color selected in real time
            if (mOnColorChangedListener != null)
                mOnColorChangedListener.onColorChanged(currentColor);

            // Change view for visibility of color
            DrawableCompat.setTint(colorView.getDrawable(), currentColor);
            colorView.invalidate();
            colorEdit.setText(String.format("%06X", 0xFFFFFF & currentColor));
            //colorEditButton.setText(String.format("%06X", 0xFFFFFF & currentColor));
        }
    };

    private void createView() {
        DrawableCompat.setTint(colorView.getDrawable(), currentColor);
        colorEdit.setText(String.format("%06X", 0xFFFFFF & currentColor));
        //colorEditButton.setText(String.format("%06X", 0xFFFFFF & currentColor));

        ViewGroup channelContainer = findViewById(R.id.acch_channel_container);
        channelContainer.removeAllViews();

        List<Channel> channels = colorMode.getColorMode().getChannels();
        channelViews.clear();
        for (Channel channel : channels) {
            ChannelView channelView = new ChannelView(getContext());

            // Assign the progress from the color
            channel.setProgress(channel.getExtractor().extract(currentColor));
            if(channel.getProgress() < channel.getMin() || channel.getProgress() > channel.getMax()) {
                throw new IllegalArgumentException(
                        "Initial progress " + channel.getProgress()
                                + " for channel: " + channel.getClass().getSimpleName()
                                + " must be between " + channel.getMin() + " and " + channel.getMax());
            }

            channelView.setChannel(channel, indicatorMode);
            channelViews.add(channelView);
        }

        /*
        colorEditButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        colorEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Log.e("ChromaColorView.afterTextChanged", "not initialize");
                if (editable.length() == 6) {
                    String color = "#FF" + editable.toString();
                    //Log.e("ChromaColorView.afterTextChanged", "color="+color);
                    //Log.e("ChromaColorView.afterTextChanged", "currentColor="+currentColor);
                    int editedColor = Color.parseColor(color);
                    //Log.e("ChromaColorView.afterTextChanged", "editedColor="+editedColor);

                    if (currentColor != editedColor) {
                        //Log.e("ChromaColorView.afterTextChanged", "color changed");
                        setCurrentColor(editedColor);
                    }
                    //else
                    //    Log.e("ChromaColorView.afterTextChanged", "color not changed");
                }
            }
        });

        colorView.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(colorEdit.getWindowToken(), 0);
        });

        for (ChannelView currentChannelView : channelViews) {
            currentChannelView.registerListener(seekBarChangeListener);
            channelContainer.addView(currentChannelView);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) currentChannelView.getLayoutParams();
            params.topMargin =
                    getResources().getDimensionPixelSize(R.dimen.acch_channel_view_margin_top);
            params.bottomMargin =
                    getResources().getDimensionPixelSize(R.dimen.acch_channel_view_margin_bottom);
        }
    }

    @Override
    public void invalidate() {
        createView();
        super.invalidate();
    }

    @SuppressWarnings("unused")
    public void setOnColorChangedListener(OnColorChangedListener listener) {
        mOnColorChangedListener = listener;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
        invalidate();
    }

    @SuppressWarnings("unused")
    public ColorMode getColorMode() {
        return colorMode;
    }

    public void setColorMode(@NonNull ColorMode colorMode) {
        this.colorMode = colorMode;
        invalidate();
    }

    @SuppressWarnings("unused")
    public IndicatorMode getIndicatorMode() {
        return indicatorMode;
    }

    public void setIndicatorMode(@NonNull IndicatorMode indicatorMode) {
        this.indicatorMode = indicatorMode;
        invalidate();
    }
}
