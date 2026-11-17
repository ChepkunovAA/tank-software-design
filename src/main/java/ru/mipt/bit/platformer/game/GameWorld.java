package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.FieldObject;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.List;

public interface GameWorld {

    List<FieldObject> getObjects();

    List<Tank> getNpcTanks();

    void addObject(FieldObject object);

    void removeObject(FieldObject object);

    boolean isInBounds(GridPoint2 coordinates);

    boolean isAvailable(GridPoint2 coordinates);

    List<FieldObject> getObjectsAt(GridPoint2 coordinates);

    void addObserver(WorldObserver observer);

    void removeObserver(WorldObserver observer);

    Tank getPlayerTank();

    void addPlayerTank(Tank tank);

    List<Bullet> getBullets();

    List<Tree> getTrees();
}
