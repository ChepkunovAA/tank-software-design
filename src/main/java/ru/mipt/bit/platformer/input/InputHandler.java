package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.util.AbstractAction;


public interface InputHandler {
    AbstractAction getActionTriggered();
}
