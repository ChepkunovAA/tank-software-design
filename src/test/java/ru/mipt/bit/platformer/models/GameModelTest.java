package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameModelTest {

    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 8;

    @Test
    public void playerTankSpawnsOnWantedCell() {
        Tank tank = new Tank(new GridPoint2(1, 1));
        GameModel model = new GameModel(tank, List.of(), List.of(), FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertEquals(1f, model.getPlayerTank().getMovementProgress(), 0f);
    }

    @Test
    public void treesContainSingleTree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        GameModel model = new GameModel(new Tank(new GridPoint2(1, 1)), List.of(), List.of(tree),
                FIELD_WIDTH, FIELD_HEIGHT);

        assertEquals(1, model.getTrees().size());
        assertEquals(new GridPoint2(1, 3), model.getTrees().get(0).getCoordinates());
    }

    @Test
    public void getTanksReturnsPlayerAndNpcTanks() {
        Tank npcTank = new Tank(new GridPoint2(3, 2));
        GameModel model = new GameModel(new Tank(new GridPoint2(1, 1)), List.of(npcTank), List.of(),
                FIELD_WIDTH, FIELD_HEIGHT);

        List<Tank> tanks = model.getTanks();

        assertEquals(2, tanks.size());
        assertEquals(new GridPoint2(1, 1), tanks.get(0).getCoordinates());
        assertEquals(new GridPoint2(3, 2), tanks.get(1).getCoordinates());
    }

    @Test
    public void getObstaclesReturnsCoordinatesOfTreesAndTanks() {
        Tank playerTank = new Tank(new GridPoint2(1, 1));
        Tank npcTank = new Tank(new GridPoint2(3, 2));
        Tree tree = new Tree(new GridPoint2(1, 3));
        GameModel model = new GameModel(playerTank, List.of(npcTank), List.of(tree),
                FIELD_WIDTH, FIELD_HEIGHT);

        Set<GridPoint2> obstacles = model.getObstacles();

        assertEquals(Set.of(new GridPoint2(1, 1), new GridPoint2(3, 2), new GridPoint2(1, 3)),
                obstacles);
    }

    @Test
    public void getObstaclesIncludesTankDestinations() {
        Tank playerTank = new Tank(new GridPoint2(1, 1));
        Tank npcTank = new Tank(new GridPoint2(3, 2));
        GameModel model = new GameModel(playerTank, List.of(npcTank), List.of(),
                FIELD_WIDTH, FIELD_HEIGHT);

        model.tryMove(npcTank, ru.mipt.bit.platformer.util.Direction.RIGHT);

        assertEquals(Set.of(new GridPoint2(1, 1), new GridPoint2(3, 2), new GridPoint2(4, 2)),
                model.getObstacles());
    }
}