/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.codetroopers.betterpickers.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Displays text with no padding at the top.
 */
@SuppressLint("AppCompatCustomView")
public class ZeroTopPaddingTextView extends TextView {

    //private static final float NORMAL_FONT_PADDING_RATIO = 0.328f;
    private static final float NORMAL_FONT_PADDING_RATIO = 0.0f; //0.208f;
    // the bold font face has less empty space on the top
    private static final float BOLD_FONT_PADDING_RATIO = 0.0f; //0.208f;

    //private static final float NORMAL_FONT_BOTTOM_PADDING_RATIO = 0.25f;
    private static final float NORMAL_FONT_BOTTOM_PADDING_RATIO = 0.1f; //0.208f;
    // the bold font face has less empty space on the top
    private static final float BOLD_FONT_BOTTOM_PADDING_RATIO = 0.1f; //0.208f;

    // pre-ICS (Droid Sans) has weird empty space on the bottom
    //private static final float PRE_ICS_BOTTOM_PADDING_RATIO = 0.233f;

    //private static final Typeface SAN_SERIF_BOLD = Typeface.create("san-serif", Typeface.BOLD);
    //private static final Typeface SAN_SERIF_CONDENSED_BOLD = Typeface.create("sans-serif-condensed", Typeface.BOLD);

    private int mPaddingRight = 0;

    //private String decimalSeparator = "";
    //private String timeSeparator = "";

    public ZeroTopPaddingTextView(Context context) {
        this(context, null);
    }

    public ZeroTopPaddingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZeroTopPaddingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setIncludeFontPadding(false);
        updatePadding();
    }


    @SuppressWarnings("EmptyMethod")
    private void init() {
        //decimalSeparator = getResources().getString(R.string.number_picker_separator);
        //timeSeparator = getResources().getString(R.string.time_picker_time_separator);
    }

    public void updatePadding() {
        float paddingRatio = NORMAL_FONT_PADDING_RATIO;
        float bottomPaddingRatio = NORMAL_FONT_BOTTOM_PADDING_RATIO;
        if (getPaint().getTypeface() != null && getPaint().getTypeface().equals(Typeface.DEFAULT_BOLD)) {
            //noinspection ConstantConditions
            paddingRatio = BOLD_FONT_PADDING_RATIO;
            bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO;
        }
        /*if (getTypeface() != null && getTypeface().equals(SAN_SERIF_BOLD)) {
            paddingRatio = BOLD_FONT_PADDING_RATIO;
            bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO;
        }
        if (getTypeface() != null && getTypeface().equals(SAN_SERIF_CONDENSED_BOLD)) {
            paddingRatio = BOLD_FONT_PADDING_RATIO;
            bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO;
        }*/
        // no need to scale by display density because getTextSize() already returns the font
        // height in px
        setPadding(0, (int) (-paddingRatio * getTextSize()), mPaddingRight,
                (int) (-bottomPaddingRatio * getTextSize()));
    }

    public void updatePaddingForBoldDate() {
        //float paddingRatio = BOLD_FONT_PADDING_RATIO;
        //float bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO;
        // no need to scale by display density because getTextSize() already returns the font
        // height in px
        setPadding(0, (int) (-BOLD_FONT_PADDING_RATIO * getTextSize()), mPaddingRight,
                (int) (-BOLD_FONT_BOTTOM_PADDING_RATIO * getTextSize()));
    }

    @SuppressWarnings("unused")
    public void setPaddingRight(int padding) {
        mPaddingRight = padding;
        updatePadding();
    }
}