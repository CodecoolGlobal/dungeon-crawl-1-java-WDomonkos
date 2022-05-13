package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.SoundClipTest;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.door.Closeddoor;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int attackStrength;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public Actor(Cell cell, int health, int attackStrength) {
        this(cell);
        this.health = health;
        this.attackStrength = attackStrength;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.STAIRS) {
            new SoundClipTest("stairs.wav");
            if (this instanceof Player) {
                if (((Player) this).getPlayerOnMap() == 1) {
                    ((Player) this).setPlayerOnMap(2);
                    ((Player) this).setChangeMap(true);
                } else if (((Player) this).getPlayerOnMap() == 2) {
                    ((Player) this).setPlayerOnMap(3);
                    ((Player) this).setChangeMap(true);
                }
            }
        }

        if (nextCell.getType() == CellType.WALL && nextCell.getItem() instanceof Closeddoor) {
            ((Player) cell.getActor()).tryToUnlockDoor(cell, nextCell);
        }

        if(nextCell.getType() == CellType.FLOOR || nextCell.getType() == CellType.FLOORTHREE || nextCell.getType() == CellType.TOWER || nextCell.getType() == CellType.BRIDGE || nextCell.getType() == CellType.FLOORTWO || nextCell.getType() == CellType.STAIRS || nextCell.getType() == CellType.WIN_TILE){
            if (nextCell.getActor() == null) {
                if (cell.getSecondActor() != null) {
                    if (cell.getSecondActor() instanceof Skeleton) {
                        cell.setActor(cell.getSecondActor());
                    } else {
                        cell.setActor(cell.getSecondActor());
                    }
                    cell.setSecondActor(null);
                } else {
                    cell.setActor(null);
                }
                nextCell.setActor(this);
                this.cell = nextCell;
            } else {                               //...if there is a monster on the cell:
                new SoundClipTest("punch1.wav");
                attack(this.getCell(), nextCell);
            }
        }
    }

    public void attack(Cell cell, Cell nextCell) {
        Actor monsterAttacked = nextCell.getActor();
        System.out.println("FIGHT!!!! " + this.getTileName() + " attacked the " + monsterAttacked.getTileName());
        monsterAttacked.setHealth(monsterAttacked.getHealth()-this.getAttackStrength());
        if (monsterAttacked.getHealth()>0) {                     // ...if the monster survived, it fights back:
            new SoundClipTest("punch1.wav");
            System.out.println( monsterAttacked.getTileName() + " fights back!");
            this.setHealth(this.getHealth()-monsterAttacked.getAttackStrength());
            if (this.getHealth()<=0) {                   // ...if attacker dies:
                System.out.println("The attacker "+ this.getTileName()+ " died!");
                cell.setActor(null);
            } else {                                     // ...if attacker survives:
                new SoundClipTest("punch1.wav");
                nextCell.setSecondActor(monsterAttacked);
                cell.setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
                System.out.println("Both survived.");
            }
        } else {                                         // ...if the monster died:
            System.out.println("The attacked "+ monsterAttacked.getTileName() +" character was killed!");
            cell.setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
