package ru.mipt.bit.platformer.graphic.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class HealthBarPainter {

    private static final Color BACKGROUND = Color.GRAY;
    private static final Color FILLED = Color.GREEN;

    private static final float WIDTH_RATIO = 0.8f;
    private static final float HEIGHT = 6f;
    private static final float GAP = 2f;

    private final Texture pixel;

    public HealthBarPainter() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        this.pixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public void paint(Batch batch, Rectangle objectBounds, float fraction) {
        Rectangle background = background(objectBounds);
        batch.setColor(BACKGROUND);
        batch.draw(pixel, background.x, background.y, background.width, background.height);

        Rectangle fill = fill(background, fraction);
        batch.setColor(FILLED);
        batch.draw(pixel, fill.x, fill.y, fill.width, fill.height);

        batch.setColor(Color.WHITE);
    }

    private Rectangle background(Rectangle objectBounds) {
        float width = objectBounds.width * WIDTH_RATIO;
        return new Rectangle(
                objectBounds.x + (objectBounds.width - width) / 2f,
                objectBounds.y + objectBounds.height + GAP,
                width,
                HEIGHT);
    }

    private Rectangle fill(Rectangle background, float fraction) {
        return new Rectangle(background.x, background.y, background.width * fraction, background.height);
    }

    public void dispose() {
        pixel.dispose();
    }
}
