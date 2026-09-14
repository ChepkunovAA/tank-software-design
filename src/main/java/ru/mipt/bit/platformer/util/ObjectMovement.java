package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;

public enum ObjectMovement {

    UP(0, 1, 90f, Keys.W, Keys.UP),
    DOWN(0, -1, -90f, Keys.S, Keys.DOWN),
    LEFT(-1, 0, 180f, Keys.A, Keys.LEFT),
    RIGHT(1, 0, 0, Keys.D, Keys.RIGHT);

    private int xShift;
    private int yShift;
    private float direction;
    private int wasdKey;
    private int arrowKey;

    ObjectMovement(int xShift, int yShift, float direction, int wasdKey, int arrowKey) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.direction = direction;
        this.wasdKey = wasdKey;
        this.arrowKey = arrowKey;
    }

    public GridPoint2 moveFrom(GridPoint2 start) {
        return new GridPoint2(start).add(xShift, yShift);
    }

    public float getDirection() {
        return direction;
    }

    public boolean isActive(Input input) {
        return (input.isKeyPressed(wasdKey) || input.isKeyPressed(arrowKey));
    }
}
