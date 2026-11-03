package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.models.Tree;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LevelFactoryTest {

    @Test
    public void configuredLevelFactoryWithFileMethodParsesLevel() {
        GameModel model = new ConfiguredLevelFactory("file").generate();

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertTreePositions(model,
                new GridPoint2(2, 6), new GridPoint2(6, 4), new GridPoint2(1, 3));
        assertNpcPositions(model,
                new GridPoint2(5, 5), new GridPoint2(5, 0));
    }

    @Test
    public void configuredLevelFactoryWithRandomMethodPlacesTankOnField() {
        GameModel model = new ConfiguredLevelFactory("random").generate();

        GridPoint2 tankPosition = model.getPlayerTank().getCoordinates();
        assertTrue(tankPosition.x >= 0 && tankPosition.x < 10);
        assertTrue(tankPosition.y >= 0 && tankPosition.y < 8);
    }

    @Test
    public void fileLevelFactoryParsesPlayerTreesAndNpcTanks() {
        GameModel model = new FileLevelFactory("levels/level1.txt").generate();

        assertEquals(new GridPoint2(1, 1), model.getPlayerTank().getCoordinates());
        assertTreePositions(model,
                new GridPoint2(2, 6), new GridPoint2(6, 4), new GridPoint2(1, 3));
        assertNpcPositions(model,
                new GridPoint2(5, 5), new GridPoint2(5, 0));
    }

    @Test
    public void randomLevelFactoryPlacesOnlyPlayerWhenProbabilitiesAreZero() {
        RandomLevelFactory factory = new RandomLevelFactory(4, 3, 0f, 0f, new Random(42));

        GameModel model = factory.generate();

        GridPoint2 tankPosition = model.getPlayerTank().getCoordinates();
        assertTrue(tankPosition.x >= 0 && tankPosition.x < 4);
        assertTrue(tankPosition.y >= 0 && tankPosition.y < 3);
        assertTrue(model.getTrees().isEmpty());
        assertTrue(model.getNpcTanks().isEmpty());
    }

    @Test
    public void randomLevelFactoryFillsEveryFreeCellWithTreeWhenTreeProbabilityIsOne() {
        RandomLevelFactory factory = new RandomLevelFactory(4, 3, 1f, 0f, new Random(1));

        GameModel model = factory.generate();

        assertEquals(4 * 3 - 1, model.getTrees().size());
        assertTrue(model.getNpcTanks().isEmpty());
    }

    @Test
    public void randomLevelFactoryFillsEveryFreeCellWithNpcWhenTankProbabilityIsOne() {
        RandomLevelFactory factory = new RandomLevelFactory(4, 3, 0f, 1f, new Random(1));

        GameModel model = factory.generate();

        assertEquals(4 * 3 - 1, model.getNpcTanks().size());
        assertTrue(model.getTrees().isEmpty());
        assertFalse(npcPositions(model).contains(model.getPlayerTank().getCoordinates()));
    }

    private void assertTreePositions(GameModel model, GridPoint2... expectedPositions) {
        Set<GridPoint2> actualPositions = model.getTrees().stream()
                .map(Tree::getCoordinates).collect(Collectors.toSet());
        assertEquals(Set.of(expectedPositions), actualPositions);
    }

    private void assertNpcPositions(GameModel model, GridPoint2... expectedPositions) {
        assertEquals(Set.of(expectedPositions), npcPositions(model));
    }

    private Set<GridPoint2> npcPositions(GameModel model) {
        return model.getNpcTanks().stream()
                .map(Tank::getCoordinates)
                .collect(Collectors.toSet());
    }
}