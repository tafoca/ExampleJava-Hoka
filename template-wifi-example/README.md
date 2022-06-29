# Overview

This is a template project for adding Wi-Fi logic to an existing HOKA server project of your choice. 

There are few steps needed for this:

In [module.ivy](module.ivy), 

* add the example that you want as a dependency in the module.ivy. Currently, example-simple has been added as an example.

In [Main](src/main/java/com/microej/example/hoka/Main.java),

* instantiate the Hoka Server of the module you added as a dependency.

<br/>
After this, your HOKA server will be able to connect to a Wi-Fi connection point, in the Simulator as well as Embedded. 
<br/>
<br/>

The corresponding interfaces and classes reside in the package com.microej.example.hoka.connectivity.

You can let the Wi-Fi configuration be read from hard coded configuration in [WirelessCredentials](src/main/java/com/microej/example/hoka/connectivity/WirelessCredentials.java), or from a file in the SD card inserted in your board. 

The file should be located under: /usr/credentials.txt and contain in three subsequent lines: 
* ssid
* passphrase
* securityMode (e.g.: WPA2)

The [ConfigurationService](src/main/java/com/microej/example/hoka/connectivity/ConfigurationService.java) will try to use the file in the SD card first, and fall back to the hard coded values if unsuccessful.

A MOCK Wi-Fi manager will be started when running on simulator. A simple implementation of a Wi-Fi manager called [EmbeddedWiFiNetworkManager](src/main/java/com/microej/example/hoka/connectivity/EmbeddedWiFiNetworkManager.java) will be started when running on your board.

# Usage

## Run on MicroEJ Simulator

1. Right-click on the project
2. Select **Run as -> MicroEJ Application**
3. Select your platform 
4. Press **OK**
5. The URL is: http://localhost:8080.
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
7. In **Configuration** tab:
	1. In the **Device** menu point, in **Deploy** sub-menu point, check all four check boxes,
8. Press **Apply**,
9. Press **Run**,
10. The MicroEJ application files (`microejapp.o`, `microejruntime.a`, include files) have been generated.
11. Building of the BSP project of the device is ready.

### Flash

1. Use the appropriate flashing tool.
2. Check the INFO logs for the URL information (this is an example): "The IP address of the board is: /192.168.0.224"
3. The provided URL now serves the Java resource [src/main/resources/public/index.html](src/main/resources/public/index.html).


# Requirements

This example has been tested on:

* MicroEJ `SDK 5.4.0`.
* With the [ESP32WROVER-Platform-GNUv52b96_xtensa-esp32-psram-2.0.0](https://github.com/MicroEJ/Platform-Espressif-ESP-WROVER-KIT-V4.1/) Platform that contains:
    * `EDC-1.3.3`.
    * `BON-1.4.0`.
    * `NET-1.1.2`.
    * `MICROUI-3.0.4`. 
    * `FS-2.0.6`. 

# Dependencies

*All dependencies are retrieved transitively by Ivy resolver*.

# Source

N.A.

# Restrictions

None.

---  
_Markdown_   
_Copyright 2022 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
