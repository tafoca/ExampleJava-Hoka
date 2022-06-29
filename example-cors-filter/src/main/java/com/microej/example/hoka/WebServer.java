/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;

import com.microej.example.hoka.connectivity.ManageableServer;

import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.HttpServer;
import ej.hoka.http.requesthandler.ClasspathFilesHandler;
import ej.hoka.http.requesthandler.RequestHandler;

/**
 * An example of a server with CORS support.
 */
public class WebServer extends ManageableServer {
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private final HttpServer http;

	/**
	 * Constructor which also starts a HTTP server.
	 *
	 * @throws IOException
	 *             Thrown when the server could not be started.
	 */
	public WebServer() throws IOException {
		// Create the HTTP server with the default classpath resource handler
		this.http = HttpServer.builder() //
				.port(DEFAULT_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS)//
				.workerCount(1)//
				.staticFilesHandler(ClasspathFilesHandler.builder() // set the static file handler
						.rootDirectory("/public") // set the static file folder form src/main/resources //$NON-NLS-1$
						.build())
				.build();

		// Before each request treatment, this instruction add CORS filter
		this.http.before(new RequestCORSFilter());

		this.http.get("/hello/:name", new RequestHandler() { //$NON-NLS-1$

			@Override
			public void process(HttpRequest request, HttpResponse response) {
				String name = request.getPathParam("name"); //$NON-NLS-1$
				response.setData("Hello " + name + " !"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});
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
