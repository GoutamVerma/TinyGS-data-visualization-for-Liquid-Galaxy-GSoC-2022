package com.Goutam.TinygsDataVisualization.create.utility.model;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.Goutam.TinygsDataVisualization.Satelite.SateliteActivity;
import com.Goutam.TinygsDataVisualization.connection.LGCommand;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionManager;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionSendFile;
import com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility;
import com.neosensory.tlepredictionengine.TlePredictionEngine;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is in charge of sending the commands to liquid galaxy
 */
public class ActionController {

    private static final String TAG_DEBUG = "ActionController";

    private static ActionController instance = null;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Handler handler2 = new Handler(Looper.getMainLooper());
    private ActionBuildCommandUtility action = new ActionBuildCommandUtility();

    public synchronized static ActionController getInstance() {
        if (instance == null)
            instance = new ActionController();
        return instance;
    }

    /**
     * Enforce private constructor
     */
    private ActionController() {}

    public void startOrbit(LGCommand.Listener listener) {

        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        LGCommand lgCommandStartOrbit = new LGCommand(ActionBuildCommandUtility.buildCommandStartOrbit(), LGCommand.CRITICAL_MESSAGE, (String result) -> {
            if (listener != null) {
                listener.onResponse(result);
            }
        });
        handler.postDelayed(() -> lgConnectionManager.addCommandToLG(lgCommandStartOrbit), 3000);
        System.out.println("ORbit ki line dehkle");
        System.out.println(lgCommandStartOrbit);
        //cleanFileKMLs(46000);
    }


