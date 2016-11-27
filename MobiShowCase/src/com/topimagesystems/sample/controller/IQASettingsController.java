package com.topimagesystems.sample.controller;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.topimagesystems.intent.CaptureIntent.IQAIntentKeys;
import com.topimagesystems.intent.IQASettingsIntent;
import com.topimagesystems.sample.DemoActionBar;
import com.topimagesystems.sample.DemoActionBar.ActionBarContext;
import com.topimagesystems.sample.R;
import com.topimagesystems.sample.ui.ShowcasePreferences;

import java.util.Locale;

public class IQASettingsController extends BaseController {

//	final int[] resEditText = { R.id.CBLLA, R.id.CBLLH, R.id.CBLLW, R.id.CBLRA,
//			R.id.CBLRH, R.id.CBLRW, R.id.CBULA, R.id.CBULH, R.id.CBULW,
//			R.id.CBURA, R.id.CBURH, R.id.CBURW, R.id.CFLLW, R.id.CFLLA,
//			R.id.CFLLH, R.id.CFLRA, R.id.CFLRH, R.id.CFLRW, R.id.CFULA,
//			R.id.CFULH, R.id.CFULW, R.id.CFURA, R.id.CFURH, R.id.CFURW,
//			R.id.CFULW, R.id.CFULW, R.id.DBMax, R.id.DBMin, R.id.DFMax,
//			R.id.DFMin, R.id.EBA, R.id.EBH, R.id.EBW, R.id.ELA, R.id.ELH,
//			R.id.ELW, R.id.ERA, R.id.ERH, R.id.ERW, R.id.ETA, R.id.ETH,
//			R.id.ETW, R.id.MAXSKEW, R.id.SBmaxSpots, R.id.SFmaxSpots };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.iqa_settings_layout);

		fillEditTextValues();
		
