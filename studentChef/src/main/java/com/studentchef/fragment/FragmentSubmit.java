package com.studentchef.fragment;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.studentchef.R;
import com.studentchef.api.API;
import com.studentchef.apicall.VolleyApiCall;
import com.studentchef.apicall.VolleyApiCall.ApiListener;
import com.studentchef.model.ItemCategories;
import com.studentchef.parser.ParsingHelper;
import com.studentchef.utils.SavePref;
import com.studentchef.utils.YogiUtils;

public class FragmentSubmit extends Fragment implements ApiListener,
        OnClickListener {
    private static final String TAG = "FragmentRecipe";
    private View v;
    private YogiUtils yogiUtils;
    private SavePref savePref;
    VolleyApiCall volleyApiCall;
    EditText ed_fullname, ed_email, ed_recipetitle, ed_serves, ed_difficulty_level, ed_avg_cost;
    Spinner spinner, spinner_time;
    Button btn_submit;
    static ArrayList<ItemCategories> arrayListcat;
    static ArrayList<String> arrayListspine;
    String catid = "";
    private EditText ed_ingredients;
    private EditText ed_methods;
    CheckBox checkBox1, checkbox2;
    ProgressDialog pd;
    private ArrayList<String> arrMinute;
    private String time;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_submit, container, false);
        initObjects();
        initViews();
        return v;
    }

    private void initViews() {
        // TODO Auto-generated method stub
        ed_fullname = (EditText) v.findViewById(R.id.editText1);
        ed_email = (EditText) v.findViewById(R.id.editText2);
        ed_recipetitle = (EditText) v.findViewById(R.id.editText3);
        ed_serves = (EditText) v.findViewById(R.id.editText5);
        ed_ingredients = (EditText) v.findViewById(R.id.editText6);
        ed_methods = (EditText) v.findViewById(R.id.editText7);

        ed_difficulty_level = (EditText) v.findViewById(R.id.editText9);
        ed_avg_cost = (EditText) v.findViewById(R.id.editText10);
        spinner_time = (Spinner) v.findViewById(R.id.spinner2);
        btn_submit = (Button) v.findViewById(R.id.button1);
        btn_submit.setOnClickListener(this);
        spinner = (Spinner) v.findViewById(R.id.spinner1);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        checkbox2 = (CheckBox) v.findViewById(R.id.checkBox2);

        spinner.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, arrayListspine));
        spinner_time.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, arrMinute));
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position > 0)
                    try {
                        Log.e(TAG, " pos== "
                                + arrayListcat.get(position).getId());
                        catid = arrayListcat.get(position).getId();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                else
                    catid = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinner_time.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position > 0)
                    try {
                        Log.e(TAG, " pos== "
                                + arrayListcat.get(position).getId());
                        if(position==0){
                            time = String.valueOf(arrMinute.get(position).charAt(0));
                        }else{
                            time = String.valueOf(arrMinute.get(position).charAt(0))+String.valueOf(arrMinute.get(position).charAt(1));
                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                else
                    time = "1";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initObjects() {
        // TODO Auto-generated method stub
        arrayListspine = new ArrayList<>();
        arrMinute = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            arrMinute.add(i * 5 + " minute");
        }
        arrayListspine.add("Recipe Category( Fish, etc.)");
        savePref = new SavePref(getActivity());
        yogiUtils = new YogiUtils(getActivity());
        volleyApiCall = new VolleyApiCall(this);
        volleyApiCall.makeJsonObjReq(API.GET_CATEGORIES, 0);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
    }

    @Override
    public void onResponse(String jsonString, int apiCall) {
        // TODO Auto-generated method stub
        pd.dismiss();
        if (apiCall == 0) {
            arrayListcat = ParsingHelper.getCategories(jsonString);
            for (int i = 0; i < arrayListcat.size(); i++) {

                if (i > 0)
                    arrayListspine.add(arrayListcat.get(i).getName());

            }
            spinner.setAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, arrayListspine));
        } else {

            Log.e(TAG, jsonString);
            try {
                JSONObject object = new JSONObject(jsonString);
                JSONObject status = object.getJSONObject("status");
                String message = status.getString("message");
                String code = status.getString("code");
                if (code.equals("1")) {
                    ed_email.setText("");
                    ed_fullname.setText("");
                    ed_ingredients.setText("");
                    ed_methods.setText("");
                    ed_recipetitle.setText("");
                    ed_serves.setText("");
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
                        .show();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    @Override
    public void onError(String error, int apiCall) {
        // TODO Auto-generated method stub
        pd.dismiss();
        if (apiCall == 1) {
            Toast.makeText(getActivity(),
                    "Check your internet connection and submit again!",
                    Toast.LENGTH_SHORT).show();

        } else {
            volleyApiCall.makeJsonObjReq(API.GET_CATEGORIES, 0);
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        Log.e(TAG, " catid== " + catid);
        if (v == btn_submit) {
            if (ed_fullname.getText().toString().equals("")) {
                ed_fullname.setError("Please enter your fullname");
            } else if (ed_email.getText().toString().equals("")) {
                ed_email.setError("Please enter your email");
            } else if (ed_recipetitle.getText().toString().equals("")) {
                ed_recipetitle.setError("Please enter your recipe title");
            } else if (catid.equals("")) {
                Toast.makeText(getActivity(), "select your recipe category",
                        Toast.LENGTH_SHORT).show();
            } else if (ed_ingredients.getText().toString().equals("")) {
                ed_ingredients.setError("Please enter your recipe ingredients");
            } else if (ed_methods.getText().toString().equals("")) {
                ed_recipetitle.setError("Please enter your recipe methods");
            } else if (!checkBox1.isChecked()) {
                Toast.makeText(getActivity(),
                        "Please accept terms & condition", Toast.LENGTH_SHORT)
                        .show();
            } else if (!checkbox2.isChecked()) {
                Toast.makeText(getActivity(),
                        "Please accept terms & condition", Toast.LENGTH_SHORT)
                        .show();
            } else {
                pd.show();
                try {
                    volleyApiCall.makeJsonObjReq(
                            API.SUBMIT_RECIPES
                                    + URLEncoder.encode(ed_fullname.getText()
                                    .toString(), "UTF-8")
                                    + "&email="
                                    + URLEncoder.encode(ed_email.getText()
                                    .toString(), "UTF-8")
                                    + "&title="
                                    + URLEncoder.encode(ed_recipetitle
                                    .getText().toString(), "UTF-8")
                                    + "&cat_id="
                                    + URLEncoder.encode(catid, "UTF-8")
                                    + "&serves="
                                    + URLEncoder.encode(ed_serves.getText()
                                    .toString(), "UTF-8")
                                    + "&ingredients="
                                    + URLEncoder.encode(ed_ingredients
                                    .getText().toString(), "UTF-8")
                                    + "&method="
                                    + URLEncoder.encode(ed_methods.getText()
                                    .toString(), "UTF-8") + "&time=" + URLEncoder.encode(time, "UTF-8") + "&dificulty_level=" + URLEncoder.encode(ed_difficulty_level.getText().toString(), "UTF-8") + "&avg_cost=" + URLEncoder.encode(ed_avg_cost.getText().toString(), "UTF-8"), 1);

                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e(TAG, " exception=== " + e.getMessage());
                }
            }
        }
    }
}
