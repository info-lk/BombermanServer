package com.company.network;

import com.company.ServerGUI;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import network.ClientVariables;
import network.ServerVariables;
import network.VBomb;
import network.VPlayer;
import network.Map;

import java.io.IOException;
import java.util.Random;

/**
 * Created by winterj on 26.03.2014.
 */
public class SimpleServer extends Thread{
    Server server;
    private static Map map;
    private final int TCP = 11111;
    private final int UDP = 22222;
    ServerGUI gui;
    private boolean running;
    private long startTime;
    private Lobby lobby;

    public SimpleServer() {
        server = new Server();
        lobby = new Lobby();
        gui = new ServerGUI(this);
        gui.start();
    }

    public static void main(String[] args) {
        new SimpleServer();
    }

    public void run() {
        server.getKryo().register(ServerVariables.class);
        server.getKryo().register(ServerVariables.CURRENT_INFORMATION.class);
        server.getKryo().register(ClientVariables.class);
        server.getKryo().register(String.class);
        server.getKryo().register(LobbyVariables.class);
        server.getKryo().register(Map.class);
        server.getKryo().register(VPlayer.class);
        server.getKryo().register(VBomb.class);
        server.getKryo().register(VPlayer[].class);
        server.getKryo().register(VPlayer.DIRECTION.class);
        server.getKryo().register(int[][].class);
        server.getKryo().register(int[].class);

        addListener();
        server.start();
        gui.printConsole("Server started");
        try {
            server.bind(11111,22222);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gui.printConsole("Server bind to TCP ["+ TCP +"] and UDP ["+ UDP + "]");
        running = true;

        if(server.getConnections().length < 1) {
            gui.printConsole("No clients connected");
            gui.printConsole("Opening Lobby");
            if (!runLobby()) {
                gui.printConsole("No clients found... closing");
                server.sendToAllTCP(new LobbyVariables(true, 30, lobby.getPlayerCount(), true, "No other clients were found.", false));
                return;
            }
        }

        server.sendToAllTCP(new LobbyVariables(true, 30, lobby.getPlayerCount(), false, "", true));

        gui.printConsole("Creating map with dimensons: " + gui.getWidth() + ", " + gui.getHeight() + "...");
        map = new Map(gui.getWidth(), gui.getHeight(), gui.getWallChance(), gui.getDestructableWallChance());
        gui.printConsole("Map created");
        gui.printConsole(map.getMapRepresentation());

        gui.printConsole("Sending map to clients...");
        ServerVariables v = new ServerVariables(map);
        server.sendToAllTCP(v);
        gui.printConsole("Ready");

        gui.printConsole("Sending players");
        ServerVariables svPlayers = new ServerVariables(generatePlayers(lobby.toArray()));
        int id = 0;
        for (Connection c : lobby.toArray()) {
            c.sendTCP(new ServerVariables(id));
            c.sendTCP(svPlayers); //Hier kommt der Fehler
            id++;
        }
        gui.printConsole("Ready");

        startTime = System.currentTimeMillis();
        gui.printConsole("Running...");
    }


    private VPlayer[] generatePlayers(Connection[] connections) {
        Random ran = new Random();
        VPlayer[] pl = new VPlayer[connections.length];
        for(int i = 0; i < pl.length; i++) {
            pl[i] = new VPlayer((double) ran.nextInt(map.getWidth() + 1), (double) ran.nextInt(map.getHeight() + 1), false, i);
        }
        return pl;
    }

    public boolean runLobby() {
        if(!running) {
            gui.printConsole("Please start server first!");
            return false;
        }

        lobby.open();
        gui.printConsole("Waiting for Players...");
        startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();
        while((stopTime - startTime) < 30000) {
            if(server.getConnections().length == 0) {
                gui.printConsole("Searching... ["+(stopTime-startTime)/1000+"|30]");
            }
            else {
                gui.printConsole(String.format("%s player(s) in lobby", lobby.getPlayerCount()));
            }

            server.sendToAllTCP(new LobbyVariables(true, (int)(stopTime-startTime)/1000, lobby.getPlayerCount(), false, "", false));

            try {
                Thread.sleep(1000);
                gui.redrawText();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stopTime = System.currentTimeMillis();
        }

        lobby.close();
        return (lobby.getPlayerCount() >= 1);
    }

    private void addListener() {
            server.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    System.out.println("Connect from " + connection.getRemoteAddressTCP());
                    if(lobby.allowsConnect()) {
                        lobby.join(connection);
                        System.out.println("Joined. Lobby count is now " + lobby.getPlayerCount());
                    } else {
                        System.out.println("Client tries to connect but lobby is closed or full");
                    }
                }

                @Override
                public void disconnected(Connection connection) {
                    lobby.remove(connection);
                }

                @Override
                public void received(Connection connection, Object object) {
                    System.out.println("ping from " + connection.getRemoteAddressTCP());
                    if(running && object instanceof ClientVariables) {
                        ClientVariables cV = (ClientVariables) object;
                        if(cV.currentInformation == 1) { //Player only
                            server.sendToAllExceptTCP(connection.getID(), new ServerVariables(new VPlayer(cV.playerXPos,cV.playerYPos,cV.hasShield, cV.ID)));
                        }
                        if(cV.currentInformation == 2) {
                            server.sendToAllExceptTCP(connection.getID(), new ServerVariables(new VBomb(cV.bombXPos,cV.bombYPos,cV.kind)));
                        }
                        if(cV.currentInformation == 3) {
                            server.sendToAllExceptTCP(connection.getID(), new ServerVariables(new VBomb(cV.bombXPos,cV.bombYPos,cV.kind), new VPlayer(cV.playerXPos,cV.playerYPos,cV.hasShield, cV.ID)));
                        }
                    if(running && object instanceof String) {
                        useCommands((String) object);
                    }
                    }
                }
            });
    }

    private void useCommands(String command) {
        if(command.equals("nuke")) {

        }else {
            gui.printConsole("One client tried the command: '" + command +"'");
        }
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
