package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.models.GameModel;

public class MoveAction implements AbstractAction {

    Direction direction;

    private MoveAction(Direction direction) {
        this.direction = direction;
    }

    static public MoveAction UP() {
        return new MoveAction(Direction.UP);
    }

    static public MoveAction DOWN() {
        return new MoveAction(Direction.DOWN);
    }

    static public MoveAction LEFT() {
        return new MoveAction(Direction.LEFT);
    }

    static public MoveAction RIGHT() {
        return new MoveAction(Direction.RIGHT);
    }

    @Override
    public void execute(GameModel model) {
        model.getPlayerTank().tryMove(direction, model.getObstacles());
    }
}
