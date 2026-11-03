package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.actions.AbstractAction;


public interface InputHandler {
    AbstractAction getActionTriggered();
}
