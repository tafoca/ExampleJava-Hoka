/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ej.annotation.Nullable;

/**
 * The user data object.
 */
public class UsersDao {
	private static final int ID_BOB = 1;
	private static final int ID_ANAS = 2;
	private static final int ID_ALGA = 3;
	private static List<User> users = new ArrayList<>();
	static {
		users.add(new User(ID_BOB, "bob")); //$NON-NLS-1$
		users.add(new User(ID_ANAS, "anas")); //$NON-NLS-1$
		users.add(new User(ID_ALGA, "alga")); //$NON-NLS-1$
	}

	private UsersDao() {
		// no-op.
	}

	/**
	 * Finds the user by id.
	 *
	 * @param id
	 *            the user id.
	 * @return the found user.
	 */
	public static @Nullable User findUserById(int id) {
		for (User user : users) {
			if (id == user.getId()) {
				return user;
			}
		}

		return null;
	}

	/**
	 * Gets a list of all users.
	 *
	 * @return the user list.
	 */
	public static List<User> getUsers() {
		return Collections.unmodifiableList(users);
	}

	/**
	 * Adds a user.
	 *
	 * @param user
	 *            the user to add.
	 */
	public static void add(User user) {
		users.add(user);
	}

	/**
	 * Removes a given user.
	 *
	 * @param user
	 *            the user to be removed.
	 */
	public static void remove(User user) {
		users.remove(user);
	}

	/**
	 * Updates the given user.
	 *
	 * @param user
	 *            the user to be updated.
	 */
	public static void updateUser(User user) {
		remove(user);
		add(user);
	}
}
