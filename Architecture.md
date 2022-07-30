# Architecture of the TinyGS Data Visualization

TinyGS data visualization is developed in Java(android).
The aim of this application is to get real time location and information about satellites, stations and packets with the help of TinyGS. And display it over `Liquid Galaxy` rigs.


## Top-level

This application is spilt into three top-level projects:

- `Connection`: core implementation to set up connection between Liquid Galaxy and TinyGS Application.
- `Data`: data gathering implementation to gets real time satellites information from TinyGS.
- `Visualization`:

The entry point for `connection` is in `LGConnectionManager.java`, where we start sessions of SSH, for sending resources and data to Liquid Galaxy.

## System overview

Here is a high-level view of the hierarchy of some of the main performer in the system:

```ascii
                                                     +------------------------+
                                +------------------->|  Ground Wave Antenna   |
                                |                    +------------------------+
                                |
                        +----------------+           +------------+
      +---------------->| TinyGS Station |---------->| LoRa Board |
      |                 +----------------+           +------------+
      |                         |
      |                         |                    +-------------------+
      |                         +------------------->| Connecting cabels |
      |                                              +-------------------+
+----------------+
| Satellite data |
+----------------+
      |                                              
      |                                           
      |                 +---------------+            +---------+
      +---------------->| TinyGS Server |----------->|   API   |
                        +---------------+            +---------+
                        
                        
                        
                        
                                                     +------------------------+
                                +------------------->|  LGConnectionManager   |
                                |                    +------------------------+
                        +----------------+
      +---------------->|   Connection   |
      |                 +----------------+           
      |                         |                    +----------------------+
      |                         +------------------->| LGConnectionSendFile |
      |                                              +----------------------+
      |
+----------------+
| Visualization  |
+----------------+                            +------------------+
      |                          +----------->| ActionControler  |
      |                          |            +------------------+
      |                  +---------------+
      +----------------->|    Action     |
                         +---------------+  
                                 |            +---------------------------+
                                 +----------->| ActionBuildCommandUtility |
                                              +---------------------------+

+----------+
|   Help   |
+----------+

+--------+
| About  |
+--------+
```

And a short description of each performer role:

- Satellite data: Gets realtime from TinyGS Server and Station.
- Visualization: Send KML file to Liquid Galaxy along with description,images and coordinate positions.
- Help: Instruct users to perform actions in application

TinyGS application perform an one way community with Liquid Galaxy

- direct messages: when actors have a reference to other actors, they can exchange [direct messages](https://doc.akka.io/docs/akka/current/typed/interaction-patterns.html)
- events: actors can emit events to a shared [event stream](https://doc.akka.io/docs/akka/current/event-bus.html), and other actors can register to these events

## Visualization scenarios

Let's dive into a few visualization scenarios to show which performers are involved.

### Designing KMLs

When we want to see information on Liquid Galaxy:

- Get the all information including: satellite name, description, image, longitude, latitude and altitude.
- Put all the information in desired kml parameters.

```ascii 


                                        +---------------------------+                                     
                                 +----->|    Balloon(Description)   |-----+                                                            
                                 |      +---------------------------+     |                                                            
+---------------------------+    |      +---------------------------+     |      +----------+
|       KML(Structure)      |----+----->|     Placemark(Icon)       |-----+----->| file.kml |
+---------------------------+    |      +---------------------------+     |      +----------+        
                  |              |      +---------------------------+     |                                                             
                  |              +----->| Trajectory(around earth)  |-----+                                                             
                  |                     +---------------------------+                                       
                  |                            |
                  |                            |
                  |      +--------+            |
                  +----->|  Tour  |<-----------+
                         +--------+
```

### Sending KML to Liquid Galaxy

When we send any file to Liquid Galaxy

- SSH channel will be create.
- Add file path to SSH channel
- and send to Liquid Galaxy Master.

```ascii
                                          +-----------------------+
                                          | Liquid Galaxt(Salve)  |
                                          +-----------------------+
                                                     |
+-----------+      +----------------+     +-----------------------+
| final.kml |----->|  SSH(channel)  |---->| Liquid Galaxy(Master) |
+-----------+      +----------------+     +-----------------------+                   
                                                     |
                                          +-----------------------+
                                          | Liquid Galaxt(Salve)  |
                                          +-----------------------+
```
