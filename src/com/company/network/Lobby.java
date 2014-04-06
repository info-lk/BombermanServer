package com.company.network;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;

/**
 * Created by Julian on 04.04.2014.
 */
public class Lobby {
    private static final int MAX_PLAYERS = 4;
    ArrayList<Connection> players;
    int count;
    private boolean allowConnect;

    public Lobby() {
        players = new ArrayList();
        count = 0;
    }

    public void open() {
        allowConnect = true;
    }

    public void close() {
        allowConnect = false;
    }

    public byte join(Connection connection) {
        if(count < MAX_PLAYERS || !allowConnect) {
            players.add(connection);
            count++;
            return 1;
        } else {
            return 0;
        }
    }

    public Connection[] toArray() {
        return players.toArray(new Connection[0]);
    }

    public Connection[] exclude(Connection connection) {
        Connection[] copy = this.toArray();
        Connection[] excluded = new Connection[copy.length-1];
        int count = 0;
        for(int i = 0; i < excluded.length; i++) {
            if(!copy[i].equals(connection)) {
                excluded[count] = copy[i];
                count++;
            }
        }
        return excluded;
    }

    public boolean allowsConnect() {
        return allowConnect;
    }
}
