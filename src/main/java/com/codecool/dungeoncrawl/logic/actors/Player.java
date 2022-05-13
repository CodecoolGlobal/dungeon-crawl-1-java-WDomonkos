package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.SoundClipTest;
import com.codecool.dungeoncrawl.logic.Cell;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.items.door.Closeddoor;
import com.codecool.dungeoncrawl.logic.items.door.Door;
import com.codecool.dungeoncrawl.logic.items.door.Opendoor;

import java.util.ArrayList;
import java.util.Arrays;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Player extends Actor implements Movable {
    private ArrayList<Item> inventory;

    public static final int HEALTH = 10;
    public static final int ATTACK_STRENGTH = 5;
    private int playerOnMap;
    private boolean changeMap = false;


    public Player(Cell cell) {
        super(cell, HEALTH, ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
        setPlayerOnMap(1);
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
    }

    public String getTileName() {
        return "player";
    }

    public boolean getChangeMap() {
        return changeMap;
    }

    public void setChangeMap(boolean changeMap) {
        this.changeMap = changeMap;
    }


    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public ArrayList getInventory() {
        return inventory;
    }

    public void pickUpItem() {
        if (this.getCell().getItem() != null && !(this.getCell().getItem() instanceof Door)) {
            new SoundClipTest("item-pick-up.wav");

            addToInventory(this.getCell().getItem());
            if (this.getCell().getItem() instanceof Sword) {
                this.setAttackStrength(getAttackStrength()+2);
            } else if (this.getCell().getItem() instanceof Health) {
                this.setHealth(getHealth()+3);
                new SoundClipTest("potion-drink.wav");

            }
            System.out.println(inventory);
            this.getCell().setItem(null);
        }
    }

    public void tryToUnlockDoor(Cell cell, Cell nextCell){
        int counter = 0;
        for (Item item : inventory) {
            counter += 1;
            if (item instanceof Key) {          //ha van nála key
                removeFromInventory(item);
                nextCell.setType(CellType.FLOOR);
                nextCell.setItem(new Opendoor(nextCell));
                new SoundClipTest("keys-jingling.wav");
                break;
            } else if (inventory.size() == counter) {       //ha nincs nála key
                System.out.println("Key missing to open door.");
            }
        }
    }


    public String displayInventory() {
        StringBuilder display = new StringBuilder();
        int keyCount = 0;
        int swordCount = 0;
        HashMap<String, Integer> inventory_dict = new HashMap();
        for(Item item : inventory){
            if(item instanceof Key){
                keyCount+=1;
                if(keyCount <= 1){
                    inventory_dict.put(item.getTileName(), keyCount);
                }else{
                    inventory_dict.put("Key", keyCount);
                }

            } else if (item instanceof Sword){
                swordCount += 1;
                if(keyCount <= 1){
                    inventory_dict.put(item.getTileName(), swordCount);
                }else{
                    inventory_dict.put("Sword", swordCount);
                }

            }
        }
        for(HashMap.Entry<String, Integer> element: inventory_dict.entrySet()){
            display.append(element.getKey()+": "+ element.getValue());
            display.append("\n");
        }


        return display.toString();
    }


    public int getPlayerOnMap() {
        return playerOnMap;
    }

    public void setPlayerOnMap(int playerOnMap) {
        this.playerOnMap = playerOnMap;
    }
}
