# Overview

The example [CORSFilterServer](src/main/java/com/microej/example/hoka/WebServer.java) shows a basic instantiation of `HttpServer` with CORS Filter.

Cross-origin resource sharing (CORS) is a mechanism that allows restricted resources on a web page to be requested from another domain outside the domain from which the first resource was served.
The CORS standard describes new HTTP headers that give browsers and servers a way to request a remote URL only if they have permission to do so. Although some validations and permissions may be performed by the server, it is generally the browser’s responsibility to support these headers and to honor the restrictions they impose.

# Usage

## Run on MicroEJ Simulator

1. Right-click on the project
2. Select **Run as -> MicroEJ Application**
3. Select your platform 
4. Press **OK**
5. The URL being used is http://localhost:8080.
6. Send a GET request to /hello/:name from another server 2 (host 2) from a client app running in a browser.
7. Notice that the CORS request is accepted by the server even if it come from a different host.
8. Disable (comment) the CORS filter and retry. the request should be rejected.

## Run on device

### Build

1. Right Click on the project,
2. Select **Run as > Run Configuration**,
3. Select **MicroEJ Application** configuration kind,
4. Click on **New launch configuration** icon,
5. In **Execution** tab:
	1. In **Target** frame, in **Platform** field, select a platform,
	2. In **Execution** frame:
		1. Select **Execute on Device**,
		2. In **Settings** field, select **Build & Deploy**,
6. Press **Apply**,
7. Press **Run**,
8. The MicroEJ application files (`microejapp.o`, `microejruntime.a`, include files) have been generated.
9. Build the BSP project of the device.

### Flash

1. Use the appropriate flashing tool.
2. Check the INFO logs for the URL information (this is an example): "The IP address of the board is: /192.168.0.224"
3. Send a GET request to /hello/:name from another server 2 (host 2) from a client app running in a browser.
4. Notice that the CORS request is accepted by the server even if it come from a different host.
5. Disable (comment) the CORS filter and retry. the request should be rejected.


# Requirements

This example has been tested on:

* MicroEJ SDK `5.4.0`.
* With the [STM32F7508-DK 1.3.2](https://github.com/MicroEJ/Platform-STMicroelectronics-STM32F7508-DK/tree/1.3.2) Platform that contains:

	* `EDC-1.3`.
	* `NET-1.1`.
	* `BON-1.4`.
	* `FS-2.0`.	

# Dependencies

*All dependencies are retrieved transitively by Ivy resolver*.

# Source

N.A.

# Restrictions

None.

---
_Markdown_   
_Copyright 2019-2022 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   