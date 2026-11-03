package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameModel {
    private Tank playerTank;
    private final List<Tank> npcTanks;
    private final List<Tank> allTanks;
    private final List<Tree> trees;

    private final List<Bullet> bullets;

    private final int fieldWidth;
    private final int fieldHeight;

    public GameModel(Tank playerTank, List<Tank> npcTanks, List<Tree> trees,
                     int fieldWidth, int fieldHeight) {
        this.playerTank = playerTank;
        this.npcTanks = npcTanks;
        this.trees = trees;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        this.allTanks = new ArrayList<>(npcTanks.size() + 1);
        allTanks.add(playerTank);
        allTanks.addAll(npcTanks);

        this.bullets = new ArrayList<>();
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Tank> getNpcTanks() {
        return npcTanks;
    }

    public List<Tank> getTanks() {
        return allTanks;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public void destroyTank(Tank tank) {
        if (playerTank == tank) {
            playerTank = null;
        }
        npcTanks.remove(tank);
        allTanks.remove(tank);
    }

    public void destroyBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public Set<GridPoint2> getObstacles() {
        Set<GridPoint2> coordinates = new HashSet<>();
        for (Tank tank : allTanks) {
            coordinates.add(tank.getCoordinates());
            coordinates.add(tank.getTargetCoordinates());
        }
        for (Tree tree : trees) {
            coordinates.add(tree.getCoordinates());
        }
        return coordinates;
    }

    public List<FieldObject> getAllObjects() {
        List<FieldObject> objects = new ArrayList<>();
        objects.addAll(allTanks);
        objects.addAll(trees);
        objects.addAll(bullets);
        return objects;
    }

    public void tryMove(Tank tank, Direction direction) {
        tank.tryMove(direction, this);
    }

    public void shoot(Tank tank) {
        Bullet newBullet = tank.tryShoot(fieldWidth, fieldHeight);
        if (newBullet != null) {
            bullets.add(newBullet);
        }
    }

    public void live(float tick) {
        for (FieldObject object : getAllObjects()) {
            object.live(tick);
        }
    }
    
    public boolean isAvaibale(GridPoint2 coordinates) {
        return !(getObstacles().contains(coordinates) ||
                coordinates.x < 0 || coordinates.x >= fieldWidth ||
                coordinates.y < 0 || coordinates.y >= fieldHeight);
    }

    public List<Bullet> getBullets() {
        return this.bullets;
    }
}