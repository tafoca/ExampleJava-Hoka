/*
 * Java
 *
 * Copyright 2017-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;

import com.microej.example.hoka.connectivity.ManageableServer;

import ej.hoka.http.HttpServer;

/**
 * This example is designed for a slow network. Resources are sent in a compressed form when available if supported by
 * the client to reduce the lengths of responses. Also, immutable resources are cached in the user's browser.
 *
 */
public class SlowNetworkServer extends ManageableServer {
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private final HttpServer http;

	/**
	 * Constructor.
	 *
	 */
	public SlowNetworkServer() {
		this.http = HttpServer.builder() //
				.port(DEFAULT_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1) //
				.developmentMode()//
				.build();

		// Match the request URI with a file in the /public/ directory of the filesystem.
		this.http.get("/*", new FileSystemView("/public", "index.html")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		this.http.after(new CacheHandler()); // Enable cache for immutable resource.
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
