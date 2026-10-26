package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameModel {
    private final Tank playerTank;
    private final List<Tank> npcTanks;
    private final List<Tank> allTanks;
    private final List<Tree> trees;
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

    public void tryMove(Tank tank, Direction direction) {
        tank.tryMove(direction, getObstacles(), fieldWidth, fieldHeight);
    }
}