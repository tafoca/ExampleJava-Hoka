/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.page.filter;

import com.microej.example.hoka.AuthenticationServer;

import ej.authz.acl.AccessControlList;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.session.Session;
import ej.hoka.session.SessionHandler;

/**
 * Authentication filter can be registered to protected any resource using the HTTPServer#before() method to intercept
 * any request.
 */
public class AuthenticationFilter implements RequestHandler {

	private static final String ATTRIBUTE_USERNAME = "username"; //$NON-NLS-1$
	private static final String PAGE_LOGIN = "login"; //$NON-NLS-1$
	private final SessionHandler sessionHandler;
	private final AccessControlList acl;

	/**
	 * Constructor.
	 *
	 * @param sessionAuthenticator
	 *            the session authenticator.
	 * @param acl
	 *            the access control list.
	 */
	public AuthenticationFilter(SessionHandler sessionAuthenticator, AccessControlList acl) {
		this.sessionHandler = sessionAuthenticator;
		this.acl = acl;
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		// Authentication: check that a valid session token is set
		String sessionId = request.getCookie(AuthenticationServer.SESSION_ID);
		if (sessionId == null || sessionId.isEmpty()) {
			AuthenticationServer.redirect(request, response, PAGE_LOGIN);
			return;
		}
		Session session = this.sessionHandler.getSession(sessionId);
		if (session == null) {
			AuthenticationServer.redirect(request, response, PAGE_LOGIN);
			return;
		}
		String username = session.getAttribute(ATTRIBUTE_USERNAME);
		if (username == null || username.isEmpty()) {
			AuthenticationServer.redirect(request, response, PAGE_LOGIN);
			return;
		}

		// Authorization - check for access authorization
		if (!this.acl.isAuthorized(username, AuthenticationServer.getHttpMethodAsString(request.getMethod()),
				request.getURI())) {
			AuthenticationServer.redirect(request, response, PAGE_LOGIN);
			return;
		}
		request.setAttribute(ATTRIBUTE_USERNAME, username);
	}
}
