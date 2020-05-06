package com.spoiledit.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

public class ExpandableTextView extends androidx.appcompat.widget.AppCompatTextView {
    public static final String TAG = ExpandableTextView.class.getCanonicalName();

    private static final int DEFAULT_TRIM_LENGTH = 150;
    private static final String ELLIPSIS = ".....";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;

    public ExpandableTextView(Context context) {
        this(context, null);
        this.trimLength = DEFAULT_TRIM_LENGTH;
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.trimLength = DEFAULT_TRIM_LENGTH;
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
        setText();
    }
}
