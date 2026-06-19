package com.amirbahadoramiri.kalayar.tools.devices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

public class Devices {
    @SuppressLint("HardwareIds")
    public static String getUniqueId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
