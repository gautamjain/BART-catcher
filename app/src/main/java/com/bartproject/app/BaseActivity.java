package com.bartproject.app;

import com.bartproject.app.network.ApiService;
import com.octo.android.robospice.SpiceManager;

import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

    private SpiceManager mSpiceManager = new SpiceManager(ApiService.class);

    @Override
    protected void onStart() {
        super.onStart();
        mSpiceManager.start(this);
    }

    @Override
    protected void onStop() {
        // Stop spice manager before calling parent
        mSpiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
