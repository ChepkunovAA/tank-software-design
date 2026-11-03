package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.actions.NpcActionGenerator;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

import java.util.ArrayList;
import java.util.List;

public class GameControllerImpl implements GameController {

    private final GameModel gameModel;
    private final NpcActionGenerator npcActionGenerator;

    public GameControllerImpl(GameModel gameModel, NpcActionGenerator npcActionGenerator) {
        this.gameModel = gameModel;
        this.npcActionGenerator = npcActionGenerator;
    }

    @Override
    public List<AbstractAction> getActions() {
        List<AbstractAction> actions = new ArrayList<>();
        for (Tank npcTank : gameModel.getNpcTanks()) {
            if (npcTank.isFree()) {
                actions.add(npcActionGenerator.getNextAction(npcTank));
            }
        }
        return actions;
    }
}