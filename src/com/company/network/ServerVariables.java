package com.company.network;

import com.company.ExternClasses.Map;
import com.company.ExternClasses.Player;

/**
 * Created by winterj on 26.03.2014.
 */
public class ServerVariables {

    public byte currentInformation = -1; //0 = Map; 1 = Players

    public Map map;

    public Player[] player;

    public ServerVariables(Map map) {
        this.map = map;
    }

    public ServerVariables(Player[] player) {
        this.player = player;
    }

    public ServerVariables(Map map, Player[] player) {
        this.map = map;
        this.player = player;
    }
}
