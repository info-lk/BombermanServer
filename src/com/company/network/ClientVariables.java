package com.company.network;

/**
 * Created by winterj on 02.04.2014.
 */
public class ClientVariables {
    byte currentInformation = -1; // 1 = Playerpos only; 2 = new Bomb only; 3 = Player and Bomb
    public double playerXPos, playerYPos;
    public boolean hasShield;

    public double bombXPos, bombYPos;
    public int kind;

    public ClientVariables(double playerXPos,double playerYPos, boolean hasShield) {
        this.playerXPos = playerXPos;
        this.playerYPos = playerYPos;
        this.hasShield = hasShield;
        currentInformation = 1;
    }

    public ClientVariables(double bombXPos, double bombYPos, int kind) {
        this.bombXPos = bombXPos;
        this.bombYPos = bombYPos;
        this.kind = kind;
        currentInformation = 2;
    }

    public ClientVariables(double playerXPos, double playerYPos, boolean hasShield, double bombXPos, double bombYPos) {
        this.playerXPos = playerXPos;
        this.playerYPos = playerYPos;
        this.hasShield = hasShield;
        this.bombXPos = bombXPos;
        this.bombYPos = bombYPos;
        currentInformation = 3;
    }


}
