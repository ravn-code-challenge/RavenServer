package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
        final DataInputStream dis;
        final DataOutputStream dos;
        final Socket s;


        // Constructor
        public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
        {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
        }

        @Override
        public void run()
        {
            String received;
            String toreturn;
            while (true)
            {
                try {

                    // Ask user what he wants
                    dos.writeUTF("What do you want?[Date | Time]..\n"+
                            "Type Exit to terminate connection.");

                    // receive the answer from client
                    received = dis.readUTF();

                    if(received.equals("Exit"))
                    {
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        System.out.println("Connection closed");
                        break;
                    }

                    // creating Date object
                    Date date = new Date();

                    // write on output stream based on the
                    // answer from the client
                    switch (received) {

                        case "Date" :
                            toreturn = fordate.format(date);
                            dos.writeUTF(toreturn);
                            break;

                        case "Time" :
                            toreturn = fortime.format(date);
                            dos.writeUTF(toreturn);
                            break;

                        default:
                            dos.writeUTF("Invalid input");
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

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
