package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.actions.AbstractAction;
import ru.mipt.bit.platformer.objects.FieldObject;

import java.util.ArrayList;
import java.util.List;


public class GameLoop {

    private final GameWorld storage;

    public GameLoop(GameWorld storage) {
        this.storage = storage;
    }

    public void update(float tick, List<AbstractAction> actions) {
        executeActions(actions);
        advanceObjects(tick);
        removeDestroyedObjects();
    }

    private void executeActions(List<AbstractAction> actions) {
        for (AbstractAction action : actions) {
            if (action != null) {
                action.execute();
            }
        }
    }

    private void advanceObjects(float tick) {
        for (FieldObject object : storage.getObjects()) {
            if (!object.isDestroyed()) {
                object.live(tick);
            }
        }
    }

    private void removeDestroyedObjects() {
        List<FieldObject> destroyed = new ArrayList<>();
        for (FieldObject object : storage.getObjects()) {
            if (object.isDestroyed()) {
                destroyed.add(object);
            }
        }
        for (FieldObject object : destroyed) {
            storage.removeObject(object);
        }
    }
}
