package com.studentchef.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentchef.R;
import com.studentchef.holder.HolderRecipe;
import com.studentchef.model.ItemRecipes;

public class AdapterRecipe extends RecyclerView.Adapter<HolderRecipe> {

	Activity activity;
	ArrayList<ItemRecipes> arrayList;

	public AdapterRecipe(Activity activity, ArrayList<ItemRecipes> arrayListCat) {
		// TODO Auto-generated constructor stub
		arrayList = arrayListCat;
		this.activity = activity;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public void onBindViewHolder(HolderRecipe holderRecipe, int position) {
		// TODO Auto-generated method stub
		holderRecipe.tv_name.setText(arrayList.get(position).getName());
		holderRecipe.tv_desc.setText(arrayList.get(position).getQuantity());

	}

	@Override
	public HolderRecipe onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub

		View v = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.adapter_recipe, null);
		HolderRecipe mh = new HolderRecipe(v);

		return mh;
	}

}
