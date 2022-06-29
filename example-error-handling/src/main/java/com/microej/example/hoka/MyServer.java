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
 * This server example shows different manner to handle error with our server. It uses the private key and certificates
 * from the src/resources folder to enable HTTPS.
 */
public class MyServer extends ManageableServer {
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private static final int CACHE_EXPIRE_TIME_SECONDS = 60 * 1000;

	private final HttpServer https;

	/**
	 * Constructor.
	 */
	public MyServer() {
		// Create the HTTP server with the ServerSocketFactory of the SSL context
		this.https = HttpServer.builder() //
				.port(DEFAULT_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1) //
				.developmentMode()//
				.withTrailingSlashSupport()// '/hello/' will match '/hello/:name' with an empty param
				.staticFilesHandler(ClasspathFilesHandler.builder() //
						.rootDirectory("/public") // set the static resources folder //$NON-NLS-1$
						.expireTimeSeconds(CACHE_EXPIRE_TIME_SECONDS).build())// set expiration time for cache. use 0 to
				// disable the
				.build();

		// common error handling. Map the BadRequestException to BadRequestHandler
		this.https.exception(BadRequestException.class, new BadRequestHandler());

		// Plug an hello handler to the given url as a GET request from REST API.
		// ":name" is a parameter, you should replace it by a name during your request.
		this.https.get("hello/:name", new RequestHandler() { //$NON-NLS-1$

			@Override
			public void process(HttpRequest request, HttpResponse response) {
				// Retrieve name from request parameters
				String name = request.getPathParam("name"); //$NON-NLS-1$
				if (name == null || name.isEmpty()) {
					// In case of wrong name parameter, we throw the mapped exception
					throw new BadRequestException();
				}

				// Else we return a basic message
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
		this.https.start();
	}

	/**
	 * Stops the server.
	 */
	@Override
	public void stop() {
		this.https.stop();
	}
}
