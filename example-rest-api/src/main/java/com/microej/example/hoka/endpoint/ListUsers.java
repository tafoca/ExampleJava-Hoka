/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.endpoint;

import static ej.hoka.http.HttpServer.halt;

import org.json.me.JSONArray;
import org.json.me.JSONException;

import com.microej.example.hoka.model.User;
import com.microej.example.hoka.model.UsersDao;

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.log.HokaLogger;

/**
 * A handler to process a request for a list of users.
 */
public class ListUsers implements RequestHandler {

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		try {
			final JSONArray jArray = new JSONArray();
			for (User user : UsersDao.getUsers()) {
				jArray.put(user.toJsonObject());
			}

			// populate response
			response.setData(jArray.toString());
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
}
