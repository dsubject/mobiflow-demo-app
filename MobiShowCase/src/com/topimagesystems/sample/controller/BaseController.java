package com.topimagesystems.sample.controller;

import com.topimagesystems.sample.Config;
import com.topimagesystems.sample.DemoActionBar;
import com.topimagesystems.sample.R;
import com.topimagesystems.util.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 */
public abstract class BaseController extends Activity {

	protected Button btnBack;
	protected Button btnRightButton;
	protected TextView txtActionBarTitle;
	protected ImageView imgActionBarIcon;
	protected DemoActionBar demoActionBar;
	private ProgressDialog progressDialog;

	private static final String ACTION_INSTRUCTIONS = "com.topimagesystems.demo.INSTRUCTIONS";


	@Override
	@Deprecated
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ensureActionBar();
	}

	protected void onCreate(Bundle savedInstanceState, int layoutResID) {
		super.onCreate(savedInstanceState);
		
		if ( !StringUtils.isEmptyOrNull(Config.ZUBHIUM_SECRET_KEY)){
	//		sdk = ZubhiumSDK.getZubhiumSDKInstance(getApplicationContext(), Config.ZUBHIUM_SECRET_KEY);
	//		sdk.enableCrashReporting(true);
		}

		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (layoutResID > -1) {
			setContentView(layoutResID);
		}
		ensureActionBar();
	}

	protected abstract void ensureActionBar();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			onSettings();
		}
		else if (item.getItemId() == R.id.instructions) {
			//onInstructions();
		}
		else if (item.getItemId() == R.id.feedback) {
			setupFeedback();
		}
		else {
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	private void setupFeedback() {
		if (getApplicationContext() != null) {
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		if ( !StringUtils.isEmptyOrNull(Config.ZUBHIUM_SECRET_KEY)){
//			BugSenseHandler.initAndStartSession(this, Config.BUGSENSE_APIKEY);
//		}
	}

	@Override
	protected void onPause() {
		super.onPause();
//		if ( !StringUtils.isEmptyOrNull(Config.ZUBHIUM_SECRET_KEY)){
//			BugSenseHandler.closeSession(this);
//		}
	}

	protected void onSettings() {
	//	NavigationManager.getInstance().showNewScreen(this, SettingsController.class, null, false);
	}

	protected void onInstructions() { // Instructions button view gone, should not get here!!
		//Intent intentInstructions = new Intent(this, com.topimagesystems.demo.controllers.instructions.InstructionsController.class);
	/*	intentInstructions.setAction(ACTION_INSTRUCTIONS);
		intentInstructions.addCategory(Intent.CATEGORY_DEFAULT);

		intentInstructions.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentInstructions.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		this.startActivityForResult(intentInstructions, Constants.INSTRUCTIONS_REQUEST_CODE);
*/	}
	
	protected void onLogout() {
//		NavigationManager.getInstance().showNewScreen(this, LoginController.class, null, true);
	}

	protected void showError(final String title, final String message, final DialogInterface.OnClickListener onclickListener) {
		if (!isFinishing()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					showProgressDialog(false);
					AlertDialog alertDialog = new AlertDialog.Builder(BaseController.this).setTitle(title).setMessage(message)
							.setCancelable(false).setPositiveButton(getString(R.string.OK), onclickListener).create();
					alertDialog.show();
				}
			});
		}
		return;
	}

	/**
	 * @param isShow
	 */
	protected void showProgressDialog(boolean isShow) {
		showProgressDialog(isShow, StringUtils.dynamicString(this,"TISProcessing"));
	}

	/**
	 * @param isShow
	 * @param message
	 */
	protected void showProgressDialog(final boolean isShow, final String message) {
		if (!isFinishing()) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (isShow) {
						if (progressDialog == null) {
							progressDialog = new ProgressDialog(BaseController.this);
							progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						}
						progressDialog.setMessage(message);
						progressDialog.show();
					}
					else {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				}
			});
		}
	}

	public void onFailed() {
	    // TODO Auto-generated method stub
	    
	}
}