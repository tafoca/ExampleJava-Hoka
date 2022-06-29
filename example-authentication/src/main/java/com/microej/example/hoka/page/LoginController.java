/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.page;

import static ej.hoka.http.HttpConstants.HTTP_STATUS_UNAUTHORIZED;
import static ej.hoka.http.HttpServer.halt;

import java.io.IOException;
import java.util.Map;

import com.microej.example.hoka.AuthenticationServer;

import ej.hoka.http.Cookie;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.body.ParameterParser;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.log.HokaLogger;
import ej.hoka.session.Session;
import ej.hoka.session.SessionHandler;

/**
 * Provides the controller to manage the login.
 */
public class LoginController implements RequestHandler {

	private static final int MAX_AGE_COOKIE_SECONDS = 900;
	private static final String USERNAME_USER = "user"; //$NON-NLS-1$
	private static final String USERNAME_ADMIN = "admin"; //$NON-NLS-1$
	private static final String PASSWORD_1234 = "1234"; //$NON-NLS-1$
	private static final String PARAM_PASSWORD = "password"; //$NON-NLS-1$
	private static final String PARAM_USERNAME = "username"; //$NON-NLS-1$
	private final SessionHandler sessionHandler;

	/**
	 * Constructor.
	 *
	 * @param sessionAuthenticator
	 *            the session authenticator.
	 */
	public LoginController(SessionHandler sessionAuthenticator) {
		this.sessionHandler = sessionAuthenticator;
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		// The userName is located in the parameters of the request.

		String userName = null;
		String password = null;
		Map<String, String> params = null;
		try {
			params = request.parseBody(new ParameterParser());
			userName = params.get(PARAM_USERNAME);
			password = params.get(PARAM_PASSWORD);
		} catch (IOException e) {
			HokaLogger.instance.error(e);
			halt(HTTP_STATUS_UNAUTHORIZED);
			return;
		}

		// We accept only admin and user as username
		if (userName == null || (!USERNAME_ADMIN.equals(userName) && !USERNAME_USER.equals(userName))) {
			halt(HTTP_STATUS_UNAUTHORIZED);
			return;
		}

		// Password must be 1234
		if (password == null || password.isEmpty() || !password.equals(PASSWORD_1234)) {
			halt(HTTP_STATUS_UNAUTHORIZED);
			return;
		}

		// Authenticate the user.
		// if the user is valid create a new session.
		final Session session = this.sessionHandler.newSession();
		session.setAttribute(PARAM_USERNAME, userName);
		Cookie cookie = Cookie.builder() // cookie will be not secured
				.name(AuthenticationServer.SESSION_ID) //
				.value(session.getId()) //
				.maxAge(MAX_AGE_COOKIE_SECONDS) //
				.httpOnly() //
				.build();
		response.addCookie(cookie);

		// Redirect to the correct page based on username
		String page;
		switch (userName) {
		case USERNAME_ADMIN:
			page = "administration"; //$NON-NLS-1$
			break;
		case USERNAME_USER:
			page = "user-profile"; //$NON-NLS-1$
			break;
		default:
			// In theory this controller never execute this instruction,
			// an Unauthorized Status is occurred before this call
			page = "login"; //$NON-NLS-1$
			break;
		}

		AuthenticationServer.redirect(request, response, page);

	}

}
