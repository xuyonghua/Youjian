package com.deepbay.youjian.utils;

import android.content.Context;

public class ConfigUtils {
    public static String getValue(Context context, int index, int arrayId) {
        String[] valueArray = context.getResources().getStringArray(arrayId);
        return valueArray[index];
    }

    public static int getIndex(Context context, String value, int arrayId) {
        String[] valueArray = context.getResources().getStringArray(arrayId);
        int index = -1;
        for (int i = 0; i < valueArray.length; i++) {
            if (value.equals(valueArray[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static String getStringIndex(Context context, String value, int arrayId) {
        String[] valueArray = context.getResources().getStringArray(arrayId);
        int index = -1;
        for (int i = 0; i < valueArray.length; i++) {
            if (value.equals(valueArray[i])) {
                index = i;
                break;
            }
        }
        return String.valueOf(index);
    }
}
