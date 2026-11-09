package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Test;
import ru.mipt.bit.platformer.graphic.renderer.ObjectRenderer;
import ru.mipt.bit.platformer.graphic.Scene;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.FieldObject;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.Direction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SceneTest {

    private final RecordingRenderer<Tank> tankRenderer = new RecordingRenderer<>(Tank.class);
    private final RecordingRenderer<Tree> treeRenderer = new RecordingRenderer<>(Tree.class);
    private final Scene scene = new Scene(tankRenderer, treeRenderer);

    @Test
    public void addedObjectIsDrawnWithRendererForItsType() {
        Tank tank = new Tank(null, new GridPoint2(1, 1));

        scene.onObjectAdded(tank);
        scene.render(null);

        assertEquals(List.of(tank), tankRenderer.rendered);
        assertTrue(treeRenderer.rendered.isEmpty());
    }

    @Test
    public void removedObjectIsNoLongerDrawn() {
        Tree tree = new Tree(new GridPoint2(2, 2));
        scene.onObjectAdded(tree);

        scene.onObjectRemoved(tree);
        scene.render(null);

        assertTrue(treeRenderer.rendered.isEmpty());
        assertTrue(scene.getRenderedObjects().isEmpty());
    }

    @Test
    public void objectsAreDrawnInInsertionOrder() {
        Tank first = new Tank(null, new GridPoint2(1, 1));
        Tree second = new Tree(new GridPoint2(2, 2));
        Tank third = new Tank(null, new GridPoint2(3, 3));

        scene.onObjectAdded(first);
        scene.onObjectAdded(second);
        scene.onObjectAdded(third);

        assertEquals(List.of(first, second, third), new ArrayList<>(scene.getRenderedObjects()));
    }

    @Test
    public void addingSameObjectTwiceKeepsSingleEntry() {
        Tank tank = new Tank(null, new GridPoint2(1, 1));

        scene.onObjectAdded(tank);
        scene.onObjectAdded(tank);

        assertEquals(1, scene.getRenderedObjects().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownObjectTypeIsRejected() {
        scene.onObjectAdded(new Bullet(null, new GridPoint2(0, 0), Direction.UP, 1));
    }

    @Test
    public void newlyRegisteredRendererIsUsedForSubsequentObjects() {
        RecordingRenderer<Bullet> bulletRenderer = new RecordingRenderer<>(Bullet.class);
        scene.register(bulletRenderer);
        Bullet bullet = new Bullet(null, new GridPoint2(0, 0), Direction.UP, 1);

        scene.onObjectAdded(bullet);
        scene.render(null);

        assertEquals(List.of(bullet), bulletRenderer.rendered);
    }

    @Test
    public void disposingSceneDisposesAllRenderers() {
        scene.dispose();

        assertTrue(tankRenderer.disposed);
        assertTrue(treeRenderer.disposed);
    }

    private static class RecordingRenderer<T extends FieldObject> implements ObjectRenderer<T> {

        private final Class<T> objectType;
        private final List<T> rendered = new ArrayList<>();
        private boolean disposed;

        RecordingRenderer(Class<T> objectType) {
            this.objectType = objectType;
        }

        @Override
        public Class<T> getObjectType() {
            return objectType;
        }

        @Override
        public void render(Batch batch, T object) {
            rendered.add(object);
        }

        @Override
        public Rectangle getObjectBounds() {
            return new Rectangle();
        }

        @Override
        public void dispose() {
            disposed = true;
        }
    }
}
