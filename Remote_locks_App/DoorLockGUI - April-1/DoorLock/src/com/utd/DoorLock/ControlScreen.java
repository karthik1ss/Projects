//Calling methods from background

package com.utd.DoorLock;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by nxd130730 on 3/11/14.
 */
public class ControlScreen extends Activity {

    private Switch lock;
    private TextView status;
    /*acn_server address*/
    public String serverHostName = "10.176.66.44";

    /*PC address*/
//    public String serverHostName = "192.168.0.18";

    public static int serverPort = 3001;
    public Socket socket = null;
//    public static int checkbit = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_controlscreen);
        lock = (Switch)findViewById(R.id.switch1);
        status = (TextView)findViewById(R.id.displayText);
        Log.i("status received",status.toString());
        lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    new ControlThread().execute("lock");
                    Log.i("Lock Status", "LOCKED!!");
//                    checkbit = 1;
                }
                else {
                    new ControlThread().execute("unlock");
                    Log.i("Lock Status", "UNLOCKED!!");
//                    checkbit = 1;
                }
            }
        });
    }



    public class ControlThread extends AsyncTask<String, String, Void>{


        @Override
        protected Void doInBackground(String... status) {

           try{
                InetAddress serverAddress = InetAddress.getByName(serverHostName);
                Log.i("Server IP Address", serverAddress.getHostAddress());
                Log.i("Server Name ", serverAddress.getHostName());
                Log.i("Server port ", String.valueOf(ControlScreen.serverPort));

                socket = new Socket(serverAddress.getHostName(), serverPort);
//                publishProgress("Connected to the server: "+serverAddress);

               String send_message=null;
               PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
               BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


               if(status[0]=="lock"){
                  send_message = "android" + " " + "lock";
//                  send_message = "Button clicked on Android is: "+lock.getTextOff().toString();
               }
               else if(status[0]=="unlock"){
                   send_message = "android" + " " + "unlock";
//                   send_message = "Button clicked on Android is: "+lock.getTextOn().toString();
               }

               output.println(send_message);
               output.flush();
               Log.i("Message sent from Android", send_message);
//                publishProgress("Message sent from Android: " + send_message);
//               checkbit = 0;

               while(true) {

//                   if(checkbit == 1) {
//                       output.println(send_message);
//                       Log.i("Message sent from Android", send_message);
//                       checkbit = 0;
//                   }

                   String read = input.readLine();
                   Log.i("Message received from Server", read);

                   if(read.contains("android")) {
                       String statusText = null, statusToast = null;
                       if(read.contains("unlocked")) {
                           statusText = "UNLOCKED";
                           statusToast = "Door UNLOCKED successfully ... ";
                           publishProgress(statusText, statusToast);
                           Log.i("Update from raspberrypi : ", statusToast);
                       } else if(read.contains("locked")) {
                           statusText = "LOCKED";
                           statusToast = "Door LOCKED successfully ... ";
                           publishProgress(statusText, statusToast);
                           Log.i("Update from raspberrypi : ", statusToast);
                       } else {
                           statusText = "UNKNOWN";
                           statusToast = "unknown status ";
                           publishProgress(statusText);
                           Log.i("Update from raspberrypi : ", statusToast);
                       }
                       break;
                   } else {
                       String invalidResponse = "Unknown response!!";
                       Log.i("!", invalidResponse);
                       break;
                   }
//                   break;
               }
            }
            catch(UnknownHostException e1){
                Log.e("Exception","Unknown Hostname Exception ", e1);
            }catch(EOFException e2){
                e2.printStackTrace();
            }catch (IOException e3) {
                Log.e("Exception", "IO Exception ", e3);
            } /*catch (ClassNotFoundException e) {
               e.printStackTrace();
           }*/
            try {
                socket.close();
            } catch (IOException e) {
                Log.e("Exception", "IO Exception ", e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            status.setText(values[0]);
            Toast.makeText(getApplicationContext(), values[1], Toast.LENGTH_LONG).show();
        }
    }
}