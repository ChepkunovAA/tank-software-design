package ru.mipt.bit.platformer.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class RenderControllerImpl implements RenderController {

    private final TiledMap level;
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final Scene scene;

    public RenderControllerImpl(TiledMap level, Scene scene) {
        this.level = level;
        this.batch = new SpriteBatch();
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.scene = scene;
    }

    @Override
    public void render() {
        levelRenderer.render();

        batch.begin();
        scene.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        scene.dispose();
        level.dispose();
    }
}
