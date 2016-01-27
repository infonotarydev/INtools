package com.infonotary.INtools.Android;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;

public class AndroidActivityTools {
    private static boolean activityVisible = false;

    /**
     * Shows visibility of a target Activity. To track the current visibility status use {@link AndroidActivityTools#activityResumed} and {@link AndroidActivityTools#activityPaused}
     *
     * @return Returns "true" if target activity is visible, "false" otherwise
     */
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    /**
     * To be used in onResume()
     */
    public static void activityResumed() {
        activityVisible = true;
        System.out.println("Activity is set as visible, user will not be notified");
    }

    /**
     * To be used when the target activity is not active
     */
    public static void activityPaused() {
        activityVisible = false;
        System.out.println("Activity is set as not visible, user will be notified");
    }

    /**
     * Clear a specific notification the app has shown
     *
     * @param activity                  Current activity in use
     * @param NOTIFICATION_PARAMETER_ID ID of the notification to be removed
     */
    public static void clearNotification(Activity activity, int NOTIFICATION_PARAMETER_ID) {
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_PARAMETER_ID);
    }

    /**
     * Clear all notifications the app has shown
     *
     * @param activity Current activity in use
     */
    public static void clearAllNotifications(Activity activity) {
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}