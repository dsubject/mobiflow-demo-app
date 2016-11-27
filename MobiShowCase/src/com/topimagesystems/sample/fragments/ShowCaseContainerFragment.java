package com.topimagesystems.sample.fragments;

import com.topimagesystems.sample.R;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// this sample class can be used as an example for nested fragments

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ShowCaseContainerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.show_case_container_layout, container, false);

		Fragment fragment = new ShowCaseFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.root, fragment);
		transaction.commit();

		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
