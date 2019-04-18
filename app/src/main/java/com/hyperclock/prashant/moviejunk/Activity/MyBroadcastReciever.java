package com.hyperclock.prashant.moviejunk.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "this is test", Toast.LENGTH_SHORT).show();
    }
}
