package com.studentchef.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SavePref {

	private static final String TAG = "SavePref";
	Context con;
	String save_id;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	public SavePref(Context c) {

		con = c;
		preferences = PreferenceManager.getDefaultSharedPreferences(con);
		editor = preferences.edit();
	}

	public void setCtype(String ctype) {
		// TODO Auto-generated method stub
		// SharedPreferences preferences =
		// PreferenceManager.getDefaultSharedPreferences(con);
		// SharedPreferences.Editor editor = preferences.edit();
		editor.putString("ctype", ctype);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "in saved");
	}

	public String getCtype() {

		String ctype = preferences.getString("ctype", "");

		Log.d(TAG, "ctype received " + ctype);

		return ctype;
	}

	public void setInboxIn(String in) {
		// TODO Auto-generated method stub

		editor.putString("in", in);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "in saved");
	}

	public String getInboxIn() {

		String in = preferences.getString("in", "");

		Log.d(TAG, "in received " + in);

		return in;
	}

	// userId

	public void setUserId(String userid) {
		// TODO Auto-generated method stub

		editor.putString("setUserId", userid);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "setLoginDetail saved");
	}

	public String getUserId() {

		String name = preferences.getString("setUserId", "");

		Log.d(TAG, "getLoginDetail received " + name);

		return name;
	}

	public void setRId(String rid) {
		// TODO Auto-generated method stub

		editor.putString("rid", rid);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "rid saved");
	}

	public String getRrId() {

		String name = preferences.getString("rid", "");

		Log.d(TAG, "rid received " + name);

		return name;
	}

	public void contactJson(String jsonCartList) {
		// TODO Auto-generated method stub

		editor.putString("contacts", jsonCartList);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "jsoncontactSet");
	}

	public String getcontactJson() {

		String name = preferences.getString("contacts", "");

		Log.d(TAG, "JsonContact" + name);

		return name;
	}

	public void setUsername(String uname) {
		// TODO Auto-generated method stub

		editor.putString("setUsername", uname);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "uname saved");
	}

	public String getUserName() {

		String name = preferences.getString("setUsername", "");

		Log.d(TAG, "UserName " + name);

		return name;
	}

	public void setPhoneNumber(String phnum) {
		// TODO Auto-generated method stub

		editor.putString("phone_num", phnum);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "phone saved");
	}

	public String getPhoneNum() {

		String name = preferences.getString("phone_num", "");

		Log.d(TAG, "phone_num is " + name);

		return name;
	}

	public void setPass(String pass) {
		// TODO Auto-generated method stub

		editor.putString("pass", pass);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "pass saved");
	}

	public String getPass() {

		String name = preferences.getString("pass", "");

		Log.d(TAG, "pass is " + name);

		return name;
	}

	public void setuserImage(String imagePath) {
		// TODO Auto-generated method stub

		editor.putString("imagePath", imagePath);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "image saved " + imagePath);
	}

	public String getUserImage() {

		String name = preferences.getString("imagePath", "");

		Log.d(TAG, "image is " + name);

		return name;
	}

	public void setUserFname(String first_name) {
		// TODO Auto-generated method stub

		editor.putString("fname", first_name);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "fname saved " + first_name);
	}

	public String getUserFname() {

		String name = preferences.getString("fname", "");

		Log.d(TAG, "fname is " + name);

		return name;
	}

	public void setUserSname(String second_name) {
		// TODO Auto-generated method stub

		editor.putString("sname", second_name);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "second saved " + second_name);
	}

	public String getUserSname() {

		String name = preferences.getString("sname", "");

		Log.d(TAG, "sname is " + name);

		return name;
	}

	public void setUserStatus(String uStatus) {
		// TODO Auto-generated method stub

		editor.putString("status", uStatus);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "status saved " + uStatus);
	}

	public String getUserStatus() {

		String name = preferences.getString("status", "");

		Log.d(TAG, "status is " + name);

		return name;
	}

	public void setUserLoc(String uLoc) {
		// TODO Auto-generated method stub

		editor.putString("loc", uLoc);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "loc saved " + uLoc);
	}

	public String getUserLoc() {

		String name = preferences.getString("loc", "");

		Log.d(TAG, "loc is " + name);

		return name;
	}

	public void setUserGender(String gender) {
		// TODO Auto-generated method stub

		editor.putString("gend", gender);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "loc saved " + gender);
	}

	public String getUserGenter() {

		String name = preferences.getString("gend", "");

		Log.d(TAG, "loc is " + name);

		return name;
	}

	public void setDeciveId(String gender) {
		// TODO Auto-generated method stub

		editor.putString("setDeciveId", gender);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "setDeciveId saved " + gender);
	}

	public String getDeciveId() {

		String name = preferences.getString("setDeciveId", "");

		Log.d(TAG, "setDeciveId is " + name);

		return name;
	}

	public String getpaymentid() {
		// TODO Auto-generated method stub

		String payid = preferences.getString("setpayid", "");

		Log.d(TAG, "setpayid is " + payid);

		return payid;
	}

	public void setpaymentid(String id) {
		// TODO Auto-generated method stub

		editor.putString("setpayid", id);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "setDeciveId saved " + id);
	}

	public void setWaitingTime(String wtime) {
		// TODO Auto-generated method stub

		editor.putString("wtime", wtime);
		editor.commit();
		// System.out.println("val save email ");

		Log.d(TAG, "wtime saved " + wtime);
	}

	public String getWaitingTime() {
		// TODO Auto-generated method stub

		String payid = preferences.getString("wtime", "");

		Log.d(TAG, "wtime is " + payid);

		return payid;
	}

	public void clear() {
		editor.clear();
		editor.commit();
	}

	public void setFname(String first_name) {
		editor.putString("fname", first_name);
		editor.commit();

		Log.d(TAG, "wtime saved " + first_name);
	}

	public String getFname() {
		String fname = preferences.getString("fname", "");

		return fname;
	}

	public void setLname(String last_name) {
		editor.putString("last_name", last_name);
		editor.commit();

		Log.d(TAG, "wtime saved " + last_name);
	}

	public String getLname() {
		String last_name = preferences.getString("last_name", "");

		return last_name;
	}

	public void setEmailId(String email) {
		editor.putString("email", email);
		editor.commit();

		Log.d(TAG, "wtime saved " + email);
	}

	public String getEmailId() {
		String email = preferences.getString("email", "");

		return email;
	}

	public void setContact(String string) {
		// TODO Auto-generated method stub
		editor.putString("contact", string);
		editor.commit();

		Log.d(TAG, "contact saved " + string);

	}

	public String getContact() {
		String contact = preferences.getString("contact", "");

		return contact;
	}

	public void setToken(String token) {
		// TODO Auto-generated method stub
		editor.putString("token", token);
		editor.commit();

		Log.d(TAG, "token saved " + token);
	}

	public String getToken() {
		String token = preferences.getString("token", "");

		return token;
	}

	public void setcatid(String catid) {
		// TODO Auto-generated method stub
		editor.putString("catid", catid);
		editor.commit();

		Log.d(TAG, "token saved " + catid);
	}

	public String getcatid() {
		String catid = preferences.getString("catid", "");

		return catid;
	}
}
