package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.objects.Tank;

public class ShootAction implements AbstractAction {
    private final Tank tank;

    public ShootAction(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        tank.tryShoot();
    }
}
