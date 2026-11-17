package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.game.GameLoop;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.game.GameWorldImpl;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.Direction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameLoopTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    @Test
    public void updateExecutesGivenActions() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank tank = new Tank(model, new GridPoint2(2, 2));
        model.addPlayerTank(tank);
        GameLoop gameLoop = new GameLoop(model);

        gameLoop.update(0f, List.of(new MoveAction(tank, Direction.RIGHT)));

        assertEquals(new GridPoint2(3, 2), tank.getTargetCoordinates());
    }

    @Test
    public void updateAdvancesObjects() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank tank = new Tank(model, new GridPoint2(2, 2));
        model.addPlayerTank(tank);
        GameLoop gameLoop = new GameLoop(model);

        gameLoop.update(0.4f, List.of(new MoveAction(tank, Direction.RIGHT)));

        assertEquals(new GridPoint2(3, 2), tank.getCoordinates());
        assertTrue(tank.isFree());
    }

    @Test
    public void updateRemovesDestroyedObjects() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 2)));
        Bullet bullet = new Bullet(model, new GridPoint2(5, 5), Direction.DOWN, 10);
        model.addObject(bullet);
        GameLoop gameLoop = new GameLoop(model);
        bullet.destroy();

        gameLoop.update(0f, List.of());

        assertFalse(model.getObjects().contains(bullet));
    }

    @Test
    public void updateRemovesTankDestroyedByBullet() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank playerTank = new Tank(model, new GridPoint2(2, 2));
        model.addPlayerTank(playerTank);
        Bullet bullet = new Bullet(model, new GridPoint2(2, 3), Direction.DOWN, 100);
        model.addObject(bullet);
        GameLoop gameLoop = new GameLoop(model);

        gameLoop.update(0.1f, List.of());

        assertTrue(playerTank.isDestroyed());
        assertEquals(List.of(), model.getTanks());
    }

    @Test
    public void spawnedBulletAppearsInTheWorld() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        Tank tank = new Tank(model, new GridPoint2(2, 2));
        model.addPlayerTank(tank);
        GameLoop gameLoop = new GameLoop(model);

        gameLoop.update(0f, List.of(new ShootAction(tank)));

        assertEquals(1, model.getBullets().size());
        assertEquals(new GridPoint2(3, 2), model.getBullets().get(0).getCoordinates());
    }

    @Test
    public void destroyedTreeDisappearsFromWorld() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(0, 2)));
        Tree tree = new Tree(new GridPoint2(5, 5));
        model.addObject(tree);
        GameLoop gameLoop = new GameLoop(model);
        tree.destroy();

        gameLoop.update(0f, List.of());

        assertTrue(model.getTrees().isEmpty());
    }

    @Test
    public void nullActionsAreSkipped() {
        GameWorldImpl model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
        model.addPlayerTank(new Tank(model, new GridPoint2(2, 2)));
        GameLoop gameLoop = new GameLoop(model);

        ArrayList<AbstractAction> actions = new ArrayList<>();
        actions.add(null);
        gameLoop.update(0f, actions);
    }
}
