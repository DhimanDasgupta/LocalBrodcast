package com.dhiman.localbrodcast;

import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by DHIMAN DASGUPTA on 11/28/2014.
 */
public class ReceiverActivity extends ActionBarActivity implements ReceiveCallback {
    private static final String ARRAY_KEY = "array";

    private final ReceiveBroadcastReceiver mBroadcastReceiver = new ReceiveBroadcastReceiver();

    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        mListView = (ListView) findViewById(R.id.activity_receive_list_view);
        if (mListView != null) {
            mArrayAdapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_list_item_1);

            if (savedInstanceState != null) {
                restoreAndFillData(savedInstanceState);
            }

            mListView.setAdapter(mArrayAdapter);
            mListView.smoothScrollToPosition(mArrayAdapter.getCount());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBroadcastReceiver.registerCallback(this);

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ReceiveCallback.SEND_INTENT_ACTION_NAME);
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBroadcastReceiver.unregisterCallback();

        LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < mArrayAdapter.getCount(); i++) {
            list.add(mArrayAdapter.getItem(i));
        }

        outState.putStringArrayList(ARRAY_KEY, list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receiver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecive(long value) {
        mArrayAdapter.add(String.valueOf(value));
        mArrayAdapter.notifyDataSetChanged();

        mListView.smoothScrollToPosition(mArrayAdapter.getCount());
    }

    private void restoreAndFillData(final Bundle savedInstanceState) {
        final ArrayList<String> list = savedInstanceState.getStringArrayList(ARRAY_KEY);

        if (list != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mArrayAdapter.addAll(list);
            } else {
                if (list.size() > 0) {
                    for (String str : list) {
                        mArrayAdapter.add(str);
                    }
                }
            }
        }

    }
}
