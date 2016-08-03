package com.studentchef.fragment;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.studentchef.ActivitySingleRecipe;
import com.studentchef.R;
import com.studentchef.adapter.AdapterRecipe;
import com.studentchef.api.API;
import com.studentchef.apicall.VolleyApiCall;
import com.studentchef.apicall.VolleyApiCall.ApiListener;
import com.studentchef.fragmentdrawer.FragmentDrawer.ClickListener;
import com.studentchef.model.ItemRecipes;
import com.studentchef.parser.ParsingHelper;
import com.studentchef.utils.SavePref;
import com.studentchef.utils.YogiUtils;

public class FragmentRecipe extends Fragment implements ApiListener,
		OnClickListener {
	private static final String TAG = "FragmentRecipe";
	private View v;
	private YogiUtils yogiUtils;
	private static SavePref savePref;
	static VolleyApiCall volleyApiCall;
	RecyclerView recyclerView;
	private ArrayList<ItemRecipes> arrayListCat;
	private ArrayList<ItemRecipes> searcharrayListCat;
	EditText ed_search;
	ImageView img_search;
	static ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_recipe, container, false);

		initObjects();
		initViews();
		return v;
	}

	private void initViews() {
		// TODO Auto-generated method stub
		ed_search = (EditText) v.findViewById(R.id.ed_search);
		ed_search.setHint("Search " + savePref.getFname());
		img_search = (ImageView) v.findViewById(R.id.img_search);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		img_search.setOnClickListener(this);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recyclerView, new ClickListener() {

					@Override
					public void onClick(View view, int position) {

						ItemRecipes itemRecipes = arrayListCat.get(position);
						Intent i = new Intent(getActivity(),
								ActivitySingleRecipe.class);
						i.putExtra("list", itemRecipes);
						startActivity(i);

					}

					@Override
					public void onLongClick(View view, int position) {

					}
				}));

	}

	private void initObjects() {
		// TODO Auto-generated method stub
		savePref = new SavePref(getActivity());
		yogiUtils = new YogiUtils(getActivity());
		volleyApiCall = new VolleyApiCall(this);
		volleyApiCall.makeJsonObjReq(API.GET_RECIPES + savePref.getcatid(), 0);
		searcharrayListCat = new ArrayList<>();
	}

	@Override
	public void onResponse(String jsonString, int apiCall) {
		// TODO Auto-generated method stub
		progressBar.setVisibility(View.GONE);
		if (apiCall == 0) {

			Log.e(TAG, " recipe res: " + jsonString);

			arrayListCat = ParsingHelper.getRecipes(jsonString);
			recyclerView.setAdapter(new AdapterRecipe(getActivity(),
					arrayListCat));
			Log.e(TAG, " arr size == " + arrayListCat.size());

		}
	}

	@Override
	public void onError(String error, int apiCall) {
		// TODO Auto-generated method stub
		volleyApiCall.makeJsonObjReq(API.GET_RECIPES + savePref.getcatid(), 0);
	}

	static class RecyclerTouchListener implements
			RecyclerView.OnItemTouchListener {

		private GestureDetector gestureDetector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context,
				final RecyclerView recyclerView,
				final ClickListener clickListener) {
			this.clickListener = clickListener;
			gestureDetector = new GestureDetector(context,
					new GestureDetector.SimpleOnGestureListener() {
						@Override
						public boolean onSingleTapUp(MotionEvent e) {
							return true;
						}

						@Override
						public void onLongPress(MotionEvent e) {
							View child = recyclerView.findChildViewUnder(
									e.getX(), e.getY());
							if (child != null && clickListener != null) {
								clickListener.onLongClick(child,
										recyclerView.getChildPosition(child));
							}
						}
					});
		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

			View child = rv.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null
					&& gestureDetector.onTouchEvent(e)) {
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {
		}

		@Override
		public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == img_search) {
			searcharrayListCat.clear();
			if (ed_search.getText().toString().equals("")) {
				searcharrayListCat.addAll(arrayListCat);
			} else {

				for (ItemRecipes itemRecipes : arrayListCat) {
					if (itemRecipes
							.getName()
							.toLowerCase(Locale.getDefault())
							.trim()
							.contains(
									ed_search.getText().toString()
											.toLowerCase().trim())) {
						searcharrayListCat.add(itemRecipes);
					}

				}
			}

			recyclerView.setAdapter(new AdapterRecipe(getActivity(),
					searcharrayListCat));
		}
	}

	public static void refresh() {
		// TODO Auto-generated method stub
		progressBar.setVisibility(View.VISIBLE);

		volleyApiCall.makeJsonObjReq(API.GET_RECIPES + savePref.getcatid(), 0);
	}
}
