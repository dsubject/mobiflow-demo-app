package com.topimagesystems.sample;

import com.topimagesystems.sample.fragments.ShowCaseFragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

//this sample class can be used as a container for fragments / nested fragments

public class ShowCaseContainerActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_case_container_layout);

		// to test nested fragments
		Fragment fragment = new ShowCaseFragment();
		
		// to test nested fragments use the commented line instead
		// Fragment fragment = new ShowCaseContainerFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.root, fragment);
		transaction.commit();
	}
	
	/*
	 * Implementation before version 4.04v
	 * 
	 * 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Fragment fragment = getFragmentManager().findFragmentById(R.id.root);
		fragment.onActivityResult(requestCode, resultCode, data);
	}
	 */
	
	/*
	 * Implementation from version 4.04v
	 * Must use super.onActivityResult for this to work
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
