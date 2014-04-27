package com.company.ExternClasses;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    public String tiles = "";

    public Map(int width, int height, double wallChance, double destructableWallChance) {
        this.width = width;
        this.height = height;

        Random r = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) { //Wall
                    tiles += 0;
                } else if(x == 1 && y == 1){
                    tiles += "2";
                } else {
                    int random = r.nextInt(100);

                    if (random <= wallChance) {
                        tiles += "0"; // Wall
                    } else if (random <= destructableWallChance) {
                        tiles += "1"; // Destructable Wall
                    } else {
                        tiles += "2"; // Ground
                    }
                }
            }

            tiles += "\n";
        }

        tiles = tiles.trim();
        System.out.println(tiles.replace("0", "# ").replace("1", "+ ").replace("2", "  "));
    }

    public String getMapRepresentation(){
        return tiles.replace("0", "# ").replace("1", "+ ").replace("2", "  ");
    }
}
