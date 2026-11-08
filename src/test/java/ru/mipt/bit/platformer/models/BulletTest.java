package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.game.GameWorldImpl;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BulletTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    private GameWorldImpl model;

    @Before
    public void setUp() {
        model = new GameWorldImpl(FIELD_WIDTH, FIELD_HEIGHT);
    }

    @Test
    public void bulletMovesOneCellOnFirstUpdate() {
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.RIGHT, 10);
        model.addObject(bullet);

        bullet.live(0.1f);

        assertEquals(new GridPoint2(3, 2), bullet.getCoordinates());
        assertEquals(0f, bullet.getMovementProgress(), 0f);
        assertFalse(bullet.isFree());
    }

    @Test
    public void bulletIsFrozenWhileMovementAnimationIsInProgress() {
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.RIGHT, 10);
        model.addObject(bullet);
        bullet.live(0.1f);

        bullet.live(0.02f);

        assertEquals(new GridPoint2(3, 2), bullet.getCoordinates());
        assertFalse(bullet.isFree());
    }

    @Test
    public void bulletMovesAgainAfterAnimationCompletes() {
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.RIGHT, 10);
        model.addObject(bullet);
        bullet.live(0.1f);

        bullet.live(0.1f);

        assertEquals(new GridPoint2(4, 2), bullet.getCoordinates());
    }

    @Test
    public void bulletTargetIsTheNextCellInItsDirection() {
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.UP, 10);

        assertEquals(new GridPoint2(2, 3), bullet.getTargetCoordinates());
    }

    @Test
    public void bulletDamagesTankItHits() {
        Tank tank = new Tank(model, new GridPoint2(3, 2));
        model.addObject(tank);
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.RIGHT, 40);
        model.addObject(bullet);

        bullet.live(0.1f);

        assertEquals(60, tank.getHitpoints());
        assertTrue(bullet.isDestroyed());
    }

    @Test
    public void bulletIsDestroyedAfterHittingATank() {
        Tank tank = new Tank(model, new GridPoint2(3, 2));
        model.addObject(tank);
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.RIGHT, 4);
        model.addObject(bullet);

        bullet.live(0.1f);

        assertEquals(new GridPoint2(2, 2), bullet.getCoordinates());
    }

    @Test
    public void bulletStopsOnTree() {
        model.addObject(new Tree(new GridPoint2(3, 2)));
        Bullet bullet = new Bullet(model, new GridPoint2(2, 2), Direction.RIGHT, 4);
        model.addObject(bullet);

        bullet.live(0.1f);

        assertEquals(new GridPoint2(2, 2), bullet.getCoordinates());
        assertTrue(bullet.isDestroyed());
    }

    @Test
    public void bulletIsDestroyedWhenLeavingTheField() {
        Bullet bullet = new Bullet(model, new GridPoint2(FIELD_WIDTH - 1, 2), Direction.RIGHT, 4);
        model.addObject(bullet);

        bullet.live(0.1f);

        assertTrue(bullet.isDestroyed());
    }
}
