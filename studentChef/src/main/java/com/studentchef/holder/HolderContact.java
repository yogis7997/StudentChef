package com.studentchef.holder;

import com.studentchef.R;
import com.studentchef.utils.CircularImageView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HolderContact extends RecyclerView.ViewHolder {

	public CircularImageView img_profile;
	public TextView tv_name, tv_status;

	public HolderContact(View arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		img_profile = (CircularImageView) arg0.findViewById(R.id.img_profile);
		tv_name = (TextView) arg0.findViewById(R.id.tv_name);
		tv_status = (TextView) arg0.findViewById(R.id.tv_status);
	}

}
