package ru.mipt.bit.platformer.actions.provider;

import ru.mipt.bit.platformer.actions.AbstractAction;

import java.util.List;

public interface ActionProvider {
    List<AbstractAction> getActions();
}
