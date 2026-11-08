package ru.mipt.bit.platformer.actions.provider;

import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.game.GameWorld;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public class ActionProviderImpl implements ActionProvider {

    private final GameWorld world;
    private final NpcActionGenerator npcActionGenerator;

    public ActionProviderImpl(GameWorld world, NpcActionGenerator npcActionGenerator) {
        this.world = world;
        this.npcActionGenerator = npcActionGenerator;
    }

    @Override
    public List<AbstractAction> getActions() {
        List<AbstractAction> actions = new ArrayList<>();
        for (Tank npcTank : world.getNpcTanks()) {
            actions.add(npcActionGenerator.getNextAction(npcTank));
        }
        return actions;
    }
}
