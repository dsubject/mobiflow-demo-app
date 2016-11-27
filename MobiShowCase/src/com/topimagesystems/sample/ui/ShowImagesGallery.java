package com.topimagesystems.sample.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import com.topimagesystems.controllers.imageanalyze.CameraManagerController;
import com.topimagesystems.sample.R;
import com.topimagesystems.sample.ShowCaseActivity;
import com.topimagesystems.util.FileUtils;
import com.topimagesystems.util.ImageUtils;
import com.topimagesystems.util.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ShowImagesGallery extends Activity {

	GridView gridView;
	GridViewAdapter imagesGridAdapter;

	private String micrRow;
	private String frontBarcodeRow;
	private String backBarcodeRow;

	ArrayList<File> lastSessionFiles;
	ArrayList<String> timeStampList;
	ArrayList<String> sessionList;
	ArrayList<String> micrList;
	ArrayList<String> lastSessionFileNames;
	
	boolean isMultiCapture = false;
	boolean isDebugMode = false;
	boolean hasBinImage;
	boolean micrAttachedToImage = false;
	private String tag = Logger.makeLogTag("ShowImagesGallery");
	public static String IMAGE_ID = "IMAGE_ID";
	public static String IMAGE_PATH = "IMAGE_PATH";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.session_images);

		if (CameraManagerController.imageType == null)
			return;

		Intent currentIntent = getIntent();

		micrRow = currentIntent.getStringExtra(ShowCaseActivity.MICR_FOUND);
		frontBarcodeRow = currentIntent.getStringExtra(ShowCaseActivity.BARCODE_FRONT_FOUND);
		backBarcodeRow = currentIntent.getStringExtra(ShowCaseActivity.BARCODE_BACK_FOUND);

		isMultiCapture = ShowcasePreferences.getIsMultiCapture(getApplicationContext());
		isDebugMode = ShowcasePreferences.isDebugMode(getApplicationContext());
		lastSessionFileNames = currentIntent.getStringArrayListExtra(ShowCaseActivity.LAST_SAVED_IMAGES_ARRAY);
		if (isMultiCapture) {
			timeStampList = currentIntent.getStringArrayListExtra(ShowCaseActivity.MULTI_PAGE_TIMESTAMP_ARRAY);
			sessionList = currentIntent.getStringArrayListExtra(ShowCaseActivity.MULTI_PAGE_SESSION_ARRAY);
			micrList = currentIntent.getStringArrayListExtra(ShowCaseActivity.MULTI_PAGE_MICR_ARRAY);
		}
		gridView = (GridView) findViewById(R.id.sessionImagesGridView);
		imagesGridAdapter = new GridViewAdapter(this, R.layout.image_grid_item, getImageArray());
		gridView.setNumColumns(isMultiCapture ? 2 : 1);
		gridView.setAdapter(imagesGridAdapter);
		micrAttachedToImage = false;
		hasBinImage = hasBinImage();
	}

	private GImageItem createImageItem(File imageFile, boolean showExtraData) {

		return createImageItem(imageFile, showExtraData, null, null);
	}

	private GImageItem createImageItem(File imageFile, boolean showExtraData, String extraData) {
		return createImageItem(imageFile, showExtraData, extraData, null);
	}

	private GImageItem createImageItem(File imageFile, boolean showExtraData, int index) {
		return createImageItem(imageFile, showExtraData, null, (sessionList != null) ? sessionList.get(index) : null);
	}

	private GImageItem createImageItem(File imageFile, boolean showExtraData, String extraData, int index) {
		return createImageItem(imageFile, showExtraData, extraData, (sessionList != null) ? sessionList.get(index) : null);
	}
	private boolean hasBinImage(){
		if (lastSessionFileNames != null && lastSessionFileNames.size() > 1){
			for (String fileName: lastSessionFileNames){
				if (fileName.contains("bin"))
					return true;
			}
			return false;
		}
		return false;
	}

	private GImageItem createImageItem(File imageFile, boolean showExtraData, String extraData, String sessionName) {
		GImageItem imageItem = new GImageItem();
		imageItem.title = getImageLabel(imageFile.getName(), sessionName);
		imageItem.file = imageFile;
		imageItem.path = imageFile.getAbsolutePath();
		int sizeValue = isMultiCapture ? 250 : 250;
		imageItem.thumbnailBitmap = ImageUtils.getScalledImageFromFile(getApplicationContext(), imageFile.getAbsolutePath(), sizeValue, sizeValue);
		if (showExtraData) {
			imageItem.showExtraData = true;
			imageItem.extraData = extraData;
		}
		return imageItem;
	}

	private boolean shouldShowMicr(String fileName) {
		String fileNameLowerCase = fileName.toLowerCase(Locale.US);
		if (!hasBinImage && fileNameLowerCase.contains("front") && micrRow != null && !micrAttachedToImage){
			micrAttachedToImage = true;
			return true;
		}
		return  !(isMultiCapture && (micrList == null || micrList.size() == 0))
				&& !(!isMultiCapture && micrRow == null) && !fileNameLowerCase.contains("back") && !fileNameLowerCase.contains("original")
				&& !fileNameLowerCase.contains("colored") && !fileNameLowerCase.contains("gray");

	}
	
	private String getBarcodeTextForImage(String fileName) {
		String barcodeString = null;
		String fileNameLowerCase = fileName.toLowerCase(Locale.US);
		
		if (!isMultiCapture) {
			if ((frontBarcodeRow != null) && !fileNameLowerCase.contains("back") && !fileNameLowerCase.contains("original")
			&& !fileNameLowerCase.contains("colored") && !fileNameLowerCase.contains("gray"))
				barcodeString = frontBarcodeRow;
			else if ((backBarcodeRow != null) && fileNameLowerCase.contains("back") && !fileNameLowerCase.contains("original")
						&& !fileNameLowerCase.contains("colored") && !fileNameLowerCase.contains("gray"))
				barcodeString = backBarcodeRow;
		}
		
		return barcodeString;
	}

	private String getImageLabel(String fileName, String sessionIndex) {
		try{
		String imageLabel = "";
		int stringId = -1;

		if (fileName.toLowerCase(Locale.US).contains("gray")) {
			if (fileName.toLowerCase(Locale.US).contains("back"))
				stringId = R.string.Back_gray;
			else
				stringId = R.string.Front_gray;
		}

		else if (fileName.toLowerCase(Locale.US).contains("original")) {
			if (fileName.toLowerCase(Locale.US).contains("back"))
				stringId = R.string.Back_original;
			else
				stringId = R.string.Front_original;
		}

		else if (fileName.toLowerCase(Locale.US).contains("colored")) {
			if (fileName.toLowerCase(Locale.US).contains("back"))
				stringId = R.string.Back_colored;
			else
				stringId = R.string.Front_colored;
		} else if (!fileName.toLowerCase(Locale.US).contains("tiff")) {
			if (fileName.toLowerCase(Locale.US).contains("back"))
				stringId = R.string.Back_tiff;
			else
				stringId = R.string.Front_tiff;
		}

		if (sessionIndex != null)
			imageLabel += "#" + (sessionIndex) + " ";

		if (stringId != -1)
			imageLabel += getResources().getString(stringId);
		else
			imageLabel += fileName;

		return imageLabel;
		}catch(Exception e){
			Logger.e(tag, Log.getStackTraceString(e));
			return "";
		}
	}

	private ArrayList<GImageItem> getImageArray() {
		ArrayList<GImageItem> imageItems = new ArrayList<GImageItem>();
		if (isMultiCapture) {
			lastSessionFiles = new ArrayList<File>();
			if (timeStampList == null)
				return imageItems;
			for (int i = 0; i < timeStampList.size(); i++) {
				String timeStamp = timeStampList.get(i);
				if (timeStamp == null)
					return imageItems;
				ArrayList<File> fileArray = findImageOnDeviceByTimeStamp(timeStamp);
				for (File imageFile : fileArray) {
					if (isValidImageFileForDisplay(imageFile)) {
						String extraData = null;
						if (shouldShowMicr(imageFile.getName()))
							extraData = isMultiCapture ? micrList.get(i) : micrRow;
						String barcodeText = getBarcodeTextForImage(imageFile.getName());
						if (barcodeText != null)
							extraData = (extraData == null) ? barcodeText : extraData + "\n" + barcodeText;
						if (extraData != null)
							imageItems.add(createImageItem(imageFile, true, extraData, i));
						else
							imageItems.add(createImageItem(imageFile, false, i));
					}

				}
				lastSessionFiles.addAll(fileArray);
			}
		} else {			
				lastSessionFiles = findImageOnDeviceByName();
			for (File imageFile : lastSessionFiles) {
				if (isValidImageFileForDisplay(imageFile)) {
					String extraData = null;
					if (shouldShowMicr(imageFile.getName()))
						extraData = micrRow;
					String barcodeText = getBarcodeTextForImage(imageFile.getName());
					if (barcodeText != null)
						extraData = (extraData == null) ? barcodeText : extraData + "\n" + barcodeText;
					if (extraData != null)
						imageItems.add(createImageItem(imageFile, true, extraData));
					else
						imageItems.add(createImageItem(imageFile, false));
				}
			}
		}

		return imageItems;

	}
	
	private boolean isValidImageFileForDisplay(File imageFile){
		String imageFileName = imageFile.getName().toLowerCase(Locale.US);
		return !imageFileName.contains(".tiff") && !imageFileName.contains("._tiff");
	}

	private ArrayList<File> findImageOnDeviceByTimeStamp(String timeStamp) {
		ArrayList<File> filesForTimeStamp = new ArrayList<File>();
		if (lastSessionFileNames == null)
			return filesForTimeStamp;
		
		for (String fileName : lastSessionFileNames) {
			if (fileName.contains(timeStamp)) {
				File file = new File(fileName);
				if (file.exists())
					filesForTimeStamp.add(file);
			}
		}
		
		return filesForTimeStamp;
	}
	
	private ArrayList<File> findImageOnDeviceByName() {
		ArrayList<File> filesForDisplay = new ArrayList<File>();
		if (lastSessionFileNames == null)
			return filesForDisplay;
		
		for (String fileName: lastSessionFileNames) {
			File file = new File(fileName);
			if (file.exists())
				filesForDisplay.add(file);
			
		}
		
		return filesForDisplay;
	}

	private void findImageOnDeviceByLastSession() {

		File imagesFolder = new File(FileUtils.getTempFilePath(getBaseContext()));

		File[] contents = imagesFolder.listFiles(new FilenameFilter() {
	        @Override
	        public boolean accept(File dir, String name) {
	            return name.toLowerCase(Locale.US).endsWith(".jpg");
	        }
		});
		lastSessionFiles = new ArrayList<File>(Arrays.asList(contents));
		/*if (CameraManagerController.isDebug) {

		} else {

		}

		File secondlastModify = null;
		String secondsLastModifyString = null;
		String secondLastDate = null;

		File lastModify = FileUtils.lastFileModified(FileUtils.getTempFilePath(getBaseContext()), null);
		String lastModifyString = lastModify.getPath();
		String lastDate = lastModify.getPath().substring(lastModifyString.indexOf("_") + 1, lastModifyString.lastIndexOf("."));

		if (!CameraManagerController.scanFrontOnly && !CameraManagerController.scanBackOnly) {
			secondlastModify = FileUtils.lastFileModified(FileUtils.getTempFilePath(getBaseContext()), lastDate);
			if (secondlastModify != null) {
				secondsLastModifyString = secondlastModify.getPath();
				secondLastDate = secondlastModify.getPath().substring(lastModifyString.indexOf("_"), lastModifyString.lastIndexOf(".") - 1);
			}
		}

		for (int i = 0; i < contents.length; i++) {
			String fileName = contents[i].getPath();
			if (fileName.contains(lastDate) || (secondlastModify != null && secondLastDate != null && fileName.contains(secondLastDate))
					&& secondsLastModifyString.contains("BACK")) {
				lastSessionFiles.add(contents[i]);
			}

		}*/
	}

	public static class GImageItem {
		public Bitmap thumbnailBitmap;
		public String title;
		public String path;
		public String description;
		public boolean showExtraData = false;
		public String extraData;
		public File file;
	}

	public class GridViewAdapter extends ArrayAdapter<GImageItem> {
		private Context context;
		private int layoutResourceId;
		private ArrayList<GImageItem> data = new ArrayList<GImageItem>();

		public GridViewAdapter(Context context, int layoutResourceId, ArrayList<GImageItem> data) {
			super(context, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ViewHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new ViewHolder();
				holder.imageTitle = (TextView) row.findViewById(R.id.thumbTitle);
				holder.imageExtraData = (TextView) row.findViewById(R.id.micrTitle);
				holder.image = (ImageView) row.findViewById(R.id.thumbImage);
				row.setTag(holder);
			} else {
				holder = (ViewHolder) row.getTag();
			}

			final GImageItem item = data.get(position);
			holder.imageTitle.setText(item.title);
			holder.image.setImageBitmap(item.thumbnailBitmap);

			LayoutParams lp = holder.image.getLayoutParams();
			if (isMultiCapture) {
				lp.width = lp.height = 300;
				holder.image.setScaleType(ScaleType.CENTER_INSIDE);
			} else {
				lp.width = lp.height = LayoutParams.WRAP_CONTENT;
				holder.image.setScaleType(ScaleType.CENTER_INSIDE);
			}

			holder.image.setLayoutParams(lp);

			if (item.showExtraData)
				holder.imageExtraData.setText(item.extraData);
			else
				holder.imageExtraData.setText("");

			holder.item = item;
			row.setClickable(true);
			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					Intent imageZoom = new Intent(ShowImagesGallery.this, ZoomViewActivity.class);
					imageZoom.putExtra(IMAGE_PATH, holder.item.path);
					startActivity(imageZoom);

				}
			});

			return row;
		}

		private class ViewHolder {
			public GImageItem item;
			TextView imageExtraData;
			TextView imageTitle;
			ImageView image;
		}

		public ArrayList<GImageItem> getData() {
			return data;
		}
	}
}
