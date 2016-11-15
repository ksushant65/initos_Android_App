package com.example.udptest;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Decoder_10 on 16-Nov-16.
 */
public class Devices {

    Map<String, InetAddress> devices;

    public void addDevice(String name, InetAddress ip){
        devices.put(name,ip);
    }

    public InetAddress getIp(String name){
        return devices.get(name);
    }

    public String getKeyByValue(InetAddress value) {
        for (Map.Entry<String, InetAddress> entry : devices.entrySet()) {
            if (value == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Devices(){
        devices = new HashMap<String, InetAddress>();
    }
}
