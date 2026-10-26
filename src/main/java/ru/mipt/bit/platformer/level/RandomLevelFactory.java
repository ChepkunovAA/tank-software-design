package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.models.Tree;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static ru.mipt.bit.platformer.config.ConfigureConstants.FIELD_HEIGHT;
import static ru.mipt.bit.platformer.config.ConfigureConstants.FIELD_WIDTH;
import static ru.mipt.bit.platformer.config.ConfigureConstants.LEVEL_PROPERTIES;

public class RandomLevelFactory implements LevelFactory {

    private static final String TREE_PROBABILITY_PROPERTY = "tree.probability";
    private static final String TANK_PROBABILITY_PROPERTY = "tank.probability";

    private final int width;
    private final int height;
    private final float treeProbability;
    private final float tankProbability;
    private final Random random;

    public RandomLevelFactory() {
        this(FIELD_WIDTH, FIELD_HEIGHT, readProperties(), new Random());
    }

    RandomLevelFactory(int width, int height, Properties properties, Random random) {
        this(width, height,
                Float.parseFloat(properties.getProperty(TREE_PROBABILITY_PROPERTY)),
                Float.parseFloat(properties.getProperty(TANK_PROBABILITY_PROPERTY)),
                random);
    }

    RandomLevelFactory(int width, int height, float treeProbability, float tankProbability, Random random) {
        this.width = width;
        this.height = height;
        this.treeProbability = treeProbability;
        this.tankProbability = tankProbability;
        this.random = random;
    }

    @Override
    public GameModel generate() {
        int totalCells = width * height;
        int playerIndex = random.nextInt(totalCells);

        List<Tree> trees = new ArrayList<>();
        List<Tank> npcTanks = new ArrayList<>();
        for (int i = 0; i < totalCells; i++) {
            if (i == playerIndex) {
                continue;
            }
            GridPoint2 coordinates = toCoordinates(i);
            if (random.nextFloat() < tankProbability) {
                npcTanks.add(new Tank(coordinates));
            } else if (random.nextFloat() < treeProbability) {
                trees.add(new Tree(coordinates));
            }
        }

        return new GameModel(new Tank(toCoordinates(playerIndex)), npcTanks, trees, width, height);
    }

    private GridPoint2 toCoordinates(int index) {
        return new GridPoint2(index % width, index / width);
    }

    private static Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream input = RandomLevelFactory.class.getClassLoader()
                .getResourceAsStream(LEVEL_PROPERTIES)) {
            if (input == null) {
                throw new IllegalStateException("Resource not found: " + LEVEL_PROPERTIES);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read resource: " + LEVEL_PROPERTIES, e);
        }
        return properties;
    }
}