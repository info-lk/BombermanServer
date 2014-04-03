package com.company.ExternClasses;



public class Tile {

    private boolean destructable;
    private boolean passable;
    private boolean hasPlayer;
    private boolean needsRedraw = true;
    private boolean isDestructing = false;
    private int currentDestruction = 0;
    private Player player;

    public Tile(boolean destructable, boolean passable) {
        this.destructable = destructable;
        this.passable = passable;
        hasPlayer = false;
        player = null;
    }

    public void destroyTile() {
        if (destructable) {
            isDestructing = true;
            currentDestruction = 0;
            needsRedraw = true;
        }
    }

    public boolean isWall() {
        return !destructable & !passable;
    }

    public boolean isDestructableWall() {
        return destructable & !passable;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public String toString() {
        return "[" + destructable + "][" + passable + "][" + hasPlayer + "]";
    }



}
