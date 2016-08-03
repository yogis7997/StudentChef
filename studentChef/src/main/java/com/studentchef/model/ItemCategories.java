package com.studentchef.model;

public class ItemCategories {

	String id, name, icon, image, description;
	int drawableicon;

	public String getId() {
		return id;
	}

	public int getDrawableicon() {
		return drawableicon;
	}

	public void setDrawableicon(int drawableicon) {
		this.drawableicon = drawableicon;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
