package com.company.ExternClasses;

public class Player {

    public double xPosition;
    public double yPosition;


    //private double health;

    public boolean hasShield;

    public Player(double xPosition, double yPosition, boolean hasShield) {
        this.hasShield = hasShield;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

}
