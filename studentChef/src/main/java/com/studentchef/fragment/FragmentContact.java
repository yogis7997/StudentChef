package com.studentchef.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studentchef.R;
import com.studentchef.adapter.AdapterContact;
import com.studentchef.api.API;
import com.studentchef.model.ItemContacts;
import com.studentchef.parser.ParsingHelper;
import com.studentchef.utils.SavePref;
import com.studentchef.utils.YogiUtils;

public class FragmentContact extends Fragment {

	private static final String TAG = "FragmentContact";
	private View v;
	private YogiUtils yogiUtils;
	private SavePref savePref;
	TextView tv_totalfriends;
	EditText ed_search;
	RecyclerView recyclerView;

	ArrayList<HashMap<String, String>> contactList;
	String contactnumbers = "";
	// private ProgressDialog progressDialog;
	ProgressBar progressBar;

	ArrayList<ItemContacts> arrayListContacts;
	private AdapterContact adapter;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_contact, container, false);

		initObjects();
		initViews();

		return v;
	}

	private void initViews() {
		// TODO Auto-generated method stub
		tv_totalfriends = (TextView) v.findViewById(R.id.tv_totalfriends);
		ed_search = (EditText) v.findViewById(R.id.ed_search);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

	}

	private void initObjects() {
		// TODO Auto-generated method stub
		yogiUtils = new YogiUtils(getActivity());

		savePref = new SavePref(getActivity());

		new GetContacts().execute();

	}

	public class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@SuppressWarnings("static-access")
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			contactList = yogiUtils.getContacts(getActivity());

			for (int i = 0; i < contactList.size(); i++) {

				Log.e(TAG, " contact== " + contactList.get(i).get("phone"));

				String s = contactList.get(i).get("phone");
				contactnumbers = contactnumbers.concat("," + s);

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Log.e(TAG,
					" conlist== "
							+ contactnumbers.substring(1,
									contactnumbers.length() - 1));

			new SyncContacts().execute();
		}
	}

	private class SyncContacts extends AsyncTask<Void, Void, String> {

		private String result = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(Void... params) {

			try {

				InputStream instream = null;

				JSONObject jsonObject = null;

				MultipartEntity reqEntity = new MultipartEntity();

				reqEntity.addPart("sync_contact", new StringBody("1"));
				reqEntity.addPart("contact", new StringBody(contactnumbers));
				reqEntity.addPart("token", new StringBody(savePref.getToken()));

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(API.BASE_URL);

				httppost.setEntity(reqEntity);

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					instream = entity.getContent();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream));
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}

					result = sb.toString();
					Log.e("test", "result sign up :" + result);
				}

			} catch (Exception e) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(String rr) {
			super.onPostExecute(rr);
			progressBar.setVisibility(View.GONE);

			arrayListContacts = ParsingHelper.getContacts(result);
			Log.e(TAG, " size== " + arrayListContacts.size());
			if (arrayListContacts.size() > 0) {
				initSetAdapter();
			}

		}
	}

	private void initSetAdapter() {
		// TODO Auto-generated method stub
		adapter = new AdapterContact(getActivity(), arrayListContacts);
		recyclerView.setAdapter(adapter);
	}

}
