package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private ArrayList<Actor> monsters = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void addMonsterToMap(Actor monster) {
        monsters.add(monster);
        System.out.println("Monster added. List size:" + monsters.size());
    }

    public void removeDeadMonsters(){
        for (Actor monster: monsters) {
            if (monster.getHealth()<=0) {
                monsters.remove(monster);
                System.out.println("One monster removed. " + monsters.size() + " left on map.");
            }

        }
    }

    public ArrayList<Actor> getMonsters() {
        return monsters;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
