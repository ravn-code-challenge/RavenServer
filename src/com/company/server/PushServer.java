package com.company.server;

import com.company.model.GiphyList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PushServer extends Thread{

    ServerSocket ss;
    ArrayList<Socket> clients = new ArrayList<>();
    ArrayList<DataOutputStream> outputStreams = new ArrayList<>();

    private static PushServer pushServer;

    private PushServer() {

    }

    public static PushServer getInstance() {
        if(pushServer == null) pushServer = new PushServer();
        return pushServer;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(8380);
            Socket s = null;
            while (true) {
                s = ss.accept();
                clients.add(s);
                System.out.println("A new client is connected to push server : " + s);

                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                outputStreams.add(dos);

            }
        }
        catch (IOException e) {
            System.out.println("Socket connection error: " + e.getMessage());
        }
    }

    public void pushListToClients(String giphyList) {
        try {
            for (DataOutputStream dOut : outputStreams) {
                dOut.writeUTF("list/" + giphyList);
            }
        }catch (IOException e) {

        }
        System.out.println("Finish pushing to clients");
    }

    public String getServerIP() {
        return ss.getInetAddress().getHostAddress() + ":" + ss.getLocalPort();
    }

    public void closeSockets() {
        try {
            for(Socket s : clients) {
                s.close();
            }
        }
        catch (IOException e) {
            System.out.println("Could not close sockets: " + e);
        }
    }


}
