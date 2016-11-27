package com.topimagesystems.sample.ui;

import org.json.JSONException;

import com.topimagesystems.Common.OCRType;
import com.topimagesystems.intent.CaptureIntent.IQAIntentKeys;
import com.topimagesystems.intent.IQASettingsIntent;
import com.topimagesystems.sample.Config;
import com.topimagesystems.sample.Config.IQADefaults;
import com.topimagesystems.util.StringUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *
 */
/**
 * @author Boaz Garty
 * 
 */
public class ShowcasePreferences {
	public static final String KEY = "mobicheck-session";
	/***/
	public static final String PREFERENCE_USER_TOKEN = "PREFERENCE_USER_TOKEN";
	public static final String PREFERENCE_USER_ID = "PREFERENCE_USER_ID";
	public static final String PREFERENCE_USERNAME = "PREFERENCE_USERNAME";
	public static final String PREFERENCE_DOMAIN = "PREFERENCE_DOMAIN";
	public static final String PREFERENCE_IS_POLICY_CONFIRMED = "PREFERENCE_IS_POLICY_CONFIRMED";
	public static final String PREFERENCE_IS_DEBUG_MODE = "PREFERENCE_IS_DEBUG_MODE";
	public static final String PREFERENCE_IS_OFFLINE_MODE = "PREFERENCE_IS_OFFLINE_MODE";
	public static final String PREFERENCE_IS_BLUR_DETECTION = "IS_BLUR_DETECTION";
	public static final String PREFERENCE_IS_BLUR_DETECTION_ON_BACK = "PREFERENCE_IS_BLUR_DETECTION_ON_BACK";
	public static final String CREATE_GRAY_SCALE = "CREATE_GRAY_SCALE";
	public static final String SHOW_INFO_SCREEN = "SHOW_INFO_SCREEN";
	public static final String SET_GUIDELINES_INDICATORS = "SET_GUIDELINES_INDICATORS";
	public static final String SHOULD_OUTPUT_ORIGINAL_IMAGE = "SHOULD_OUTPUT_ORIGINAL_IMAGE";
	public static final String SHOULD_OUTPUT_ORIGINAL_COLOR_IMAGE = "SHOULD_OUTPUT_ORIGINAL_COLOR_IMAGE";
	public static final String SHOULD_OUTPUT_BW_IMAGE = "SHOULD_OUTPUT_BW_IMAGE";
	public static final String MULTI_CAPTURE = "MULTI_CAPTURE";
	public static final String DYNAMIC_CAPTURE = "DYNAMIC_CAPTURE";
	public static final String SOFT_CAPTURE = "SOFT_CAPTURE";
	public static final String ENABLE_BARCODE = "ENABLE_BARCODE";
	public static final String ENABLE_QRCODE = "ENABLE_QRCODE";
	public static final String SET_COUNTDOWN_SOUND = "SET_COUNTDOWN_SOUND";
	public static final String SHOW_COUNTDOWN = "SHOW_COUNTDOWN";
	public static final String BINARIZATION_TYPE = "BINARIZATION_TYPE";
	public static final String PREFERENCE_IS_ENCRYPTION = "PREFERENCE_IS_ENCRYPTION";
	public static final String PREFERENCE_IS_MANUAL_CAPTURE = "PREFERENCE_IS_MANUAL_CAPTURE";
	public static final String PREFERENCE_IS_IQA_MODE = "PREFERENCE_IS_IQA_MODE";
	public static final String PREFERENCE_MICR_TYPE = "PREFERENCE_MICR_TYPE";
	public static final String PREFERENCE_MAXIMUM_NUMBER_OF_RETRIES = "PREFERENCE_MAXIMUM_NUMBER_OF_RETRIES";
	public static final String PREFERENCE_ROW_SCOPE_FROM = "PREFERENCE_ROW_SCOPE_FROM";
	public static final String PREFERENCE_ROW_SCOPE_TO = "PREFERENCE_ROW_SCOPE_TO";
	public static final String PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO = "PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO";
	public static final String PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO = "PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO";
	public static final String PREFERENCE_IS_FIRST_RUN = "PREFERENCE_IS_FIRST_RUN";
	public static final String PREFERENCE_SCAN_FRONT_ONLY = "PREFERENCE_SCAN_FRONT_ONLY";
	public static final String PREFERENCE_SCAN_BACK_ONLY = "PREFERENCE_SCAN_BACK_ONLY";
	public static final String PREFERENCE_CUSTOM_VIEW_UI = "PREFERENCE_CUSTOM_VIEW_UI";
	public static final String IS_TEST_MODE = "IS_TEST_MODE";

