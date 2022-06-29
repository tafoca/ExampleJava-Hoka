/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.model;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import ej.annotation.Nullable;

/**
 * The user model.
 */
public class User {
	private static final String JSON_NAME = "name"; //$NON-NLS-1$
	private static final String JSON_ID = "id"; //$NON-NLS-1$
	private final int id;
	private final String name;

	/**
	 * User constructor.
	 *
	 * @param id
	 *            the user id.
	 * @param name
	 *            the user name.
	 */
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Transforms current object to JSON.
	 *
	 * @return the JSON object.
	 * @throws JSONException
	 *             when the transformation fails.
	 */
	public JSONObject toJsonObject() throws JSONException {
		final JSONObject o = new JSONObject();
		o.put(JSON_ID, this.id);
		o.put(JSON_NAME, this.name);
		return o;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.id;
		return result;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

}
