/*
 * Java
 *
 * Copyright 2017-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;

import javax.net.ServerSocketFactory;

import com.microej.example.hoka.connectivity.ManageableServer;
import com.microej.example.hoka.endpoint.CreateNewUser;
import com.microej.example.hoka.endpoint.DeleteUser;
import com.microej.example.hoka.endpoint.GetUserById;
import com.microej.example.hoka.endpoint.ListUsers;
import com.microej.example.hoka.endpoint.UpdateUser;

import ej.hoka.http.HttpServer;
import ej.hoka.http.encoding.EncodingRegistry;
import ej.hoka.http.requesthandler.ClasspathFilesHandler;

/**
 * Example of a set of REST API for a user resource.
 */
public class RestServer extends ManageableServer {

	private static final String URI_USER = "user"; //$NON-NLS-1$
	private static final String URI_USER_BY_ID = "user/:id"; //$NON-NLS-1$
	private static final int CONNECTION_TIMEOUT = 60 * 1000;
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private final HttpServer http;

	/**
	 * Constructor.
	 *
	 */
	public RestServer() {
		this.http = HttpServer.builder() //
				.port(DEFAULT_PORT) // setup server port number. 0 means random port
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) // setup max simultaneous connections
				.workerCount(1) // setup thread count to handle incoming connections
				.connectionTimeout(CONNECTION_TIMEOUT) // setup connection timeout
				.encodingRegistry(new EncodingRegistry()) // register a custom encoding registry
				.secure(ServerSocketFactory.getDefault()) // set server socket factory. to setup ssl / https
				.apiBase("/api/v1/") // set a common uri base for all registered path that don't start //$NON-NLS-1$
										// with a /
				.developmentMode()// enable development mode, send stack trace to client side
				.withTrailingSlashSupport()// '/user/' will match '/user/:id' with an empty param
				.staticFilesHandler(ClasspathFilesHandler.builder() // set the static file handler
						.rootDirectory("/public") // set the static file folder form src/main/resources //$NON-NLS-1$
						.build())
				.build();

		this.http.get(URI_USER_BY_ID, new GetUserById());
		this.http.delete(URI_USER_BY_ID, new DeleteUser());
		this.http.get("users", new ListUsers()); //$NON-NLS-1$
		this.http.post(URI_USER, new CreateNewUser());
		this.http.put(URI_USER, new UpdateUser());
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
