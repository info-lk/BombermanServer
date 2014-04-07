package com.company.network;

import com.company.ExternClasses.Bomb;
import com.company.ExternClasses.Map;
import com.company.ExternClasses.Player;

/**
 * Created by winterj on 26.03.2014.
 */
public class ServerVariables {

    public byte currentInformation = -1; //0 = Bomb and Player; 1 = Map; 2 = Player; 3 = command; 4 = bomb

    public Map map;

    public Player player; //The player to update

    public Bomb bomb;

    public byte command = -1;  //-1 = nothing|0 = pause| 1 = kick| 2 = ?|

    public ServerVariables(Map map) {
        this.map = map;
        currentInformation = 1;
    }

    public ServerVariables(Player player) {
        this.player = player;
        currentInformation = 2;
    }

    public ServerVariables(byte command) {
        this.command = command;
        currentInformation = 3;
    }

    public ServerVariables(Bomb bomb) {
        this.bomb = bomb;
        currentInformation = 4;
    }

    public ServerVariables(Bomb bomb, Player player) {
        this.player = player;
        this.bomb = bomb;
        currentInformation = 0;
    }
}
