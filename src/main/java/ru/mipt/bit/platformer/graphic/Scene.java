package ru.mipt.bit.platformer.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.graphic.renderer.ObjectRenderer;
import ru.mipt.bit.platformer.objects.FieldObject;
import ru.mipt.bit.platformer.game.WorldObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Scene implements WorldObserver {

    private final List<ObjectRenderer<?>> renderers;
    private final Map<FieldObject, ObjectRenderer<FieldObject>> renderedObjects = new LinkedHashMap<>();

    public Scene(ObjectRenderer<?>... renderers) {
        this.renderers = new ArrayList<>(Arrays.asList(renderers));
    }

    public void register(ObjectRenderer<?> renderer) {
        renderers.add(renderer);
    }

    @Override
    public void onObjectAdded(FieldObject object) {
        renderedObjects.put(object, rendererFor(object));
    }

    @Override
    public void onObjectRemoved(FieldObject object) {
        renderedObjects.remove(object);
    }

    public void render(Batch batch) {
        for (Map.Entry<FieldObject, ObjectRenderer<FieldObject>> entry : renderedObjects.entrySet()) {
            entry.getValue().render(batch, entry.getKey());
        }
    }

    public Collection<FieldObject> getRenderedObjects() {
        return Collections.unmodifiableCollection(renderedObjects.keySet());
    }

    private ObjectRenderer<FieldObject> rendererFor(FieldObject object) {
        for (ObjectRenderer<?> renderer : renderers) {
            if (renderer.getObjectType().isInstance(object)) {
                return (ObjectRenderer<FieldObject>) renderer;
            }
        }
        throw new IllegalArgumentException("No renderer registered for " + object.getClass().getName());
    }

    public void dispose() {
        for (ObjectRenderer<?> renderer : renderers) {
            renderer.dispose();
        }
    }
}
