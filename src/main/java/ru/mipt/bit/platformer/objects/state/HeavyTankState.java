package ru.mipt.bit.platformer.objects.state;

import ru.mipt.bit.platformer.objects.Tank;

public class HeavyTankState extends TankState {

    public HeavyTankState(Tank tank) {
        super(tank);
    }

    @Override
    public float getMovementSpeed() {
        return tank.getBaseSpeed() / 3f;
    }

    @Override
    public boolean canShoot() {
        return false;
    }

    @Override
    public long getShootCooldown() {
        return tank.getBaseCooldown();
    }

    @Override
    public void update() {
        float ratio = tank.getHitpointsRatio();
        if (ratio > 0.7f) {
            tank.setState(new LightTankState(tank));
        } else if (ratio >= 0.15f) {
            tank.setState(new MediumTankState(tank));
        }
    }
}
