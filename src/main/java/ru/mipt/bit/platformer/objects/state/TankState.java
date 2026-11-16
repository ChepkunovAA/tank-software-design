package ru.mipt.bit.platformer.objects.state;

import ru.mipt.bit.platformer.objects.Tank;

public abstract class TankState {

    protected Tank tank;

    public TankState(Tank tank) {
        this.tank = tank;
    }

    abstract public float getMovementSpeed();

    abstract public boolean canShoot();

    abstract public long getShootCooldown();

    abstract public void update();
}
