package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.controller.GameController;
import ru.mipt.bit.platformer.controller.RenderController;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.util.AbstractAction;
import ru.mipt.bit.platformer.util.MoveAction;

public class GameDesktopLauncher implements ApplicationListener {

    private GameModel gameState;
    private KeyboardInputHandler inputHandler;
    private GameController gameController;
    private RenderController renderController;


    @Override
    public void create() {
        gameState = new GameModel();

        inputHandler = new KeyboardInputHandler();
        inputHandler.addKey(Input.Keys.W, MoveAction.UP());
        inputHandler.addKey(Input.Keys.UP, MoveAction.UP());
        inputHandler.addKey(Input.Keys.S, MoveAction.DOWN());
        inputHandler.addKey(Input.Keys.DOWN, MoveAction.DOWN());
        inputHandler.addKey(Input.Keys.A, MoveAction.LEFT());
        inputHandler.addKey(Input.Keys.LEFT, MoveAction.LEFT());
        inputHandler.addKey(Input.Keys.D, MoveAction.RIGHT());
        inputHandler.addKey(Input.Keys.RIGHT, MoveAction.RIGHT());

        gameController = new GameController(gameState);
        renderController = new RenderController();
    }

    @Override
    public void render() {
        AbstractAction inputAction = inputHandler.getActionTriggered();
        if (inputAction != null) {
            inputAction.execute(gameState);
        }

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        gameController.update(deltaTime);
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
