package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        //Start server thread
        Server server = new Server();
        server.start();

        Server s = new Server();
        //Enter data using BufferReader
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {// Re
                System.out.println("Type the number next to command");
                System.out.println("1. View IP Address currently serving in");
                System.out.println("2. Number of Clients connected");
                System.out.println("3. Data serving to Clients");
                int name = Integer.parseInt(reader.readLine());

                if(name == 4) return;
                switch (name) {
                    case 1:
                        System.out.println(server.getServerIP());
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                }

                // Printing the read line
                System.out.println(name);
            }catch (IOException e) {
                break;
            }

        }
    }
}
