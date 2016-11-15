package major.smartconfig.Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Created by Decoder_10 on 10-Nov-16.
 */
public class TestClient {

    public static void main(String args[]){


        try {
            System.out.println("Starting New Server Socket! ");
            final DatagramSocket socket = new DatagramSocket(1408);

        Thread listen = new Thread(new Runnable() {
            @Override
            public void run() {
                startListening(socket);
            }
        });
        listen.start();

        Thread sendPacket = new Thread(new Runnable() {
            @Override
            public void run() {

                Scanner sc = new Scanner(System.in);
                while(true) {
                    String packet = sc.nextLine().trim();
                    try {
                        InetAddress ip = InetAddress.getByName("172.19.0.108");
                        send(socket, ip, packet);
                        System.out.println("Sent a packet with data: " + packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendPacket.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void startListening(DatagramSocket socket){
        try{
            byte[] data = new byte[100];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            while(true){
                socket.receive(packet);
                System.out.println("Received Data: " + new String(packet.getData()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void send(DatagramSocket socket, InetAddress ip, String message){
        try {
            socket.send(new DatagramPacket(message.getBytes(), message.length(), ip, 1408));
            System.out.println("Sent data: " + message);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
