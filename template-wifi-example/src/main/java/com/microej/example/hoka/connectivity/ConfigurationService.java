/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka.connectivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ej.annotation.Nullable;
import ej.ecom.wifi.SecurityMode;
import ej.hoka.log.HokaLogger;

/**
 * Provides configuration.
 */
public class ConfigurationService {
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	/**
	 * Put here the name of your security mode if not provided via SD card.
	 */
	private static final SecurityMode YOUR_LOCAL_SECURITY_MODE = SecurityMode.WPA2;
	/**
	 * Put here the name of the Wireless network that should be taken if not provided via SD card.
	 */
	private static final String YOUR_LOCAL_WIFI_GATEWAY = "MyWifiGateway"; //$NON-NLS-1$
	/**
	 * Put here the password of the Wireless network that should be taken if not provided via SD card.
	 */
	private static final String YOUR_LOCAL_WIFI_PASSWORD = EMPTY_STRING;
	private static final String USING_DEFAULT_WIRELESS_CREDENTIALS = "Using default wireless credentials"; //$NON-NLS-1$
	private static final String FOUND_CREDENTIALS_SD_CARD = "Found credentials in SD card"; //$NON-NLS-1$
	private static final String READING_SECURITY_MODE = "Reading SecurityMode"; //$NON-NLS-1$
	private static final String CREDENTIALS_FILE_EXISTS = "credentialsFile.exists: "; //$NON-NLS-1$
	private static final String USER_DIR = "user.dir"; //$NON-NLS-1$
	private static final String WORKING_DIRECTORY = "Working Directory: "; //$NON-NLS-1$
	private static final String CREDENTIALS_PATH = "credentials.txt"; //$NON-NLS-1$

	/**
	 * Returns credentials. If possible finds and returns credentials lying on an SD card inserted in the board, under
	 * /usr/credentials.txt. Otherwise it will return default credentials.
	 *
	 * @return Credentials the wireless credentials
	 */
	protected WirelessCredentials getWirelessCredentials() {
		WirelessCredentials credentials = null;
		HokaLogger.instance.info(WORKING_DIRECTORY + System.getProperty(USER_DIR));
		final File credentialsFile = new File(CREDENTIALS_PATH);
		HokaLogger.instance.info(CREDENTIALS_FILE_EXISTS + credentialsFile.exists());
		if (credentialsFile.exists()) {
			try (BufferedReader fileInputStream = new BufferedReader(
					new InputStreamReader(new FileInputStream(credentialsFile)), 64)) {
				String ssid = fileInputStream.readLine();
				String passphrase = fileInputStream.readLine();
				HokaLogger.instance.info(READING_SECURITY_MODE);
				String securityModeReadLine = fileInputStream.readLine();
				String securityModeValue = (securityModeReadLine != null) ? securityModeReadLine : EMPTY_STRING;
				SecurityMode securityMode = getSecurityModeEnumValueFromString(securityModeValue);
				if (ssid != null && passphrase != null && securityMode != null) {
					HokaLogger.instance.info(FOUND_CREDENTIALS_SD_CARD);
					credentials = new WirelessCredentials(ssid, passphrase, securityMode);
				}
			} catch (IOException e) {
				HokaLogger.instance.error(e);
			}
		}

		if (null == credentials) {
			credentials = getDefaultWirelessCredentials();
		}
		return credentials;
	}

	/**
	 * Gets the default wireless credentials.
	 *
	 * @return WirelessCredentials the default wireless credentials.
	 */
	private WirelessCredentials getDefaultWirelessCredentials() {
		HokaLogger.instance.info(USING_DEFAULT_WIRELESS_CREDENTIALS);
		return new WirelessCredentials(YOUR_LOCAL_WIFI_GATEWAY, YOUR_LOCAL_WIFI_PASSWORD, YOUR_LOCAL_SECURITY_MODE);
	}

	/**
	 * Finds the value of the given enumeration by name, case-insensitive.
	 *
	 * @param givenValue
	 *            the security mode as string
	 * @return the security mode as enumeration value
	 **/
	private @Nullable SecurityMode getSecurityModeEnumValueFromString(String givenValue) {
		for (SecurityMode securityMode : SecurityMode.values()) {
			if (securityMode.toString().equalsIgnoreCase(givenValue)) {
				return securityMode;
			}
		}
		return null;
	}
}
