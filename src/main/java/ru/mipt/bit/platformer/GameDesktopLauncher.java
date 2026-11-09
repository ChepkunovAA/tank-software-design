package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.actions.provider.ActionProvider;
import ru.mipt.bit.platformer.actions.provider.ActionProviderImpl;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.provider.NpcActionGenerator;
import ru.mipt.bit.platformer.actions.provider.RandomNpcActionGenerator;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.game.GameLoop;
import ru.mipt.bit.platformer.graphic.RenderController;
import ru.mipt.bit.platformer.graphic.RenderControllerImpl;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.KeyboardInputHandler;
import ru.mipt.bit.platformer.level.ConfiguredLevelFactory;
import ru.mipt.bit.platformer.level.LevelFactory;
import ru.mipt.bit.platformer.game.GameWorld;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.graphic.renderer.BulletRenderer;
import ru.mipt.bit.platformer.graphic.Scene;
import ru.mipt.bit.platformer.graphic.renderer.TankRenderer;
import ru.mipt.bit.platformer.graphic.renderer.TreeRenderer;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.config.ConfigureConstants.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private GameWorld gameState;
    private InputHandler inputHandler;
    private ActionProvider actionProvider;
    private GameLoop gameLoop;
    private RenderController renderController;


    @Override
    public void create() {
        GameStartLauncher();

        initInputHandler();

        initGameLoop();

        initGraphic();
    }

    private void initGameLoop() {
        NpcActionGenerator npcActionGenerator = new RandomNpcActionGenerator();
        actionProvider = new ActionProviderImpl(gameState, npcActionGenerator);
        gameLoop = new GameLoop(gameState);
    }

    private void GameStartLauncher() {
        LevelFactory levelFactory = new ConfiguredLevelFactory();
        gameState = levelFactory.generate();
    }

    private void initGraphic() {
        TiledMap level = new TmxMapLoader().load(LEVEL_TEXTURE);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        Scene scene = new Scene(
                new TankRenderer(TANK_TEXTURE, tileMovement),
                new TreeRenderer(TREE_TEXTURE, groundLayer),
                new BulletRenderer(BULLET_TEXTURE, tileMovement));
        gameState.addObserver(scene);

        renderController = new RenderControllerImpl(level, scene);
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
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        gameLoop.update(deltaTime, getActions());
        renderController.render();
    }

    private List<AbstractAction> getActions() {
        List<AbstractAction> actions = new ArrayList<>();
        actions.add(inputHandler.getActionTriggered());
        actions.addAll(actionProvider.getActions());
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
