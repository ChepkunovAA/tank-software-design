package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.models.GameModel;

public interface RenderController {
    void render(GameModel model);

    void dispose();
}
