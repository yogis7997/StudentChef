package com.studentchef.fragmentdrawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studentchef.R;
import com.studentchef.model.ItemCategories;

/**
 * Created by Ravi Tamada on 12-03-2015.
 */
public class NavigationDrawerAdapter extends
		RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
	List<NavDrawerItem> data = Collections.emptyList();
	private LayoutInflater inflater;
	private Context context;
	ArrayList<ItemCategories> resArr;

	int arr[] = { R.drawable.ic_home, R.drawable.ic_chiken, R.drawable.ic_lamb,
			R.drawable.ic_beef };

	public NavigationDrawerAdapter(Context context,
			ArrayList<ItemCategories> arrayList) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		resArr = arrayList;
	}

	public void delete(int position) {
		// data.remove(position);
		// notifyItemRemoved(position);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		ItemCategories itemCategories = resArr.get(position);
		holder.title.setText(itemCategories.getName());

		if (position > 0 && position < resArr.size() - 4) {
			try {
				Picasso.with(context).load(itemCategories.getImage())
						.resize(50, 50).placeholder(R.drawable.ic_launcher)
						.error(R.drawable.ic_launcher).into(holder.img_icon);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}
		} else {
			holder.img_icon.setImageResource(resArr.get(position)
					.getDrawableicon());
		}

	}

	@Override
	public int getItemCount() {
		return resArr.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		ImageView img_icon;

		public MyViewHolder(View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.title);
			img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
		}
	}
}
