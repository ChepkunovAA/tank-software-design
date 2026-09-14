package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends FieldObject {

    private GridPoint2 targetCoordinates;
    private float movementProgress;

    private static final float MOVEMENT_SPEED = 0.4f;

    public Tank(String textureSource, GridPoint2 coordinates) {
        super(textureSource, coordinates);
        this.targetCoordinates = coordinates;
        this.movementProgress = 1f;
    }

    public void tryMove(ObjectMovement movement, Tree obstacle) {
        if (isMovementEnded()) {
            return;
        }
        GridPoint2 target = movement.moveFrom(this.getCoordinates());
        if (!target.equals(obstacle.getCoordinates())) {
            movementProgress = 0f;
            this.targetCoordinates = target;
        }
        this.rotation = movement.getDirection();
    }

    public void processMovement(TileMovement tileMovement, float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(this.getPosition(), this.getCoordinates(), targetCoordinates, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (!isMovementEnded()) {
            this.coordinates = targetCoordinates;
        }
    }

    public boolean isMovementEnded() {
        return movementProgress != 1f;
    }
}
