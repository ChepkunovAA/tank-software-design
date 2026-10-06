package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

public class GameController {

    private final GameModel gameModel;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void update (float deltaTime) {
        Tank playerTank = gameModel.getPlayerTank();
        playerTank.processMovement(deltaTime);
    }

}