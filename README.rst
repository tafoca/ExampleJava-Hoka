
.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/sdk_5.4.json
   :align: left

.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/arch_7.14.json
   :align: left

Overview
========

This project contains examples using the HOKA HTTP Server :

-  `example-simple <example-simple/README.md>`__ : a minimal example of
   the HOKA webserver.
-  `example-https <example-https/README.md>`__ : similar to
   **example-simple** but using HTTPS.
-  `example-http-https-redirect <example-http-https-redirect/README.md>`__
   : an example showing how to setup an automated redirection from HTTP
   to HTTPS.
-  `example-rest-api <example-rest-api/README.md>`__ : an example of a
   REST API.
-  `example-error-handling <example-error-handling/README.md>`__ : an
   example showing common errors handling.
-  `example-cors-filter <example-cors-filter/README.md>`__ : an example
   to setup CORS support in the server using filters.
-  `example-authentication <example-authentication/README.md>`__ : an
   example of a cookie session authentication and authorizations.
-  `example-slow-network <example-slow-network/README.md>`__ : an
   example for slow networks using compression and caching techniques.
-  `template-wifi-example <template-wifi-example/README.md>`__ : a
   template to insert a HOKA example to enable Wi-Fi connectivity.

Usage
=====

The examples can be run either on the simulator or on the device using
following procedures.

Run on MicroEJ Simulator
------------------------

1. Right Click on the project to run
2. Select **Run as -> MicroEJ Application**
3. Select your platform
4. Press **Ok**
5. The application logging will be printed to the IDE console. The log will print the URL where you can access the HOKA server.  

Run on device
-------------

Build
~~~~~

1.  Right Click on the example to build
2.  Select **Run as -> Run Configuration**
3.  Select **MicroEJ Application** configuration kind
4.  Click on **New launch configuration** icon
5.  In **Execution** tab

6.  In **Target** frame, in **Platform** field, select a relevant
    platform (but not a virtual device)
7.  In **Execution** frame

    1. Select **Execute on Device**
    2. In **Settings** field, select **Build & Deploy**
8.  In **Configuration** tab
9.  In **Device** frame

    1. Select **Deploy**
    2. In **Configuration** field, check the three first boxes. They all start with "Deploy.." 

10.  Press **Apply**
11.  Press **Run**
12. The MicroEj files will have been generated and deployed to your platform of choice, for example: A:/git/Platform-STMicroelectronics-STM32F7508-DK/stm32f7508_freertos-bsp/projects/microej/SW4STM32

Flash
~~~~~

1. Use the appropriate flashing tool. For example: The platform **Platform-STMicroelectronics-STM32F7508-DK** has two scripts lying under A:/git/Platform-STMicroelectronics-STM32F7508-DK/stm32f7508_freertos-bsp/projects/microej/SW4STM32: build.bat, run.bat. Execute first build.bat and finally run.bat to flash the contents to the board.

Inspect application logging
~~~~~~~~~~~~~~~~~~~~~~~~~~~
The examples will log to the board. The log will print the URL where you can access the HOKA server.

To see the logging, use a connection tool like putty to establish a serial connection to the board.
 
After connecting your board to your computer, inspect your device settings and see which COM port is being used by the board. In putty then choose a serial connection and the serial line to connect to, for example: COM3. 

Requirements
============

This library requires the following Foundation Libraries:

::

    @FOUNDATION_LIBRARIES_LIST@

Dependencies
============

*All dependencies are retrieved transitively by Ivy resolver*.

Source
======

N.A.

Restrictions
============

None.

--------------

..  
  Copyright 2019-2021 MicroEJ Corp. All rights reserved.
  Use of this source code is governed by a BSD-style license that can be found with this software.