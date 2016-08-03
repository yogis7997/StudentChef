package com.studentchef.fragmentdrawer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.studentchef.R;
import com.studentchef.api.API;
import com.studentchef.apicall.VolleyApiCall;
import com.studentchef.apicall.VolleyApiCall.ApiListener;
import com.studentchef.model.ItemCategories;
import com.studentchef.parser.ParsingHelper;
import com.studentchef.utils.Constants;
import com.studentchef.utils.SavePref;

@SuppressLint("NewApi")
public class FragmentDrawer extends Fragment implements ApiListener {

	private static String TAG = FragmentDrawer.class.getSimpleName();

	private RecyclerView recyclerView;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private NavigationDrawerAdapter adapter;
	private View containerView;
	private static String[] titles = null;
	private FragmentDrawerListener drawerListener;
	ArrayList<ItemCategories> arrayList;
	ArrayList<ItemCategories> arrayListCat;
	VolleyApiCall volleyApiCall;
	private View layout;
	private SavePref savePref;

	public FragmentDrawer() {
		Log.e(TAG, "drawer const");
	}

	public void setDrawerListener(FragmentDrawerListener listener) {
		this.drawerListener = listener;
	}

	public static List<NavDrawerItem> getData() {
		List<NavDrawerItem> data = new ArrayList<>();

		// preparing navigation drawer items
		for (int i = 0; i < titles.length; i++) {
			NavDrawerItem navItem = new NavDrawerItem();
			navItem.setTitle(titles[i]);
			data.add(navItem);
		}
		return data;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// drawer labels
		titles = getActivity().getResources().getStringArray(
				R.array.nav_drawer_labels);
		savePref = new SavePref(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflating view layout
		Log.e(TAG, "drawer onCreateView");
		// if (!Constants.isMainfragment) {
		layout = inflater.inflate(R.layout.fragment_navigation_drawer,
				container, false);
		// } else {
		// layout = inflater.inflate(R.layout.fragment_nav, container, false);
		// }
		volleyApiCall = new VolleyApiCall(this);
		volleyApiCall.makeJsonObjReq(API.GET_CATEGORIES, 0);
		recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

		// ll_desserts = (LinearLayout) layout.findViewById(R.id.ll_dessert);
		// ll_grill = (LinearLayout) layout.findViewById(R.id.ll_Grill);
		// ll_soup = (LinearLayout) layout.findViewById(R.id.ll_soup);
		// ll_salad = (LinearLayout) layout.findViewById(R.id.ll_Salads);

		// ImageView imageView = (ImageView)
		// layout.findViewById(R.id.imageView1);

		// imageView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// mDrawerLayout.closeDrawer(containerView);
		// }
		// });

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recyclerView, new ClickListener() {

					@Override
					public void onClick(View view, int position) {
						drawerListener.onDrawerItemSelected(view, position,arrayList.size());
						
						mDrawerLayout.closeDrawer(containerView);
						try {
							savePref.setcatid(arrayList.get(position).getId());
							savePref.setFname(arrayList.get(position).getName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onLongClick(View view, int position) {

					}
				}));

		return layout;
	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout,
			final Toolbar toolbar, ArrayList<ItemCategories> arrayListCat) {
		Log.e(TAG, "drawer setup");

		// if (Constants.isMainfragment) {
		containerView = getActivity().findViewById(
				R.id.fragment_navigation_drawer);
		// } else {
		// containerView = getActivity().findViewById(
		// R.id.fragment_navigation_drawer1);
		// }

		arrayList = arrayListCat;

		mDrawerLayout = drawerLayout;

		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
				toolbar, R.string.drawer_open, R.string.drawer_close) {
			@SuppressLint("NewApi")
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				toolbar.setAlpha(1 - slideOffset / 2);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

	}

	public static interface ClickListener {
		public void onClick(View view, int position);

		public void onLongClick(View view, int position);
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

	public interface FragmentDrawerListener {
		public void onDrawerItemSelected(View view, int position, int i);
	}

	@Override
	public void onResponse(String jsonString, int apiCall) {
		// TODO Auto-generated method stub
	
		arrayList = ParsingHelper.getCategories(jsonString);
		Log.e(TAG, " arr size == " + arrayList.size());
		ItemCategories itemCategories = new ItemCategories();
		itemCategories.setDrawableicon(R.drawable.ic_resume);
		itemCategories.setName("Submit Your Own Recipe");
		arrayList.add(itemCategories);

		ItemCategories itemCategories1 = new ItemCategories();
		itemCategories1.setDrawableicon(R.drawable.ic_about);
		itemCategories1.setName("About Us");

		arrayList.add(itemCategories1);
		ItemCategories itemCategories2 = new ItemCategories();
		itemCategories2.setDrawableicon(R.drawable.ic_contact);
		itemCategories2.setName("Contact Us");
		arrayList.add(itemCategories2);

		ItemCategories itemCategories3 = new ItemCategories();
		itemCategories3.setDrawableicon(R.drawable.ic_tnc);
		itemCategories3.setName("Terms & Conditions");
		arrayList.add(itemCategories3);
		if (arrayList.size() > 0) {
			adapter = new NavigationDrawerAdapter(getActivity(), arrayList);
			recyclerView.setAdapter(adapter);
		}
	}

	@Override
	public void onError(String error, int apiCall) {
		// TODO Auto-generated method stub
		volleyApiCall.makeJsonObjReq(API.GET_CATEGORIES, 0);
	}
}
