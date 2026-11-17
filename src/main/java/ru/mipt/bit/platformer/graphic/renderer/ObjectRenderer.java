package ru.mipt.bit.platformer.graphic.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.FieldObject;


public interface ObjectRenderer<T extends FieldObject> {

    Class<T> getObjectType();

    void render(Batch batch, T object);

    Rectangle getObjectBounds();

    void dispose();
}
