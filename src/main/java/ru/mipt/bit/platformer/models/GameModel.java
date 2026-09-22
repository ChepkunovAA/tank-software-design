package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class GameModel {
    private final Tank playerTank;
    private final List<Tree> obstacles;

    public GameModel() {
        this.obstacles = List.of(new Tree(new GridPoint2(1, 3)));
        this.playerTank = new Tank(new GridPoint2(1, 1));
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Tree> getObstacles() {
        return obstacles;
    }
}
