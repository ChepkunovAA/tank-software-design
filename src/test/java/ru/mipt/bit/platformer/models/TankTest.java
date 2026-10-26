package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TankTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    private Tank tank;
    private Set<GridPoint2> obstacles;

    @Before
    public void setUp() {
        tank = new Tank(new GridPoint2(1, 1));
        obstacles = Set.of(new GridPoint2(1, 3));
    }

    @Test
    public void initialStateIsIdleAtItsCell() {
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 1), tank.getTargetCoordinates());
        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertTrue(tank.isMovementEnded());
    }

    @Test
    public void tryMoveStartsMovementTowardsTarget() {
        tank.tryMove(Direction.RIGHT, obstacles, FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(0f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertFalse(tank.isMovementEnded());
    }

    @Test
    public void tryMoveDoesNotMutateCurrentCell() {
        tank.tryMove(Direction.RIGHT, obstacles, FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertNotEquals(tank.getCoordinates(), tank.getTargetCoordinates());
    }

    @Test
    public void tryMoveTowardsObstacleIsIgnored() {
        Set<GridPoint2> blockingObstacles = Set.of(new GridPoint2(1, 2));

        tank.tryMove(Direction.UP, blockingObstacles, FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(1, 1), tank.getTargetCoordinates());
        assertTrue(tank.isMovementEnded());
    }

    @Test
    public void tryMoveIntoAnotherTankDestinationIsIgnored() {
        Tank movingTank = new Tank(new GridPoint2(2, 2));
        movingTank.tryMove(Direction.RIGHT, Set.of(), FIELD_WIDTH, FIELD_HEIGHT);
        Tank otherTank = new Tank(new GridPoint2(4, 2));

        otherTank.tryMove(Direction.LEFT, Set.of(movingTank.getTargetCoordinates()),
                FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(1f, otherTank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(4, 2), otherTank.getTargetCoordinates());
        assertTrue(otherTank.isMovementEnded());
    }

    @Test
    public void tryMoveAwayFromOwnCellIsAllowedEvenIfCellIsInObstacles() {
        tank.tryMove(Direction.RIGHT, Set.of(tank.getCoordinates()), FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertFalse(tank.isMovementEnded());
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
        Tank cornerTank = new Tank(new GridPoint2(0, 0));

        cornerTank.tryMove(Direction.RIGHT, Set.of(), FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(new GridPoint2(1, 0), cornerTank.getTargetCoordinates());
        assertFalse(cornerTank.isMovementEnded());
    }

    @Test
    public void tryMoveSetsRotation() {
        tank.tryMove(Direction.UP, obstacles, FIELD_WIDTH, FIELD_HEIGHT);
        assertEquals(90f, tank.getRotation(), 0f);

        tank.processMovement(1f);
        tank.tryMove(Direction.DOWN, obstacles, FIELD_WIDTH, FIELD_HEIGHT);
        assertEquals(-90f, tank.getRotation(), 0f);
    }

    @Test
    public void processMovementAdvancesProgressWithoutSnappingCell() {
        tank.tryMove(Direction.RIGHT, obstacles, FIELD_WIDTH, FIELD_HEIGHT);
        tank.processMovement(0.2f);

        assertEquals(0.5f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    public void processMovementCompletesAndUpdatesCoordinates() {
        tank.tryMove(Direction.RIGHT, obstacles, FIELD_WIDTH, FIELD_HEIGHT);
        tank.processMovement(0.4f);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertTrue(tank.isMovementEnded());
    }

    @Test
    public void inputDuringMovementIsIgnored() {
        tank.tryMove(Direction.RIGHT, obstacles, FIELD_WIDTH, FIELD_HEIGHT);
        tank.tryMove(Direction.UP, obstacles, FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertEquals(0f, tank.getMovementProgress(), 0f);
    }

    private void assertBlockedAtBoundary(GridPoint2 start, Direction direction) {
        Tank boundaryTank = new Tank(start);

        boundaryTank.tryMove(direction, Set.of(), FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(start, boundaryTank.getTargetCoordinates());
        assertTrue(boundaryTank.isMovementEnded());
    }
}