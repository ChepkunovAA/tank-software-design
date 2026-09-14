package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Tree extends FieldObject{
    public Tree(String textureSource, GridPoint2 coordinates, TiledMapTileLayer groundLayer) {
        super(textureSource, coordinates);
        moveRectangleAtTileCenter(groundLayer, this.getPosition(), this.getCoordinates());
    }

}
