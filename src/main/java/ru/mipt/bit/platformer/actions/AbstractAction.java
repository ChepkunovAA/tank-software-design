package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.models.GameModel;

public interface AbstractAction {
    void execute(GameModel model);
}
