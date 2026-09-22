package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.util.Direction;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TankTest {

    private Tank tank;
    private List<Tree> obstacles;

    @Before
    public void setUp() {
        tank = new Tank(new GridPoint2(1, 1));
        obstacles = List.of(new Tree(new GridPoint2(1, 3)));
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
        tank.tryMove(Direction.RIGHT, obstacles);

        assertEquals(0f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertFalse(tank.isMovementEnded());
    }

    @Test
    public void tryMoveDoesNotMutateCurrentCell() {
        tank.tryMove(Direction.RIGHT, obstacles);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertNotEquals(tank.getCoordinates(), tank.getTargetCoordinates());
    }

    @Test
    public void tryMoveTowardsObstacleIsIgnored() {
        List<Tree> blockingObstacles = List.of(new Tree(new GridPoint2(1, 2)));

        tank.tryMove(Direction.UP, blockingObstacles);

        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(1, 1), tank.getTargetCoordinates());
        assertTrue(tank.isMovementEnded());
    }

    @Test
    public void tryMoveSetsRotation() {
        tank.tryMove(Direction.UP, obstacles);
        assertEquals(90f, tank.getRotation(), 0f);

        tank.processMovement(1f);
        tank.tryMove(Direction.DOWN, obstacles);
        assertEquals(-90f, tank.getRotation(), 0f);
    }

    @Test
    public void processMovementAdvancesProgressWithoutSnappingCell() {
        tank.tryMove(Direction.RIGHT, obstacles);
        tank.processMovement(0.2f);

        assertEquals(0.5f, tank.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    public void processMovementCompletesAndUpdatesCoordinates() {
        tank.tryMove(Direction.RIGHT, obstacles);
        tank.processMovement(0.4f);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertEquals(1f, tank.getMovementProgress(), 0f);
        assertTrue(tank.isMovementEnded());
    }

    @Test
    public void inputDuringMovementIsIgnored() {
        tank.tryMove(Direction.RIGHT, obstacles);
        tank.tryMove(Direction.UP, obstacles);

        assertEquals(new GridPoint2(2, 1), tank.getTargetCoordinates());
        assertEquals(0f, tank.getMovementProgress(), 0f);
    }
}