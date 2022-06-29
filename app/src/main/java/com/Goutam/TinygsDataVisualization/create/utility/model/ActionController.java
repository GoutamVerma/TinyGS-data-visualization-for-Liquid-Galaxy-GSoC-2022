package com.Goutam.TinygsDataVisualization.create.utility.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.Goutam.TinygsDataVisualization.connection.LGCommand;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionManager;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionSendFile;
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

    public void sendEnxanetaFile(AppCompatActivity activity) {
        createResourcesFolder();
        cleanFileKMLs(0);
        String imagePath = getEnxanetaFile(activity);
        Log.w(TAG_DEBUG, "Enxaneta KML FILEPATH: " + imagePath);
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

    public void sendStarlinkConstFile(AppCompatActivity activity) {
        createResourcesFolder();
        cleanFileKMLs(0);
        String imagePath = getStarlinkConstFile(activity);
        Log.w(TAG_DEBUG, "StarlinkConst KML FILEPATH: " + imagePath);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();


        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteStarlinkConstFile(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);
        startOrbit(null);
    }
    public void sendIridiumConstFile(AppCompatActivity activity) {
        createResourcesFolder();
        cleanFileKMLs(0);
        String imagePath = getIridiumConstFile(activity);
        Log.w(TAG_DEBUG, "IridiumConst KML FILEPATH: " + imagePath);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteIridiumConstFile(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);
        startOrbit(null);
    }

    public void sendRocketTraj(AppCompatActivity activity) {
        createResourcesFolder();
        cleanFileKMLs(0);
        double[] lla_coords = {0,0,0};
        String imagePath = getRocketFile(activity);
        Log.w(TAG_DEBUG, "Rocket KML FILEPATH: " + imagePath);
        LGConnectionSendFile lgConnectionSendFile = LGConnectionSendFile.getInstance();
        lgConnectionSendFile.addPath(imagePath);
        lgConnectionSendFile.startConnection();

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteRocketFile(),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 2000);

        handler.postDelayed(() -> {
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildCommandInsertFlyTo2(lla_coords),
                    LGCommand.CRITICAL_MESSAGE, (String result) -> {
            });
            LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
            lgConnectionManager.startConnection();
            lgConnectionManager.addCommandToLG(lgCommand);
        }, 4000);

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
        return file1.getPath();
    }


    private String getEnxanetaFile(AppCompatActivity activity) {
        File file = new File(activity.getFilesDir() + "/Enxaneta.kml");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("Enxaneta.kml");
                int size = is.available();
                Log.w(TAG_DEBUG, "GET Enxaneta KML SIZE: " + size);
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
    private String getStarlinkConstFile(AppCompatActivity activity) {
        File file = new File(activity.getFilesDir() + "/StarlinkConst.kml");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("StarlinkConst.kml");
                int size = is.available();
                Log.w(TAG_DEBUG, "GET ISS KML SIZE: " + size);
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
    private String getIridiumConstFile(AppCompatActivity activity) {
        File file = new File(activity.getFilesDir() + "/IridiumConst.kml");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("IridiumConst.kml");
                int size = is.available();
                Log.w(TAG_DEBUG, "GET ISS KML SIZE: " + size);
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

    private String getRocketFile(AppCompatActivity activity) {
        File file = new File(activity.getFilesDir() + "/rocket_simulation.kml");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("rocket_simulation.kml");
                int size = is.available();
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

    public void sendLiveSCN(AppCompatActivity activity, String scn) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Runnable task1 = () -> {
            try {
                String url = "https://celestrak.com/NORAD/elements/gp.php?CATNR=" + scn + "&FORMAT=TLE";

                String completeTLE[] = Jsoup.connect(url).ignoreContentType(true).execute().body().split("\\n");
                Date date = new Date();
                double[] lla_coords = TlePredictionEngine.getSatellitePosition(completeTLE[1], completeTLE[2], true, date);

                lla_coords[2] = lla_coords[2]*1000;

                /* Inserts the orbit part as a tour */
                String orbit = ActionBuildCommandUtility.buildCommandInsertOrbit(lla_coords, 100000);

                String kml = "echo '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n" +
                        "    <Document id=\"1\">\n" +
                        "        <Style id=\"4\">\n" +
                        "            <LineStyle id=\"5\">\n" +
                        "                <color>ff0000ff</color>\n" +
                        "                <colorMode>normal</colorMode>\n" +
                        "            </LineStyle>\n" +
                        "            <BalloonStyle>\n" +
                        "                <bgColor>ffffffff</bgColor>\n" +
                        "                <textColor>ffff0000</textColor>\n" +
                        "                <displayMode>default</displayMode>\n" +
                        "            </BalloonStyle>\n" +
                        "        </Style>\n" +
                        "        <Placemark id=\"3\">\n" +
                        "            <name>" + completeTLE[0] + "</name>\n" +
                        "            <description> Satellite Catalog Number: " + scn + " with current coordinates:\nlongitude: " + lla_coords[1] + "\nlatitude: " + lla_coords[0] + "\nheight: " + lla_coords[2] + "</description>\n" +
                        "            <styleUrl>#4</styleUrl>\n" +
                        "            <gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                        "            <Point id=\"2\">\n" +
                        "                <coordinates>" + lla_coords[1] + "," + lla_coords[0] + "," + lla_coords[2] + "</coordinates>\n" +
                        "                <extrude>1</extrude>\n" +
                        "                <altitudeMode>relativeToGround</altitudeMode>\n" +
                        "            </Point>\n" +
                        "        </Placemark>\n" + orbit +
                        "    </Document>\n" +
                        "</kml>" +
                        "' > /var/www/html/liveSCN" + scn + ".kml";


                LGCommand lgCommand = new LGCommand(kml, LGCommand.CRITICAL_MESSAGE,(String result) -> {
                });
                LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
                lgConnectionManager.startConnection();
                lgConnectionManager.addCommandToLG(lgCommand);

                writeLiveSCN(scn);

                startOrbit(null);

            } catch (Exception e) {
                System.out.println("ERROR" + e.toString());
            }
        };
        executorService.submit(task1);
        executorService.shutdown();
    }

    public void sendLiveGroup(AppCompatActivity activity, String group_name) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Runnable task1 = () -> {
            try {
                String url = "https://celestrak.com/NORAD/elements/gp.php?GROUP=" + group_name + "&FORMAT=TLE";
                System.out.println(url);
                String lines[] = Jsoup.connect(url).ignoreContentType(true).execute().body().split("\\n");
                System.out.println("Num of lines: " + lines.length);
                int i = 0;
                int n = 0;
                int number_of_satellites = (lines.length / 3); //Grouped in three lines each satellite
                System.out.println("Num of satellites: " + number_of_satellites);

                String[][] satellites = new String[number_of_satellites][3];
                Date date = new Date();
                StringBuilder placemarks = new StringBuilder();
                StringBuilder tour = new StringBuilder();


                while (i < 25) {
                    satellites[i][0] = lines[n+0];
                    satellites[i][1] = lines[n+1];
                    satellites[i][2] = lines[n+2];

                    double[] lla_coords = TlePredictionEngine.getSatellitePosition(satellites[i][1], satellites[i][2], true, date);
                    String placemark = "        <Placemark id=\"3\">\n" +
                            "            <name>" + satellites[i][0] + "</name>\n" +
                            "            <description> Current coordinates:\nlongitude: " + lla_coords[1] + "\nlatitude: " + lla_coords[0] + "\nheight: " + lla_coords[2] + "</description>\n" +
                            "            <styleUrl>#4</styleUrl>\n" +
                            "            <gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                            "            <Point id=\"2\">\n" +
                            "                <coordinates>" + lla_coords[1] + "," + lla_coords[0] + "," + lla_coords[2]*1000 + "</coordinates>\n" +
                            "                <extrude>1</extrude>\n" +
                            "                <width>10</width>" +
                            "                <altitudeMode>relativeToGround</altitudeMode>\n" +
                            "            </Point>\n" +
                            "        </Placemark>\n";

                    String flyto = ActionBuildCommandUtility.buildCommandInsertFlyTo(lla_coords);

                    placemarks.append(placemark);
                    tour.append(flyto);
                    System.out.println(placemark);
                    n = n + 3;
                    i++;
                }


                System.out.println("TOUR: " + tour);
                System.out.println("Satellite length: "  + satellites.length);
                System.out.println("SATELLITE TLE 0: "  + satellites[1][0]);
                System.out.println("SATELLITE TLE 1: "  + satellites[1][1]);
                System.out.println("SATELLITE TLE 2: "  + satellites[1][2]);

                /* Inserts the orbit part as a tour */
                String orbit = "";


                String kml = "echo '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n" +
                        "    <Document id=\"1\">\n" +
                        "        <Style id=\"4\">\n" +
                        "            <LineStyle id=\"5\">\n" +
                        "                <color>ff0000ff</color>\n" +
                        "                <colorMode>normal</colorMode>\n" +
                        "            </LineStyle>\n" +
                        "            <BalloonStyle>\n" +
                        "                <bgColor>ffffffff</bgColor>\n" +
                        "                <textColor>ffff0000</textColor>\n" +
                        "                <displayMode>default</displayMode>\n" +
                        "            </BalloonStyle>\n" +
                        "        </Style>\n" + placemarks + tour +
                        "    </Document>\n" +
                        "</kml>" +
                        "' > /var/www/html/liveSCN" + group_name + ".kml";

                Log.w(TAG_DEBUG, "DEF COMMAND: " + kml.toString());
                LGCommand lgCommand = new LGCommand(kml, LGCommand.CRITICAL_MESSAGE,(String result) -> {
                });
                LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
                lgConnectionManager.startConnection();
                lgConnectionManager.addCommandToLG(lgCommand);

                startOrbit(null);

                writeLiveSCN(group_name);

            } catch (Exception e) {
                System.out.println("ERROR" + e.toString());
            }
        };
        executorService.submit(task1);
        executorService.shutdown();
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

    public void sendPacket(AppCompatActivity activity, String description, String name, double[] lla_coords, String imagePath){
        createResourcesFolder();
        cleanFileKMLs(0);
        String kml = "";

        System.out.println(kml);
        System.out.println(name.split(" ")[0]);

        LGCommand lgCommand = new LGCommand(kml, LGCommand.CRITICAL_MESSAGE,(String result) -> {
        });
        System.out.println(lgCommand);
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);

        startOrbit(null);

        writeSpaceport(name.split(" ")[0]);
    }

    public void sendSpaceportFile(AppCompatActivity activity, String description, String name, double[] lla_coords, String imagePath) {
//
//        createResourcesFolder();
//        cleanFileKMLs(0);
//        /* Inserts the orbit part as a tour */
//        String orbit = ActionBuildCommandUtility.buildCommandInsertOrbit(lla_coords, 1000);
//
//        StringBuilder kml=null;
//        kml.append("echo '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
//                "<Document>\n" +
//                "\t<name>" + name + "</name>\n" +
//                "\t<StyleMap id=\"m_ylw-pushpin\">\n" +
//                "\t\t<Pair>\n" +
//                "\t\t\t<key>normal</key>\n" +
//                "\t\t\t<styleUrl>#s_ylw-pushpin</styleUrl>\n" +
//                "\t\t</Pair>\n" +
//                "\t\t<Pair>\n" +
//                "\t\t\t<key>highlight</key>\n" +
//                "\t\t\t<styleUrl>#s_ylw-pushpin_hl</styleUrl>\n" +
//                "\t\t</Pair>\n" +
//                "\t</StyleMap>\n" +
//                "\t<Style id=\"s_ylw-pushpin_hl\">\n" +
//                "\t\t<IconStyle>\n" +
//                "\t\t\t<scale>1.4</scale>\n" +
//                "\t\t\t<Icon>\n" +
//                "\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/track.png</href>\n" +
//                "\t\t\t</Icon>\n" +
//                "\t\t\t<hotSpot x=\"32\" y=\"32\" xunits=\"pixels\" yunits=\"pixels\"/>\n" +
//                "\t\t</IconStyle>\n" +
//                "\t\t<ListStyle>\n" +
//                "\t\t</ListStyle>\n" +
//                "\t</Style>\n" +
//                "\t<Style id=\"s_ylw-pushpin\">\n" +
//                "\t\t<IconStyle>\n" +
//                "\t\t\t<scale>1.2</scale>\n" +
//                "\t\t\t<Icon>\n" +
//                "\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/track.png</href>\n" +
//                "\t\t\t</Icon>\n" +
//                "\t\t\t<hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n" +
//                "\t\t</IconStyle>\n" +
//                "\t\t<ListStyle>\n" +
//                "\t\t</ListStyle>\n" +
//                "\t</Style>\n" +
//                "\t<Placemark>\n" +
//                "\t\t<name>" + name + "</name>\n" +
//                "\t\t<description>\n" + description + "</description>\n" +
//                "\t\t<LookAt>\n" +
//                "\t\t\t<longitude>" + lla_coords[1] + "</longitude>\n" +
//                "\t\t\t<latitude>" + lla_coords[0] + "</latitude>\n" +
//                "\t\t\t<altitude>" + lla_coords[2] + "</altitude>\n" +
//                "\t\t\t<heading>-0.001127248273239458</heading>\n" +
//                "\t\t\t<tilt>5.841915356537878</tilt>\n" +
//                "\t\t\t<range>4793.403883588249</range>\n" +
//                "\t\t\t<gx:altitudeMode>relativeToGround</gx:altitudeMode>\n" +
//                "\t\t</LookAt>\n" +
//                "\t\t<styleUrl>#m_ylw-pushpin</styleUrl>\n" +
//                "\t\t<gx:balloonVisibility>1</gx:balloonVisibility>\n" +
//                "\t\t<Point>\n" +
//                "\t\t\t<gx:drawOrder>1</gx:drawOrder>\n" +
//                "\t\t\t<coordinates>" + lla_coords[0] + "," + lla_coords[1] + "," + lla_coords[2] + "</coordinates>\n" +
//                "\t\t</Point>\n" +
//                "\t</Placemark>\n" + orbit +
//                "</Document>\n" +
//                "</kml>"+
//                "' > /var/www/html/spaceport" + name.split(" ")[0] + ".kml");
//
//
//        System.out.println(kml);
//        System.out.println(name.split(" ")[0]);
//
//        LGCommand lgCommand = new LGCommand(kml, LGCommand.CRITICAL_MESSAGE,(String result) -> {
//        });
//        System.out.println(lgCommand);
//        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
//        lgConnectionManager.startConnection();
//        lgConnectionManager.addCommandToLG(lgCommand);
//
//        startOrbit(null);
//
//        writeSpaceport(name.split(" ")[0]);
    }

    public void writeSpaceport(String name) {
        String command = "echo 'http://localhost:81/spaceport" + name + ".kml' > " +
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
