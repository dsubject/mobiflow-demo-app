package com.topimagesystems.sample;

import com.topimagesystems.data.SessionResultParams;
import com.topimagesystems.sample.ui.ShowcasePreferences;

import android.app.Application;
import android.provider.Settings.Secure;

public class MobiSampleApplication extends Application {

    private SessionResultParams currentSessionParams;
    private static MobiSampleApplication instance;

    public static MobiSampleApplication getInstance() {
	return instance;
    }

    @Override
    public void onCreate() {
	super.onCreate();
	// bugsense
	//		BugSenseHandler.setLogging(1000, "*:W");
	//	DemoFileUtils.clearTempFiles(getApplicationContext());
	//Crashlytics.start(this);
	//Fabric.with(this, new Crashlytics());

	//instance = this;
	initDB();

    }

    public String getDeviceId() {
	return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
    }

    public boolean isDebugMode() {
	return ShowcasePreferences.isDebugMode(getApplicationContext());
    }

    private void initDB() {
    }

    public SessionResultParams getLastSessionParams() {
	return currentSessionParams;
    }

    public void setCurrentSessionResults(SessionResultParams _currentSessionParams) {
	currentSessionParams = _currentSessionParams;
    }

    public void clearLastSessionParams() {
	if (this.currentSessionParams != null) {
	    this.currentSessionParams = null;
	}
    }
}