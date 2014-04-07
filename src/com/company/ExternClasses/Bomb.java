package com.company.ExternClasses;

/**
 * Created by Julian on 07.04.2014.
 */
public class Bomb {
    public double layBombAtX, layBombAtY;

    public int kind; //1 = Water, 2 = ?... rest = normal

    public Bomb(double layBombAtX, double layBombAtY, int kind) {
        this.layBombAtX = layBombAtX;
        this.layBombAtY = layBombAtY;
        this.kind = kind;
    }
}
