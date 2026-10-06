package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

public class GameControllerImpl implements GameController {

    private final GameModel gameModel;

    public GameControllerImpl(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void update (float deltaTime) {
        Tank playerTank = gameModel.getPlayerTank();
        playerTank.processMovement(deltaTime);
    }

}