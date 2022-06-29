/*
 * Java
 *
 * Copyright 2019-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.page;

import com.microej.example.hoka.AuthenticationServer;

import ej.hoka.http.Cookie;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.log.HokaLogger;
import ej.hoka.session.Session;
import ej.hoka.session.SessionHandler;

/**
 * Provides a handler for the get logout request.
 */
public class LogoutPage implements RequestHandler {

	private static final String LOGIN_PAGE = "login";
	private final SessionHandler sessionHandler;

	public LogoutPage(SessionHandler sessionAuthenticator) {
		this.sessionHandler = sessionAuthenticator;
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		HokaLogger.instance.info("LogoutPage.process()");
		final String sessionId = request.getCookie(AuthenticationServer.SESSION_ID);
		if (sessionId == null || sessionId.isEmpty() || sessionId.trim().isEmpty()) {
			AuthenticationServer.redirect(request, response, LOGIN_PAGE);
			return;
		}

		final Session session = sessionHandler.getSession(sessionId);
		if (session == null) {
			// no known session with this id.
			// session never existed or has expired.
			AuthenticationServer.redirect(request, response, LOGIN_PAGE);
			return;
		}

		// Remove the server session
		sessionHandler.removeSession(sessionId);
		// set cookie with max-age Zero
		// to notify the browsed that the cookie needs to be removed
		Cookie cookie = Cookie.builder() // cookie will be not secured
				.name(AuthenticationServer.SESSION_ID) //
				.value(session.getId()) //
				.maxAge(-1) //
				.httpOnly() //
				.build();
		response.addCookie(cookie);

		AuthenticationServer.redirect(request, response, LOGIN_PAGE);
	}

}
