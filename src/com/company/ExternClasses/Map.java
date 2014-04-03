package com.company.ExternClasses;

import java.util.Random;

public class Map {

    private int width;
    private int height;
    private float blockWidth;
    private float blockHeight;
    public Tile[][] tile;

    private double wallChance = 10;
    private double destructableWallChance = 15;

    private Random r;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tile = new Tile[width][height];

        r = new Random();

        initialize();
    }

    public Map(int width, int height, double wallChance, double destructableWallChance) {
        this.width = width;
        this.height = height;

        tile = new Tile[width][height];

        this.destructableWallChance = destructableWallChance;
        this.wallChance = wallChance;

        r = new Random();

        initialize();
    }

    private void initialize() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) { //Wall
                    tile[x][y] = new Tile(false, false);
                } else {
                    tile[x][y] = generateRandomTile();
                }
            }
            System.out.println();
        }
    }

    private Tile generateRandomTile() {
        int random = r.nextInt(100);
        if (random <= wallChance) {
            return new Tile(false, false); // Wall
        } else if (random <= destructableWallChance) {
            return new Tile(true, false); // Destructable Wall
        } else {
            return new Tile(false, true); // Ground
        }
    }

    public Tile getRandomTile() {
        int x = r.nextInt(width - 2) + 1;
        int y = r.nextInt(height - 2) + 1;

        return tile[x][y];
    }
}
