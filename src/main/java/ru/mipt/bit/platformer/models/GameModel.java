package ru.mipt.bit.platformer.models;

import java.util.List;

public class GameModel {
    private final Tank playerTank;
    private final List<Tree> obstacles;

    public GameModel(Tank playerTank, List<Tree> obstacles) {
        this.playerTank = playerTank;
        this.obstacles = obstacles;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Tree> getObstacles() {
        return obstacles;
    }
}
