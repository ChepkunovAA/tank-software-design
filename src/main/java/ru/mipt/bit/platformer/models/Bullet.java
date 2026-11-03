package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Collection;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet extends FieldObject {

    private Direction direction;
    private int damage;

    private float movementProgress = 1f;
    private final float MOVEMENT_SPEED = 0.1f;

    public Bullet(GridPoint2 coordinates) {
        super(coordinates);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void handleHit(FieldObject object, GameModel model) {
        model.destroyBullet(this);
    }

    @Override
    public void live(float tick) {
        movementProgress = continueProgress(movementProgress, tick, MOVEMENT_SPEED);
        if (isFree()) {
            this.coordinates.add(direction.getShift());
        }
    }

    public boolean isFree() {
        return movementProgress == 1f;
    }

    public void tryMove(GameModel model) {
        if (!isFree()) {
            return;
        }
        for (FieldObject object : model.getAllObjects()) {
            if (object.coordinates.equals(coordinates)) {
                object.handleHit(this, model);
                model.destroyBullet(this);
                return;
            }
        }

        movementProgress = 0f;
    }

    public GridPoint2 getTargetCoordinates() {
        return new GridPoint2(coordinates).add(direction.getShift());
    }

    public float getMovementProgress() {
        return this.movementProgress;
    }
}
