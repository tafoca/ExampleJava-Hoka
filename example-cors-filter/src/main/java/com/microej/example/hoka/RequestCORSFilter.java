/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import static ej.hoka.http.HttpServer.halt;

import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;

/**
 * A handler adding CORS functionality.
 */
public class RequestCORSFilter implements RequestHandler {

	private static final String ASTERISK = "*"; //$NON-NLS-1$

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		// Set the CORS filter header

		response.addHeader("Access-Control-Allow-Origin", ASTERISK); //$NON-NLS-1$
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); //$NON-NLS-1$ //$NON-NLS-2$
		response.addHeader("Access-Control-Allow-Headers", ASTERISK); //$NON-NLS-1$
		response.addHeader("Access-Control-Max-Age", "80000"); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the request method and header to manage CORS Filter case
		int method = request.getMethod();
		if (method == HttpRequest.OPTIONS && request.getHeaders().containsKey("origin")) { //$NON-NLS-1$
			// Return immediately with the CORS headers
			halt("204 No Content"); //$NON-NLS-1$
		}
	}
}
