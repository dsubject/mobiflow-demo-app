package com.topimagesystems.sample;

import android.util.Log;

public class Config {
	// >>>>>>// production
//d
	public final static int LOG_LEVEL = Log.DEBUG;
	public final static String BUGSENSE_APIKEY = "7d01881f";
	public final static String ZUBHIUM_SECRET_KEY = "d756730f64860a63303e7b5beb05c8";
	// <<<<<//

	// >>>>>>// development
	// public final static boolean DEFAULT_IS_DEBUG = true;;
	// public final static int LOG_LEVEL = Log.DEBUG;
	// public final static String BUGSENSE_APIKEY = null;
	// public final static String ZUBHIUM_SECRET_KEY = null;
	// <<<<<//

	public static final int DEVICE_TYPE_ANDROID = 2;
	/** ocr related values */
	public final static int MAXIMUM_NUMBER_OF_MICR_RETRIES = 5;
	public final static int DEFAULT_ROW_SCOPE_FROM = 10;
	public final static int DEFAULT_ROW_SCOPE_TO = 50;

	public final static float DEFAULT_VALIDATION_MAX_RATIO_HEIGHT_WIDTH = 0.65f;
	public final static float DEFAULT_VALIDATION_MIN_RATIO_HEIGHT_WIDTH = 0.35f;
	public final static int ORIENTATION_THRESHOLD_X = 30;
	public final static int ORIENTATION_THRESHOLD_X_MIN_1 = 330;
	public final static int ORIENTATION_THRESHOLD_X_MAX_1 = 360;
	public final static int ORIENTATION_THRESHOLD_Y = 30;
	public final static int ORIENTATION_THRESHOLD_Z = 30;
	public final static float RATIO_TRESHHOLD = 0.10f;

	/***
	 * Default IQADefaults Image Settings (start)
	 */
	public static class IQADefaults {
		
		
		public final static boolean IS_IQA_MODE = false;

		public final static float CORNERDATA_FRONT_TL_W = 1.0f;
		public final static float CORNERDATA_FRONT_TL_H = 1.0f;
		public final static float CORNERDATA_FRONT_TL_A = 0.4f;

		public final static float CORNERDATA_FRONT_TR_W = 1.0f;
		public final static float CORNERDATA_FRONT_TR_H = 1.0f;
		public final static float CORNERDATA_FRONT_TR_A = 0.4f;

		public final static float CORNERDATA_FRONT_BL_W = 1.0f;
		public final static float CORNERDATA_FRONT_BL_H = 1.0f;
		public final static float CORNERDATA_FRONT_BL_A = 0.4f;

		public final static float CORNERDATA_FRONT_BR_W = 0.8f;
		public final static float CORNERDATA_FRONT_BR_H = 0.8f;
		public final static float CORNERDATA_FRONT_BR_A = 0.3f;
		//
		public final static float CORNERDATA_BACK_TL_W = 3.0f;
		public final static float CORNERDATA_BACK_TL_H = 3.0f;
		public final static float CORNERDATA_BACK_TL_A = 1.0f;

		public final static float CORNERDATA_BACK_TR_W = 3.0f;
		public final static float CORNERDATA_BACK_TR_H = 3.0f;
		public final static float CORNERDATA_BACK_TR_A = 1.0f;

		public final static float CORNERDATA_BACK_BL_W = 0.8f;
		public final static float CORNERDATA_BACK_BL_H = 0.8f;
		public final static float CORNERDATA_BACK_BL_A = 0.3f;

		public final static float CORNERDATA_BACK_BR_W = 3.0f;
		public final static float CORNERDATA_BACK_BR_H = 3.0f;
		public final static float CORNERDATA_BACK_BR_A = 1.0f;
		// ---edge
		public final static float EDGE_DATA_T_W = 0.8f;
		public final static float EDGE_DATA_T_H = 0.8f;
		public final static float EDGE_DATA_T_A = 0.3f;

		public final static float EDGE_DATA_B_W = 0.8f;
		public final static float EDGE_DATA_B_H = 0.8f;
		public final static float EDGE_DATA_B_A = 0.3f;

		public final static float EDGE_DATA_L_W = 0.8f;
		public final static float EDGE_DATA_L_H = 0.8f;
		public final static float EDGE_DATA_L_A = 0.3f;

		public final static float EDGE_DATA_R_W = 0.8f;
		public final static float EDGE_DATA_R_H = 0.8f;
		public final static float EDGE_DATA_R_A = 0.3f;

		public final static float MAX_SKEW = 7.5f;

		public final static float MAX_DARNESS_FRONT = 0.9f;
		public final static float MIN_DARNESS_FRONT = 0.009f;
		public final static float MAX_DARNESS_BACK = 0.98f;
		public final static float MIN_DARNESS_BACK = 0.0038f;

		public final static int MAX_SPOTS_FRONT = 5852;
		public final static int MAX_SPOTS_BACK = 5852;
		
		public final static float MIN_IMAGE_SIZE_FRONT = 0.5f;
		public final static float MAX_IMAGE_SIZE_FRONT = 200f;
		public final static float MIN_IMAGE_SIZE_BACK= 0.5f;
		public final static float MAX_IMAGE_SIZE_BACK = 200f;

		public final static int HORIZONTAL_STREAK_SUM_BLACK_PIXELS = 25;
		public final static int HORIZONTAL_STREAK_LINE_WIDTH = 12;
		public final static int HORIZONTAL_STREAK_NUM_LINES = 3;

		public final static int CARBON_STRIP_SUM_BLACK_PIXELS = 25;
		public final static int CARBON_STRIP_LINE_WIDTH = 12;
		public final static int CARBON_STRIP_NUM_LINES = 1;

		public final static float PIGGY_DISTANCE_THRESHOLD = 0.5f;
		public final static float PIGGY_LOCATION_THRESHOLD = 3.1f;

	}

	/**
	 * Default IQADefaults Image Settings (end)
	 * */

	public final static int HTTP_CONNECTION_TIMEOUT = 40;
	public final static int HTTP_SOCKET_TIMEOUT = 70;
}