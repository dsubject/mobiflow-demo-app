package com.topimagesystems.sample.ui;

import com.topimagesystems.intent.IQASettingsIntent;


public class IqaSettings {
	// >>>>>>// production
	public final boolean DEFAULT_IS_DEBUG = true;;
	public final String BUGSENSE_APIKEY = "7d01881f";
	public final String ZUBHIUM_SECRET_KEY = "d756730f64860a63303e7b5beb05c8";
	// <<<<<//

	// >>>>>>// development
	// public final boolean DEFAULT_IS_DEBUG = true;;
	// public final int LOG_LEVEL = Log.DEBUG;
	// public final String BUGSENSE_APIKEY = null;
	// public final String ZUBHIUM_SECRET_KEY = null;
	// <<<<<//

	public final int DEVICE_TYPE_ANDROID = 2;
	/** ocr related values */
	public final int MAXIMUM_NUMBER_OF_MICR_RETRIES = 5;
	public final int DEFAULT_ROW_SCOPE_FROM = 10;
	public final int DEFAULT_ROW_SCOPE_TO = 50;

	public final float DEFAULT_VALIDATION_MAX_RATIO_HEIGHT_WIDTH = 0.65f;
	public final float DEFAULT_VALIDATION_MIN_RATIO_HEIGHT_WIDTH = 0.30f;
	public final int ORIENTATION_THRESHOLD_X = 30;
	public final int ORIENTATION_THRESHOLD_X_MIN_1 = 330;
	public final int ORIENTATION_THRESHOLD_X_MAX_1 = 360;
	public final int ORIENTATION_THRESHOLD_Y = 30;
	public final int ORIENTATION_THRESHOLD_Z = 30;
	public final float RATIO_TRESHHOLD = 0.05f;

	/*** Default IQADefaults Image Settings (start) */
	public class IQADefaults {

		public final boolean IS_IQA_MODE = false;

		public final float CORNERDATA_FRONT_TL_W = 1.0f;
		public final float CORNERDATA_FRONT_TL_H = 1.0f;
		public final float CORNERDATA_FRONT_TL_A = 0.4f;

		public final float CORNERDATA_FRONT_TR_W = 1.0f;
		public final float CORNERDATA_FRONT_TR_H = 1.0f;
		public final float CORNERDATA_FRONT_TR_A = 0.4f;

		public final float CORNERDATA_FRONT_BL_W = 1.0f;
		public final float CORNERDATA_FRONT_BL_H = 1.0f;
		public final float CORNERDATA_FRONT_BL_A = 0.4f;

		public final float CORNERDATA_FRONT_BR_W = 0.8f;
		public final float CORNERDATA_FRONT_BR_H = 0.8f;
		public final float CORNERDATA_FRONT_BR_A = 0.3f;
		//
		public final float CORNERDATA_BACK_TL_W = 3.0f;
		public final float CORNERDATA_BACK_TL_H = 3.0f;
		public final float CORNERDATA_BACK_TL_A = 1.0f;

		public final float CORNERDATA_BACK_TR_W = 3.0f;
		public final float CORNERDATA_BACK_TR_H = 3.0f;
		public final float CORNERDATA_BACK_TR_A = 1.0f;

		public final float CORNERDATA_BACK_BL_W = 0.8f;
		public final float CORNERDATA_BACK_BL_H = 0.8f;
		public final float CORNERDATA_BACK_BL_A = 0.3f;

		public final float CORNERDATA_BACK_BR_W = 3.0f;
		public final float CORNERDATA_BACK_BR_H = 3.0f;
		public final float CORNERDATA_BACK_BR_A = 1.0f;
		// ---edge
		public final float EDGE_DATA_T_W = 0.8f;
		public final float EDGE_DATA_T_H = 0.8f;
		public final float EDGE_DATA_T_A = 0.3f;

		public final float EDGE_DATA_B_W = 0.8f;
		public final float EDGE_DATA_B_H = 0.8f;
		public final float EDGE_DATA_B_A = 0.3f;

