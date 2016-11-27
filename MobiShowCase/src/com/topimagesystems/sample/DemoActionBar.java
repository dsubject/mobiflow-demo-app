package com.topimagesystems.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 */
public class DemoActionBar extends RelativeLayout {

    public enum ActionBarContext {
	NONE, DEFAULT, LOGIN, INSTRUCTIONS, PRIVACY_POLICY, SETTINGS, IQASETTINGS, CAMERA_MANAGER, DEPOSITS, DEPOSIT_DETAILS, SEND_CONFIRMATION, SEND_DEPOSIT,SHOW_CASE,GALLERY_ZOOM
    }

    private Button btnLeft;
    private Button btnRight;
    private TextView txtActionBarTitle;
    private ImageView imgActionBarIcon;
    private ActionBarButtonClickListener actionBarButtonClickListener;

    public DemoActionBar(Context context) {
	super(context);
    }

    public DemoActionBar(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public DemoActionBar(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);

    }

    @Override
    protected void onFinishInflate() {
	super.onFinishInflate();
	init();
    }

    /**
	 * 
	 */
    private void init() {
	btnLeft = (Button) findViewById(R.id.btnBack);
	btnRight = (Button) findViewById(R.id.btnSettings);
	txtActionBarTitle = (TextView) findViewById(R.id.txtActionBarTitle);
	imgActionBarIcon = (ImageView) findViewById(R.id.imgActionBarIcon);

	final ActionBarListener actionBarListener = new ActionBarListener();

	if (btnRight != null)
	    btnRight.setOnClickListener(actionBarListener);
	if (btnLeft != null)
	    btnLeft.setOnClickListener(actionBarListener);
    }
   
    /** @param actionBarContext */
    public void ensureUI(ActionBarContext actionBarContext) {
	imgActionBarIcon.setVisibility(View.VISIBLE);
	switch (actionBarContext) {
	case NONE:
	case DEFAULT:
	    btnRight.setVisibility(View.GONE);
	    btnLeft.setVisibility(View.GONE);
	    break;
	case SEND_CONFIRMATION:
	    btnRight.setVisibility(View.GONE);
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText(R.string.Confirmation);
	    break;
	case SEND_DEPOSIT:
	    btnRight.setVisibility(View.GONE);
	    btnLeft.setVisibility(View.VISIBLE);
	    txtActionBarTitle.setText(R.string.CheckDepositInfo);
	    break;
	case LOGIN:
	    btnRight.setVisibility(View.VISIBLE);
	    btnRight.setText(R.string.Settings);
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText(R.string.Login);
	    imgActionBarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_bar_login));
	    break;
	case SETTINGS:
	    btnRight.setVisibility(View.GONE);
	    btnRight.setText(R.string.IQA);
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText(R.string.Settings);
	    imgActionBarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_bar_login));
	    break;
	case IQASETTINGS:
	    btnRight.setVisibility(View.GONE);
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText(R.string.IQASettings);
	    imgActionBarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_bar_login));
	    break;
	case INSTRUCTIONS:
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText(R.string.WhatisMobiCheck);
	    imgActionBarIcon.setBackgroundResource(R.drawable.ic_nav_bar_instructions);
	    btnRight.setText(getContext().getString(R.string.Skip));
	    break;
	case PRIVACY_POLICY:
	    txtActionBarTitle.setText(R.string.PrivacyPolicy);
	    imgActionBarIcon.setBackgroundResource(R.drawable.ic_nav_bar_policy);
	    btnLeft.setVisibility(View.GONE);
	    btnRight.setVisibility(View.GONE);

	    break;
	case DEPOSITS:
	    btnRight.setVisibility(View.VISIBLE);
	    btnRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_actionbar_menu));
	    btnRight.setText("");
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText(R.string.Deposits);
	    imgActionBarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_bar_deposits));
	    break;
	case DEPOSIT_DETAILS:
	    btnRight.setVisibility(View.GONE);
	    btnLeft.setVisibility(View.VISIBLE);
	    txtActionBarTitle.setVisibility(View.VISIBLE);
	    txtActionBarTitle.setText(R.string.DepositDetails);
	    imgActionBarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_bar_deposits));
	    break;
	case CAMERA_MANAGER:
	    btnRight.setVisibility(View.GONE);
	    btnLeft.setVisibility(View.GONE);
	    txtActionBarTitle.setText("");	    
	    imgActionBarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_nav_bar_info));
	    break;

	case SHOW_CASE:
	    btnRight.setVisibility(View.GONE);
	    btnRight.setText(R.string.IQA);
	    btnLeft.setVisibility(View.GONE);

	    String title = "TISShowCase " +  getResources().getString(R.string.TISVersion);
	    txtActionBarTitle.setText(title);
	    imgActionBarIcon.setVisibility(View.GONE);
	    break;

	default:
	    break;
	}
    }

    public void setRightBtnText(String string) {
	if (btnRight != null) {
	    btnRight.setText(string);
	}
    }

    public void setRightBtnVisiblity(boolean isVisible) {
	if (btnRight != null) {
	    btnRight.setVisibility(isVisible ? View.VISIBLE : View.GONE);
	}
    }

    public void setActionBarButtonClickListener(ActionBarButtonClickListener actionBarButtonClickListener) {
	this.actionBarButtonClickListener = actionBarButtonClickListener;
    }

    /**
	 *
	 */
    class ActionBarListener implements OnClickListener {
	@Override
	public void onClick(View v) {
	    if (actionBarButtonClickListener == null)
		return;

	    if (v == btnLeft) {
		actionBarButtonClickListener.onBtnLeftClick();
	    } else if (v == btnRight) {
		actionBarButtonClickListener.onBtnRightClick();
	    }
	}
    }

    /**
	 *
	 */
    public interface ActionBarButtonClickListener {
	public void onBtnLeftClick();

	public void onBtnRightClick();
    }

}