package com.Goutam.TinygsDataVisualization.create.utility.model;


import android.util.Log;

import com.Goutam.TinygsDataVisualization.create.utility.model.balloon.Balloon;
import com.Goutam.TinygsDataVisualization.create.utility.model.movement.Movement;
import com.Goutam.TinygsDataVisualization.create.utility.model.poi.POI;
import com.Goutam.TinygsDataVisualization.create.utility.model.poi.POICamera;
import com.Goutam.TinygsDataVisualization.create.utility.model.poi.POILocation;
import com.Goutam.TinygsDataVisualization.create.utility.model.shape.Point;
import com.Goutam.TinygsDataVisualization.create.utility.model.shape.Shape;

import java.util.List;

/**
 * This class is in charge of creating the commands that are going to be send to the liquid galaxy
 */
public class ActionBuildCommandUtility {

    private static final String TAG_DEBUG = "ActionBuildCommandUtility";

    private static String BASE_PATH = "/var/www/html/";
    public static String RESOURCES_FOLDER_PATH = BASE_PATH + "resources/";

    /**
     * @return Command to create the resources fule
     */
    static String buildCommandCreateResourcesFolder() {
        return "mkdir -p " + RESOURCES_FOLDER_PATH;
    }

    public static String buildCommandwriteStartTourFile(){
        String command = "echo \"http://lg1:81/Tour.kml\"  > " +
                BASE_PATH +
                "kmls.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }

    public static String buildCommandStartTour() {
        String command = "echo \"playtour=TestTour\" > /tmp/query.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }

    public static String buildCommandTour(String lon,String lat,String alti,String des,String name){
        String startCommand = "echo '" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\"\n" +
                " xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n" +
                "<Document>\n" +
                "  <name>Tour</name>\n" +
                "  <open>1</open>\n\n" +
                "  <gx:Tour>\n" +
                "    <name>TestTour</name>\n" +
                "    <gx:Playlist>\n\n";

        //Build the tour
        StringBuilder folderBalloonShapes = new StringBuilder();
        folderBalloonShapes.append("  <Folder>\n" +
                        "   <name>Points and Shapes</name>\n\n")
                .append("   <Style id=\"linestyleExample\">\n" +
                        "    <LineStyle>\n" +
                        "    <color>501400FF</color>\n" +
                        "    <width>100</width>\n" +
                        "    <gx:labelVisibility>1</gx:labelVisibility>\n" +
                        "    </LineStyle>\n" +
                        "   </Style>\n\n")
                .append(stylekml());

        String middleCommand = buildTour(folderBalloonShapes,lon,lat,alti,des,name);
        folderBalloonShapes.append("  </Folder>\n");
        folderBalloonShapes.append("</Document>\n" + "</kml> ' > ").append(BASE_PATH).append("Tour.kml");
        Log.w(TAG_DEBUG, "FOLDER COMMAND: " + folderBalloonShapes.toString());
        String endCommand = "    </gx:Playlist>\n" +
                "  </gx:Tour>\n\n";
        Log.w(TAG_DEBUG, "FINAL COMMAND: " + startCommand + middleCommand + folderBalloonShapes.toString() + endCommand);
        return startCommand + middleCommand + endCommand + folderBalloonShapes.toString();
    }

    private static String stylekml(){
        return   "\t<Style id=\"inline1\">\n" +
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

    private static String buildTour(StringBuilder folderBalloonShapes,String lon,String lat,String alti,String des,String name) {
        StringBuilder command = new StringBuilder();
        Action action;
        command.append(POICommand(lon,lat,alti));
        command.append(BalloonCommand(folderBalloonShapes,name,des));
        command.append(BalloonCommand(folderBalloonShapes,lon,lat,alti,des,name));

        return command.toString();
    }
    private static String BalloonCommand(StringBuilder folderBalloonShapes,String lon,String lat,String alti,String des,String name) {

        String animate = "    <gx:AnimatedUpdate>\n" +
                "    <gx:duration>0</gx:duration>\n" +
                "     <Update>\n" +
                "      <targetHref/>\n" +
                "      <Change>\n" +
                "       <Placemark targetId=\"" +  "ballon1"  + "\">\n" +
                "        <gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                "       </Placemark>\n" +
                "      </Change>\n" +
                "     </Update>\n" +
                "    </gx:AnimatedUpdate>\n\n";

        String startCommand = "    <Placemark id=\"" + "ballon1" + "\">\n" +
                "     <name>" + name + "</name>\n" +
                "     <description>";
        String description = "";
        description = des;
        String endCommand =
                "      </description>\n" +
                        "      <styleUrl>#s_ylw-pushpin</styleUrl>\n" +
                        "      <Point>\n" +
                        "      <extrude>1</extrude>\n" +
                        "      <gx:drawOrder>1</gx:drawOrder>\n" +
                        "      <coordinates>" + lon + "," + lat +"," + "800000" + "</coordinates>\n"+
                        "      <gx:altitudeMode>" + "absolute" + "</gx:altitudeMode>\n" +
                        "      </Point>\n" +
                        "    </Placemark>\n\n"+
                        "<Placemark>\n" +
                        "<name>Circle Measure</name>\n" +
                        "<styleUrl>#inline1</styleUrl>\n" +
                        "<LineString>\n" +
                        "<tessellate>1</tessellate>\n" +
                        "<altitudeMode>absolute</altitudeMode>\n" +
                        "<coordinates>" + generateCircle(lon,lat,alti) +" </coordinates>\n" +
                        "</LineString>\n" +
                        "</Placemark>";

        folderBalloonShapes.append(startCommand + description + endCommand);
        Log.w(TAG_DEBUG, "BALLOON: " + folderBalloonShapes);
        String animateClose = "    <gx:AnimatedUpdate>\n" +
                "    <gx:duration>0</gx:duration>\n" +
                "     <Update>\n" +
                "      <targetHref/>\n" +
                "      <Change>\n" +
                "       <Placemark targetId=\"" +  "ballon1"  + "\">\n" +
                "        <gx:balloonVisibility>0</gx:balloonVisibility>\n" +
                "       </Placemark>\n" +
                "      </Change>\n" +
                "     </Update>\n" +
                "    </gx:AnimatedUpdate>\n\n";
        String wait = "4";
        return animate + wait + animateClose;
    }

    private static String BalloonCommand(StringBuilder folderBalloonShapes,String name,String des) {
        String TEST_PLACE_MARK_ID = "balloon" + "32";

        String animate = "    <gx:AnimatedUpdate>\n" +
                "    <gx:duration>0</gx:duration>\n" +
                "     <Update>\n" +
                "      <targetHref/>\n" +
                "      <Change>\n" +
                "       <Placemark targetId=\"" +  TEST_PLACE_MARK_ID  + "\">\n" +
                "        <gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                "       </Placemark>\n" +
                "      </Change>\n" +
                "     </Update>\n" +
                "    </gx:AnimatedUpdate>\n\n";

        String startCommand = "    <Placemark id=\"" + TEST_PLACE_MARK_ID + "\">\n" +
                "     <name>" + name + "</name>\n" +
                "     <description>\n" +
                "      <![CDATA[\n" +
                "      <head>\n" +
                "      <!-- Required meta tags -->\n" +
                "      <meta charset=\"UTF-8\">\n" +
                "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "\n" +
                "      <!-- Bootstrap CSS -->\n" +
                "      <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "      </head>\n" +
                "      <body>\n" +
                "       <div class=\"p-lg-5\" align=\"center\">\n" +
                "\n";
        String description = "";
            description = "       <h5>" + des + "</h5>\n" +
                    "       <br>\n";

        String endCommand = "       </div>\n    <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                "       <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n" +
                "       <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>\n" +
                "       </body>\n" +
                "       ]]>\n" +
                "      </description>\n" +
                "      <Point>\n" +
                "       <coordinates>" + "32.2563256" + "," + "32.2563256" + "</coordinates>\n" +
                "      </Point>\n" +
                "    </Placemark>\n\n";
        String wait = commandWait(50);
        folderBalloonShapes.append(startCommand + description + endCommand);
        Log.w(TAG_DEBUG, "BALLOON: " + folderBalloonShapes);
        String animateClose = "    <gx:AnimatedUpdate>\n" +
                "    <gx:duration>0</gx:duration>\n" +
                "     <Update>\n" +
                "      <targetHref/>\n" +
                "      <Change>\n" +
                "       <Placemark targetId=\"" +  TEST_PLACE_MARK_ID  + "\">\n" +
                "        <gx:balloonVisibility>0</gx:balloonVisibility>\n" +
                "       </Placemark>\n" +
                "      </Change>\n" +
                "     </Update>\n" +
                "    </gx:AnimatedUpdate>\n\n";
        return animate + wait + animateClose;
    }

    private static String generateCircle(String longi, String lati, String alti){
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

    private static String POICommand(String lon,String lat,String alti) {
        String command =  "     <gx:FlyTo>\n" +
                "      <gx:duration>" + "10" + "</gx:duration>\n" +
                "      <gx:flyToMode>bounce</gx:flyToMode>\n" +
                "      <LookAt>\n" +
                "       <longitude>" + lon + "</longitude>\n" +
                "       <latitude>" + lat + "</latitude>\n" +
                "       <altitude>" + "800000" + "</altitude>\n" +
                "       <heading>" + "35.236" + "</heading>\n" +
                "       <tilt>" + "60" + "</tilt>\n" +
                "       <range>" + "56" + "</range>\n" +
                "       <gx:altitudeMode>" + "absolute" + "</gx:altitudeMode>\n" +
                "     </LookAt>\n" +
                "    </gx:FlyTo>\n\n";
        Log.w(TAG_DEBUG, "POI COMMAND: " + command);
        return  command;
    }

    /**
     * Build the command to paint a balloon in Liquid Galaxy
     * @return String with command
     */
    public static String buildCommandBalloonWithLogos() {
        String startCommand =  "echo '" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\"\n" +
                " xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n" +
                "\n" +
                "<Document>\n" +
                "  <name>A tour and some features</name>\n" +
                "  <open>1</open>\n" +
                "\n" +
                "  <gx:Tour>\n" +
                "    <name>Play me!</name>\n" +
                "    <gx:Playlist>\n" +
                "\n" +
                "      <gx:FlyTo>\n" +
                "        <gx:duration>5.0</gx:duration>\n" +
                "        <!-- bounce is the default flyToMode -->\n" +
                "        <Camera>\n" +
                "          <longitude>170.157</longitude>\n" +
                "          <latitude>-43.671</latitude>\n" +
                "          <altitude>9700</altitude>\n" +
                "          <heading>-6.333</heading>\n" +
                "          <tilt>33.5</tilt>\n" +
                "        </Camera>\n" +
                "      </gx:FlyTo>\n" +
                "\n" +
                "      <gx:Wait>\n" +
                "        <gx:duration>1.0</gx:duration>\n" +
                "      </gx:Wait>\n" +
                "\n" +
                "      <gx:FlyTo>\n" +
                "        <gx:duration>6.0</gx:duration>\n" +
                "        <Camera>\n" +
                "          <longitude>174.063</longitude>\n" +
                "          <latitude>-39.663</latitude>\n" +
                "          <altitude>18275</altitude>\n" +
                "          <heading>-4.921</heading>\n" +
                "          <tilt>65</tilt>\n" +
                "          <altitudeMode>absolute</altitudeMode>\n" +
                "        </Camera>\n" +
                "      </gx:FlyTo>\n" +
                "\n" +
                "      <gx:FlyTo>\n" +
                "        <gx:duration>3.0</gx:duration>\n" +
                "        <gx:flyToMode>smooth</gx:flyToMode>\n" +
                "        <LookAt>\n" +
                "          <longitude>174.007</longitude>\n" +
                "          <latitude>-39.279</latitude>\n" +
                "          <altitude>0</altitude>\n" +
                "          <heading>112.817</heading>\n" +
                "          <tilt>68.065</tilt>\n" +
                "          <range>6811.884</range>\n" +
                "          <altitudeMode>relativeToGround</altitudeMode>\n" +
                "        </LookAt>\n" +
                "      </gx:FlyTo>\n" +
                "\n" +
                "      <gx:FlyTo>\n" +
                "        <gx:duration>3.0</gx:duration>\n" +
                "        <gx:flyToMode>smooth</gx:flyToMode>\n" +
                "        <LookAt>\n" +
                "          <longitude>174.064</longitude>\n" +
                "          <latitude>-39.321</latitude>\n" +
                "          <altitude>0</altitude>\n" +
                "          <heading>-48.463</heading>\n" +
                "          <tilt>67.946</tilt>\n" +
                "          <range>4202.579</range>\n" +
                "          <altitudeMode>relativeToGround</altitudeMode>\n" +
                "        </LookAt>\n" +
                "       </gx:FlyTo>\n" +
                "\n" +
                "      <gx:FlyTo>\n" +
                "        <gx:duration>5.0</gx:duration>\n" +
                "        <LookAt>\n" +
                "          <longitude>175.365</longitude>\n" +
                "          <latitude>-36.523</latitude>\n" +
                "          <altitude>0</altitude>\n" +
                "          <heading>-95</heading>\n" +
                "          <tilt>65</tilt>\n" +
                "          <range>2500</range>\n" +
                "          <altitudeMode>relativeToGround</altitudeMode>\n" +
                "        </LookAt>\n" +
                "      </gx:FlyTo>\n" +
                "\n" +
                "      <gx:AnimatedUpdate>\n" +
                "        <gx:duration>0.0</gx:duration>\n" +
                "        <Update>\n" +
                "          <targetHref/>\n" +
                "          <Change>\n" +
                "            <Placemark targetId=\"pin2\">\n" +
                "              <gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                "            </Placemark>\n" +
                "          </Change>\n" +
                "        </Update>\n" +
                "      </gx:AnimatedUpdate>\n" +
                "\n" +
                "      <gx:Wait>\n" +
                "        <gx:duration>6.0</gx:duration>\n" +
                "      </gx:Wait>\n" +
                "\n" +
                "    </gx:Playlist>\n" +
                "  </gx:Tour>\n" +
                "\n" +
                "  <Folder>\n" +
                "    <name>Points and polygons</name>\n" +
                "\n" +
                "    <Style id=\"pushpin\">\n" +
                "      <IconStyle>\n" +
                "        <Icon>\n" +
                "          <href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n" +
                "        </Icon>\n" +
                "      </IconStyle>\n" +
                "    </Style>\n" +
                "\n" +
                "    <Placemark id=\"mountainpin1\">\n" +
                "      <name>New Zealand's Southern Alps</name>\n" +
                "      <styleUrl>#pushpin</styleUrl>\n" +
                "      <Point>\n" +
                "        <coordinates>170.144,-43.605,0</coordinates>\n" +
                "      </Point>\n" +
                "    </Placemark>\n" +
                "\n" +
                "    <Placemark id=\"pin2\">\n" +
                "      <name>The End</name>\n" +
                "      <description>\n" +
                "        Learn more at http://developers.google.com/kml/documentation\n" +
                "      </description>\n" +
                "      <styleUrl>pushpin</styleUrl>\n" +
                "      <Point>\n" +
                "        <coordinates>175.370,-36.526,0</coordinates>\n" +
                "      </Point>\n" +
                "    </Placemark>\n" +
                "\n" +
                "    <Placemark id=\"polygon1\">\n" +
                "      <name>Polygon</name>\n" +
                "      <Polygon>\n" +
                "        <tessellate>1</tessellate>\n" +
                "        <outerBoundaryIs>\n" +
                "          <LinearRing>\n" +
                "            <coordinates>\n" +
                "              175.365,-36.522,0\n" +
                "              175.366,-36.530,0\n" +
                "              175.369,-36.529,0\n" +
                "              175.366,-36.521,0\n" +
                "              175.365,-36.522,0\n" +
                "            </coordinates>\n" +
                "          </LinearRing>\n" +
                "        </outerBoundaryIs>\n" +
                "      </Polygon>\n" +
                "    </Placemark>\n" +
                "\n" +
                "  </Folder>\n" +
                "</Document>\n" +
                "</kml>" +
                "' > " +
                BASE_PATH +
                "kml/slave_4.kml";
        Log.w(TAG_DEBUG, "Command: " + startCommand);
        return startCommand;
    }

    /**
     * Get the absolute path of the file
     * @return the absolute path
     */

    static String buildWriteStarlinkFile() {
        String command = "echo 'http://lg1:81/resources/Starlink.kml' > " +
                BASE_PATH +
                "kmls.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }

    static String buildWriteISSFile() {
        String command = "echo 'http://lg1:81/resources/ISS.kml' > " +
                BASE_PATH +
                "kmls.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }
    /**
     * @return Command to clean the kmls.txt
     */
    public static String buildCleanKMLs() {
        String command = "echo '' > " +
                BASE_PATH +
                "kmls.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }

    /**
     * @return Command to clean the query.txt
     */
    public static String buildCleanQuery() {
        String command = "echo '' > /tmp/query.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }


    public static String buildCommandStartOrbit() {
        String command = "echo \"playtour=Orbit\" > /tmp/query.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }

    private static void orbit(POI poi, StringBuilder command) {
        double heading = poi.getPoiCamera().getHeading();
        int orbit = 0;
        while (orbit <= 36) {
            if (heading >= 360) heading = heading - 360;
            command.append("    <gx:FlyTo>\n").append("    <gx:duration>1.2</gx:duration> \n")
                    .append("    <gx:flyToMode>smooth</gx:flyToMode> \n")
                    .append("     <LookAt> \n")
                    .append("      <longitude>").append(poi.getPoiLocation().getLongitude()).append("</longitude> \n")
                    .append("      <latitude>").append(poi.getPoiLocation().getLatitude()).append("</latitude> \n")
                    .append("      <heading>").append(heading).append("</heading> \n")
                    .append("      <tilt>").append(60).append("</tilt> \n")
                    .append("      <gx:fovy>35</gx:fovy> \n")
                    .append("      <range>").append(poi.getPoiCamera().getRange()).append("</range> \n")
                    .append("      <gx:altitudeMode>absolute</gx:altitudeMode> \n")
                    .append("      </LookAt> \n")
                    .append("    </gx:FlyTo> \n\n");
            heading = heading + 10;
            orbit++;
        }
    }

    private static void movement(POI poi, StringBuilder command, int duration) {
        POILocation poiLocation = poi.getPoiLocation();
        POICamera poiCamera = poi.getPoiCamera();
        command.append("    <gx:FlyTo>\n")
                .append("    <gx:duration>").append(duration).append("</gx:duration>\n")
                .append("    <gx:flyToMode>smooth</gx:flyToMode>\n")
                .append("     <LookAt>\n")
                .append("      <longitude>").append(poiLocation.getLongitude()).append("</longitude>\n")
                .append("      <latitude>").append(poiLocation.getLatitude()).append("</latitude>\n")
                .append("      <altitude>").append(poiLocation.getAltitude()).append("</altitude>\n")
                .append("      <heading>").append(poiCamera.getHeading()).append("</heading>\n")
                .append("      <tilt>").append(poiCamera.getTilt()).append("</tilt>\n")
                .append("      <range>").append(poiCamera.getRange()).append("</range>\n")
                .append("      <gx:altitudeMode>").append(poiCamera.getAltitudeMode()).append("</gx:altitudeMode>\n")
                .append("     </LookAt>\n")
                .append("    </gx:FlyTo>\n\n");
    }

    private static String commandWait(int duration) {
        String waitCommand =  "    <gx:Wait>\n" +
                "     <gx:duration>" + duration + "</gx:duration>\n" +
                "    </gx:Wait>\n\n";
        Log.w(TAG_DEBUG, "WAIT COMMAND:" + waitCommand);
        return  waitCommand;
    }

    public static String buildCommandExitTour(){
        String command = "echo \"exittour=true\" > /tmp/query.txt";
        Log.w(TAG_DEBUG, "command: " + command);
        return command;
    }

    public static String buildCommandCleanSlaves() {
        String command = "echo '' > " + BASE_PATH + "kmls.txt ";
        Log.w(TAG_DEBUG, "commandCleanSlaves: " + command);
        return command;
    }
}
