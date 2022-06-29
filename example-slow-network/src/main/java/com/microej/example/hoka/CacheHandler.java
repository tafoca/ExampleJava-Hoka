/*
 * Java
 *
 * Copyright 2017-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

import ej.hoka.http.HttpRequest;
import ej.hoka.http.HttpResponse;
import ej.hoka.http.requesthandler.RequestHandler;
import ej.hoka.http.support.Mime;

/**
 * Handler which adds Cache control to an incoming request.
 */
public class CacheHandler implements RequestHandler {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String FIELD_CACHE_CONTROL = "Cache-Control"; //$NON-NLS-1$
	private static final String FIELD_CACHE_CONTROL_VALUE_IMMUTABLE = "public, max-age=31536000, immutable"; //$NON-NLS-1$
	private static final String MIME_TYPE_IMAGE = "image/"; //$NON-NLS-1$

	// List the MIME type to consider as immutable.
	private static final String[] immutableMimeTypes = new String[] { Mime.MIME_CSS, Mime.MIME_JS, MIME_TYPE_IMAGE };

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		final String mimeType = response.getMimeType();
		if (mimeType != null && isImmutable(mimeType)) {
			response.addHeader(FIELD_CACHE_CONTROL, FIELD_CACHE_CONTROL_VALUE_IMMUTABLE);
		}
	}

	private boolean isImmutable(String mimeType) {
		for (String immutableMimeType : immutableMimeTypes) {
			String typeToBeChecked = (immutableMimeType != null) ? immutableMimeType : EMPTY_STRING;
			if (immutableMimeType != null && mimeType.startsWith(typeToBeChecked)) {
				return true;
			}
		}
		return false;
	}
}