		Button iqaBtnConfirm = (Button) findViewById(R.id.iqaBtnConfirm);
		final IQASettingsController mActivity = this;
		iqaBtnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveEditTextValues();
				mActivity.finish();
			}
		});

	}

	private void fillEditTextValues() {

		
		IQASettingsIntent iqaSettings = ShowcasePreferences.getIQASettings(getApplicationContext());
		
		// front corner data
		// corner data front TL
		setEditText(R.id.CFULH,iqaSettings.cornerDataFrontTLH);
		setEditText(R.id.CFULW,iqaSettings.cornerDataFrontTLW);
		setEditText(R.id.CFULA,iqaSettings.cornerDataFrontTLA);
		// corner data front TR
		setEditText(R.id.CFURH,iqaSettings.cornerDataFrontTRH);
		setEditText(R.id.CFURW,iqaSettings.cornerDataFrontTRW);
		setEditText(R.id.CFURA,iqaSettings.cornerDataFrontTRA);
		// corner data front BR
		setEditText(R.id.CFLRH,iqaSettings.cornerDataFrontBRH);
		setEditText(R.id.CFLRW,iqaSettings.cornerDataFrontBRW);
		setEditText(R.id.CFLRA,iqaSettings.cornerDataFrontBRA);
		// corner data front BL
		setEditText(R.id.CFLLH,iqaSettings.cornerDataFrontBLH);
		setEditText(R.id.CFLLW,iqaSettings.cornerDataFrontBLW);
		setEditText(R.id.CFLLA,iqaSettings.cornerDataFrontBLA);
		
		// back corner data
		// corner data back TL
		setEditText(R.id.CBULH,iqaSettings.cornerDataBackTLH);
		setEditText(R.id.CBULW,iqaSettings.cornerDataBackTLW);
		setEditText(R.id.CBULA,iqaSettings.cornerDataBackTLA);
		// corner data back TR
		setEditText(R.id.CBURH,iqaSettings.cornerDataBackTRH);
		setEditText(R.id.CBURW,iqaSettings.cornerDataBackTRW);
		setEditText(R.id.CBURA,iqaSettings.cornerDataBackTRA);
		// corner data back BR
		setEditText(R.id.CBLRH,iqaSettings.cornerDataBackBRH);
		setEditText(R.id.CBLRW,iqaSettings.cornerDataBackBRW);
		setEditText(R.id.CBLRA,iqaSettings.cornerDataBackBRA);
		// corner data back BL
		setEditText(R.id.CBLLH,iqaSettings.cornerDataBackBLH);
		setEditText(R.id.CBLLW,iqaSettings.cornerDataBackBLW);
		setEditText(R.id.CBLLA,iqaSettings.cornerDataBackBLA);
				
		
		// edge data
		// edge data Top
		setEditText(R.id.ETH,iqaSettings.edgeDataTH);
		setEditText(R.id.ETW,iqaSettings.edgeDataTW);
		setEditText(R.id.ETA,iqaSettings.edgeDataTA);
		// edge data Right
		setEditText(R.id.ERH,iqaSettings.edgeDataRH);
		setEditText(R.id.ERW,iqaSettings.edgeDataRW);
		setEditText(R.id.ERA,iqaSettings.edgeDataRA);
		// corner data Bottom
		setEditText(R.id.EBH,iqaSettings.edgeDataBH);
		setEditText(R.id.EBW,iqaSettings.edgeDataBW);
		setEditText(R.id.EBA,iqaSettings.edgeDataBA);
		// corner data Left
		setEditText(R.id.ELH,iqaSettings.edgeDataLH);
		setEditText(R.id.ELW,iqaSettings.edgeDataLW);
		setEditText(R.id.ELA,iqaSettings.edgeDataLA);
				
		
		setEditText(R.id.DFMax,iqaSettings.maxDarknessFront);
		setEditText(R.id.DFMin,iqaSettings.minDarknessFront);
		setEditText(R.id.DBMax,iqaSettings.maxDarknessBack);
		setEditText(R.id.DBMin,iqaSettings.minDarknessBack);

		setEditText(R.id.MAXSKEW,iqaSettings.maxRotationSkew);

		setEditText(R.id.SFmaxSpots,iqaSettings.numberOfSpotsFront);
		setEditText(R.id.SBmaxSpots,iqaSettings.numberOfSpotsBack);

		setEditText(R.id.imagesizeFrontMin,iqaSettings.imagesizeFrontMin);
		setEditText(R.id.imagesizeFrontMax,iqaSettings.imagesizeFrontMax);
		setEditText(R.id.imagesizeBackMin,iqaSettings.imagesizeBackMin);
		setEditText(R.id.imagesizeBackMax,iqaSettings.imagesizeBackMax);

		setEditText(R.id.horizSreakSumPixels, iqaSettings.horizontalStreakSumOfBlackPixels);
		setEditText(R.id.horizSreakLineWidth, iqaSettings.horizontalStreakLineWidth);
		setEditText(R.id.horizSreakNumLines, iqaSettings.horizontalStreakNumLines);

		setEditText(R.id.carbonStripSumPixels, iqaSettings.carbonStripSumOfBlackPixels);
		setEditText(R.id.carbonStripLineWidth, iqaSettings.carbonStripLineWidth);
		setEditText(R.id.carbonStripNumLines, iqaSettings.carbonStripNumLines);

		setEditText(R.id.piggyBackDistance, iqaSettings.piggyBackMaxWidth);
		setEditText(R.id.piggyBackLocation, iqaSettings.piggyBackMaxAR);
		setEditText(R.id.piggyBackHeight, iqaSettings.piggyBackMaxHeight);
		setEditText(R.id.piggyMinAR, iqaSettings.piggyBackMinAR);

	}

	

	public  void saveEditTextValues() {
		
		Context context = getApplicationContext();
		ShowcasePreferences.setIQAMode(context,true);

		Editor editor = context.getSharedPreferences(ShowcasePreferences.KEY,Context.MODE_PRIVATE).edit();
		 
		// front corner data
		// corner data front TL
		saveEditText(editor,R.id.CFULH,IQAIntentKeys.CORNERDATA_FRONT_TL_H_KEY);
		saveEditText(editor,R.id.CFULW,IQAIntentKeys.CORNERDATA_FRONT_TL_W_KEY);
		saveEditText(editor,R.id.CFULA,IQAIntentKeys.CORNERDATA_FRONT_TL_A_KEY);
		// corner data front TR
		saveEditText(editor,R.id.CFURH,IQAIntentKeys.CORNERDATA_FRONT_TR_H_KEY);
		saveEditText(editor,R.id.CFURW,IQAIntentKeys.CORNERDATA_FRONT_TR_W_KEY);
		saveEditText(editor,R.id.CFURA,IQAIntentKeys.CORNERDATA_FRONT_TR_A_KEY);
		// corner data front BR
		saveEditText(editor,R.id.CFLRH,IQAIntentKeys.CORNERDATA_FRONT_BR_H_KEY);
		saveEditText(editor,R.id.CFLRW,IQAIntentKeys.CORNERDATA_FRONT_BR_W_KEY);
		saveEditText(editor,R.id.CFLRA,IQAIntentKeys.CORNERDATA_FRONT_BR_A_KEY);
		// corner data front BL
		saveEditText(editor,R.id.CFLLH,IQAIntentKeys.CORNERDATA_FRONT_BL_H_KEY);
		saveEditText(editor,R.id.CFLLW,IQAIntentKeys.CORNERDATA_FRONT_BL_W_KEY);
		saveEditText(editor,R.id.CFLLA,IQAIntentKeys.CORNERDATA_FRONT_BL_A_KEY);
	
		// back corner data
		// corner data back TL
		saveEditText(editor,R.id.CBULH,IQAIntentKeys.CORNERDATA_BACK_TL_H_KEY);
		saveEditText(editor,R.id.CBULW,IQAIntentKeys.CORNERDATA_BACK_TL_W_KEY);
		saveEditText(editor,R.id.CBULA,IQAIntentKeys.CORNERDATA_BACK_TL_A_KEY);
		// corner data back TR
		saveEditText(editor,R.id.CBURH,IQAIntentKeys.CORNERDATA_BACK_TR_H_KEY);
		saveEditText(editor,R.id.CBURW,IQAIntentKeys.CORNERDATA_BACK_TR_W_KEY);
		saveEditText(editor,R.id.CBURA,IQAIntentKeys.CORNERDATA_BACK_TR_A_KEY);
		// corner data back BR
		saveEditText(editor,R.id.CBLRH,IQAIntentKeys.CORNERDATA_BACK_BR_H_KEY);
		saveEditText(editor,R.id.CBLRW,IQAIntentKeys.CORNERDATA_BACK_BR_W_KEY);
		saveEditText(editor,R.id.CBLRA,IQAIntentKeys.CORNERDATA_BACK_BR_A_KEY);
		// corner data back BL
		saveEditText(editor,R.id.CBLLH,IQAIntentKeys.CORNERDATA_BACK_BL_H_KEY);
		saveEditText(editor,R.id.CBLLW,IQAIntentKeys.CORNERDATA_BACK_BL_W_KEY);
		saveEditText(editor,R.id.CBLLA,IQAIntentKeys.CORNERDATA_BACK_BL_A_KEY);
			
		// edge data
		// edge data Top
		saveEditText(editor,R.id.ETH,IQAIntentKeys.EDGE_DATA_T_H_KEY);
		saveEditText(editor,R.id.ETW,IQAIntentKeys.EDGE_DATA_T_W_KEY);
		saveEditText(editor,R.id.ETA,IQAIntentKeys.EDGE_DATA_T_A_KEY);
		// edge data Right
		saveEditText(editor,R.id.ERH,IQAIntentKeys.EDGE_DATA_R_H_KEY);
		saveEditText(editor,R.id.ERW,IQAIntentKeys.EDGE_DATA_R_W_KEY);
		saveEditText(editor,R.id.ERA,IQAIntentKeys.EDGE_DATA_R_A_KEY);
		// corner data Bottom
		saveEditText(editor,R.id.EBH,IQAIntentKeys.EDGE_DATA_B_H_KEY);
		saveEditText(editor,R.id.EBW,IQAIntentKeys.EDGE_DATA_B_W_KEY);
		saveEditText(editor,R.id.EBA,IQAIntentKeys.EDGE_DATA_B_A_KEY);
		// corner data Left
		saveEditText(editor,R.id.ELH,IQAIntentKeys.EDGE_DATA_L_H_KEY);
		saveEditText(editor,R.id.ELW,IQAIntentKeys.EDGE_DATA_L_W_KEY);
		saveEditText(editor,R.id.ELA,IQAIntentKeys.EDGE_DATA_L_A_KEY);

		
		saveEditText(editor,R.id.DFMax,IQAIntentKeys.MAX_DARNESS_FRONT_KEY);
		saveEditText(editor,R.id.DFMin,IQAIntentKeys.MIN_DARNESS_FRONT_KEY);
		saveEditText(editor,R.id.DBMax,IQAIntentKeys.MAX_DARNESS_BACK_KEY);
		saveEditText(editor,R.id.DBMin,IQAIntentKeys.MIN_DARNESS_BACK_KEY);

		saveEditText(editor,R.id.MAXSKEW,IQAIntentKeys.MAX_SKEW_KEY);

		int value = Integer.valueOf(((EditText) findViewById(R.id.SFmaxSpots)).getText().toString());
		editor.putInt(IQAIntentKeys.MAX_SPOTS_FRONT_KEY, value);
		
		value = Integer.valueOf(((EditText) findViewById(R.id.SBmaxSpots)).getText().toString());
		editor.putInt(IQAIntentKeys.MAX_SPOTS_BACK_KEY, value);
		
		saveEditText(editor,R.id.imagesizeFrontMin,IQAIntentKeys.MIN_IMAGE_SIZE_FRONT_KEY);
		saveEditText(editor,R.id.imagesizeFrontMax,IQAIntentKeys.MAX_IMAGE_SIZE_FRONT_KEY);
		saveEditText(editor,R.id.imagesizeBackMin,IQAIntentKeys.MIN_IMAGE_SIZE_BACK_KEY);
		saveEditText(editor,R.id.imagesizeBackMax,IQAIntentKeys.MAX_IMAGE_SIZE_BACK_KEY);

		saveEditText(editor,R.id.horizSreakSumPixels, IQAIntentKeys.HORIZONTAL_STREAK_SUM_BLACK_PIXELS);
		saveEditText(editor,R.id.horizSreakLineWidth, IQAIntentKeys.HORIZONTAL_STREAK_LINE_WIDTH);
		saveEditText(editor,R.id.horizSreakNumLines, IQAIntentKeys.HORIZONTAL_STREAK_NUM_LINES);

		saveEditText(editor, R.id.carbonStripSumPixels, IQAIntentKeys.CARBON_STRIP_SUM_BLACK_PIXELS);
		saveEditText(editor, R.id.carbonStripLineWidth, IQAIntentKeys.CARBON_STRIP_LINE_WIDTH);
		saveEditText(editor, R.id.carbonStripNumLines, IQAIntentKeys.CARBON_STRIP_NUM_LINES);

		saveEditText(editor, R.id.piggyBackDistance, IQAIntentKeys.PIGGY_BACK_WIDTH_THRESHOLD);
		saveEditText(editor, R.id.piggyBackLocation, IQAIntentKeys.PIGGY_BACK_LOCATION_THRESHOLD);

		editor.commit();
	}
	
	
	private void saveEditText(Editor editor, int resID, String cornerdataFrontTlHKey) {
		 String strValue = ((EditText) findViewById(resID)).getText().toString();
		if(resID == R.id.SFmaxSpots || resID == R.id.SBmaxSpots ||
				resID == R.id.horizSreakNumLines || resID == R.id.horizSreakSumPixels || resID == R.id.horizSreakLineWidth ||
				resID == R.id.carbonStripNumLines || resID == R.id.carbonStripSumPixels || resID == R.id.carbonStripLineWidth) {
			Integer value = Integer.valueOf(strValue);
			editor.putInt(cornerdataFrontTlHKey, value);
		}
		else {
			Float value = Float.valueOf(strValue);
			editor.putFloat(cornerdataFrontTlHKey, value);
		}
	}

	private void setEditText(int resID, float value) {
		String strValue = String.format(Locale.ENGLISH, "%.2f", value);
		if (resID == R.id.DFMax || resID == R.id.DFMin
				|| resID == R.id.DBMax || resID == R.id.DBMin)
			strValue = String.format(Locale.ENGLISH, "%.4f", value);
		if(resID == R.id.SFmaxSpots || resID == R.id.SBmaxSpots ||
				resID == R.id.horizSreakNumLines || resID == R.id.horizSreakSumPixels || resID == R.id.horizSreakLineWidth ||
				resID == R.id.carbonStripNumLines || resID == R.id.carbonStripSumPixels || resID == R.id.carbonStripLineWidth)
			strValue = (int)value +"";
		((EditText) findViewById(resID)).setText(strValue);		
	}
	
	

