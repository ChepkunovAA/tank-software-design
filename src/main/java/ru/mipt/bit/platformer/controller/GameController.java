package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.actions.AbstractAction;

import java.util.List;

public interface GameController {
    List<AbstractAction> getActions();
}
