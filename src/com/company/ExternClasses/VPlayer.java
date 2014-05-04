package com.company.ExternClasses;

public class VPlayer {

    public double xPosition;
    public double yPosition;
    public static enum DIRECTION {UP, DOWN, LEFT, RIGHT}
    public DIRECTION lookDirection;
    public int ID;


    //private double health;

    public boolean hasShield;

    public VPlayer(double xPosition, double yPosition, boolean hasShield, DIRECTION lookDirection, int ID) {
        this.hasShield = hasShield;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.lookDirection = lookDirection;
        this.ID = ID;
    }

    public VPlayer(double xPosition, double yPosition, boolean hasShield, int ID) {
        this.hasShield = hasShield;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.lookDirection = DIRECTION.DOWN;
        this.ID = ID;
    }

}
