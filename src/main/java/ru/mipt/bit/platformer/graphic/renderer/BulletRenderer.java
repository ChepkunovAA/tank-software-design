package ru.mipt.bit.platformer.graphic.renderer;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;

public class BulletRenderer extends FieldObjectRenderer<Bullet> {

    private final TileMovement tileMovement;

    public BulletRenderer(String texturePath, TileMovement tileMovement) {
        super(Bullet.class, texturePath);
        this.tileMovement = tileMovement;
    }

    @Override
    protected void positionAt(Bullet bullet) {
        tileMovement.moveRectangleBetweenTileCenters(position, bullet.getCoordinates(),
                bullet.getTargetCoordinates(), bullet.getMovementProgress());
    }
}
