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

    private final int width;
    private final int height;
    private final float treeProbability;
    private final Random random;

    public RandomLevelFactory() {
        this(FIELD_WIDTH, FIELD_HEIGHT, readTreeProbability(), new Random());
    }

    RandomLevelFactory(int width, int height, float treeProbability, Random random) {
        this.width = width;
        this.height = height;
        this.treeProbability = treeProbability;
        this.random = random;
    }

    @Override
    public GameModel generate() {
        int totalCells = width * height;
        int tankIndex = random.nextInt(totalCells);
        GridPoint2 tankPosition = new GridPoint2(tankIndex % width, tankIndex / width);

        List<Tree> trees = new ArrayList<>();
        for (int i = 0; i < totalCells; i++) {
            if (i == tankIndex) {
                continue;
            }
            if (random.nextFloat() < treeProbability) {
                trees.add(new Tree(new GridPoint2(i % width, i / width)));
            }
        }

        return new GameModel(new Tank(tankPosition), trees);
    }

    private static float readTreeProbability() {
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
        return Float.parseFloat(properties.getProperty(TREE_PROBABILITY_PROPERTY));
    }
}