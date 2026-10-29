package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.models.GameModel;

public interface LevelFactory {

    GameModel generate();
}