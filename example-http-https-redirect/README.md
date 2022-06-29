# Overview

This example shows how to enable HTTPS and HTTP in the same instance of the Hoka web server.
All the requests made using HTTP are redirected to HTTPS.

The example is using standard HTTP/S port numbers 80/443. If those ports are not available on your test machine you can use different ones like 8080 and 8081.

# Usage

## Run on MicroEJ Simulator

1. Right-click on the project
2. Select **Run as -> MicroEJ Application**
3. In **Execution** tab:
	1. In **Target** frame, in **Platform** field, select a platform,
4. In **Configuration** tab:
	1. Set **Runtime > Memory > Heaps > Immortal heap size (in bytes)** to 6000 (it has to be enough space for the key and the certificates).
5. The URL http://localhost:8080 now serves the Java resource "/public/index.html".
6. Check also the alternative IP Address to be used in the log of the application. 
7. Add an Exception in your browser to trust the given certificate.
8. The request will be redirected to HTTPS and provide the Java resource.

### Build

1. Right Click on the project,
2. Select **Run as > Run Configuration**,
3. Select **MicroEJ Application** configuration kind,
4. Click on **New launch configuration** icon,
5. In **Execution** tab:		
	1. In **Target** frame, in **Platform** field, select a platform,
	2. In **Execution** frame:
		1. Select **Execute on Device**,
		2. In **Settings** field, select **Build & Deploy**. 
6. In **Configuration** tab:
		1. Set **Runtime > Memory > Heaps > Immortal heap size (in bytes)** to 6000 (it has to be enough space for the key and the certificates),
		2. Set **Runtime > Memory > Threads > Number of threads** to 6,		
7. Press **Apply**,
8. Press **Run**,
9. The MicroEJ application files (`microejapp.o`, `microejruntime.a`, include files) have been generated.
10. Build the BSP project of the device.

### Flash

1. Use the appropriate flashing tool.
2. Check the IP Address to be used in the log of the application. 
3. The URL http://[IP Address]:8080 now serves the Java resource [src/main/resources/public/index.html](src/main/resources/public/index.html).
4. Add an Exception in your browser to trust the given certificate.
5. The request will be redirected to HTTPS and provide the Java resource.


# Requirements

This example has been tested on:

* MicroEJ SDK `5.4.0`.
* With the [STM32F7508-DK 1.3.2](https://github.com/MicroEJ/Platform-STMicroelectronics-STM32F7508-DK/tree/1.3.2) Platform that contains:

	* `EDC-1.3`.
	* `NET-1.1`.
	* `BON-1.4`.
	* `FS-2.0`.	
	* `SSL-2.1`.

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