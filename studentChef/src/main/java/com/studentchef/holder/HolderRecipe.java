package com.studentchef.holder;

import com.daimajia.swipe.SwipeLayout;
import com.nineoldandroids.view.ViewHelper;
import com.studentchef.R;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HolderRecipe extends RecyclerView.ViewHolder {

	public TextView tv_name, tv_desc;
	private SwipeLayout sample1, sample2, sample3;
	public HolderRecipe(View v) {
		super(v);
		// TODO Auto-generated constructor stub
		tv_name = (TextView) v.findViewById(R.id.textView1);
		tv_desc = (TextView) v.findViewById(R.id.textView2);
		sample1 = (SwipeLayout) v.findViewById(R.id.sample1);
		sample1.setShowMode(SwipeLayout.ShowMode.PullOut);
		View starBottView = sample1.findViewById(R.id.starbott);
		sample1.addDrag(SwipeLayout.DragEdge.Left, sample1.findViewById(R.id.bottom_wrapper));
		sample1.addDrag(SwipeLayout.DragEdge.Right, sample1.findViewById(R.id.bottom_wrapper_2));
		sample1.addDrag(SwipeLayout.DragEdge.Top, starBottView);
		sample1.addDrag(SwipeLayout.DragEdge.Bottom, starBottView);
		sample1.addRevealListener(R.id.delete, new SwipeLayout.OnRevealListener() {
			@Override
			public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {

			}
		});

		sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(MyActivity.this, "Click on surface", Toast.LENGTH_SHORT).show();
//				Log.d(MyActivity.class.getName(), "click on surface");
			}
		});
		sample1.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
//				Toast.makeText(MyActivity.this, "longClick on surface", Toast.LENGTH_SHORT).show();
//				Log.d(MyActivity.class.getName(), "longClick on surface");
				return true;
			}
		});
		sample1.findViewById(R.id.star2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(MyActivity.this, "Star", Toast.LENGTH_SHORT).show();
			}
		});

		sample1.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(MyActivity.this, "Trash Bin", Toast.LENGTH_SHORT).show();
			}
		});

		sample1.findViewById(R.id.magnifier2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(MyActivity.this, "Magnifier", Toast.LENGTH_SHORT).show();
			}
		});

		sample1.addRevealListener(R.id.starbott, new SwipeLayout.OnRevealListener() {
			@Override
			public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
				View star = child.findViewById(R.id.star);
				float d = child.getHeight() / 2 - star.getHeight() / 2;
				ViewHelper.setTranslationY(star, d * fraction);
				ViewHelper.setScaleX(star, fraction + 0.6f);
				ViewHelper.setScaleY(star, fraction + 0.6f);
			}
		});
	}

}
