package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.controller.GameController;
import ru.mipt.bit.platformer.controller.RenderController;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.models.GameModel;

public class GameDesktopLauncher implements ApplicationListener {

    private GameModel gameState;
    private GameController gameController;
    private RenderController renderController;


    @Override
    public void create() {
        gameState = new GameModel();

        KeyboardInputHandler inputHandler = new KeyboardInputHandler();
        gameController = new GameController(inputHandler);
        renderController = new RenderController();
    }

    @Override
    public void render() {
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        gameController.update(gameState, deltaTime);
        renderController.render(gameState);
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        renderController.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
