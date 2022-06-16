package com.Goutam.TinygsDataVisualization.create.utility.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Goutam.TinygsDataVisualization.connection.LGCommand;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionManager;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionSendFile;
import com.neosensory.tlepredictionengine.TlePredictionEngine;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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

    public void sendISSfile(AppCompatActivity activity) {
        createResourcesFolder();
        cleanFileKMLs(0);

        String imagePath = getISSFile(activity);
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
            LGCommand lgCommand = new LGCommand(ActionBuildCommandUtility.buildWriteEnxanetaFile(),
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

    private String getISSFile(AppCompatActivity activity) {
        File file = new File(activity.getFilesDir() + "/ISS.kml");
        if (!file.exists()) {
            try {
                InputStream is = activity.getAssets().open("ISS.kml");
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
        String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>Circle Measure.kml</name>\n" +
                "\t<Style id=\"inline\">\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ff0000ff</color>\n" +
                "\t\t\t<width>2</width>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\t<Style id=\"inline0\">\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ff0000ff</color>\n" +
                "\t\t\t<width>2</width>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\t<StyleMap id=\"inline1\">\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>normal</key>\n" +
                "\t\t\t<styleUrl>#inline0</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>highlight</key>\n" +
                "\t\t\t<styleUrl>#inline</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t</StyleMap>\n" +
                "\t<Placemark>\n" +
                "\t\t<name>Circle Measure</name>\n" +
                "\t\t<styleUrl>#inline1</styleUrl>\n" +
                "\t\t<LineString>\n" +
                "\t\t\t<tessellate>1</tessellate>\n" +
                "\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                "\t\t\t<coordinates>\n" +
                "\t\t\t\t35.65095188307584,-0.5446760455675255,800000 36.42633666458408,-0.2419776623658956,800000 37.22483661110317,-0.006988561012517308,800000 38.04057571646405,0.1585091033709722,800000 38.86752795898867,0.253256732746197,800000 39.69956833479145,0.2765324514434745,800000 40.5305265533961,0.2281587731670529,800000 41.35424197558719,0.1085044991544322,800000 42.16461828525588,-0.08151922023605709,800000 42.95567640592726,-0.3404685203145204,800000 43.72160429688115,-0.6663818156109156,800000 44.45680248168711,-1.056799642175712,800000 45.1559244536511,-1.50878870327858,800000 45.81391144246447,-2.01896938336347,800000 46.42602138593803,-2.583545955228301,800000 46.98785230224956,-3.198338715559865,800000 47.49536057687943,-3.858817340526312,800000 47.94487494456582,-4.560134849424141,800000 48.33310714623656,-5.29716169160183,800000 48.65716036586582,-6.064519620101012,800000 48.91453659957114,-6.856615174431078,800000 49.10314407996654,-7.667672754724778,800000 49.2213057764452,-8.491767421042331,800000 49.26776982185232,-9.322857686524083,800000 49.24172248391186,-10.15481868403774,800000 49.14280401238513,-10.9814761662918,800000 48.97112735784185,-11.79664184311212,800000 48.72729938455534,-12.59415056136494,800000 48.41244380098869,-13.36789978832608,800000 48.02822462376668,-14.1118917648313,800000 47.57686859783207,-14.82027854887347,800000 47.06118464588837,-15.4874099747953,800000 46.48457814922335,-16.10788431301222,800000 45.85105770826637,-16.67660114017349,800000 45.16523203327707,-17.18881563508265,800000 44.43229480616901,-17.64019322218984,800000 43.65799575392639,-18.02686321721574,800000 42.84859678327483,-18.34546991628098,800000 42.01081282118869,-18.59321943829945,800000 41.15173793500151,-18.767920603836,800000 40.27875829245317,-18.86801822763526,800000 39.39945447082701,-18.89261742067958,800000 38.52149643395383,-18.84149783143237,800000 37.6525350733825,-18.71511718160855,800000 36.8000944860508,-18.51460393444676,800000 35.97146910133582,-18.24173943058651,800000 35.17362938278522,-17.8989302938824,800000 34.41313916156418,-17.48917230641646,800000 33.69608678890111,-17.01600724737419,800000 33.02803132124772,-16.4834743657767,800000 32.41396397531099,-15.89605820769937,800000 31.8582842004382,-15.25863445297469,800000 31.36478898156206,-14.57641525320927,800000 30.93667344885479,-13.85489532759088,800000 30.5765405452153,-13.09979979342361,800000 30.28641738129404,-12.31703441178039,800000 30.06777596447487,-11.51263863884894,800000 29.9215561875669,-10.6927416092688,800000 29.84818926578898,-9.863520952343048,800000 29.84762017921989,-9.031164163592363,800000 29.91932807912325,-8.201832126403856,800000 30.06234402343126,-7.381624301785796,800000 30.27526579840754,-6.576545076293491,800000 30.55626994470545,-5.792470775239478,800000 30.90312142537085,-5.035116905496352,800000 31.31318164214313,-4.310005283970347,800000 31.78341571753322,-3.622430827967944,800000 32.31040010723464,-2.977427925309256,800000 32.89033168469329,-2.379736457463675,800000 33.519039442395,-1.833767709601324,800000 34.19199987979054,-1.343570557813858,800000 34.90435699604825,-0.9127984657848471,800000 35.65095188307584,-0.5446760455675255,800000 \n" +
                "\t\t\t</coordinates>\n" +
                "\t\t</LineString>\n" +
                "\t</Placemark>\n" +
                "</Document>\n" +
                "</kml>\n"+
                "' > /var/www/html/spaceport" + name.split(" ")[0] + ".kml";
        ;

        System.out.println(kml);
        System.out.println(name.split(" ")[0]);

        LGCommand lgCommand = new LGCommand(kml, LGCommand.CRITICAL_MESSAGE,(String result) -> {
        });
        System.out.println(lgCommand);
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);
        System.out.println("Clearing lg connection manager");
//        startOrbit(null);

//        writeSpaceport(name.split(" ")[0]);
    }

    public void sendSpaceportFile(AppCompatActivity activity, String description, String name, double[] lla_coords, String imagePath) {

        createResourcesFolder();
        cleanFileKMLs(0);
        /* Inserts the orbit part as a tour */
        String orbit = ActionBuildCommandUtility.buildCommandInsertOrbit(lla_coords, 1000);

        String kml = "echo '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>" + name + "</name>\n" +
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
                "\t<Style id=\"s_ylw-pushpin_hl\">\n" +
                "\t\t<IconStyle>\n" +
                "\t\t\t<scale>1.4</scale>\n" +
                "\t\t\t<Icon>\n" +
                "\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/track.png</href>\n" +
                "\t\t\t</Icon>\n" +
                "\t\t\t<hotSpot x=\"32\" y=\"32\" xunits=\"pixels\" yunits=\"pixels\"/>\n" +
                "\t\t</IconStyle>\n" +
                "\t\t<ListStyle>\n" +
                "\t\t</ListStyle>\n" +
                "\t</Style>\n" +
                "\t<Style id=\"s_ylw-pushpin\">\n" +
                "\t\t<IconStyle>\n" +
                "\t\t\t<scale>1.2</scale>\n" +
                "\t\t\t<Icon>\n" +
                "\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/track.png</href>\n" +
                "\t\t\t</Icon>\n" +
                "\t\t\t<hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n" +
                "\t\t</IconStyle>\n" +
                "\t\t<ListStyle>\n" +
                "\t\t</ListStyle>\n" +
                "\t</Style>\n" +
                "\t<Placemark>\n" +
                "\t\t<name>" + name + "</name>\n" +
                "\t\t<description>\n" + description + "</description>\n" +
                "\t\t<LookAt>\n" +
                "\t\t\t<longitude>" + lla_coords[1] + "</longitude>\n" +
                "\t\t\t<latitude>" + lla_coords[0] + "</latitude>\n" +
                "\t\t\t<altitude>" + lla_coords[2] + "</altitude>\n" +
                "\t\t\t<heading>-0.001127248273239458</heading>\n" +
                "\t\t\t<tilt>5.841915356537878</tilt>\n" +
                "\t\t\t<range>4793.403883588249</range>\n" +
                "\t\t\t<gx:altitudeMode>relativeToGround</gx:altitudeMode>\n" +
                "\t\t</LookAt>\n" +
                "\t\t<styleUrl>#m_ylw-pushpin</styleUrl>\n" +
                "\t\t<gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                "\t\t<Point>\n" +
                "\t\t\t<gx:drawOrder>1</gx:drawOrder>\n" +
                "\t\t\t<coordinates>" + lla_coords[0] + "," + lla_coords[1] + "," + lla_coords[2] + "</coordinates>\n" +
                "\t\t</Point>\n" +
                "\t</Placemark>\n" + orbit +
                "</Document>\n" +
                "</kml>"+
                "' > /var/www/html/spaceport" + name.split(" ")[0] + ".kml";

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