//	private static Float getIQADefaultValue(int resID) {
//		switch (resID) {
//		case R.id.CBULW:
//		case R.id.CBULH:
//		case R.id.CBURW:
//		case R.id.CBURH:
//		case R.id.CBLRW:
//		case R.id.CBLRH:
//			return 3.00F;
//		case R.id.CFULW:
//		case R.id.CFURW:
//		case R.id.CFULH:
//		case R.id.CFURH:
//		case R.id.CFLLW:
//		case R.id.CFLLH:
//		case R.id.CBULA:
//		case R.id.CBURA:
//		case R.id.CBLRA:
//			return 1.00F;
//		case R.id.CFLRW:
//		case R.id.CFLRH:
//		case R.id.CBLLW:
//		case R.id.CBLLH:
//		case R.id.ETW:
//		case R.id.ETH:
//		case R.id.EBW:
//		case R.id.EBH:
//		case R.id.ELW:
//		case R.id.ELH:
//		case R.id.ERW:
//		case R.id.ERH:
//			return 0.80F;
//		case R.id.CFULA:
//		case R.id.CFURA:
//		case R.id.CFLLA:
//			return 0.40F;
//		case R.id.CFLRA:
//		case R.id.CBLLA:
//		case R.id.ETA:
//		case R.id.EBA:
//		case R.id.ELA:
//		case R.id.ERA:
//			return 0.30F;
//		case R.id.MAXSKEW:
//			return 7.5F;
//		case R.id.DFMax:
//			return 0.9F;
//		case R.id.DFMin:
//			return 0.009F;
//		case R.id.DBMax:
//			return 0.98F;
//		case R.id.DBMin:
//			return 0.0038F;
//		case R.id.SBmaxSpots:
//		case R.id.SFmaxSpots:
//			return 5852F;
//		default:
//			return 0.4F;
//		}
//	}

	@Override
	protected void ensureActionBar() {
		demoActionBar = (DemoActionBar) findViewById(R.id.demoActionBar_ref);
		demoActionBar.ensureUI(ActionBarContext.IQASETTINGS);

	}

}
