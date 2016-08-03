package com.studentchef.api;

public class API {

	public static final String BASE_URL = "http://www.studentchef.co.uk/api/";

	public static final String GET_CATEGORIES = BASE_URL
			+ "getDishesCategories.php?";

	public static final String LOG_IN = BASE_URL + "user=1&action=login&email=";

	public static final String SIGN_UP = BASE_URL + "send_otp=1&contact=";

	public static final String OTP_CHECK = BASE_URL + "check_otp=1&contact=";

	public static final String GET_RECIPES = BASE_URL
			+ "getRecipes.php?cat_id=";
	public static final String SUBMIT_RECIPES = BASE_URL
			+ "submitRecipe.php?name=";

	public static final String CNTACT_US = BASE_URL + "contactUs.php?name=x";

}
