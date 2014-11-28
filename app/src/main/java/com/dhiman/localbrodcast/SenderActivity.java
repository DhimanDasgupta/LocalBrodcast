package com.dhiman.localbrodcast;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by DHIMAN DASGUPTA on 11/28/2014.
 */
public class SenderActivity extends ActionBarActivity {
    private static final String TEXT_KEY = "text";

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            final long currentMilis = System.currentTimeMillis();
            sendBroadcast(currentMilis);

            try {
                mTextView.setText("Last Send At : " + currentMilis);
            } catch (Exception e){

            }

            mHandler.postDelayed(mRunnable, 2 * 1000);
        }
    };

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        mTextView = (TextView) findViewById(R.id.activity_send_text_view);

        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_KEY));
        }

        final Button button = (Button) findViewById(R.id.activity_send_button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), ReceiverActivity.class));
                }
            });
        }

        mHandler.postDelayed(mRunnable, 2 * 1000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TEXT_KEY, mTextView.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sender, menu);
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

    private void sendBroadcast(final long milis) {
        final Intent intent = new Intent(ReceiveCallback.SEND_INTENT_ACTION_NAME);
        intent.setComponent(new ComponentName(getPackageName(), "ReceiveBroadcastReceiver"));
        intent.putExtra(ReceiveCallback.SEND_INTENT_KEY, milis);
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);

        Log.d("Send", "Text Send At : " + milis);
    }
}
