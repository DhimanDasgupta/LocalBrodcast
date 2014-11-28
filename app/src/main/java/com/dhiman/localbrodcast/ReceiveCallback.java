package com.dhiman.localbrodcast;

/**
 * Created by DHIMAN DASGUPTA on 11/28/2014.
 */
public interface ReceiveCallback {
    public void onRecive(final long value);

    public static final String SEND_INTENT_ACTION_NAME = "com.dhiman.localbrodcast.action";
    public static final String SEND_INTENT_KEY = "milis_value";
}
