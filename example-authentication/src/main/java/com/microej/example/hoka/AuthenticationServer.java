/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import static ej.hoka.http.HttpConstants.HTTP_STATUS_REDIRECT;
import static ej.hoka.http.HttpServer.halt;

import java.io.IOException;
import java.security.SecureRandom;

import com.microej.example.hoka.connectivity.ManageableServer;
import com.microej.example.hoka.page.AdminPage;
import com.microej.example.hoka.page.LoginController;
import com.microej.example.hoka.page.LoginPage;
import com.microej.example.hoka.page.LogoutPage;
import com.microej.example.hoka.page.UserPage;
import com.microej.example.hoka.page.filter.AuthenticationFilter;

import ej.authz.acl.AccessControlList;
import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.HttpServer;
import ej.hoka.http.requesthandler.ClasspathFilesHandler;
import ej.hoka.session.SessionHandler;

/**
 * Provides a definition for a server which manage an authentication example.
 */
public class AuthenticationServer extends ManageableServer {
	/**
	 * Session id to be added to a cookie.
	 */
	public static final String SESSION_ID = "jsession"; //$NON-NLS-1$

	private static final String PAGE_LOGIN = "login"; //$NON-NLS-1$
	private static final String PAGE_LOGOUT = "logout"; //$NON-NLS-1$
	private static final String PAGE_USER_PROFILE = "user-profile"; //$NON-NLS-1$
	private static final String PAGE_ADMINISTRATION = "administration"; //$NON-NLS-1$
	private static final String RESOURCE_PAGE_LOGOUT = "/logout"; //$NON-NLS-1$
	private static final String RESOURCE_PAGE_USER_PROFILE = "/user-profile"; //$NON-NLS-1$
	private static final String RESOURCE_PAGE_ADMINISTRATION = "/administration"; //$NON-NLS-1$
	private static final String PROFILE_USER = "user"; //$NON-NLS-1$
	private static final String PROFILE_ADMIN = "admin"; //$NON-NLS-1$
	private static final String CACHE_CONTROL = "Cache-Control"; //$NON-NLS-1$
	private static final String CACHE_NOCACHE_NOSTORE = "no-cache, no store"; //$NON-NLS-1$
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private final HttpServer http;

	/**
	 * Constructor. Creates the authentication server.
	 */
	public AuthenticationServer() {
		// This part adds the access right for each profile.
		// The admin profile can access all pages.
		// The user profile can only access the user-profile and the logout pages.
		final AccessControlList acl = new AccessControlList();
		acl.addPermission(PROFILE_ADMIN, RESOURCE_PAGE_ADMINISTRATION, HttpConstants.HTTP_METHOD_GET);
		acl.addPermission(PROFILE_ADMIN, RESOURCE_PAGE_USER_PROFILE, HttpConstants.HTTP_METHOD_GET);
		acl.addPermission(PROFILE_ADMIN, RESOURCE_PAGE_LOGOUT, HttpConstants.HTTP_METHOD_GET);

		acl.addPermission(PROFILE_USER, RESOURCE_PAGE_USER_PROFILE, HttpConstants.HTTP_METHOD_GET);
		acl.addPermission(PROFILE_USER, RESOURCE_PAGE_LOGOUT, HttpConstants.HTTP_METHOD_GET);

		this.http = HttpServer.builder() //
				.port(DEFAULT_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1) //
				.staticFilesHandler(ClasspathFilesHandler.builder().rootDirectory("/public").build()) // //$NON-NLS-1$
				.build();

		// Create the session handler to manage users sessions
		final SessionHandler sessionHandler = new SessionHandler(new SecureRandom());

		// Request filter to control authentication and access authorization
		final AuthenticationFilter aF = new AuthenticationFilter(sessionHandler, acl);
		// List all pages to apply the authentication filter on.
		this.http.before(PAGE_ADMINISTRATION, aF);
		this.http.before(PAGE_USER_PROFILE, aF);
		this.http.before(PAGE_LOGOUT, aF);

		// Private pages
		this.http.get(PAGE_ADMINISTRATION, new AdminPage());
		this.http.get(PAGE_USER_PROFILE, new UserPage());
		this.http.get(PAGE_LOGOUT, new LogoutPage(sessionHandler));

		// Public pages
		this.http.post(PAGE_LOGIN, new LoginController(sessionHandler));
		this.http.get("/*", new LoginPage()); // Login is mapped to everything else //$NON-NLS-1$
	}

	/**
	 * Redirects the client to requested page.
	 *
	 * @param request
	 *            the original request.
	 * @param response
	 *            the response to fill.
	 * @param page
	 *            the page to redirect.
	 */
	public static void redirect(final HttpRequest request, final HttpResponse response, final String page) {
		String host = request.getHeader("host"); //$NON-NLS-1$

		// Set location and return with redirect code immediately
		response.addHeader("Location", "http://" + host + "/" + page); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		response.addHeader(CACHE_CONTROL, CACHE_NOCACHE_NOSTORE);
		if (request.getHeaders().containsKey("upgrade-insecure-requests")) { //$NON-NLS-1$
			// A Vary header can be used so that the site isn't served by caches to clients
			// that don’t support the upgrade mechanism.
			response.addHeader("Vary", "Upgrade-Insecure-Requests"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		halt(HTTP_STATUS_REDIRECT);

	}

	/**
	 * Gives a translation in readable string from HttpRequest method value.
	 *
	 * @param httpMethod
	 *            the HTTP method as an Integer.
	 * @return The HTTP method as string.
	 */
	public static String getHttpMethodAsString(int httpMethod) {
		switch (httpMethod) {
		case HttpRequest.GET:
			return HttpConstants.HTTP_METHOD_GET;
		case HttpRequest.POST:
			return HttpConstants.HTTP_METHOD_POST;
		case HttpRequest.PUT:
			return HttpConstants.HTTP_METHOD_PUT;
		case HttpRequest.DELETE:
			return HttpConstants.HTTP_METHOD_DELETE;
		default:
			throw new IllegalArgumentException("Http method not supported"); //$NON-NLS-1$
		}
	}

	/**
	 * Starts the server.
	 *
	 * @throws IOException
	 *             Thrown when the server could not be started.
	 */
	@Override
	public void start() throws IOException {
		this.http.start();
	}

	/**
	 * Stops the server.
	 */
	@Override
	public void stop() {
		this.http.stop();
	}
}
