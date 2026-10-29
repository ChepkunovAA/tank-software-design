package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveActionTest {

    @Test
    public void moveActionDoesNotMovePlayerOutsideField() {
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(), List.of(), 10, 8);

        new MoveAction(model.getPlayerTank(), Direction.LEFT).execute(model);

        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
        assertTrue(model.getPlayerTank().isMovementEnded());
    }

    @Test
    public void moveActionDoesNotMovePlayerIntoNpcTank() {
        Tank npcTank = new Tank(new GridPoint2(1, 0));
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(npcTank), List.of(),
                10, 8);

        new MoveAction(model.getPlayerTank(), Direction.RIGHT).execute(model);

        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
    }

    @Test
    public void moveActionMovesPlayerInsideField() {
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(), List.of(), 10, 8);

        new MoveAction(model.getPlayerTank(), Direction.RIGHT).execute(model);

        assertEquals(new GridPoint2(1, 0), model.getPlayerTank().getTargetCoordinates());
    }

    @Test
    public void moveActionMovesAnyTankNotOnlyPlayer() {
        Tank npcTank = new Tank(new GridPoint2(2, 2));
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(npcTank), List.of(),
                10, 8);

        new MoveAction(npcTank, Direction.RIGHT).execute(model);

        assertEquals(new GridPoint2(3, 2), npcTank.getTargetCoordinates());
        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
    }
}