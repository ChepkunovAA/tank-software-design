package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.actions.*;
import ru.mipt.bit.platformer.controller.GameController;
import ru.mipt.bit.platformer.controller.GameControllerImpl;
import ru.mipt.bit.platformer.controller.RenderController;
import ru.mipt.bit.platformer.controller.RenderControllerImpl;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.level.ConfiguredLevelFactory;
import ru.mipt.bit.platformer.level.LevelFactory;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.renderer.TankRenderer;
import ru.mipt.bit.platformer.renderer.TreeRenderer;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.config.ConfigureConstants.*;

public class GameDesktopLauncher implements ApplicationListener {

    private GameModel gameState;
    private InputHandler inputHandler;
    private GameController gameController;
    private RenderController renderController;


    @Override
    public void create() {
        GameStartLauncher();

        initInputHandler();

        initGameController();

        initGraphic();
    }

    private void initGameController() {
        NpcActionGenerator npcActionGenerator = new RandomNpcActionGenerator();
        gameController = new GameControllerImpl(gameState, npcActionGenerator);
    }

    private void GameStartLauncher() {
        LevelFactory levelFactory = new ConfiguredLevelFactory();
        gameState = levelFactory.generate();
    }

    private void initGraphic() {
        TiledMap level = new TmxMapLoader().load(LEVEL_TEXTURE);
        TankRenderer tankRenderer = new TankRenderer(TANK_TEXTURE);
        TreeRenderer treeRenderer = new TreeRenderer(TREE_TEXTURE);
        renderController = new RenderControllerImpl(level, tankRenderer, treeRenderer);
    }

    private void initInputHandler() {
        Tank playerTank = gameState.getPlayerTank();
        inputHandler = new KeyboardInputHandler()
                .addKey(Input.Keys.W, MoveAction.UP(playerTank))
                .addKey(Input.Keys.UP, MoveAction.UP(playerTank))
                .addKey(Input.Keys.S, MoveAction.DOWN(playerTank))
                .addKey(Input.Keys.DOWN, MoveAction.DOWN(playerTank))
                .addKey(Input.Keys.A, MoveAction.LEFT(playerTank))
                .addKey(Input.Keys.LEFT, MoveAction.LEFT(playerTank))
                .addKey(Input.Keys.D, MoveAction.RIGHT(playerTank))
                .addKey(Input.Keys.RIGHT, MoveAction.RIGHT(playerTank))
                .addKey(Input.Keys.SPACE, new ShootAction(playerTank));
    }

    @Override
    public void render() {
        List<AbstractAction> actions = getActions();
        executeActions(actions);
        live();
        renderController.render(gameState);
    }

    private void live() {
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        gameState.live(deltaTime);
    }

    private void executeActions(List<AbstractAction> actions) {
        for (AbstractAction action : actions) {
            if (action != null) {
                action.execute(gameState);
            }
        }
    }

    private List<AbstractAction> getActions() {
        List<AbstractAction> actions = new ArrayList<>();
        actions.add(new BulletShiftAction());
        actions.add(inputHandler.getActionTriggered());
        actions.addAll(gameController.getActions());
        return actions;
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
