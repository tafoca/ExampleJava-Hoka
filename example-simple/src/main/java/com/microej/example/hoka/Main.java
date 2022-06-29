/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;

import com.microej.example.hoka.connectivity.ManageableServer;
import com.microej.example.hoka.connectivity.ServerManager;

/**
 * Main class. Starts an authentication server and monitors its connectivity.
 */
public class Main {
	private static final long ONE_HOUR = 60 * 60 * 1000L;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            the arguments for the entry point.
	 * @throws InterruptedException
	 *             If interrupted while sleeping.
	 * @throws IOException
	 *             Thrown when the server could not be started.
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		ManageableServer simpleServer = new SimpleServer();
		ServerManager.startServer(simpleServer);

		// Stop after 1 hour
		Thread.sleep(ONE_HOUR);

		ServerManager.stopServer(simpleServer);
	}
}
