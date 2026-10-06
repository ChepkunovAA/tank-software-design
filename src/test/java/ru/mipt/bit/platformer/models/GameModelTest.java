package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameModelTest {

    @Test
    public void playerTankSpawnsOnWantedCell() {
        GameModel model = new GameModel();

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertEquals(1f, model.getPlayerTank().getMovementProgress(), 0f);
    }

    @Test
    public void obstaclesContainSingleTree() {
        GameModel model = new GameModel();

        assertEquals(1, model.getObstacles().size());
        assertEquals(new GridPoint2(1, 3), model.getObstacles().get(0).getCoordinates());
    }
}