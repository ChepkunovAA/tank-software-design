package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameControllerImplTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    @Test
    public void npcTankMovesInRandomlyChosenDirection() {
        Tank npcTank = new Tank(new GridPoint2(2, 2));
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(npcTank), List.of(),
                FIELD_WIDTH, FIELD_HEIGHT);
        GameControllerImpl controller = new GameControllerImpl(model, new SequenceRandom(3));

        controller.update(0f);

        assertEquals(new GridPoint2(3, 2), npcTank.getTargetCoordinates());
        assertFalse(npcTank.isMovementEnded());
    }

    @Test
    public void npcTankDoesNotEnterAnotherTankDestination() {
        Tank firstNpc = new Tank(new GridPoint2(1, 1));
        Tank secondNpc = new Tank(new GridPoint2(3, 1));
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(firstNpc, secondNpc),
                List.of(), FIELD_WIDTH, FIELD_HEIGHT);
        GameControllerImpl controller = new GameControllerImpl(model, new SequenceRandom(3, 2));

        controller.update(0f);

        assertEquals(new GridPoint2(2, 1), firstNpc.getTargetCoordinates());
        assertFalse(firstNpc.isMovementEnded());
        assertEquals(new GridPoint2(3, 1), secondNpc.getTargetCoordinates());
        assertTrue(secondNpc.isMovementEnded());
    }

    @Test
    public void npcTankReachesItsDestinationAfterEnoughTime() {
        Tank npcTank = new Tank(new GridPoint2(2, 2));
        GameModel model = new GameModel(new Tank(new GridPoint2(0, 0)), List.of(npcTank), List.of(),
                FIELD_WIDTH, FIELD_HEIGHT);
        GameControllerImpl controller = new GameControllerImpl(model, new SequenceRandom(3));

        controller.update(0.4f);

        assertEquals(new GridPoint2(3, 2), npcTank.getCoordinates());
        assertTrue(npcTank.isMovementEnded());
    }

    private static class SequenceRandom extends Random {

        private final int[] values;
        private int index;

        SequenceRandom(int... values) {
            this.values = values;
        }

        @Override
        public int nextInt(int bound) {
            int value = values[index % values.length];
            index++;
            return value;
        }
    }
}