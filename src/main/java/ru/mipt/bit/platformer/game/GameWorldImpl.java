package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.FieldObject;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GameWorldImpl implements GameWorld {

    private final List<FieldObject> objects = new ArrayList<>();
    private final List<WorldObserver> observers = new ArrayList<>();

    private Tank playerTank;

    private final int fieldWidth;
    private final int fieldHeight;

    public GameWorldImpl(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Tank> getTanks() {
        List<Tank> tanks = objectsOfType(Tank.class);
        if (playerTank != null) {
            tanks.remove(playerTank);
            tanks.add(0, playerTank);
        }
        return tanks;
    }

    @Override
    public List<Tank> getNpcTanks() {
        List<Tank> tanks = getTanks();
        tanks.remove(playerTank);
        return tanks;
    }

    public List<Tree> getTrees() {
        return objectsOfType(Tree.class);
    }

    public List<Bullet> getBullets() {
        return objectsOfType(Bullet.class);
    }

    public void addPlayerTank(Tank tank) {
        this.playerTank = tank;
        addObject(tank);
    }

    @Override
    public void addObject(FieldObject object) {
        objects.add(object);
        for (WorldObserver observer : observers) {
            observer.onObjectAdded(object);
        }
    }

    @Override
    public void removeObject(FieldObject object) {
        if (object == playerTank) {
            playerTank = null;
        }
        if (objects.remove(object)) {
            for (WorldObserver observer : observers) {
                observer.onObjectRemoved(object);
            }
        }
    }

    @Override
    public List<FieldObject> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    @Override
    public void addObserver(WorldObserver observer) {
        observers.add(observer);
        for (FieldObject object : new ArrayList<>(objects)) {
            observer.onObjectAdded(object);
        }
    }

    @Override
    public void removeObserver(WorldObserver observer) {
        observers.remove(observer);
    }

    public Set<GridPoint2> getObstacles() {
        Set<GridPoint2> coordinates = new HashSet<>();
        for (FieldObject object : objects) {
            if (!object.isDestroyed()) {
                coordinates.addAll(object.getOccupiedCoordinates());
            }
        }
        return coordinates;
    }

    @Override
    public List<FieldObject> getObjectsAt(GridPoint2 coordinates) {
        List<FieldObject> result = new ArrayList<>();
        for (FieldObject object : objects) {
            if (!object.isDestroyed() && object.getOccupiedCoordinates().contains(coordinates)) {
                result.add(object);
            }
        }
        return result;
    }

    @Override
    public boolean isAvailable(GridPoint2 coordinates) {
        return isInBounds(coordinates) && !getObstacles().contains(coordinates);
    }

    @Override
    public boolean isInBounds(GridPoint2 coordinates) {
        return coordinates.x >= 0 && coordinates.x < fieldWidth &&
                coordinates.y >= 0 && coordinates.y < fieldHeight;
    }

    private <T extends FieldObject> List<T> objectsOfType(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (FieldObject object : objects) {
            if (type.isInstance(object)) {
                result.add(type.cast(object));
            }
        }
        return result;
    }
}
