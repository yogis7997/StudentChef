package com.studentchef;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.studentchef.R;
import com.studentchef.api.API;
import com.studentchef.apicall.VolleyApiCall;
import com.studentchef.apicall.VolleyApiCall.ApiListener;
import com.studentchef.fragment.FragmentAboutUs;
import com.studentchef.fragment.FragmentChat;
import com.studentchef.fragment.FragmentContact;
import com.studentchef.fragment.FragmentContactUs;
import com.studentchef.fragment.FragmentHome;
import com.studentchef.fragment.FragmentRecipe;
import com.studentchef.fragment.FragmentSubmit;
import com.studentchef.fragment.FragmentTernNCond;
import com.studentchef.fragmentdrawer.FragmentDrawer;
import com.studentchef.model.ItemCategories;
import com.studentchef.parser.ParsingHelper;
import com.studentchef.utils.SavePref;
import com.studentchef.utils.YogiUtils;

public class ActivityMain extends AppCompatActivity implements
		FragmentDrawer.FragmentDrawerListener, ApiListener, OnClickListener {

	private static final String TAG = null;
	YogiUtils yogiUtils;
	SavePref savePref;
	private Toolbar toolbar;
	// private TextView title;
	private FragmentDrawer drawerFragment;
	ArrayList<ItemCategories> arrayListCat;
	VolleyApiCall volleyApiCall;
	ImageView img_refresh;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.fragmentParentViewGroup, new FragmentHome())
					.commit();
		}
		// if(savedInstanceState==null)
		initObjects();
		initToolbar();
	}

	private void initToolbar() {
		// TODO Auto-generated method stub
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		// title = (TextView) toolbar.findViewById(R.id.title);
		//
		// title.setText("Contacts");
		img_refresh = (ImageView) toolbar.findViewById(R.id.img_refresh);
		img_refresh.setOnClickListener(this);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("RobotoLight");
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		resetDrawer();
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
	}

	public void resetDrawer() {

		Log.e(TAG, " reset");
		drawerFragment = (FragmentDrawer) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_navigation_drawer);

		drawerFragment.setUp(R.id.fragment_navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout), toolbar,
				arrayListCat);
		drawerFragment.setDrawerListener(this);

	}

	private void initObjects() {
		// TODO Auto-generated method stub
		yogiUtils = new YogiUtils(this);
		yogiUtils.setStatusBarColor(findViewById(R.id.status_bar),
				getResources().getColor(R.color.status_color));

		volleyApiCall = new VolleyApiCall(this);
		volleyApiCall.makeJsonObjReq(API.GET_CATEGORIES, 0);

		savePref = new SavePref(this);

	}

	@Override
	public void onDrawerItemSelected(View view, int position, int size) {
		// TODO Auto-generated method stub
		// FragmentContact a2Fragment = new FragmentContact();
		// FragmentTransaction transaction = getSupportFragmentManager()
		// .beginTransaction();
		//
		// // Store the Fragment in stack
		// transaction.addToBackStack(null);
		// transaction.replace(R.id.fragment_mainLayout, a2Fragment).commit();

		Log.e(TAG, " size== " + size);
		if (position == 0) {
			ChangeFragment(new FragmentHome());
			img_refresh.setVisibility(View.GONE);
		} else if (position == size - 4) {
			ChangeFragment(new FragmentSubmit());
			img_refresh.setVisibility(View.GONE);
		} else if (position == size - 3) {
			ChangeFragment(new FragmentAboutUs());
			img_refresh.setVisibility(View.GONE);
		} else if (position == size - 2) {
			ChangeFragment(new FragmentContactUs());
			img_refresh.setVisibility(View.GONE);
		} else if (position == size - 1) {
			ChangeFragment(new FragmentTernNCond());
			img_refresh.setVisibility(View.GONE);
		} else {
			img_refresh.setVisibility(View.GONE);

			ChangeFragment(new FragmentRecipe());
		}

		// if (position == 0) {
		//
		// } else if (position == 1) {
		// ChangeFragment(new FragmentChat());
		// } else if (position == 2) {
		// // ChangeFragment(new FragmentContact());
		// } else if (position == 3) {
		// // ChangeFragment(new FragmentContact());
		// }

	}

	public void ChangeFragment(Fragment fr) {
		// FragmentManager fm = getFragmentManager();
		//
		// FragmentTransaction fragmentTransaction = fm.beginTransaction();
		//
		// fragmentTransaction.replace(R.id.fragment_place, fr);
		//
		// fragmentTransaction.commit();

		getFragmentManager().beginTransaction()
				.replace(R.id.fragmentParentViewGroup, fr).addToBackStack(null)
				.commit();

	}

	@Override
	public void onResponse(String jsonString, int apiCall) {
		// TODO Auto-generated method stub
		if (apiCall == 0) {

			Log.e(TAG, " cat res: " + jsonString);

			arrayListCat = ParsingHelper.getCategories(jsonString);
			Log.e(TAG, " arr size == " + arrayListCat.size());
			ItemCategories itemCategories = new ItemCategories();
			itemCategories.setDrawableicon(R.drawable.ic_resume);
			itemCategories.setName("Submit Your own recipe");
			arrayListCat.add(itemCategories);

			ItemCategories itemCategories1 = new ItemCategories();
			itemCategories1.setDrawableicon(R.drawable.ic_about);
			itemCategories1.setName("About Us");

			arrayListCat.add(itemCategories1);
			ItemCategories itemCategories2 = new ItemCategories();
			itemCategories2.setDrawableicon(R.drawable.ic_contact);
			itemCategories2.setName("Contact Us");
			arrayListCat.add(itemCategories2);

			ItemCategories itemCategories3 = new ItemCategories();
			itemCategories3.setDrawableicon(R.drawable.ic_tnc);
			itemCategories3.setName("Terms & Condition");
			arrayListCat.add(itemCategories3);
		}

	}

	@Override
	public void onError(String error, int apiCall) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == img_refresh) {
			FragmentRecipe.refresh();
		}
	}

}
