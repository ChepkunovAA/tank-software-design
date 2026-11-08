package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.game.GameWorldImpl;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TankTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    private GameWorldImpl model;
    private Tank tank;

    @Before
    public void setUp() {
        model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        tank = new Tank(model, new GridPoint2(1, 1));
        model.addPlayerTank(tank);
    }

    @Test
    public void initialStateIsIdleAtItsCell() {
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 1), tank.getTargetCoordinates());
        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertTrue(tank.isFree());
    }

    @Test
    public void tryMoveStartsMovementTowardsTarget() {
        tank.tryMove(Direction.RIGHT);

        assertEquals(0f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertFalse(tank.isFree());
    }

    @Test
    public void tryMoveDoesNotMutateCurrentCell() {
        tank.tryMove(Direction.RIGHT);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertNotEquals(tank.getCoordinates(), tank.getTargetCoordinates());
    }

    @Test
    public void tryMoveTowardsObstacleIsIgnored() {
        model.addObject(new Tree(new GridPoint2(1, 2)));

        tank.tryMove(Direction.UP);

        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(1, 1), tank.getTargetCoordinates());
        assertTrue(tank.isFree());
    }

    @Test
    public void tryMoveIntoAnotherTankDestinationIsIgnored() {
        Tank movingTank = new Tank(model, new GridPoint2(2, 2));
        model.addObject(movingTank);
        movingTank.tryMove(Direction.RIGHT);
        Tank otherTank = new Tank(model, new GridPoint2(4, 2));
        model.addObject(otherTank);

        otherTank.tryMove(Direction.LEFT);

        assertEquals(1f, otherTank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(4, 2), otherTank.getTargetCoordinates());
        assertTrue(otherTank.isFree());
    }

    @Test
    public void tryMoveIntoAnotherTankCellIsIgnored() {
        Tank otherTank = new Tank(model, new GridPoint2(2, 1));
        model.addObject(otherTank);

        tank.tryMove(Direction.RIGHT);

        assertEquals(new GridPoint2(1, 1), tank.getTargetCoordinates());
        assertTrue(tank.isFree());
    }

    @Test
    public void tryMoveAwayFromOwnCellIsAllowedEvenIfCellIsAnObstacle() {
        tank.tryMove(Direction.RIGHT);

        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertFalse(tank.isFree());
    }

    @Test
    public void tryMoveOutsideFieldIsIgnored() {
        assertBlockedAtBoundary(new GridPoint2(0, 3), Direction.LEFT);
        assertBlockedAtBoundary(new GridPoint2(FIELD_WIDTH - 1, 3), Direction.RIGHT);
        assertBlockedAtBoundary(new GridPoint2(3, 0), Direction.DOWN);
        assertBlockedAtBoundary(new GridPoint2(3, FIELD_HEIGHT - 1), Direction.UP);
    }

    @Test
    public void tryMoveAlongBoundaryIsAllowed() {
        Tank cornerTank = new Tank(model, new GridPoint2(0, 0));
        model.addObject(cornerTank);

        cornerTank.tryMove(Direction.RIGHT);

        assertEquals(new GridPoint2(1, 0), cornerTank.getTargetCoordinates());
        assertFalse(cornerTank.isFree());
    }

    @Test
    public void inputDuringMovementIsIgnored() {
        tank.tryMove(Direction.RIGHT);
        tank.tryMove(Direction.UP);

        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertEquals(0f, tank.getMovementProgress(), 0f);
    }

    @Test
    public void tankOccupiesBothCellsWhileMoving() {
        tank.tryMove(Direction.RIGHT);

        assertEquals(java.util.Set.of(new GridPoint2(1, 1), new GridPoint2(2, 1)),
                tank.getOccupiedCoordinates());
    }

    @Test
    public void takeDamageReducesHitpointsButNotBelowZero() {
        tank.takeDamage(40);

        assertEquals(60, tank.getHitpoints());

        tank.takeDamage(100);

        assertEquals(0, tank.getHitpoints());
    }

    @Test
    public void negativeDamageIsIgnored() {
        tank.takeDamage(-5);

        assertEquals(100, tank.getHitpoints());
    }

    @Test
    public void killedTankIsMarkedAsDestroyed() {
        tank.handleHit(1000);

        assertTrue(tank.isDestroyed());
    }

    private void assertBlockedAtBoundary(GridPoint2 start, Direction direction) {
        Tank boundaryTank = new Tank(model, start);
        model.addObject(boundaryTank);

        boundaryTank.tryMove(direction);

        assertEquals(start, boundaryTank.getTargetCoordinates());
        assertTrue(boundaryTank.isFree());
    }
}
