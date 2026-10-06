package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends FieldObject {

    private GridPoint2 targetCoordinates;
    private float movementProgress;

    private static final float MOVEMENT_SPEED = 0.4f;

    public Tank(GridPoint2 coordinates) {
        super(coordinates);
        this.targetCoordinates = coordinates;
        this.movementProgress = 1f;
    }

    public void tryMove(Direction movement, List<Tree> obstacles) {
        if (!isMovementEnded()) {
            return;
        }
        GridPoint2 target = new GridPoint2(coordinates).add(movement.getShift());
        rotation = movement.getRotation();
        for (Tree obstacle : obstacles) {
            if (target.equals(obstacle.getCoordinates())) {
                return;
            }
        }
        movementProgress = 0f;
        targetCoordinates = target;
    }

    public void processMovement(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isMovementEnded()) {
            this.coordinates = targetCoordinates;
        }
    }

    public boolean isMovementEnded() {
        return movementProgress == 1f;
    }

    public GridPoint2 getTargetCoordinates() {
        return targetCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }
}
