package com.spoiledit.listeners;

public interface OnSpoilerActionClickListener extends OnSpoilerUserClickListener {
    void onContentToggled(int position);

    void onThumbsUp(int position);

    void onThumbsDown(int position);
}
