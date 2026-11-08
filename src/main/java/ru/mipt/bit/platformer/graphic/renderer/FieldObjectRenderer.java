package ru.mipt.bit.platformer.graphic.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.FieldObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class FieldObjectRenderer<T extends FieldObject> implements ObjectRenderer<T> {

    private final Class<T> objectType;
    private final Texture texture;
    private final TextureRegion textureRegion;
    protected final Rectangle position;

    protected FieldObjectRenderer(Class<T> objectType, String texturePath) {
        this.objectType = objectType;
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(texture);
        this.position = createBoundingRectangle(textureRegion);
    }

    @Override
    public Class<T> getObjectType() {
        return objectType;
    }

    @Override
    public void render(Batch batch, T object) {
        positionAt(object);
        drawTextureRegionUnscaled(batch, textureRegion, position, object.getRotation());
    }

    protected abstract void positionAt(T object);

    @Override
    public void dispose() {
        texture.dispose();
    }
}
