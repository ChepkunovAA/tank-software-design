package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameModelTest {

    @Test
    public void playerTankSpawnsOnWantedCell() {
        Tank tank = new Tank(new GridPoint2(1, 1));
        GameModel model = new GameModel(tank, List.of());

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertEquals(1f, model.getPlayerTank().getMovementProgress(), 0f);
    }

    @Test
    public void obstaclesContainSingleTree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        GameModel model = new GameModel(new Tank(new GridPoint2(1, 1)), List.of(tree));

        assertEquals(1, model.getObstacles().size());
        assertEquals(new GridPoint2(1, 3), model.getObstacles().get(0).getCoordinates());
    }
}