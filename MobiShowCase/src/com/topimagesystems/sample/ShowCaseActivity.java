package com.topimagesystems.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.topimagesystems.Common.OCRType;
import com.topimagesystems.controllers.NavigationManager;
import com.topimagesystems.controllers.imageanalyze.CameraController;
import com.topimagesystems.controllers.imageanalyze.CameraManagerController;
import com.topimagesystems.controllers.imageanalyze.CameraManagerController.TISMobiFlowMessages;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.TISBarcodeType;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.TISFlowErrorMessage;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.TISFlowGeneralMessages;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.TISFlowInputMessages;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.TISFlowUIMessages;
import com.topimagesystems.data.Preferences;
import com.topimagesystems.data.SessionResultParams;
import com.topimagesystems.data.TISLicenseParameters;
import com.topimagesystems.intent.CaptureIntent;
import com.topimagesystems.intent.CaptureIntent.CardParams;
import com.topimagesystems.intent.CaptureIntent.FullPageCaptureParams;
import com.topimagesystems.intent.CaptureIntent.LevelerType;
import com.topimagesystems.intent.CaptureIntent.SessionType;
import com.topimagesystems.intent.CaptureIntent.TISBinarizationType;
import com.topimagesystems.intent.CaptureIntent.TISDocumentType;
import com.topimagesystems.intent.CaptureIntent.TISFlowUXType;
import com.topimagesystems.intent.CaptureIntent.TISScanBarcodeLocation;
import com.topimagesystems.intent.CaptureIntent.checkCaptureParams;
import com.topimagesystems.intent.CaptureIntent.customCaptureParams;
import com.topimagesystems.intent.CaptureIntent.passportParams;
import com.topimagesystems.intent.CaptureIntent.paymentCaptureParams;
import com.topimagesystems.intent.IQASettingsIntent;
import com.topimagesystems.micr.OCRResult;
import com.topimagesystems.sample.DemoActionBar.ActionBarButtonClickListener;
import com.topimagesystems.sample.DemoActionBar.ActionBarContext;
import com.topimagesystems.sample.controller.BaseController;
import com.topimagesystems.sample.controller.IQASettingsController;
import com.topimagesystems.sample.ui.IqaSettings;
import com.topimagesystems.sample.ui.ShowcasePreferences;
import com.topimagesystems.sample.ui.ShowImagesGallery;
import com.topimagesystems.util.FileUtils;
import com.topimagesystems.util.Logger;
import com.topimagesystems.util.OcrValidationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShowCaseActivity extends BaseController implements OnItemSelectedListener, TISMobiFlowMessages {
	final static String tag = "ShowCaseActivity";
	private BaseController baseController;
	private ScrollView scrollBox;
	private ToggleButton btnDebugMode;
	private ToggleButton btnManualCaptureMode;
	private ToggleButton btnIQA;
	private ToggleButton btnBlurDetection;
	private ToggleButton btnBlurDetectionOnBack;
	private ToggleButton btnSoftCapture;
	private ToggleButton shouldOutputGrayScale;
	private ToggleButton infoScreen;
	private ToggleButton btnShowGuideLinesIndicators;
	private ToggleButton shouldOutputOriginalImage;
	private ToggleButton shouldOutputColoredImage;
	private ToggleButton shouldOutputBWImage;
	private ToggleButton TestMode;
	private ToggleButton btnVideoMode;
	private ToggleButton btnCustomView;
	private ToggleButton enableCountDownSound;
	private ToggleButton btnShowCountDown;
	private ToggleButton btnScanFrontOnlyBtn;
	private ToggleButton btnScanBackOnlyBtn;
	private ToggleButton portraitModeButton;
	private ToggleButton btnMultiCaptureButton;

	private ToggleButton dynamicCaptureBtn;
	private ToggleButton enableBarcodeButton;
	private ToggleButton enableQRCodeButton;
	private ToggleButton enableMaxResolutionStills;
	private ToggleButton enableTapToFocus;
	//	private ToggleButton useCameraAPI2Btn;
	private static int BLINKID_REQUEST_CODE = 222;
	private Spinner cpMICR;
	private Spinner inputType;
	private Spinner levlerType;
	private Spinner binarizationTypeSpinner;
	// private EditText txtMaxNumberOfRetries;
	private EditText txtValidFrom;
	private EditText txtValidTo;

	private EditText maxAspectRatio;
	private EditText minAspectRatio;

	private EditText heightEt;
	private EditText widthEt;


	private RelativeLayout MicrLayout;
	private RelativeLayout apectRatioMinLayout;
	private RelativeLayout apectRatioMaxLayout;

	private RelativeLayout ratioWidth;
	private RelativeLayout ratioHeight;
	private RelativeLayout portraitModeLayout;
	private RelativeLayout txtValidToLayout;
	private RelativeLayout txtValidFromLayout;
	private RelativeLayout seekBarLayout;
	private RelativeLayout seekBarColorImageCompressionLayout;
	private RelativeLayout seekBarGrayScaleImageCompressionLayout;
	private RelativeLayout seekBarSoftCaptureThresholdLayout;
	private float maxAspectRatioVal;
	private float minAspectRatioVal;
	static private OCRType ocrType;
	private Button btnLeft;
	private boolean isDebugMode = false;
	private boolean isBlurEnable = false;
	private boolean isSoftCapture = false;
	private boolean isShowInfoScreen = false;
	private boolean isShowGuideLinesIndicators = true;
	private boolean isShouldOutputOriginalImage = true;
	private boolean isShouldOutputColoredImage = false;
	private boolean isShouldOutputBWImage = true;
	private boolean isEnableCountDownSound = false;
	private boolean isShowCountDown = false;
	private boolean isIQAEnabled = false;
	private boolean isShouldOutputGrayScaleImage = false;
	private boolean isScanFrontOnly = false;
	private boolean isScanBackOnly = false;
	private boolean isCustomView = false;
	private boolean isVideoMode = false;
	private boolean isTestMode = false;
	private static boolean isMultiCapture = false;
	private boolean isDynamicCapture = false;
	private TextView BinarizationSeekBarProgress;
	private float binarizationTrashold = 20;
	private float colorImageCompression = 1.0f;
	private float grayscaleImageCompression = 1.0f;
	private float softCaptureThreshold = 0.0f;

	private SeekBar colorImageCompressSeekbar;
	private TextView colorImageCompressTextView;
	private SeekBar grayScaleImageCompressSeekbar;
	private TextView grayScaleImageCompressTextView;
	private SeekBar binarizationTrasholdSeekbar;

	private SeekBar softCaptureThresholdSeekbar;
	private TextView softCaptureThresholdTextView;


	private boolean isEnableBarcode = false;
	private boolean isEnableQRCode = false;
	private TISBinarizationType binarizationType = TISBinarizationType.TIS_GENERAL_BINARIZATION;

	private TISDocumentType imageType;
	private String baseImagesFolder;
	public static byte[] frontImageTiff;
	public static byte[] frontImageJpeg;
	public static byte[] frontImageGray;
	public static byte[] backImageJpeg;
	public static byte[] backImageTiff;
	public static byte[] backImageGray;
	public static byte[] originalImage;
	private IqaSettings sessionInuptParams;
	private static SessionResultParams currentSessionResult;
	public static int[] inputFrontImageArray;
	public static String MICR_FOUND = "MICR_FOUND";
	public static String BARCODE_FRONT_FOUND = "BARCODE_FRONT_FOUND";
	public static String BARCODE_BACK_FOUND = "BARCODE_BACK_FOUND";
	public static String MULTI_PAGE_TIMESTAMP_ARRAY = "com.topimagesystems.sample.MULTI_PAGE_TIMESTAMP_ARRAY";
	public static String MULTI_PAGE_MICR_ARRAY = "com.topimagesystems.sample.MULTI_PAGE_MICR_ARRAY";
	public static String MULTI_PAGE_SESSION_ARRAY = "com.topimagesystems.sample.MULTI_PAGE_SESSION_ARRAY";
	public static String LAST_SAVED_IMAGES_ARRAY = "com.topimagesystems.sample.LAST_SAVED_IMAGES_ARRAY";
	public static final int MY_PERMISSIONS_REQUEST_EXTERNAL_PERMISSION = 100;

	private Button btnConfirm;
	private MobiSampleApplication app;

	// private int maxNumberOfRetriesVal;
	private boolean errorMessageReceived = false;
	private static ArrayList<String> imagesTimeStampArray;
	private static ArrayList<String> multiCaptureExtraDataArray;
	private static ArrayList<String> multiCaptureSessionArray;
	private static ArrayList<String> latestCapturedImages;
	private static int sessionCounter;
	boolean isExternalStoragePermissionApproved = false;
	private static int ocrSpinenrPosition = -1;

	Resources mResources;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.show_case_layout);

		app = ((MobiSampleApplication) getApplication());

		mResources = getResources();

		scrollBox = (ScrollView) findViewById(R.id.scrollBox);
		btnDebugMode = (ToggleButton) findViewById(R.id.btnDebugMode);
		btnIQA = (ToggleButton) findViewById(R.id.btnEnableIQA);
		cpMICR = (Spinner) findViewById(R.id.cpMICR);
		inputType = (Spinner) findViewById(R.id.inputType);
		levlerType = (Spinner) findViewById(R.id.levlerType);
		binarizationTypeSpinner = (Spinner) findViewById(R.id.binarizationTypeSpinner);
		btnBlurDetection = (ToggleButton) findViewById(R.id.btnBlurDetection);
		btnBlurDetectionOnBack = (ToggleButton) findViewById(R.id.blurOnBackToggle);
		btnSoftCapture = (ToggleButton) findViewById(R.id.btnSoftCapture);
		shouldOutputGrayScale = (ToggleButton) findViewById(R.id.createGrayScale);
		infoScreen = (ToggleButton) findViewById(R.id.infoScreen);
		btnShowGuideLinesIndicators = (ToggleButton) findViewById(R.id.showGuideLinesIndicators);
		shouldOutputOriginalImage = (ToggleButton) findViewById(R.id.shouldOutputOriginalImage);
		shouldOutputColoredImage = (ToggleButton) findViewById(R.id.shouldOutputColoredImage);
		shouldOutputBWImage = (ToggleButton) findViewById(R.id.shouldOutputBWImage);
		TestMode = (ToggleButton) findViewById(R.id.isTestMode);
		enableCountDownSound = (ToggleButton) findViewById(R.id.enableCountDownSound);
		btnShowCountDown = (ToggleButton) findViewById(R.id.showCountDown);
		btnVideoMode = (ToggleButton) findViewById(R.id.videoMode);
		btnCustomView = (ToggleButton) findViewById(R.id.customView);
		btnScanFrontOnlyBtn = (ToggleButton) findViewById(R.id.scanFrontOnlyBtn);
		btnScanBackOnlyBtn = (ToggleButton) findViewById(R.id.scanBackOnlyBtn);
		portraitModeButton = (ToggleButton) findViewById(R.id.btnPortraitMode);
		btnMultiCaptureButton = (ToggleButton) findViewById(R.id.multiCaptureButton);
		dynamicCaptureBtn = (ToggleButton) findViewById(R.id.dynamicCaptureBtn);
		enableMaxResolutionStills = (ToggleButton) findViewById(R.id.enableMaxResolutionBtn);
		enableTapToFocus = (ToggleButton) findViewById(R.id.enableTapToFocusBtn);
		//	useCameraAPI2Btn = (ToggleButton) findViewById(R.id.useCameraAPI2Btn);
		String s = String.valueOf(getString(R.string.TISVersion).charAt(0));

		View dynamicCapture = (View) findViewById(R.id.dynamiCaptureLayout);
		if (Float.valueOf(getString(R.string.TISVersion).charAt(0)) < 4) {
			dynamicCapture.setVisibility(View.GONE);
			isDynamicCapture = false;
		}

		btnMultiCaptureButton = (ToggleButton) findViewById(R.id.multiCaptureButton);
		enableBarcodeButton = (ToggleButton) findViewById(R.id.enableBarcode);

		BinarizationSeekBarProgress = (TextView) findViewById(R.id.binarizationTextViewSeekBar);
		colorImageCompressSeekbar = (SeekBar) findViewById(R.id.seekBarColorImageCompression);

		colorImageCompressTextView = (TextView) findViewById(R.id.textViewColorImageCompression);
		grayScaleImageCompressSeekbar = (SeekBar) findViewById(R.id.seekBarGrayScaleImageCompression);
		softCaptureThresholdSeekbar = (SeekBar) findViewById(R.id.seekBarSoftCaptureThreshold);
		softCaptureThresholdTextView = (TextView) findViewById(R.id.textViewSoftCaptureThreshold);
		grayScaleImageCompressTextView = (TextView) findViewById(R.id.textViewGrayScaleImageCompression);
		binarizationTrasholdSeekbar = (SeekBar) findViewById(R.id.binarizationSeekBar);
		enableQRCodeButton = (ToggleButton) findViewById(R.id.enableQRCode);
		// txtMaxNumberOfRetries = (EditText)
		// findViewById(R.id.txtMaxNumberOfRetries);
		txtValidFrom = (EditText) findViewById(R.id.txtValidFrom);
		txtValidTo = (EditText) findViewById(R.id.txtValidTo);
		MicrLayout = (RelativeLayout) findViewById(R.id.MicrLayout);
		txtValidFromLayout = (RelativeLayout) findViewById(R.id.txtValidFromLayout);
		txtValidToLayout = (RelativeLayout) findViewById(R.id.txtValidToLayout);
		apectRatioMinLayout = (RelativeLayout) findViewById(R.id.apectRatioMinLayout);
		apectRatioMaxLayout = (RelativeLayout) findViewById(R.id.apectRatioMaxLayout);

		ratioHeight = (RelativeLayout) findViewById(R.id.heigthLayout);
		ratioWidth = (RelativeLayout) findViewById(R.id.widthLayout);
		seekBarLayout = (RelativeLayout) findViewById(R.id.seekBarLayout);
		seekBarColorImageCompressionLayout = (RelativeLayout) findViewById(R.id.layoutColorImageCompression);
		seekBarGrayScaleImageCompressionLayout = (RelativeLayout) findViewById(R.id.layoutGrayScaleImageCompression);
		seekBarSoftCaptureThresholdLayout = (RelativeLayout) findViewById(R.id.layoutSoftCaptureThreshold);
		portraitModeLayout = (RelativeLayout) findViewById(R.id.portraitModeLayout);
		heightEt = (EditText) findViewById(R.id.heigthEt);
		widthEt = (EditText) findViewById(R.id.widthEt);
		maxAspectRatio = (EditText) findViewById(R.id.apectRatioMaxEt);
		minAspectRatio = (EditText) findViewById(R.id.apectRatioMinEt);

		btnLeft = (Button) findViewById(R.id.btnBack);
		btnLeft.setVisibility(View.VISIBLE);
		btnLeft.setText(getResources().getString(R.string.showImages));
		Context appContext = getApplicationContext();
		boolean isIQAMode = btnIQA.isChecked();
		btnIQA.setChecked(isIQAMode);
		if (btnIQA.isChecked()){
			demoActionBar.setRightBtnVisiblity(true);
		}
		ensureImageTypeSpinner();
		ensureMICRTypeSpinner();
		ensureLevelerTypeSpinner();
		ensureBinarizationTypeSpinner();
		setButtonsState();

		baseController = this;
		binarizationTrasholdSeekbar.setProgress(30);
		BinarizationSeekBarProgress.setText("Trashold: " + binarizationTrasholdSeekbar.getProgress() / 100 + "/" + 1);
		colorImageCompressTextView.setText(String.format(mResources.getString(R.string.ColoredImageCompressionValue), colorImageCompression));
		grayScaleImageCompressTextView.setText(String.format(mResources.getString(R.string.GrayScaleImageCompressionValue), grayscaleImageCompression));
		btnIQA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				demoActionBar.setRightBtnVisiblity(isChecked);
			}
		});

		binarizationTrasholdSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			//	 int progress = 0;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				binarizationTrashold = (float) seekBar.getProgress() / 100;
				BinarizationSeekBarProgress.setText("Trashold: " + binarizationTrashold + "/" + 1);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				//progress = progresValue;
				binarizationTrashold = (float) progress / 100;
				BinarizationSeekBarProgress.setText("Trashold: " + binarizationTrashold + "/" + 1);

			}
		});

		colorImageCompressSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				colorImageCompression = (float) colorImageCompressSeekbar.getProgress() / 100;
				colorImageCompressTextView.setText(String.format(mResources.getString(R.string.ColoredImageCompressionValue), colorImageCompression));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				colorImageCompression = (float) progress / 100;
				colorImageCompressTextView.setText(String.format(mResources.getString(R.string.ColoredImageCompressionValue), colorImageCompression));
			}
		});

		softCaptureThresholdTextView.setText(String.format(mResources.getString(R.string.SoftCaptureThresholdValue), softCaptureThreshold));
		softCaptureThresholdSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {


			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				softCaptureThreshold = (float) softCaptureThresholdSeekbar.getProgress() / 100;
				softCaptureThresholdTextView.setText(String.format(mResources.getString(R.string.SoftCaptureThresholdValue), softCaptureThreshold));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				softCaptureThreshold = (float) progress / 100;
				softCaptureThresholdTextView.setText(String.format(mResources.getString(R.string.SoftCaptureThresholdValue), softCaptureThreshold));

			}
		});

		shouldOutputColoredImage.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				seekBarColorImageCompressionLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			}
		});

		grayScaleImageCompressSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				grayscaleImageCompression = (float) grayScaleImageCompressSeekbar.getProgress() / 100;
				grayScaleImageCompressTextView.setText(String.format(mResources.getString(R.string.GrayScaleImageCompressionValue), grayscaleImageCompression));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				grayscaleImageCompression = (float) progress / 100;
				grayScaleImageCompressTextView.setText(String.format(mResources.getString(R.string.GrayScaleImageCompressionValue), grayscaleImageCompression));
			}
		});


		shouldOutputGrayScale.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				seekBarGrayScaleImageCompressionLayout.setVisibility(isChecked? View.VISIBLE: View.GONE);
			}
		});

		btnSoftCapture.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				seekBarSoftCaptureThresholdLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			}
		});

		btnScanFrontOnlyBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					btnScanBackOnlyBtn.setChecked(false);

			}
		});


		btnScanBackOnlyBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					btnScanFrontOnlyBtn.setChecked(false);

			}
		});

		enableBarcodeButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked)
					enableQRCodeButton.setChecked(false);

			}
		});

		enableQRCodeButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					enableBarcodeButton.setChecked(true);

			}
		});
		btnLeft.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				PopupMenu popup = null;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
					popup = new PopupMenu(getBaseContext(), btnLeft);
					popup.getMenuInflater()
							.inflate(R.menu.option_button_menu, popup.getMenu());
				}
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						public boolean onMenuItemClick(MenuItem item) {
							if (item.getItemId() == R.id.resetButtons){
								resetToggles();
							}
							if (item.getItemId() == R.id.resetToDefault){
								setDefaultToggleState(imageType);
							}
							return true;
						}
					});
				}
				popup.show();

				//Creating the instance of PopupMenu

				//Inflating the Popup using xml file


				//registering popup with OnMenuItemClickListener


				; //showing popup menu

				//closing the setOnClickListener method
				return true;
			}
		});

		btnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent showImages = new Intent(ShowCaseActivity.this, ShowImagesGallery.class);
				showImages.putStringArrayListExtra(MULTI_PAGE_TIMESTAMP_ARRAY, imagesTimeStampArray);
				showImages.putStringArrayListExtra(MULTI_PAGE_MICR_ARRAY, multiCaptureExtraDataArray);
				showImages.putStringArrayListExtra(MULTI_PAGE_SESSION_ARRAY, multiCaptureSessionArray);
				showImages.putStringArrayListExtra(LAST_SAVED_IMAGES_ARRAY, latestCapturedImages);

				if (currentSessionResult != null) {
					if (currentSessionResult.getOcrParams() != null && currentSessionResult.getOcrParams()[1] != null) {
						Log.i("start show images with ocr params", currentSessionResult.getOcrParams()[1]);

						String micrValue = currentSessionResult.getOcrParams()[1];

						if (imageType == TISDocumentType.CARD &&  ocrType == OCRType.MRZ) {
							String[] ocrData = currentSessionResult.getOcrParams();
							OCRResult ocrResult = new OCRResult(ocrData);
							HashMap<String,String> parsedData = OcrValidationUtils.parseIDCardResult(ocrResult.ocrResultWithDelimiter);
							micrValue = parsedData.toString();
						}

						showImages.putExtra(MICR_FOUND, micrValue);
					}

					if (currentSessionResult.getBarcodeResult() != null) {
						//if (barcodeResult.getBarcodeDataFront() != null)
						if (currentSessionResult.getBarcodeResult().getBarcodeDataFront()!= null){
							String data = currentSessionResult.getBarcodeResult().getBarcodeDataFront();
							HashMap<String,String> parsedData =  OcrValidationUtils.DLBarcodeParser.parseDLBarcode(data);
							if (parsedData == null || parsedData.size() < 2)
								showImages.putExtra(BARCODE_FRONT_FOUND, data);
							else
								showImages.putExtra(BARCODE_FRONT_FOUND, parsedData.toString());
						}
						//if (barcodeResult.getBarcodeDataBack() != null)
						if (currentSessionResult.getBarcodeResult().getBarcodeDataBack()!= null) {
							String data = currentSessionResult.getBarcodeResult().getBarcodeDataBack();
							HashMap<String, String> parsedData = OcrValidationUtils.DLBarcodeParser.parseDLBarcode(data);
							if (parsedData == null || parsedData.size() < 2)
								showImages.putExtra(BARCODE_BACK_FOUND, data);
							else
								showImages.putExtra(BARCODE_BACK_FOUND, parsedData.toString());
						}
					}

				} else {

					Log.i("showCase", "currentSessionResult is null");
				}
				//File tiffFile = new File(FileUtils.getTempFilePath(getBaseContext()) + FileUtils.captureImageFront);
				//File originalFile = new File(FileUtils.getTempFilePath(getBaseContext()) + FileUtils.captureImageFrontOriginal);
				//if (!tiffFile.exists() && !originalFile.exists()) {
				//File imagesFolder = new File(baseImagesFolder);
				//	File[] contents = imagesFolder.listFiles();
				if (latestCapturedImages == null || latestCapturedImages.size() == 0) {
					Toast.makeText(getBaseContext(), "No Images To Show", Toast.LENGTH_SHORT).show();
					return;
				}
				//	}
				startActivity(showImages);
			}

		});

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				latestCapturedImages = new ArrayList<String>();
				CameraManagerController.sessionType = SessionType.NORMAL;
				//				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
				//				     // only for gingerbread and newer versions
				//					askPermission();
				//				}
				//				else{
				if (!btnDebugMode.isChecked()){
					int res = getBaseContext().checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

					if (res == PackageManager.PERMISSION_GRANTED) {
						File sdCard = Environment.getExternalStorageDirectory();
						sdCard = Environment.getExternalStorageDirectory();
						baseImagesFolder = sdCard.getAbsolutePath() + "/" + ".mobiflow/";
						String logFile =  baseImagesFolder.replace(".mobiflow/", ".debugmobiflow/");
						// clear saved images. 
						FileUtils.clearFiles(getBaseContext(),baseImagesFolder);
						// clear log file.
						FileUtils.clearFiles(getBaseContext(),logFile);
					}
					String internalStorageLocation = getBaseContext().getFilesDir().getAbsolutePath() + "/" + FileUtils.tempPath + "/";
					FileUtils.clearFiles(getBaseContext(), internalStorageLocation);
					String debugInternalStorageLocation = getBaseContext().getFilesDir().getAbsolutePath() + "/" + FileUtils.tempDebugPath + "/";
					FileUtils.clearFiles(getBaseContext(), debugInternalStorageLocation);

				}
				startCapture();
				//}

			}

		});
	}
	private void setDefaultToggleState(TISDocumentType docType) {
		btnIQA.setChecked(false);
		btnBlurDetection.setChecked(false);
		btnBlurDetectionOnBack.setChecked(false);
		infoScreen.setChecked(false);
		btnShowGuideLinesIndicators.setChecked(true);
		shouldOutputOriginalImage.setChecked(true);
		shouldOutputColoredImage.setChecked(true);
		shouldOutputBWImage.setChecked(true);
		btnScanFrontOnlyBtn.setChecked(true);
		TestMode.setChecked(false);
		btnDebugMode.setChecked(false);
		btnSoftCapture.setChecked(false);
		btnShowGuideLinesIndicators.setChecked(true);
		btnVideoMode.setChecked(false);
		btnCustomView.setChecked(false);
		enableCountDownSound.setChecked(false);
		btnScanFrontOnlyBtn.setChecked(true);
		btnScanBackOnlyBtn.setChecked(false);
		portraitModeButton.setChecked(false);
		btnMultiCaptureButton.setChecked(false);
		dynamicCaptureBtn.setChecked(false);
		enableBarcodeButton.setChecked(false);
		enableQRCodeButton.setChecked(false);
		enableMaxResolutionStills.setChecked(false);
		enableTapToFocus.setChecked(true);
		//useCameraAPI2Btn.setChecked(false);
		switch (docType){
			case CHECK:
				setCheckDefaultToggleState();
				break;
			case PAYMENT:
				btnBlurDetection.setChecked(true);
				break;
			case CARD:

				break;
			case PASSPORT:
				btnVideoMode.setChecked(true);
				break;
			case FULL_PAGE:
				btnBlurDetection.setChecked(true);
		}
	}
	private void resetToggles() {
		btnDebugMode.setChecked(false);
		btnIQA.setChecked(false);;
		btnBlurDetection.setChecked(false);
		btnBlurDetectionOnBack.setChecked(false);
		btnSoftCapture.setChecked(false);;
		shouldOutputGrayScale.setChecked(false);;
		infoScreen.setChecked(false);;
		btnShowGuideLinesIndicators.setChecked(false);
		shouldOutputOriginalImage.setChecked(false);
		shouldOutputColoredImage.setChecked(false);
		shouldOutputBWImage.setChecked(false);;;
		TestMode.setChecked(false);
		btnVideoMode.setChecked(false);
		btnCustomView.setChecked(false);
		enableCountDownSound.setChecked(false);
		btnShowCountDown.setChecked(false);
		btnScanFrontOnlyBtn.setChecked(false);
		btnScanBackOnlyBtn.setChecked(false);
		portraitModeButton.setChecked(false);
		btnMultiCaptureButton.setChecked(false);
		dynamicCaptureBtn.setChecked(false);
		enableBarcodeButton.setChecked(false);
		enableQRCodeButton.setChecked(false);
		enableMaxResolutionStills.setChecked(false);
		enableTapToFocus.setChecked(false);
	}


	/**
	 *
	 */
	private void setButtonsState() {
		boolean blurDefaultVal = false;
		if (cpMICR.getLastVisiblePosition() == 1 || cpMICR.getLastVisiblePosition() == 2 ){
			blurDefaultVal = false;
		}
		btnBlurDetection.setChecked(ShowcasePreferences.getIsBlurDetection(this, blurDefaultVal));
		btnBlurDetectionOnBack.setChecked(ShowcasePreferences.getIsBlurDetectionOnBack(this, blurDefaultVal));
		infoScreen.setChecked(ShowcasePreferences.getInfoScreen(this));
		btnShowGuideLinesIndicators.setChecked(ShowcasePreferences.getGuideLinesIndicators(this));
		shouldOutputOriginalImage.setChecked(ShowcasePreferences.getOutputOriginalImage(this));

		shouldOutputColoredImage.setChecked(ShowcasePreferences.getShouldOutputColoredImage(this));
		shouldOutputBWImage.setChecked(ShowcasePreferences.getShouldOutputBWImage(this));
		enableCountDownSound.setChecked(ShowcasePreferences.getCountDownSound(this));
		btnShowCountDown.setChecked(ShowcasePreferences.getShowCountDown(this));

		btnIQA.setChecked(ShowcasePreferences.getIQAEnabled(this));
		btnDebugMode.setChecked(ShowcasePreferences.isDebugMode(this));
		shouldOutputGrayScale.setChecked(ShowcasePreferences.getCreateGrayscale(this));
		btnScanFrontOnlyBtn.setChecked(ShowcasePreferences.getScanFrontOnly(this));
		btnScanBackOnlyBtn.setChecked(ShowcasePreferences.getScanBackOnly(this));
		btnCustomView.setChecked(ShowcasePreferences.getIsCustomView(this));
		TestMode.setChecked(ShowcasePreferences.getIsTestMode(this));
		btnVideoMode.setChecked(ShowcasePreferences.getIsVideoMode(this));
		btnMultiCaptureButton.setChecked(ShowcasePreferences.getIsMultiCapture(this));
		dynamicCaptureBtn.setChecked(ShowcasePreferences.getIsDynamicCapture(this));
		enableBarcodeButton.setChecked(ShowcasePreferences.getIsEnableBarcode(this));
		enableQRCodeButton.setChecked(ShowcasePreferences.getIsEnableQRCode(this));
		btnSoftCapture.setChecked(ShowcasePreferences.getIsSoftCapture(this));
		colorImageCompression = ShowcasePreferences.getColorImageCompression(this);

		colorImageCompressTextView.setText(String.format(mResources.getString(R.string.ColoredImageCompressionValue), colorImageCompression));
		colorImageCompressSeekbar.setProgress((int) (grayscaleImageCompression * 100));
		grayscaleImageCompression = ShowcasePreferences.getGrayScaleImageCompression(this);
		enableMaxResolutionStills.setChecked(ShowcasePreferences.getUsemaxResolutionStills(this));
		//useCameraAPI2Btn.setChecked(ShowcasePreferences.getUseCameraAPI2(this));
		grayScaleImageCompressTextView.setText(String.format(mResources.getString(R.string.GrayScaleImageCompressionValue), grayscaleImageCompression));
		grayScaleImageCompressSeekbar.setProgress((int) (grayscaleImageCompression * 100));

		softCaptureThreshold = ShowcasePreferences.getSoftCaptureThreshold(this);
		softCaptureThresholdTextView.setText(String.format(mResources.getString(R.string.SoftCaptureThresholdValue), softCaptureThreshold));
		softCaptureThresholdSeekbar.setProgress((int) (softCaptureThreshold * 100));

		enableTapToFocus.setChecked(ShowcasePreferences.getEnableTapToFocus(this));


	}

	private void initParams() {
		isBlurEnable = btnBlurDetection.isChecked();
		isShowInfoScreen = infoScreen.isChecked();
		isShowGuideLinesIndicators = btnShowGuideLinesIndicators.isChecked();
		isShouldOutputOriginalImage = shouldOutputOriginalImage.isChecked();
		isSoftCapture = btnSoftCapture.isChecked();
		isShouldOutputColoredImage = shouldOutputColoredImage.isChecked();
		isShouldOutputBWImage = shouldOutputBWImage.isChecked();
		isEnableCountDownSound = enableCountDownSound.isChecked();
		isShowCountDown = btnShowCountDown.isChecked();
		isIQAEnabled = btnIQA.isChecked();
		isDebugMode = btnDebugMode.isChecked();
		isShouldOutputGrayScaleImage = shouldOutputGrayScale.isChecked();
		isScanFrontOnly = btnScanFrontOnlyBtn.isChecked();
		isCustomView = btnCustomView.isChecked();
		isScanFrontOnly = btnScanFrontOnlyBtn.isChecked();
		isScanBackOnly = btnScanBackOnlyBtn.isChecked();
		isVideoMode = btnVideoMode.isChecked();
		isTestMode = TestMode.isChecked();
		isMultiCapture = btnMultiCaptureButton.isChecked();
		isDynamicCapture = dynamicCaptureBtn.isChecked();
		isEnableBarcode = enableBarcodeButton.isChecked();
		isEnableQRCode = enableQRCodeButton.isChecked();
		imagesTimeStampArray = new ArrayList<String>();
		multiCaptureExtraDataArray = new ArrayList<String>();
		multiCaptureSessionArray = new ArrayList<String>();
		sessionCounter = 0;

		CameraController.registerListener(this);
		int val = 3;
		// if (txtMaxNumberOfRetries.getText().toString().length() > 0) {
		// val = Integer.valueOf(txtMaxNumberOfRetries.getText().toString());
		// }
		// maxNumberOfRetriesVal = val;
		if (TestMode.isChecked()) {
			CameraManagerController.sessionType = SessionType.TEST;
		}
		CameraController.registerListener(this);
		saveSettings();
	}

	private void ensureMICRTypeSpinner() {

		cpMICR.setOnItemSelectedListener(this);
		String imageType = inputType.getSelectedItem().toString();
		List<CharSequence> micrTypes = new ArrayList<CharSequence>();
		if (imageType.equalsIgnoreCase("CARD")) {
			micrTypes.add(getResources().getString(R.string.off));
			micrTypes.add(getResources().getString(R.string.PAN));
			micrTypes.add(getResources().getString(R.string.MRZ));
		} else if (imageType.equalsIgnoreCase("CHECK")) {
			micrTypes.add(getResources().getString(R.string.E138B));
			micrTypes.add(getResources().getString(R.string.CMC7));
			micrTypes.add(getResources().getString(R.string.off));
			maxAspectRatio.setText(String.valueOf(ShowcasePreferences.getMaxAspectRatio(this)));
			minAspectRatio.setText(String.valueOf(ShowcasePreferences.getMinAspectRatio(this)));
		} else if (imageType.equalsIgnoreCase("Bill Payment")) {
			micrTypes.add(getResources().getString(R.string.off));
			micrTypes.add(getResources().getString(R.string.OCRA));
		}

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, micrTypes);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cpMICR.setAdapter(adapter);
		cpMICR.setSelection(ocrSpinenrPosition);
	}

	private void ensureBinarizationTypeSpinner() {
		binarizationTypeSpinner.setOnItemSelectedListener(this);
		List<CharSequence> binarizationTypeSpinnerTypes = new ArrayList<CharSequence>();
		binarizationTypeSpinnerTypes.add(getResources().getString(R.string.TISGeneralBinarization));
		binarizationTypeSpinnerTypes.add(getResources().getString(R.string.TISCheckBinarization));

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, binarizationTypeSpinnerTypes);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		binarizationTypeSpinner.setAdapter(adapter);

		int position = binarizationTypeSpinner.getSelectedItemPosition();

		switch (position) {
			case 0:
				binarizationType = TISBinarizationType.TIS_GENERAL_BINARIZATION;
				binarizationTrasholdSeekbar.setVisibility(View.VISIBLE);
				BinarizationSeekBarProgress.setVisibility(View.VISIBLE);
				break;
			case 1:
				binarizationType = TISBinarizationType.TIS_CHECK_BINARIZATION;
				binarizationTrasholdSeekbar.setVisibility(View.GONE);
				BinarizationSeekBarProgress.setVisibility(View.GONE);
				break;
		}

		// MICRType inputType =
		// ShowcasePreferences.getCurrentMICRType(getApplicationContext());

	}

	private void ensureLevelerTypeSpinner() {
		levlerType.setOnItemSelectedListener(this);
		List<CharSequence> levlerTypes = new ArrayList<CharSequence>();
		levlerTypes.add(getResources().getString(R.string.one_unit_levler));
		levlerTypes.add(getResources().getString(R.string.two_unit_levler));
		levlerTypes.add(getResources().getString(R.string.scale_levler));
		levlerTypes.add(getResources().getString(R.string.none_levler));

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, levlerTypes);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		levlerType.setAdapter(adapter);

		int position = levlerType.getSelectedItemPosition();

		switch (position) {

			case 0:
				CameraManagerController.levlerType = LevelerType.ONE_UNIT;
				break;
			case 1:
				CameraManagerController.levlerType = LevelerType.TWO_UNITS;
				break;
			case 2:
				CameraManagerController.levlerType = LevelerType.SCALE;
				break;
			case 3:
				CameraManagerController.levlerType = LevelerType.NONE;
				break;
		}

		// MICRType inputType =
		// ShowcasePreferences.getCurrentMICRType(getApplicationContext());

	}

	private void ensureImageTypeSpinner() {
		List<CharSequence> imageTypes = new ArrayList<CharSequence>();

		imageTypes.add(getResources().getString(R.string.Check));
		imageTypes.add(getResources().getString(R.string.Payment_text));
		imageTypes.add(getResources().getString(R.string.Document_text));
		imageTypes.add(getResources().getString(R.string.Custom));
		imageTypes.add(getResources().getString(R.string.Passport));
		imageTypes.add(getResources().getString(R.string.Card));

		ArrayAdapter<CharSequence> itemsAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, imageTypes);
		itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inputType.setAdapter(itemsAdapter);
		inputType.setOnItemSelectedListener(this);

	}

	private void startCheckFlow() {
		int fromVal;
		int toVal;
		initParams();
	//
			IQASettingsIntent iqaSettings = ShowcasePreferences.getIQASettings(getApplicationContext());


/*
		IQASettingsIntent iqaSettings = new IQASettingsIntent();
		// init IQA defaults
		iqaSettings = iqaSettings.getIQASettingsDefault();
		init IQA UK defaults
		iqaSettings = iqaSettings.getIQAStandart57Defaults();
*/

		CaptureIntent captureIntent = new CaptureIntent(baseController);
		checkCaptureParams input = (checkCaptureParams) captureIntent.getCaptureParams(TISDocumentType.CHECK);
		input.IQASettings = iqaSettings;
		// inputImageSize = new int[];

		input.ocrType = ocrType;
		input.enableIQA = isIQAEnabled;
		input.IQASettings = iqaSettings;
		if (txtValidFrom.getText().toString().length() < 1) {
			fromVal = 20;
		} else {
			fromVal = Integer.valueOf(txtValidFrom.getText().toString());
		}
		if (txtValidTo.getText().toString().length() < 1) {
			toVal = 50;
		} else {
			toVal = Integer.valueOf(txtValidTo.getText().toString());
		}
		input.minMICRLength = fromVal;
		input.maxMICRLength = toVal;		
		HashMap<String, String> stringVal = new HashMap<String, String>();
		stringVal.put("TISAspectRatioError", "Wrong Apsect Ratio, Please capture Check");
		input.dynamicStrings = stringVal;
		input.enableSoftCapture = true;
//		input.frontImageSize = new int[2];
//		input.frontImageSize[0] = 1200;
//		input.frontImageSize[1] = 600;
		checkEditTextConditionsForChecks();
		if (minAspectRatioVal != -1 && maxAspectRatioVal!=-1) {
			input.minHeightWidthAspectRatio = minAspectRatioVal;
			input.maxHeightWidthAspectRatio = maxAspectRatioVal;
		}
		setSessionParams(input);

		// input.frontImageSize = new int[]{1200,600};
		captureIntent.captureDocument(input);



	/*	CaptureIntent captureIntent = new CaptureIntent(this); // this is your Activity context
		IQASettingsIntent iqaSettings = new IQASettingsIntent().getIQASettingsDefault(); // init default IQA settings class
		checkCaptureParams input = (checkCaptureParams) captureIntent.getCaptureParams(TISDocumentType.CHECK); // init default check configuration.
		input.outputColorImage = false; // cropped color image, seems you don’t need this from the settings.
		input.IQASettings = iqaSettings;
		input.uxType = TISFlowUXType.STATIC;
		input.license = new TISLicenseParameters("","" , ""); // input the license here
		CameraController.registerListener(this); // this == class context, register listener to get messages from the SDK, don’t have to be here,can be anywhere before .
		captureIntent.captureDocument(input); // open camera screen.*/

	}


	private void startPaymentFlow() {
		initParams();
		CaptureIntent captureIntent = new CaptureIntent(baseController);
		paymentCaptureParams input = (paymentCaptureParams) captureIntent.getCaptureParams(TISDocumentType.PAYMENT);
		// captureParams input = new
		// CaptureIntent.captureParams(TISDocumentType.PAYMENT); //new
		// CaptureIntent.paymentCaptureParams();

		//IQASettingsIntent iqaSettings = ShowcasePreferences.getIQASettings(getApplicationContext()).getIQASettingsDefault();
		//input.IQASettings = iqaSettings;
		input.showDefaultProcessingView = true;
		HashMap<String, String> paymetStrings = new HashMap<String, String>();
		paymetStrings.put("TISFlowPleaseCaptureCheckFront", "Please capture the front face of the bill");
		paymetStrings.put("TISFlowPleaseCaptureCheckBack", "Please capture the back of the bill");
		input.dynamicStrings = paymetStrings;
		//input.infoScreenInterval = 4000;
		input.binarizationType = TISBinarizationType.TIS_GENERAL_BINARIZATION;
		setSessionParams(input);
		captureIntent.captureDocument(input);

	}

	private void startDocumentFlow() {
		initParams();
		CaptureIntent CaptureIntent = new CaptureIntent(baseController);
		FullPageCaptureParams input = (FullPageCaptureParams) CaptureIntent.getCaptureParams(TISDocumentType.FULL_PAGE);
		HashMap<String, String> fullPageStrings = new HashMap<String, String>();

		setSessionParams(input);
		//input.frontImageSize = new int[]{1200,600};
		fullPageStrings.put("TISFlowPleaseCaptureCheckBack", "Please capture the back of the documnet");
		input.dynamicStrings = fullPageStrings;

		CaptureIntent.captureDocument(input);
	}

	private void startCustomFlow() {
		initParams();
		CaptureIntent CaptureIntent = new CaptureIntent(baseController);

		customCaptureParams input = (customCaptureParams) CaptureIntent.getCaptureParams(TISDocumentType.CUSTOM);
		input.portraitCapture = portraitModeButton.isChecked();
		setSessionParams(input);
		if (isDynamicCapture && maxAspectRatioVal == 0 && minAspectRatioVal == 0) {
			input.maxHeightWidthAspectRatio = maxAspectRatioVal;
			input.minHeightWidthAspectRatio = minAspectRatioVal;
		} else {
			if (checkEditTextConditions(input.portraitCapture)) {
				input.maxHeightWidthAspectRatio = maxAspectRatioVal;
				input.minHeightWidthAspectRatio = minAspectRatioVal;
			} else {
				return;
			}
		}

		if (input.portraitCapture)
			CameraManagerController.sessionType = SessionType.PORTRAIT;
		CaptureIntent.captureDocument(input);
	}

	private void startPassportFlow() {
		initParams();
		CaptureIntent CaptureIntent = new CaptureIntent(baseController);

		passportParams input = (passportParams) CaptureIntent.getCaptureParams(TISDocumentType.PASSPORT);
		// input.portraitCapture = portraitModeButton.isChecked();

		HashMap<String, String> strings = new HashMap<String, String>();

		strings.put("TISFlowPleaseCaptureCheckFront", "Please capture the front face of the passport");
		strings.put("TISFlowPleaseCaptureCheckBack", "Please capture the back of the passport");

		// if (checkEditTextConditions(input.portraitCapture)) {
		input.maxHeightWidthAspectRatio = 0.8f;
		input.minHeightWidthAspectRatio = 0.6f;
		//			if (input.portraitCapture)
		input.dynamicStrings = strings;
		//				CameraManagerController.sessionType = SessionType.PORTRAIT;
		setSessionParams(input);
		CaptureIntent.captureDocument(input);
		// }

	}

	private void startCardFlow() {
		initParams();
		CaptureIntent CaptureIntent = new CaptureIntent(baseController);
		CardParams input = (CardParams) CaptureIntent.getCaptureParams(TISDocumentType.CARD);
		HashMap<String, String> strings = new HashMap<String, String>();
		strings.put("TISFlowPleaseCaptureCheckFront", "Please capture the front face of the Card");
		strings.put("TISFlowPleaseCaptureCheckBack", "Please capture the back of the Card");
		//strings.put("TISFlowErrorReadingOCRMessage", "error reading Pan Card data");

		input.dynamicStrings = strings;
		setSessionParams(input);
		CaptureIntent.captureDocument(input);

	}


	private void checkEditTextConditionsForChecks() {
		float maxRatioHW = -1;
		float minRatioHW = -1;

		if (maxAspectRatio.getText().toString() != null && maxAspectRatio.getText().toString().trim().length() > 0) {
			maxRatioHW = Float.valueOf(maxAspectRatio.getText().toString());
		}
		if (minAspectRatio.getText().toString() != null && minAspectRatio.getText().toString().trim().length() > 0) {
			minRatioHW = Float.valueOf(minAspectRatio.getText().toString());
		}
		maxAspectRatioVal = maxRatioHW;
		minAspectRatioVal = minRatioHW;
	}

	private boolean checkEditTextConditions(boolean isPortrait) {
		float maxRatioHW = -1;
		float minRatioHW = -1;
		float width = -1;
		float height = -1;

		if (maxAspectRatio.getText().toString() != null && maxAspectRatio.getText().toString().trim().length() > 0) {
			maxRatioHW = Float.valueOf(maxAspectRatio.getText().toString());
		}
		if (minAspectRatio.getText().toString() != null && minAspectRatio.getText().toString().trim().length() > 0) {
			minRatioHW = Float.valueOf(minAspectRatio.getText().toString());
		}
		if (widthEt.getText().toString() != null && widthEt.getText().toString().trim().length() > 0) {
			width = Float.valueOf(widthEt.getText().toString());
		}
		if (heightEt.getText().toString() != null && heightEt.getText().toString().trim().length() > 0) {
			height = Float.valueOf(heightEt.getText().toString());

		}
		if (maxRatioHW == -1 && minRatioHW != -1) {
			Toast.makeText(this, "Please Enter a valid Maximum Aspect Ratio", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (maxRatioHW == -1 && minRatioHW == -1 && widthEt.getText().toString() == null && heightEt.getText().toString() == null) {
			Toast.makeText(this, "Please Enter Min and Max Apect Ratio", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (maxRatioHW != -1 && minRatioHW == -1) {
			Toast.makeText(this, "Please Enter a valid Minimum Aspect Ratio", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (maxRatioHW != -1 && minRatioHW != -1) {
			if (minRatioHW >= maxRatioHW) {
				Toast.makeText(this, "Max Aspect Ratio should be bigger than Min Aspect Ratio", Toast.LENGTH_SHORT).show();
				return false;
			}
			maxAspectRatioVal = maxRatioHW;
			minAspectRatioVal = minRatioHW;
			return true;
		}
		if (maxRatioHW == -1 && minRatioHW == -1 && (width == 0 || height == 0)) {
			Toast.makeText(this, "Please Enter Valid Height and Width Values", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (maxRatioHW == -1 && minRatioHW == -1 && width != -1 && height != -1) {
			if (isPortrait) {
				float switchWidth = width;
				width = height;
				height = switchWidth;
			}
			maxAspectRatioVal = (float) (width / height * 1.1);
			minAspectRatioVal = (float) (width / height * 0.9);
			if (height > width) {
				maxAspectRatioVal = (float) (width / height * 1.1);
				minAspectRatioVal = (float) (width / height * 0.9);
			} else {
				maxAspectRatioVal = (float) (height / width * 1.1);
				minAspectRatioVal = (float) (height / width * 0.9);
			}
			return true;

		}
		return false;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub		
		cpMICR.setSelection(ocrSpinenrPosition);
		super.onResume();
	}
	@TargetApi(23)
	@SuppressLint("NewApi")
	private void setSessionParams(CaptureIntent.baseCaptureParams input) {
		input.showInfoScreen = infoScreen.isChecked();
		input.enableCountdownSound = enableCountDownSound.isChecked();
		input.outputOriginalImage = shouldOutputOriginalImage.isChecked();
		input.outputColorImage = shouldOutputColoredImage.isChecked();
		input.outputBinarizedImage = shouldOutputBWImage.isChecked();
		input.outputGrayscaleImage = shouldOutputGrayScale.isChecked();
		input.useMaxResolution = enableMaxResolutionStills.isChecked();
		input.enableIQA = btnIQA.isChecked();
		input.tapToFocus = enableTapToFocus.isChecked();
		//input.useCameraAPI2 = useCameraAPI2Btn.isChecked();
		if (isTestMode) {
			CameraManagerController.sessionType = SessionType.TEST;
		}


		// input.maximumNumOfRetries = maxNumberOfRetriesVal;


		switch (ocrSpinenrPosition) {
			case 0:
				if (imageType == TISDocumentType.CHECK) {
					input.ocrType = OCRType.E_138B;
				}
				if (imageType == TISDocumentType.CARD) {
					input.ocrType = OCRType.OFF;
				}
				if (imageType == TISDocumentType.PAYMENT) {
					input.ocrType = OCRType.OFF;
				}
				break;
			case 1:
				if (imageType == TISDocumentType.CHECK) {
					input.ocrType = OCRType.CMC7;
				}
				if (imageType == TISDocumentType.CARD) {
					input.ocrType = OCRType.PAN;
				}
				if (imageType == TISDocumentType.PAYMENT) {
					input.ocrType = OCRType.OCRA;
				}
				break;
			case 2:
				if (imageType == TISDocumentType.CHECK) {
					input.ocrType = OCRType.OFF;
				}
				if (imageType == TISDocumentType.CARD) {
					input.ocrType = OCRType.MRZ;
				}
				break;
		}


		if (input.ocrType == null)
			input.ocrType = OCRType.OFF;
		ocrType = input.ocrType;


		input.debugMode = btnDebugMode.isChecked();
		input.enableIQA = btnIQA.isChecked();
		input.enableBlurDetection = btnBlurDetection.isChecked();
		input.enableBlurDetectionOnBackSide = btnBlurDetectionOnBack.isChecked();
		input.enableSoftCapture = btnSoftCapture.isChecked();
		input.softCaptureThreshold = softCaptureThreshold;
		input.customView = btnCustomView.isChecked();
		input.videoFeedProcessing = btnVideoMode.isChecked();
		input.multiPageCapture = btnMultiCaptureButton.isChecked();
		input.uxType = isDynamicCapture ? TISFlowUXType.LIVE : TISFlowUXType.STATIC;
		input.scanFrontOnly = btnScanFrontOnlyBtn.isChecked();
		input.scanBackOnly = btnScanBackOnlyBtn.isChecked();
		input.showCountDown = btnShowCountDown.isChecked();
		input.showGuidelinesIndicators = btnShowGuideLinesIndicators.isChecked();
		if (isEnableBarcode) {
			if (isScanBackOnly)
				input.scanBarcodeLocation = TISScanBarcodeLocation.BARCODE_BACK;
			else
				input.scanBarcodeLocation = TISScanBarcodeLocation.BARCODE_FRONT;
		} else
			input.scanBarcodeLocation = TISScanBarcodeLocation.BARCODE_NONE;
		input.barcodeTypes = getBarcodeTypes();
		input.binarizationType = binarizationType;
		input.binarizationThreshold = binarizationTrashold;
		input.colorImageCompression = colorImageCompression;
		input.grayscaleImageCompression = grayscaleImageCompression;

		input.license = new TISLicenseParameters("ABCD","","");

		// input.maxNumberOfRetires = ShowcasePreferences.getMaxNumberOfRetries(this);
		//ViewPagerFragmentActivity
	}
	// permission denied, save to internal storage.
	@TargetApi(23)
	private boolean askPermission() {
		try {
			Context c = getApplicationContext();
			int res = this.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (res == PackageManager.PERMISSION_GRANTED) {
				// create .mobiflow and .debugmobiflow folders.
				isExternalStoragePermissionApproved = true;
				if (isMultiCapture) {
					copyFilesFromInternalToExternalStorage();
				} else {
					saveSessionDataToDevice(getImageBasePath(true)); // save images and log file if debug enabled.
				}
			} else {
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_PERMISSION);
			}
			return true;
		}catch (Exception e){
			Log.e(tag,Log.getStackTraceString(e));
		}
		return false;
	}
	private void copyFilesFromInternalToExternalStorage() {
		Thread fileSaver = new Thread() {
			public void run() {

				String internalStorageLocation = getBaseContext().getFilesDir().getAbsolutePath() + "/" + FileUtils.tempPath + "/";
				File sdCard = Environment.getExternalStorageDirectory();
				String externalStorageLocation = sdCard.getAbsolutePath() + "/" + ".mobiflow/";
				try

				{
					File cacheDir = new File(internalStorageLocation);
					List<File> filesList = Arrays.asList(cacheDir.listFiles());
					if (filesList == null) {
						return;
					}
					File to;
					int filesNumber = filesList.size();
					for (int i = 0; i < filesNumber; i++) {
						File curr = filesList.get(i);
						to = new File(externalStorageLocation + curr.getName());
						to.createNewFile();
						//Log.e("File Size",String.valueOf(from.length()));
						copyFile(curr, to);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		fileSaver.start();
	}

	private String getImageBasePath(boolean isExternalStorageApproved) {
		String timeAndData = "FRONT_" + FileUtils.getCurrentTime() + ".jpg";
		if (isExternalStorageApproved) {
			File sdCard = Environment.getExternalStorageDirectory();
			baseImagesFolder = sdCard.getAbsolutePath() + "/" + ".mobiflow/";
			// create .mobiflow dir
			File dir = new File(baseImagesFolder);
			dir.mkdirs();
		} else {
			baseImagesFolder = this.getFilesDir().getAbsolutePath() + "/" + FileUtils.tempPath + "/";
		}
		String basePath;
		basePath = baseImagesFolder + timeAndData;
		return basePath;
	}

	private ArrayList<TISBarcodeType> getBarcodeTypes() {
		if (isEnableBarcode) {
			ArrayList<TISBarcodeType> barcodeTypes = new ArrayList<TISBarcodeType>();
			if (isEnableQRCode) {
				barcodeTypes.add(TISBarcodeType.QR_CODE);
				barcodeTypes.add(TISBarcodeType.AZTEC_CODE);
				barcodeTypes.add(TISBarcodeType.DATA_MATRIX_CODE);
			}
			barcodeTypes.add(TISBarcodeType.UPCE_CODE);
			barcodeTypes.add(TISBarcodeType.CODE_39_CODE);
			barcodeTypes.add(TISBarcodeType.CODE_39_MOD_43_CODE);
			barcodeTypes.add(TISBarcodeType.EAN_13_CODE);
			barcodeTypes.add(TISBarcodeType.EAN_8_CODE);
			barcodeTypes.add(TISBarcodeType.CODE_93_CODE);
			barcodeTypes.add(TISBarcodeType.CODE_128_CODE);
			barcodeTypes.add(TISBarcodeType.PDF_417_CODE);
			barcodeTypes.add(TISBarcodeType.INTERLEAVED_2_OF_5_CODE);
			barcodeTypes.add(TISBarcodeType.ITF_14_CODE);
			return barcodeTypes;
		}
		return null;
	}

	private void startCapture() {
		ocrSpinenrPosition = cpMICR.getSelectedItemPosition();
		switch (imageType) {
			case CHECK:
				startCheckFlow();
				break;

			case PAYMENT:
				startPaymentFlow();
				break;
			case FULL_PAGE:
				startDocumentFlow();
				break;
			case CUSTOM:
				startCustomFlow();
				break;
			case PASSPORT:
				startPassportFlow();
				break;
			case CARD:
				startCardFlow();
				break;

			default:
				startPaymentFlow();

		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_EXTERNAL_PERMISSION: {
				String timeAndData = FileUtils.getCurrentTime() + ".jpg";

				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// create .mobiflow and .debugmobiflow folders.
					isExternalStoragePermissionApproved = true;
					saveSessionDataToDevice(getImageBasePath(true));
					// permission was granted, save to sd card
				} else {
					isExternalStoragePermissionApproved = false;
					saveSessionDataToDevice(getImageBasePath(false));
					// permission denied, save to internal storage.
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}

	@Override
	protected void ensureActionBar() {
		demoActionBar = (DemoActionBar) findViewById(R.id.demoActionBar_ref);
		demoActionBar.ensureUI(ActionBarContext.SHOW_CASE);
		if (ShowcasePreferences.isIQAMode(this)){
			demoActionBar.setRightBtnVisiblity(true);
		}
		demoActionBar.setActionBarButtonClickListener(new ActionBarButtonClickListener() {
			@Override
			public void onBtnRightClick() {
				// settings button
				NavigationManager.getInstance().showNewScreen(ShowCaseActivity.this, IQASettingsController.class, null, Constants.IQA_SETTINGS_REQUEST_CODE, false);
			}

			@Override
			public void onBtnLeftClick() {
				// TODO Auto-generated method stub

			}

		});
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View arg1, int position, long arg3) {
		// change the view for different types of images.

		if (adapter.getId() == R.id.inputType) {
			MicrLayout.setVisibility(View.GONE);
			txtValidFromLayout.setVisibility(View.GONE);
			txtValidToLayout.setVisibility(View.GONE);
			apectRatioMinLayout.setVisibility(View.GONE);
			apectRatioMaxLayout.setVisibility(View.GONE);
			ratioWidth.setVisibility(View.GONE);
			ratioHeight.setVisibility(View.GONE);
			portraitModeLayout.setVisibility(View.GONE);
			seekBarLayout.setVisibility(View.VISIBLE);
			switch (position) {
				case 0:
					MicrLayout.setVisibility(View.VISIBLE);
					txtValidFromLayout.setVisibility(View.VISIBLE);
					txtValidToLayout.setVisibility(View.VISIBLE);
					ensureMICRTypeSpinner();
					imageType = TISDocumentType.CHECK;
					seekBarLayout.setVisibility(View.GONE);
					apectRatioMaxLayout.setVisibility(View.VISIBLE);
					apectRatioMinLayout.setVisibility(View.VISIBLE);
					if (ShowcasePreferences.isFirstRun(this)) {
						setCheckDefaultToggleState();
						ShowcasePreferences.setIsFirstRun(this, false);
					}
					break;
				case 1:
					imageType = TISDocumentType.PAYMENT;
					MicrLayout.setVisibility(View.VISIBLE);
					ensureMICRTypeSpinner();
					if (ShowcasePreferences.isFirstRun(this)) {
						setDefaultToggleState(TISDocumentType.PAYMENT);
						ShowcasePreferences.setIsFirstRun(this, false);
					}
					break;
				case 2:
					imageType = TISDocumentType.FULL_PAGE;
					if (ShowcasePreferences.isFirstRun(this)) {
						setDefaultToggleState(TISDocumentType.FULL_PAGE);
						ShowcasePreferences.setIsFirstRun(this, false);
					}
					break;
				case 3:
					apectRatioMinLayout.setVisibility(View.VISIBLE);
					apectRatioMaxLayout.setVisibility(View.VISIBLE);
					ratioWidth.setVisibility(View.VISIBLE);
					ratioHeight.setVisibility(View.VISIBLE);
					portraitModeLayout.setVisibility(View.VISIBLE);
					imageType = TISDocumentType.CUSTOM;
					if (ShowcasePreferences.isFirstRun(this)) {
						setDefaultToggleState(TISDocumentType.CUSTOM);
						ShowcasePreferences.setIsFirstRun(this, false);
					}
					break;
				case 4:
					imageType = TISDocumentType.PASSPORT;
					if (ShowcasePreferences.isFirstRun(this)) {
						setDefaultToggleState(TISDocumentType.PASSPORT);
						ShowcasePreferences.setIsFirstRun(this, false);
					}
					break;

				case 5:
					imageType = TISDocumentType.CARD;
					MicrLayout.setVisibility(View.VISIBLE);
					ensureMICRTypeSpinner();
					if (ShowcasePreferences.isFirstRun(this)) {
						setDefaultToggleState(TISDocumentType.CARD);
						ShowcasePreferences.setIsFirstRun(this, false);
					}
					break;

				default: // payments

					break;
			}
		}
		if (adapter.getId() == R.id.levlerType) {
			int spinnerPosition = levlerType.getSelectedItemPosition();

			switch (spinnerPosition) {
				case 0:
					CameraManagerController.levlerType = LevelerType.ONE_UNIT;
					break;
				case 1:
					CameraManagerController.levlerType = LevelerType.TWO_UNITS;
					break;
				case 2:
					CameraManagerController.levlerType = LevelerType.SCALE;
					break;
				case 3:
					CameraManagerController.levlerType = LevelerType.NONE;
					break;
			}

		} else if (adapter.getId() == R.id.binarizationTypeSpinner) {
			int spinnerPosition = binarizationTypeSpinner.getSelectedItemPosition();

			switch (spinnerPosition) {
				case 0:
					binarizationType = TISBinarizationType.TIS_GENERAL_BINARIZATION;
					BinarizationSeekBarProgress.setVisibility(View.VISIBLE);
					binarizationTrasholdSeekbar.setVisibility(View.VISIBLE);
					break;
				case 1:
					binarizationType = TISBinarizationType.TIS_CHECK_BINARIZATION;
					BinarizationSeekBarProgress.setVisibility(View.GONE);
					binarizationTrasholdSeekbar.setVisibility(View.GONE);
					break;

			}

		}

	}
	private void setCheckDefaultToggleState() {
		btnIQA.setChecked(true);
		btnBlurDetection.setChecked(false);
		btnBlurDetectionOnBack.setChecked(false);
		btnVideoMode.setChecked(true);
		btnScanFrontOnlyBtn.setChecked(false);
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (CameraManagerController.sessionType == SessionType.TEST) {
			askPermission();
		}

		if (requestCode == CaptureIntent.MOBI_FLOW_REQUEST_CODE) {
			Log.e("onActivityResult",String.valueOf(resultCode));
			switch (resultCode) {
				case RESULT_OK:
					// parse sesion image result.
					currentSessionResult = CaptureIntent.parseActivityResult(requestCode, resultCode, data);
					if (currentSessionResult.getOcrParams() != null) {
						String ocrResult = currentSessionResult.getOcrParams()[1];
						// for passport you can get results hashMap with this helper method
						//HashMap<String, String> passportResult = OcrValidationUtils.parsePassportResult(ocrResult);


					}

					// get the front image size if needed for split .
					if (currentSessionResult.getFrontImageRectArray() != null) {
						inputFrontImageArray = currentSessionResult.getFrontImageRectArray();

					}

					// use this to get barcode results.
					//currentSessionResult.getBarcodeResult();

					// save multiple images on MultiCapture mode.
					if (!isMultiCapture) {
						// the images are received as byte[] on device memory, this is helper method to save the images to device file system.
						// if needed

					}
					break;
				// user pressed on cancel button
				case RESULT_CANCELED:
					break;
				// user pressed cancel from Alert
				case CameraManagerController.RESULT_CANCELED_FROM_ALERT: // User tap on Cancel from alert
					break;

				case CameraManagerController.RESULT_MULTI_CAPTURE_FINISH:

					break;

//			case CameraManagerController.RESULT_CAMERA_PERMISSION_ACSSES_DENIED:
//				// on api 23 target apps, MobiFlow checking for camera permission if not approved will reach here. 
//				String permissionErrorMessage = data.getStringExtra(CaptureIntent.MOBIFLOW_ERROR_DETAILS);
//				if (permissionErrorMessage != null)
//					Log.e("error", permissionErrorMessage);
//				break;
				// get result after session has been closed.
				case CameraManagerController.RESULT_CLOSE_SESSION:
					// will reach here after
					currentSessionResult = CaptureIntent.parseActivityResult(requestCode, resultCode, data);
					// decode images, Tiff and jpg

					if (currentSessionResult.getFrontImageRectArray() != null) {
						inputFrontImageArray = currentSessionResult.getFrontImageRectArray();
					}


					break;

				// will get here if error or exception was thorwn from the library.
				case CameraManagerController.RESULT_LIBRARY_ERROR:
					//the exception or error will be received here.
					String errorMessage = data.getStringExtra(CaptureIntent.MOBIFLOW_ERROR_DETAILS);
					if (errorMessage != null)
						Log.e("error", errorMessage);
					// do something with the error
					// got unexpected Error from the Library or exception
					break;
				//			case CameraManagerController.RESULT_CAMERA_PERMISSION_ACSSES_DENIED:
				//
				//				break;
				case CameraManagerController.RESULT_LICENSE_INVALID:
					String licenseErrorMessage = data.getStringExtra(CaptureIntent.MOBIFLOW_ERROR_DETAILS);
					if (licenseErrorMessage != null)
						Log.e("error", licenseErrorMessage);
					break;
			}


			// helper method if needed to remove the images byte array

		}
		// must use this to unregister the listeners
		CameraController.unregisterListener();
		askPermission();
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void saveSessionDataToDevice(String basePath) {
		try {
			String baseFront = basePath;
			String baseBack = basePath.replace("FRONT_", "BACK_");

			latestCapturedImages = new ArrayList<String>();
			//saveDataForDebugMode(basePath.replace(".jpg", ".tiff"));
			saveFrontSideImages(baseFront);

					//saveDataForDebugMode(baseBack.replace(".jpg", "_bin.tiff"));
			saveBackSideImages(baseBack);
			if (btnDebugMode.isChecked() && isExternalStoragePermissionApproved) {

				File logDir = new File(baseImagesFolder.replace("mobiflow", "debugmobiflow"));
				logDir.mkdirs();
				File from;
				File to;
				try {
					///data/user/0/com.topimagesystems.sample/files/.mobiflow/log.txt
					if(FileUtils.logFilePath == null)
						return;

					from = new File(FileUtils.logFilePath);
					to = new File(baseImagesFolder.replace("mobiflow", "debugmobiflow") + "/log.txt");
					to.createNewFile();
					//Log.e("File Size",String.valueOf(from.length()));
					copyFile(from, to);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				//from.renameTo(to);

				//			} catch (IOException e) {
				//				e.printStackTrace();

			}
		}catch (Exception e){
			Log.e(tag, Log.getStackTraceString(e));
		}

	}

	private void fillExifAttributes(String path) {
		try {
			ExifInterface exif = new ExifInterface(path);
			exif.setAttribute(ExifInterface.TAG_MODEL, FileUtils.getDeviceName() + " Android version " + Build.VERSION.RELEASE);
			exif.setAttribute(ExifInterface.TAG_MAKE, getResources().getString(R.string.TISVersion));
			exif.saveAttributes();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// check exif attributes
		/*try {
			ExifInterface exif = new ExifInterface(frontBinJpgPath);
			String comment = exif.getAttribute("UserComment");
			Log.i("my attribute", "UserComment:" + comment);
			comment = exif.getAttribute(ExifInterface.TAG_MAKE);
			Log.i("my attribute", "TAG_MAKE:" + comment);
			comment = exif.getAttribute(ExifInterface.TAG_MODEL);
			Log.i("my attribute", "TAG_MODEL:" + comment);

		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}


	@Override
	public void onFailed() {
		// TODO Auto-generated method stub

	}

	private void saveDataForMulticapture(String[] extraData) {
		if (extraData == null) {
			return;
		}
		try {
			imagesTimeStampArray.add(getTimeStampFromFilePath(extraData[0]));
			multiCaptureSessionArray.add(String.valueOf(++sessionCounter));

			if (extraData[3] != null)
				multiCaptureExtraDataArray.add(extraData[3]);
			else
				multiCaptureExtraDataArray.add(null);


			if (!(isScanFrontOnly || isScanBackOnly)) {
				imagesTimeStampArray.add(getTimeStampFromFilePath(extraData[1]));
				multiCaptureSessionArray.add(String.valueOf(sessionCounter));
				multiCaptureExtraDataArray.add(null);
			}
		} catch (Exception e) {
			Logger.i(tag, Log.getStackTraceString(e));
		}
	}

	private void saveDataForDebugMode(String filePath) {
		try {

			String fileToAdd = getTimeStampFromFilePath(filePath);

			if (fileToAdd != null && imagesTimeStampArray!= null) {
				imagesTimeStampArray.add(fileToAdd);
			}
		}catch (Exception e){
			Log.e(tag,Log.getStackTraceString(e));
		}
	}

	public static void copyFile(File src, File dst) throws IOException {
		FileInputStream fileIn = new FileInputStream(src);
		FileChannel inChannel = fileIn.getChannel();

		FileOutputStream fileOut = new FileOutputStream(dst);
		FileChannel outChannel = fileOut.getChannel();

		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null) {
				inChannel.close();
				fileIn.close();
			}
			if (outChannel != null)
				outChannel.close();
			fileOut.close();
		}
	}



	private String getTimeStampFromFilePath(String path) {
		try {
			String fileName = path.substring(path.indexOf("_") + 1, path.lastIndexOf("_"));
			return fileName;
		} catch (Exception e) {
			return null;
		}

	}

	private void saveFrontSideImages(String path) {
		if (path != null) {
			if (path.charAt(path.length() - 5) == '_')
				path = path.substring(0, path.length() - 5).concat(".jpg");
			saveLastImageIfNeedToDisplay(SessionResultParams.jpegBWFront, ShowcasePreferences.getShouldOutputBWImage(this),
					path.replace(".jpg", "_.jpg"), true);
			saveLastImageIfNeedToDisplay(SessionResultParams.originalFront, ShowcasePreferences.getOutputOriginalImage(this),
					path.replace(".jpg", "_original.jpg"), true);
			saveLastImageIfNeedToDisplay(SessionResultParams.tiffFront, ShowcasePreferences.getShouldOutputBWImage(this),
					path.replace(".jpg", "_.tiff"), false);
			saveLastImageIfNeedToDisplay(SessionResultParams.grayscaleFront, ShowcasePreferences.getCreateGrayscale(this),
					path.replace(".jpg", "_gray.jpg"), false);
			saveLastImageIfNeedToDisplay(SessionResultParams.colorFront, ShowcasePreferences.getShouldOutputColoredImage(this),
					path.replace(".jpg", "_colored.jpg"), true);
		}
	}


	private void saveBackSideImages(String path) {
		if (path != null) {
			if (path.charAt(path.length() - 5) == '_')
				path = path.substring(0, path.length() - 5).concat(".jpg");
			saveLastImageIfNeedToDisplay(SessionResultParams.jpegBWBack, ShowcasePreferences.getShouldOutputBWImage(this),
					path.replace(".jpg", "_.jpg"), true);
			saveLastImageIfNeedToDisplay(SessionResultParams.originalBack, ShowcasePreferences.getOutputOriginalImage(this),
					path.replace(".jpg", "_original.jpg"), true);
			saveLastImageIfNeedToDisplay(SessionResultParams.tiffBack, ShowcasePreferences.getShouldOutputBWImage(this),
					path.replace(".jpg", "_.tiff"), false);
			saveLastImageIfNeedToDisplay(SessionResultParams.grayscaleBack, ShowcasePreferences.getCreateGrayscale(this),
					path.replace(".jpg", "_gray.jpg"), false);
			saveLastImageIfNeedToDisplay(SessionResultParams.colorBack, ShowcasePreferences.getShouldOutputColoredImage(this),
					path.replace(".jpg", "_colored.jpg"), true);
		}
	}

	private void saveLastImageIfNeedToDisplay(byte[] imageData, boolean shouldOutPut, String path, boolean shouldAddMetaData) {
		if (imageData != null && shouldOutPut) {
			FileUtils.writeToFile(imageData, path);
			latestCapturedImages.add(path);
			if (shouldAddMetaData)
				fillExifAttributes(path);
		}
	}



	private void saveSettings() {
		ShowcasePreferences.setIsBlurDetection(this, isBlurEnable);
		ShowcasePreferences.setIsBlurDetectionOnBack(this, btnBlurDetectionOnBack.isChecked());
		// setIsBlurDetection
		ShowcasePreferences.setInfoScreen(this, isShowInfoScreen);
		ShowcasePreferences.setGuideLinesIndicators(this, isShowGuideLinesIndicators);
		ShowcasePreferences.setOutputOriginalImage(this, isShouldOutputOriginalImage);
		ShowcasePreferences.setShouldOutputColoredImage(this, isShouldOutputColoredImage);
		ShowcasePreferences.setShouldOutputBWImage(this, isShouldOutputBWImage);
		ShowcasePreferences.setCountDownSound(this, isEnableCountDownSound);
		ShowcasePreferences.setIQAEnabled(this, isIQAEnabled);
		ShowcasePreferences.setIsDebugMode(this, isDebugMode);
		ShowcasePreferences.setCreateGrayscale(this, isShouldOutputGrayScaleImage);
		ShowcasePreferences.setScanFrontOnly(this, isScanFrontOnly);
		ShowcasePreferences.setScanBackOnly(this, isScanBackOnly);
		ShowcasePreferences.setIsCustomView(this, isCustomView);
		ShowcasePreferences.setIsVideoMode(this, isVideoMode);
		ShowcasePreferences.setIsMultiCapture(this, isMultiCapture);
		ShowcasePreferences.setShowCountDown(this, isShowCountDown);
		ShowcasePreferences.setIsTestMode(this, isTestMode);
		ShowcasePreferences.setShowCountDown(this, isShowCountDown);
		ShowcasePreferences.setIsDynamicCapture(this, isDynamicCapture);
		ShowcasePreferences.setIsEnableBarcode(this, isEnableBarcode);
		ShowcasePreferences.setIsEnableQRCode(this, isEnableQRCode);
		ShowcasePreferences.setIsSoftCapture(this, isSoftCapture);
		ShowcasePreferences.setColorImageCompression(this, colorImageCompression);
		ShowcasePreferences.setGrayScaleImageCompression(this, grayscaleImageCompression);
		ShowcasePreferences.setSoftCaptureThreshold(this, softCaptureThreshold);
		ShowcasePreferences.setUsemaxResolutionStills(this, enableMaxResolutionStills.isChecked());
		ShowcasePreferences.setEnableTapToFocus(this, enableTapToFocus.isChecked());
		//ShowcasePreferences.setUseCameraAPI2(this, useCameraAPI2Btn.isChecked());
		String maxValAsString = maxAspectRatio.getText().toString();
		String minValAsString = minAspectRatio.getText().toString();

		if (maxValAsString != null && !maxValAsString.equals("") && minValAsString.length() > 0) {
			if (maxValAsString.contains(".")) {
				ShowcasePreferences.setMaxAspectRatio(this, Float.valueOf(maxAspectRatio.getText().toString()));
			} else {
				ShowcasePreferences.setMaxAspectRatio(this, Integer.valueOf(maxAspectRatio.getText().toString()));
			}
		}
		if (minValAsString != null && !minValAsString.equals("") && minValAsString.length() > 0) {
			if (minValAsString.contains(".")) {
				ShowcasePreferences.setMinAspectRatio(this, Float.valueOf(minAspectRatio.getText().toString()));
			} else {
				ShowcasePreferences.setMinAspectRatio(this, Integer.valueOf(minAspectRatio.getText().toString()));
			}
		}

	}

	@Override
	public void onMobiFlowGeneralMessageReceived(TISFlowGeneralMessages message, Object[] extraData, Context context) {
		// get messages from the library.
		CaptureIntent.callbackReturnMessage returnMessage = CameraController.getManagerListener();
		Log.e("onMobiFlowGeneralMessageReceived","TISFlowGeneralMessages "+ message.name().toString());
		switch (message) {
			case BACK_PRESSED:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;

			case CAPTURE_BACK:
				if (errorMessageReceived) { // if got error on front image don't
					// proceed to capture back, close
					// session with image result.
					returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				} else {
					returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				}
				break;

			case PAN_CARD_OCR_RESULT:
				String[] ocrData = (String[]) extraData;
				boolean panValidation = OcrValidationUtils.validationPanCard((String) extraData[1]);
				panValidation = true;
				if (panValidation) {
					returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
				} else {
					returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_FAILED);
				}
				break;
			case CHECK_OCR_RESULT:
				String[] ocrCheckData = (String[]) extraData;
				returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
				break;

			case ID_CARD_OCR_RESULT:
				returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
				break;

			case PASSPORT_OCR_RESULT:
				Object[] passportResult = extraData;
				boolean passportValidation = OcrValidationUtils.validatePassport((String) extraData[0], Integer.valueOf((String) (extraData[1])));
				HashMap<String, String> passportResultParsed = (HashMap<String, String>) extraData[4];
				if (passportValidation) {
					returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
				} else {
					returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_FAILED);
				}
				break;


			case MULTI_CAPTURE:
				// get the image here!!! image data can be taken
				if (extraData != null) {
					// save multi capture images
					if (isScanFrontOnly) {
						saveFrontSideImages((String) extraData[0]);
					} else if (isScanBackOnly) {
						saveBackSideImages((String) extraData[0]);
					} else {
						saveFrontSideImages((String) extraData[0]);
						saveBackSideImages((String) extraData[1]);
					}
				}

				saveDataForMulticapture((String[]) extraData);

				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			default:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
		}
		//		returnMessage = null;

	}

	@Override
	public void onMobiFlowErrorMessageReceived(TISFlowErrorMessage error, Object[] extraData, Context context) {
		// get Error messages from the library.
		Log.e("onMobiFlowErrorMessageReceived","TISFlowErrorMessage "+ error.name().toString());
		CaptureIntent.callbackReturnMessage returnMessage = CameraController.getManagerListener();
		switch (error) {
			case ERROR_MICR_READING_CHECK:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				errorMessageReceived = true;
				break;
			case ERROR_IMAGE_CONTRAST:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				errorMessageReceived = true;
				break;
			case ERROR_MICR_LENGHT: // for checks!
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				errorMessageReceived = true;
				break;
			case ERROR_NO_VALID_BOUNDING_BOX: // didn't find any boundingbox
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_BLUR_DETECTED:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_DARKNESS:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_CORNER_DATA:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_EDGE_DATA:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_SKEW:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_NUM_SPOTS:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_HORIZONTAL_STREAK:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_PIGGY_BACK:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_IQA_CARBON_STRIP:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_MICR_INTERUPPTED:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_MICR_ON_BACK:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case UNSUPPORTED_CAMERA:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case UNSPORTTED_CPU:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			case ERROR_OCR_READING: // ocr general error.
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;
			default:
				returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
				break;

		}
		return;
	}

	@Override
	public void onMobiFlowUIEventMessageReceived(TISFlowUIMessages message, ViewGroup cameraOverlayView) {
		// TODO Auto-generated method stub
		// get UI messages from the library.		
		Log.e("onMobiFlowUIEventMessageReceived","TISFlowGeneralMessages "+ message.name().toString());
		CaptureIntent.callbackReturnMessage returnMessage = CameraController.getManagerListener();
		switch (message) {
			case INIT_LAYOUT:

				break;
			case AFTER_PROCESSING:

				break;
			case BEFORE_PROCESSING:

				break;

			//cameraOverlayView.findViewById(R.id.customImgLogoWaterMark).setVisibility(View.GONE);
			case INSTRUCTION_CHANGED:
				break;

			default:
				break;
		}
	}

}
