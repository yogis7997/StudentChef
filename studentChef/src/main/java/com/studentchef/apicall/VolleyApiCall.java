package com.studentchef.apicall;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.studentchef.app.AppController;

public class VolleyApiCall {

	protected static final String TAG = "VolleyApiCall";
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

	/**
	 * Making json object request
	 * */

	ApiListener apiListener;

	public VolleyApiCall(ApiListener apiListener) {
		super();
		this.apiListener = apiListener;
	}

	public interface ApiListener {

		public void onResponse(String jsonString, int apiCall);

		public void onError(String error, int apiCall);
	}

	public void makeJsonObjReq(String url, final int apiCall) {

		Log.e(TAG, " url== " + url);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e(TAG, response.toString());

						apiListener.onResponse(response.toString(), apiCall);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						apiListener.onError(error.getMessage(), apiCall);

					}
				}) {

			/**
			 * Passing some request headers
			 * */
			// @Override
			// public Map<String, String> getHeaders() throws AuthFailureError {
			// HashMap<String, String> headers = new HashMap<String, String>();
			// headers.put("Content-Type", "application/json");
			// return headers;
			// }
			//
			// @Override
			// protected Map<String, String> getParams() {
			// Map<String, String> params = new HashMap<String, String>();
			// params.put("name", "Androidhive");
			// params.put("email", "abc@androidhive.info");
			// params.put("pass", "password123");
			//
			// return params;
			// }

		};

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	/**
	 * Making json array request
	 * */
	public void makeJsonArryReq(String url, final int apiCall) {
		Log.e(TAG, " url== " + url);
		JsonArrayRequest req = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						apiListener.onResponse(response.toString(), apiCall);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						apiListener.onError(error.getMessage(), apiCall);
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, tag_json_arry);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
	}
}
