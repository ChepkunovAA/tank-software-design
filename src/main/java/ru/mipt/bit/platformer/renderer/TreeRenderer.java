package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.models.FieldObject;
import ru.mipt.bit.platformer.models.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeRenderer extends FieldObjectRenderer {
    public TreeRenderer(String texturePath) {
        super(texturePath);
    }

    public void render(Batch batch, Tree tree, TiledMapTileLayer groundLayer) {
        moveRectangleAtTileCenter(groundLayer, position, tree.getCoordinates());
        super.renderObject(batch, tree);
    }
}
