/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.endpoint;

import static ej.hoka.http.HttpServer.halt;

import org.json.me.JSONException;

import com.microej.example.hoka.model.User;
import com.microej.example.hoka.model.UsersDao;

import ej.annotation.Nullable;
import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.log.HokaLogger;

/**
 * Gets a user by id.
 */
public class GetUserById implements RequestHandler {

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		final User user;
		try {
			// populate response
			user = getUserById(request);
			assert (user != null);
			response.setData(user.toJsonObject().toString());
			response.addHeader(HttpConstants.HEADER_CONTENT_TYPE, "application/json"); //$NON-NLS-1$

		} catch (final JSONException e) {
			HokaLogger.instance.error(e);
			final String message = e.getMessage();
			if (message == null) {
				halt(HttpConstants.HTTP_STATUS_INTERNALERROR);
			} else {
				halt(HttpConstants.HTTP_STATUS_INTERNALERROR, message);
			}
		}
	}

	/**
	 * Returns a user, or halts the HTTP processing chain.
	 *
	 * @param request
	 *            the request with the user id.
	 * @return the found user.
	 */
	protected static @Nullable User getUserById(HttpRequest request) {
		User user = null;
		// Get and validate Query request parameters
		final String idParam = request.getPathParam("id"); //$NON-NLS-1$
		if (idParam == null || idParam.isEmpty()) {
			halt(HttpConstants.HTTP_STATUS_BADREQUEST, "id param is required"); //$NON-NLS-1$
			return user;
		}

		int id = Integer.parseInt(idParam);

		// Get user by id from database
		user = UsersDao.findUserById(id);
		if (user == null) {
			halt(HttpConstants.HTTP_STATUS_NOTFOUND, "user with id '" + id + "' was not found"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return user;
	}
}
