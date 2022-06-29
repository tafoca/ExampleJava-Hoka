/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import static ej.hoka.http.HttpServer.halt;

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;

/**
 * Stop the execution and return 400 Bad Request.
 */
public class BadRequestHandler implements RequestHandler {

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		// For our error handling we want to return a custom error message.
		halt(HttpConstants.HTTP_STATUS_OK,
				"The path parameter is required. Please add it to the url. For example: http://localhost:8080/hello/world"); //$NON-NLS-1$
	}
}
