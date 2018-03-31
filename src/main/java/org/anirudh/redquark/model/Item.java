package org.anirudh.redquark.model;

import org.apache.solr.client.solrj.beans.Field;

public class Item {

	@Field("id")
	String id;

	@Field("name")
	 String name;

	@Field
	Float price;

	/**
	 * Empty constructor is required
	 */
	public Item() {
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param id
	 * @param name
	 * @param price
	 */
	public Item(String id, String name, Float price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

}
