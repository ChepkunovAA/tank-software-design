package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankRenderer extends FieldObjectRenderer {

    public TankRenderer(String texturePath) {
        super(texturePath);
    }

    public void render(Batch batch, Tank tank, TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(position, tank.getCoordinates(),
                tank.getTargetCoordinates(), tank.getMovementProgress());
        renderObject(batch, tank);
    }
}
