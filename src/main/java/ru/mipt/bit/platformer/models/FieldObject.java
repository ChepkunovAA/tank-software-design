package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;

public abstract class FieldObject {
    GridPoint2 coordinates;
    float rotation;

    public FieldObject(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        this.rotation = 0f;
    }

    public void live(float tick) {}

    public void handleHit(FieldObject object, GameModel model) {}

    public GridPoint2 getCoordinates() { return coordinates; }

    public float getRotation() { return rotation; }
}
