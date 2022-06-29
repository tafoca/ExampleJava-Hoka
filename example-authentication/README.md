# Overview

This example [AuthenticationServer](src/main/java/com/microej/example/hoka/AuthenticationServer.java)  contains 4 pages:

Public pages available without authentication:

- GET | `/login` this endpoint serve the login page. think of it as the view part in an MVC world
- POST | `/login` this endpoint is the login controller, where we check the user credentials and create the session cookie.

Private pages where authentication is required:

- GET | `/user-profile` user and admin authenticated users can browse this url
- GET | `/admininistration` only admin authenticated users can browse this url

The example uses the default session-based authentication implementations in package ej.hoka.auth.session in the Hoka library and the ACL authorization implementation in the access-control library.
 

# Usage

## Run on MicroEJ Simulator

1. Right-click on the project
2. Select **Run as -> MicroEJ Application**
3. Select your platform 
4. Press **OK**
5. The URL being used is http://localhost:8080.
6. Go to [/administration](http://localhost:8080/administration), you should be redirect to the login page.
7. Go to [/user-profile](http://localhost:8080/user-profile), you should be redirect to the login page.
8. Go to [/login](http://localhost:8080/login) to login as "admin" with "1234" password.
9. Now you should have access to `/user-profile` and `/administration`.
10. Go to [/api/logout](http://localhost:8080/logout) or click on 'Logout'.
11. Go back to [/administration](http://localhost:8080/administration), you are, again, not authenticated and redirected to login page.
12. Repeat the same steps as "user" with "1234" password. You should only have access to the `/user-profile` page.

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
3. The port being used is 8080.
4. Go to [/administration](http://192.168.0.224:8080/administration), you should be redirect to the login page.
5. Go to [/user-profile](http://192.168.0.224:8080/user-profile), you should be redirect to the login page.
6. Go to [/login](http://192.168.0.224:8080/login) to login as "admin" with "1234" password.
7. Now you should have access to `/user-profile` and `/administration`.
8. Go to [/api/logout](http://192.168.0.224:8080/logout) or click on 'Logout'.
9. Go back to [/administration](http://192.168.0.224:8080/administration), you are, again, not authenticated and redirected to login page.
10. Repeat the same steps as "user" with "1234" password. You should only have access to the `/user-profile` page.


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