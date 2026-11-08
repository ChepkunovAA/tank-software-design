package ru.mipt.bit.platformer.actions.provider;

import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Properties;
import java.util.Random;

import static ru.mipt.bit.platformer.util.PropertyReader.readLevelProperties;

public class RandomNpcActionGenerator implements NpcActionGenerator {

    private static final String NPC_AGGRESSION_PROPERTY = "npc.aggression";

    private final float shootProbability;
    private final Random random;

    public RandomNpcActionGenerator() {
        this(readLevelProperties(), new Random());
    }

    RandomNpcActionGenerator(Properties properties, Random random) {
        this.random = random;
        this.shootProbability = Float.parseFloat(properties.getProperty(NPC_AGGRESSION_PROPERTY));
    }

    @Override
    public AbstractAction getNextAction(Tank tank) {
        if (random.nextFloat() < shootProbability) {
            return new ShootAction(tank);
        }
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[random.nextInt(directions.length)];
        return new MoveAction(tank, randomDirection);
    }
}
