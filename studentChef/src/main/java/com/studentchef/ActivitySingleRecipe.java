package com.studentchef;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studentchef.api.API;
import com.studentchef.apicall.VolleyApiCall;
import com.studentchef.model.ItemRecipes;
import com.studentchef.utils.SavePref;
import com.studentchef.utils.YogiUtils;

public class ActivitySingleRecipe extends AppCompatActivity {
	private static final String TAG = null;
	YogiUtils yogiUtils;
	SavePref savePref;
	private Toolbar toolbar;
	TextView tv_ingredients, tv_methods, tv_name;
	ItemRecipes itemRecipes;
	private TextView title;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singlerecipe);
		itemRecipes = (ItemRecipes) getIntent().getSerializableExtra("list");
		initObjects();
		initToolbar();
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_ingredients = (TextView) findViewById(R.id.tv_ingredients);
		tv_methods = (TextView) findViewById(R.id.tv_method);
		tv_name.setText(itemRecipes.getName());
		tv_ingredients.setText(itemRecipes.getIngredients());
		tv_methods.setText(itemRecipes.getMethod());
	}

	@SuppressWarnings("deprecation")
	private void initToolbar() {
		// TODO Auto-generated method stub
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		title = (TextView) toolbar.findViewById(R.id.title);
		ImageView img_title = (ImageView) toolbar.findViewById(R.id.imageView1);
		img_title.setVisibility(View.VISIBLE);
		title.setVisibility(View.GONE);
		title.setText(savePref.getFname());
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("RobotoLight");
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		final Drawable upArrow = getResources().getDrawable(
				R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(getResources().getColor(R.color.toggle_color),
				PorterDuff.Mode.SRC_ATOP);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);
	}

	private void initObjects() {
		// TODO Auto-generated method stub
		yogiUtils = new YogiUtils(this);
		yogiUtils.setStatusBarColor(findViewById(R.id.status_bar),
				getResources().getColor(R.color.status_color));

		savePref = new SavePref(this);

	}

	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
	}
}
