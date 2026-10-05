package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.controller.GameController;
import ru.mipt.bit.platformer.controller.GameControllerImpl;
import ru.mipt.bit.platformer.controller.RenderController;
import ru.mipt.bit.platformer.controller.RenderControllerImpl;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.renderer.TankRenderer;
import ru.mipt.bit.platformer.renderer.TreeRenderer;
import ru.mipt.bit.platformer.util.AbstractAction;
import ru.mipt.bit.platformer.util.MoveAction;

import static ru.mipt.bit.platformer.config.ConfigureConstants.*;

public class GameDesktopLauncher implements ApplicationListener {

    private GameModel gameState;
    private InputHandler inputHandler;
    private GameController gameController;
    private RenderController renderController;


    @Override
    public void create() {
        gameState = new GameModel();

        inputHandler = new KeyboardInputHandler()
                .addKey(Input.Keys.W, MoveAction.UP())
                .addKey(Input.Keys.UP, MoveAction.UP())
                .addKey(Input.Keys.S, MoveAction.DOWN())
                .addKey(Input.Keys.DOWN, MoveAction.DOWN())
                .addKey(Input.Keys.A, MoveAction.LEFT())
                .addKey(Input.Keys.LEFT, MoveAction.LEFT())
                .addKey(Input.Keys.D, MoveAction.RIGHT())
                .addKey(Input.Keys.RIGHT, MoveAction.RIGHT());

        gameController = new GameControllerImpl(gameState);

        TiledMap level = new TmxMapLoader().load(LEVEL_TEXTURE);
        TankRenderer tankRenderer = new TankRenderer(TANK_TEXTURE);
        TreeRenderer treeRenderer = new TreeRenderer(TREE_TEXTURE);
        renderController = new RenderControllerImpl(level, tankRenderer, treeRenderer);
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
