package com.example.udptest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class MyActivity extends Activity{

    static Context context;
    static TextView textView2;
    static Devices devices;
    static UDP udp;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        udp = new UDP(this,8809);
        context = getApplicationContext();
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setMovementMethod(new ScrollingMovementMethod());
        devices = new Devices();

        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                udp.startListening();
            }
        });
        t1.start();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button sendButton = (Button) findViewById(R.id.button);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String data = editText.getText().toString();

                try{
                    String stringIp = (String) spinner.getSelectedItem();
                    final InetAddress ip = InetAddress.getByName(stringIp);
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            udp.send(udp.serverSocket, ip, data);
                        }
                    });
                    t1.start();


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }



    public void managePacket(DatagramPacket packet){
        System.out.println("Received Packet from ip: " + packet.getAddress() + " Data: " + new String(packet.getData()).trim());

        String packetRaw = new String(packet.getData()).trim();
        String packetData[] = packetRaw.split(" ");

        if(packetData[0].equals("0")){
            devices.addDevice(packetData[1],packet.getAddress());
            udp.send(udp.serverSocket,packet.getAddress(),"2 :: " + udp.serverSocket.getLocalAddress().toString());
            System.out.println("Packet Data: 2 :: " + udp.serverSocket.getLocalAddress().toString() + "\nPacket Sent to: " + packet.getAddress());
        }

        if(packetData[0].equals("3")){
            String deviceName = devices.getKeyByValue(packet.getAddress());
            String currentConfig = packetData[1];
            String ip = packet.getAddress().toString();

            //Add these to UI
        }

        final String newString = textView2.getText() + "\n" +new String(packet.getData()).trim();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView2.setText(newString);
            }
        });

    }

}
