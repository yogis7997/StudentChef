package com.studentchef.holder;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.nineoldandroids.view.ViewHelper;
import com.studentchef.R;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HolderRecipe extends RecyclerView.ViewHolder {

    public TextView tv_name, tv_desc, tv_time, tv_difficult, tv_cost;
    public SwipeLayout sample1, sample2, sample3;
    public View itemView;
    public ImageView img_minutes, img_minutes_grey;
    public LinearLayout ll;

    public HolderRecipe(View v) {
        super(v);
        // TODO Auto-generated constructor stub
        itemView = v;
        tv_name = (TextView) v.findViewById(R.id.textView1);
        tv_desc = (TextView) v.findViewById(R.id.textView2);
        tv_cost = (TextView) v.findViewById(R.id.tv_cost);
        tv_difficult = (TextView) v.findViewById(R.id.tv_difficulty_level);
        tv_time = (TextView) v.findViewById(R.id.tv_minutes);
        img_minutes = (ImageView) v.findViewById(R.id.free_minutes);
        img_minutes_grey = (ImageView) v.findViewById(R.id.free_minutes_grey);
        ll = (LinearLayout) v.findViewById(R.id.ll);
        sample1 = (SwipeLayout) v.findViewById(R.id.sample1);
        sample1.setShowMode(SwipeLayout.ShowMode.PullOut);
        sample1.addDrag(SwipeLayout.DragEdge.Left, sample1.findViewById(R.id.bottom_wrapper));
        sample1.setRightSwipeEnabled(false);


//		sample1.addRevealListener(R.id.delete, new SwipeLayout.OnRevealListener() {
//			@Override
//			public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
//
//			}
//		});

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
//		sample1.findViewById(R.id.star2).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				Toast.makeText(MyActivity.this, "Star", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		sample1.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				Toast.makeText(MyActivity.this, "Trash Bin", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		sample1.findViewById(R.id.magnifier2).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				Toast.makeText(MyActivity.this, "Magnifier", Toast.LENGTH_SHORT).show();
//			}
//		});

//		sample1.addRevealListener(R.id.starbott, new SwipeLayout.OnRevealListener() {
//			@Override
//			public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
//				View star = child.findViewById(R.id.star);
//				float d = child.getHeight() / 2 - star.getHeight() / 2;
//				ViewHelper.setTranslationY(star, d * fraction);
//				ViewHelper.setScaleX(star, fraction + 0.6f);
//				ViewHelper.setScaleY(star, fraction + 0.6f);
//			}
//		});
    }

}
