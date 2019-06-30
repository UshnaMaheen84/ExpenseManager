package com.example.lenovo.reminder;

import android.app.AlarmManager;
import android.content.Context;

public class myAlarmManager {

    private static AlarmManager sAlarmManager= null;

    public static AlarmManager getInstance(Context context) {

        if (sAlarmManager == null) {
            sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return sAlarmManager;
    }
}
