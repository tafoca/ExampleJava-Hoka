/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.endpoint;

import static ej.hoka.http.HttpServer.halt;

import java.io.IOException;
import java.util.Map;

import org.json.me.JSONException;

import com.microej.example.hoka.model.User;
import com.microej.example.hoka.model.UsersDao;

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.body.ParameterParser;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.log.HokaLogger;

/**
 * A HTTP handler to update the user.
 */
public class UpdateUser implements RequestHandler {
	private static final String EMPTY_NAME = ""; //$NON-NLS-1$
	private static final String KEY_NAME = "name"; //$NON-NLS-1$
	private static final String KEY_ID = "id"; //$NON-NLS-1$

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		try {
			Map<String, String> requestData = request.parseBody(new ParameterParser());
			if (!requestData.containsKey(KEY_ID)) {
				halt(HttpConstants.HTTP_STATUS_BADREQUEST, "id param is required"); //$NON-NLS-1$
				return;
			}

			if (!requestData.containsKey(KEY_NAME)) {
				halt(HttpConstants.HTTP_STATUS_BADREQUEST, "name param is required"); //$NON-NLS-1$
				return;
			}

			int id = Integer.parseInt(requestData.get(KEY_ID));
			String name = requestData.get(KEY_NAME);

			// check if user exists
			final User user = UsersDao.findUserById(id);
			if (user == null) {
				halt(HttpConstants.HTTP_STATUS_NOTFOUND, "user with id '" + id + "' was not found"); //$NON-NLS-1$//$NON-NLS-2$
				return;
			}

			// add user
			if (name == null) {
				name = EMPTY_NAME;
			}
			User newUser = new User(id, name);
			UsersDao.updateUser(newUser);

			// populate response
			response.setData(newUser.toJsonObject().toString());
			response.addHeader(HttpConstants.HEADER_CONTENT_TYPE, "application/json"); //$NON-NLS-1$

		} catch (final IOException | JSONException e) {
			HokaLogger.instance.error(e);
			final String message = e.getMessage();
			if (message == null) {
				halt(HttpConstants.HTTP_STATUS_INTERNALERROR);
			} else {
				halt(HttpConstants.HTTP_STATUS_INTERNALERROR, message);
			}
		}
	}
}
