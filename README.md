
# Liquid Galaxy TinyGS Data Visualization Tool

## TinyGS Data Visualization Tool
   <img src="https://raw.githubusercontent.com/GoutamVerma/TinyGS-data-visualization-for-Liquid-Galaxy-GSoC-2022/main/app/src/main/java/com/Goutam/TinygsDataVisualization/GIF.gif"/>


## Discription

This is an android application which visualize all Satellites and Ground Station available on TinyGS server. This application have capability to perform geographical animations
around the satellites and ground stations. To perform actions users needs to have running Liquid Galaxy, android tablet and internet connection.

*This application have following features*

* **Satellite** A collection of satellite moving around the earth.

* **Ground Station** A tiny ground station running with the help of LoRa boards and ground wave antenna.

* **Packets** A signals received from satellite with the help of Ground Station or TinyGS.

This application is developed as part of Google Summer of Code 2022 by Goutam Verma, with Merul Diman as mentor, and Andreu Ibañez, Mario Ezquerro as co-mentors.

For information regarding how to create content click [here](https://docs.google.com/document/d/1ctusDehQJA2rD2hkLhemHXaoJVU2jk1SpHPEBQ8IngI/edit?usp=sharing)

![app_banner](https://user-images.githubusercontent.com/66783850/187384759-9d03c653-a5e4-47a4-9184-b38d05000ba6.png)


## Prerequisite
* Android 10(API Level 29)
* 10 inch screen of tablet
* USB of [OEM](https://developer.android.com/studio/run/oem-usb) controllers of your tablet
* Internet connection

## Deployment

*Deployment process is divided into two parts,Installation and Visualization*
### **Installation**
*There is two ways to install this application :*

* **Running with APK**
1. Download the latest release of apk.
2. Install it on physical device or emulator.

* **Running from source code**.  *for more information click [here](https://docs.google.com/document/d/1_ZeUjEMmFg7-kAOK7lCrzjlebY4-GX5xe7kvF8cZIK4/edit?usp=sharing)*
1. Import the project from version control in android.
2. If you have a physical android tablet use that otherwise create a new AVD.(for more information click here)
3. Connect android device or emulator
4. Click on the “Run” button(Apk will install and show UI of Apk)


### **How to Visualize Satellite on Liquid Galaxy**
1. Install and open apk in device.
2. Click on LG setting tab, enter the username,password and Ip address of master machine with port no(Ip:Port no).
3. Visit `satellite` tab and select the satellite from view.
4. Users will see the information and images about satellite.
5. Click on `Visualise` to see animation on Liquid Galaxy rigs.

### **How to visualise Packets and Ground Stations**
1. Establish connection between Liquid Galaxy and apk.
2. Visit `packets` or `station` tab.
3. Users will see the list of all ground stations and packets.
4. Click on `orbit`, If users wants to see orbit view around ground stations
5. Click on `fly-to`, If users wants to simply visit POI along with information about it.

*For more information*

* How to import project in android studio? click [here](https://developer.android.com/studio/intro/migrate)
* How to create Android virtual device? click [here](https://developer.android.com/studio/run/managing-avds)
* How to run project in android studio? click [here](https://developer.android.com/studio/run)

## Tech Stack
* Java
* ESP,KML
* Datagram socket
* Android Studio
* Google Earth Studio

## Authors

* [Goutam Verma](https://github.com/GoutamVerma)😎

## License

Licensed by [MIT](https://raw.githubusercontent.com/GoutamVerma/TinyGS-data-visualization-for-Liquid-Galaxy-GSoC-2022/main/LICENSE)

  
