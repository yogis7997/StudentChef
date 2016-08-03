package com.studentchef.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentchef.R;
import com.studentchef.holder.HolderContact;
import com.studentchef.model.ItemContacts;
import com.squareup.picasso.Picasso;

public class AdapterContact extends RecyclerView.Adapter<HolderContact> {
	ArrayList<ItemContacts> arrayList;
	Activity activity;

	public AdapterContact(Activity activity,
			ArrayList<ItemContacts> arrayListContacts) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		arrayList = arrayListContacts;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return (null != arrayList ? arrayList.size() : 0);
	}

	@Override
	public void onBindViewHolder(HolderContact holder, int position) {
		// TODO Auto-generated method stub
		holder.tv_name.setText(arrayList.get(position).getName());
		holder.tv_status.setText("");
		holder.img_profile.setBorderWidth(0);

		try {
//			Picasso.with(activity).load(arrayList.get(position).getPic())
//					.placeholder(R.drawable.profile).error(R.drawable.profile)
//					.into(holder.img_profile);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public HolderContact onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.adapter_contact, null);
		HolderContact mh = new HolderContact(v);

		return mh;
	}

}
