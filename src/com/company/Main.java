package com.company;

import com.company.server.ApiServer;
import com.company.server.GiphyController;
import com.company.server.PushServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        //Start apiServer thread
        ApiServer apiServer = new ApiServer();
        apiServer.start();

        PushServer pushServer = PushServer.getInstance();
        pushServer.start();

        //Enter data using BufferReader
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {// Re
                System.out.println("Type the number next to command");
                System.out.println("1. View IP Address currently serving in");
                System.out.println("2. Number of Clients connected");
                System.out.println("3. Data serving to Clients");
                System.out.println("4. Write Data to persistant storage");
                int name = Integer.parseInt(reader.readLine());

                switch (name) {
                    case 1:
                        System.out.println("Server IP:" + apiServer.getServerIP());
                        break;
                    case 2:
                        System.out.println("Clients Connected:" + apiServer.getNumberOfClients());
                        break;
                    case 3:
                        System.out.println(GiphyController.getInstance().getJsonList());
                        break;
                    case 4:
                        if(GiphyController.getInstance().writeList()) {
                            System.out.println("Writing data was successful");
                        }else {
                            System.out.println("Failed to write data");
                        }
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