		public final float EDGE_DATA_L_W = 0.8f;
		public final float EDGE_DATA_L_H = 0.8f;
		public final float EDGE_DATA_L_A = 0.3f;

		public final float EDGE_DATA_R_W = 0.8f;
		public final float EDGE_DATA_R_H = 0.8f;
		public final float EDGE_DATA_R_A = 0.3f;

		public final float MAX_SKEW = 7.5f;

		public final float MAX_DARNESS_FRONT = 0.9f;
		public final float MIN_DARNESS_FRONT = 0.009f;
		public final float MAX_DARNESS_BACK = 0.98f;
		public final float MIN_DARNESS_BACK = 0.0038f;

		public final int MAX_SPOTS_FRONT = 5852;
		public final int MAX_SPOTS_BACK = 5852;

		public final float MIN_IMAGE_SIZE_FRONT = 0.5f;
		public final float MAX_IMAGE_SIZE_FRONT = 200f;
		public final float MIN_IMAGE_SIZE_BACK = 0.5f;
		public final float MAX_IMAGE_SIZE_BACK = 200f;

		public final int HORIZONTAL_STREAK_SUM_BLACK_PIXELS = 25;
		public final int HORIZONTAL_STREAK_LINE_WIDTH = 10;
		public final int HORIZONTAL_STREAK_NUM_LINES = 3;

		public final int CARBON_STRIP_SUM_BLACK_PIXELS = 25;
		public final int CARBON_STRIP_LINE_WIDTH = 10;
		public final int CARBON_STRIP_NUM_LINES = 1;

		public final float PIGGY_BACK_WIDTH_THRESHOLD = 10.0f;
		public final float PIGGY_BACK_LOCATION_THRESHOLD = 20.0f;
	}

