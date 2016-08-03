package com.studentchef.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.studentchef.R;
import com.studentchef.model.ItemCategories;
import com.studentchef.model.ItemContacts;
import com.studentchef.model.ItemRecipes;

public class ParsingHelper {

	public static ArrayList<ItemContacts> getContacts(String result) {
		// TODO Auto-generated method stub
		ArrayList<ItemContacts> arrayList = new ArrayList<>();
		try {
			JSONObject object = new JSONObject(result);
			JSONArray contactArray = object.getJSONArray(Constants._CONTACT);
			for (int i = 0; i < contactArray.length(); i++) {
				JSONObject conObject = contactArray.getJSONObject(i);
				ItemContacts itemContacts = new ItemContacts();
				itemContacts.setId(conObject.getString(Constants._ID));
				itemContacts
						.setContact(conObject.getString(Constants._CONTACT));
				itemContacts.setName(conObject.getString(Constants._NAME));
				itemContacts.setPic(conObject.getString(Constants._PIC));
				arrayList.add(itemContacts);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return arrayList;

	}

	public static ArrayList<ItemCategories> getCategories(String jsonString) {
		// TODO Auto-generated method stub
		ArrayList<ItemCategories> arrayList = new ArrayList<>();
		ItemCategories itemCategories0 = new ItemCategories();
		itemCategories0.setDrawableicon(R.drawable.ic_home);
		itemCategories0.setName("Home");
		arrayList.add(itemCategories0);
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject statusObject = object.getJSONObject("status");
			String code = statusObject.getString("code");
			String message = statusObject.getString("message");
			if (code.equals("1")) {

				JSONArray bodyArray = object.getJSONArray("body");
				for (int i = 0; i < bodyArray.length(); i++) {
					JSONObject bodyObject = bodyArray.getJSONObject(i);
					ItemCategories itemCategories = new ItemCategories();
					itemCategories.setDescription(bodyObject
							.getString(Constants._DESCRIPTION));
					itemCategories.setIcon(bodyObject
							.getString(Constants._ICON));
					itemCategories.setId(bodyObject.getString(Constants._ID));
					itemCategories.setImage(bodyObject
							.getString(Constants._IMAGE));
					itemCategories.setName(bodyObject
							.getString(Constants._NAME));
					arrayList.add(itemCategories);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return arrayList;
	}

	public static ArrayList<ItemRecipes> getRecipes(String jsonString) {
		// TODO Auto-generated method stub
		ArrayList<ItemRecipes> arrayList = new ArrayList<>();

		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject statusObject = object.getJSONObject("status");
			String code = statusObject.getString("code");
			String message = statusObject.getString("message");
			if (code.equals("1")) {

				JSONArray bodyArray = object.getJSONArray("body");
				for (int i = 0; i < bodyArray.length(); i++) {
					JSONObject bodyObject = bodyArray.getJSONObject(i);
					ItemRecipes itemCategories = new ItemRecipes();
					itemCategories.setIngredients(bodyObject
							.getString(Constants._INGREDIENTS));
					itemCategories.setMethod(bodyObject
							.getString(Constants._METHODS));
					itemCategories.setId(bodyObject.getString(Constants._ID));
					itemCategories.setQuantity(bodyObject
							.getString(Constants._QUANTITY));
					itemCategories.setName(bodyObject
							.getString(Constants._NAME));
					arrayList.add(itemCategories);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return arrayList;
	}
}
