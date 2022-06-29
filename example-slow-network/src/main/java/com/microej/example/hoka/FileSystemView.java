/*
 * Java

 *
 * Copyright 2017-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import static ej.hoka.http.HttpServer.halt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ej.hoka.http.HttpConstants;
import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.http.support.Mime;
import ej.hoka.log.HokaLogger;

/**
 * Processes file accesses.
 */
public class FileSystemView implements RequestHandler {

	private static final String MIME_TYPE_IMAGE = "image"; //$NON-NLS-1$
	private static final String GZIP = "gzip"; //$NON-NLS-1$
	private static final String SLASH = "/"; //$NON-NLS-1$
	private static final String DEFAULT_INDEX = "index.html"; //$NON-NLS-1$

	private final File rootDirectory;
	private final String welcomeFile;

	/**
	 * Constructor.
	 *
	 * @param rootPath
	 *            the root path.
	 */
	public FileSystemView(String rootPath) {
		this(rootPath, DEFAULT_INDEX);
	}

	/**
	 * Constructor with welcome file.
	 *
	 * @param rootPath
	 *            the root path.
	 * @param welcomeFile
	 *            the welcome file.
	 */
	public FileSystemView(String rootPath, String welcomeFile) {
		final File rootFile = new File(rootPath);
		if (!rootFile.exists() || !rootFile.isDirectory()) {
			throw new IllegalArgumentException();
		}
		this.rootDirectory = rootFile;
		this.welcomeFile = welcomeFile;
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		String path = request.getURI();
		if (SLASH.equals(path)) {
			path += this.welcomeFile;
		}

		File file = null;

		// Try to find the compressed version of the requested resource if the browser
		// supports it.
		String encodingHeader = request.getHeader(HttpConstants.HEADER_ACCEPT_ENCODING);
		if (encodingHeader != null && encodingHeader.contains(GZIP)) {
			file = new File(this.rootDirectory, path + ".gz"); //$NON-NLS-1$
			assertFileInRootDirectory(file);
		}

		boolean isCompressed = file != null && file.exists() && file.isFile();

		if (!isCompressed) {
			file = new File(this.rootDirectory, path);
			assertFileInRootDirectory(file);
			if (!file.exists() || !file.isFile()) {
				halt(HttpConstants.HTTP_STATUS_NOTFOUND);
			}
		}

		FileInputStream is;

		try {
			assert (file != null);
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// file not found returns 404
			HokaLogger.instance.error(e);
			final String message = e.getMessage();
			if (message == null) {
				halt(HttpConstants.HTTP_STATUS_NOTFOUND);
			} else {
				halt(HttpConstants.HTTP_STATUS_NOTFOUND, message);
			}
			return;
		}

		response.setStatus(HttpConstants.HTTP_STATUS_OK);
		final String mimeType = Mime.getMIMEType(path);
		if (mimeType != null) {
			response.setMimeType(mimeType);
		} else {
			response.setMimeType(MIME_TYPE_IMAGE);
		}
		response.setData(is);
		if (isCompressed) {
			// if using the compressed file, add the content encoding for it.
			response.addHeader(HttpConstants.HEADER_CONTENT_ENCODING, GZIP);
		}

	}

	/**
	 * Check that the file is within the root directory. The canonical path is checked to avoid directory traversal
	 * using .. in the path
	 *
	 * @param file
	 *            the file to check
	 */
	private void assertFileInRootDirectory(File file) {
		try {
			if (!file.getCanonicalPath().startsWith(this.rootDirectory.getAbsolutePath())) {
				halt(HttpConstants.HTTP_STATUS_FORBIDDEN);
			}
		} catch (IOException e) {
			HokaLogger.instance.error(e);
			halt(HttpConstants.HTTP_STATUS_FORBIDDEN);
		}
	}
}
