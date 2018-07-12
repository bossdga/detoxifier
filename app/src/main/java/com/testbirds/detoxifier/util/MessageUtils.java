package com.testbirds.detoxifier.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class to show messages
 */
public class MessageUtils {

    /**
     * Displays a message from a String id
     * @param context
     * @param message
     * @param length
     */
    public static void showMessage(Context context, int message, int length) {
        if (context != null) {
            final String text = context.getResources().getString(message);
            showMessage(context, text, length);
        }
    }

    /**
     * Displays a message from a String
     * @param context
     * @param message
     */
    private static void showMessage(Context context, String message, int length) {
        if (context != null) {
            Toast.makeText(context, message, length).show();
        }
    }

}