package com.topimagesystems.sample.ui;

import java.io.File;

import com.topimagesystems.sample.R;
import com.topimagesystems.util.FileUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ShareActionProvider;

/** ZoomViewActivity.java */
public class ZoomViewActivity extends Activity implements OnMenuItemClickListener {

	private ShareActionProvider mShareActionProvider;
	private Bitmap currentZoomBitmap;
	private String currentImagePath;
	private String currentMicr;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_layout);
		//setContentView(R.id.main);
		TouchImageView img = new TouchImageView(getApplicationContext());
		// int imageId = getIntent().getIntExtra(ShowImagesGallery.IMAGE_ID,
		// -1);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		currentImagePath = getIntent().getStringExtra(ShowImagesGallery.IMAGE_PATH);
		if (currentImagePath != null)
			currentZoomBitmap = BitmapFactory.decodeFile(currentImagePath, options);

		// String filePath =
		// getIntent().getStringExtra(Constants.INTENT_IMAGE_PATH);

		// switch (imageId) {
		//
		// case R.id.firstImage:
		// currentImagePath = FileUtils.captureImageFront;
		// currentZoomBitmap = findImageOnDevice(FileUtils.captureImageFront);
		// break;
		//
		// case R.id.SecoundImage:
		// currentImagePath = FileUtils.captureImageFrontGray;
		// currentZoomBitmap =
		// findImageOnDevice(FileUtils.captureImageFrontGray);
		// break;
		// case R.id.orginalFront:
		// currentImagePath = FileUtils.captureImageFrontOriginal;
		// currentZoomBitmap =
		// findImageOnDevice(FileUtils.captureImageFrontOriginal);
		// break;
		// case R.id.coloredFront:
		// currentImagePath = FileUtils.captureImageFrontColored;
		// currentZoomBitmap =
		// findImageOnDevice(FileUtils.captureImageFrontColored);
		// break;
		//
		// case R.id.ThirdImage:
		// currentImagePath = FileUtils.captureImageBack;
		//
		// currentZoomBitmap = findImageOnDevice(FileUtils.captureImageBack);
		//
		// break;
		//
		// case R.id.ForthImage:
		// currentImagePath = FileUtils.captureImageBackGray;
		// currentZoomBitmap =
		// findImageOnDevice(FileUtils.captureImageBackGray);
		// break;
		//
		// case R.id.originalBack:
		// currentImagePath = FileUtils.captureImageBackOriginal;
		// currentZoomBitmap =
		// findImageOnDevice(FileUtils.captureImageBackOriginal);
		// break;
		//
		// case R.id.coloredBack:
		// currentImagePath = FileUtils.captureImageBackColored;
		// currentZoomBitmap =
		// findImageOnDevice(FileUtils.captureImageBackColored);
		// break;
		//
		// }
		if (currentZoomBitmap != null) {

			img.setImageBitmap(currentZoomBitmap);
			img.setMaxZoom(4f);
			setContentView(img);
		} else
			finish();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		getMenuInflater().inflate(R.menu.zoom_image_menu, menu);
		final MenuItem item = menu.findItem(R.id.menu_item_share);
		ShareActionProvider provider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();

		if (provider != null) {
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.setType("image/jpg");
			File file = new File(currentImagePath);
			Uri url = Uri.fromFile(file);

			shareIntent.putExtra(Intent.EXTRA_STREAM, url);
			provider.setShareIntent(shareIntent);
		}

		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (currentZoomBitmap != null) {
			currentZoomBitmap.recycle();
			currentZoomBitmap = null;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_share:
			mShareActionProvider = (ShareActionProvider) item.getActionProvider();
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.setType("image/jpg");
			// File file = new File(FileUtils.getTempFilePath(this) +
			// currentImagePath);
			File file = new File(currentImagePath);
			Uri url = Uri.fromFile(file);

			sendIntent.putExtra(Intent.EXTRA_STREAM, url);
			mShareActionProvider.setShareIntent(sendIntent);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Bitmap findImageOnDevice(String path) {

		Bitmap bitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		if (!path.contains(".mobiflow")) {
			bitmap = BitmapFactory.decodeFile(FileUtils.getTempFilePath(this) + path, options);
		} else {
			bitmap = BitmapFactory.decodeFile(path, options);
		}

		if (bitmap != null) {
			return bitmap;

		}
		return null;

	}

	// @Override
	// protected void ensureActionBar() {
	// demoActionBar = new DemoActionBar(this);
	// demoActionBar.ensureUI(ActionBarContext.GALLERY_ZOOM);
	// demoActionBar.setActionBarButtonClickListener(new
	// ActionBarButtonClickListener() {
	// @SuppressLint("NewApi")
	// @Override
	// public void onBtnRightClick() {
	// //mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	// Intent sendIntent = new Intent();
	// sendIntent.setAction(Intent.ACTION_SEND);
	// sendIntent.setType("image/jpg");
	// //File file = new File(FileUtils.getTempFilePath(this) +
	// currentImagePath);
	// File file = new File(currentImagePath);
	// Uri url = Uri.fromFile(file);
	//
	// sendIntent.putExtra(Intent.EXTRA_STREAM, url);
	// mShareActionProvider.setShareIntent(sendIntent);
	// // settings button
	// // NavigationManager.getInstance().showNewScreen(ShowCaseActivity.this,
	// IQASettingsController.class, null, Constants.IQA_SETTINGS_REQUEST_CODE,
	// false);
	// }
	//
	// @Override
	// public void onBtnLeftClick() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// });
	//
	//
	// }
}