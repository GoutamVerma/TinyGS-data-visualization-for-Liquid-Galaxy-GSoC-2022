package com.Goutam.TinygsDataVisualization.create.utility.model;

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


    /**
     * Paint a balloon with the logos
     */
    public void sendBalloonWithLogos(AppCompatActivity activity) {
        createResourcesFolder();

        String imagePath = getLogosFile(activity);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();

        cleanFileKMLs(0);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandBalloonWithLogos(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
            }, 2000);
    }

    public void sendStarlinkfile(AppCompatActivity activity) {
        createResourcesFolder();
        cleanFileKMLs(0);

        String imagePath = getStarlinkFile(activity);
        Log.w(TAG_DEBUG, "STARLINK KML FILEPATH: " + imagePath);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();


        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteStarlinkFile(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);

        startOrbit(null);
    }

    public void sendISSfile(AppCompatActivity activity,String lon,String lat,String alti,String des,String name) {
        createResourcesFolder();
        cleanFileKMLs(0);

        String imagePath = getISSFile(activity,lon,lat,alti,des,name);
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

    public void sendTour(LGCommand.Listener listener,String lon,String lat,String alti,String des,String name){
        cleanFileKMLs(0);
        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandTour(lon,lat,alti,des,name), LGCommand.CRITICAL_MESSAGE, (String result) -> {
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

    public void sendOribitfile(AppCompatActivity activity,String lon,String lat,String alti,String des,String name) {
        createResourcesFolder();
        cleanFileKMLs(0);

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

    private String getLogosFile(AppCompatActivity activity) {
        File file = new File(activity.getCacheDir() + "/logos.png");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("logos.png");
                int size = is.available();
                Log.w(TAG_DEBUG, "SIZE: " + size);
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buffer);
                fos.close();

                return file.getPath();
            } catch (Exception e) {
                Log.w(TAG_DEBUG, "ERROR: " + e.getMessage());
            }
        }
        return file.getPath();
    }


    private String getStarlinkFile(AppCompatActivity activity) {
        File file = new File(activity.getFilesDir() + "/Starlink.kml");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("Starlink.kml");
                int size = is.available();
                Log.w(TAG_DEBUG, "GET Starlink KML SIZE: " + size);
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buffer);
                fos.close();

                return file.getPath();
            } catch (Exception e) {
                Log.w(TAG_DEBUG, "ERROR: " + e.getMessage());
            }
        }
        return file.getPath();
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
            coordinate +=  Math.toDegrees(longitude)+ ","+ Math.toDegrees(latitude)+","+"800000 ";
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

    private String getISSFile(AppCompatActivity activity,String lon,String lat,String alti,String description,String name) {
        System.out.println(lon+" "+lat+ " "+alti);
        String kml = generateStyle()+
                "\t\t<Placemark>\n" +
                "\t\t\t<name>"+name+"</name>\n" +
                "<description>"+description+"</description>"+
                "\t\t\t<LookAt>\n" +
                "\t\t\t\t<longitude>"+lon+"</longitude>\n" +
                "\t\t\t\t<latitude>"+lat+"</latitude>\n" +
                "\t\t\t\t<altitude>0</altitude>\n" +
                "\t\t\t\t<heading>-5.029091935818897</heading>\n" +
                "\t\t\t\t<tilt>0</tilt>\n" +
                "\t\t\t\t<range>4880964.396775676</range>\n" +
                "\t\t\t\t<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\n" +
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
                "\t\t<name>Circle Measure</name>\n" +
                "\t\t<styleUrl>#inline1</styleUrl>\n" +
                "\t\t<LineString>\n" +
                "\t\t\t<tessellate>1</tessellate>\n" +
                "\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                "\t\t\t<coordinates>\n" +
                "\t\t\t"+generateCircle(lon,lat,alti) +" </coordinates>\n" +
                "\t\t</LineString>\n" +
                "\t</Placemark>"+
                "\t\t<gx:Tour>\n" +
                "             <name>Orbit</name>\n" +
                "             <gx:Playlist>\n" +

                "        <gx:FlyTo>\n" +
                "        <gx:duration>2.2</gx:duration>\n" +
                "        <gx:flyToMode>smooth</gx:flyToMode>\n" +
                "         \t\t\t<LookAt>\n" +
                "                   \t\t\t\t<longitude>"+lon+"</longitude>\n" +
                "                   \t\t\t\t<latitude>"+lat+"</latitude>\n" +
                "                   \t\t\t\t<altitude>800000</altitude>\n" +
                "                   \t\t\t\t<heading>0</heading>\n" +
                "                   \t\t\t\t <gx:fovy>0</gx:fovy>\n" +
                "                   \t\t\t\t<tilt>0</tilt>\n" +
                "                   \t\t\t\t<range>0</range>\n" +
                "                   \t\t\t\t<gx:altitudeMode>absolute</gx:altitudeMode>\n" +
                "                   \t\t\t</LookAt>\n" +
                "                 </gx:FlyTo>\n" +

                "        <gx:FlyTo>\n" +
                "        <gx:duration>3.2</gx:duration>\n" +
                "        <gx:flyToMode>smooth</gx:flyToMode>\n" +
                "         \t\t\t<LookAt>\n" +
                "                   \t\t\t\t<longitude>"+lon+"</longitude>\n" +
                "                   \t\t\t\t<latitude>"+lat+"</latitude>\n" +
                "                   \t\t\t\t<altitude>0</altitude>\n" +
                "                   \t\t\t\t<heading>0</heading>\n" +
                "                   \t\t\t\t <gx:fovy>0</gx:fovy>\n" +
                "                   \t\t\t\t<tilt>20</tilt>\n" +
                "                   \t\t\t\t<range>4880964.396775676</range>\n" +
                "                   \t\t\t\t<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\n" +
                "                   \t\t\t</LookAt>\n" +
                "                 </gx:FlyTo>\n" +
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

    public String command_orbit(AppCompatActivity activity,String lon,String lat,String alti,String description,String name) {
        System.out.println(lon+" "+lat+ " "+alti);
        String kml = generateStyle()+
                "\t\t<Placemark>\n" +
                "\t\t\t<name>"+name+"</name>\n" +
                "<description>"+description+"</description>"+
                "\t\t\t<LookAt>\n" +
                "\t\t\t\t<longitude>"+lon+"</longitude>\n" +
                "\t\t\t\t<latitude>"+lat+"</latitude>\n" +
                "\t\t\t\t<altitude>500000</altitude>\n" +
                "\t\t\t\t<heading>-5.029091935818897</heading>\n" +
                "\t\t\t\t<tilt>0</tilt>\n" +
                "\t\t\t\t<range>4880964.396775676</range>\n" +
                "\t\t\t\t<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\n" +
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
                "\t\t<name>Circle Measure</name>\n" +
                "\t\t<styleUrl>#inline1</styleUrl>\n" +
                "\t\t<LineString>\n" +
                "\t\t\t<tessellate>1</tessellate>\n" +
                "\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                "\t\t\t<coordinates>\n" +
                "\t\t\t"+generateCircle(lon,lat,alti) +" </coordinates>\n" +
                "\t\t</LineString>\n" +
                "\t</Placemark>"+
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
                    "      <tilt>60</tilt> \n"+
                    "      <gx:fovy>35</gx:fovy> \n"+
                    "      <range>100</range> \n"+
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
     * It cleans the kmls.txt file
     */
    public void cleanQuery(int duration) {
        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCleanQuery(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, duration);
    }

    public void writeLiveSCN(String scn) {
        String command = "echo 'http://localhost:81/liveSCN" + scn + ".kml' > " +
                "/var/www/html/" +
                "kmls.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        LGCommand lgCommand = new LGCommand(command,
                LGCommand.CRITICAL_MESSAGE, (String result) -> {
        });
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);
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
    };
}
