# Overview

This is the minimum example of the HOKA library.

The example [SimpleServer](src/main/java/com/microej/example/hoka/SimpleServer.java) shows a basic instantiation of `HttpServer`.

It uses the `ClasspathFilesHandler`. Each time a URL is requested by a client, a match is done between this URL and resource in the [src/main/resources/public](src/main/resources/public) folder.

# Usage

## Run on MicroEJ Simulator

1. Right-click on the project
2. Select **Run as -> MicroEJ Application**
3. Select your platform 
4. Press **OK**
5. The URL being used is http://localhost:8080.
6. The provided URL now serves the Java resource [src/main/resources/public/index.html](src/main/resources/public/index.html).

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
3. The provided URL now serves the Java resource [src/main/resources/public/index.html](src/main/resources/public/index.html).


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
_Copyright 2020-2022 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
