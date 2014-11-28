package com.dhiman.localbrodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DHIMAN DASGUPTA on 11/28/2014.
 */
public class ReceiveBroadcastReceiver extends BroadcastReceiver {
    private ReceiveCallback mReceiveCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ReceiveCallback.SEND_INTENT_ACTION_NAME.equals(intent.getAction())) {
            final long currentMilis = intent.getLongExtra(ReceiveCallback.SEND_INTENT_KEY,
                    -1l);

            if (mReceiveCallback != null) {
                mReceiveCallback.onRecive(currentMilis);
            }
        }
    }

    public void registerCallback(final ReceiveCallback receiveCallback) {
        mReceiveCallback = receiveCallback;
    }

    public void unregisterCallback() {
        mReceiveCallback = null;
    }
}
