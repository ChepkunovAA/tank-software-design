package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.models.Tree;
import ru.mipt.bit.platformer.renderer.TankRenderer;
import ru.mipt.bit.platformer.renderer.TreeRenderer;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class RenderControllerImpl implements RenderController {

    private final Batch batch;

    private final TiledMap level;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;

    private final MapRenderer levelRenderer;
    private final TankRenderer tankRenderer;
    private final TreeRenderer treeRenderer;

    public RenderControllerImpl(TiledMap level, TankRenderer tankRenderer, TreeRenderer treeRenderer) {
        this.batch = new SpriteBatch();
        this.tankRenderer = tankRenderer;
        this.treeRenderer = treeRenderer;

	    this.level = level;
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void render(GameModel model) {
        levelRenderer.render();

        batch.begin();
        for (Tank tank : model.getTanks()) {
            tankRenderer.render(batch, tank, tileMovement);
        }
        for (Tree tree : model.getTrees()) {
            treeRenderer.render(batch, tree, groundLayer);
        }
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        tankRenderer.dispose();
        treeRenderer.dispose();
        level.dispose();
    }
}