/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jimmy
 */
public class NuNeGoeieClient {

    private static MulticastSocket socket;
    private static InetAddress group;
    private static InetAddress ownAdress;
    private static DatagramPacket packet;
    private static String eigenIp;

    public static void main(String[] args) {
        sendBroadcast();
        while (true) {
            receiveAnswer();
        }
    }

    public static void sendBroadcast() {
        try {
            socket = new MulticastSocket(4446);
            group = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(group);

            ownAdress = InetAddress.getLocalHost();
            eigenIp = "/"+ ownAdress.getHostAddress().toString();
            byte[] buf = new byte[256];
            String message = eigenIp;
            buf = message.getBytes();
            packet = new DatagramPacket(buf, buf.length, group, 4446);
            

            socket.send(packet);
            System.out.println("sent");

        } catch (IOException ex) {
            Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void receiveAnswer() {
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        String received = new String(packet.getData(), 0, packet.getLength());
        if(!received.equals(eigenIp))
            System.out.println("eigen ip adres wordt teruggezonden");
        System.out.println(received + " from " + packet.getAddress().toString());
    }
}