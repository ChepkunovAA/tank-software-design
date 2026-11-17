package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.game.GameWorldImpl;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveActionTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    @Test
    public void moveActionDoesNotMovePlayerOutsideField() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 0)));

        new MoveAction(model.getPlayerTank(), Direction.LEFT).execute();

        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
        assertTrue(model.getPlayerTank().isFree());
    }

    @Test
    public void moveActionDoesNotMovePlayerIntoNpcTank() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 0)));
        model.addObject(new Tank(model, new GridPoint2(1, 0)));

        new MoveAction(model.getPlayerTank(), Direction.RIGHT).execute();

        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
    }

    @Test
    public void moveActionDoesNotMovePlayerIntoTree() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 0)));
        model.addObject(new Tree(new GridPoint2(1, 0)));

        new MoveAction(model.getPlayerTank(), Direction.RIGHT).execute();

        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
    }

    @Test
    public void moveActionMovesPlayerInsideField() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 0)));

        new MoveAction(model.getPlayerTank(), Direction.RIGHT).execute();

        assertEquals(new GridPoint2(1, 0), model.getPlayerTank().getTargetCoordinates());
    }

    @Test
    public void moveActionMovesAnyTankNotOnlyPlayer() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 0)));
        Tank npcTank = new Tank(model, new GridPoint2(2, 2));
        model.addObject(npcTank);

        new MoveAction(npcTank, Direction.RIGHT).execute();

        assertEquals(new GridPoint2(3, 2), npcTank.getTargetCoordinates());
        assertEquals(new GridPoint2(0, 0), model.getPlayerTank().getTargetCoordinates());
    }
}
