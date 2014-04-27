package com.company.ExternClasses;



public class Tile {

    private boolean destructable;
    private boolean passable;

    public Tile(boolean destructable, boolean passable) {
        this.destructable = destructable;
        this.passable = passable;
    }

    public boolean isWall() {
        return !destructable & !passable;
    }

    public boolean isDestructableWall() {
        return destructable & !passable;
    }

}
