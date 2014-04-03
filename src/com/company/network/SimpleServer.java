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

    public SimpleServer() {
        server = new Server();
        gui = new ServerGUI(this);
    }

    public static void main(String[] args) {
        new SimpleServer();
    }

    public void start() {
        server.start();
        gui.printConsole("Server startet");
        try {
            server.bind(11111,22222);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gui.printConsole("Server bind to TCP ["+ TCP +"] and UDP ["+ UDP + "]");

        gui.printConsole("Creating map...");
        createMap(gui.getWidth(),gui.getHeight(),gui.getWallChance(),gui.getDestructableWallChance());
        gui.printConsole("Map created");

        ServerVariables v = new ServerVariables(map);
        server.sendToAllTCP(v);

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if(object instanceof ClientVariables) {
                    ClientVariables cV = (ClientVariables) object;
                }
            }
        });

    }

    private void createMap(int width, int height, double wallChance, double destructableWallChance) {
        map = new Map(30,20,5,20);
    }

    public void freeze() {
    }

    public void shutDown() {
        server.close();
    }
}
