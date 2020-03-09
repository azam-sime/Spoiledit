package com.spoiledit.repos;

import android.content.Context;

public class DashboardRepo extends RootRepo {
    public static final String TAG = DashboardRepo.class.getCanonicalName();

    public static DashboardRepo dashboardRepo;

    public static synchronized DashboardRepo initialise(Context context) {
        synchronized (TAG) {
            if (dashboardRepo == null)
                dashboardRepo = new DashboardRepo(context);
        }

        return dashboardRepo;
    }

    private Context context;

    private DashboardRepo(Context context) {
        this.context = context;

        init(context);
    }
}
