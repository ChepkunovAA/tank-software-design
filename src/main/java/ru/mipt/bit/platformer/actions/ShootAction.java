package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;

public class ShootAction implements AbstractAction {
    private final Tank tank;

    public ShootAction(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute(GameModel model) {
        model.shoot(tank);
    }
}
