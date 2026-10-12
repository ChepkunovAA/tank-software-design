package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.models.GameModel;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LevelFactoryTest {

    @Test
    public void configuredLevelFactoryWithFileMethodParsesLevel() {
        GameModel model = new ConfiguredLevelFactory("file").generate();

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertTreePositions(model,
                new GridPoint2(2, 6), new GridPoint2(6, 4), new GridPoint2(1, 3));
    }

    @Test
    public void configuredLevelFactoryWithRandomMethodPlacesTankOnField() {
        GameModel model = new ConfiguredLevelFactory("random").generate();

        GridPoint2 tankPosition = model.getPlayerTank().getCoordinates();
        assertTrue(tankPosition.x >= 0 && tankPosition.x < 10);
        assertTrue(tankPosition.y >= 0 && tankPosition.y < 8);
    }

    @Test
    public void fileLevelFactoryParsesPlayerAndTrees() {
        GameModel model = new FileLevelFactory("levels/level1.txt").generate();

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertTreePositions(model,
                new GridPoint2(2, 6), new GridPoint2(6, 4), new GridPoint2(1, 3));
    }

    private void assertTreePositions(GameModel model, GridPoint2... expectedPositions) {
        List<GridPoint2> actualPositions = model.getObstacles().stream()
                .map(tree -> tree.getCoordinates())
                .toList();
        assertEquals(java.util.Set.of(expectedPositions), java.util.Set.copyOf(actualPositions));
    }

    @Test
    public void randomLevelFactoryPlacesTankOnFieldWithoutTreesWhenProbabilityIsZero() {
        RandomLevelFactory factory = new RandomLevelFactory(4, 3, 0f, new Random(42));

        GameModel model = factory.generate();

        GridPoint2 tankPosition = model.getPlayerTank().getCoordinates();
        assertTrue(tankPosition.x >= 0 && tankPosition.x < 4);
        assertTrue(tankPosition.y >= 0 && tankPosition.y < 3);
        assertTrue(model.getObstacles().isEmpty());
    }

    @Test
    public void randomLevelFactoryFillsEveryCellExceptTankWhenProbabilityIsOne() {
        RandomLevelFactory factory = new RandomLevelFactory(4, 3, 1f, new Random(1));

        GameModel model = factory.generate();

        assertEquals(4 * 3 - 1, model.getObstacles().size());
    }
}