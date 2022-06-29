/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.hoka;

/**
 * Bad HTTP Request.
 *
 * Use this exception when a request parameters validation fails
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Base constructor.
	 */
	public BadRequestException() {
		super();
	}

	/**
	 * Constructor with message.
	 *
	 * @param msg
	 *            the message.
	 */
	public BadRequestException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with message and exception.
	 *
	 * @param msg
	 *            the message.
	 * @param e
	 *            the exception.
	 */
	public BadRequestException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * Constructor with exception.
	 *
	 * @param e
	 *            the exception.
	 */
	public BadRequestException(Throwable e) {
		super(e);
	}
}
