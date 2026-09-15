package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class FieldObject {
    private Texture texture;
    private TextureRegion textureRegion;
    Rectangle position;
    GridPoint2 coordinates;
    float rotation;

    public FieldObject(String textureSource, GridPoint2 coordinates) {
        this.texture = new Texture(textureSource);
        this.textureRegion = new TextureRegion(texture);
        this.position = createBoundingRectangle(textureRegion);
        this.coordinates = coordinates;
        this.rotation = 0f;
    }

    public Rectangle getPosition() {
        return position;
    }

    public GridPoint2 getCoordinates() { return coordinates; }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, position, rotation);
    }

    public void dispose() {
        texture.dispose();
    }
}
