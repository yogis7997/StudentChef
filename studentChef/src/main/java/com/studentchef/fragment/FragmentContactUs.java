package com.studentchef.fragment;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.studentchef.R;
import com.studentchef.api.API;
import com.studentchef.apicall.VolleyApiCall;
import com.studentchef.apicall.VolleyApiCall.ApiListener;
import com.studentchef.utils.SavePref;
import com.studentchef.utils.YogiUtils;

public class FragmentContactUs extends Fragment implements ApiListener,
		OnClickListener {
	private static final String TAG = "FragmentRecipe";
	private View v;
	VolleyApiCall volleyApiCall;

	EditText ed_fullname, ed_email, ed_subject, ed_message;
	Button btn_submit;
	private ProgressDialog pd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_contact_us, container, false);
		initObjects();
		initViews();
		return v;
	}

	private void initViews() {
		// TODO Auto-generated method stub
		ed_fullname = (EditText) v.findViewById(R.id.editText1);
		ed_email = (EditText) v.findViewById(R.id.editText2);
		ed_subject = (EditText) v.findViewById(R.id.editText3);
		ed_message = (EditText) v.findViewById(R.id.editText4);
		btn_submit = (Button) v.findViewById(R.id.button1);
		btn_submit.setOnClickListener(this);

	}

	private void initObjects() {
		// TODO Auto-generated method stub
		volleyApiCall = new VolleyApiCall(this);
		volleyApiCall.makeJsonObjReq(API.GET_CATEGORIES, 0);
		pd = new ProgressDialog(getActivity());
		pd.setMessage("Loading...");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_submit) {
			if (ed_fullname.getText().toString().equals("")) {
				ed_fullname.setError("Please enter your full name");
			} else if (ed_email.getText().toString().equals("")) {
				ed_email.setError("Please Enter your email id");
			} else if (ed_subject.getText().toString().equals("")) {
				ed_subject.setError("Please enter subject");
			} else if (ed_message.getText().toString().equals("")) {
				ed_subject.setError("Please type message");
			} else {
				pd.show();
				try {
					volleyApiCall.makeJsonObjReq(
							API.CNTACT_US
									+ URLEncoder.encode(ed_fullname.getText()
											.toString(), "UTF-8") + "&email="
									+ URLEncoder.encode(ed_email.getText().toString(),"UTF-8")
									+ "&subject="
									+ URLEncoder.encode(ed_subject.getText().toString(),"UTF-8")
									+ "&message="
									+ URLEncoder.encode(ed_message.getText().toString(),"UTF-8"), 0);
				} catch (Exception e) {
					Log.e(TAG, " Exception== " + e.getMessage());
				}
			}

		}
	}

	@Override
	public void onResponse(String jsonString, int apiCall) {
		// TODO Auto-generated method stub
		Log.e(TAG, jsonString);
		pd.dismiss();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject status = object.getJSONObject("status");
			String code = status.getString("code");
			if (code.equals("1")) {
				ed_email.setText("");
				ed_fullname.setText("");
				ed_message.setText("");
				ed_subject.setText("");
			}
			String message = status.getString("message");
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onError(String error, int apiCall) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(),
				"Check your internet connection and submit again!",
				Toast.LENGTH_SHORT).show();
	}

}
