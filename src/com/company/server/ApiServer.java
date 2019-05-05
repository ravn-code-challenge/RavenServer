package com.company.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ApiServer extends Thread{

    ServerSocket ss;
    ArrayList<Socket> clients = new ArrayList<>();
    GiphyController giphyController = GiphyController.getInstance();

    @Override
    public void run() {
        try {
            ss = new ServerSocket(8381);
            Socket s = null;
            while (true) {
                s = ss.accept();
                clients.add(s);
                System.out.println("A new client is connected API Server : " + s);

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

    public int getNumberOfClients() {
        return clients.size();
    }

    // ClientHandler class
    class ClientHandler extends Thread {
        final DataInputStream dis;
        final DataOutputStream dos;
        final Socket s;
        private ReadWriteLock rwlock = new ReentrantReadWriteLock();
        String sortField;

        // Constructor
        public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
        {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
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
            int index = arg.indexOf("/");
            if(index != -1) {
                return arg.substring(index+1);
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
            while (true) {
                try {

                    // receive the answer from client
                    argument = dis.readUTF();
                    firstArgument = getFirstArgument(argument);
                    secondArgument = getSecondArgument(argument);

                    if(firstArgument.toLowerCase().contains("list")) {
                        System.out.println("List called");
                        System.out.println(giphyController.getJsonList());
                        dos.writeUTF(giphyController.getJsonList());
                    }
                    else if(firstArgument.toLowerCase().contains("add")) {
                        System.out.println("Add called");
                        System.out.println(secondArgument);
                        boolean result = giphyController.add(secondArgument);
                        if(result) {
                            System.out.println("Write succeeded");
                            dos.writeUTF("ok");
                            PushServer.getInstance().pushListToClients(giphyController.getJsonList());
                        }
                        else {
                            System.out.println("Write failed");
                            dos.writeUTF("error");
                        }
                    }
                    else if(firstArgument.toLowerCase().contains("update")) {
                        System.out.println("Update called: " + argument);
                        System.out.println(secondArgument);
                        boolean result = giphyController.update(secondArgument);
                        if(result) {
                            System.out.println("Write succeeded");
                            dos.writeUTF("ok");
                            PushServer.getInstance().pushListToClients(giphyController.getJsonList());
                        }
                        else {
                            System.out.println("Write failed");
                            dos.writeUTF("error");
                        }
                    }
                    else if(firstArgument.toLowerCase().contains("remove")) {
                        System.out.println("Remove called, ID: " + secondArgument);
                        if(secondArgument == null) {
                            dos.writeUTF("error/does not exist");
                        }
                        else {
                            boolean removeStatus = giphyController.remove(Long.parseLong(secondArgument));
                            if(removeStatus) {
                                dos.writeUTF("ok");
                            }
                            else {
                                dos.writeUTF("error/does not exist");
                            }
                        }
                    }
                    else if(firstArgument.toLowerCase().contains("viewcount")) {
                        System.out.println("Viewcount called: " + argument);
                        System.out.println(secondArgument);
                        boolean result = giphyController.incrementViewCount(Long.parseLong(secondArgument));
                        if(result) {
                            System.out.println("Increment succeeded");
                            PushServer.getInstance().pushListToClients(giphyController.getJsonList());
                        }
                    }
                    else if(firstArgument.toLowerCase().contains("sort")) {
                        String[] args = secondArgument.split("&");
                        String json = giphyController.sort(args[0], args[1]);
                        dos.writeUTF("ok");
                        PushServer.getInstance().pushListToClients(json);
                    }
                    else if(firstArgument.toLowerCase().contains("exit")) {
                        System.out.println("Exit called");
                        this.s.close();
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
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
