package com.studentchef.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fonts {
    private AssetManager mngr;

    public Fonts(Context context) {
        mngr = context.getAssets();
    }
    private enum AssetTypefaces {
        RobotoLight,
        RobotoThin,
        RobotoCondensedBold,
        RobotoCondensedLight,
        RobotoCondensedRegular
    }

    private Typeface getTypeface(AssetTypefaces font) {
        Typeface tf = null;
        switch (font) {
            case RobotoLight:
                tf = Typeface.createFromAsset(mngr,"fonts/Quicksand-Light.ttf");
                break;
            case RobotoThin:
                tf = Typeface.createFromAsset(mngr,"fonts/Quicksand-Italic.ttf");
                break;
            case RobotoCondensedBold:
                tf = Typeface.createFromAsset(mngr,"fonts/Quicksand-Bold.ttf");
                break;
            case RobotoCondensedLight:
                tf = Typeface.createFromAsset(mngr,"fonts/Quicksand-LightItalic.ttf");
                break;
            case RobotoCondensedRegular:
                tf = Typeface.createFromAsset(mngr,"fonts/Quicksand-Regular.ttf");
                break;
            default:
                tf = Typeface.DEFAULT;
                break;
        }
        return tf;
    }
    public void setupLayoutTypefaces(View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setupLayoutTypefaces(child);
                }
            } else if (v instanceof TextView) {
                if (v.getTag().toString().equals(AssetTypefaces.RobotoLight.toString())){
                    ((TextView)v).setTypeface(getTypeface(AssetTypefaces.RobotoLight));
                }else if (v.getTag().toString().equals(AssetTypefaces.RobotoCondensedRegular.toString())) {
                    ((TextView)v).setTypeface(getTypeface(AssetTypefaces.RobotoCondensedRegular));
                }else if (v.getTag().toString().equals(AssetTypefaces.RobotoCondensedBold.toString())) {
                    ((TextView)v).setTypeface(getTypeface(AssetTypefaces.RobotoCondensedBold));
                }else if (v.getTag().toString().equals(AssetTypefaces.RobotoCondensedLight.toString())) {
                    ((TextView)v).setTypeface(getTypeface(AssetTypefaces.RobotoCondensedLight));
                }else if (v.getTag().toString().equals(AssetTypefaces.RobotoThin.toString())) {
                    ((TextView)v).setTypeface(getTypeface(AssetTypefaces.RobotoThin));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }
}