    public void sendcleanlogo(AppCompatActivity activity,int rig){
        createResourcesFolder();
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();
        double rigs = Math.floor( rig/ 2) + 2;
        cleanFileKMLs(0);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.clean_logo((int)rigs),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);

    }
    public void sendcleanballloon(AppCompatActivity activity,int rig){
        createResourcesFolder();
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();
        cleanFileKMLs(0);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.clean_balloon(rig),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);

    }


    public void sendshutdown(AppCompatActivity activity,String username,String password, Integer rigs){
      createResourcesFolder();
      LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
      lgConnectionSendFile.startConnection();

      cleanFileKMLs(0);

      handler.postDelayed(() -> {
          LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandshutdown(username,password,rigs),
                  LGCommand.CRITICAL_MESSAGE, (String result) -> {
          });
          LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
          lgConnectionManager.startConnection();
          lgConnectionManager.addCommandToLG(lgCommand);
      }, 2000);

  }


  public void sendReboot(AppCompatActivity activity,String username,String password,Integer rigs){
        createResourcesFolder();
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();

        cleanFileKMLs(0);

       handler.postDelayed(() -> {
           LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandReboot(username,password,rigs),
                   LGCommand.CRITICAL_MESSAGE, (String result) -> {
           });
           LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
           lgConnectionManager.startConnection();
           lgConnectionManager.addCommandToLG(lgCommand);
       }, 2000);

  }

    public void sendLaunch(AppCompatActivity activity,String username,String password,Integer rigs){
        createResourcesFolder();
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();

        cleanFileKMLs(0);

           LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandReLaunch(username,password,rigs),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);

    }

    /**
     * Paint a balloon with the logos
     */
    public void sendBalloonWithLogos(AppCompatActivity activity,int rig) {
        createResourcesFolder();

        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();
        double rigs = Math.floor( rig/ 2) + 2;
        cleanFileKMLs(0);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandBalloonWithLogos((int)rigs),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
            }, 2000);
    }

    public void sendBalloon(AppCompatActivity activity,String des,int rig) {
        createResourcesFolder();
        double rigs = Math.floor( rig/ 2) + 1;
        ActionController.getInstance().sendcleanballloon(activity,(int)rigs);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();

        cleanFileKMLs(0);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandBalloon(des,(int)rigs),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);
    }


    public void sendTourStation(LGCommand.Listener listener,String lon,String lat,String alti,String des,String name){
        cleanFileKMLs(0);
        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandTourStation(lon,lat,alti,des,name), LGCommand.CRITICAL_MESSAGE, (String result) -> {
                if (listener != null) {
                    listener.onResponse(result);
                }
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);

            LGCommand lgCommandWriteTour = new LGCommand(ActionBuildCommandUtility.buildCommandwriteStartTourFile(), LGCommand.CRITICAL_MESSAGE, (String result) -> {
                if (listener != null) {
                    listener.onResponse(result);
                }
            });
            lgConnectionManager.addCommandToLG(lgCommandWriteTour);

            LGCommand lgCommandStartTour = new LGCommand(ActionBuildCommandUtility.buildCommandStartTour(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            handler2.postDelayed(() -> lgConnectionManager.addCommandToLG(lgCommandStartTour), 1500);
        }, 1000);
    }
    public void sendBalloonRaw(AppCompatActivity activity,String des,String raw,int rig) {
        createResourcesFolder();
        double rigs = Math.floor( rig/ 2) + 1;
        ActionController.getInstance().sendcleanballloon(activity,(int)rigs);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.startConnection();
        cleanFileKMLs(0);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandBalloonRaw(des,raw,(int)rigs),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);
    }

    public void sendOribitfile(AppCompatActivity activity,String lon,String lat,String alti,String des,String name) {
        createResourcesFolder();
        String imagePath = command_orbit(activity,lon,lat,alti,des,name);
        Log.w(TAG_DEBUG, "ISS KML FILEPATH: " + imagePath);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();



        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteISSFile(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);

        startOrbit(null);
    }



    public void sendOribitStation(AppCompatActivity activity,String lon,String lat,String alti,String des,String name) {
        createResourcesFolder();
        String imagePath = command_orbit_station(activity,lon,lat,alti,des,name);
        Log.w(TAG_DEBUG, "ISS KML FILEPATH: " + imagePath);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();



        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteISSFile(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);

        startOrbit(null);
    }


    private String generateCircle(String longi,String lati,String alti){
        double centerLat = Math.toRadians(Double.parseDouble(lati));
        double centerLng = Math.toRadians(Double.parseDouble(longi));
        double diameter = 800; // diameter of circle in km
        double dist = diameter / 6371.0;

        // start generating KML

        String coordinate="";
        for (int x = 0; x <= 360; x ++)
        {
            double brng = Math.toRadians(x);
            double latitude = Math.asin(Math.sin(centerLat) * Math.cos(dist) + Math.cos(centerLat) * Math.sin(dist) * Math.cos(brng));
            double longitude = centerLng + Math.atan2(Math.sin(brng) * Math.sin(dist)* Math.cos(centerLat), Math.cos(dist) - Math.sin(centerLat)
                    * Math.sin(latitude)) ;
            coordinate +=  Math.toDegrees(longitude)+ ","+ Math.toDegrees(latitude)+" ";
        }
        return coordinate;
    }
    private String generateStyle(){
        return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>My Places.kml</name>\n"+
                "\t<Style id=\"inline1\">\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ff0000ff</color>\n" +
                "\t\t\t<width>2</width>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\t<StyleMap id=\"inline0\">\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>normal</key>\n" +
                "\t\t\t<styleUrl>#inline1</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>highlight</key>\n" +
                "\t\t\t<styleUrl>#inline</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t</StyleMap>\n" +
                "\t<Style id=\"s_ylw-pushpin\">\n" +
                "\t\t<IconStyle>\n" +
                "\t\t\t<scale>3</scale>\n" +
                "\t\t\t<Icon>\n" +
                "\t\t\t\t<href>https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/FP_Satellite_icon.svg/1200px-FP_Satellite_icon.svg.png</href>\n" +
                "\t\t\t</Icon>\n" +
                "\t\t\t<hotSpot x=\"0.5\" y=\"0.5\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "\t\t</IconStyle>\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ff0000ff</color>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\t<Style id=\"inline\">\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ff0000ff</color>\n" +
                "\t\t\t<width>2</width>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\t<Style id=\"s_ylw-pushpin_hl\">\n" +
                "\t\t<IconStyle>\n" +
                "\t\t\t<scale>3.54545</scale>\n" +
                "\t\t\t<Icon>\n" +
                "\t\t\t\t<href>"+"app/src/main/res/drawable/satellite_icon.png"+"</href>\n" +
                "\t\t\t</Icon>\n" +
                "\t\t\t<hotSpot x=\"0.5\" y=\"0.5\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "\t\t</IconStyle>\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ff0000ff</color>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\t<StyleMap id=\"m_ylw-pushpin\">\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>normal</key>\n" +
                "\t\t\t<styleUrl>#s_ylw-pushpin</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>highlight</key>\n" +
                "\t\t\t<styleUrl>#s_ylw-pushpin_hl</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t</StyleMap>\n" +
                "\n" +
                "\t\t<Style>\n" +
                "\t\t\t<ListStyle>\n" +
                "\t\t\t\t<listItemType>check</listItemType>\n" +
                "\t\t\t\t<ItemIcon>\n" +
                "\t\t\t\t\t<state>open</state>\n" +
                "\t\t\t\t\t<href>:/mysavedplaces_open.png</href>\n" +
                "\t\t\t\t</ItemIcon>\n" +
                "\t\t\t\t<ItemIcon>\n" +
                "\t\t\t\t\t<state>closed</state>\n" +
                "\t\t\t\t\t<href>:/mysavedplaces_closed.png</href>\n" +
                "\t\t\t\t</ItemIcon>\n" +
                "\t\t\t\t<bgColor>00ffffff</bgColor>\n" +
                "\t\t\t\t<maxSnippetLines>2</maxSnippetLines>\n" +
                "\t\t\t</ListStyle>\n" +
                "\t\t</Style>\n";

    }


    public String command_orbit(AppCompatActivity activity,String lon,String lat,String alti,String description,String name) {
        System.out.println(lon+" "+lat+ " "+alti);
        String kml = generateStyle()+
                "\t\t<Placemark>\n" +
                "\t\t\t<name>"+name+"</name>\n" +
                "<description>"+description+"</description>"+
                "\t\t\t<LookAt>\n" +
                "\t\t\t\t<longitude>"+lon+"</longitude>\n" +
                "\t\t\t\t<latitude>"+lat+"</latitude>\n" +
                "\t\t\t\t<altitude>500000</altitude>\n"  +
                "\t\t\t\t<gx:altitudeMode>absolute</gx:altitudeMode>\n" +
                "\t\t\t</LookAt>\n" +
                "\t\t\t<styleUrl>#s_ylw-pushpin</styleUrl>\n" +
                "\t\t\t<Point>\n" +
                "\t\t\t\t<extrude>1</extrude>\n" +
                "\t\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                "\t\t\t\t<gx:drawOrder>1</gx:drawOrder>\n" +
                "\t\t\t\t<coordinates>"+lon +","+lat +",800000</coordinates>\n" +
                "\t\t\t</Point>\n" +
                "\t\t</Placemark>\n" +
                "<Placemark>\n" +
                "<name>Circle Measure</name>\n" +
                "<styleUrl>#inline1</styleUrl>\n" +
                "<LineString>\n" +
                "<tessellate>1</tessellate>\n" +
                "<altitudeMode>absolute</altitudeMode>\n" +
                "<coordinates>" + generateCircle(lon,lat,alti) +" </coordinates>\n" +
                "</LineString>\n" +
                "</Placemark>"+
                "\t\t<gx:Tour>\n" +
                "             <name>Orbit</name>\n" +
                "             <gx:Playlist>\n" +
                command_orbit(lon,lat,alti)+
                "     </gx:Playlist>\n" +
                "    </gx:Tour>\n" +
                "</Document>\n" +
                "</kml>\n";
        System.out.println(kml);
        File file = new File(activity.getFilesDir() + "/ISS.kml");
        if (file.exists()) file.delete();
        File file1 = new File(activity.getFilesDir() + "/ISS.kml");
        if (!file1.exists()){
            try {
                byte[] buffer = kml.toString().getBytes(StandardCharsets.UTF_8);
                FileOutputStream fos = new FileOutputStream(file1);
                fos.write(buffer);
                fos.close();
                return file1.getPath();
            } catch (Exception e) {
                Log.w(TAG_DEBUG, "ERROR: " + e.getMessage());
            }
        }
        return kml;
    }
    public String generateStyleStation(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>My Places.kml</name>\n"+
               "<Style id=\"s_ylw-pushpin_hl\">\n" +
                "\t\t<IconStyle>\n" +
                "\t\t\t<scale>3.54545</scale>\n" +
                "\t\t\t<Icon>\n" +
                "\t\t\t\t<href>https://images.vexels.com/media/users/3/136801/isolated/preview/a4196f75c1ca34834f62f7a9994a3f39-satellite-antena-circle-icon.png</href>\n" +
                "\t\t\t</Icon>\n" +
                "\t\t\t<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n" +
                "\t\t</IconStyle>\n" +
                "\t\t<LabelStyle>\n" +
                "\t\t\t<scale>5</scale>\n" +
                "\t\t</LabelStyle>\n" +
                "\t</Style>\n" +
                "\t<Style id=\"s_ylw-pushpin\">\n" +
                "\t\t<IconStyle>\n" +
                "\t\t\t<scale>3</scale>\n" +
                "\t\t\t<Icon>\n" +
                "\t\t\t\t<href>https://images.vexels.com/media/users/3/136801/isolated/preview/a4196f75c1ca34834f62f7a9994a3f39-satellite-antena-circle-icon.png</href>\n" +
                "\t\t\t</Icon>\n" +
                "\t\t\t<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n" +
                "\t\t</IconStyle>\n" +
                "\t\t<LabelStyle>\n" +
                "\t\t\t<scale>5</scale>\n" +
                "\t\t</LabelStyle>\n" +
                "\t</Style>\n" +
                "\t<StyleMap id=\"m_ylw-pushpin\">\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>normal</key>\n" +
                "\t\t\t<styleUrl>#s_ylw-pushpin</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>highlight</key>\n" +
                "\t\t\t<styleUrl>#s_ylw-pushpin_hl</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t</StyleMap>\n";
    }

    public String command_orbit_station(AppCompatActivity activity,String lon,String lat,String alti,String description,String name) {
        System.out.println(lon+" "+lat+ " "+alti);
        String kml = generateStyleStation()+
                "<Placemark>\n" +
                "\t\t<name>"+name+"</name>\n" +
                "\t\t<LookAt>\n" +
                "\t\t\t<longitude>"+lat+"</longitude>\n" +
                "\t\t\t<latitude>"+lon+"</latitude>\n" +
                "\t\t\t<altitude>0</altitude>\n" +
                "\t\t\t<heading>3.665464581981053</heading>\n" +
                "\t\t\t<tilt>60</tilt>\n" +
                "\t\t\t<range>600</range>\n" +
                "\t\t\t<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\n" +
                "\t\t</LookAt>\n" +
                "\t\t<styleUrl>#m_ylw-pushpin</styleUrl>\n" +
                "\t\t<Point>\n" +
                "\t\t\t<gx:drawOrder>1</gx:drawOrder>\n" +
                "\t\t\t<coordinates>"+lat+","+lon+",0</coordinates>\n" +
                "\t\t</Point>\n" +
                "\t</Placemark>\n"+
                "\t\t<gx:Tour>\n" +
                "             <name>Orbit</name>\n" +
                "             <gx:Playlist>\n" +
                command_orbit_station(lat,lon,alti)+
                "     </gx:Playlist>\n" +
                "    </gx:Tour>\n" +
                "</Document>\n" +
                "</kml>\n";


        System.out.println(kml);
        File file = new File(activity.getFilesDir() + "/ISS.kml");
        if (file.exists()) file.delete();
        File file1 = new File(activity.getFilesDir() + "/ISS.kml");
        if (!file1.exists()){
            try {
                byte[] buffer = kml.toString().getBytes(StandardCharsets.UTF_8);
                FileOutputStream fos = new FileOutputStream(file1);
                fos.write(buffer);
                fos.close();
                return file1.getPath();
            } catch (Exception e) {
                Log.w(TAG_DEBUG, "ERROR: " + e.getMessage());
            }
        }
        return kml;
    }


    public static String command_orbit_station(String longitude,String latitude,String altitude) {
        double heading = 0.0;
        int orbit = 0;
        String command="";
        while (orbit <= 36) {
            if (heading >= 360) heading = heading - 360;
            command += "    <gx:FlyTo>\n"+
                    "    <gx:duration>1.2</gx:duration> \n"+
                    "    <gx:flyToMode>smooth</gx:flyToMode> \n"+
                    "     <LookAt> \n"+
                    "      <longitude>"+longitude +"</longitude> \n"+
                    "      <latitude>"+latitude+"</latitude> \n"+
                    "      <heading>"+heading+"</heading> \n"+
                    "      <tilt>60</tilt> \n"+
                    "      <gx:fovy>35</gx:fovy> \n"+
                    "      <range>1000</range> \n"+
                    "      <gx:altitudeMode>absolute</gx:altitudeMode> \n"+
                    "      </LookAt> \n"+
                    "    </gx:FlyTo> \n\n";
            heading = heading + 10;
            orbit++;
        }
        return command;
    }
    public static String command_orbit(String longitude,String latitude,String altitude) {
        double heading = 0.0;
        int orbit = 0;
        String command="";
        while (orbit <= 36) {
            if (heading >= 360) heading = heading - 360;
            command += "    <gx:FlyTo>\n"+
                    "    <gx:duration>1.2</gx:duration> \n"+
                    "    <gx:flyToMode>smooth</gx:flyToMode> \n"+
                    "     <LookAt> \n"+
                    "      <longitude>"+longitude +"</longitude> \n"+
                    "      <latitude>"+latitude+"</latitude> \n"+
                    "      <altitude>800000</altitude> \n"+
                    "      <heading>"+heading+"</heading> \n"+
                    "      <tilt>45</tilt> \n"+
                    "      <gx:fovy>60</gx:fovy> \n"+
                    "      <range>15000</range> \n"+
                    "      <gx:altitudeMode>absolute</gx:altitudeMode> \n"+
                    "      </LookAt> \n"+
                    "    </gx:FlyTo> \n\n";
                    heading = heading + 10;
            orbit++;
        }
        return command;
    }


    /**
     * Create the Resource folder
     */
    public void createResourcesFolder() {
        LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandCreateResourcesFolder(), LGCommand.CRITICAL_MESSAGE, (String result) -> {
        });
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);
    }


    /**
     * It cleans the kmls.txt file
     */
    public void cleanFileKMLs(int duration) {
        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCleanKMLs(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, duration);
        System.out.println("CLEAN KML");
        //cleanQuery(duration);
        exitTour();
    }

    /**
     * Exit Tour
     */
    public void exitTour(){
        //cleanFileKMLs(0);
        LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandExitTour(),
                LGCommand.CRITICAL_MESSAGE, (String result) -> {
        });
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);

        LGCommand lgCommandCleanSlaves = new LGCommand(ActionBuildCommandUtility.buildCommandCleanSlaves(),
                LGCommand.CRITICAL_MESSAGE, (String result) -> {
        });
        lgConnectionManager.addCommandToLG(lgCommandCleanSlaves);
    }

    public void cleanTour(){
        //cleanFileKMLs(0);
        LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandExitTour(),
                LGCommand.CRITICAL_MESSAGE, (String result) -> {
        });
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);
    }
}
