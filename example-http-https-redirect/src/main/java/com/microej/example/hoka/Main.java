/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.microej.example.hoka.connectivity.ManageableServer;
import com.microej.example.hoka.connectivity.ServerManager;

/**
 * Main class. Starts a Web server and monitors its connectivity.
 */
public class Main {
	private static final long ONE_HOUR = 60 * 60 * 1000L;

	/**
	 * Entry point.
	 *
	 * @param args the arguments for the entry point.
	 * @throws InterruptedException     Thrown if interrupted while sleeping.
	 * @throws IOException              Thrown if http server could not be started.
	 * @throws GeneralSecurityException Thrown if ssl context could not be created.
	 */
	public static void main(String[] args) throws InterruptedException, GeneralSecurityException, IOException {
		final ManageableServer webServer = new WebServer();
		ServerManager.startServer(webServer);

		// Stop after 1 hour
		Thread.sleep(ONE_HOUR);
		ServerManager.stopServer(webServer);
	}
}
