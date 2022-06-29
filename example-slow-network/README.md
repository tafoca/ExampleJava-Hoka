# Overview

The [SlowNetworkServer](src/main/java/com/microej/example/hoka/SlowNetworkServer.java) is designed for a slow network :

-   Resources are sent in a compressed (gzip) form when available if supported by the client.
-   Also, immutable resources are cached in the user\'s browser.

# Usage

## Run on MicroEJ Simulator

1. Right Click on the project, 
2. Select **Run as > Run Configuration**,
3. Select **MicroEJ Application** configuration kind,
4. Click on **New launch configuration** icon,
5. In **Execution** tab:
	1. In **Target** frame, in **Platform** field, select a platform,
	2. In **Execution** frame:
		1. Select **Execute on Simulator**,
	3. In **Options files** frame:
		1. Click **Add..**,
		2. Add the file under build/sim/sim.properties,
6. Press **Apply**,
7. Press **Run**. 
8. The URL being used is http://localhost:8080.
9. The provided URL now serves the Java resource [src/main/resources/public/index.html](src/main/resources/public/index.html).
10. You should see the [microej.v1.png](filesystem/public/img/microej.v1.png) image.
11. Refresh the page by pressing `ENTER` in the URL bar (refresh button will disable the cache for the request) and look at the Hoka logs : the image is not requested again (because it was cached by the browser).
12. Edit the file [index.html](filesystem/public/index.html) and change the image source to `png/microej.v2.png`.
13. Go to [localhost:8080](http://localhost:8080) again, you should see the [microej.v2.png](filesystem/public/img/microej.v2.png) image.
14. Refresh the page by pressing `ENTER` in the URL bar (refresh button will disable the cache for the request) and look at the Hoka logs : the image is not requested again (because it was cached by the browser).


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
4. You should see the [microej.v1.png](filesystem/public/img/microej.v1.png) image.
5. Refresh the page by pressing `ENTER` in the URL bar (refresh button will disable the cache for the request) and look at the Hoka logs : the image is not requested again (because it was cached by the browser).
6. Edit the file [index.html](filesystem/public/index.html) and change the image source to `png/microej.v2.png`.
7. Go to [192.168.0.224:8080](http://localhost:8080) again, you should see the [microej.v2.png](filesystem/public/img/microej.v2.png) image.
8. Refresh the page by pressing `ENTER` in the URL bar (refresh button will disable the cache for the request) and look at the Hoka logs : the image is not requested again (because it was cached by the browser).

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
