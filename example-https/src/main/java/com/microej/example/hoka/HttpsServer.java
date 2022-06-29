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
 * This server example shows how to use the HTTP server in secure mode (HTTPS). It uses the private key and certificates
 * from the src/resources folder to enable HTTPS.
 */
public class HttpsServer extends ManageableServer {
	private static final int DEFAULT_PORT = 8080;
	private static final int SIMULTANEOUS_CONNECTIONS = 5;
	private static final int CACHE_EXPIRE_TIME_SECONDS = 60 * 1000;
	private static final String PACKAGE = "/https/"; //$NON-NLS-1$
	private static final String KEY_PATH = PACKAGE + "hoka.key"; //$NON-NLS-1$
	private static final String CERTIFICATE_PATH = PACKAGE + "hoka.crt"; //$NON-NLS-1$
	private static final String CA_CERTIFICATE_PATH = PACKAGE + "ca.crt"; //$NON-NLS-1$
	private final HttpServer https;

	/**
	 * Constructor.
	 *
	 * @throws GeneralSecurityException
	 *             a security exception.
	 * @throws IOException
	 *             a I/O exception.
	 */
	public HttpsServer() throws GeneralSecurityException, IOException {
		// Setup the SSL context with custom key and certificates.
		final SslContextBuilder sslContextBuilder = new SslContextBuilder();
		sslContextBuilder.addClientKey(KEY_PATH, CERTIFICATE_PATH, CA_CERTIFICATE_PATH);
		final SSLContext sslContext = sslContextBuilder.build("123456"); // password //$NON-NLS-1$

		// Create the HTTP server with the ServerSocketFactory of the SSL context
		this.https = HttpServer.builder() //
				.port(DEFAULT_PORT) //
				.simultaneousConnections(SIMULTANEOUS_CONNECTIONS) //
				.workerCount(1) //
				.secure(sslContext.getServerSocketFactory()) // set the ssl context for TLS setup
				.developmentMode() //
				.staticFilesHandler(ClasspathFilesHandler.builder() //
						.rootDirectory("/public") // set the static resources folder //$NON-NLS-1$
						.expireTimeSeconds(CACHE_EXPIRE_TIME_SECONDS)// set expiration time for cache. use 0 to disable
																		// the cache
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
