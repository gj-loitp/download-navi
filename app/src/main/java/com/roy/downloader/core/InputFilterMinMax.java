package com.roy.downloader.core;

import android.text.InputFilter;
import android.text.Spanned;

/*
 * Filtering numbers, which are outside the specified range.
 */

public class InputFilterMinMax implements InputFilter {
    private final int min;
    private final int max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        if (charSequence.length() != 0 && charSequence.charAt(0) == '-')
            return null;

        try {
            int input = Integer.parseInt(spanned.toString() + charSequence.toString());
            if (inRange(min, max, input))
                return null;

        } catch (NumberFormatException e) {
            /* Ignore */
        }

        return "";
    }

    private boolean inRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
