package com.company;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server extends Thread{

    ServerSocket ss;
    ArrayList<Socket> clients = new ArrayList<>();

    @Override
    public void run() {
        try {
            ss = new ServerSocket(8381);
            Socket s = null;
            while (true) {
                s = ss.accept();
                clients.add(s);
                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();
            }
        }
        catch (IOException e) {
            System.out.println("Socket connection error: " + e.getMessage());
        }
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

    // ClientHandler class
    class ClientHandler extends Thread {
        final DataInputStream dis;
        final DataOutputStream dos;
        final Socket s;
        private ReadWriteLock rwlock = new ReentrantReadWriteLock();
        JSONParser parser = new JSONParser();
        String sortField;

        // Constructor
        public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
        {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
        }

        private void list() {
            rwlock.readLock().lock();
            try {
                System.out.println("Reading json file");
                JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
                dos.writeUTF(a.toJSONString());
            } catch (IOException e) {
                System.out.println("IOEception: " + e);
            } catch (ParseException e) {
                System.out.println("Parse exception: " + e);
            }

            finally {
                rwlock.readLock().unlock();
            }
        }

        String getFirstArgument(String arg) {
            int index = arg.indexOf("{");
            if(index != -1) {
                return arg.substring(0, index);
            }
            else {
                return arg;
            }
        }

        String getSecondArgument(String arg) {
            int index = arg.indexOf("{");
            if(index != -1) {
                return arg.substring(index);
            }
            else {
                return null;
            }
        }


        @Override
        public void run() {
            String argument;
            String firstArgument;
            String secondArgument;
            while (true)
            {
                try {

                    // receive the answer from client
                    argument = dis.readUTF();
                    firstArgument = getFirstArgument(argument);
                    secondArgument = getSecondArgument(argument);

                    if(firstArgument.toLowerCase().contains("list")) {
                        System.out.println("List called");
                        list();
                    }
                    else if(firstArgument.toLowerCase().contains("add")) {
                        System.out.println("Add called");
                    }
                    else if(firstArgument.toLowerCase().contains("remove")) {
                        System.out.println("Remove called");
                    }
                    else if(firstArgument.toLowerCase().contains("update")) {
                        System.out.println("Update called");
                    }
                    else if(firstArgument.toLowerCase().contains("sort")) {
                        System.out.println("Sort called");
                    }
                    else if(firstArgument.toLowerCase().contains("exit")) {
                        System.out.println("Exit called");
                        this.s.close();
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try
            {
                // closing resources
                this.dis.close();
                this.dos.close();
                System.out.println("Thread has ended");

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
