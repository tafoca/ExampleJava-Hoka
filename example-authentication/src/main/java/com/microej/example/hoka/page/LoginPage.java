/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.page;

import static ej.hoka.http.HttpServer.halt;

import java.io.InputStream;

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.http.support.Mime;

/**
 * The login page request handler. This handler returns the login HTML page.
 */
public class LoginPage implements RequestHandler {

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		// return login form
		response.setStatus(HttpConstants.HTTP_STATUS_OK);
		response.setMimeType(Mime.MIME_HTML);
		InputStream loginPage = this.getClass().getResourceAsStream("/public/login.html"); //$NON-NLS-1$
		if (loginPage == null) {
			halt(HttpConstants.HTTP_STATUS_INTERNALERROR);
		} else {
			response.setData(loginPage);
		}
	}
}
