package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameWorld;
import ru.mipt.bit.platformer.util.Direction;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet extends FieldObject {

    private final GameWorld world;
    private final Direction direction;
    private final int damage;

    private float movementProgress = 0f;
    private static final float MOVEMENT_SPEED = 0.1f;

    public Bullet(GameWorld world, GridPoint2 coordinates,
                  Direction direction, int damage) {
        super(coordinates);
        this.world = world;
        this.direction = direction;
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    @Override
    public void handleHit(int damage) {
        destroy();
    }

    @Override
    public void live(float tick) {
        if (isDestroyed()) {
            return;
        }
        movementProgress = continueProgress(movementProgress, tick, MOVEMENT_SPEED);
        if (!isFree()) {
            return;
        }

        GridPoint2 target = getTargetCoordinates();
        if (!world.isInBounds(target)) {
            destroy();
            return;
        }
        if (tryHitObjectAt(target)) {
            return;
        }

        this.coordinates = target;
        movementProgress = 0f;
    }

    private boolean tryHitObjectAt(GridPoint2 coordinates) {
        List<FieldObject> objects = world.getObjectsAt(coordinates);
        for (FieldObject object : objects) {
            if (object == this || object.isDestroyed()) {
                continue;
            }
            object.handleHit(getDamage());
            destroy();
            return true;
        }
        return false;
    }

    public boolean isFree() {
        return movementProgress == 1f;
    }

    public GridPoint2 getTargetCoordinates() {
        return new GridPoint2(coordinates).add(direction.getShift());
    }

    public float getMovementProgress() {
        return this.movementProgress;
    }

    public Direction getDirection() {
        return direction;
    }
}
