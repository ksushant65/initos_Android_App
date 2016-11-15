package major.smartconfig.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.DatagramPacket;
import java.util.ArrayList;

import major.smartconfig.Entity.Device;
import major.smartconfig.Network.Devices;
import major.smartconfig.Network.UDP;
import major.smartconfig.R;
import major.smartconfig.UI.Adapters.DevicesAdapter;

public class AllDevicesActivity extends AppCompatActivity {

    public static AllDevicesActivity home;
    static Devices devices;
    static UDP udp;
    static Context context;
    ArrayList<Device> deviceList = new ArrayList<>();
    DevicesAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        home = this;

        initialiseNetwork();
        deviceList.add(new Device("Air Conditioner","20 C","192.168.2.1",null));
        deviceList.add(new Device("TV","Ch:42","192.168.2.5",null));
        deviceList.add(new Device("Air Purifier","29 ppm","192.168.2.6",null));

        RecyclerView recycleView = (RecyclerView) findViewById(R.id.device_list);
        deviceAdapter = new DevicesAdapter(this, deviceList,R.layout.device_item);
        recycleView.setAdapter(deviceAdapter);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initialiseNetwork(){
        udp = new UDP(this,8809);
        devices = new Devices();
        context = getApplicationContext();


        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                udp.startListening();
            }
        });
        t1.start();

        udp.sendBrodcast(udp.serverSocket,"1 :: "+udp.serverSocket.getLocalAddress().toString());
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

            System.out.println("Device config received! deviceName : "
                    + deviceName + " currentConfig : " + currentConfig + " ip : " + ip);

            deviceList.add(new Device(deviceName,currentConfig,ip,null));
            deviceAdapter.notifyDataSetChanged();
        }

    }
}
