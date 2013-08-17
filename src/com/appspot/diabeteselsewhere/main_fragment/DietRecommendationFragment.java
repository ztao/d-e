package com.appspot.diabeteselsewhere.main_fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.appspot.diabeteselsewhere.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DietRecommendationFragment extends Fragment {

	private static final int REQUEST_CODE = 1;
	private Bitmap bitmap;
	private ImageView imageView;
	private static final int DIALOG_ALERT = 10;

	private final static String dtag = "DietRecommendationFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.diet_recommendation,
				container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Button shareButton = (Button) getView().findViewById(R.id.shareFoodButton);
		Log.d(dtag,shareButton.toString());
		shareButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View View) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		InputStream stream = null;
		if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK)
			try {
				// We need to recyle unused bitmaps
				if (bitmap != null) {
					bitmap.recycle();
				}
				stream = getActivity().getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);

				ViewGroup rootView = (ViewGroup) getView().findViewById(R.id.foodList);
				imageView = new ImageView(getActivity());
				imageView.setImageBitmap(bitmap);
				imageView.setAdjustViewBounds(true);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(10, 20, 10, 30);
				imageView.setLayoutParams(lp);
				Button addDesBtn = new Button(getActivity());
				addDesBtn.setText("Add description");
				addDesBtn.setBackgroundColor(R.color.PaleGreen);
				rootView.addView(imageView);
				rootView.addView(addDesBtn);
				addDesBtn.setOnClickListener( new View.OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View view) {
						getActivity().showDialog(DIALOG_ALERT);
					} 
				});

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

	}

	public static Fragment create() {
		DietRecommendationFragment fragment = new DietRecommendationFragment();
		return fragment;
	}
}
