package major.smartconfig.Network;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import major.smartconfig.UI.AllDevicesActivity;

/**
 * Created by Decoder_10 on 10-Nov-16.
 */
public class UDP {
    public DatagramSocket serverSocket;
    public Context context;

    public UDP(Context context,int port){
        this.context = context;
        try {
            serverSocket = new DatagramSocket(port);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendBrodcast(DatagramSocket serverSocket, String data){
        try {
            serverSocket.setBroadcast(true);
            serverSocket.send(new DatagramPacket(data.getBytes(),data.length(), InetAddress.getByName("255.255.255.255") , 8809));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(DatagramSocket serverSocket, InetAddress ip, String data){
        try {
            serverSocket.send(new DatagramPacket(data.getBytes(), data.length(), ip, 8809));
            System.out.println("Packet sent to ip: " + ip.toString() + " Data: " + data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startListening(){
        try {
            while(true){
                byte[] data = new byte[100];
                DatagramPacket packet = new DatagramPacket(data,data.length);
                System.out.println("Waiting for packet...");
                serverSocket.receive(packet);
                System.out.println("Receivied Data: " + new String(packet.getData()).trim());
                ((AllDevicesActivity)context).managePacket(packet);
            }
        }
        catch(Exception e){
            System.out.println("Unable to listen! Server Error!!");
            e.printStackTrace();
        }
    }
}
