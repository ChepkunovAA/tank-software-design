package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.models.Tank;

public interface NpcActionGenerator {
    public AbstractAction getNextAction(Tank tank);
}
