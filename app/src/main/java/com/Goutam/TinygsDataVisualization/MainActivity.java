package com.Goutam.TinygsDataVisualization;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility;
import com.Goutam.TinygsDataVisualization.utility.ConstantPrefs;

import com.Goutam.TinygsDataVisualization.connection.LGCommand;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionManager;
import com.Goutam.TinygsDataVisualization.connection.LGConnectionSendFile;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;


import java.util.regex.Pattern;

public class MainActivity extends TopBarActivity {

    private static final Pattern HOST_PORT = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]):[0-9]+$");

    private Button buttConnectMenu, buttConnectLiquidGalaxy, buttTryAgain,restart,clear,power,clean_logo,relaunch;
    private TextView connecting, textUsername, textPassword, textInsertUrl,textlgrigs;
    private EditText URI, username, password,lg_rigs;
    private ImageView logo;
    private TextView connectionStatus;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View topBar = findViewById(R.id.top_bar);
        buttConnectMenu = topBar.findViewById(R.id.butt_connect_menu);
        connecting = findViewById(R.id.connecting);

        buttConnectLiquidGalaxy = findViewById(R.id.butt_connect_liquid_galaxy);
        URI = findViewById(R.id.uri);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        connectionStatus = findViewById(R.id.connection_status);

        textUsername = findViewById(R.id.text_username);
        textPassword = findViewById(R.id.text_password);
        textInsertUrl = findViewById(R.id.text_insert_url);
        logo = findViewById(R.id.logo);
        buttTryAgain = findViewById(R.id.butt_try_again);
        textlgrigs = findViewById(R.id.lg_rigs_txt);
        lg_rigs = findViewById(R.id.lg_rig);

        restart = findViewById(R.id.reboot);
        clear = findViewById(R.id.clear_kml);
        power = findViewById(R.id.shutdown);
        clean_logo= findViewById(R.id.clean_logo);
        relaunch = findViewById(R.id.relaunch);

        buttConnectLiquidGalaxy.setOnClickListener((view) -> connectionTest());
        buttTryAgain.setOnClickListener((view) -> {
            SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
            loadConnectionStatus(sharedPreferences);
            changeToMainView();
            SharedPreferences.Editor editor = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE).edit();
            editor.putBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
            editor.apply();
        });

        clear.setOnClickListener(view -> ClearKML());
        restart.setOnClickListener(view -> reboot());
        power.setOnClickListener(view -> shutdown());
        relaunch.setOnClickListener(view -> relaunch());
        clean_logo.setOnClickListener(view-> clean_logo());
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);


    }


    @Override
    protected void onResume() {
        loadSharedData();
        super.onResume();
    }

    private void loadConnectionStatus(SharedPreferences sharedPreferences) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_green));
        } else {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_red));
        }
    }
    /**
     * Load the data that is in shared preferences
     */
    private void loadSharedData() {
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        loadConnectionStatus(sharedPreferences);

        if(isConnected){
            changeToNewView();
        } else {
            String text = sharedPreferences.getString(ConstantPrefs.URI_TEXT.name(), "");
            String usernameText = sharedPreferences.getString(ConstantPrefs.USER_NAME.name(), "");
            String passwordText = sharedPreferences.getString(ConstantPrefs.USER_PASSWORD.name(), "");
            String rigs =sharedPreferences.getString(ConstantPrefs.LG_RIGS.name(), "");
            boolean isTryToReconnect = sharedPreferences.getBoolean(ConstantPrefs.TRY_TO_RECONNECT.name(), false);

            if(!text.equals("")) URI.setText(text);
            if(!rigs.equals("")) lg_rigs.setText(rigs);
            if(!usernameText.equals("")) username.setText(usernameText);
            if(!passwordText.equals("")) password.setText(passwordText);
            if(isTryToReconnect) buttConnectLiquidGalaxy.setText(getResources().getString(R.string.button_try_again));
        }
    }

    /**
     * Create a connection to the liquid galaxy and Test if it is working
     */
    private void connectionTest() {
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);

        String hostPort = URI.getText().toString();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String portno = lg_rigs.getText().toString();
        saveConnectionInfo(hostPort, usernameText, passwordText,portno);

        if (!isValidHostNPort(hostPort)) {
            CustomDialogUtility.showDialog(MainActivity.this, getResources().getString(R.string.activity_connection_host_port_error));
            return;
        }

        Dialog dialog = CustomDialogUtility.getDialog(this, getResources().getString(R.string.connecting));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        changeButtonTextConnecting();
        createLgCommand(hostPort, usernameText, passwordText, dialog);
    }

    /**
     * Save the information in shared preference
     * @param hostPort Host:port
     * @param usernameText username
     * @param passwordText password
     */
    private void saveConnectionInfo(String hostPort, String usernameText, String passwordText, String rigs) {
        SharedPreferences.Editor editor = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE).edit();
        editor.putString(ConstantPrefs.URI_TEXT.name(), hostPort);
        editor.putString(ConstantPrefs.USER_NAME.name(), usernameText);
        editor.putString(ConstantPrefs.USER_PASSWORD.name(), passwordText);
        editor.putBoolean(ConstantPrefs.TRY_TO_RECONNECT.name(), true);
        editor.putString(ConstantPrefs.LG_RIGS.name(),rigs);
        editor.apply();
    }

    /**
     * Create LgCommand  for the test
     * @param hostPort A string with the host and the port
     * @param usernameText The username
     * @param passwordText The password
     * @param dialog The dialog that is going to be shown to the user
     */
    private void createLgCommand(String hostPort, String usernameText, String passwordText, Dialog dialog) {
        final String command = "echo 'connection';";
        LGCommand lgCommand = new LGCommand(command, LGCommand.CRITICAL_MESSAGE, response -> dialog.dismiss());
        createConnection(usernameText, passwordText, hostPort, lgCommand);
        sendMessageError(lgCommand, dialog);
    }

    /**
     * Create the connection to the liquid galaxy
     * @param username The username of the connection
     * @param password The password of the connection
     * @param hostPort The String with the hos and the port of the liquid galxy
     * @param lgCommand The command to be send
     */
    private void createConnection(String username, String password, String hostPort, LGCommand lgCommand) {
        String[] hostNPort = hostPort.split(":");
        String hostname = hostNPort[0];
        int port = Integer.parseInt(hostNPort[1]);
        LGConnectionManager lgConnectionManager = LGConnectionManager.getInstance();
        lgConnectionManager.setData(username, password, hostname, port);
        lgConnectionManager.startConnection();
        lgConnectionManager.addCommandToLG(lgCommand);
        LGConnectionSendFile.getInstance().setData(username, password, hostname, port);
    }

    /**
     * Change the Button for a TextView with the text "Connecting ..."
     */
    private void changeButtonTextConnecting() {
        buttConnectLiquidGalaxy.setVisibility(View.INVISIBLE);
        connecting.setVisibility(View.VISIBLE);
        connecting.setText(getResources().getString(R.string.connecting));
    }

    /**
     * Create a Dialog to inform the user if the connection to the liquid galaxy has fail or not
     * @param lgCommand The command send to liquid galaxy
     * @param dialog The dialog that has been show to the user
     */
    private void sendMessageError(LGCommand lgCommand, Dialog dialog) {
        handler.postDelayed(() -> {
            if (dialog.isShowing()){
                LGConnectionManager.getInstance().removeCommandFromLG(lgCommand);
                dialog.dismiss();
                CustomDialogUtility.showDialog(MainActivity.this, getResources().getString(R.string.activity_connection_error_connect));
                SharedPreferences.Editor editor = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE).edit();
                editor.putBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
                editor.apply();
                connecting.setVisibility(View.INVISIBLE);
                buttConnectLiquidGalaxy.setVisibility(View.VISIBLE);
                buttConnectLiquidGalaxy.setText(getResources().getString(R.string.button_try_again));
            }else{
                CustomDialogUtility.showDialog(MainActivity.this, getResources().getString(R.string.activity_connection_success));
                SharedPreferences.Editor editor = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE).edit();
                editor.putBoolean(ConstantPrefs.IS_CONNECTED.name(), true);
                editor.apply();
                changeToNewView();
                ActionController actionController = ActionController.getInstance();
                actionController.exitTour();
                ActionController.getInstance().sendBalloonWithLogos(MainActivity.this);
                SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
                loadConnectionStatus(sharedPreferences);

            }
        }, 2000);
    }

    /**
     * Change the view to the connected to liquid galaxy view
     */
    private void changeToNewView() {
        buttConnectLiquidGalaxy.setVisibility(View.INVISIBLE);
        connecting.setVisibility(View.INVISIBLE);
        URI.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        textUsername.setVisibility(View.INVISIBLE);
        lg_rigs.setVisibility(View.INVISIBLE);
        textlgrigs.setVisibility(View.INVISIBLE);
        textPassword.setVisibility(View.INVISIBLE);
        textInsertUrl.setVisibility(View.INVISIBLE);
        logo.setVisibility(View.VISIBLE);
        buttTryAgain.setVisibility(View.VISIBLE);
    }

    /**
     * Change the view to the try to connect to liquid galaxy view
     */
    private void changeToMainView() {
        buttConnectLiquidGalaxy.setVisibility(View.VISIBLE);
        connecting.setVisibility(View.VISIBLE);
        URI.setVisibility(View.VISIBLE);
        lg_rigs.setVisibility(View.VISIBLE);
        textlgrigs.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        textUsername.setVisibility(View.VISIBLE);
        textPassword.setVisibility(View.VISIBLE);
        textInsertUrl.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
        buttTryAgain.setVisibility(View.INVISIBLE);
        loadSharedData();
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);
    }

    /**
     * Validate if the string is valid
     *
     * @param hostPort The string with the host and the port
     * @return true if is a valid string with the host and the port
     */
    private boolean isValidHostNPort(String hostPort) {
        return HOST_PORT.matcher(hostPort).matches();
    }
    private void ClearKML() {
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            ActionController actionController = ActionController.getInstance();
            actionController.sendcleanballloon(MainActivity.this);
            actionController.exitTour();
        }else{
            CustomDialogUtility.showDialog(MainActivity.this,"LG is not connected, Please connect first!");
        }
    }

    private void relaunch(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to relaunch Liquid Galaxy?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
                String users = sharedPreferences.getString(ConstantPrefs.USER_NAME.name(), "");
                String passwordText = sharedPreferences.getString(ConstantPrefs.USER_PASSWORD.name(), "");
                String name = sharedPreferences.getString(ConstantPrefs.LG_RIGS.name(), "");
                if(name.matches("")){
                    CustomDialogUtility.showDialog(MainActivity.this,"Please enter total numbers of LG rigs!");
                    return;
                }
                Log.d("TAG DEBUG",users);
                boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
                if (isConnected) {
                    for (int i = Integer.valueOf(name); i>=1; i--) {
                        try {
                            ActionController.getInstance().sendLaunch(MainActivity.this,users,passwordText,i);
                        }
                        catch (Exception e) {

                        }
                    }
                    return;
                }else{
                    CustomDialogUtility.showDialog(MainActivity.this,"LG is not connected,Please connect first!");
                }
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    private void reboot(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to reboot Liquid Galaxy?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
                String users = sharedPreferences.getString(ConstantPrefs.USER_NAME.name(), "");
                String passwordText = sharedPreferences.getString(ConstantPrefs.USER_PASSWORD.name(), "");
                String name = sharedPreferences.getString(ConstantPrefs.LG_RIGS.name(), "");
                if(name.matches("")){
                    CustomDialogUtility.showDialog(MainActivity.this,"Please enter total numbers of LG rigs!");
                    return;
                }
                Log.d("TAG DEBUG",users);
                boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
                if (isConnected) {
                    for (int i = Integer.valueOf(name); i>=1; i--) {
                        try {
                            ActionController.getInstance().sendReboot(MainActivity.this,users,passwordText,i);
                        }
                        catch (Exception e) {

                        }
                    }
                    return;
                }else{
                    CustomDialogUtility.showDialog(MainActivity.this,"LG is not connected,Please connect first!");
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void shutdown(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to power-off Liquid Galaxy?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
                String users = sharedPreferences.getString(ConstantPrefs.USER_NAME.name(), "");
                String passwordText = sharedPreferences.getString(ConstantPrefs.USER_PASSWORD.name(), "");
                String name = sharedPreferences.getString(ConstantPrefs.LG_RIGS.name(), "");
                if(name.matches("")){
                    CustomDialogUtility.showDialog(MainActivity.this,"Please enter total numbers of LG rigs!");
                    return;
                }
                Log.d("TAG DEBUG",users);
                boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
                if (isConnected) {
                    for (int i = Integer.valueOf(name); i>=1; i--) {
                        try {
                            ActionController.getInstance().sendshutdown(MainActivity.this,users,passwordText,i);
                        }
                        catch (Exception e) {

                        }
                    }
                }else{
                    CustomDialogUtility.showDialog(MainActivity.this,"LG is not connected,Please connect first!");
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        }

    private void clean_logo(){

        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            ActionController.getInstance().sendcleanlogo(MainActivity.this);
            }else{
            CustomDialogUtility.showDialog(MainActivity.this,"LG is not connected,Please connect first!");
        }
    }


}