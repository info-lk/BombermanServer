package com.company.network;

import com.company.ExternClasses.Map;
import com.company.ExternClasses.Player;

/**
 * Created by winterj on 26.03.2014.
 */
public class ServerVariables {

    public byte currentInformation = -1; //0 = All; 1 = Map; 2 = Players; 3 = command

    public Map map;

    public Player[] player;

    public byte command;  //-1 = error| 0 = pause| 1 = kick| 2 = ?|

    public ServerVariables(Map map) {
        this.map = map;
        currentInformation = 1;
    }

    public ServerVariables(Player[] player) {
        this.player = player;
        currentInformation = 2;
    }

    public ServerVariables(byte command) {
        this.command = command;
        currentInformation = 3;
    }

    public ServerVariables(Map map, Player[] player, byte command) {
        this.map = map;
        this.player = player;
        this.command = command;
        currentInformation = 0;
    }
}
