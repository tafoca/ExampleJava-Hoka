/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.connectivity;

import java.io.IOException;

import ej.annotation.Nullable;
import ej.ecom.wifi.AccessPoint;
import ej.hoka.log.HokaLogger;
import ej.net.util.NtpUtil;
import ej.net.util.wifi.WifiNetworkManager;

/**
 * The Wi-Fi network manager for when the Server is run on embedded.
 */
public class EmbeddedWifiNetworkManager implements WirelessNetworkManager {
	private static final int ACCESS_POINT_JOIN_TIMEOUT = 20000;
	private @Nullable WifiNetworkManager wifiManager = null;

	/**
	 * Constructor.
	 *
	 * @throws IOException
	 *             When AccessPoint cannot be created.
	 */
	public EmbeddedWifiNetworkManager() throws IOException {
		super();
	}

	@Override
	public void init() throws IOException {
		HokaLogger.instance.info("EmbeddedWifiNetworkManager.init()"); //$NON-NLS-1$

		// Join the configured Access Point
		WirelessCredentials credentials = new ConfigurationService().getWirelessCredentials();
		this.wifiManager = new WifiNetworkManager();
		this.wifiManager.joinAccessPoint(credentials.getSsId(), credentials.getPassword(),
				credentials.getSecurityMode(), ACCESS_POINT_JOIN_TIMEOUT);

		// Update the local time on the board
		NtpUtil.updateLocalTime();
	}

	@Override
	public void deinit() throws IOException {
		HokaLogger.instance.info("EmbeddedWifiNetworkManager.deinit()"); //$NON-NLS-1$
		assert (this.wifiManager != null);
		this.wifiManager.leaveAccessPoint();
	}

	@Override
	public void joinAccessPoint(AccessPoint accessPoint, @Nullable String password, int timeout) throws IOException {
		HokaLogger.instance.info("EmbeddedWifiNetworkManager.joinAccessPoint(accessPoint, password, timeout)"); //$NON-NLS-1$
		assert (this.wifiManager != null);
		this.wifiManager.joinAccessPoint(accessPoint, password, timeout);
	}
}