	// parameters used for license id capture
	public static final String PREFERENCE_IS_BACK_BINARIZATION_SAME_AS_FRONT = "PREFERENCE_IS_BACK_BINARIZATION_SAME_AS_FRONT";
	public static final String PREFERENCE_IS_USE_CUSTOM_ALGORITHM_ON_BACK = "PREFERENCE_IS_USE_CUSTOM_ALGORITHM_ON_BACK";
	public static final String PREFERENCE_OUTPUT_HEIHGT_IN_INCH = "PREFERENCE_OUTPUT_HEIHGT_IN_INCH";
	public static final String PREFERENCE_OUTPUT_WIDTH_IN_INCH = "PREFERENCE_OUTPUT_WIDTH_IN_INCH";
	public static final String PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO_BACK = "PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO_BACK";
	public static final String PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO_BACK = "PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO_BACK";
	public static final String PREFERENCE_COLOR_IMAGE_COMPRESSION = "PREFERENCE_COLOR_IMAGE_COMPRESSION";
	public static final String PREFERENCE_GRAY_SCALE_IMAGE_COMPRESSION = "PREFERENCE_GRAY_SCALE_IMAGE_COMPRESSION";
	public static final String PREFERENCE_SOFT_CAPTURE_THRESHOLD = "PREFERENCE_SOFT_CAPTURE_THRESHOLD";
	public static final String PREFERENCE_USE_MAX_RESOLITION = "PREFERENCE_USE_MAX_RESOLITION";
	public static final String PREFERENCE_USE_CAMERA_API_2 = "PREFERENCE_USE_CAMERA_API_2";
	public static final String ENABLE_TAP_TO_FOCUS = "ENABLE_TAP_TO_FOCUS";
	public static final String IS_VIDEO_MODE = "IS_VIDEO_MODE";
	public static final String INSTRUCTIONS_SLIDES = "INSTRUCTIONS_SLIDES";
	public static final String MICR_POSITION = "MICR_POSITION";
	public static final String ASPECT_RATIO_MIN = "ASPECT_RATIO_MIN";
	public static final String ASPECT_RATIO_MAX = "ASPECT_RATIO_MAX";
	public static SharedPreferences restorePreferences(Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return savedSession;
	}

