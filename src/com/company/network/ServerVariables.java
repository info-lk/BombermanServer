package com.company.network;

import com.company.ExternClasses.Bomb;
import com.company.ExternClasses.Map;
import com.company.ExternClasses.Player;

/**
 * Created by winterj on 26.03.2014.
 */
public class ServerVariables {

    public enum CURRENT_INFORMATION {NONE, BOMB_PLAYER, MAP, PLAYER, BOMB, COMMAND, PLAYER_LIST}
    public CURRENT_INFORMATION current = CURRENT_INFORMATION.NONE; //0 = Bomb and Player; 1 = Map; 2 = Player; 3 = command; 4 = bomb

    public Map map;

    public Player player; //The player to update
    public Player[] players;

    public Bomb bomb;

    public byte command = -1;  //-1 = nothing|0 = pause| 1 = kick| 2 = ?|

    public ServerVariables(Map map) {
        this.map = map;
        current = CURRENT_INFORMATION.MAP;
    }

    public ServerVariables(Player player) {
        this.player = player;
        current = CURRENT_INFORMATION.PLAYER;
    }

    public ServerVariables(Player[] players) {
        this.players = players;
        current = CURRENT_INFORMATION.PLAYER_LIST;
    }

    public ServerVariables(byte command) {
        this.command = command;
        current = CURRENT_INFORMATION.COMMAND;
    }

    public ServerVariables(Bomb bomb) {
        this.bomb = bomb;
        current = CURRENT_INFORMATION.BOMB;
    }

    public ServerVariables(Bomb bomb, Player player) {
        this.player = player;
        this.bomb = bomb;
        current = CURRENT_INFORMATION.BOMB_PLAYER;
    }
}
