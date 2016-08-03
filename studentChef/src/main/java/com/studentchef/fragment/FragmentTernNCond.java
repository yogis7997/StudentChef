package com.studentchef.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentchef.R;
import com.studentchef.apicall.VolleyApiCall;

public class FragmentTernNCond extends Fragment {
	private static final String TAG = "FragmentRecipe";
	private View v;
	VolleyApiCall volleyApiCall;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_term, container, false);
		return v;
	}



	
}
