package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.models.Bullet;
import ru.mipt.bit.platformer.models.GameModel;

import java.util.ArrayList;
import java.util.List;

public class BulletShiftAction implements AbstractAction {
    @Override
    public void execute(GameModel model) {
        List<Bullet> bullets = new ArrayList<>(model.getBullets());
        for (Bullet bullet : bullets) {
            bullet.tryMove(model);
        }
    }
}
