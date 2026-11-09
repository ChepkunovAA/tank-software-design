package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameWorld;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Collections;
import java.util.Set;

import static java.lang.Integer.max;
import static ru.mipt.bit.platformer.util.Direction.getDirectionByRotation;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends FieldObject implements Healthable {

    private final GameWorld world;

    private GridPoint2 targetCoordinates;
    private float movementProgress;

    private final float speed;
    private int hitpoints;
    private final int maxHitpoints;
    private final int damage;

    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;
    private static final int DEFAULT_HITPOINTS = 100;
    private static final int DEFAULT_DAMAGE = 10;

    private long lastShootMoment = 0;
    private final long shootCoolDown = 1000;

    public Tank(GameWorld world, GridPoint2 coordinates, float speed, int hitpoints, int damage) {
        super(coordinates);
        this.world = world;
        this.targetCoordinates = coordinates;
        this.movementProgress = 1f;
        this.speed = speed;
        this.damage = damage;
        this.hitpoints = hitpoints;
        this.maxHitpoints = hitpoints;
    }

    public Tank(GameWorld world, GridPoint2 coordinates) {
        this(world, coordinates, DEFAULT_MOVEMENT_SPEED, DEFAULT_HITPOINTS, DEFAULT_DAMAGE);
    }

    @Override
    public void handleHit(int damage) {
        takeDamage(damage);
        if (hitpoints <= 0) {
            destroy();
        }
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            return;
        }
        hitpoints = max(0, hitpoints - damage);
    }

    @Override
    public Set<GridPoint2> getOccupiedCoordinates() {
        if (targetCoordinates.equals(coordinates)) {
            return Collections.singleton(coordinates);
        }
        return Set.of(coordinates, targetCoordinates);
    }

    public void tryShoot() {
        if (isDestroyed() || !isAbleToShoot()) {
            return;
        }

        lastShootMoment = System.currentTimeMillis();
        Direction tankDirection = getDirectionByRotation(this.rotation);
        GridPoint2 bulletCoordinates = new GridPoint2(this.coordinates).add(tankDirection.getShift());
        if (!world.isInBounds(bulletCoordinates)) {
            return;
        }
        world.addObject(new Bullet(world, bulletCoordinates, tankDirection, damage));
    }

    public void tryMove(Direction movement) {
        if (isDestroyed() || !isFree()) {
            return;
        }

        GridPoint2 target = new GridPoint2(coordinates).add(movement.getShift());
        rotation = movement.getRotation();
        if (!world.isAvailable(target)) {
            return;
        }

        movementProgress = 0f;
        targetCoordinates = target;
    }

    @Override
    public void live(float tick) {
        movementProgress = continueProgress(movementProgress, tick, speed);
        if (isFree()) {
            this.coordinates = targetCoordinates;
        }
    }

    public boolean isFree() {
        return movementProgress == 1f;
    }

    private boolean isAbleToShoot() {
        return System.currentTimeMillis() > lastShootMoment + shootCoolDown;
    }

    public GridPoint2 getTargetCoordinates() {
        return targetCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }
}
