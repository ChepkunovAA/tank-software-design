package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameWorld;
import ru.mipt.bit.platformer.game.GameWorldImpl;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static ru.mipt.bit.platformer.config.ConfigureConstants.FIELD_HEIGHT;
import static ru.mipt.bit.platformer.config.ConfigureConstants.FIELD_WIDTH;
import static ru.mipt.bit.platformer.util.PropertyReader.readLevelProperties;

public class RandomLevelFactory implements LevelFactory {

    private static final String TREE_PROBABILITY_PROPERTY = "tree.probability";
    private static final String TANK_PROBABILITY_PROPERTY = "tank.probability";

    private final int width;
    private final int height;
    private final float treeProbability;
    private final float tankProbability;
    private final Random random;

    public RandomLevelFactory() {
        this(FIELD_WIDTH, FIELD_HEIGHT, readLevelProperties(), new Random());
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
    public GameWorld generate() {
        GameWorld model = new GameWorldImpl(width, height);

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
                npcTanks.add(new Tank(model, coordinates));
            } else if (random.nextFloat() < treeProbability) {
                trees.add(new Tree(coordinates));
            }
        }

        model.addPlayerTank(new Tank(model, toCoordinates(playerIndex)));
        npcTanks.forEach(model::addObject);
        trees.forEach(model::addObject);
        return model;
    }

    private GridPoint2 toCoordinates(int index) {
        return new GridPoint2(index % width, index / width);
    }
}
