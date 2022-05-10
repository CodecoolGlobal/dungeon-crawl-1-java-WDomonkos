package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.door.Opendoor;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Actor {
    private ArrayList<Item> inventory;

//    Key key = new Key();


    public final int HEALTH = 10;
    public final int ATTACK_STRENGTH = 5;


    public Player(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public ArrayList getInventory() {
        return inventory;
    }

    public void pickUpItem() {
        if (this.getCell().getItem() != null) {
            addToInventory(this.getCell().getItem());
            System.out.println(inventory);
            this.getCell().setItem(null);
        }

    }

    public void move() {};

    public void move(int dx, int dy) {
        Cell cell = getCell();
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.FLOOR || nextCell.getType() == CellType.OPENDOOR) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                setCell(nextCell);
            }
        } else if (nextCell.getType() == CellType.CLOSEDDOOR && nextCell.getActor() == null) {
            int counter = 0;
            for (Item item : inventory) {
                counter += 1;
                if (item instanceof Key) {          //ha van nála key
                    removeFromInventory(item);
                    cell.setActor(null);
                    nextCell.setType(CellType.OPENDOOR);
                    nextCell.setActor(this);
                    setCell(nextCell);
                    break;
                } else if (inventory.size() == counter) {       //ha nincs nála key
                    System.out.println("Key missing to open door.");
                }
//                if (inventory.contains(instanceof Key)) {
//                  if (inventory.stream().anyMatch(item -> item instanceof Key)) {
            }
        } else {                               //...if there is a monster on the cell:
            attack(nextCell);
        }
    }
}
