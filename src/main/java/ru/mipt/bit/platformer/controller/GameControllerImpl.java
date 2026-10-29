package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.MoveAction;

import java.util.Random;

public class GameControllerImpl implements GameController {

    private final GameModel gameModel;
    private final Random random;

    public GameControllerImpl(GameModel gameModel) {
        this(gameModel, new Random());
    }

    GameControllerImpl(GameModel gameModel, Random random) {
        this.gameModel = gameModel;
        this.random = random;
    }

    public void update(float deltaTime) {
        gameModel.getPlayerTank().processMovement(deltaTime);

        for (Tank npcTank : gameModel.getNpcTanks()) {
            if (npcTank.isMovementEnded()) {
                new MoveAction(npcTank, randomDirection()).execute(gameModel);
            }
            npcTank.processMovement(deltaTime);
        }
    }

    private Direction randomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
}