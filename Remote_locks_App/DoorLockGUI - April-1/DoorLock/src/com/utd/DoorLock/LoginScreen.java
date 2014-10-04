package com.utd.DoorLock;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Nihanth on 3/8/14.
 */
public class LoginScreen extends Activity {

    public Socket socket = null;
    public String serverHostName = "10.176.66.44";
    public Boolean result = false;

    public static int serverPort = 3001;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loginscreen);
        Button button = (Button)findViewById(R.id.login_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent viewIntent = new Intent(v.getContext(), ControlScreen.class);

//                startActivityForResult(viewIntent, 0);

                EditText emailId = (EditText) findViewById(R.id.editText);
                EditText password = (EditText) findViewById(R.id.editText2);

                String username = emailId.getText().toString();
                String passwd = password.getText().toString();

                new LoginThread().execute(username,passwd);

//                if(isValidUserName() && isValidPassword())
//                {
//                    startActivityForResult(viewIntent, 0);
//                }
//                else
//                {
                    //Toast.makeText(LoginScreen.this, "Invalid Email-id/Password.", 30000).show();
                //}
            }
        });
    }

    public class LoginThread extends AsyncTask<String, String, Void> {


//    private boolean isValidPassword() {
//        EditText password = (EditText) findViewById(R.id.editText2);
//        if(password.getText().length() > 0)
//        {
//            if(password.getText().toString().equals("doorlock"))
//            {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean isValidUserName() {
//        EditText emailId = (EditText) findViewById(R.id.editText);
//        if(emailId.getText().length() > 0)
//        {
//            if(emailId.getText().toString().equals("admin@doorlock.com"))
//            {
//                return true;
//            }
//        }
//        return false;
//    }

        @Override
        protected Void doInBackground(String... status) {


            InetAddress serverAddress = null;

            try {
                serverAddress = InetAddress.getByName(serverHostName);
                Log.i("Server IP Address", serverAddress.getHostAddress());
                Log.i("Server Name ", serverAddress.getHostName());
                Log.i("Server port ", String.valueOf(ControlScreen.serverPort));
                socket = new Socket(serverAddress.getHostName(), serverPort);
                String send_message = null;
                PrintWriter output = null;
                output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                if (status.length != 0) {
                    send_message = "android_validation:" + status[0] + ":" + status[1];
                    //                  send_message = "Button clicked on Android is: "+lock.getTextOff().toString();
                }

                output.println(send_message);
                output.flush();
                Log.i("Message sent from Android", send_message);

                while (true) {

                    String read = input.readLine();
                    Log.i("Message received from Server", read);
                   // if (read.contains("android")) {
                        String statusUpdate = null;
                        if (read.equals("PASS")) {

                            statusUpdate = "Login Success  ... ";
                            Intent viewIntent = new Intent(getApplicationContext(), ControlScreen.class);
                            startActivityForResult(viewIntent, 0);
                            //startActivity(viewIntent);
                            publishProgress(statusUpdate);
                            break;

                        } else {
                            statusUpdate = "Login Failed. Invalid Email-id/Password. ";
                            //Toast.makeText(LoginScreen.this, "Invalid Email-id/Password.", 30000).show();
                            publishProgress(statusUpdate);
                            break;
                        }
                  //  }

                }
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }


            //                publishProgress("Connected to the server: "+serverAddress);


            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
        }
    }


  }