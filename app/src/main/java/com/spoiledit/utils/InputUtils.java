package com.spoiledit.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public final class InputUtils {
    private static final String TAG = InputUtils.class.getCanonicalName();

    private static final int digitsBeforeDecimal = 8;
    private static final int digitsAfterDecimal = 3;
    private static final String decimal = ".";

    private InputUtils() {
    }

    public static void hideKeyboard(Context context) {
        if (context != null) {
            if (((Activity) context).getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void showKeyboard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public static InputFilter getNoInputFilter() {
        return (charSequence, i, i1, spanned, i2, i3) -> "";
    }

    public static InputFilter[] getLengthFilters(int length) {
        return new InputFilter[]{new InputFilter.LengthFilter(length)};
    }
}
