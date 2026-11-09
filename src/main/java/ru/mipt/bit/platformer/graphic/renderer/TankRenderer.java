package ru.mipt.bit.platformer.graphic.renderer;

import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankRenderer extends FieldObjectRenderer<Tank> {

    private final TileMovement tileMovement;

    public TankRenderer(String texturePath, TileMovement tileMovement) {
        super(Tank.class, texturePath);
        this.tileMovement = tileMovement;
    }

    @Override
    protected void positionAt(Tank tank) {
        tileMovement.moveRectangleBetweenTileCenters(position, tank.getCoordinates(),
                tank.getTargetCoordinates(), tank.getMovementProgress());
    }
}
