package ru.mipt.bit.platformer.graphic.renderer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.objects.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeRenderer extends FieldObjectRenderer<Tree> {

    private final TiledMapTileLayer groundLayer;

    public TreeRenderer(String texturePath, TiledMapTileLayer groundLayer) {
        super(Tree.class, texturePath);
        this.groundLayer = groundLayer;
    }

    @Override
    protected void positionAt(Tree tree) {
        moveRectangleAtTileCenter(groundLayer, position, tree.getCoordinates());
    }
}
