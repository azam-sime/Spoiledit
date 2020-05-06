package com.spoiledit.listeners;

public interface OnSpoilerActionClickListener {
    void onContentToggled(int position);

    void onThumbsUp(int position);

    void onThumbsDown(int position);

    void onSelection(int position);
}
