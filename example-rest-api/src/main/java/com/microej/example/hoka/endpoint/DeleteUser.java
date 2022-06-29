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

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.log.HokaLogger;

/**
 * A HTTP handler which deletes a user.
 */
public class DeleteUser implements RequestHandler {

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		final User user = GetUserById.getUserById(request);
		if (user == null) {
			return;
		}

		// remove the user
		UsersDao.remove(user);

		// populate response
		try {
			response.setData(user.toJsonObject().toString());
			response.addHeader(HttpConstants.HEADER_CONTENT_TYPE, "application/json"); //$NON-NLS-1$
		} catch (JSONException e) {
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