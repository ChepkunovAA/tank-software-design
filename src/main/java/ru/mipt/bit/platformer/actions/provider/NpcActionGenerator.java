package ru.mipt.bit.platformer.actions.provider;

import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.objects.Tank;

public interface NpcActionGenerator {
    AbstractAction getNextAction(Tank tank);
}
