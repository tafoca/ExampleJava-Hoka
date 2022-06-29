/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.connectivity;

import java.io.IOException;

import android.net.ConnectivityManager;
import ej.hoka.log.HokaLogger;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.service.ServiceFactory;

/**
 * Starts and stops the server.
 */
public class ServerManager {
	private static final String STOPPING_SERVER = "Stopping server.."; //$NON-NLS-1$
	private static final String NO_CONNECTIVITY_MANAGER = "No connectivity manager found."; //$NON-NLS-1$

	private ServerManager() {
		// No instantiation intended.
	}

	/**
	 * Starts the {@link ManageableServer} and registers it with the connectivity manager.
	 *
	 * @param manageableServer
	 *            the server to be started.
	 * @throws IOException
	 *             When the server could not be started.
	 */
	public static void startServer(ManageableServer manageableServer) throws IOException {
		ConnectivityManager connectivityManager = ServiceFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			manageableServer.start();
			ConnectivityUtil.registerAndCall(connectivityManager, manageableServer);
		} else {
			HokaLogger.instance.info(NO_CONNECTIVITY_MANAGER);
		}
	}

	/**
	 * Stops the {@link ManageableServer} and unregisters it with the connectivity manager.
	 *
	 * @param manageableServer
	 *            the server to be stopped.
	 */
	public static void stopServer(ManageableServer manageableServer) {
		HokaLogger.instance.info(STOPPING_SERVER);
		ConnectivityManager connectivityManager = ServiceFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityManager.unregisterNetworkCallback(manageableServer);
			manageableServer.stop();
		}
	}
}
