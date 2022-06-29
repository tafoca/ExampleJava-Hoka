/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import java.io.IOException;

import com.microej.example.hoka.connectivity.EmbeddedWifiNetworkManager;
import com.microej.example.hoka.connectivity.ManageableServer;
import com.microej.example.hoka.connectivity.ServerManager;
import com.microej.example.hoka.connectivity.SimulatorWifiNetworkManager;
import com.microej.example.hoka.connectivity.WirelessNetworkManager;

import ej.bon.Constants;
import ej.service.ServiceFactory;

/**
 * Main class. Starts an existing HOKA server example. <br/>
 * Also starts Wi-Fi routines to connect to a Wi-Fi connection point. The 'Wi-Fi support' comments mark the logic that
 * has to be added for this.
 */
public class Main {
	private static final long ONE_HOUR = 60 * 60 * 1000L;

	// Wi-Fi support
	/** BON Constant to know if we are running on simulator or on board. **/
	private static final String ON_SIMULATOR = "com.microej.library.microui.onS3"; //$NON-NLS-1$

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

		// Wi-Fi support
		final WirelessNetworkManager wifiNetworkManager;
		if (Constants.getBoolean(ON_SIMULATOR)) {
			wifiNetworkManager = ServiceFactory.getService(WirelessNetworkManager.class,
					SimulatorWifiNetworkManager.class);
		} else {
			wifiNetworkManager = ServiceFactory.getService(WirelessNetworkManager.class,
					EmbeddedWifiNetworkManager.class);
		}
		wifiNetworkManager.init();

		// Choose one HOKA example for which to enable Wi-Fi
		ManageableServer myHokaServer = new SimpleServer();
		ServerManager.startServer(myHokaServer);

		// Stop after 1 hour
		Thread.sleep(ONE_HOUR);

		ServerManager.stopServer(myHokaServer);

		// Wi-Fi support
		wifiNetworkManager.deinit();
	}
}
