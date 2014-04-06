package com.company.network;

import com.company.ExternClasses.Map;
import com.company.ServerGUI;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by winterj on 26.03.2014.
 */
public class SimpleServer {
    Server server;
    private static Map map;
    private final int TCP = 11111;
    private final int UDP = 22222;
    ServerGUI gui;
    private boolean running;
    private long startTime;

    public SimpleServer() {
        server = new Server();
        gui = new ServerGUI(this);
    }

    public static void main(String[] args) {
        new SimpleServer();
    }

    public void start() {
        server.start();
        if(server.getConnections().length < 2) {
            gui.printConsole("No clients connected");
            gui.printConsole("Opening Lobby");
            runLobby();
        }
        gui.printConsole("Server started");
        try {
            server.bind(11111,22222);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gui.printConsole("Server bind to TCP ["+ TCP +"] and UDP ["+ UDP + "]");

        gui.printConsole("Creating map...");
        createMap(gui.getWidth(),gui.getHeight(),gui.getWallChance(),gui.getDestructableWallChance());
        gui.printConsole("Map created");

        gui.printConsole("Sending map to clients...");
        ServerVariables v = new ServerVariables(map);
        server.sendToAllTCP(v);
        gui.printConsole("Ready");

        running = true;
        startTime = System.currentTimeMillis();

        gui.printConsole("Running...");

        runGame();

    }

    private void runLobby() {
        gui.printConsole("Waiting for Players...");
        startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();
        while((stopTime - startTime) < 30000) {
            if(server.getConnections().length == 0) {
                gui.printConsole("Searching... ["+(stopTime-startTime)/1000+"]");
            }
            else {
                gui.printConsole(String.format("[%s] player(s) in lobby", server.getConnections().length));
            }
            try {
                Thread.sleep(1000);
                gui.redrawText();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopTime = System.currentTimeMillis();
        }
    }

    private void createMap(int width, int height, double wallChance, double destructableWallChance) {
        map = new Map(30,20,5,20);
    }

    private void runGame() {
            server.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if(object instanceof ClientVariables) {
                        ClientVariables cV = (ClientVariables) object;
                    }
                }
            });
    }

    public void freeze() {
        if(running) {
            server.sendToAllTCP(new ServerVariables((byte) 0)); //Pause
            server.stop();
            gui.printConsole("Server paused");
        } else {
            gui.printConsole("Can't pause. Server is not running");
        }
    }

    public void shutDown() {
        running = false;
        server.close();
        gui.printConsole(String.format("Server has run %s Minutes", (System.currentTimeMillis()-startTime) / 60000));
    }
}
