package com.spoiledit.listeners;

public interface OnPermissionChangeListener {
    void onAllowPermission(String[] permissions);

    void onPermissionDenied();
}
