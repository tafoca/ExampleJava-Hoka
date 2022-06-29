/*
 * Java
 *
 * Copyright 2017-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;

import com.microej.example.hoka.connectivity.ManageableServer;

import ej.hoka.http.HttpServer;
import ej.hoka.http.requesthandler.ClasspathFilesHandler;

/**
 * This simple server exposes resources from the src/main/resources/public folder.
 */
public class SimpleServer extends ManageableServer {
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private final HttpServer http;

	/**
	 * Constructor.
	 *
	 * @throws IOException
	 *             Thrown when the server could not be started.
	 */
	public SimpleServer() throws IOException {
		// Create the HTTP server with the default classpath resource handler
		this.http = HttpServer.builder() //
				.port(DEFAULT_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1) //
				.staticFilesHandler(ClasspathFilesHandler.builder() // set the static file handler
						.rootDirectory("/public") // set the static file folder form src/main/resources //$NON-NLS-1$
						.build())
				.build();
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
