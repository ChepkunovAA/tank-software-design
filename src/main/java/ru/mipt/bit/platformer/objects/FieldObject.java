package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collections;
import java.util.Set;

public abstract class FieldObject {

    protected GridPoint2 coordinates;
    protected float rotation;

    private boolean destroyed;

    public FieldObject(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        this.rotation = 0f;
    }

    public void live(float tick) {
    }

    public void handleHit(int damage) {
    }

    public Set<GridPoint2> getOccupiedCoordinates() {
        return Collections.singleton(coordinates);
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }
}
