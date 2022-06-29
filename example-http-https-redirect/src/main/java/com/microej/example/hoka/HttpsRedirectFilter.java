/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import static ej.hoka.http.HttpConstants.HTTP_STATUS_REDIRECT;
import static ej.hoka.http.HttpServer.halt;

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;

/**
 * This class provides a redirection of HTTP to HTTPS.
 */
public class HttpsRedirectFilter implements RequestHandler {

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		String host = request.getHeader("host"); //$NON-NLS-1$

		// set location and return with redirect code immediately
		response.addHeader("Location", "https://" + host + request.getURI()); //$NON-NLS-1$ //$NON-NLS-2$
		if (request.getHeaders().containsKey("upgrade-insecure-requests")) { //$NON-NLS-1$
			// A Vary header can be used so that the site isn't served by caches to clients
			// that don’t support the upgrade mechanism.
			response.addHeader("Vary", "Upgrade-Insecure-Requests"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (request.getMethod() == HttpRequest.GET) {
			halt(HTTP_STATUS_REDIRECT);
		} else {
			// this mainly to tell the browser / client to redirect also the request data
			// for post/put or delete methods
			halt(HttpConstants._307_TEMPORARY_REDIRECT);
		}
	}
}
