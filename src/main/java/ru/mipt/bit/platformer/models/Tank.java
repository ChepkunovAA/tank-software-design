package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Collection;

import static java.lang.Integer.max;
import static ru.mipt.bit.platformer.util.Direction.getDirectionByRotation;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends FieldObject {

    private GridPoint2 targetCoordinates;
    private float movementProgress;
    private int hitpoints;
    private int damage;

    private static final float MOVEMENT_SPEED = 0.4f;

    public Tank(GridPoint2 coordinates, int hitpoints, int damage) {
        super(coordinates);
        this.targetCoordinates = coordinates;
        this.movementProgress = 1f;
    }

    public Tank(GridPoint2 coordinates) {
        this(coordinates, 10, 10);
    }

    @Override
    public void handleHit(FieldObject object, GameModel model) {
        if (object instanceof Bullet) {
            Bullet bullet = (Bullet) object;
            takeDamage(bullet.getDamage());
        }
        if (!isAlive()) {
            model.destroyTank(this);
        }
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            return;
        }
        hitpoints = max(0, hitpoints - damage);
    }

    public boolean isAlive() {
        return hitpoints > 0;
    }

    public Bullet tryShoot(int fieldWidth, int fieldHeight) {
        Direction tankDirection = getDirectionByRotation(this.rotation);
        GridPoint2 bulletCoordinates = new GridPoint2(this.coordinates).add(tankDirection.getShift());
        if (bulletCoordinates.x < 0 || bulletCoordinates.x >= fieldWidth ||
                bulletCoordinates.y < 0 || bulletCoordinates.y >= fieldHeight) {
            return null;
        }
        Bullet bullet = new Bullet(bulletCoordinates);
        bullet.setDirection(tankDirection);
        bullet.setDamage(damage);
        return bullet;
    }

    public void tryMove(Direction movement, GameModel model) {
        if (!isFree()) {
            return;
        }
        GridPoint2 target = new GridPoint2(coordinates).add(movement.getShift());
        rotation = movement.getRotation();
        if (!model.isAvaibale(target)) {
            return;
        }

        movementProgress = 0f;
        targetCoordinates = target;
    }

    @Override
    public void live(float tick) {
        movementProgress = continueProgress(movementProgress, tick, MOVEMENT_SPEED);
        if (isFree()) {
            this.coordinates = targetCoordinates;
        }
    }

    public boolean isFree() {
        return movementProgress == 1f;
    }

    public GridPoint2 getTargetCoordinates() {
        return targetCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }
}
