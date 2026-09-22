package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.util.Action;
import ru.mipt.bit.platformer.util.Direction;

public class GameController {

    private final KeyboardInputHandler inputHandler;

    public GameController(KeyboardInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void update(GameModel model, float deltaTime) {
        Action newAction = inputHandler.getActionTriggered();
        switch (newAction) {
            case UP: tryMovePlayerTank(model, Direction.UP); break;
            case DOWN: tryMovePlayerTank(model, Direction.DOWN); break;
            case LEFT: tryMovePlayerTank(model, Direction.LEFT); break;
            case RIGHT: tryMovePlayerTank(model, Direction.RIGHT); break;
        }

        Tank playerTank = model.getPlayerTank();
        playerTank.processMovement(deltaTime);
    }

    private void tryMovePlayerTank(GameModel model, Direction direction) {
        model.getPlayerTank().tryMove(direction, model.getObstacles());
    }
}