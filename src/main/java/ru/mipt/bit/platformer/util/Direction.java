package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {

    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, 180f),
    RIGHT(1, 0, 0);

    private int xShift;
    private int yShift;
    private float rotation;

    Direction(int xShift, int yShift, float rotation) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.rotation = rotation;
    }

    public GridPoint2 getShift() {
        return new GridPoint2(xShift, yShift);
    }

    public float getRotation() {
        return rotation;
    }

    public static Direction getDirectionByRotation(float rotation) {
        switch ((int) rotation) {
            case (90): return Direction.UP;
            case (-90): return Direction.DOWN;
            case (180): return Direction.LEFT;
            case (0): return Direction.RIGHT;
        }
        throw new RuntimeException("Некорректное направление");
    }
}
