package com.studentchef.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.studentchef.R;
import com.studentchef.holder.HolderRecipe;
import com.studentchef.model.ItemRecipes;

public class AdapterRecipe extends RecyclerSwipeAdapter<HolderRecipe> {

    Activity activity;
    ArrayList<ItemRecipes> arrayList;
    private int arr[] = {R.drawable.five_minutes_clock, R.drawable.ten_minutes_clock, R.drawable.fifteen_minutes_clock, R.drawable.twenty_minutes_clock, R.drawable.twenty_five_minutes_clock, R.drawable.thirty_minutes_clock, R.drawable.thirty_five_minutes_clock, R.drawable.fourty_minutes_clock, R.drawable.fourty_five_minutes_clock, R.drawable.fifty_minutes_clock, R.drawable.fifty_five_minutes_clock, R.drawable.sixty_minutes_clock};
    private int arr1[] = {R.drawable.five_minutes_clock_grey, R.drawable.ten_minutes_clock_grey, R.drawable.fifteen_minutes_clock_grey, R.drawable.twenty_minutes_clock_grey, R.drawable.twenty_five_minutes_clock_grey, R.drawable.thirty_minutes_clock_grey, R.drawable.thirty_five_minutes_clock_grey, R.drawable.fourty_minutes_clock_grey, R.drawable.fourty_five_minutes_clock_grey, R.drawable.fifty_minutes_clock_grey, R.drawable.fifty_five_minutes_clock_grey, R.drawable.sixty_minutes_clock_grey};
    private ArrayList<HashMap<Integer, Boolean>> arrayListBool;

    public AdapterRecipe(Activity activity, ArrayList<ItemRecipes> arrayListCat) {
        // TODO Auto-generated constructor stub
        arrayList = arrayListCat;
        this.activity = activity;
        arrayListBool = new ArrayList<>();
        for (int i = 0; i < arrayListBool.size(); i++) {
            HashMap<Integer, Boolean> hashMap = new HashMap<>();
            hashMap.put(i, false);
            arrayListBool.add(hashMap);
        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(final HolderRecipe holderRecipe, final int position) {
        // TODO Auto-generated method stub
        holderRecipe.tv_name.setText(arrayList.get(position).getName());
        holderRecipe.tv_desc.setText(arrayList.get(position).getQuantity());
//        if (!arrayList.get(position).getTime().equals(""))
        holderRecipe.tv_time.setText(arrayList.get(position).getTime() + " Minutes");
        holderRecipe.tv_difficult.setText(arrayList.get(position).getDificulty_type());
        holderRecipe.tv_cost.setText(arrayList.get(position).getAvg_cost());
        if (!arrayList.get(position).getTime().equals("")) {
            holderRecipe.img_minutes.setImageResource(arr[Integer.parseInt(arrayList.get(position).getTime()) / 5]);
            holderRecipe.img_minutes_grey.setImageResource(arr1[Integer.parseInt(arrayList.get(position).getTime()) / 5]);
        } else {
            holderRecipe.img_minutes.setImageResource(arr[15 / 5]);
            holderRecipe.img_minutes_grey.setImageResource(arr1[10 / 5]);
        }


        holderRecipe.sample1.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                holderRecipe.ll.setVisibility(View.GONE);
//                arrayListBool.get(position).put(position,false);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
                holderRecipe.ll.setVisibility(View.VISIBLE);
//                arrayListBool.get(position).put(position,true);
            }
        });
        mItemManger.bind(holderRecipe.itemView, position);
    }

    @Override
    public HolderRecipe onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        // TODO Auto-generated method stub

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.sample1, null);
        HolderRecipe mh = new HolderRecipe(v);

        return mh;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sample1;
    }
}
