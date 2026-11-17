package ru.mipt.bit.platformer.graphic.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphic.HealthBarVisibility;
import ru.mipt.bit.platformer.objects.FieldObject;
import ru.mipt.bit.platformer.objects.Healthable;


public class HealthBarRenderer<T extends FieldObject & Healthable> implements ObjectRenderer<T> {

    private final ObjectRenderer<T> delegate;
    private final HealthBarVisibility visibility;
    private final HealthBarPainter painter;

    public HealthBarRenderer(ObjectRenderer<T> delegate, HealthBarVisibility visibility,
                             HealthBarPainter painter) {
        this.delegate = delegate;
        this.visibility = visibility;
        this.painter = painter;
    }

    @Override
    public Class<T> getObjectType() {
        return delegate.getObjectType();
    }

    @Override
    public void render(Batch batch, T object) {
        delegate.render(batch, object);
        if (visibility.isVisible()) {
            float fraction = fraction(object.getHitpoints(), object.getMaxHitpoints());
            painter.paint(batch, delegate.getObjectBounds(), fraction);
        }
    }

    @Override
    public Rectangle getObjectBounds() {
        return delegate.getObjectBounds();
    }

    @Override
    public void dispose() {
        delegate.dispose();
        painter.dispose();
    }

    private float fraction(int hitpoints, int maxHitpoints) {
        if (maxHitpoints <= 0) {
            return 0f;
        }
        return Math.max(0f, Math.min(1f, (float) hitpoints / maxHitpoints));
    }

}
