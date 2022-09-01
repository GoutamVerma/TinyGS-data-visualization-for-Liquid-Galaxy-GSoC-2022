package com.Goutam.TinygsDataVisualization.create.utility.model;



import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import com.Goutam.TinygsDataVisualization.create.utility.model.balloon.Balloon;
import com.Goutam.TinygsDataVisualization.create.utility.model.movement.Movement;
import com.Goutam.TinygsDataVisualization.create.utility.model.poi.POI;
import com.Goutam.TinygsDataVisualization.create.utility.model.poi.POICamera;
import com.Goutam.TinygsDataVisualization.create.utility.model.poi.POILocation;
import com.Goutam.TinygsDataVisualization.create.utility.model.shape.Point;
import com.Goutam.TinygsDataVisualization.create.utility.model.shape.Shape;
import com.Goutam.TinygsDataVisualization.utility.ConstantPrefs;

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
    public static String buildCommandRemoveBallon(){
        String command ="echo '' > /var/www/html/kml/slave_3.kml";
        return command;
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


    public static String stylekmlforStation(){
        return "<Style id=\"s_ylw-pushpin_hl\">\n" +
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

    public static String buildCommandTourStation(String lon,String lat,String alti,String des,String name){
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
                .append(stylekmlforStation());

        String middleCommand = buildTourStation(folderBalloonShapes,lon,lat,alti,des,name);
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


    private static String buildTourStation(StringBuilder folderBalloonShapes,String lon,String lat,String alti,String des,String name) {
        StringBuilder command = new StringBuilder();
        command.append(POICommandStation(lon,lat,alti));
        command.append(BalloonCommandStation(folderBalloonShapes,name,des));
        command.append(BalloonCommandStation(folderBalloonShapes,lon,lat,alti,des,name));

        return command.toString();
    }

    private static String buildTour(StringBuilder folderBalloonShapes,String lon,String lat,String alti,String des,String name) {
        StringBuilder command = new StringBuilder();
        Action action;
        command.append(POICommand(lon,lat,alti));
        command.append(BalloonCommand(folderBalloonShapes,name,des));
        command.append(BalloonCommand(folderBalloonShapes,lon,lat,alti,des,name));

        return command.toString();
    }
    private static String BalloonCommandStation(StringBuilder folderBalloonShapes,String lon,String lat,String alti,String des,String name) {

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
                        "      <coordinates>" + lat + "," + lon +" " + "</coordinates>\n"+
                        "      <gx:altitudeMode>" + "absolute" + "</gx:altitudeMode>\n" +
                        "      </Point>\n" +
                        "    </Placemark>\n\n";

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

    private static String BalloonCommandStation(StringBuilder folderBalloonShapes,String name,String des) {
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
                "<![CDATA[<!-- BalloonStyle background color:\n" +
                "ffffffff\n" +
                "-->\n" +
                "<!-- Icon URL:\n" +
                "http://maps.google.com/mapfiles/kml/paddle/purple-blank.png\n" +
                "-->\n" +
                "<table width=\"400\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <img src=\"https://raw.githubusercontent.com/GoutamVerma/TinyGS-data-visualization-for-Liquid-Galaxy-GSoC-2022/main/app/src/main/res/drawable-xxxhdpi/applogo.png\" alt=\"picture\" width=\"150\" height=\"150\" />\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <h2><font color='#00CC99'>TinyGS Data Visualization Tool</font></h2>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\">\n" +
                "     <p><font color=\"#3399CC\">Description: \n" +
                des+
                "</font></p>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td align=\"center\">\n" +
                "     <a href=\"#\"> </a>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <font color=\"#999999\">TinyGS Data Visualization Tool 2022</font>\n" +
                "   </td>\n" +
                " </tr>\n" +
                "</table>]]>"+
                "      </description>\n" +
                "      <Point>\n" +
                "       <coordinates>" + "32.2563256" + "," + "32.2563256" + "</coordinates>\n" +
                "      </Point>\n" +
                "    </Placemark>\n\n";
        String wait = commandWait(50);
        folderBalloonShapes.append(startCommand);
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
                "<![CDATA[<!-- BalloonStyle background color:\n" +
        "ffffffff\n" +
                "-->\n" +
                "<!-- Icon URL:\n" +
                "http://maps.google.com/mapfiles/kml/paddle/purple-blank.png\n" +
                "-->\n" +
                "<table width=\"400\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <img src=\"https://raw.githubusercontent.com/GoutamVerma/TinyGS-data-visualization-for-Liquid-Galaxy-GSoC-2022/main/app/src/main/res/drawable-xxxhdpi/applogo.png\" alt=\"picture\" width=\"150\" height=\"150\" />\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <h2><font color='#00CC99'>TinyGS Data Visualization Tool</font></h2>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\">\n" +
                "     <p><font color=\"#3399CC\">Description: \n" +
                des+
                "</font></p>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td align=\"center\">\n" +
                "     <a href=\"#\"> </a>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <font color=\"#999999\">TinyGS Data Visualization Tool 2022</font>\n" +
                "   </td>\n" +
                " </tr>\n" +
                "</table>]]>"+
                "      </description>\n" +
                "      <Point>\n" +
                "       <coordinates>" + "32.2563256" + "," + "32.2563256" + "</coordinates>\n" +
                "      </Point>\n" +
                "    </Placemark>\n\n";
        String wait = commandWait(50);
        folderBalloonShapes.append(startCommand);
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
                "      <gx:duration>" + "8" + "</gx:duration>\n" +
                "      <gx:flyToMode>smooth</gx:flyToMode>\n" +
                "      <LookAt>\n" +
                "       <longitude>" + lon + "</longitude>\n" +
                "       <latitude>" + lat + "</latitude>\n" +
                "       <altitude>" + "800000" + "</altitude>\n" +
                "      <heading>"+"0"+"</heading> \n"+
                "      <tilt>45</tilt> \n"+
                "      <gx:fovy>60</gx:fovy> \n"+
                "      <range>10000</range> \n"+
                "      <gx:altitudeMode>absolute</gx:altitudeMode> \n"+
                "     </LookAt>\n" +

                "    </gx:FlyTo>\n\n";
        Log.w(TAG_DEBUG, "POI COMMAND: " + command);
        return  command;
    }
    private static String POICommandStation(String lon,String lat,String alti) {
        String command =  "     <gx:FlyTo>\n" +
                "      <gx:duration>" + "4" + "</gx:duration>\n" +
                "      <gx:flyToMode>bounce</gx:flyToMode>\n" +
                "      <LookAt>\n" +
                "       <longitude>" + lat + "</longitude>\n" +
                "       <latitude>" + lon + "</latitude>\n" +
                "       <heading>" + "0" + "</heading>\n" +
                "       <tilt>" + "60" + "</tilt>\n" +
                "       <range>" + "1000" + "</range>\n" +
                "       <gx:altitudeMode>" + "absolute" + "</gx:altitudeMode>\n" +
                "     </LookAt>\n" +
                "    </gx:FlyTo>\n\n";
        Log.w(TAG_DEBUG, "POI COMMAND: " + command);
        return  command;
    }

    public static String clean_logo(){
        String clean_logo= "echo '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "  <Document id=\"1\">\n" +
                "  </Document>\n" +
                "</kml>' > /var/www/html/kml/slave_4.kml";
        Log.d("TAG_DEBUG",clean_logo);
        return clean_logo;
    }

    public static String clean_balloon(){
        String command =  "echo '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "  <Document id=\"1\">\n" +
                "  </Document>\n" +
                "</kml>' > /var/www/html/kml/slave_3.kml";
        Log.d("TAG_DEBUG",command);
        return command;
    }

    public static String buildCommandshutdown(String username,String password,Integer rigs){
       String shutdown = "sshpass -p "+password+" ssh -t lg"+rigs+" \"echo "+password+" | sudo -S poweroff\"";
        Log.d(TAG_DEBUG,shutdown);
        return shutdown;
    }

    public static String buildCommandReboot(String username,String password,Integer rigs){
        String reboot1 = "sshpass -p "+ password +" ssh -t lg"+rigs+ " \"echo "+password+" | sudo -S reboot\"";
        Log.d(TAG_DEBUG,reboot1);
        return reboot1;
    }


    public static String buildCommandReLaunch(String username,String password,Integer rigs){
        String relaunch =  "RELAUNCH_CMD=\"\\\n" +
                "if [ -f /etc/init/lxdm.conf ]; then\n" +
                "  export SERVICE=lxdm\n" +
                "elif [ -f /etc/init/lightdm.conf ]; then\n" +
                "export SERVICE=lightdm\n" +
                "else\n" +
                "  exit 1\n" +
                "fi\n" +
                "if  [[ \\$(service \\$SERVICE status) =~ 'stop' ]]; then\n" +
                "  echo "+password+" | sudo -S service \\${SERVICE} start\n" +
                "else\n" +
                "  echo "+password+" | sudo -S service \\${SERVICE} restart\n" +
                "fi\n" +
                "\" && sshpass -p "+password+" ssh -x -t lg@lg"+rigs+" \"$RELAUNCH_CMD\"\n";
        Log.d(TAG_DEBUG,relaunch);
        return relaunch;
    }

    /**
     * Build the command to paint a balloon in Liquid Galaxy
     * @return String with command
     */
    public static String buildCommandBalloonWithLogos() {

        String startCommand =  "echo '" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                " <name>VolTrac</name>\n" +
                " <open>1</open>\n" +
                " <description>The logo it located in the bottom left hand corner</description>\n" +
                " <Folder>\n" +
                "   <name>tags</name>\n" +
                "   <Style>\n" +
                "     <ListStyle>\n" +
                "       <listItemType>checkHideChildren</listItemType>\n" +
                "       <bgColor>00ffffff</bgColor>\n" +
                "       <maxSnippetLines>2</maxSnippetLines>\n" +
                "     </ListStyle>\n" +
                "   </Style>\n" +
                "   <ScreenOverlay id=\"abc\">\n" +
                "     <name>VolTrac</name>\n" +
                "     <Icon>\n" +
                "       <href>https://raw.githubusercontent.com/GoutamVerma/TinyGS-data-visualization-for-Liquid-Galaxy-GSoC-2022/main/app/src/main/res/drawable/new_logo.png</href>\n" +
                "     </Icon>\n" +
                "     <overlayXY x=\"0\" y=\"1\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "     <screenXY x=\"0.2\" y=\"0.98\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "     <rotationXY x=\"0\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "     <size x=\"0\" y=\"0\" xunits=\"pixels\" yunits=\"fraction\"/>\n" +
                "   </ScreenOverlay>\n" +
                " </Folder>\n" +
                "</Document>\n" +
                "</kml>\n"+
                "' > " +
                "/var/www/html/kml/slave_4.kml";
        Log.w(TAG_DEBUG, "Command: " + startCommand);
        return startCommand;
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


    private static String commandWait(int duration) {
        String waitCommand =  "    <gx:Wait>\n" + "     <gx:duration>" + duration + "</gx:duration>\n" +
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

    public static String buildCommandBalloon(String des) {
        String startCommand =  "echo '" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                " <name>historic.kml</name>\n" +
                " <Style id=\"purple_paddle\">\n" +
                "   <BalloonStyle>\n" +
                "     <text>$[description]</text>\n" +
                "     <bgColor>ff1e1e1e</bgColor>\n" +
                "   </BalloonStyle>\n" +
                " </Style>\n" +
                " <Placemark id=\"0A7ACC68BF23CB81B354\">\n" +
                "   <name>TinyGS Data Visualization</name>\n" +
                "   <Snippet maxLines=\"0\"></Snippet>\n" +
                "   <description><![CDATA[<!-- BalloonStyle background color:\n" +
                "ffffffff\n" +
                "-->\n" +
                "<!-- Icon URL:\n" +
                "http://maps.google.com/mapfiles/kml/paddle/purple-blank.png\n" +
                "-->\n" +
                "<table width=\"400\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <img src=\"https://raw.githubusercontent.com/GoutamVerma/TinyGS-data-visualization-for-Liquid-Galaxy-GSoC-2022/main/app/src/main/res/drawable-xxxhdpi/applogo.png\" alt=\"picture\" width=\"150\" height=\"150\" />\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <h2><font color='#00CC99'>TinyGS Data Visualization Tool</font></h2>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\">\n" +
                "     <p><font color=\"#FFFFFF\">Description: \n" +
                des+
                "</font></p>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td align=\"center\">\n" +
                "     <a href=\"#\"> </a>\n" +
                "   </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                "   <td colspan=\"2\" align=\"center\">\n" +
                "     <font color=\"#FFFFFF\">TinyGS Data Visualization Tool 2022</font>\n" +
                "   </td>\n" +
                " </tr>\n" +
                "</table>]]></description>\n" +
                "   <LookAt>\n" +
                "     <longitude>-17.841486</longitude>\n" +
                "     <latitude>28.638478</latitude>\n" +
                "     <altitude>0</altitude>\n" +
                "     <heading>0</heading>\n" +
                "     <tilt>0</tilt>\n" +
                "     <range>24000</range>\n" +
                "   </LookAt>\n" +
                "   <styleUrl>#purple_paddle</styleUrl>\n" +
                "   <gx:balloonVisibility>1</gx:balloonVisibility>\n" +
                "   <Point>\n" +
                "     <coordinates>-17.841486,28.638478,0</coordinates>\n" +
                "   </Point>\n" +
                " </Placemark>\n" +
                "</Document>\n" +
                "</kml> "+
                "' > " +
                "/var/www/html/kml/slave_3.kml";
        Log.d("TAG DEBUG",startCommand);
        return startCommand;
    }
}