	public static void clear(Context context) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isFirstRun(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_FIRST_RUN, true);
	}

	/**
	 * @param context
	 * @param isFirstRun
	 */
	public static void setIsFirstRun(Context context, boolean isFirstRun) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_FIRST_RUN, isFirstRun);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static String getUserToken(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getString(PREFERENCE_USER_TOKEN, "");
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setUserToken(Context context, String userToken) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(PREFERENCE_USER_TOKEN, userToken);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static String getUserId(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getString(PREFERENCE_USER_ID, "");
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setUserId(Context context, String userId) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(PREFERENCE_USER_ID, userId);
		editor.commit();
	}


	public static int getMicrPosition(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getInt(MICR_POSITION, 1);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setMicrPosition(Context context, int micrPosition) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putInt(MICR_POSITION, micrPosition);
		editor.commit();
	}


	/**
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getString(PREFERENCE_USERNAME, "");
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setUserName(Context context, String username) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(PREFERENCE_USERNAME, username);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static String getDomain(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getString(PREFERENCE_DOMAIN, "");
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setDomain(Context context, String domain) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(PREFERENCE_DOMAIN, domain);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isPolicyConfirmed(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_POLICY_CONFIRMED, false);
	}

	/**
	 * @param context
	 * @param b
	 */
	public static void setIsPolicyConfirmed(Context context, boolean b) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_POLICY_CONFIRMED, b);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isDebugMode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_DEBUG_MODE, false);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setIsDebugMode(Context context, boolean isDebugMode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_DEBUG_MODE, isDebugMode);
		editor.commit();
	}

	
	public static void setShouldOutputColoredImage(Context context, boolean outputColoredImage) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SHOULD_OUTPUT_ORIGINAL_COLOR_IMAGE, outputColoredImage);
		editor.commit();
	}
	
	public static boolean getShouldOutputColoredImage(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SHOULD_OUTPUT_ORIGINAL_COLOR_IMAGE, true);
	}
	
	public static void setShouldOutputBWImage(Context context, boolean outputBWImage) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SHOULD_OUTPUT_BW_IMAGE, outputBWImage);
		editor.commit();
	}
	
	public static boolean getShouldOutputBWImage(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SHOULD_OUTPUT_BW_IMAGE, true);
	}

	
	
	public static void setIsVideoMode(Context context, boolean isVideoMode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(IS_VIDEO_MODE, isVideoMode);
		editor.commit();
	}


	public static float getMinAspectRatio(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(ASPECT_RATIO_MIN, 0.35f);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setMinAspectRatio(Context context, float minAR) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(ASPECT_RATIO_MIN, minAR);
		editor.commit();
	}



	public static float getMaxAspectRatio(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(ASPECT_RATIO_MAX, 0.52f);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setMaxAspectRatio(Context context, float maxAR) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(ASPECT_RATIO_MAX, maxAR);
		editor.commit();
	}

	
	public static boolean getIsVideoMode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(IS_VIDEO_MODE, false);
	}

	
	
	public static void setIsMultiCapture(Context context, boolean isMultiCapture) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(MULTI_CAPTURE, isMultiCapture);
		editor.commit();
	}
	
	public static boolean getIsMultiCapture(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(MULTI_CAPTURE, false);
	}
	
	
	public static void setIsDynamicCapture(Context context, boolean isMultiCapture) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(DYNAMIC_CAPTURE, isMultiCapture);
		editor.commit();
	}
	
	public static boolean getIsDynamicCapture(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(DYNAMIC_CAPTURE, false);
	}
	
	
	public static void setIsSoftCapture(Context context, boolean isSoftCapture) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SOFT_CAPTURE, isSoftCapture);
		editor.commit();
	}
	
	public static boolean getIsSoftCapture(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SOFT_CAPTURE, false);
	}
	
	

	public static void setIsEnableBarcode(Context context, boolean isEnableBarcode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(ENABLE_BARCODE, isEnableBarcode);
		editor.commit();
	}
	
	public static boolean getIsEnableBarcode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(ENABLE_BARCODE, false);
	}
	
	public static void setIsEnableQRCode(Context context, boolean isEnableQRcode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(ENABLE_QRCODE, isEnableQRcode);
		editor.commit();
	}
	
	public static boolean getIsEnableQRCode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(ENABLE_QRCODE, false);
	}

	
	
	/**
	 * @param context
	 * @return
	 */
	public static boolean isOfflineMode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_OFFLINE_MODE, true);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setIsOfflineMode(Context context, boolean isOfflineMode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_OFFLINE_MODE, isOfflineMode);
		editor.commit();
	}


	public static void setCreateGrayscale(Context context,boolean createGrayScaleImage) {

		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(CREATE_GRAY_SCALE,createGrayScaleImage);
		editor.commit();
	}
	

	public static boolean getCreateGrayscale(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(CREATE_GRAY_SCALE, true);
	}

	public static boolean getInfoScreen(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SHOW_INFO_SCREEN, false);
	}

	public static void setInfoScreen(Context context, boolean infoScreenEnable) {

		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SHOW_INFO_SCREEN, infoScreenEnable);
		editor.commit();

	}

	public static boolean getGuideLinesIndicators(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SET_GUIDELINES_INDICATORS, true);
	}

	public static void setGuideLinesIndicators(Context context, boolean isGuidlinesEnabled) {

		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SET_GUIDELINES_INDICATORS, isGuidlinesEnabled);
		editor.commit();

	}

	public static void setOutputOriginalImage(Context context, boolean showOriginalImage) {

		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SHOULD_OUTPUT_ORIGINAL_IMAGE, showOriginalImage);
		editor.commit();
	}

	public static boolean getOutputOriginalImage(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SHOULD_OUTPUT_ORIGINAL_IMAGE, true);
	}

	public static void setCountDownSound(Context context,boolean isCountDownEnable) {		
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SET_COUNTDOWN_SOUND, isCountDownEnable);
		editor.commit();
	}
	public static boolean getCountDownSound(Context context) {		
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SET_COUNTDOWN_SOUND, false);
	}
	
	public static void setShowCountDown(Context context,boolean isShowCountDown) {				
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(SHOW_COUNTDOWN, isShowCountDown);		
		editor.commit();	
		
	}
	
	public static boolean getShowCountDown(Context context) {		
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(SHOW_COUNTDOWN, false);
	}
	

	public static void setIsTestMode(Context context,boolean isTestMode) {		
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(IS_TEST_MODE, isTestMode);		
		editor.commit();
	}
	
	public static boolean getIsTestMode(Context context) {		
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(IS_TEST_MODE, false);
	}
	
	



	/**
	 * @param context
	 * @param userToken
	 */
	public static void setIsBlurDetection(Context context, boolean isBlurDetection) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_BLUR_DETECTION, isBlurDetection);
		editor.commit();
	}
	
	/**
	 * @param context
	 * @param userToken
	 */
	public static boolean getIsBlurDetection(Context context,boolean defaultValue) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);		
		return prefs.getBoolean(PREFERENCE_IS_BLUR_DETECTION, defaultValue);
	}



	/**
	 * @param context
	 * @param userToken
	 */
	public static void setIsBlurDetectionOnBack(Context context, boolean isBlurDetection) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_BLUR_DETECTION_ON_BACK, isBlurDetection);
		editor.commit();
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static boolean getIsBlurDetectionOnBack(Context context,boolean defaultValue) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_BLUR_DETECTION_ON_BACK, defaultValue);
	}



	/**
	 * @param context
	 * @return
	 */
	public static boolean isEncryption(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_ENCRYPTION, false);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setIsEncryption(Context context, boolean isEncryption) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_ENCRYPTION, isEncryption);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isManualMode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_MANUAL_CAPTURE, false);
	}

	/**
	 * @param context
	 * @return isIQAEnabled
	 */
	public static boolean isIQAMode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_IQA_MODE, IQADefaults.IS_IQA_MODE);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setManualCaptureMode(Context context, boolean isManualCaptureMode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_MANUAL_CAPTURE, isManualCaptureMode);
		editor.commit();
	}

	/**
	 * @param context
	 * @param isIQAMode
	 */
	public static void setIQAMode(Context context, boolean isIQAMode) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_IQA_MODE, isIQAMode);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static OCRType getCurrentMICRType(Context context) {
		OCRType result = OCRType.OFF;
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		final String micrTypeValue = prefs.getString(PREFERENCE_MICR_TYPE, "");
		if (!StringUtils.isEmptyOrNull(micrTypeValue)) {
			result = OCRType.valueOf(micrTypeValue);
		}
		return result;
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setCurrentMICRType(Context context, OCRType micrType) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(PREFERENCE_MICR_TYPE, micrType.name());
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static int getMaxNumberOfRetries(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getInt(PREFERENCE_MAXIMUM_NUMBER_OF_RETRIES, Config.MAXIMUM_NUMBER_OF_MICR_RETRIES);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setMaxNumberOfRetries(Context context, int maxNumberOfRetries) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putInt(PREFERENCE_MAXIMUM_NUMBER_OF_RETRIES, maxNumberOfRetries);
		editor.commit();
	}
	
	
	public static boolean getScanFrontOnly(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_SCAN_FRONT_ONLY, false);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setScanFrontOnly(Context context, boolean ScanFrontOnly) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_SCAN_FRONT_ONLY, ScanFrontOnly);
		editor.commit();
	}
	
	
	public static boolean getScanBackOnly(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_SCAN_BACK_ONLY, false);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setScanBackOnly(Context context, boolean ScanBackOnly) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_SCAN_BACK_ONLY, ScanBackOnly);
		editor.commit();
	}
	
	
	public static boolean getIsCustomView(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_CUSTOM_VIEW_UI, false);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setIsCustomView(Context context, boolean useCustomView) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_CUSTOM_VIEW_UI, useCustomView);
		editor.commit();
	}




	/**
	 * @param context
	 * @return
	 */
	public static int getValidFrom(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getInt(PREFERENCE_ROW_SCOPE_FROM, Config.DEFAULT_ROW_SCOPE_FROM);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setValidFrom(Context context, int validFrom) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putInt(PREFERENCE_ROW_SCOPE_FROM, validFrom);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static int getValidTo(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getInt(PREFERENCE_ROW_SCOPE_TO, Config.DEFAULT_ROW_SCOPE_TO);
	}

	/**
	 * @param context
	 * @param userToken
	 */
	public static void setValidTo(Context context, int validTo) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putInt(PREFERENCE_ROW_SCOPE_TO, validTo);
		editor.commit();
	}

	/** ocr max ratio validation */
	public static float getOCRValidationHWMaxRatio(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO, Config.DEFAULT_VALIDATION_MAX_RATIO_HEIGHT_WIDTH);
	}

	public static void setOCRMaxRatioValidation(Context context, float maxRatio) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO, maxRatio);
		editor.commit();
	}

	/** ocr min ratio validation */
	public static float getOCRValidationHWMinRatio(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO, Config.DEFAULT_VALIDATION_MIN_RATIO_HEIGHT_WIDTH);
	}

	public static void setOCRMinRatioValidation(Context context, float minRatio) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO, minRatio);
		editor.commit();
	}

	/** output height in inch **/
	public static float getOutputHeightInInch(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_OUTPUT_HEIHGT_IN_INCH, -1.0f);
	}

	public static void setOutputHeightInInch(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_OUTPUT_HEIHGT_IN_INCH, value);
		editor.commit();
	}

	/** output width in inch **/
	public static float getOutputWidthInInch(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_OUTPUT_WIDTH_IN_INCH, -1.0f);
	}

	public static void setOutputWidthInInch(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_OUTPUT_WIDTH_IN_INCH, value);
		editor.commit();
	}

	/** is back binarization */
	public static boolean isBackBinarizationAsFront(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_BACK_BINARIZATION_SAME_AS_FRONT, false);
	}

	public static void setIsBackBinarizationAsFron(Context context, boolean value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_BACK_BINARIZATION_SAME_AS_FRONT, value);
		editor.commit();
	}

	/** is run OCR on front */
	public static boolean isUseCustomAlgorithmOnBack(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_USE_CUSTOM_ALGORITHM_ON_BACK, false);
	}

	public static void setIsUseCustomAlgorithmOnBack(Context context, boolean value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_USE_CUSTOM_ALGORITHM_ON_BACK, value);
		editor.commit();
	}

	/** ocr max ratio validation back */
	public static float getOCRValidationHWMaxRatioBack(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO_BACK, -1.0f);
	}

	public static void setOCRMaxRatioValidationBack(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_OCR_VALIDATION_HW_MAX_RATIO_BACK, value);
		editor.commit();
	}

	/** ocr min ratio validation back */
	public static float getOCRValidationHWMinRatioBack(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO_BACK, -1.0f);
	}

	public static void setOCRMinRatioValidationBack(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_OCR_VALIDATION_HW_MIN_RATIO_BACK, value);
		editor.commit();
	}

	public static boolean getIQAEnabled(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_IS_IQA_MODE, IQADefaults.IS_IQA_MODE);
	}

	public static void setIQAEnabled(Context context, boolean value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_IS_IQA_MODE, value);
		editor.commit();
	}


	public static float getColorImageCompression(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_COLOR_IMAGE_COMPRESSION, 1.0f);
	}

	public static void setColorImageCompression(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_COLOR_IMAGE_COMPRESSION, value);
		editor.commit();
	}
	


	public static float getGrayScaleImageCompression(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_GRAY_SCALE_IMAGE_COMPRESSION, 1.0f);
	}

	public static void setGrayScaleImageCompression(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_GRAY_SCALE_IMAGE_COMPRESSION, value);
		editor.commit();
	}

	public static float getSoftCaptureThreshold(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getFloat(PREFERENCE_SOFT_CAPTURE_THRESHOLD, 0.0f);
	}

	public static void setSoftCaptureThreshold(Context context, float value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putFloat(PREFERENCE_SOFT_CAPTURE_THRESHOLD, value);
		editor.commit();
	}
	
	
	public static boolean getUsemaxResolutionStills(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_USE_MAX_RESOLITION, false);
	}

	public static void setUsemaxResolutionStills(Context context, boolean value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_USE_MAX_RESOLITION, value);
		editor.commit();
	}

	public static boolean getUseCameraAPI2(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_USE_CAMERA_API_2, false);
	}

	public static void setUseCameraAPI2(Context context, boolean value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_USE_CAMERA_API_2, value);
		editor.commit();
	}

	public static boolean getEnableTapToFocus(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return prefs.getBoolean(ENABLE_TAP_TO_FOCUS, false);
	}

	public static void setEnableTapToFocus(Context context, boolean value) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(ENABLE_TAP_TO_FOCUS, value);
		editor.commit();
	}
	
	
	/**
	 * @param context
	 * @param payload
	 * @throws JSONException
	 */

	public static IQASettingsIntent getIQASettings(Context context) {

		IQASettingsIntent iqaSettings = new IQASettingsIntent();

		SharedPreferences prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		// iqaSettings.isIQAMode = prefs.getBoolean(PREFERENCE_IS_IQA_MODE,
		// IQADefaults.IS_IQA_MODE);
		// front corner data
		// corner data front TL
		iqaSettings.cornerDataFrontTLH = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_TL_H_KEY,
				IQADefaults.CORNERDATA_FRONT_TL_H);
		iqaSettings.cornerDataFrontTLW = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_TL_W_KEY,
				IQADefaults.CORNERDATA_FRONT_TL_W);
		iqaSettings.cornerDataFrontTLA = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_TL_A_KEY,
				IQADefaults.CORNERDATA_FRONT_TL_A);
		// corner data front TR
		iqaSettings.cornerDataFrontTRH = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_TR_H_KEY,
				IQADefaults.CORNERDATA_FRONT_TR_H);
		iqaSettings.cornerDataFrontTRW = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_TR_W_KEY,
				IQADefaults.CORNERDATA_FRONT_TR_W);
		iqaSettings.cornerDataFrontTRA = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_TR_A_KEY,
				IQADefaults.CORNERDATA_FRONT_TR_A);
		// corner data front BR
		iqaSettings.cornerDataFrontBRH = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_BR_H_KEY,
				IQADefaults.CORNERDATA_FRONT_BR_H);
		iqaSettings.cornerDataFrontBRW = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_BR_W_KEY,
				IQADefaults.CORNERDATA_FRONT_BR_W);
		iqaSettings.cornerDataFrontBRA = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_BR_A_KEY,
				IQADefaults.CORNERDATA_FRONT_BR_A);
		// corner data front BL
		iqaSettings.cornerDataFrontBLH = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_BL_H_KEY,
				IQADefaults.CORNERDATA_FRONT_BL_H);
		iqaSettings.cornerDataFrontBLW = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_BL_W_KEY,
				IQADefaults.CORNERDATA_FRONT_BL_W);
		iqaSettings.cornerDataFrontBLA = prefs.getFloat(IQAIntentKeys.CORNERDATA_FRONT_BL_A_KEY,
				IQADefaults.CORNERDATA_FRONT_BL_A);

		// back corner data
		// corner data front TL
		iqaSettings.cornerDataBackTLH = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_TL_H_KEY,
				IQADefaults.CORNERDATA_BACK_TL_H);
		iqaSettings.cornerDataBackTLW = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_TL_W_KEY,
				IQADefaults.CORNERDATA_BACK_TL_W);
		iqaSettings.cornerDataBackTLA = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_TL_A_KEY,
				IQADefaults.CORNERDATA_BACK_TL_A);
		// corner data front TR
		iqaSettings.cornerDataBackTRH = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_TR_H_KEY,
				IQADefaults.CORNERDATA_BACK_TR_H);
		iqaSettings.cornerDataBackTRW = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_TR_W_KEY,
				IQADefaults.CORNERDATA_BACK_TR_W);
		iqaSettings.cornerDataBackTRA = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_TR_A_KEY,
				IQADefaults.CORNERDATA_BACK_TR_A);
		// corner data front BR
		iqaSettings.cornerDataBackBRH = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_BR_H_KEY,
				IQADefaults.CORNERDATA_BACK_BR_H);
		iqaSettings.cornerDataBackBRW = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_BR_W_KEY,
				IQADefaults.CORNERDATA_BACK_BR_W);
		iqaSettings.cornerDataBackBRA = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_BR_A_KEY,
				IQADefaults.CORNERDATA_BACK_BR_A);
		// corner data front BL
		iqaSettings.cornerDataBackBLH = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_BL_H_KEY,
				IQADefaults.CORNERDATA_BACK_BL_H);
		iqaSettings.cornerDataBackBLW = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_BL_W_KEY,
				IQADefaults.CORNERDATA_BACK_BL_W);
		iqaSettings.cornerDataBackBLA = prefs.getFloat(IQAIntentKeys.CORNERDATA_BACK_BL_A_KEY,
				IQADefaults.CORNERDATA_BACK_BL_A);

		// edge data
		iqaSettings.edgeDataTH = prefs.getFloat(IQAIntentKeys.EDGE_DATA_T_H_KEY, IQADefaults.EDGE_DATA_T_H);
		iqaSettings.edgeDataTW = prefs.getFloat(IQAIntentKeys.EDGE_DATA_T_W_KEY, IQADefaults.EDGE_DATA_T_W);
		iqaSettings.edgeDataTA = prefs.getFloat(IQAIntentKeys.EDGE_DATA_T_A_KEY, IQADefaults.EDGE_DATA_T_A);

		iqaSettings.edgeDataRH = prefs.getFloat(IQAIntentKeys.EDGE_DATA_R_H_KEY, IQADefaults.EDGE_DATA_R_H);
		iqaSettings.edgeDataRW = prefs.getFloat(IQAIntentKeys.EDGE_DATA_R_W_KEY, IQADefaults.EDGE_DATA_R_W);
		iqaSettings.edgeDataRA = prefs.getFloat(IQAIntentKeys.EDGE_DATA_R_A_KEY, IQADefaults.EDGE_DATA_R_A);

		iqaSettings.edgeDataBH = prefs.getFloat(IQAIntentKeys.EDGE_DATA_B_H_KEY, IQADefaults.EDGE_DATA_B_H);
		iqaSettings.edgeDataBW = prefs.getFloat(IQAIntentKeys.EDGE_DATA_B_W_KEY, IQADefaults.EDGE_DATA_B_W);
		iqaSettings.edgeDataBA = prefs.getFloat(IQAIntentKeys.EDGE_DATA_B_A_KEY, IQADefaults.EDGE_DATA_B_A);

		iqaSettings.edgeDataLH = prefs.getFloat(IQAIntentKeys.EDGE_DATA_L_H_KEY, IQADefaults.EDGE_DATA_L_H);
		iqaSettings.edgeDataLW = prefs.getFloat(IQAIntentKeys.EDGE_DATA_L_W_KEY, IQADefaults.EDGE_DATA_L_W);
		iqaSettings.edgeDataLA = prefs.getFloat(IQAIntentKeys.EDGE_DATA_L_A_KEY, IQADefaults.EDGE_DATA_L_A);

		iqaSettings.maxDarknessFront = prefs.getFloat(IQAIntentKeys.MAX_DARNESS_FRONT_KEY,
				IQADefaults.MAX_DARNESS_FRONT);
		iqaSettings.minDarknessFront = prefs.getFloat(IQAIntentKeys.MIN_DARNESS_FRONT_KEY,
				IQADefaults.MIN_DARNESS_FRONT);
		iqaSettings.maxDarknessBack = prefs.getFloat(IQAIntentKeys.MAX_DARNESS_BACK_KEY, IQADefaults.MAX_DARNESS_BACK);
		iqaSettings.minDarknessBack = prefs.getFloat(IQAIntentKeys.MIN_DARNESS_BACK_KEY, IQADefaults.MIN_DARNESS_BACK);
		iqaSettings.maxRotationSkew = prefs.getFloat(IQAIntentKeys.MAX_SKEW_KEY, IQADefaults.MAX_SKEW);
		iqaSettings.numberOfSpotsFront = prefs.getInt(IQAIntentKeys.MAX_SPOTS_FRONT_KEY, IQADefaults.MAX_SPOTS_FRONT);
		iqaSettings.numberOfSpotsBack = prefs.getInt(IQAIntentKeys.MAX_SPOTS_BACK_KEY, IQADefaults.MAX_SPOTS_BACK);

		iqaSettings.imagesizeFrontMin = prefs.getFloat(IQAIntentKeys.MIN_IMAGE_SIZE_FRONT_KEY,
				IQADefaults.MIN_IMAGE_SIZE_FRONT);
		iqaSettings.imagesizeFrontMax = prefs.getFloat(IQAIntentKeys.MAX_IMAGE_SIZE_FRONT_KEY,
				IQADefaults.MAX_IMAGE_SIZE_FRONT);
		iqaSettings.imagesizeBackMin = prefs.getFloat(IQAIntentKeys.MIN_IMAGE_SIZE_BACK_KEY,
				IQADefaults.MIN_IMAGE_SIZE_BACK);
		iqaSettings.imagesizeBackMax = prefs.getFloat(IQAIntentKeys.MAX_IMAGE_SIZE_BACK_KEY,
				IQADefaults.MAX_IMAGE_SIZE_BACK);

		iqaSettings.horizontalStreakSumOfBlackPixels = prefs.getInt(IQAIntentKeys.HORIZONTAL_STREAK_SUM_BLACK_PIXELS, IQADefaults.HORIZONTAL_STREAK_SUM_BLACK_PIXELS);
		iqaSettings.horizontalStreakLineWidth = prefs.getInt(IQAIntentKeys.HORIZONTAL_STREAK_LINE_WIDTH, IQADefaults.HORIZONTAL_STREAK_LINE_WIDTH);
		iqaSettings.horizontalStreakNumLines = prefs.getInt(IQAIntentKeys.HORIZONTAL_STREAK_NUM_LINES, IQADefaults.HORIZONTAL_STREAK_NUM_LINES);

		iqaSettings.carbonStripSumOfBlackPixels = prefs.getInt(IQAIntentKeys.CARBON_STRIP_SUM_BLACK_PIXELS, IQADefaults.CARBON_STRIP_SUM_BLACK_PIXELS);
		iqaSettings.carbonStripLineWidth = prefs.getInt(IQAIntentKeys.CARBON_STRIP_LINE_WIDTH, IQADefaults.CARBON_STRIP_LINE_WIDTH);
		iqaSettings.carbonStripNumLines = prefs.getInt(IQAIntentKeys.CARBON_STRIP_NUM_LINES, IQADefaults.CARBON_STRIP_NUM_LINES);

		iqaSettings.piggyBackMaxWidth = prefs.getFloat(IQAIntentKeys.PIGGY_BACK_WIDTH_THRESHOLD, IQADefaults.PIGGY_DISTANCE_THRESHOLD);
		iqaSettings.piggyBackMaxAR = prefs.getFloat(IQAIntentKeys.PIGGY_BACK_LOCATION_THRESHOLD, IQADefaults.PIGGY_LOCATION_THRESHOLD);

		return iqaSettings;
	}

}