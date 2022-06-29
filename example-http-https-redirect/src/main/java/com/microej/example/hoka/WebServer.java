/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;

import com.microej.example.hoka.connectivity.ManageableServer;

import ej.hoka.http.HttpServer;
import ej.hoka.http.requesthandler.ClasspathFilesHandler;
import ej.net.util.ssl.SslContextBuilder;

/**
 * This server example shows how to use the HTTP server in secure mode (HTTPS) and the HTTP server at the same time. It
 * uses the private key and certificates from the src/resources folder to enable HTTPS.
 */
public class WebServer extends ManageableServer {

	private static final String PACKAGE = "/https/"; //$NON-NLS-1$

	private static final int HTTP_PORT = 80;
	private static final int HTTPS_PORT = 443;

	private static final String KEY_PATH = PACKAGE + "hoka.key"; //$NON-NLS-1$
	private static final String CERTIFICATE_PATH = PACKAGE + "hoka.crt"; //$NON-NLS-1$
	private static final String CA_CERTIFICATE_PATH = PACKAGE + "ca.crt"; //$NON-NLS-1$

	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private static final int CACHE_EXPIRE_TIME_SECONDS = 60 * 1000;

	// server instances
	private final HttpServer https;
	private final HttpServer http;

	/**
	 * Constructor.
	 *
	 * @throws GeneralSecurityException
	 *             a security exception.
	 * @throws IOException
	 *             a I/O exception.
	 */
	public WebServer() throws GeneralSecurityException, IOException {

		// Setup the SSL context with custom key and certificates.
		SslContextBuilder sslContextBuilder = new SslContextBuilder();
		sslContextBuilder.addClientKey(KEY_PATH, CERTIFICATE_PATH, CA_CERTIFICATE_PATH);
		SSLContext sslContext = sslContextBuilder.build("123456"); // password //$NON-NLS-1$

		// Create the HTTP server with the ServerSocketFactory of the SSL context
		this.https = HttpServer.builder() //
				.port(HTTPS_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1) //
				.secure(sslContext.getServerSocketFactory()).developmentMode() // send stack trace to client
				.staticFilesHandler(ClasspathFilesHandler.builder() //
						.rootDirectory("/public") // set the static resources folder //$NON-NLS-1$
						.expireTimeSeconds(CACHE_EXPIRE_TIME_SECONDS) // set expiration time for cache. Use 0 to disable
						// the cache
						.build())
				.build(); // serve static files

		// redirect all /* uri from http to https
		this.http = HttpServer.builder() //
				.port(HTTP_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1).build();
		this.http.before(new HttpsRedirectFilter());
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
		this.https.start();
	}

	/**
	 * Stops the server.
	 */
	@Override
	public void stop() {
		this.https.stop();
		this.http.stop();
	}
}