	public IQASettingsIntent getIQASettingsDefault() {

		IQASettingsIntent iqaSettings = new IQASettingsIntent();
		IQADefaults defaultParams = new IQADefaults();

		// corner data front TL
		iqaSettings.cornerDataFrontTLH = defaultParams.CORNERDATA_FRONT_TL_H;
		iqaSettings.cornerDataFrontTLW = defaultParams.CORNERDATA_FRONT_TL_W;
		iqaSettings.cornerDataFrontTLA = defaultParams.CORNERDATA_FRONT_TL_A;
		// corner data front TR
		iqaSettings.cornerDataFrontTRH = defaultParams.CORNERDATA_FRONT_TR_H;
		iqaSettings.cornerDataFrontTRW = defaultParams.CORNERDATA_FRONT_TR_W;
		iqaSettings.cornerDataFrontTRA = defaultParams.CORNERDATA_FRONT_TR_A;
		// corner data front BR
		iqaSettings.cornerDataFrontBRH = defaultParams.CORNERDATA_FRONT_BR_H;
		iqaSettings.cornerDataFrontBRW = defaultParams.CORNERDATA_FRONT_BR_W;
		iqaSettings.cornerDataFrontBRA = defaultParams.CORNERDATA_FRONT_BR_A;
		// corner data front BL
		iqaSettings.cornerDataFrontBLH = defaultParams.CORNERDATA_FRONT_BL_H;
		iqaSettings.cornerDataFrontBLW = defaultParams.CORNERDATA_FRONT_BL_W;
		iqaSettings.cornerDataFrontBLA = defaultParams.CORNERDATA_FRONT_BL_A;

		// back corner data
		// corner data front TL
		iqaSettings.cornerDataBackTLH = defaultParams.CORNERDATA_BACK_TL_H;
		iqaSettings.cornerDataBackTLW = defaultParams.CORNERDATA_BACK_TL_W;
		iqaSettings.cornerDataBackTLA = defaultParams.CORNERDATA_BACK_TL_A;
		// corner data front TR
		iqaSettings.cornerDataBackTRH = defaultParams.CORNERDATA_BACK_TR_H;
		iqaSettings.cornerDataBackTRW = defaultParams.CORNERDATA_BACK_TR_W;
		iqaSettings.cornerDataBackTRA = defaultParams.CORNERDATA_BACK_TR_A;
		// corner data front BR
		iqaSettings.cornerDataBackBRH = defaultParams.CORNERDATA_BACK_BR_H;
		iqaSettings.cornerDataBackBRW = defaultParams.CORNERDATA_BACK_BR_W;
		iqaSettings.cornerDataBackBRA = defaultParams.CORNERDATA_BACK_BR_A;
		// corner data front BL
		iqaSettings.cornerDataBackBLH = defaultParams.CORNERDATA_BACK_BL_H;
		iqaSettings.cornerDataBackBLW = defaultParams.CORNERDATA_BACK_BL_W;
		iqaSettings.cornerDataBackBLA = defaultParams.CORNERDATA_BACK_BL_A;

		// edge data
		iqaSettings.edgeDataTH = defaultParams.EDGE_DATA_T_H;
		iqaSettings.edgeDataTW = defaultParams.EDGE_DATA_T_W;
		iqaSettings.edgeDataTA = defaultParams.EDGE_DATA_T_A;

		iqaSettings.edgeDataRH = defaultParams.EDGE_DATA_R_H;
		iqaSettings.edgeDataRW = defaultParams.EDGE_DATA_R_W;
		iqaSettings.edgeDataRA = defaultParams.EDGE_DATA_R_A;

		iqaSettings.edgeDataBH = defaultParams.EDGE_DATA_B_H;
		iqaSettings.edgeDataBW = defaultParams.EDGE_DATA_B_W;
		iqaSettings.edgeDataBA = defaultParams.EDGE_DATA_B_A;

		iqaSettings.edgeDataLH = defaultParams.EDGE_DATA_L_H;
		iqaSettings.edgeDataLW = defaultParams.EDGE_DATA_L_W;
		iqaSettings.edgeDataLA = defaultParams.EDGE_DATA_L_A;

		iqaSettings.maxDarknessFront = defaultParams.MAX_DARNESS_FRONT;
		iqaSettings.minDarknessFront = defaultParams.MIN_DARNESS_FRONT;
		iqaSettings.maxDarknessBack = defaultParams.MAX_DARNESS_BACK;
		iqaSettings.minDarknessBack = defaultParams.MIN_DARNESS_BACK;
		iqaSettings.maxRotationSkew = defaultParams.MAX_SKEW;
		iqaSettings.numberOfSpotsFront = defaultParams.MAX_SPOTS_FRONT;
		iqaSettings.numberOfSpotsBack = defaultParams.MAX_SPOTS_BACK;

		iqaSettings.imagesizeFrontMin = defaultParams.MIN_IMAGE_SIZE_FRONT;
		iqaSettings.imagesizeFrontMax = defaultParams.MAX_IMAGE_SIZE_FRONT;
		iqaSettings.imagesizeBackMin = defaultParams.MIN_IMAGE_SIZE_BACK;
		iqaSettings.imagesizeBackMax = defaultParams.MAX_IMAGE_SIZE_BACK;

		iqaSettings.horizontalStreakSumOfBlackPixels = defaultParams.HORIZONTAL_STREAK_SUM_BLACK_PIXELS;
		iqaSettings.horizontalStreakLineWidth = defaultParams.HORIZONTAL_STREAK_LINE_WIDTH;
		iqaSettings.horizontalStreakNumLines = defaultParams.HORIZONTAL_STREAK_NUM_LINES;

		iqaSettings.carbonStripSumOfBlackPixels = defaultParams.CARBON_STRIP_SUM_BLACK_PIXELS;
		iqaSettings.carbonStripLineWidth = defaultParams.CARBON_STRIP_LINE_WIDTH;
		iqaSettings.carbonStripNumLines = defaultParams.CARBON_STRIP_NUM_LINES;

		iqaSettings.piggyBackMaxWidth = defaultParams.PIGGY_BACK_WIDTH_THRESHOLD;
		iqaSettings.piggyBackMaxAR = defaultParams.PIGGY_BACK_LOCATION_THRESHOLD;

		return iqaSettings;
	}


	/** Default IQADefaults Image Settings (end) */

	public final int HTTP_CONNECTION_TIMEOUT = 40;
	public final int HTTP_SOCKET_TIMEOUT = 70;

}