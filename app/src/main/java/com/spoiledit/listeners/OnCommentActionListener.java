package com.spoiledit.listeners;

public interface OnCommentActionListener extends OnSpoilerActionClickListener {
    void onReplyComment(int position);

    void onReport(int position);
}
