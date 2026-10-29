package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.models.GameModel;

public interface AbstractAction {
    void execute(GameModel model);
}
