package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.game.GameWorld;

public interface LevelFactory {

    GameWorld generate();
}