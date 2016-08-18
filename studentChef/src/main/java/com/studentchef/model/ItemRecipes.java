package com.studentchef.model;

import java.io.Serializable;

public class ItemRecipes implements Serializable {
	String id, name, quantity, ingredients, method,time,avg_cost,dificulty_type;

	public String getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAvg_cost() {
		return avg_cost;
	}

	public void setAvg_cost(String avg_cost) {
		this.avg_cost = avg_cost;
	}

	public String getDificulty_type() {
		return dificulty_type;
	}

	public void setDificulty_type(String dificulty_type) {
		this.dificulty_type = dificulty_type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
