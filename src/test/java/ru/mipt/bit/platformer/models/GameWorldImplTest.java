package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.game.GameWorldImpl;
import ru.mipt.bit.platformer.game.WorldObserver;
import ru.mipt.bit.platformer.objects.FieldObject;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GameWorldImplTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    @Test
    public void playerTankSpawnsOnWantedCell() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);

        model.addPlayerTank(new Tank(model, new GridPoint2(1, 1)));

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertEquals(1f, model.getPlayerTank().getMovementProgress(), 0f);
    }

    @Test
    public void treesContainSingleTree() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(1, 1)));
        model.addObject(new Tree(new GridPoint2(1, 3)));

        assertEquals(1, model.getTrees().size());
        assertEquals(new GridPoint2(1, 3), model.getTrees().get(0).getCoordinates());
    }

    @Test
    public void getTanksReturnsPlayerAndNpcTanks() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank npcTank = new Tank(model, new GridPoint2(3, 2));
        model.addPlayerTank(new Tank(model, new GridPoint2(1, 1)));
        model.addObject(npcTank);

        List<Tank> tanks = model.getTanks();

        assertEquals(2, tanks.size());
        assertEquals(new GridPoint2(1, 1), tanks.get(0).getCoordinates());
        assertEquals(new GridPoint2(3, 2), tanks.get(1).getCoordinates());
    }

    @Test
    public void getNpcTanksDoesNotContainPlayerTank() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank npcTank = new Tank(model, new GridPoint2(3, 2));
        model.addPlayerTank(new Tank(model, new GridPoint2(1, 1)));
        model.addObject(npcTank);

        assertEquals(List.of(npcTank), model.getNpcTanks());
    }

    @Test
    public void getObstaclesReturnsCoordinatesOfTreesAndTanks() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank npcTank = new Tank(model, new GridPoint2(3, 2));
        model.addPlayerTank(new Tank(model, new GridPoint2(1, 1)));
        model.addObject(npcTank);
        model.addObject(new Tree(new GridPoint2(1, 3)));

        Set<GridPoint2> obstacles = model.getObstacles();

        assertEquals(Set.of(new GridPoint2(1, 1), new GridPoint2(3, 2), new GridPoint2(1, 3)),
                obstacles);
    }

    @Test
    public void getObstaclesIncludesTankDestinations() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank npcTank = new Tank(model, new GridPoint2(3, 2));
        model.addPlayerTank(new Tank(model, new GridPoint2(1, 1)));
        model.addObject(npcTank);

        npcTank.tryMove(ru.mipt.bit.platformer.util.Direction.RIGHT);

        assertEquals(Set.of(new GridPoint2(1, 1), new GridPoint2(3, 2), new GridPoint2(4, 2)),
                model.getObstacles());
    }

    @Test
    public void obstaclesIgnoreDestroyedObjects() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tree tree = new Tree(new GridPoint2(1, 3));
        model.addObject(tree);

        tree.destroy();

        assertTrue(model.getObstacles().isEmpty());
    }

    @Test
    public void removingPlayerTankClearsPlayerReference() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank playerTank = new Tank(model, new GridPoint2(1, 1));
        model.addPlayerTank(playerTank);

        model.removeObject(playerTank);

        assertNull(model.getPlayerTank());
        assertTrue(model.getTanks().isEmpty());
    }

    @Test
    public void observerIsNotifiedAboutAddedObjects() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        RecordingObserver observer = new RecordingObserver();
        model.addObserver(observer);

        Tank tank = new Tank(model, new GridPoint2(1, 1));
        model.addPlayerTank(tank);
        Tree tree = new Tree(new GridPoint2(2, 2));
        model.addObject(tree);

        assertEquals(List.of(tank, tree), observer.added);
        assertTrue(observer.removed.isEmpty());
    }

    @Test
    public void observerAddedLaterReceivesExistingObjects() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank tank = new Tank(model, new GridPoint2(1, 1));
        model.addPlayerTank(tank);
        Tree tree = new Tree(new GridPoint2(2, 2));
        model.addObject(tree);

        RecordingObserver observer = new RecordingObserver();
        model.addObserver(observer);

        assertEquals(List.of(tank, tree), observer.added);
    }

    @Test
    public void observerIsNotifiedAboutRemovedObjects() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        RecordingObserver observer = new RecordingObserver();
        model.addObserver(observer);
        Tree tree = new Tree(new GridPoint2(2, 2));
        model.addObject(tree);

        model.removeObject(tree);

        assertEquals(List.of(tree), observer.removed);
    }

    @Test
    public void removedObserverIsNoLongerNotified() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        RecordingObserver observer = new RecordingObserver();
        model.addObserver(observer);
        model.removeObserver(observer);

        model.addObject(new Tree(new GridPoint2(2, 2)));

        assertTrue(observer.added.isEmpty());
    }

    @Test
    public void removingAbsentObjectDoesNotNotifyObservers() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        RecordingObserver observer = new RecordingObserver();
        model.addObserver(observer);

        model.removeObject(new Tree(new GridPoint2(2, 2)));

        assertTrue(observer.removed.isEmpty());
    }

    @Test
    public void cellsOutsideFieldAreNotInBounds() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);

        assertTrue(model.isInBounds(new GridPoint2(0, 0)));
        assertTrue(model.isInBounds(new GridPoint2(FIELD_WIDTH - 1, FIELD_HEIGHT - 1)));
        assertFalse(model.isInBounds(new GridPoint2(-1, 0)));
        assertFalse(model.isInBounds(new GridPoint2(FIELD_WIDTH, 0)));
        assertFalse(model.isInBounds(new GridPoint2(0, -1)));
        assertFalse(model.isInBounds(new GridPoint2(0, FIELD_HEIGHT)));
    }

    @Test
    public void occupiedCellsAreNotAvailable() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addObject(new Tree(new GridPoint2(2, 2)));

        assertFalse(model.isAvailable(new GridPoint2(2, 2)));
        assertTrue(model.isAvailable(new GridPoint2(2, 3)));
        assertFalse(model.isAvailable(new GridPoint2(-1, 3)));
    }

    private static class RecordingObserver implements WorldObserver {

        private final List<FieldObject> added = new java.util.ArrayList<>();
        private final List<FieldObject> removed = new java.util.ArrayList<>();

        @Override
        public void onObjectAdded(FieldObject object) {
            added.add(object);
        }

        @Override
        public void onObjectRemoved(FieldObject object) {
            removed.add(object);
        }
    }
}
