package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

public class MoveAction implements AbstractAction {

    private final Tank tank;
    private final Direction direction;

    public MoveAction(Tank tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    static public MoveAction UP(Tank tank) {
        return new MoveAction(tank, Direction.UP);
    }

    static public MoveAction DOWN(Tank tank) {
        return new MoveAction(tank, Direction.DOWN);
    }

    static public MoveAction LEFT(Tank tank) {
        return new MoveAction(tank, Direction.LEFT);
    }

    static public MoveAction RIGHT(Tank tank) {
        return new MoveAction(tank, Direction.RIGHT);
    }

    @Override
    public void execute(GameModel model) {
        model.tryMove(tank, direction);
    }
}