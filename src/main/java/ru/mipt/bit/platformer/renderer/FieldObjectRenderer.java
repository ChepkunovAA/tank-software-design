package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.models.FieldObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class FieldObjectRenderer {
    private Texture texture;
    protected TextureRegion textureRegion;
    protected Rectangle position;

    public FieldObjectRenderer(String texturePath) {
        texture = new Texture(texturePath);
        textureRegion = new TextureRegion(texture);
        position = createBoundingRectangle(textureRegion);
    }

    protected void renderObject(Batch batch, FieldObject object) {
        float rotation = object.getRotation();
        drawTextureRegionUnscaled(batch, textureRegion, position, rotation);
    }

    public void dispose() {
        texture.dispose();
    }
}
