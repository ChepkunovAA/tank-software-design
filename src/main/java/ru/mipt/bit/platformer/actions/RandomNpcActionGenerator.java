package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Properties;

import static com.badlogic.gdx.math.MathUtils.random;
import static ru.mipt.bit.platformer.util.PropertyReader.readLevelProperties;

public class RandomNpcActionGenerator implements NpcActionGenerator{
    private static final String NPC_AGGRESSION_PROPERTY = "npc.aggression";
    private final float shootProbability;
    
    public RandomNpcActionGenerator() {
        this(readLevelProperties());
    }

    RandomNpcActionGenerator(Properties properties) {
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
